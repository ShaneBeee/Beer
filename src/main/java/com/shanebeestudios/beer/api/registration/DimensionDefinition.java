package com.shanebeestudios.beer.api.registration;

import com.mojang.datafixers.util.Pair;
import com.shanebeestudios.beer.api.utils.RegistryUtils;
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

        public Builder(ResourceKey<LevelStem> resourceKey, ResourceKey<DimensionType> dimensionTypeKey, ResourceKey<NoiseGeneratorSettings> noiseGeneratorSettingsKey) {
            this.dimensionType = dimensionTypeKey;
            this.resourceKey = resourceKey;
            this.noiseGeneratorSettingsKey = noiseGeneratorSettingsKey;
        }

        @SuppressWarnings("UnusedReturnValue")
        public Builder addPoint(ResourceKey<Biome> biomeKey, Parameter continentalness, Parameter temperature, Parameter humidity, Parameter erosion, Parameter depth, Parameter weirdness, long offset) {
            ParameterPoint parameterPoint = new ParameterPoint(temperature, humidity, continentalness, erosion, depth, weirdness, offset);
            Holder.Reference<Biome> biomeReference = RegistryUtils.getBiomeReference(biomeKey);
            this.paramList.add(new Pair<>(parameterPoint, biomeReference));

            return this;
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
