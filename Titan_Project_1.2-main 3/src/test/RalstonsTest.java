package src.test;

import org.junit.Assert;
import org.junit.Test;
import src.main.calculations.landingOpenController.CelestialObjectOpenController;
import src.main.calculations.SolarSystem;
import src.main.calculations.solvers.RalstonsSolver;

public class RalstonsTest {
    final CelestialObjectOpenController[] testObject = new CelestialObjectOpenController[2];
    final CelestialObjectOpenController[] testObjects = new CelestialObjectOpenController[3];
    CelestialObjectOpenController[] testObjectsMass = new CelestialObjectOpenController[3];
    CelestialObjectOpenController[] testObjectsZeroForce = new CelestialObjectOpenController[3];

    /**
     * test if the method update positions works correctly for only one celestial
     * object. The first object in the array is not accounted for as the mass is 0,
     * but we need it as the method UpdatePositions ignores the first object, which is
     * usually the sun. And the sun does not move in our case, so we do not need to
     * calculate the new position of it
     */
    @Test
    public void testUpdatePositions_SingleObject() {

        RalstonsSolver ralston = new RalstonsSolver();
        SolarSystem system = new SolarSystem();

        testObject[0] = new CelestialObjectOpenController("testPlanet", 0.0, new double[] { 0.0, 0.0, 0.0 },
                new double[] { 0.0, 0.0, 0.0 });
        testObject[1] = new CelestialObjectOpenController("testPlanet1", 2.0, new double[] { 1.0, 2.0, 3.0 },
                new double[] { 4.0, 5.0, 6.0 });

        system.setObjects(testObject);

        ralston.updatePositions(system);

        // Verify that the position and velocity have been updated correctly
        double[] expectedPosition = new double[] { 7261.0, 9077.0, 10893.0 };
        double[] expectedVelocity = new double[] { 4.0, 5.0, 6.0 };

        Assert.assertArrayEquals(expectedPosition, testObject[1].getPosition(), 0.001);
        Assert.assertArrayEquals(expectedVelocity, testObject[1].getVelocity(), 0.001);
    }

    /**
     * Test the method UpdatePositions for multiple celestial objects. To make the
     * position and velocity is updated correctly
     */
    @Test
    public void testUpdatePositions_MultipleObjects() {
        RalstonsSolver ralston = new RalstonsSolver();
        SolarSystem system = new SolarSystem();

        testObjects[0] = new CelestialObjectOpenController("testPlanet", 0.0, new double[] { 0.0, 0.0, 0.0 },
                new double[] { 0.0, 0.0, 0.0 });
        testObjects[1] = new CelestialObjectOpenController("testPlanet1", 1.0, new double[] { 4.0e10, 9.0e10, 2.0e10 },
                new double[] { 23.06728712927, 25.527218722, 19.321128128 });
        testObjects[2] = new CelestialObjectOpenController("testPlanet2", 2.0, new double[] { 7.0e10, 6.0e10, 8.0e10 },
                new double[] { 22.11234521321, 17.342344141, 27.913434341 });

        system.setObjects(testObjects);

        for (int i = 0; i < 10e3; i++) {
            ralston.updatePositions(system);
        }

        // Verify that the positions and velocities have been updated correctly
        double[] expectedPosition1 = new double[] { 4.041867e10, 9.046331e10, 2.035067e10 };
        double[] expectedVelocity1 = new double[] { 23.0672, 25.5272, 19.3211 };
        double[] expectedPosition2 = new double[] { 7.040133e10, 6.031476e10, 8.050662e10 };
        double[] expectedVelocity2 = new double[] { 22.1123, 17.3423, 27.9134 };

        Assert.assertArrayEquals(expectedPosition1, testObjects[1].getPosition(), 1e6);
        Assert.assertArrayEquals(expectedVelocity1, testObjects[1].getVelocity(), 0.0001);
        Assert.assertArrayEquals(expectedPosition2, testObjects[2].getPosition(), 1e6);
        Assert.assertArrayEquals(expectedVelocity2, testObjects[2].getVelocity(), 0.0001);
    }

}
