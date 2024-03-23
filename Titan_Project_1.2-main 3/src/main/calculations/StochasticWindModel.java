package src.main.calculations;

import java.util.Random;

public class StochasticWindModel {
    private final Random random;
    private final double standardDeviationWind; // in km/s
    private final double meanWind; // in km/s
    private double windSpeed; // in km/s

    public StochasticWindModel(double standardDeviationWind, double meanWind) {
        this.random = new Random();
        this.standardDeviationWind = standardDeviationWind;
        this.meanWind = meanWind;
    }

    /**
     * Calculates the wind speed based off of the altitude of the spaceship
     *
     * @param currentAltitude Current altitude of the spaceship
     * @param pWindSpeed      Wind speed at the highest altitude
     * @return returns the wind speed based off of the altitude of the spaceship
     */
    public double windSpeedWithImpactOfAltitude(double currentAltitude, double pWindSpeed) {
        // Assuming the decrease in altitude is linear to increase of wind speed
        double change = 0;
        // in km
        double highestAltitude = 50;
        if (currentAltitude < highestAltitude) {
            change = calculateImpactOfAltitude(highestAltitude, currentAltitude);
        }
        return pWindSpeed * change;
    }

    /**
     * Calculates the wind speed in the beginning, thus at the start of the
     * atmosphere
     */
    public void setWindSpeed() {
        // Assuming that the wind speed follows a normal/Gaussian distribution with a
        // mean value and a standard deviation
        windSpeed = (random.nextGaussian() * standardDeviationWind + meanWind);
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    /**
     * We assume that when the altitude decreases that the wind speed increases, thus that it is linear from each other
     *
     * @param highestAltitude The altitude at which the atmosphere starts
     * @param currentAltitude The current altitude of the spaceship
     * @return How much the wind speed is changed depending on the altitude
     */
    private double calculateImpactOfAltitude(double highestAltitude, double currentAltitude) {
        double percentage = currentAltitude / highestAltitude;
        double decrease = 1 - percentage;
        return 1 + decrease;
    }

}