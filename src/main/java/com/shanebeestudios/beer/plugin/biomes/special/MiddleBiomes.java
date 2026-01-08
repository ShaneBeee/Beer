package com.shanebeestudios.beer.plugin.biomes.special;

import com.shanebeestudios.beer.plugin.registration.BeerBiomes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

public class MiddleBiomes {

    public static ResourceKey<Biome> getBiome(int temp, int humidity, int weirdness) {
        return switch (temp) {
            case 0 -> getFrozen(humidity, weirdness);
            case 1 -> getCold(humidity, weirdness);
            case 2 -> getTemperate(humidity, weirdness);
            case 3 -> getWarm(humidity, weirdness);
            default -> getHot(humidity, weirdness);
        };
    }

    private static ResourceKey<Biome> getFrozen(int humidity, int weirdness) {
        return switch (humidity) {
            case 0 -> weirdness == 1 ? Biomes.ICE_SPIKES : Biomes.SNOWY_PLAINS;
            case 1 -> Biomes.SNOWY_PLAINS;
            case 2 -> weirdness == 1 ? Biomes.SNOWY_TAIGA : Biomes.SNOWY_PLAINS;
            case 3 -> Biomes.SNOWY_TAIGA;
            default -> Biomes.TAIGA;
        };
    }

    private static ResourceKey<Biome> getCold(int humidity, int weirdness) {
        return switch (humidity) {
            case 0, 1 -> BeerBiomes.PLAINS_COLD_PLAINS;
            case 2 -> BeerBiomes.FOREST_TALL_OAK;
            case 3 -> Biomes.TAIGA;
            default -> weirdness == 1 ? Biomes.OLD_GROWTH_PINE_TAIGA : Biomes.OLD_GROWTH_SPRUCE_TAIGA;
        };
    }

    private static ResourceKey<Biome> getTemperate(int humidity, int weirdness) {
        return switch (humidity) {
            case 0 -> weirdness == 1 ? Biomes.SUNFLOWER_PLAINS : Biomes.FLOWER_FOREST;
            case 1 -> BeerBiomes.PLAINS_TEMPERATE_PLAINS;
            case 2 -> Biomes.FOREST;
            case 3 -> weirdness == 1 ? Biomes.OLD_GROWTH_BIRCH_FOREST : Biomes.BIRCH_FOREST;
            default -> weirdness == 1 ? BeerBiomes.PLAINS_LUSH_PLAINS : Biomes.DARK_FOREST;
        };
    }

    private static ResourceKey<Biome> getWarm(int humidity, int weirdness) {
        return switch (humidity) {
            case 0 -> Biomes.SAVANNA;
            case 1 -> BeerBiomes.PLAINS_DRY_PLAINS;
            case 2 -> weirdness == 1 ? BeerBiomes.PLAINS_TEMPERATE_PLAINS : BeerBiomes.FOREST_LUSH_FOREST;
            case 3 -> weirdness == 1 ? Biomes.SPARSE_JUNGLE : Biomes.JUNGLE;
            default -> weirdness == 1 ? Biomes.BAMBOO_JUNGLE : Biomes.JUNGLE;
        };
    }

    private static ResourceKey<Biome> getHot(int humidity, int weirdness) {
        return humidity <= 2 ? BeerBiomes.DESERT_DRY_DESERT : BeerBiomes.DESERT_LUSH_DESERT;
    }

}
