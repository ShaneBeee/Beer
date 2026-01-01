package com.shanebeestudios.beer.plugin.biomes.continental;

import com.shanebeestudios.beer.plugin.biomes.BeerBiomes;
import com.shanebeestudios.beer.api.utils.ParamPoints;
import com.shanebeestudios.beer.plugin.biomes.special.BadlandBiomes;
import com.shanebeestudios.beer.plugin.biomes.special.BeachBiomes;
import com.shanebeestudios.beer.plugin.biomes.special.MiddleBiomes;
import com.shanebeestudios.beer.plugin.biomes.special.RiverBiomes;
import com.shanebeestudios.beer.plugin.biomes.special.ShatteredBiomes;
import org.bukkit.block.Biome;
import org.bukkit.generator.BiomeParameterPoint;
import org.jetbrains.annotations.NotNull;

public class CoastalBiomes {

    public static @NotNull Biome getBiome(BiomeParameterPoint paramPoint) {
        int pv = ParamPoints.PEAKS_AND_VALLEYS.getFixedPoint(paramPoint);
        return switch (pv) {
            case 0 -> getValley(paramPoint);
            case 1 -> getLow(paramPoint);
            case 2 -> getMid(paramPoint);
            case 3 -> getHigh(paramPoint);
            default -> getPeaks(paramPoint);
        };
    }

    public static @NotNull Biome getValley(BiomeParameterPoint paramPoint) {
        int temp = ParamPoints.TEMPERATURE.getFixedPoint(paramPoint);
        int humidity = ParamPoints.HUMIDITY.getFixedPoint(paramPoint);
        return switch (temp) {
            case 0, 1 -> RiverBiomes.getBiome(paramPoint);
            case 2 -> BeerBiomes.RIVER_TEMPERATE_RIVER;
            default -> humidity > 3 ? BeerBiomes.RIVER_LUSH_RIVER : BeerBiomes.RIVER_DESERT_RIVER;
        };
    }

    public static @NotNull Biome getLow(BiomeParameterPoint paramPoint) {
        int erosion = ParamPoints.EROSION.getFixedPoint(paramPoint);
        int temp = ParamPoints.TEMPERATURE.getFixedPoint(paramPoint);
        int humidity = ParamPoints.HUMIDITY.getFixedPoint(paramPoint);
        int weirdness = ParamPoints.WEIRDNESS.getFixedPoint(paramPoint);
        return switch (erosion) {
            case 0, 1, 2 -> Biome.STONY_SHORE;
            case 3, 4 -> BeachBiomes.getBiome(paramPoint);
            case 5 -> {
                if (weirdness == 1) {
                    yield BeachBiomes.getBiome(paramPoint);
                } else {
                    if (temp <= 1 || humidity == 4) {
                        yield MiddleBiomes.getBiome(paramPoint);
                    } else {
                        yield Biome.WINDSWEPT_SAVANNA;
                    }
                }
            }
            default -> BeachBiomes.getBiome(paramPoint);
        };
    }

    public static @NotNull Biome getMid(BiomeParameterPoint paramPoint) {
        int erosion = ParamPoints.EROSION.getFixedPoint(paramPoint);
        int temp = ParamPoints.TEMPERATURE.getFixedPoint(paramPoint);
        int humidity = ParamPoints.HUMIDITY.getFixedPoint(paramPoint);
        int weirdness = ParamPoints.WEIRDNESS.getFixedPoint(paramPoint);
        return switch (erosion) {
            case 0, 1, 2 -> Biome.STONY_SHORE;
            case 3 -> MiddleBiomes.getBiome(paramPoint);
            case 4 -> weirdness == 0 ? BeachBiomes.getBiome(paramPoint) : MiddleBiomes.getBiome(paramPoint);
            case 5 -> {
                if (weirdness == 1) {
                    yield BeachBiomes.getBiome(paramPoint);
                } else {
                    if (temp <= 1 || humidity == 4) {
                        yield MiddleBiomes.getBiome(paramPoint);
                    } else {
                        yield Biome.WINDSWEPT_SAVANNA;
                    }
                }
            }
            default -> weirdness == 0 ? BeachBiomes.getBiome(paramPoint) : MiddleBiomes.getBiome(paramPoint);
        };
    }

    public static @NotNull Biome getHigh(BiomeParameterPoint paramPoint) {
        int erosion = ParamPoints.EROSION.getFixedPoint(paramPoint);
        int temp = ParamPoints.TEMPERATURE.getFixedPoint(paramPoint);
        int humidity = ParamPoints.HUMIDITY.getFixedPoint(paramPoint);
        int weirdness = ParamPoints.WEIRDNESS.getFixedPoint(paramPoint);
        return switch (erosion) {
            case 0, 1, 2, 3, 4 -> MiddleBiomes.getBiome(paramPoint);
            case 5 -> {
                if (weirdness == 0 || temp <= 1 || humidity == 4) {
                    yield MiddleBiomes.getBiome(paramPoint);
                } else {
                    yield Biome.WINDSWEPT_SAVANNA;
                }
            }
            default -> MiddleBiomes.getBiome(paramPoint);
        };
    }

    public static @NotNull Biome getPeaks(BiomeParameterPoint paramPoint) {
        int erosion = ParamPoints.EROSION.getFixedPoint(paramPoint);
        int temp = ParamPoints.TEMPERATURE.getFixedPoint(paramPoint);
        int weirdness = ParamPoints.WEIRDNESS.getFixedPoint(paramPoint);
        int humidity = ParamPoints.HUMIDITY.getFixedPoint(paramPoint);
        return switch (erosion) {
            case 0 -> {
                if (temp <= 2) yield weirdness == 1 ? Biome.FROZEN_PEAKS : Biome.JAGGED_PEAKS;
                else if (temp == 3) yield Biome.STONY_PEAKS;
                else yield BadlandBiomes.getBiome(paramPoint);
            }
            case 1 -> {
                if (temp == 0) yield humidity <= 1 ? Biome.SNOWY_SLOPES : Biome.GROVE;
                else if (temp < 4) yield MiddleBiomes.getBiome(paramPoint);
                else yield BadlandBiomes.getBiome(paramPoint);
            }
            case 2, 3, 4 -> MiddleBiomes.getBiome(paramPoint);
            case 5 -> {
                if (weirdness == 0 || temp == 0 || temp == 1 || humidity == 4) {
                    yield ShatteredBiomes.getBiome(paramPoint);
                } else {
                    yield Biome.WINDSWEPT_SAVANNA;
                }
            }
            default -> MiddleBiomes.getBiome(paramPoint);
        };
    }

}
