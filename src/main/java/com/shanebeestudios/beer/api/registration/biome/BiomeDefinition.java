package com.shanebeestudios.beer.api.registration.biome;


import com.shanebeestudios.beer.api.utils.RegistryUtils;
import com.shanebeestudios.coreapi.util.Utils;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.attribute.AmbientParticle;
import net.minecraft.world.attribute.EnvironmentAttribute;
import net.minecraft.world.attribute.EnvironmentAttributes;
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
public class BiomeDefinition {

    private final Identifier identifier;
    private final Biome biome;
    private final List<TagKey<Biome>> tagKeys;

    public BiomeDefinition(Identifier identifier, Biome biome, List<TagKey<Biome>> tagKeys) {
        this.identifier = identifier;
        this.biome = biome;
        this.tagKeys = tagKeys;
    }

    public Identifier getIdentifier() {
        return this.identifier;
    }

    public Biome getBiome() {
        return this.biome;
    }

    public List<TagKey<Biome>> getTagKeys() {
        return this.tagKeys;
    }

    public Biome register() {
        return RegistryUtils.registerBiome(this);
    }

    public static Builder builder(String key) {
        return new Builder(key);
    }

    public static class Builder {

        private final Identifier key;
        private final Biome.BiomeBuilder biomeBuilder = new Biome.BiomeBuilder();
        private BiomeSpecialEffects.Builder specialEffects = null;
        private final BiomeGenerationSettings.PlainBuilder genSettings = new BiomeGenerationSettings.PlainBuilder();
        private final MobSpawnSettings.Builder mobSpawnSettings = new MobSpawnSettings.Builder();
        private final List<TagKey<Biome>> tagKeys = new ArrayList<>();

        private Builder(String key) {
            this.key = Identifier.parse(key);
        }

        public <T> Builder setAttribute(EnvironmentAttribute<@NotNull T> attribute, T value) {
            this.biomeBuilder.setAttribute(attribute, value);
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

        public Builder dryFoliageColorrOverride(int dryFoliageColor) {
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

        public Builder features(List<String> rawGeneration,
                                List<String> lakes,
                                List<String> localModifications,
                                List<String> undergroundStructures,
                                List<String> surfaceStructures,
                                List<String> strongholds,
                                List<String> undergroundOres,
                                List<String> undergroundDecoration,
                                List<String> fluidSprings,
                                List<String> vegetalDecoration,
                                List<String> topLayerModification) {
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

        private void addFeaturesByList(@Nullable List<String> stringList, Decoration decoration) {
            if (stringList == null) return;
            for (String s : stringList) {
                Identifier identifier = Identifier.parse(s);
                Holder<PlacedFeature> featureHolder = RegistryUtils.getPlacedFeature(identifier);
                if (featureHolder != null) {
                    this.genSettings.addFeature(decoration, featureHolder);
                } else {
                    Utils.log("&eUnknown feature &r'&b%s&r'", identifier.toString());
                }
            }
        }

        public Builder carvers(@NotNull String... carvers) {
            for (String s : carvers) {
                Identifier identifier = Identifier.parse(s);
                Holder<ConfiguredWorldCarver<?>> featureHolder = RegistryUtils.getCarver(identifier);
                if (featureHolder != null) {
                    this.genSettings.addCarver(featureHolder);
                }else {
                    Utils.log("&eUnknown carver &r'&b%s&r'", identifier.toString());
                }
            }
            return this;
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

            return new BiomeDefinition(this.key, this.biomeBuilder.build(), this.tagKeys);
        }
    }

}
