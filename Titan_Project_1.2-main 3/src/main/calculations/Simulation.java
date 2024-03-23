package src.main.calculations;

import src.main.calculations.landingOpenController.CelestialObjectOpenController;
import src.main.calculations.solvers.EulerSolver;

public class Simulation {

    //  ---   This class can launch the simulation, without the GUI, in order to test the code   ---

    // Change these fields to run the simulation as you see fit:
    final private static int numberOfIterations = 200000;
    final private static int idObjectToFollow = Data.returnPlanetID("earth");
    final private static int intervalToPrintPositions = 10000;

    /*
     * Possible objects to follow:
     *  - sun
     *  - mercury
     *  - venus
     *  - earth
     *  - mars
     *  - jupiter
     *  - saturn
     *  - uranus
     *  - neptune
     *  - moon
     *  - titan
     *  - probe
     */

    /**
     * Run this method to test the simulation without the GUI
     */
    public static void main(String[] args) {

        SolarSystem system = new SolarSystem();
        CelestialObjectOpenController followedObject = system.getObjects()[idObjectToFollow];

        for (int i = 0; i < numberOfIterations; i++) {

            // Here the solver is specified:
            EulerSolver.updatePositions(system);

            if (i % intervalToPrintPositions == 0) {
                System.out.println(
                        followedObject.getName() +
                                "'s X: " + followedObject.getPosition()[0] +
                                " Y: " + followedObject.getPosition()[1] +
                                " Z: " + followedObject.getPosition()[2]
                );
            }
        }

    }

}
