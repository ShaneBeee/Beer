package com.shanebeestudios.beer.plugin.biomes.continental;

import com.shanebeestudios.beer.plugin.biomes.special.BadlandBiomes;
import com.shanebeestudios.beer.plugin.biomes.special.BeachBiomes;
import com.shanebeestudios.beer.plugin.biomes.special.MiddleBiomes;
import com.shanebeestudios.beer.plugin.biomes.special.RiverBiomes;
import com.shanebeestudios.beer.plugin.biomes.special.ShatteredBiomes;
import com.shanebeestudios.beer.plugin.registration.BeerBiomes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import org.jetbrains.annotations.NotNull;

public class CoastalBiomes {

    public static @NotNull ResourceKey<Biome> getBiome(int temp, int humidity, int weirdness, int pv, int erosion) {
        return switch (pv) {
            case 0 -> getValley(temp, humidity, weirdness);
            case 1 -> getLow(temp, humidity, weirdness, erosion);
            case 2 -> getMid(temp, humidity, weirdness, erosion);
            case 3 -> getHigh(temp, humidity, weirdness, erosion);
            default -> getPeaks(temp, humidity, weirdness, erosion);
        };
    }

    public static @NotNull ResourceKey<Biome> getValley(int temp, int humidity, int weirdness) {
        return switch (temp) {
            case 0, 1 -> RiverBiomes.getBiome(temp, humidity, weirdness);
            case 2 -> BeerBiomes.RIVER_TEMPERATE_RIVER;
            default -> humidity > 3 ? BeerBiomes.RIVER_LUSH_RIVER : BeerBiomes.RIVER_DESERT_RIVER;
        };
    }

    public static @NotNull ResourceKey<Biome> getLow(int temp, int humidity, int weirdness, int erosion) {
        return switch (erosion) {
            case 0, 1, 2 -> Biomes.STONY_SHORE;
            case 3, 4 -> BeachBiomes.getBiome(temp, humidity, weirdness);
            case 5 -> {
                if (weirdness == 1) {
                    yield BeachBiomes.getBiome(temp, humidity, weirdness);
                } else {
                    if (temp <= 1 || humidity == 4) {
                        yield MiddleBiomes.getBiome(temp, humidity, weirdness);
                    } else {
                        yield Biomes.WINDSWEPT_SAVANNA;
                    }
                }
            }
            default -> BeachBiomes.getBiome(temp, humidity, weirdness);
        };
    }

    public static @NotNull ResourceKey<Biome> getMid(int temp, int humidity, int weirdness, int erosion) {
        return switch (erosion) {
            case 0, 1, 2 -> Biomes.STONY_SHORE;
            case 3 -> MiddleBiomes.getBiome(temp, humidity, weirdness);
            case 4 ->
                weirdness == 0 ? BeachBiomes.getBiome(temp, humidity, weirdness) : MiddleBiomes.getBiome(temp, humidity, weirdness);
            case 5 -> {
                if (weirdness == 1) {
                    yield BeachBiomes.getBiome(temp, humidity, weirdness);
                } else {
                    if (temp <= 1 || humidity == 4) {
                        yield MiddleBiomes.getBiome(temp, humidity, weirdness);
                    } else {
                        yield Biomes.WINDSWEPT_SAVANNA;
                    }
                }
            }
            default ->
                weirdness == 0 ? BeachBiomes.getBiome(temp, humidity, weirdness) : MiddleBiomes.getBiome(temp, humidity, weirdness);
        };
    }

    public static @NotNull ResourceKey<Biome> getHigh(int temp, int humidity, int weirdness, int erosion) {
        return switch (erosion) {
            case 0, 1, 2, 3, 4 -> MiddleBiomes.getBiome(temp, humidity, weirdness);
            case 5 -> {
                if (weirdness == 0 || temp <= 1 || humidity == 4) {
                    yield MiddleBiomes.getBiome(temp, humidity, weirdness);
                } else {
                    yield Biomes.WINDSWEPT_SAVANNA;
                }
            }
            default -> MiddleBiomes.getBiome(temp, humidity, weirdness);
        };
    }

    public static @NotNull ResourceKey<Biome> getPeaks(int temp, int humidity, int weirdness, int erosion) {
        return switch (erosion) {
            case 0 -> {
                if (temp <= 2) yield weirdness == 1 ? Biomes.FROZEN_PEAKS : Biomes.JAGGED_PEAKS;
                else if (temp == 3) yield Biomes.STONY_PEAKS;
                else yield BadlandBiomes.getBiome(temp, humidity, weirdness);
            }
            case 1 -> {
                if (temp == 0) yield humidity <= 1 ? Biomes.SNOWY_SLOPES : Biomes.GROVE;
                else if (temp < 4) yield MiddleBiomes.getBiome(temp, humidity, weirdness);
                else yield BadlandBiomes.getBiome(temp, humidity, weirdness);
            }
            case 2, 3, 4 -> MiddleBiomes.getBiome(temp, humidity, weirdness);
            case 5 -> {
                if (weirdness == 0 || temp == 0 || temp == 1 || humidity == 4) {
                    yield ShatteredBiomes.getBiome(temp, humidity, weirdness);
                } else {
                    yield Biomes.WINDSWEPT_SAVANNA;
                }
            }
            default -> MiddleBiomes.getBiome(temp, humidity, weirdness);
        };
    }

}
