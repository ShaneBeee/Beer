package com.shanebeestudios.beer.plugin.biomes.continental;

import com.shanebeestudios.beer.plugin.biomes.special.BadlandBiomes;
import com.shanebeestudios.beer.plugin.biomes.special.MiddleBiomes;
import com.shanebeestudios.beer.plugin.biomes.special.PlateauBiomes;
import com.shanebeestudios.beer.plugin.biomes.special.RiverBiomes;
import com.shanebeestudios.beer.plugin.biomes.special.ShatteredBiomes;
import com.shanebeestudios.beer.plugin.biomes.special.SwampBiomes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("SwitchStatementWithTooFewBranches")
public class NearInlandBiomes {

    public static @NotNull ResourceKey<Biome> getBiome(int temp, int humidity, int weirdness, int pv, int erosion) {
        return switch (pv) {
            case 0 -> getValley(temp, humidity, weirdness, pv, erosion);
            case 1 -> getLow(temp, humidity, weirdness, pv, erosion);
            case 2 -> getMid(temp, humidity, weirdness, pv, erosion);
            case 3 -> getHigh(temp, humidity, weirdness, pv, erosion);
            default -> getPeaks(temp, humidity, weirdness, pv, erosion);
        };
    }

    public static @NotNull ResourceKey<Biome> getValley(int temp, int humidity, int weirdness, int pv, int erosion) {
        if (erosion == 6) return SwampBiomes.getBiome(temp, humidity, weirdness);
        return RiverBiomes.getBiome(temp, humidity, weirdness);
    }

    public static @NotNull ResourceKey<Biome> getLow(int temp, int humidity, int weirdness, int pv, int erosion) {
        return switch (erosion) {
            case 0, 1 -> temp == 4 ? BadlandBiomes.getBiome(temp, humidity, weirdness) :
                MiddleBiomes.getBiome(temp, humidity, weirdness);
            case 2, 3, 4 -> MiddleBiomes.getBiome(temp, humidity, weirdness);
            case 5 -> {
                if (weirdness == 0 || temp == 0 || temp == 1 || humidity == 4) {
                    yield MiddleBiomes.getBiome(temp, humidity, weirdness);
                } else {
                    yield Biomes.WINDSWEPT_SAVANNA;
                }
            }
            default -> switch (temp) {
                case 0 -> MiddleBiomes.getBiome(temp, humidity, weirdness);
                default -> SwampBiomes.getBiome(temp, humidity, weirdness);
            };
        };

    }

    public static @NotNull ResourceKey<Biome> getMid(int temp, int humidity, int weirdness, int pv, int erosion) {
        return switch (erosion) {
            case 0 ->
                temp < 3 ? (humidity < 2 ? Biomes.SNOWY_SLOPES : Biomes.GROVE) : PlateauBiomes.getBiome(temp, humidity, weirdness);
            case 1 -> {
                if (temp == 0) {
                    yield humidity <= 1 ? Biomes.SNOWY_SLOPES : Biomes.GROVE;
                } else if (temp < 4) {
                    yield MiddleBiomes.getBiome(temp, humidity, weirdness);
                } else {
                    yield BadlandBiomes.getBiome(temp, humidity, weirdness);
                }
            }
            case 2, 3, 4 -> MiddleBiomes.getBiome(temp, humidity, weirdness);
            case 5 -> {
                if (weirdness == 0 || temp == 0 || temp == 1 || humidity == 4) {
                    yield MiddleBiomes.getBiome(temp, humidity, weirdness);
                } else {
                    yield Biomes.WINDSWEPT_SAVANNA;
                }
            }
            default -> switch (temp) {
                case 0 -> MiddleBiomes.getBiome(temp, humidity, weirdness);
                default -> SwampBiomes.getBiome(temp, humidity, weirdness);
            };
        };
    }

    public static @NotNull ResourceKey<Biome> getHigh(int temp, int humidity, int weirdness, int pv, int erosion) {
        return switch (erosion) {
            case 0 -> {
                if (temp < 3) {
                    yield humidity <= 1 ? Biomes.SNOWY_SLOPES : Biomes.GROVE;
                } else {
                    yield PlateauBiomes.getBiome(temp, humidity, weirdness);
                }
            }
            case 1 -> {
                if (temp == 0) {
                    yield humidity <= 1 ? Biomes.SNOWY_SLOPES : Biomes.GROVE;
                } else if (temp < 4) {
                    yield MiddleBiomes.getBiome(temp, humidity, weirdness);
                } else {
                    yield BadlandBiomes.getBiome(temp, humidity, weirdness);
                }
            }
            case 2, 3, 4 -> MiddleBiomes.getBiome(temp, humidity, weirdness);
            case 5 -> {
                if (weirdness == 0 || temp == 0 || temp == 1 || humidity == 4) {
                    yield MiddleBiomes.getBiome(temp, humidity, weirdness);
                } else {
                    yield Biomes.WINDSWEPT_SAVANNA;
                }
            }
            default -> MiddleBiomes.getBiome(temp, humidity, weirdness);
        };
    }

    public static @NotNull ResourceKey<Biome> getPeaks(int temp, int humidity, int weirdness, int pv, int erosion) {
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
