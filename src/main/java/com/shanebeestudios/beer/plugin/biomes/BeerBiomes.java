package com.shanebeestudios.beer.plugin.biomes;

import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.block.Biome;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class BeerBiomes {

    public static final Registry<Biome> BIOME_REGISTRY = RegistryAccess.registryAccess().getRegistry(RegistryKey.BIOME);
    public static final List<Biome> BEER_BIOMES = new ArrayList<>();

    // CAVE
    public static Biome CAVE_DRY_CAVE = getBiome("cave/dry_cave");
    public static Biome CAVE_ICE_CAVE = getBiome("cave/ice_cave");

    // COAST
    public static Biome COAST_BEACHY_BEACH = getBiome("coast/beachy_beach");
    public static Biome COAST_COAST = getBiome("coast/coast");
    public static Biome COAST_DRY_COAST = getBiome("coast/dry_coast");
    public static Biome COAST_FROZEN_BEACH = getBiome("coast/frozen_beach");
    public static Biome COAST_LUSH_COAST = getBiome("coast/lush_coast");
    public static Biome COAST_PALM_BEACH = getBiome("coast/palm_beach");

    // DESERT
    public static Biome DESERT_PLAIN_DESERT = getBiome("desert/plain_desert");

    // FOREST
    public static Biome FOREST_MOSS_GARDEN = getBiome("forest/moss_garden");

    // PLAINS
    public static Biome PLAINS_DRY_PLAINS = getBiome("plains/dry_plains");
    public static Biome PLAINS_LUSH_PLAINS = getBiome("plains/lush_plains");
    public static Biome PLAINS_PLAINS = getBiome("plains/plains");

    // RIVER
    public static Biome RIVER_DESERT_RIVER = getBiome("river/desert_river");
    public static Biome RIVER_LUSH_RIVER = getBiome("river/lush_river");
    public static Biome RIVER_TEMPERATE_RIVER = getBiome("river/temperate_river");

    // SWAMP
    public static Biome SWAMP_DRIPLEAF_SWAMP = getBiome("swamp/dripleaf_swamp");

    @NotNull
    private static Biome getBiome(String id) {
        NamespacedKey key = NamespacedKey.fromString("beer:" + id);
        assert key != null;
        Biome biome = BIOME_REGISTRY.get(key);
        if (biome == null) {
            throw new IllegalArgumentException("Missing biome: " + key);
        }
        BEER_BIOMES.add(biome);
        return biome;
    }

}
