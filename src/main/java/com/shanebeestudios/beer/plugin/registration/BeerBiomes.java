package com.shanebeestudios.beer.plugin.registration;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;

public class BeerBiomes {

    // CAVE
    public static final ResourceKey<Biome> CAVE_DRY_CAVE = register("cave/dry_cave");
    public static final ResourceKey<Biome> CAVE_ICE_CAVE = register("cave/ice_cave");

    // COAST
    public static final ResourceKey<Biome> COAST_BEACHY_COAST = register("coast/beachy_beach");
    public static final ResourceKey<Biome> COAST_COAST = register("coast/coast");
    public static final ResourceKey<Biome> COAST_DRY_COAST = register("coast/dry_coast");
    public static final ResourceKey<Biome> COAST_FROZEN_BEACH = register("coast/frozen_beach");
    public static final ResourceKey<Biome> COAST_LUSH_COAST = register("coast/lush_coast");
    public static final ResourceKey<Biome> COAST_PALM_BEACH = register("coast/palm_beach");

    // DESERT
    public static final ResourceKey<Biome> DESERT_DRY_DESERT = register("desert/dry_desert");
    public static final ResourceKey<Biome> DESERT_LUSH_DESERT = register("desert/lush_desert");

    // FOREST
    public static final ResourceKey<Biome> FOREST_MOSS_GARDEN = register("forest/moss_garden");
    public static final ResourceKey<Biome> FOREST_TALL_OAK = register("forest/tall_oak");
    public static final ResourceKey<Biome> FOREST_LUSH_FOREST = register("forest/lush_forest");

    // PLAINS
    public static final ResourceKey<Biome> PLAINS_DRY_PLAINS = register("plains/dry_plains");
    public static final ResourceKey<Biome> PLAINS_LUSH_PLAINS = register("plains/lush_plains");
    public static final ResourceKey<Biome> PLAINS_TEMPERATE_PLAINS = register("plains/temperate_plains");

    // RIVERS
    public static final ResourceKey<Biome> RIVER_DESERT_RIVER = register("river/desert_river");
    public static final ResourceKey<Biome> RIVER_LUSH_RIVER = register("river/lush_river");
    public static final ResourceKey<Biome> RIVER_TEMPERATE_RIVER = register("river/temperate_river");

    // SWAMPS
    public static final ResourceKey<Biome> SWAMP_COLD_SWAMP = register("swamp/cold_swamp");
    public static final ResourceKey<Biome> SWAMP_DRIPLEAF_SWAMP = register("swamp/dripleaf_swamp");

    private static ResourceKey<Biome> register(String key) {
        return ResourceKey.create(Registries.BIOME, Identifier.parse("beer:" + key));
    }

}
