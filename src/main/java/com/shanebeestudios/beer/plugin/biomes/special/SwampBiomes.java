package com.shanebeestudios.beer.plugin.biomes.special;

import com.shanebeestudios.beer.plugin.registration.BeerBiomes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

@SuppressWarnings("unused")
public class SwampBiomes {

    public static ResourceKey<Biome> getBiome(int temp, int humidity, int weirdness) {
        return switch (temp) {
            case 0, 1 -> BeerBiomes.SWAMP_COLD_SWAMP;
            case 3 -> BeerBiomes.SWAMP_DRIPLEAF_SWAMP;
            case 4 -> Biomes.MANGROVE_SWAMP;
            default -> Biomes.SWAMP;
        };
    }

}
