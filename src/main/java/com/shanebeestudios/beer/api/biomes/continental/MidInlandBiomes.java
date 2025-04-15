package com.shanebeestudios.beer.api.biomes.continental;

import com.shanebeestudios.beer.api.BeerBiomes;
import com.shanebeestudios.beer.api.ParamPoints;
import com.shanebeestudios.beer.api.biomes.special.BadlandBiomes;
import com.shanebeestudios.beer.api.biomes.special.MiddleBiomes;
import com.shanebeestudios.beer.api.biomes.special.PlateauBiomes;
import com.shanebeestudios.beer.api.biomes.special.RiverBiomes;
import com.shanebeestudios.beer.api.biomes.special.ShatteredBiomes;
import org.bukkit.block.Biome;
import org.bukkit.generator.BiomeParameterPoint;
import org.jetbrains.annotations.NotNull;

public class MidInlandBiomes {

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
        int erosion = ParamPoints.EROSION.getFixedPoint(paramPoint);
        int temp = ParamPoints.TEMPERATURE.getFixedPoint(paramPoint);
        return switch (erosion) {
            case 0, 1 -> temp == 4 ? BadlandBiomes.getBiome(paramPoint) : MiddleBiomes.getBiome(paramPoint);
            case 2, 3, 4, 5 -> RiverBiomes.getBiome(paramPoint);
            default -> switch (temp) {
                case 0 -> RiverBiomes.getBiome(paramPoint);
                case 1, 2 -> BeerBiomes.SWAMP;
                default -> Biome.MANGROVE_SWAMP;
            };
        };
    }

    public static @NotNull Biome getLow(BiomeParameterPoint paramPoint) {
        int erosion = ParamPoints.EROSION.getFixedPoint(paramPoint);
        int temp = ParamPoints.TEMPERATURE.getFixedPoint(paramPoint);
        int humidity = ParamPoints.HUMIDITY.getFixedPoint(paramPoint);
        return switch (erosion) {
            case 0, 1 -> switch (temp) {
                case 0 -> switch (humidity) {
                    case 0, 1 -> Biome.SNOWY_SLOPES;
                    default -> Biome.GROVE;
                };
                case 1, 2, 3 -> MiddleBiomes.getBiome(paramPoint);
                default -> BadlandBiomes.getBiome(paramPoint);
            };
            case 2, 3 -> temp == 4 ? BadlandBiomes.getBiome(paramPoint) : MiddleBiomes.getBiome(paramPoint);
            case 4, 5 -> MiddleBiomes.getBiome(paramPoint);
            default -> switch (temp) {
                case 0 -> MiddleBiomes.getBiome(paramPoint);
                case 1, 2 -> BeerBiomes.SWAMP;
                default -> Biome.MANGROVE_SWAMP;
            };
        };
    }

    public static @NotNull Biome getMid(BiomeParameterPoint paramPoint) {
        int erosion = ParamPoints.EROSION.getFixedPoint(paramPoint);
        int temp = ParamPoints.TEMPERATURE.getFixedPoint(paramPoint);
        int humidity = ParamPoints.HUMIDITY.getFixedPoint(paramPoint);
        return switch (erosion) {
            case 0 -> temp < 3 ? humidity < 2 ? Biome.SNOWY_SLOPES : Biome.GROVE : PlateauBiomes.getBiome(paramPoint);
            case 1 -> {
                if (temp == 0) {
                    yield humidity <= 1 ? Biome.SNOWY_SLOPES : Biome.GROVE;
                } else if (temp < 4) {
                    yield MiddleBiomes.getBiome(paramPoint);
                } else {
                    yield BadlandBiomes.getBiome(paramPoint);
                }
            }
            case 2, 3 -> temp < 4 ? MiddleBiomes.getBiome(paramPoint) : BadlandBiomes.getBiome(paramPoint);
            case 4 -> MiddleBiomes.getBiome(paramPoint);
            case 5 -> ShatteredBiomes.getBiome(paramPoint);
            default -> switch (temp) {
                case 0 -> MiddleBiomes.getBiome(paramPoint);
                case 1, 2 -> BeerBiomes.SWAMP;
                default -> Biome.MANGROVE_SWAMP;
            };
        };
    }

    public static @NotNull Biome getHigh(BiomeParameterPoint paramPoint) {
        int erosion = ParamPoints.EROSION.getFixedPoint(paramPoint);
        int temp = ParamPoints.TEMPERATURE.getFixedPoint(paramPoint);
        int humidity = ParamPoints.HUMIDITY.getFixedPoint(paramPoint);
        int weirdness = ParamPoints.WEIRDNESS.getFixedPoint(paramPoint);
        return switch (erosion) {
            case 0 -> switch (temp) {
                case 0, 1, 2 -> weirdness == 1 ? Biome.FROZEN_PEAKS : Biome.JAGGED_PEAKS;
                case 3 -> Biome.STONY_PEAKS;
                default -> BadlandBiomes.getBiome(paramPoint);
            };
            case 1 -> switch (temp) {
                case 0, 1, 2 -> humidity <= 1 ? Biome.SNOWY_SLOPES : Biome.GROVE;
                default -> PlateauBiomes.getBiome(paramPoint);
            };
            case 2 -> PlateauBiomes.getBiome(paramPoint);
            case 3 -> temp < 4 ? MiddleBiomes.getBiome(paramPoint) : BadlandBiomes.getBiome(paramPoint);
            case 4 -> MiddleBiomes.getBiome(paramPoint);
            case 5 -> ShatteredBiomes.getBiome(paramPoint);
            default -> MiddleBiomes.getBiome(paramPoint);
        };
    }

    public static @NotNull Biome getPeaks(BiomeParameterPoint paramPoint) {
        int erosion = ParamPoints.EROSION.getFixedPoint(paramPoint);
        int temp = ParamPoints.TEMPERATURE.getFixedPoint(paramPoint);
        int weirdness = ParamPoints.WEIRDNESS.getFixedPoint(paramPoint);
        return switch (erosion) {
            case 0,1 -> switch (temp) {
                case 0, 1, 2 -> weirdness == 1 ? Biome.FROZEN_PEAKS : Biome.JAGGED_PEAKS;
                case 3 -> Biome.STONY_PEAKS;
                default -> BadlandBiomes.getBiome(paramPoint);
            };
            case 2 -> PlateauBiomes.getBiome(paramPoint);
            case 3 -> temp < 4 ? MiddleBiomes.getBiome(paramPoint) : BadlandBiomes.getBiome(paramPoint);
            case 4 -> MiddleBiomes.getBiome(paramPoint);
            case 5 -> ShatteredBiomes.getBiome(paramPoint);
            default -> MiddleBiomes.getBiome(paramPoint);
        };
    }

}
