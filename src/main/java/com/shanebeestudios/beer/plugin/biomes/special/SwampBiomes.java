package com.shanebeestudios.beer.plugin.biomes.special;

import com.shanebeestudios.beer.plugin.registration.BeerBiomes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

public class SwampBiomes {

    public static ResourceKey<Biome> getBiome(int temp, int humidity, int weirdness, int erosion, int pv) {
        if (pv <= 1 && erosion == 6) {
            if (temp == 1 || temp == 2) {
                return BeerBiomes.SWAMP_DRIPLEAF_SWAMP;
            } else if (temp == 3 || temp == 4) {
                return Biomes.MANGROVE_SWAMP;
            }
        }
        return null;
    }

}
