package com.shanebeestudios.beer.plugin;

import com.shanebeestudios.beer.plugin.registration.BiomeRegistration;
import com.shanebeestudios.beer.plugin.registration.ConfiguredFeatureRegistration;
import com.shanebeestudios.beer.plugin.registration.DimensionRegistration;
import com.shanebeestudios.beer.plugin.registration.PlacedFeatureRegistration;
import com.shanebeestudios.coreapi.util.Utils;
import io.papermc.paper.datapack.Datapack;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class BeerPlugin extends JavaPlugin {

    private static BeerPlugin INSTANCE;

    @Override
    public void onEnable() {
        INSTANCE = this;

        Utils.setPrefix("&7[&bBeer&7] ");

        Datapack zBeer = Bukkit.getDatapackManager().getPack("file/Z-Beer.zip");
        if (zBeer == null || !zBeer.isEnabled()) {
            Utils.log("Attempting to register definables and dump for datapacks");
            // Register definable objects
            ConfiguredFeatureRegistration.registerFeatures();
            PlacedFeatureRegistration.registerFeatures();
            BiomeRegistration.registerBiomes();
            DimensionRegistration dimensionRegistration = new DimensionRegistration();

            // Dump objects to datapack
            ConfiguredFeatureRegistration.dumpToDatapack();
            PlacedFeatureRegistration.dumpToDatapack();
            BiomeRegistration.dumpToRegistry();
            dimensionRegistration.dumpToRegistry();
        }
    }

    public static BeerPlugin getPluginInstance() {
        return INSTANCE;
    }

}
