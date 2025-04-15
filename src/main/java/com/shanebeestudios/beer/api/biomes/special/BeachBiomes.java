package com.shanebeestudios.beer.api.biomes.special;

import com.shanebeestudios.beer.api.BeerBiomes;
import com.shanebeestudios.beer.api.ParamPoints;
import org.bukkit.block.Biome;
import org.bukkit.generator.BiomeParameterPoint;
import org.jetbrains.annotations.NotNull;

public class BeachBiomes {

    public static @NotNull Biome getBiome(BiomeParameterPoint paramPoint) {
        int temp = ParamPoints.TEMPERATURE.getFixedPoint(paramPoint);
        int humidity = ParamPoints.HUMIDITY.getFixedPoint(paramPoint);
        int weirdness = ParamPoints.WEIRDNESS.getFixedPoint(paramPoint);
        return switch (temp) {
            case 0, 1 -> BeerBiomes.COAST_FROZEN_BEACH;
            case 2 -> weirdness == 0 ? BeerBiomes.COAST_COAST : BeerBiomes.COAST_BEACHY_BEACH;
            case 3 -> humidity <= 2 ? BeerBiomes.COAST_PALM_BEACH : BeerBiomes.COAST_LUSH_COAST;
            default -> humidity <= 2 ? BeerBiomes.COAST_DRY_COAST : BeerBiomes.COAST_LUSH_COAST;
        };
    }

}
