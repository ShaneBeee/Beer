package com.shanebeestudios.beer.plugin.biomes.special;

import com.shanebeestudios.beer.plugin.registration.BeerBiomes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import org.jetbrains.annotations.Nullable;

public class CaveBiomes {

    @Nullable
    public static ResourceKey<Biome> getBiome(int temp, int humidity) {
        if (temp <= 1) {
            return BeerBiomes.CAVE_ICE_CAVE;
        } else if (temp >= 3) {
            if (humidity <= 1) return BeerBiomes.CAVE_DRY_CAVE;
            else if (humidity >= 3) return Biomes.LUSH_CAVES;
        }
        return null;
    }

}
