package com.shanebeestudios.beer.api.registration;

import com.shanebeestudios.beer.api.utils.RegistryUtils;
import com.shanebeestudios.coreapi.util.Utils;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.data.worldgen.Carvers;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.attribute.AmbientParticle;
import net.minecraft.world.attribute.EnvironmentAttribute;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.attribute.modifier.AttributeModifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Create/Register a new Biome
 */
public class BiomeDefinition implements Definition<Biome> {

    private final ResourceKey<Biome> resourceKey;
    private final Biome biome;
    private final List<TagKey<Biome>> tagKeys;

    private BiomeDefinition(ResourceKey<Biome> resourceKey, Biome biome, List<TagKey<Biome>> tagKeys) {
        this.resourceKey = resourceKey;
        this.biome = biome;
        this.tagKeys = tagKeys;
    }

    @Override
    public ResourceKey<Biome> getResourceKey() {
        return this.resourceKey;
    }

    public List<TagKey<Biome>> getTagKeys() {
        return this.tagKeys;
    }

    @Override
    public Holder.Reference<Biome> register() {
        return RegistryUtils.registerBiome(this);
    }

    @Override
    public Biome getValue() {
        return this.biome;
    }

    public static Builder builder(ResourceKey<Biome> resourceKey) {
        return new Builder(resourceKey);
    }

    @SuppressWarnings("unused")
    public static class Builder {

        private final ResourceKey<Biome> resourceKey;
        private final Biome.BiomeBuilder biomeBuilder = new Biome.BiomeBuilder();
        private BiomeSpecialEffects.Builder specialEffects = null;
        private final BiomeGenerationSettings.PlainBuilder genSettings = new BiomeGenerationSettings.PlainBuilder();
        private final MobSpawnSettings.Builder mobSpawnSettings = new MobSpawnSettings.Builder();
        private final List<TagKey<Biome>> tagKeys = new ArrayList<>();

        private Builder(ResourceKey<Biome> resourceKey) {
            this.resourceKey = resourceKey;
        }

        /**
         * Set the environmental attribute of this biome.
         *
         * @param attribute Attribute to set
         * @param value     Value to set to
         * @param <T>       Type of value according to attribute
         * @return This builder
         */
        public <T> Builder setAttribute(EnvironmentAttribute<@NotNull T> attribute, T value) {
            this.biomeBuilder.setAttribute(attribute, value);
            return this;
        }

        /**
         * Apply a modifier to an environmental attribute of this biome.
         *
         * @param attribute Attribute to modify
         * @param modifier  Modifier to apply to attribute
         * @param parameter Value of modification
         * @param <T>       Type of value according to attribute
         * @param <P>       Type of modifier according to attribute
         * @return This builder
         */
        public <T, P> Builder modifyAttribute(EnvironmentAttribute<T> attribute, AttributeModifier<T, P> modifier, P parameter) {
            this.biomeBuilder.modifyAttribute(attribute, modifier, parameter);
            return this;
        }

        public Builder temperature(float temperature) {
            this.biomeBuilder.temperature(temperature);
            return this;
        }

        public Builder downfall(float downfall) {
            this.biomeBuilder.downfall(downfall);
            return this;
        }

        public Builder hasPrecipitation(boolean hasPrecipitation) {
            this.biomeBuilder.hasPrecipitation(hasPrecipitation);
            return this;
        }

        public Builder waterColor(int waterColor) {
            this.specialEffectsBuilder().waterColor(waterColor);
            return this;
        }

        public Builder foliageColorOverride(int foliageColor) {
            this.specialEffectsBuilder().foliageColorOverride(foliageColor);
            return this;
        }

        public Builder dryFoliageColorOverride(int dryFoliageColor) {
            this.specialEffectsBuilder().dryFoliageColorOverride(dryFoliageColor);
            return this;
        }

        public Builder grassColorOverride(int grassColor) {
            this.specialEffectsBuilder().grassColorOverride(grassColor);
            return this;
        }

        public Builder grassColorModifier(BiomeSpecialEffects.GrassColorModifier grassModifier) {
            this.specialEffectsBuilder().grassColorModifier(grassModifier);
            return this;
        }

        public Builder particle(ParticleOptions particleOption, float probability) {
            this.biomeBuilder.setAttribute(EnvironmentAttributes.AMBIENT_PARTICLES, AmbientParticle.of(particleOption, probability));
            return this;
        }

        public Builder features(List<?> rawGeneration,
                                List<?> lakes,
                                List<?> localModifications,
                                List<?> undergroundStructures,
                                List<?> surfaceStructures,
                                List<?> strongholds,
                                List<?> undergroundOres,
                                List<?> undergroundDecoration,
                                List<?> fluidSprings,
                                List<?> vegetalDecoration,
                                List<?> topLayerModification) {
            addFeaturesByList(rawGeneration, Decoration.RAW_GENERATION);
            addFeaturesByList(lakes, Decoration.LAKES);
            addFeaturesByList(localModifications, Decoration.LOCAL_MODIFICATIONS);
            addFeaturesByList(undergroundStructures, Decoration.UNDERGROUND_STRUCTURES);
            addFeaturesByList(surfaceStructures, Decoration.SURFACE_STRUCTURES);
            addFeaturesByList(strongholds, Decoration.STRONGHOLDS);
            addFeaturesByList(undergroundOres, Decoration.UNDERGROUND_ORES);
            addFeaturesByList(undergroundDecoration, Decoration.UNDERGROUND_DECORATION);
            addFeaturesByList(fluidSprings, Decoration.FLUID_SPRINGS);
            addFeaturesByList(vegetalDecoration, Decoration.VEGETAL_DECORATION);
            addFeaturesByList(topLayerModification, Decoration.TOP_LAYER_MODIFICATION);

            return this;
        }

        @SuppressWarnings("unchecked")
        private void addFeaturesByList(@Nullable List<?> list, Decoration decoration) {
            if (list == null) return;
            for (Object o : list) {
                switch (o) {
                    case String s -> {
                        Identifier identifier = Identifier.parse(s);
                        Holder<PlacedFeature> featureHolder = RegistryUtils.getPlacedFeature(identifier);
                        if (featureHolder != null) {
                            this.genSettings.addFeature(decoration, featureHolder);
                        } else {
                            Utils.log("&eUnknown feature &r'&b%s&r' &efound for biome &r'&a%s&r'",
                                identifier.toString(), this.resourceKey.identifier().toString());
                        }
                    }
                    case Holder.Reference<?> ref when ref.value() instanceof PlacedFeature ->
                        this.genSettings.addFeature(decoration, (Holder.Reference<PlacedFeature>) ref);
                    case ResourceKey<?> key -> {
                        Holder.Reference<PlacedFeature> ref = RegistryUtils.getPlacedFeatureReference((ResourceKey<PlacedFeature>) key);
                        this.genSettings.addFeature(decoration, ref);
                    }
                    case null, default -> Utils.log("&eUnknown feature &r'&b%s&r' &efound for biome &r'&a%s&r'",
                        o, this.resourceKey.identifier().toString());
                }
            }
        }

        @SuppressWarnings("unchecked")
        public Builder carvers(@NotNull Object... carvers) {
            for (Object o : carvers) {
                switch (o) {
                    case String s -> {
                        Identifier identifier = Identifier.parse(s);
                        Holder<ConfiguredWorldCarver<?>> featureHolder = RegistryUtils.getCarver(identifier);
                        if (featureHolder != null) {
                            this.genSettings.addCarver(featureHolder);
                        } else {
                            Utils.log("&eUnknown carver &r'&b%s&r' &efound for biome &r'&a%s&r'",
                                identifier.toString(), this.resourceKey.identifier().toString());
                        }
                    }
                    case Holder.Reference<?> ref when ref.value() instanceof ConfiguredWorldCarver<?> ->
                        this.genSettings.addCarver((Holder.Reference<ConfiguredWorldCarver<?>>) ref);
                    case ResourceKey<?> key -> {
                        Holder.Reference<ConfiguredWorldCarver<?>> ref = RegistryUtils.getCarverReference((ResourceKey<ConfiguredWorldCarver<?>>) key);
                        this.genSettings.addCarver(ref);
                    }
                    default -> Utils.log("&eUnknown carver &r'&b%s&r' &efound for biome &r'&a%s&r'",
                        o, this.resourceKey.identifier().toString());
                }
            }
            return this;
        }

        public Builder addDefaultOverworldCarvers() {
            return carvers(Carvers.CAVE, Carvers.CAVE_EXTRA_UNDERGROUND, Carvers.CANYON);
        }

        public Builder addTag(Identifier key) {
            TagKey<Biome> tagKey = RegistryUtils.getTagKey(RegistryUtils.getBiomeRegistry(), key.toString());
            if (tagKey != null) {
                this.tagKeys.add(tagKey);
            }
            return this;
        }

        public Builder addMobSpawn(MobCategory mobCategory, EntityType<?> entityType, int weight, int minCount, int maxCount) {
            minCount = Math.max(minCount, 1);
            maxCount = Math.max(maxCount, minCount);
            MobSpawnSettings.SpawnerData spawnerData = new MobSpawnSettings.SpawnerData(entityType, minCount, maxCount);
            this.mobSpawnSettings.addSpawn(mobCategory, weight, spawnerData);
            return this;
        }

        private BiomeSpecialEffects.Builder specialEffectsBuilder() {
            if (this.specialEffects == null) {
                this.specialEffects = new BiomeSpecialEffects.Builder();
            }
            return this.specialEffects;
        }

        public BiomeDefinition build() {
            this.biomeBuilder
                .specialEffects(Objects.requireNonNullElseGet(this.specialEffects, () ->
                        // Match from Plains if no special effects present
                        new BiomeSpecialEffects.Builder().waterColor(4159204))
                    .build())
                .generationSettings(this.genSettings.build())
                .mobSpawnSettings(this.mobSpawnSettings.build());

            return new BiomeDefinition(this.resourceKey, this.biomeBuilder.build(), this.tagKeys);
        }
    }

}
