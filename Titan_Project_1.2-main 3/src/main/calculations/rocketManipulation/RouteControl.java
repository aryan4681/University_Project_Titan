package src.main.calculations.rocketManipulation;

import src.main.calculations.SolarSystem;

/**
 * This class is responsible for controlling the route of the rocket.
 */
public class RouteControl {
    public static final double[] decreaseFire = new double[]{-1637000.0, 936000.0, 14500.0};
    private final Engine engine = new Engine();
    private final SolarSystem system;
    private final double[] initialFire = new double[]{1607217.6098823547, -620067.0599937439, -44522.12333679199};
    public int numberOfMethodCalled = 1;
    public int stage = 1;
    private double[] increaseFire = new double[]{-2136310.577392578, -152364.73083496094, 171161.65161132812};

    /**
     * Constructor for the RouteControl class.
     *
     * @param system The SolarSystem object to control.
     */
    public RouteControl(SolarSystem system) {
        this.system = system;
    }

    /**
     * Sets the increaseFire array.
     *
     * @param increaseFire The array to set.
     */
    public void setIncreaseFire(double[] increaseFire) {
        this.increaseFire = increaseFire;
    }

    /**
     * Checks the control of the rocket.
     */
    public void checkControl() {

        switch (stage) {
            case 1 -> controlStage1();
            case 2 -> controlStage2();
            case 3 -> controlStage3();
        }
        numberOfMethodCalled++;

    }

    /**
     * Controls the first stage of the rocket.
     */
    public void controlStage1() {
        if (numberOfMethodCalled == 1) {
            System.out.println("Stage 1 - Travel to Titan");
            //System.out.println("Initial fire 1");
            engine.fireEngineWithImpuls(initialFire, system);
        }

        if (numberOfMethodCalled == 2) {
            //System.out.println("Initial fire 2");
            engine.fireEngineWithImpuls(initialFire, system);
        }

        updateStage1();
    }

    /**
     * Updates the first stage of the rocket.
     */
    public void updateStage1() {
        if (system.calculateDistanceTitanRocket() < 300 + 7000 + 1000) {
            System.out.println("Stage 2 - Orbiting");
            stage = 2;
            numberOfMethodCalled = 0;
        }
    }

    /**
     * Controls the second stage of the rocket.
     */
    public void controlStage2() {
        if (numberOfMethodCalled == 1) {
            //System.out.println("Decrease fire 1");
            engine.fireEngineWithImpuls(decreaseFire, system);
        }

        if (numberOfMethodCalled == 2) {
            //System.out.println("Decrease fire 2");
            engine.fireEngineWithImpuls(decreaseFire, system);
        }
        updateStage2();
    }

    /**
     * Updates the second stage of the rocket.
     */
    private void updateStage2() {
        if (system.calculateDistanceEarthTitan() > system.calculateDistanceEarthRocket() && numberOfMethodCalled > 60) {
            System.out.println("Stage 3 - Travel to Earth");
            stage = 3;
            numberOfMethodCalled = 0;
        }
    }

    /**
     * Controls the third stage of the rocket.
     */
    public void controlStage3() {
        if (numberOfMethodCalled == 1) {
            //System.out.println("Increase fire 1");
            engine.fireEngineWithImpuls(increaseFire, system);
        }

        if (numberOfMethodCalled == 2) {
            //System.out.println("Increase fire 2");
            engine.fireEngineWithImpuls(increaseFire, system);
        }
    }

    /**
     * Gets the consumed fuel.
     *
     * @return The consumed fuel.
     */
    public double getConsumedFuel() {
        return engine.getConsumedFuel();
    }
}
