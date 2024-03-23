package src.main.calculations.solvers;

import src.main.calculations.landingOpenController.CelestialObjectOpenController;
import src.main.calculations.Data;
import src.main.calculations.SolarSystem;

/**
 * This class is responsible for updating positions of all objects using the
 * Ralstons method.
 */
public class RalstonsSolver {

    /**
     * This method using Ralstons solver updates our position every step size.
     */
    public static void updatePositions(SolarSystem system) {

        CelestialObjectOpenController[] objects = system.getObjects();

        double[][] force = system.calculateForces();

        double[][] k1 = new double[objects.length][3];
        double[][] k2 = new double[objects.length][3];

        double[][] vk1 = new double[objects.length][3];
        double[][] vk2 = new double[objects.length][3];

        double[][] k = new double[objects.length][3];
        double[][] vk = new double[objects.length][3];

        for (int i = 1; i < objects.length; i++) {
            for (int j = 0; j < 3; j++) {
                k1[i][j] = Data.TIME_STEP * objects[i].getPosition()[j];
                vk1[i][j] = Data.TIME_STEP * (force[i][j] / objects[i].getMass());

                k2[i][j] = Data.TIME_STEP * (k1[i][j] * (2.0 / 3.0));
                vk2[i][j] = Data.TIME_STEP * (vk1[i][j] * (2.0 / 3.0));

                k[i][j] = k1[i][j] * (0.25) + k2[i][j] * (0.75);
                vk[i][j] = vk1[i][j] * (0.25) + vk2[i][j] * (0.75);

                objects[i].addToPosition(j, k[i][j]);
                objects[i].addToVelocity(j, vk[i][j]);
            }
        }

        // Updating time engine
        system.getTimeEngine().seconds((int) Data.TIME_STEP);
    }

    /**
     * This method iterates through Alston's iterative approximation method and returns the approximation
     * for y(1) for ODE y' = (t + y)^2 - 1, where the actual solution is calculated by y(t) = 2/(3 − 2t) − t
     *
     * @param stepSize with which we iterate
     * @return approximated solution
     */
    public static double experimentResult(double stepSize) {

        double yOld = 2.0 / 3.0;
        double yCurrent = 2.0 / 3.0;
        double tOld;

        double k1;
        double k2;

        double numberOfIterations = 1 / stepSize;


        for (int i = 0; i < numberOfIterations; i++) {
            tOld = stepSize * i;

            k1 = stepSize * ((Math.pow(tOld + yOld, 2.0)) - 1.0);
            k2 = stepSize * ((Math.pow(tOld + (2.0 / 3.0) * stepSize + yOld + (2.0 / 3.0) * k1, 2.0)) - 1.0);

            yCurrent = yOld + (1.0 / 4.0) * (k1 + 3.0 * k2);

            yOld = yCurrent;

        }

        return yCurrent;
    }
}
