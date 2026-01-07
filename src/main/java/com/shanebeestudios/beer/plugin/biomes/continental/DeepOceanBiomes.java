package com.shanebeestudios.beer.plugin.biomes.continental;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

public class DeepOceanBiomes {

    public static ResourceKey<Biome> getBiome(int temp, int humidity, int weirdness) {
        return switch (temp) {
            case 0 -> Biomes.DEEP_FROZEN_OCEAN;
            case 1 -> Biomes.DEEP_COLD_OCEAN;
            case 2 -> Biomes.DEEP_OCEAN;
            case 3 -> Biomes.DEEP_LUKEWARM_OCEAN;
            default -> Biomes.WARM_OCEAN;
        };
    }

}
