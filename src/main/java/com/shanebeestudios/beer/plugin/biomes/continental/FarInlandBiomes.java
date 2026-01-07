package com.shanebeestudios.beer.plugin.biomes.continental;

import com.shanebeestudios.beer.plugin.biomes.special.BadlandBiomes;
import com.shanebeestudios.beer.plugin.biomes.special.MiddleBiomes;
import com.shanebeestudios.beer.plugin.biomes.special.PlateauBiomes;
import com.shanebeestudios.beer.plugin.biomes.special.RiverBiomes;
import com.shanebeestudios.beer.plugin.biomes.special.ShatteredBiomes;
import com.shanebeestudios.beer.plugin.registration.BeerBiomes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import org.jetbrains.annotations.NotNull;

public class FarInlandBiomes {

    public static @NotNull ResourceKey<Biome> getBiome(int temp, int humidity, int weirdness, int pv, int erosion) {
        return switch (pv) {
            case 0 -> getValley(temp, humidity, weirdness, erosion);
            case 1 -> getLow(temp, humidity, weirdness, erosion);
            case 2 -> getMid(temp, humidity, weirdness, erosion);
            case 3 -> getHigh(temp, humidity, weirdness, erosion);
            default -> getPeaks(temp, humidity, weirdness, erosion);
        };
    }

    public static @NotNull ResourceKey<Biome> getValley(int temp, int humidity, int weirdness, int erosion) {
        return switch (erosion) {
            case 0, 1 ->
                temp == 4 ? BadlandBiomes.getBiome(temp, humidity, weirdness) : MiddleBiomes.getBiome(temp, humidity, weirdness);
            case 2, 3, 4, 5 -> RiverBiomes.getBiome(temp, humidity, weirdness);
            default -> switch (temp) {
                case 0 -> RiverBiomes.getBiome(temp, humidity, weirdness);
                case 1, 2 -> BeerBiomes.SWAMP_DRIPLEAF_SWAMP;
                default -> Biomes.MANGROVE_SWAMP;
            };
        };
    }

    public static @NotNull ResourceKey<Biome> getLow(int temp, int humidity, int weirdness, int erosion) {
        return switch (erosion) {
            case 0, 1 -> switch (temp) {
                case 0 -> switch (humidity) {
                    case 0, 1 -> Biomes.SNOWY_SLOPES;
                    default -> Biomes.GROVE;
                };
                case 1, 2, 3 -> MiddleBiomes.getBiome(temp, humidity, weirdness);
                default -> BadlandBiomes.getBiome(temp, humidity, weirdness);
            };
            case 2, 3 ->
                temp == 4 ? BadlandBiomes.getBiome(temp, humidity, weirdness) : MiddleBiomes.getBiome(temp, humidity, weirdness);
            case 4, 5 -> MiddleBiomes.getBiome(temp, humidity, weirdness);
            default -> switch (temp) {
                case 0 -> MiddleBiomes.getBiome(temp, humidity, weirdness);
                case 1, 2 -> BeerBiomes.SWAMP_DRIPLEAF_SWAMP;
                default -> Biomes.MANGROVE_SWAMP;
            };
        };
    }

    public static @NotNull ResourceKey<Biome> getMid(int temp, int humidity, int weirdness, int erosion) {
        return switch (erosion) {
            case 0 ->
                temp < 3 ? humidity < 2 ? Biomes.SNOWY_SLOPES : Biomes.GROVE : PlateauBiomes.getBiome(temp, humidity, weirdness);
            case 1 -> {
                if (temp == 0) {
                    yield humidity <= 1 ? Biomes.SNOWY_SLOPES : Biomes.GROVE;
                } else if (temp < 4) {
                    yield MiddleBiomes.getBiome(temp, humidity, weirdness);
                } else {
                    yield BadlandBiomes.getBiome(temp, humidity, weirdness);
                }
            }
            case 2 -> PlateauBiomes.getBiome(temp, humidity, weirdness);
            case 3 ->
                temp < 4 ? MiddleBiomes.getBiome(temp, humidity, weirdness) : BadlandBiomes.getBiome(temp, humidity, weirdness);
            case 4 -> MiddleBiomes.getBiome(temp, humidity, weirdness);
            case 5 -> ShatteredBiomes.getBiome(temp, humidity, weirdness);
            default -> switch (temp) {
                case 0 -> MiddleBiomes.getBiome(temp, humidity, weirdness);
                case 1, 2 -> BeerBiomes.SWAMP_DRIPLEAF_SWAMP;
                default -> Biomes.MANGROVE_SWAMP;
            };
        };
    }

    public static @NotNull ResourceKey<Biome> getHigh(int temp, int humidity, int weirdness, int erosion) {
        return switch (erosion) {
            case 0 -> switch (temp) {
                case 0, 1, 2 -> weirdness == 1 ? Biomes.FROZEN_PEAKS : Biomes.JAGGED_PEAKS;
                case 3 -> Biomes.STONY_PEAKS;
                default -> BadlandBiomes.getBiome(temp, humidity, weirdness);
            };
            case 1 -> switch (temp) {
                case 0, 1, 2 -> humidity <= 1 ? Biomes.SNOWY_SLOPES : Biomes.GROVE;
                default -> PlateauBiomes.getBiome(temp, humidity, weirdness);
            };
            case 2, 3 -> PlateauBiomes.getBiome(temp, humidity, weirdness);
            case 4 -> MiddleBiomes.getBiome(temp, humidity, weirdness);
            case 5 -> ShatteredBiomes.getBiome(temp, humidity, weirdness);
            default -> MiddleBiomes.getBiome(temp, humidity, weirdness);
        };
    }

    public static @NotNull ResourceKey<Biome> getPeaks(int temp, int humidity, int weirdness, int erosion) {
        return switch (erosion) {
            case 0, 1 -> switch (temp) {
                case 0, 1, 2 -> weirdness == 1 ? Biomes.FROZEN_PEAKS : Biomes.JAGGED_PEAKS;
                case 3 -> Biomes.STONY_PEAKS;
                default -> BadlandBiomes.getBiome(temp, humidity, weirdness);
            };
            case 2, 3 -> PlateauBiomes.getBiome(temp, humidity, weirdness);
            case 4 -> MiddleBiomes.getBiome(temp, humidity, weirdness);
            case 5 -> ShatteredBiomes.getBiome(temp, humidity, weirdness);
            default -> MiddleBiomes.getBiome(temp, humidity, weirdness);
        };
    }

}
