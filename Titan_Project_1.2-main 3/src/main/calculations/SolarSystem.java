package src.main.calculations;

import src.main.calculations.landingOpenController.CelestialObjectOpenController;
import src.main.calculations.landingOpenController.LandingModule;
import src.main.calculations.landingOpenController.SpaceshipOpenController;

import java.util.Arrays;

public class SolarSystem {

    // --- This class holds our CelestialObjectOpenController objects and the force calculation method ---

    private static final double GRAVITATIONAL_CONST = 6.6743e-20; // gravitational constant (km^3/kg*s^2)
    private static final double ORBIT_ALTITUDE = 200; // requested attitude to orbit Titan (km)
    private final TimeEngine timeEngine;
    // Array of Celestial Objects on which we'll be operating - every object has mass, position and velocity
    private CelestialObjectOpenController[] objects;

    /**
     * Creates a celestial object array with the planets in our Solar system.
     */
    public SolarSystem() {
        objects = new CelestialObjectOpenController[12];

        for (int i = 0; i < 11; i++) {
            objects[i] = new CelestialObjectOpenController(
                    Data.NAME[i],
                    Data.MASS[i],
                    Arrays.copyOf(Data.POSITION[i], Data.POSITION[i].length),
                    Arrays.copyOf(Data.VELOCITY[i], Data.VELOCITY[i].length)
            );
        }

        SpaceshipOpenController spaceshipOpenController = new SpaceshipOpenController(
                Data.NAME[11],
                Data.MASS[11],
                Arrays.copyOf(Data.POSITION[11], Data.POSITION[11].length),
                Arrays.copyOf(Data.VELOCITY[11], Data.VELOCITY[11].length),
                new LandingModule(0.0, 0.0, 0.0, 0.0,
                        0.0, 0.0));
        objects[11] = spaceshipOpenController;

        timeEngine = new TimeEngine();
    }

    /**
     * Constructor for testing purposes, one celestial object can be specified.
     */
    public SolarSystem(CelestialObjectOpenController object, CelestialObjectOpenController object2) {
        objects = new CelestialObjectOpenController[2];
        objects[0] = object;
        objects[1] = object2;
        timeEngine = new TimeEngine();
    }

    /**
     * Returns the object specified, in our use case it returns a specified planet.
     *
     * @return objects
     */
    public CelestialObjectOpenController[] getObjects() {
        return objects;
    }

    /**
     * Sets an object within a solarsystem. This is used in our tests.
     *
     * @param pObjects objects
     */
    public void setObjects(CelestialObjectOpenController[] pObjects) {
        objects = pObjects;
    }

    /**
     * Returns the time engine.
     *
     * @return timeEngine
     */
    public TimeEngine getTimeEngine() {
        return timeEngine;
    }

    /**
     * Calculates Newton's law of gravitational force for every planet, by every
     * planet.
     *
     * @return the 2d array with each planets total force for every
     * coordinate(x,y,z).
     */
    public double[][] calculateForces() {

        double[][] force = new double[objects.length][3];

        // calculate forces working on every planet except sun
        for (int i = 1; i < objects.length; i++) {

            double[] sumOfForces = {0, 0, 0};

            for (int j = 0; j < objects.length; j++) {

                if (j == i) {
                    continue;
                }

                for (int k = 0; k < 3; k++) {
                    sumOfForces[k] += GRAVITATIONAL_CONST * objects[i].getMass() * objects[j].getMass() *
                            (objects[i].getPosition()[k] - objects[j].getPosition()[k]) /
                            (Math.pow((Math.sqrt(
                                            Math.pow(objects[j].getPosition()[0] - objects[i].getPosition()[0], 2) +
                                                    Math.pow(objects[j].getPosition()[1] - objects[i].getPosition()[1], 2) +
                                                    Math.pow(objects[j].getPosition()[2] - objects[i].getPosition()[2], 2))),
                                    3));
                }
            }

            sumOfForces[0] = -1 * sumOfForces[0];
            sumOfForces[1] = -1 * sumOfForces[1];
            sumOfForces[2] = -1 * sumOfForces[2];

            force[i] = sumOfForces;
        }

        return force;
    }

    /**
     * Takes into consideration objects and calculates the distance between the
     * earth object and the rocket object
     *
     * @return distance between the Earth and the rocket
     */
    public double calculateDistanceEarthRocket() {
        CelestialObjectOpenController earth = objects[3];
        CelestialObjectOpenController rocket = objects[11];

        // Calculating the distance

        return Math.sqrt(
                Math.pow(earth.getPosition()[0] - rocket.getPosition()[0], 2) +
                        Math.pow(earth.getPosition()[1] - rocket.getPosition()[1], 2) +
                        Math.pow(earth.getPosition()[2] - rocket.getPosition()[2], 2)
        );


    }

    /**
     * Takes into consideration objects and calculates the distance between the
     * titan object and the rocket object
     *
     * @return distance between the Titan and the Earth
     */
    public double calculateDistanceTitanRocket() {
        CelestialObjectOpenController titan = objects[10];
        CelestialObjectOpenController rocket = objects[11];

        // Calculating the distance

        return Math.sqrt(
                Math.pow(titan.getPosition()[0] - rocket.getPosition()[0], 2) +
                        Math.pow(titan.getPosition()[1] - rocket.getPosition()[1], 2) +
                        Math.pow(titan.getPosition()[2] - rocket.getPosition()[2], 2)
        );


    }

    /**
     * Takes into consideration objects and calculates the distance between the titan object and the rocket object
     *
     * @return distance between the Titan and the rocket
     */
    public double calculateDistanceEarthTitan() {
        CelestialObjectOpenController titan = objects[10];
        CelestialObjectOpenController earth = objects[3];


        //Calculating the distance

        return Math.sqrt(
                Math.pow(titan.getPosition()[0] - earth.getPosition()[0], 2) +
                        Math.pow(titan.getPosition()[1] - earth.getPosition()[1], 2) +
                        Math.pow(titan.getPosition()[2] - earth.getPosition()[2], 2)
        );


    }

    public double calculateEntryVelocityTitan() {
        return Math.sqrt((GRAVITATIONAL_CONST * objects[10].getMass()) / (ORBIT_ALTITUDE + Data.getRadius(10)));
    }

}
