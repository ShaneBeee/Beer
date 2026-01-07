package com.shanebeestudios.beer.plugin.biomes.continental;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

public class OceanBiomes {

    public static ResourceKey<Biome> getBiome(int temp, int humidity, int weirdness) {
        return switch (temp) {
            case 0 -> Biomes.FROZEN_OCEAN;
            case 1 -> Biomes.COLD_OCEAN;
            case 2 -> Biomes.OCEAN;
            case 3 -> Biomes.LUKEWARM_OCEAN;
            default -> Biomes.WARM_OCEAN;
        };
    }

}
