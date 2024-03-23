package src.test;

import org.junit.*;

import src.main.calculations.landingOpenController.CelestialObjectOpenController;
import src.main.calculations.SolarSystem;

public class SolarSystemTest {

    /**
     * Tests if the method calculateForces works correctly for one object. The force
     * should be 0
     */
    @Test
    public void testCalculateForces_SingleObject() {
        SolarSystem system = new SolarSystem();

        CelestialObjectOpenController[] testObject = new CelestialObjectOpenController[1];
        testObject[0] = new CelestialObjectOpenController("testPlanet", 1.0, new double[] { 0.0, 0.0, 0.0 }, null);

        system.setObjects(testObject);

        double[][] expectedForce = new double[1][3];
        expectedForce[0] = new double[] { 0.0, 0.0, 0.0 };

        double[][] actualForce = system.calculateForces();

        Assert.assertArrayEquals(expectedForce, actualForce);
    }

    /**
     * Tests if the method calculateForces works correctly for multiple objects.
     */
    @Test
    public void testCalculateForces_MultipleObjects() {
        SolarSystem system = new SolarSystem();
        CelestialObjectOpenController[] testObjects = new CelestialObjectOpenController[2];
        testObjects[0] = new CelestialObjectOpenController("testPlanet", 1.0, new double[] { 0.0, 0.0, 0.0 }, null);
        testObjects[1] = new CelestialObjectOpenController("testPlanet", 2.0, new double[] { 1.0, 0.0, 0.0 }, null);

        system.setObjects(testObjects);

        double[] expectedForce1 = { 0.0, 0.0, 0.0 };
        double[] expectedForce2 = { -1.33486e-19, 0.0, 0.0 };

        double[][] actualForce = system.calculateForces();

        double[] actualForce1 = actualForce[0];
        double[] actualForce2 = actualForce[1];

        Assert.assertArrayEquals(expectedForce1, actualForce1, 1e-24);
        Assert.assertArrayEquals(expectedForce2, actualForce2, 1e-24);
    }

    /**
     * Tests if the distance is calculated correctly
     */
    @Test
    public void calculateDistanceEarthRocketTest() {
        SolarSystem system = new SolarSystem();
        double expected = 6370.0;
        Assert.assertEquals(expected, system.calculateDistanceEarthRocket(), 0.001);
    }

    /**
     * Tests if the distance is calculated correctly
     */
    @Test
    public void calculateDistanceTitanRocketTest() {
        SolarSystem system = new SolarSystem();
        double expected = 1.57901827e9;
        Assert.assertEquals(expected, system.calculateDistanceTitanRocket(), 1e6);
    }

    /**
     * Tests if the distance is calculated correctly
     */
    @Test
    public void calculateDistanceEarthTitanTest() {
        SolarSystem system = new SolarSystem();
        double expected = 1.57901827e9;
        Assert.assertEquals(expected, system.calculateDistanceEarthTitan(), 1e6);
    }

    /**
     * Tests if the velocity needed for entering Titan is calculated correctly
     */
    @Test
    public void calculateEntryVelocityTitanTest() {
        SolarSystem system = new SolarSystem();
        double expected = 3.2481;
        Assert.assertEquals(expected, system.calculateEntryVelocityTitan(), 1e6);
    }

}
