package src.main.calculations.solvers;

import src.main.calculations.landingOpenController.CelestialObjectOpenController;
import src.main.calculations.Data;
import src.main.calculations.SolarSystem;

/**
 * This class is responsible for updating positions of all objects using Euler's
 * method.
 */
public class EulerSolver {

    /**
     * This method using Euler's solver updates our position every step size.
     */
    public static void updatePositions(SolarSystem system) {

        CelestialObjectOpenController[] objects = system.getObjects();

        double[][] force = system.calculateForces();

        for (int i = 1; i < objects.length; i++) {
            for (int j = 0; j < 3; j++) {
                objects[i].addToPosition(j, (Data.TIME_STEP * objects[i].getVelocity()[j]));
                objects[i].addToVelocity(j, (Data.TIME_STEP * (force[i][j] / objects[i].getMass())));
            }
        }

        // Updating time engine
        system.getTimeEngine().seconds((int) Data.TIME_STEP);
    }

    /**
     * This method iterates through Euler's iterative approximation method and returns the approximation
     * for y(1) for ODE y' = (t + y)^2 - 1, where the actual solution is calculated by y(t) = 2/(3 − 2t) − t
     *
     * @param stepSize with which we iterate
     * @return approximated solution
     */
    public static double experimentResult(double stepSize) {

        double yOld = 2.0 / 3.0;
        double yCurrent = 2.0 / 3.0;
        double tOld;

        double numberOfIterations = 1 / stepSize;

        for (int i = 0; i < numberOfIterations; i++) {
            tOld = stepSize * i;

            yCurrent = yOld + stepSize * ((Math.pow(tOld + yOld, 2.0)) - 1.0);

            yOld = yCurrent;
        }

        return yCurrent;
    }

}