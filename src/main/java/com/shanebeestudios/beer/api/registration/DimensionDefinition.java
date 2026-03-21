package com.shanebeestudios.beer.api.registration;

import com.mojang.datafixers.util.Pair;
import com.shanebeestudios.beer.api.utils.RegistryUtils;
import com.shanebeestudios.coreapi.util.Utils;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.Climate.Parameter;
import net.minecraft.world.level.biome.Climate.ParameterPoint;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class DimensionDefinition implements Definition<LevelStem> {

    ResourceKey<LevelStem> resourceKey;
    LevelStem levelStem;

    public DimensionDefinition(ResourceKey<LevelStem> resourceKey, LevelStem levelStem) {
        this.resourceKey = resourceKey;
        this.levelStem = levelStem;
    }

    @Override
    public Holder.Reference<LevelStem> register() {
        return null;
    }

    @Override
    public ResourceKey<LevelStem> getResourceKey() {
        return this.resourceKey;
    }

    @Override
    public LevelStem getValue() {
        return this.levelStem;
    }

    @Override
    public List<TagKey<LevelStem>> getTagKeys() {
        // Won't be needing this
        return List.of();
    }

    public static Builder overworldBuilder(ResourceKey<LevelStem> resourceKey) {
        return builder(resourceKey, BuiltinDimensionTypes.OVERWORLD, NoiseGeneratorSettings.OVERWORLD);
    }

    public static Builder builder(ResourceKey<LevelStem> resourceKey, ResourceKey<DimensionType> dimensionTypeKey, ResourceKey<NoiseGeneratorSettings> noiseGeneratorSettingsKey) {
        return new Builder(resourceKey, dimensionTypeKey, noiseGeneratorSettingsKey);
    }

    public static class Builder {

        ResourceKey<LevelStem> resourceKey;
        ResourceKey<DimensionType> dimensionType;
        ResourceKey<NoiseGeneratorSettings> noiseGeneratorSettingsKey;
        List<Pair<ParameterPoint, Holder<Biome>>> paramList = new ArrayList<>();
        java.util.Map<ResourceKey<Biome>, Holder.Reference<Biome>> biomeReferenceCache = new java.util.HashMap<>();

        public Builder(ResourceKey<LevelStem> resourceKey, ResourceKey<DimensionType> dimensionTypeKey, ResourceKey<NoiseGeneratorSettings> noiseGeneratorSettingsKey) {
            this.dimensionType = dimensionTypeKey;
            this.resourceKey = resourceKey;
            this.noiseGeneratorSettingsKey = noiseGeneratorSettingsKey;
        }

        @SuppressWarnings("UnusedReturnValue")
        public Builder addPoint(@NotNull ResourceKey<Biome> biomeKey, Parameter continentalness, Parameter temperature, Parameter humidity, Parameter erosion, Parameter depth, Parameter weirdness, long offset) {
            ParameterPoint parameterPoint = new ParameterPoint(temperature, humidity, continentalness, erosion, depth, weirdness, offset);
            Holder.Reference<Biome> biomeReference = biomeReferenceCache.computeIfAbsent(biomeKey, RegistryUtils::getBiomeReference);
            this.paramList.add(new Pair<>(parameterPoint, biomeReference));

            return this;
        }

        public Builder consolidate() {
            int originalSize = this.paramList.size();
            Utils.log("Starting consolidation with " + originalSize + " parameter points");

            // Debug: Count unique biomes and duplicates
            java.util.Map<Holder<Biome>, Integer> biomeCount = new java.util.HashMap<>();
            for (Pair<ParameterPoint, Holder<Biome>> pair : this.paramList) {
                biomeCount.merge(pair.getSecond(), 1, Integer::sum);
            }

            int uniqueBiomes = biomeCount.size();
            int biomesWithDuplicates = (int) biomeCount.values().stream().filter(count -> count > 1).count();
            Utils.log("Unique biomes: " + uniqueBiomes);
            Utils.log("Biomes with duplicates: " + biomesWithDuplicates);

            Utils.log("All biomes and their occurrence count:");
            biomeCount.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .limit(20)
                .forEach(e -> Utils.log("  " + e.getValue() + " times"));

            // Group by biome first to avoid O(n²) comparisons across all biomes
            java.util.Map<Holder<Biome>, java.util.List<Pair<ParameterPoint, Holder<Biome>>>> groupedByBiome = new java.util.HashMap<>();
            for (Pair<ParameterPoint, Holder<Biome>> pair : this.paramList) {
                groupedByBiome.computeIfAbsent(pair.getSecond(), k -> new ArrayList<>()).add(pair);
            }

            Utils.log("Processing " + groupedByBiome.size() + " unique biomes...");

            List<Pair<ParameterPoint, Holder<Biome>>> consolidated = new ArrayList<>();
            int totalMerges = 0;

            for (java.util.Map.Entry<Holder<Biome>, java.util.List<Pair<ParameterPoint, Holder<Biome>>>> entry : groupedByBiome.entrySet()) {
                java.util.List<Pair<ParameterPoint, Holder<Biome>>> biomePoints = new ArrayList<>(entry.getValue());
                int originalCount = biomePoints.size();

                boolean merged;
                do {
                    merged = false;
                    for (int i = 0; i < biomePoints.size(); i++) {
                        for (int j = i + 1; j < biomePoints.size(); j++) {
                            ParameterPoint mergedPoint = tryMerge(biomePoints.get(i).getFirst(), biomePoints.get(j).getFirst());
                            if (mergedPoint != null) {
                                biomePoints.set(i, new Pair<>(mergedPoint, entry.getKey()));
                                biomePoints.remove(j);
                                merged = true;
                                totalMerges++;
                                break;
                            }
                        }
                        if (merged) break;
                    }
                } while (merged);

                consolidated.addAll(biomePoints);
                if (originalCount > biomePoints.size()) {
                    Utils.log("  Merged " + originalCount + " -> " + biomePoints.size() + " points");
                }
            }

            this.paramList = consolidated;

            int finalSize = this.paramList.size();
            Utils.log("Consolidation complete!");
            Utils.log("Total merges performed: " + totalMerges);
            Utils.log("Reduced from " + originalSize + " to " + finalSize + " parameter points");
            Utils.log("Reduction: " + (originalSize - finalSize) + " points (" +
                String.format("%.2f", (100.0 * (originalSize - finalSize) / originalSize)) + "%)");

            return this;
        }

        private ParameterPoint tryMerge(ParameterPoint p1, ParameterPoint p2) {
            // Check if offset matches
            if (p1.offset() != p2.offset()) return null;

            int differences = 0;
            Parameter mergedTemp = null, mergedHumid = null, mergedCont = null;
            Parameter mergedErosion = null, mergedDepth = null, mergedWeird = null;

            // Temperature
            if (!parametersEqual(p1.temperature(), p2.temperature())) {
                mergedTemp = tryMergeParameter(p1.temperature(), p2.temperature());
                if (mergedTemp == null) return null;
                differences++;
            } else {
                mergedTemp = p1.temperature();
            }

            // Humidity
            if (!parametersEqual(p1.humidity(), p2.humidity())) {
                mergedHumid = tryMergeParameter(p1.humidity(), p2.humidity());
                if (mergedHumid == null) return null;
                differences++;
            } else {
                mergedHumid = p1.humidity();
            }

            // Continentalness
            if (!parametersEqual(p1.continentalness(), p2.continentalness())) {
                mergedCont = tryMergeParameter(p1.continentalness(), p2.continentalness());
                if (mergedCont == null) return null;
                differences++;
            } else {
                mergedCont = p1.continentalness();
            }

            // Erosion
            if (!parametersEqual(p1.erosion(), p2.erosion())) {
                mergedErosion = tryMergeParameter(p1.erosion(), p2.erosion());
                if (mergedErosion == null) return null;
                differences++;
            } else {
                mergedErosion = p1.erosion();
            }

            // Depth
            if (!parametersEqual(p1.depth(), p2.depth())) {
                mergedDepth = tryMergeParameter(p1.depth(), p2.depth());
                if (mergedDepth == null) return null;
                differences++;
            } else {
                mergedDepth = p1.depth();
            }

            // Weirdness
            if (!parametersEqual(p1.weirdness(), p2.weirdness())) {
                mergedWeird = tryMergeParameter(p1.weirdness(), p2.weirdness());
                if (mergedWeird == null) return null;
                differences++;
            } else {
                mergedWeird = p1.weirdness();
            }

            // Only merge if exactly one parameter differs
            if (differences != 1) return null;

            return new ParameterPoint(mergedTemp, mergedHumid, mergedCont, mergedErosion, mergedDepth, mergedWeird, p1.offset());
        }

        private boolean parametersEqual(Parameter p1, Parameter p2) {
            return p1.min() == p2.min() && p1.max() == p2.max();
        }

        private Parameter tryMergeParameter(Parameter p1, Parameter p2) {
            // Check if ranges are adjacent using quantized long values
            long p1Min = p1.min();
            long p1Max = p1.max();
            long p2Min = p2.min();
            long p2Max = p2.max();

            // Check if ranges are adjacent (edges touch)
            if (p1Max == p2Min) {
                // Convert quantized values back to floats using Climate's unquantize method
                // The quantization uses: quantize(x) = round(x * 10000)
                // So unquantize: x / 10000.0
                float newMin = Climate.unquantizeCoord(p1Min);
                float newMax = Climate.unquantizeCoord(p2Max);
                return Parameter.span(newMin, newMax);
            } else if (p2Max == p1Min) {
                float newMin = Climate.unquantizeCoord(p2Min);
                float newMax = Climate.unquantizeCoord(p1Max);
                return Parameter.span(newMin, newMax);
            }

            return null;
        }

        @SuppressWarnings("OptionalGetWithoutIsPresent")
        public DimensionDefinition build() {
            Holder.Reference<DimensionType> dimensionTypeReference = RegistryUtils.getRegistry(Registries.DIMENSION_TYPE).get(this.dimensionType).get();

            BiomeSource biomeSource = MultiNoiseBiomeSource.createFromList(new Climate.ParameterList<>(this.paramList));
            Holder.Reference<NoiseGeneratorSettings> genSettings = RegistryUtils.getRegistry(Registries.NOISE_SETTINGS).get(this.noiseGeneratorSettingsKey).get();

            LevelStem levelStem = new LevelStem(dimensionTypeReference, new NoiseBasedChunkGenerator(biomeSource, genSettings));

            return new DimensionDefinition(this.resourceKey, levelStem);
        }
    }

}
