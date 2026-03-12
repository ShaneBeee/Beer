package com.shanebeestudios.beer.api.registration;

import com.shanebeestudios.beer.api.utils.RegistryUtils;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.util.KeyframeTrack;
import net.minecraft.world.attribute.EnvironmentAttribute;
import net.minecraft.world.attribute.modifier.AttributeModifier;
import net.minecraft.world.timeline.Timeline;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public class TimelineDefinition implements Definition<Timeline> {

    private final ResourceKey<Timeline> resourceKey;
    private final Timeline timeline;
    private final List<TagKey<Timeline>> tagKeys;

    private TimelineDefinition(ResourceKey<Timeline> resourceKey, Timeline timeline, List<TagKey<Timeline>> tagKeys) {
        this.resourceKey = resourceKey;
        this.timeline = timeline;
        this.tagKeys = tagKeys;
    }

    @Override
    public Holder.Reference<Timeline> register() {
        return RegistryUtils.registerTimeline(this);
    }

    @Override
    public ResourceKey<Timeline> getResourceKey() {
        return this.resourceKey;
    }

    @Override
    public Timeline getValue() {
        return this.timeline;
    }

    @Override
    public List<TagKey<Timeline>> getTagKeys() {
        return this.tagKeys;
    }

    public static Builder builder(ResourceKey<Timeline> resourceKey) {
        return new Builder(resourceKey);
    }

    public static class Builder {

        private final ResourceKey<Timeline> resourceKey;
        private final Timeline.Builder builder = Timeline.builder();
        private final List<TagKey<Timeline>> tagKeys = new ArrayList<>();

        private Builder(ResourceKey<Timeline> resourceKey) {
            this.resourceKey = resourceKey;
        }

        public Builder periodTicks(int ticks) {
            this.builder.setPeriodTicks(ticks);
            return this;
        }

        public <Value, Argument> Builder addModifierTrack(EnvironmentAttribute<Value> attribute,
                                                          AttributeModifier<Value, Argument> modifier,
                                                          Consumer<KeyframeTrack.Builder<Argument>> builder) {
            this.builder.addModifierTrack(attribute, modifier, builder);
            return this;
        }

        public <Value> Builder addTrack(EnvironmentAttribute<Value> attribute, Consumer<KeyframeTrack.Builder<Value>> builder) {
            this.builder.addTrack(attribute, builder);
            return this;
        }

        @SafeVarargs
        public final Builder addToTag(TagKey<Timeline>... tagKeys) {
            Collections.addAll(this.tagKeys, tagKeys);
            return this;
        }

        public TimelineDefinition build() {
            return new TimelineDefinition(this.resourceKey, this.builder.build(), this.tagKeys);
        }

    }

}
