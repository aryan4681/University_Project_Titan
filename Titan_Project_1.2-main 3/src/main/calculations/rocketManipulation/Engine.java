package src.main.calculations.rocketManipulation;

import src.main.calculations.Data;
import src.main.calculations.landingOpenController.CelestialObjectOpenController;
import src.main.calculations.SolarSystem;

/**
 * This class represents the engine of the rocket.
 * It is responsible for firing the engine and calculating the consumed fuel.
 */
public class Engine {

    private double consumedFuel = 0;

    /**
     * This method computes the impuls using the force and the time step, after which it adjusts the velocity accordingly.
     * Its secondary function is to calculate the amount of fuel that is consumed in total.
     *
     * @param force  the force that is applied to the rocket
     * @param system the solar system
     */
    public void fireEngineWithImpuls(double[] force, SolarSystem system) {

        CelestialObjectOpenController rocket = system.getObjects()[11];

        double[] impuls = new double[]{
                force[0] * Data.TIME_STEP * (1.0 / 1000),
                force[1] * Data.TIME_STEP * (1.0 / 1000),
                force[2] * Data.TIME_STEP * (1.0 / 1000),
        };

        double deltaConsumedFuel = convertVectorToConsumedFuelAmount(impuls);
        consumedFuel += deltaConsumedFuel;

        double[] velocityDelta = new double[]{
                impuls[0] * (1.0 / rocket.getMass()),
                impuls[1] * (1.0 / rocket.getMass()),
                impuls[2] * (1.0 / rocket.getMass()),
        };

        rocket.addToVelocity(0, velocityDelta[0]);
        rocket.addToVelocity(1, velocityDelta[1]);
        rocket.addToVelocity(2, velocityDelta[2]);

    }

    /**
     * This method converts the impuls to the amount of fuel that is consumed.
     *
     * @param impuls the impuls that is applied to the rocket
     * @return the amount of fuel that is consumed
     */
    public double convertVectorToConsumedFuelAmount(double[] impuls) {

        double vectorLength = Math.sqrt(
                Math.pow(impuls[0], 2) +
                        Math.pow(impuls[1], 2) +
                        Math.pow(impuls[2], 2)
        );

        return vectorLength * Data.MASS[11] * (1 / Data.TIME_STEP);
    }

    /**
     * This method returns the amount of fuel that is consumed.
     *
     * @return the amount of fuel that is consumed
     */
    public double getConsumedFuel() {
        return consumedFuel;
    }

}
