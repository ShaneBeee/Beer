package com.shanebeestudios.beer.plugin;

import com.shanebeestudios.beer.api.registration.feature.ConfiguredFeatureDefinition;
import com.shanebeestudios.beer.api.registration.feature.ConfiguredFeatureRegistration;
import com.shanebeestudios.beer.plugin.biomes.BiomeGenerator;
import com.shanebeestudios.beer.api.registration.biome.BiomeRegistration;
import com.shanebeestudios.beer.api.registration.feature.PlacedFeatureRegistration;
import com.shanebeestudios.coreapi.util.Utils;
import io.papermc.paper.datapack.Datapack;
import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.plugin.java.JavaPlugin;

public class BeerPlugin extends JavaPlugin {

    private static BeerPlugin INSTANCE;

    @Override
    public void onEnable() {
        INSTANCE = this;

        Utils.setPrefix("&7[&bBeer&7] ");

        Datapack zBeer = Bukkit.getDatapackManager().getPack("file/Z-Beer.zip");
        if (zBeer != null && zBeer.isEnabled()) {
            Utils.log("&bLoading custom world...");
            if (loadCustomWorld()) {
                Utils.log("&aCustom world loaded!");
            } else {
                Utils.log("&cFailed to load custom world!");
            }
        } else {
            Utils.log("Attempting to build biomes and dump");
            ConfiguredFeatureRegistration.registerFeatures();
            PlacedFeatureRegistration.registerFeatures();
            BiomeRegistration.generateBiomes();
        }
    }

    private boolean loadCustomWorld() {
        WorldCreator worldCreator = new WorldCreator("world_beer");
        worldCreator.biomeProvider(new BiomeGenerator());

        return worldCreator.createWorld() != null;
    }

    public static BeerPlugin getPluginInstance() {
        return INSTANCE;
    }

}
