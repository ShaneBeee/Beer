package com.shanebeestudios.beer.plugin.biomes.special;

import com.shanebeestudios.beer.plugin.biomes.BeerBiomesOld;
import com.shanebeestudios.beer.api.utils.ParamPoints;
import org.bukkit.block.Biome;
import org.bukkit.generator.BiomeParameterPoint;
import org.jetbrains.annotations.NotNull;

public class BeachBiomes {

    public static @NotNull Biome getBiome(BiomeParameterPoint paramPoint) {
        int temp = ParamPoints.TEMPERATURE.getFixedPoint(paramPoint);
        int humidity = ParamPoints.HUMIDITY.getFixedPoint(paramPoint);
        int weirdness = ParamPoints.WEIRDNESS.getFixedPoint(paramPoint);
        return switch (temp) {
            case 0, 1 -> BeerBiomesOld.COAST_FROZEN_BEACH;
            case 2 -> weirdness == 0 ? BeerBiomesOld.COAST_COAST : BeerBiomesOld.COAST_BEACHY_BEACH;
            case 3 -> humidity <= 2 ? BeerBiomesOld.COAST_PALM_BEACH : BeerBiomesOld.COAST_LUSH_COAST;
            default -> humidity <= 2 ? BeerBiomesOld.COAST_DRY_COAST : BeerBiomesOld.COAST_LUSH_COAST;
        };
    }

}
