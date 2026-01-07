package com.shanebeestudios.beer.api.utils;

public enum ParamPoints {
    CONTINENTALNESS() {
        @Override
        public int getFixedPoint(double continentalness) {
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
        public int getFixedPoint(double point) {
            // Depth doesn't have a fixed point
            // So let's just do some math
            return (int) (point * 128);
        }
    },
    EROSION() {
        @Override
        public int getFixedPoint(double erosion) {
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
        public int getFixedPoint(double humidity) {
            if (humidity <= -0.35) return 0;
            else if (humidity <= -0.1) return 1;
            else if (humidity <= 0.1) return 2;
            else if (humidity <= 0.3) return 3;
            else if (humidity <= 1.0) return 4;
            else return 5; // This shouldn't happen, but safety measure
        }
    },
    PEAKS_AND_VALLEYS() {
        private double getPoint(double weirdness) {
            return -(Math.abs(Math.abs(weirdness) - 0.6666667F) - 0.33333334F) * 3.0F;
        }

        @Override
        public int getFixedPoint(double weirdness) {
            double peaksAndValleys = getPoint(weirdness);
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
        public int getFixedPoint(double point) {
            if (point <= -0.45) return 0;
            else if (point <= -0.15) return 1;
            else if (point <= 0.2) return 2;
            else if (point <= 0.55) return 3;
            else if (point <= 1.0) return 4;
            else return 5; // This shouldn't happen, but safety measure
        }
    },
    WEIRDNESS() {
        @Override
        public int getFixedPoint(double point) {
            // I couldn't find great data on this one
            // This is all I could find
            return point <= 0 ? 0 : 1;
        }
    };

    ParamPoints() {
    }

    public abstract int getFixedPoint(double point);

}
