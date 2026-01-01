package com.shanebeestudios.beer.api.registration.feature;

import com.shanebeestudios.beer.api.utils.RegistryUtils;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ConfiguredFeatureDefinition {

    private final Identifier identifier;
    private final ConfiguredFeature<?, ?> configuredFeature;
    private final List<TagKey<ConfiguredFeature<?, ?>>> tagKeys;

    private ConfiguredFeatureDefinition(@NotNull Identifier identifier, @NotNull ConfiguredFeature<?, ?> configuredFeature, @NotNull List<TagKey<ConfiguredFeature<?, ?>>> tagKeys) {
        this.identifier = identifier;
        this.configuredFeature = configuredFeature;
        this.tagKeys = tagKeys;
    }

    public Identifier getIdentifier() {
        return this.identifier;
    }

    public ConfiguredFeature<?, ?> getFeature() {
        return this.configuredFeature;
    }

    public Holder<ConfiguredFeature<?, ?>> getFeatureHolder() {
        return Holder.direct(this.configuredFeature);
    }

    public List<TagKey<ConfiguredFeature<?, ?>>> getTagKeys() {
        return this.tagKeys;
    }

    public Holder.Reference<ConfiguredFeature<?, ?>> register() {
        return RegistryUtils.registerConfiguredFeature(this);
    }

    public static Builder builder(String key) {
        return new Builder(key);
    }

    public static Builder builder() {
        return new Builder("holder");
    }

    public static class Builder {

        private final Identifier identifier;
        private Feature<? extends FeatureConfiguration> feature;
        private FeatureConfiguration config;

        public Builder(String key) {
            this.identifier = Identifier.parse(key);
        }

        public Builder feature(Feature<? extends FeatureConfiguration> feature) {
            this.feature = feature;
            return this;
        }

        public Builder config(FeatureConfiguration config) {
            this.config = config;
            return this;
        }

        public ConfiguredFeatureDefinition build() {

            ConfiguredFeature<?, ?> fcfConfiguredFeature = new ConfiguredFeature(this.feature, this.config);

            return new ConfiguredFeatureDefinition(this.identifier, fcfConfiguredFeature, List.of());
        }

    }

}
