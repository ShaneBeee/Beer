package com.shanebeestudios.beer.plugin.biomes.special;

import com.shanebeestudios.beer.plugin.registration.BeerBiomes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.NotNull;

public class BeachBiomes {

    public static @NotNull ResourceKey<Biome> getBiome(int temp, int humidity, int weirdness) {
        return switch (temp) {
            case 0, 1 -> BeerBiomes.COAST_FROZEN_BEACH;
            case 2 -> weirdness == 0 ? BeerBiomes.COAST_COAST : BeerBiomes.COAST_BEACHY_COAST;
            case 3 -> humidity <= 2 ? BeerBiomes.COAST_PALM_BEACH : BeerBiomes.COAST_LUSH_COAST;
            default -> humidity <= 2 ? BeerBiomes.COAST_DRY_COAST : BeerBiomes.COAST_LUSH_COAST;
        };
    }

}
