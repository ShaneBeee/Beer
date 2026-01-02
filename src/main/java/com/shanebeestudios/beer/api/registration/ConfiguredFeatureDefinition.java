package com.shanebeestudios.beer.api.registration;

import com.shanebeestudios.beer.api.utils.RegistryUtils;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ConfiguredFeatureDefinition implements Definition<ConfiguredFeature<?, ?>> {

    private final ResourceKey<ConfiguredFeature<?, ?>> resourceKey;
    private final ConfiguredFeature<?, ?> configuredFeature;
    private final List<TagKey<ConfiguredFeature<?, ?>>> tagKeys;

    private ConfiguredFeatureDefinition(ResourceKey<ConfiguredFeature<?, ?>> resourceKey, @NotNull ConfiguredFeature<?, ?> configuredFeature, @NotNull List<TagKey<ConfiguredFeature<?, ?>>> tagKeys) {
        this.resourceKey = resourceKey;
        this.configuredFeature = configuredFeature;
        this.tagKeys = tagKeys;
    }

    @Override
    public ConfiguredFeature<?, ?> getValue() {
        return this.configuredFeature;
    }

    @Override
    public ResourceKey<ConfiguredFeature<?, ?>> getResourceKey() {
        return this.resourceKey;
    }

    @Override
    public Holder.Reference<ConfiguredFeature<?, ?>> register() {
        return RegistryUtils.registerConfiguredFeature(this);
    }

    public List<TagKey<ConfiguredFeature<?, ?>>> getTagKeys() {
        return this.tagKeys;
    }

    public static Builder builder(ResourceKey<ConfiguredFeature<?, ?>> key) {
        return new Builder(key);
    }

    public static Builder builder() {
        return new Builder(null);
    }

    public static class Builder {

        private final ResourceKey<ConfiguredFeature<?, ?>> identifier;
        private Feature<? extends FeatureConfiguration> feature;
        private FeatureConfiguration config;

        public Builder(ResourceKey<ConfiguredFeature<?, ?>> key) {
            this.identifier = key;
        }

        public <F extends FeatureConfiguration> Builder config(Feature<F> feature, F config) {
            this.feature = feature;
            this.config = config;
            return this;
        }

        @SuppressWarnings({"rawtypes", "unchecked"})
        public ConfiguredFeatureDefinition build() {

            ConfiguredFeature<?, ?> fcfConfiguredFeature = new ConfiguredFeature(this.feature, this.config);

            return new ConfiguredFeatureDefinition(this.identifier, fcfConfiguredFeature, List.of());
        }

    }

}
