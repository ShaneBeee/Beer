package com.shanebeestudios.beer;

import com.shanebeestudios.beer.api.BiomeGenerator;
import com.shanebeestudios.coreapi.util.Utils;
import net.kyori.adventure.util.TriState;
import org.bukkit.WorldCreator;
import org.bukkit.plugin.java.JavaPlugin;

public class BeerPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        Utils.setPrefix("&7[&bBeer&7] ");
        Utils.log("&bLoading custom world...");
        if (loadCustomWorld()) {
            Utils.log("&aCustom world loaded!");
        } else {
            Utils.log("&cFailed to load custom world!");
        }
    }

    @Override
    public void onDisable() {
    }

    private boolean loadCustomWorld() {
        WorldCreator worldCreator = new WorldCreator("world_beer");
        worldCreator.biomeProvider(new BiomeGenerator());
        //worldCreator.generator(new DummyChunkGenerator());
        worldCreator.keepSpawnLoaded(TriState.FALSE);

        return worldCreator.createWorld() != null;
    }

}
