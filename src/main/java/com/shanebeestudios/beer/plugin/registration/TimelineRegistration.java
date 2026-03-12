package com.shanebeestudios.beer.plugin.registration;

import com.shanebeestudios.beer.api.registration.TimelineDefinition;
import com.shanebeestudios.beer.api.utils.DumpRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TimelineTags;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.attribute.modifier.FloatModifier;
import net.minecraft.world.timeline.Timeline;

import java.util.ArrayList;
import java.util.List;

public class TimelineRegistration {

    private static final List<TimelineDefinition> TIMELINES = new ArrayList<>();

    public static void registerTimelines() {
        TIMELINES.addAll(timelines());
    }

    public static void dumpToRegistry() {
        // Dump biomes to datapack files
        DumpRegistry.dumpDefinables(TIMELINES);
        DumpRegistry.dumpDefinablesTags(TIMELINES);
    }

    private static List<TimelineDefinition> timelines() {
        List<TimelineDefinition> timelines = new ArrayList<>();

        ResourceKey<Timeline> moonlightKey = register("moonlight");
        TimelineDefinition moonlight = TimelineDefinition.builder(moonlightKey)
            .periodTicks(192000)
            .addModifierTrack(EnvironmentAttributes.SKY_LIGHT_FACTOR,
                FloatModifier.MULTIPLY,
                builder -> builder
                    .addKeyframe(730, 1.0f)
                    .addKeyframe(11270, 1.0f)
                    .addKeyframe(13140, 1.0f)
                    .addKeyframe(22860, 1.0f)
                    .addKeyframe(24730, 1.0f)
                    .addKeyframe(35270, 1.0f)
                    .addKeyframe(37140, 0.75f)
                    .addKeyframe(46860, 0.75f)
                    .addKeyframe(48730, 1.0f)
                    .addKeyframe(59270, 1.0f)
                    .addKeyframe(61140, 0.5f)
                    .addKeyframe(70860, 0.5f)
                    .addKeyframe(72730, 1.0f)
                    .addKeyframe(83270, 1.0f)
                    .addKeyframe(85140, 0.2f)
                    .addKeyframe(94860, 0.2f)
                    .addKeyframe(96730, 1.0f)
                    .addKeyframe(107270, 1.0f)
                    .addKeyframe(109140, 0.0001f)
                    .addKeyframe(118860, 0.0001f)
                    .addKeyframe(120730, 1.0f)
                    .addKeyframe(131270, 1.0f)
                    .addKeyframe(133140, 0.2f)
                    .addKeyframe(142860, 0.2f)
                    .addKeyframe(144730, 1.0f)
                    .addKeyframe(155270, 1.0f)
                    .addKeyframe(157140, 0.5f)
                    .addKeyframe(166860, 0.5f)
                    .addKeyframe(168730, 1.0f)
                    .addKeyframe(179270, 1.0f)
                    .addKeyframe(181140, 0.75f)
                    .addKeyframe(190860, 0.75f))
            .addToTag(TimelineTags.IN_OVERWORLD)
            .build();

        moonlight.register();
        timelines.add(moonlight);

        return timelines;
    }

    @SuppressWarnings("SameParameterValue")
    private static ResourceKey<Timeline> register(String key) {
        return ResourceKey.create(Registries.TIMELINE, Identifier.parse("beer:" + key));
    }

}
