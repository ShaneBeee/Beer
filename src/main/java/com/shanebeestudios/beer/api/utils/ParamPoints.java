package com.shanebeestudios.beer.api.utils;

import org.bukkit.generator.BiomeParameterPoint;

public enum ParamPoints {
    CONTINENTALNESS() {
        @Override
        public double getPoint(BiomeParameterPoint point) {
            return point.getContinentalness();
        }

        @Override
        public int getFixedPoint(BiomeParameterPoint point) {
            double continentalness = point.getContinentalness();
            if (continentalness <= -1.05) return 0; // Mushroom Fields
            else if (continentalness <= -0.455) return 1; // Deep ocean
            else if (continentalness <= -0.19) return 2; // Ocean
            else if (continentalness <= -0.11) return 3; // Coast
            else if (continentalness <= 0.03) return 4; // Near-Inland
            else if (continentalness <= 0.3) return 5; // Mid-Inland
            else if (continentalness <= 1.0) return 6; // Far-Inland
            else return 7; // This shouldn't happen, but safety measure
        }
    },
    DEPTH() {
        @Override
        public double getPoint(BiomeParameterPoint point) {
            return point.getDepth();
        }

        @Override
        public int getFixedPoint(BiomeParameterPoint point) {
            // Depth doesn't have a fixed point
            // So let's just do some math
            return (int) (point.getDepth() * 128);
        }
    },
    EROSION() {
        @Override
        public double getPoint(BiomeParameterPoint point) {
            return point.getErosion();
        }

        @Override
        public int getFixedPoint(BiomeParameterPoint point) {
            double erosion = point.getErosion();
            if (erosion <= -0.78) return 0;
            else if (erosion <= -0.375) return 1;
            else if (erosion <= -0.2225) return 2;
            else if (erosion <= 0.05) return 3;
            else if (erosion <= 0.45) return 4;
            else if (erosion <= 0.55) return 5;
            else if (erosion <= 1.0) return 6;
            else return 7; // This shouldn't happen, but safety measure
        }
    },
    HUMIDITY() {
        @Override
        public double getPoint(BiomeParameterPoint point) {
            return point.getHumidity();
        }

        @Override
        public int getFixedPoint(BiomeParameterPoint point) {
            double humidity = point.getHumidity();
            if (humidity <= -0.35) return 0;
            else if (humidity <= -0.1) return 1;
            else if (humidity <= 0.1) return 2;
            else if (humidity <= 0.3) return 3;
            else if (humidity <= 1.0) return 4;
            else return 5; // This shouldn't happen, but safety measure
        }
    },
    PEAKS_AND_VALLEYS() {
        @Override
        public double getPoint(BiomeParameterPoint point) {
            return -(Math.abs(Math.abs(point.getWeirdness()) - 0.6666667F) - 0.33333334F) * 3.0F;
        }

        @Override
        public int getFixedPoint(BiomeParameterPoint point) {
            double peaksAndValleys = getPoint(point);
            if (peaksAndValleys <= -0.85) return 0; // Valleys
            else if (peaksAndValleys <= -0.6) return 1; // Low
            else if (peaksAndValleys <= 0.2) return 2; // Mid
            else if (peaksAndValleys <= 0.7) return 3; // High
            else if (peaksAndValleys <= 1.0) return 4; // Peaks
            else return 5; // This shouldn't happen, but safety measure
        }
    },
    TEMPERATURE() {
        @Override
        public double getPoint(BiomeParameterPoint point) {
            return point.getTemperature();
        }

        @Override
        public int getFixedPoint(BiomeParameterPoint point) {
            double temperature = point.getTemperature();
            if (temperature <= -0.45) return 0;
            else if (temperature <= -0.15) return 1;
            else if (temperature <= 0.2) return 2;
            else if (temperature <= 0.55) return 3;
            else if (temperature <= 1.0) return 4;
            else return 5; // This shouldn't happen, but safety measure
        }
    },
    WEIRDNESS() {
        @Override
        public double getPoint(BiomeParameterPoint point) {
            return point.getWeirdness();
        }

        @Override
        public int getFixedPoint(BiomeParameterPoint point) {
            // I couldn't find great data on this one
            // This is all I could find
            return point.getWeirdness() <= 0 ? 0 : 1;
        }
    };

    ParamPoints() {
    }

    public abstract double getPoint(BiomeParameterPoint point);

    public abstract int getFixedPoint(BiomeParameterPoint point);

}
