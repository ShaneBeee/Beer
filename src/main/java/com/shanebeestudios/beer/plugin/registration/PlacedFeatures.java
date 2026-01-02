package com.shanebeestudios.beer.plugin.registration;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class PlacedFeatures {

    // DECOR
    public static final ResourceKey<PlacedFeature> DECOR_HANGING_FENCE = register("decor/hanging_fence");
    
    // DELTAS
    public static final ResourceKey<PlacedFeature> DELTA_BEACH_DELTA = register("delta/beach_delta");
    public static final ResourceKey<PlacedFeature> DELTA_COASTAL_DELTA = register("delta/coastal_delta");
    public static final ResourceKey<PlacedFeature> DELTA_DRIPLEAF_SWAMP_DELTA = register("delta/dripleaf_swamp_delta");
    public static final ResourceKey<PlacedFeature> DELTA_DRY_CAVE_DELTA = register("delta/dry_cave_delta");
    public static final ResourceKey<PlacedFeature> DELTA_SWAMP_DELTA = register("delta/swamp_delta_2");
    
    // TERRAIN
    public static final ResourceKey<PlacedFeature> TERRAIN_BROWN_CONCRETE_DISK = register("terrain/brown_concrete_disk");
    public static final ResourceKey<PlacedFeature> TERRAIN_DIORITE_CLIFFS = register("terrain/diorite_cliffs");
    public static final ResourceKey<PlacedFeature> TERRAIN_GRASS_TO_SAND = register("terrain/grass_to_sand");
    public static final ResourceKey<PlacedFeature> TERRAIN_SAND_SHORE_DISK = register("terrain/sand_shore_disk");
    public static final ResourceKey<PlacedFeature> TERRAIN_STONE_CLIFF = register("terrain/stone_cliff");
    public static final ResourceKey<PlacedFeature> TERRAIN_STONE_TO_ICE = register("terrain/stone_to_ice");
    public static final ResourceKey<PlacedFeature> TERRAIN_WATER_BLOB = register("terrain/water_blob");
    
    // TREE
    public static final ResourceKey<PlacedFeature> TREE_BEACHY_PALM = register("tree/beachy_palm");
    public static final ResourceKey<PlacedFeature> TREE_FALLEN_STRIPPED_PALE_OAK = register("tree/fallen_stripped_pale_oak");
    public static final ResourceKey<PlacedFeature> TREE_FALLEN_WARPED_STEM = register("tree/fallen_warped_stem");
    public static final ResourceKey<PlacedFeature> TREE_MOSS_GARDEN = register("tree/moss_garden");
    public static final ResourceKey<PlacedFeature> TREE_PALM_BEACH_PALM = register("tree/palm_beach_palm");
    public static final ResourceKey<PlacedFeature> TREE_TALL_STRIPPED_PALE_OAK = register("tree/tall_stripped_pale_oak");

    // VEGETATION
    public static final ResourceKey<PlacedFeature> VEGETATION_AZALEA_BUSH = register("vegetation/azalea_bush");
    public static final ResourceKey<PlacedFeature> VEGETATION_MOSS_PATCH = register("vegetation/moss_patch");
    public static final ResourceKey<PlacedFeature> VEGETATION_PATCH_CHERRY_PETALS = register("vegetation/patch_cherry_petals");
    public static final ResourceKey<PlacedFeature> VEGETATION_PATCH_CLIFF_GRASS = register("vegetation/patch_cliff_grass");
    public static final ResourceKey<PlacedFeature> VEGETATION_PATCH_HAY_BALE = register("vegetation/patch_hay_bale");
    public static final ResourceKey<PlacedFeature> VEGETATION_PATCH_SMALL_DRIPLEAF = register("vegetation/patch_small_dripleaf");
    public static final ResourceKey<PlacedFeature> VEGETATION_PATCH_WATER_LEAVES = register("vegetation/patch_water_leaves");
    public static final ResourceKey<PlacedFeature> VEGETATION_ROOT_DIRT_BLOB = register("vegetation/rooted_dirt_blob");

    private static ResourceKey<PlacedFeature> register(String key) {
        return ResourceKey.create(Registries.PLACED_FEATURE, Identifier.parse("beer:" + key));
    }

}
