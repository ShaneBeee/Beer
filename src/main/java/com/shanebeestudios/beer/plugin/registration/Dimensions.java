package com.shanebeestudios.beer.plugin.registration;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.dimension.LevelStem;

public class Dimensions {

    public static ResourceKey<LevelStem> BEER_TEST_WORLD = registerDimensions("test_world");

    private static ResourceKey<LevelStem> registerDimensions(String key) {
        return ResourceKey.create(Registries.LEVEL_STEM, Identifier.parse("beer:" + key));
    }

}
