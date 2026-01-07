package com.shanebeestudios.beer.plugin.biomes.special;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

public class BadlandBiomes {

    public static ResourceKey<Biome> getBiome(int temp, int humidity, int weirdness) {
        return switch (humidity) {
            case 0, 1 -> weirdness == 1 ? Biomes.ERODED_BADLANDS : Biomes.BADLANDS;
            case 2 -> Biomes.BADLANDS;
            default -> Biomes.WOODED_BADLANDS;
        };
    }

}
