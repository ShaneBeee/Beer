package com.shanebeestudios.biome;

import com.google.gson.FormattingStyle;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class BiomeChanger {

    public enum Effects {
        FOG(12638463),
        WATER(4159204),
        WATER_FOG(329011);

        private final int color;

        Effects(int color) {
            this.color = color;
        }
    }

    public enum Season {
        SPRING("spring",
            -0.2f,
            0.3,
            null,
            new Color(5336976),
            new Color(2171215),
            new Color(16751037),
            new Color(0x77CB4E)),
        SUMMER("summer",
            0.5f,
            -0.5),
        FALL("fall",
            0.2f,
            -0.2,
            null,
            new Color(6388580),
            new Color(2302743),
            new Color(14316854),
            new Color(-7168948)),
        WINTER("winter",
            -2f,
            0);

        private final boolean hasEffects;
        private final String name;
        private final float temp;
        private final double downfall;

        private Color fog;
        private Color water;
        private Color waterFog;
        private Color foliage;
        private Color grass;

        Season(String name, float temp, double downfall) {
            this.hasEffects = false;
            this.name = name;
            this.temp = temp;
            this.downfall = downfall;
        }

        Season(String name, float temp, double downfall, Color fog, Color water, Color waterFog, Color foliage, Color grass) {
            this.hasEffects = true;
            this.name = name;
            this.temp = temp;
            this.downfall = downfall;
            this.fog = fog;
            this.water = water;
            this.waterFog = waterFog;
            this.foliage = foliage;
            this.grass = grass;
        }

        public int getFog() {
            return fog != null ? fog.getRGB() : 0;
        }

        public int getWater() {
            return water != null ? water.getRGB() : 0;
        }

        public int getWaterFog() {
            return waterFog != null ? waterFog.getRGB() : 0;
        }

        public int getFoliage() {
            return foliage != null ? foliage.getRGB() : 0;
        }

        public int getGrass() {
            return grass != null ? grass.getRGB() : 0;
        }
    }

    public static void generateBiomes() {
        System.out.println("Adjusting biomes");
        adjustBiomes("minecraft");
        adjustBiomes("wythers");
    }

    @SuppressWarnings("DataFlowIssue")
    public static void adjustBiomes(String namespace) {
        String biomePath = "biomes/" + namespace + "/worldgen/biome/";
        File biomesDir = new File(biomePath);
        if (!biomesDir.exists() || !biomesDir.isDirectory()) return;

        // Loop each biome and adjust
        for (File file : biomesDir.listFiles()) {
            if (!file.getName().contains(".json")) continue;
            // Adjust for each season
            for (Season season : Season.values()) {
                adjustBiomeForSeason(season, file);
            }
        }
    }

    public static void adjustBiomeForSeason(Season season, File file) {
        try {
            // Load json
            JsonReader reader = new JsonReader(new FileReader(file));
            Gson gson = new Gson();
            JsonObject json = gson.fromJson(reader, JsonObject.class);

            // Adjust biome
            double temperature = json.get("temperature").getAsDouble();
            double newTemp = temperature + season.temp;
            json.addProperty("temperature", Math.clamp(newTemp, 0.0, 2.0));
            if (newTemp < 1.5f) {
                json.addProperty("has_precipitation", true);
            }
            float downfall = json.get("downfall").getAsFloat();
            json.addProperty("downfall", Math.clamp(downfall + season.downfall, 0.0, 1.0));

            // Adjust biome effects
            if (season.hasEffects) {
                JsonObject effects = json.getAsJsonObject("effects");
                if (effects == null) effects = new JsonObject();
                // Required Values
                int fog = season.getFog();
                if (fog == 0) // If it's unset, either get from file or get default
                    fog = effects.has("fog_color") ? effects.get("fog_color").getAsInt() : Effects.FOG.color;
                effects.addProperty("fog_color", fog);

                int water = season.getWater();
                if (water == 0)
                    water = effects.has("water_color") ? effects.get("water_color").getAsInt() : Effects.WATER.color;
                effects.addProperty("water_color", water);

                int waterFog = season.getWaterFog();
                if (waterFog == 0)
                    waterFog = effects.has("water_fog_color") ? effects.get("water_fog_color").getAsInt() : Effects.WATER_FOG.color;
                effects.addProperty("water_fog_color", waterFog);


                // Optional values
                if (season.getFoliage() != 0) effects.addProperty("foliage_color", season.getFoliage());
                if (season.getGrass() != 0) effects.addProperty("grass_color", season.getGrass());
                json.add("effects", effects);
            }

            // Write new seasonal json to file
            String newPath = file.getPath().replace(file.getName(), season.name + "/");
            newPath = newPath.replace("biomes/", "Beer/data/");
            File newFile = new File(newPath + "/");
            newFile.mkdirs();
            newFile = new File(newPath + "/" + file.getName());
            JsonWriter jsonWriter = new JsonWriter(new FileWriter(newFile));
            jsonWriter.setFormattingStyle(FormattingStyle.PRETTY);
            gson.toJson(json, jsonWriter);
            jsonWriter.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
