package com.shanebeestudios.biome;

import org.zeroturnaround.zip.ZipUtil;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        BiomeChanger.generateBiomes();

        File in = new File("Beer");
        File out = new File("../../desktop/Server/Skript/1-21-1/worlds/world/datapacks/Beer.zip");
        ZipUtil.pack(in, out, name -> name.toLowerCase().contains("ds_store") ? null : name);
    }

}
