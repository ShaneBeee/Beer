package com.shanebeestudios.beer.plugin.biomes.special;

import com.shanebeestudios.beer.plugin.biomes.BeerBiomes;
import com.shanebeestudios.beer.api.utils.ParamPoints;
import org.bukkit.block.Biome;
import org.bukkit.generator.BiomeParameterPoint;
import org.jetbrains.annotations.NotNull;

public class ShatteredBiomes {

    public static @NotNull Biome getBiome(BiomeParameterPoint paramPoint) {
        int temp = ParamPoints.TEMPERATURE.getFixedPoint(paramPoint);
        return switch (temp) {
            case 0, 1 -> getCold(paramPoint);
            case 2 -> getTemperate(paramPoint);
            case 3 -> getWarm(paramPoint);
            default -> getHot(paramPoint);
        };
    }

    private static @NotNull Biome getCold(@NotNull BiomeParameterPoint paramPoint) {
        int humidity = ParamPoints.HUMIDITY.getFixedPoint(paramPoint);
        return switch (humidity) {
            case 0, 1 -> Biome.WINDSWEPT_GRAVELLY_HILLS;
            case 2 -> Biome.WINDSWEPT_HILLS;
            default -> Biome.WINDSWEPT_FOREST;
        };
    }

    private static @NotNull Biome getTemperate(@NotNull BiomeParameterPoint paramPoint) {
        int humidity = ParamPoints.HUMIDITY.getFixedPoint(paramPoint);
        return switch (humidity) {
            case 0, 1, 2 -> Biome.WINDSWEPT_HILLS;
            default -> Biome.WINDSWEPT_FOREST;
        };
    }

    private static @NotNull Biome getWarm(@NotNull BiomeParameterPoint paramPoint) {
        int humidity = ParamPoints.HUMIDITY.getFixedPoint(paramPoint);
        int weirdness = ParamPoints.WEIRDNESS.getFixedPoint(paramPoint);
        return switch (humidity) {
            case 0, 1 -> Biome.SAVANNA;
            case 2 -> weirdness == 1 ? BeerBiomes.PLAINS_PLAINS : Biome.FOREST;
            case 3 -> weirdness == 1 ? Biome.SPARSE_JUNGLE : Biome.JUNGLE;
            default -> weirdness == 1 ? Biome.BAMBOO_JUNGLE : Biome.JUNGLE;
        };
    }

    private static @NotNull Biome getHot(@NotNull BiomeParameterPoint paramPoint) {
        int humidity = ParamPoints.HUMIDITY.getFixedPoint(paramPoint);
        return humidity <= 2 ? BeerBiomes.DESERT_DRY_DESERT : BeerBiomes.DESERT_LUSH_DESERT;
    }

}
