package com.shanebeestudios.beer.plugin.registration;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class ConfiguredFeatures {

    // TERRAIN
    public static final ResourceKey<ConfiguredFeature<?,?>> TERRAIN_SAND_SHORE_DISK = register("terrain/sand_shore_disk");

    // TREE
    public static final ResourceKey<ConfiguredFeature<?,?>> TREE_FALLEN_STRIPPED_PALE_OAK = register("tree/fallen_stripped_pale_oak");
    public static final ResourceKey<ConfiguredFeature<?,?>> TREE_FALLEN_STRIPPED_WARPED_STEM = register("tree/fallen_stripped_warped_stem");
    public static final ResourceKey<ConfiguredFeature<?,?>> TREE_FALLEN_WARPED_STEM = register("tree/fallen_warped_stem");
    public static final ResourceKey<ConfiguredFeature<?,?>> TREE_PALM_TREE = register("tree/palm_tree");

    // VEGETATION
    public static final ResourceKey<ConfiguredFeature<?,?>> VEGETATION_AZALEA_BUSH = register("vegetation/azalea_bush");
    public static final ResourceKey<ConfiguredFeature<?,?>> VEGETATION_AZALEA_SCRUB = register("vegetation/azalea_scrub");
    public static final ResourceKey<ConfiguredFeature<?,?>> VEGETATION_FLOWERING_AZALEA_SCRUB = register("vegetation/flowering_azalea_scrub");
    public static final ResourceKey<ConfiguredFeature<?,?>> VEGETATION_PATCH_CLIFF_GRASS = register("vegetation/patch_cliff_grass");
    
    private static ResourceKey<ConfiguredFeature<?,?>> register(String key) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, Identifier.parse("beer:" + key));
    }

}
