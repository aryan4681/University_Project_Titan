package src.main.calculations.landingOpenController;

import src.main.calculations.Data;
import src.main.calculations.SolarSystem;
import src.main.calculations.StochasticWindModel;

import java.util.LinkedList;
import java.util.Queue;

/**
 * This class is responsible for the landing procedure of the landing module. It calculates the trajectory of the landing module and stores it in a queue. The queue can be accessed by the controller to get the next step of the trajectory.
 */
public class OpenController {

    private final static double MAX_TORQUE = 1.0; // radian per second squared
    private final static double MAX_THRUST_MAIN_ENGINE = 10 * PhysicsEngine.GRAVITY_TITAN; // km/s^2
    private final static double angleOfRotation = (3 * Math.PI) / 2;
    private final static double angularVelocity = 0.0;
    private final static double horizontalPositionLandingModule = -100; // GIVEN BY EXAMINERS, OPPOSITE SIGN OF VX
    private final static double horizontalVelocity = -1.12; // GIVEN BY EXAMINERS, OPPOSITE SIGN OF X
    private final static double verticalPositionLandingModule = 100; // GIVEN BY EXAMINERS, IS POSITIVE
    private final static double verticalVelocity = 2.23; // CHOSEN BY US
    private final SolarSystem system; // the solar system
    private final Queue<double[]> trajectory = new LinkedList<>(); // queue of trajectory steps
    private final StochasticWindModel windModel = new StochasticWindModel(0.0002, 0.002);

    public OpenController(SolarSystem system) {
        this.system = system;
        activateLandingProcedure();
    }

    /**
     * This method gets called by GUI to get the next step of the trajectory.
     * It returns the next step of the trajectory and removes it from the queue.
     *
     * @return The next step of the trajectory
     */
    public double[] getNextStep() {
        return trajectory.poll();
    }

    public boolean nextStepExists() {
        return !trajectory.isEmpty();
    }

    /**
     * This method activates the landing procedure. It calls all necessary methods to determine the trajectory of the landing module (x-axis, y-axis and angle). The methods that get called fill the trajectory queue internally.
     */
    private void activateLandingProcedure() {

        SolarSystem system2D = generateSolarsystemSpaceshipTitan2Dimensional(system);
        SpaceshipOpenController spaceshipOpenController = null;
        for (CelestialObjectOpenController object : system2D.getObjects()) {
            if (object instanceof SpaceshipOpenController) {
                spaceshipOpenController = (SpaceshipOpenController) object;
            }
        }
        assert spaceshipOpenController != null;
        LandingModule landingModule = spaceshipOpenController.getLandingModule();

        landingModule.setAngleOfRotation(angleOfRotation);
        landingModule.setAngularVelocity(angularVelocity);
        landingModule.setHorizontalPositionLandingModule(horizontalPositionLandingModule);
        landingModule.setHorizontalVelocity(horizontalVelocity);
        landingModule.setVerticalPositionLandingModule(verticalPositionLandingModule);
        landingModule.setVerticalVelocity(verticalVelocity);

        windModel.setWindSpeed();

        // Change y velocity to zero
        System.out.println("VERTICAL VELOCITY");
        System.out.println("Initial velocity: " + landingModule.getVerticalVelocity() + "         Target velocity: 0");
        makeYVelocityZero(system2D, landingModule);
        System.out.println("Velocity after change: " + landingModule.getVerticalVelocity() + "\n");

        // Change x velocity to zero
        System.out.println("HORIZONTAL VELOCITY");
        System.out.println("Initial velocity: " + landingModule.getHorizontalVelocity() + "         Target velocity: 0");
        makeXVelocityZero(system2D, landingModule);
        System.out.println("Velocity after change: " + landingModule.getHorizontalVelocity());
        System.out.println("Horizontal velocity is zero\n");

        // Change y velocity to zero
        System.out.println("VERTICAL VELOCITY");
        System.out.println("Initial velocity: " + landingModule.getVerticalVelocity() + "         Target velocity: 0");
        makeYVelocityZero(system2D, landingModule);
        System.out.println("Velocity after change: " + landingModule.getVerticalVelocity() + "\n");

        // Move rocket to y-axis. (X = 0)
        System.out.println("HORIZONTAL POSITION");
        System.out.println("Initial position: " + landingModule.getHorizontalPositionLandingModule() + "         Target position: 0");
        moveToYAxis(system2D, landingModule);
        System.out.println("Horizontal position after change: " + landingModule.getHorizontalPositionLandingModule() + "\n");

        // Move rocket to x-axis. (Y = 0)
        System.out.println("VERTICAL POSITION");
        System.out.println("Initial position: " + landingModule.getVerticalPositionLandingModule() + "         Target position: 0");
        moveToXAxis(system2D, landingModule);
        System.out.println("Vertical position after change: " + landingModule.getVerticalPositionLandingModule() + "\n");

        // Print final values
        System.out.println("FINAL VALUES");
        System.out.println("Angle: " + landingModule.getAngleOfRotation());
        System.out.println("Angle velocity: " + landingModule.getAngularVelocity());
        System.out.println("Horizontal position: " + landingModule.getHorizontalPositionLandingModule());
        System.out.println("Horizontal velocity: " + landingModule.getHorizontalVelocity());
        System.out.println("Vertical position: " + landingModule.getVerticalPositionLandingModule());
        System.out.println("Vertical velocity: " + landingModule.getVerticalVelocity() + "\n");

        printErrors(landingModule);
    }

    private void printErrors(LandingModule landingModule) {
        double DELTA_X = 0.0001;
        double DELTA_Y = 0.0001;
        double DELTA_ANGLE = 0.02;
        double EPSILON_X = 0.0001;
        double EPSILON_Y = 0.0001;
        double EPSILON_ANGLE = 0.01;

        System.out.println("ERRORS");

        double angleError = Math.abs(landingModule.getAngleOfRotation() - (2 * Math.PI));
        if (angleError < DELTA_ANGLE) {
            System.out.println("Angle error PASSED");
        } else {
            System.out.println("Angle error FAILED");
        }
        System.out.println("Angle error: " + angleError + "\n");

        double angleVelocityError = Math.abs(landingModule.getAngularVelocity() - 0);
        if (angleVelocityError < EPSILON_ANGLE) {
            System.out.println("Angle velocity PASSED");
        } else {
            System.out.println("Angle velocity FAILED");
        }
        System.out.println("Angle velocity error: " + angleVelocityError + "\n");

        double xError = Math.abs(landingModule.getHorizontalPositionLandingModule() - 0);
        if (xError < DELTA_X) {
            System.out.println("X position error PASSED");
        } else {
            System.out.println("X position error FAILED");
        }
        System.out.println("X error: " + xError + "\n");

        double xVelocityError = Math.abs(landingModule.getHorizontalVelocity() - 0);
        if (xVelocityError < EPSILON_X) {
            System.out.println("X velocity error PASSED");
        } else {
            System.out.println("X velocity error FAILED");
        }
        System.out.println("X velocity error: " + xVelocityError + "\n");

        double yError = Math.abs(landingModule.getVerticalPositionLandingModule() - 0);
        if (yError < DELTA_Y) {
            System.out.println("Y position error PASSED");
        } else {
            System.out.println("Y position error FAILED");
        }
        System.out.println("Y error: " + yError + "\n");

        double yVelocityError = Math.abs(landingModule.getVerticalVelocity() - 0);
        if (yVelocityError < EPSILON_Y) {
            System.out.println("Y velocity error PASSED");
        } else {
            System.out.println("Y velocity error FAILED");
        }
        System.out.println("Y velocity error: " + yVelocityError + "\n");
    }

    private void applyWind(LandingModule landingModule) {
        landingModule.setHorizontalPositionLandingModule(landingModule.getHorizontalPositionLandingModule() +
                windModel.windSpeedWithImpactOfAltitude(landingModule.getVerticalPositionLandingModule(),
                        windModel.getWindSpeed()));
    }

    /**
     * This method moves the landing module to the x-axis. This means after the method Y = 0.
     *
     * @param solarsystem   The solar system in which the landing module is located.
     * @param landingModule The landing module that has to be moved to the x-axis.
     */
    private void moveToXAxis(SolarSystem solarsystem, LandingModule landingModule) {

        changeAngle(solarsystem, landingModule, landingModule.getAngleOfRotation(), 2 * Math.PI);

        double yPosition = landingModule.getVerticalPositionLandingModule();
        double yVelocity = landingModule.getVerticalVelocity();
        double accelIncludingGravity = MAX_THRUST_MAIN_ENGINE - PhysicsEngine.GRAVITY_TITAN;
        boolean reachedZeroAltitude = false;
        int accelerationSteps = 0;

        while (!reachedZeroAltitude) {
            yPosition += yVelocity;
            yVelocity -= PhysicsEngine.GRAVITY_TITAN;
            accelerationSteps++;

            double yPositionTmp = yPosition;
            double yVelocityTmp = yVelocity;
            // Check if, using max acceleration, the landing module reaches the surface of Titan or not. If not this means it can fall more to the surface of Titan.
            boolean tmp = false;
            while (!tmp) {
                yPositionTmp += yVelocityTmp;
                yVelocityTmp += accelIncludingGravity;
                if (yVelocityTmp < 0) {
                    tmp = true;
                }
            }
            if (yPositionTmp <= 0) {
                reachedZeroAltitude = true;
            }
        }
        for (int i = 0; i < accelerationSteps; i++) {
            PhysicsEngine.updateLandingModuleState(solarsystem, 0, 0);
            trajectory.add(new double[]{landingModule.getHorizontalPositionLandingModule(),
                    landingModule.getVerticalPositionLandingModule(),
                    landingModule.getAngleOfRotation()});
            applyWind(landingModule);
        }
        int deaccelerationSteps = Math.abs((int) (landingModule.getVerticalVelocity() / accelIncludingGravity));
        for (int i = 0; i < deaccelerationSteps; i++) {
            PhysicsEngine.updateLandingModuleState(solarsystem, MAX_THRUST_MAIN_ENGINE, 0);
            trajectory.add(new double[]{landingModule.getHorizontalPositionLandingModule(),
                    landingModule.getVerticalPositionLandingModule(),
                    landingModule.getAngleOfRotation()});
            applyWind(landingModule);
            if (landingModule.getVerticalPositionLandingModule() <= 0.001) {
                break;
            }
        }
    }

    /**
     * This method decelerates the Y velocity to zero. This means after the method Y velocity = 0.
     *
     * @param solarsystem   The solar system in which the landing module is located.
     * @param landingModule The landing module that has to be decelerated.
     */
    private void makeYVelocityZero(SolarSystem solarsystem, LandingModule landingModule) {

        changeAngle(solarsystem, landingModule, landingModule.getAngleOfRotation(), 2 * Math.PI);

        double conversion = 0.01;

        while (!(landingModule.getVerticalVelocity() < conversion && landingModule.getVerticalVelocity() > -conversion)) {
            boolean valueLessThanOneReached = false;
            double deaccelerationAmount = landingModule.getVerticalVelocity();
            double time = 1;
            if (landingModule.getVerticalVelocity() > 0) {
                while (!valueLessThanOneReached) {
                    deaccelerationAmount /= 2;
                    time *= 2;
                    if (deaccelerationAmount < 1) {
                        valueLessThanOneReached = true;
                    }
                }
            } else if (landingModule.getVerticalVelocity() < 0) {
                while (!valueLessThanOneReached) {
                    deaccelerationAmount /= 2;
                    time *= 2;
                    if (deaccelerationAmount > -1) {
                        valueLessThanOneReached = true;
                    }
                }
            }
            for (int i = 0; i < time - 1; i++) {
                PhysicsEngine.updateLandingModuleState(solarsystem, -deaccelerationAmount, 0);
                trajectory.add(new double[]{landingModule.getHorizontalPositionLandingModule(),
                        landingModule.getVerticalPositionLandingModule(),
                        landingModule.getAngleOfRotation()});
                applyWind(landingModule);
                System.out.println("Vertical velocity: " + landingModule.getVerticalVelocity());
            }
        }
    }

    /**
     * This method moves the landing module to the y-axis. This means after the method X = 0.
     *
     * @param solarsystem   The solar system in which the landing module is located.
     * @param landingModule The landing module that has to be moved to the y-axis.
     */
    private void moveToYAxis(SolarSystem solarsystem, LandingModule landingModule) {

        changeAngle(solarsystem, landingModule, landingModule.getAngleOfRotation(), Math.PI / 2);

        double differencePositionAndYAxis = landingModule.getHorizontalPositionLandingModule() - 0;

        boolean allowedAcceleration = false;
        int time = 2;
        double accel = 0;
        while (!allowedAcceleration) {
            accel = differencePositionAndYAxis / Math.pow(time, 2);
            if (accel < MAX_THRUST_MAIN_ENGINE && accel > -MAX_THRUST_MAIN_ENGINE) {
                allowedAcceleration = true;
            } else {
                time++;
            }
        }
        System.out.println("Time: " + time + "      Accel: " + accel);
        for (int i = 0; i < time; i++) {
            PhysicsEngine.updateLandingModuleState(solarsystem, -accel, 0);
            trajectory.add(new double[]{landingModule.getHorizontalPositionLandingModule(),
                    landingModule.getVerticalPositionLandingModule(),
                    landingModule.getAngleOfRotation()});
            applyWind(landingModule);
        }

        for (int i = 0; i < time; i++) {
            PhysicsEngine.updateLandingModuleState(solarsystem, accel, 0);
            trajectory.add(new double[]{landingModule.getHorizontalPositionLandingModule(),
                    landingModule.getVerticalPositionLandingModule(),
                    landingModule.getAngleOfRotation()});
            applyWind(landingModule);
        }
    }

    /**
     * This method decelerates the X velocity to zero. This means after the method X velocity = 0.
     *
     * @param solarsystem   The solar system in which the landing module is located.
     * @param landingModule The landing module that has to be decelerated.
     */
    private void makeXVelocityZero(SolarSystem solarsystem, LandingModule landingModule) {

        changeAngle(solarsystem, landingModule, landingModule.getAngleOfRotation(), Math.PI / 2);

        double conversion = 0.0000001;

        while (!(landingModule.getHorizontalVelocity() < conversion && landingModule.getHorizontalVelocity() > -conversion)) {
            boolean valueLessThanOneReached = false;
            double deaccelerationAmount = landingModule.getHorizontalVelocity();
            double time = 1;
            if (landingModule.getHorizontalVelocity() > 0) {
                while (!valueLessThanOneReached) {
                    deaccelerationAmount /= 2;
                    time *= 2;
                    if (deaccelerationAmount < MAX_THRUST_MAIN_ENGINE) {
                        valueLessThanOneReached = true;
                    }
                }
            } else if (landingModule.getHorizontalVelocity() < 0) {
                while (!valueLessThanOneReached) {
                    deaccelerationAmount /= 2;
                    time *= 2;
                    if (deaccelerationAmount > -MAX_THRUST_MAIN_ENGINE) {
                        valueLessThanOneReached = true;
                    }
                }
            }
            for (int i = 0; i < time - 1; i++) {
                PhysicsEngine.updateLandingModuleState(solarsystem, -deaccelerationAmount, 0);
                trajectory.add(new double[]{landingModule.getHorizontalPositionLandingModule(),
                        landingModule.getVerticalPositionLandingModule(),
                        landingModule.getAngleOfRotation()});
                applyWind(landingModule);
            }
        }
        // Due to java having a hard time with floating point numbers, the angular velocity will never be exactly zero.
        if (landingModule.getHorizontalVelocity() < conversion && landingModule.getHorizontalVelocity() > -conversion) {
            landingModule.setHorizontalVelocity(0);
        }

    }

    /**
     * This method rotates the landing module to the correct angle. This means after the method the angle of rotation is the specified angle.
     *
     * @param solarsystem           The solar system in which the landing module is located.
     * @param landingModule         The landing module that has to be rotated.
     * @param currentAngleInRadians The current angle of rotation of the landing module.
     * @param targetAngleInRadians  The target angle of rotation of the landing module.
     */
    private void changeAngle(SolarSystem solarsystem, LandingModule landingModule, double currentAngleInRadians, double targetAngleInRadians) {
        double differenceInRadians = currentAngleInRadians - targetAngleInRadians;

        boolean allowedAcceleration = false;
        int time = 1;
        double accel = 0;
        while (!allowedAcceleration) {
            accel = differenceInRadians / Math.pow(time, 2);
            if (accel < MAX_TORQUE && accel > -MAX_TORQUE) {
                allowedAcceleration = true;
            } else {
                time++;
            }
        }

        // Move to half of the angle, so that you can brake on time and end up with an angular velocity of zero
        for (int i = 0; i < time; i++) {
            PhysicsEngine.updateLandingModuleState(solarsystem, 0, -accel);
            trajectory.add(new double[]{landingModule.getHorizontalPositionLandingModule(),
                    landingModule.getVerticalPositionLandingModule(),
                    landingModule.getAngleOfRotation()});
        }

        for (int i = 0; i < time; i++) {
            PhysicsEngine.updateLandingModuleState(solarsystem, 0, accel);
            trajectory.add(new double[]{landingModule.getHorizontalPositionLandingModule(),
                    landingModule.getVerticalPositionLandingModule(),
                    landingModule.getAngleOfRotation()});
        }
        // Due to java having a hard time with floating point numbers, the angular velocity will never be exactly zero.
        if (landingModule.getAngularVelocity() < 0.00001) {
            landingModule.setAngularVelocity(0);
            landingModule.setAngleOfRotation(targetAngleInRadians);
        }
    }

    /**
     * This method takes a complete solarsystem and returns a solarsystem with only the spaceship and the landing module in 2D. This means only the x-axis and y-axis.
     *
     * @param solarsystem The solarsystem that has to be converted to 2D.
     * @return The solarsystem with only the spaceship and the landing module in 2D.
     */
    private SolarSystem generateSolarsystemSpaceshipTitan2Dimensional(SolarSystem solarsystem) {

        CelestialObjectOpenController titan = new CelestialObjectOpenController(
                "Titan",
                1.3452e23,
                new double[]{solarsystem.getObjects()[10].getPosition()[0], solarsystem.getObjects()[10].getPosition()[1]},
                new double[]{solarsystem.getObjects()[10].getVelocity()[0], solarsystem.getObjects()[10].getVelocity()[1]}
        );
        SpaceshipOpenController spaceshipOpenController = new SpaceshipOpenController(
                "SpaceshipOpenController",
                10000.0,
                new double[]{solarsystem.getObjects()[11].getPosition()[0], solarsystem.getObjects()[11].getPosition()[1]},
                new double[]{solarsystem.getObjects()[11].getVelocity()[0], solarsystem.getObjects()[11].getVelocity()[1]},
                new LandingModule(0.0, 0.0,
                        0.0, 0.0,
                        0.0, 0.0)
        );
        SolarSystem titanSpaceshipSolarSystem = new SolarSystem(titan, spaceshipOpenController);

        // Get the spaceshipOpenController from the new solar system
        SpaceshipOpenController spaceshipOpenController2DSystem = (SpaceshipOpenController) titanSpaceshipSolarSystem.getObjects()[1];
        LandingModule landingModule = spaceshipOpenController2DSystem.getLandingModule();

        // Set the position and velocity of the titan and the spaceshipOpenController to the relative same as in the original solar system
        // Titan
        titanSpaceshipSolarSystem.getObjects()[0].setPosition(new double[]{0.0, -Data.RADIUS[10]});
        titanSpaceshipSolarSystem.getObjects()[0].setVelocity(new double[]{0.0, 0.0});

        // SpaceshipOpenController
        titanSpaceshipSolarSystem.getObjects()[1].setPosition(new double[]{1.361820e9, -4.855040e8});
        titanSpaceshipSolarSystem.getObjects()[1].setVelocity(new double[]{solarsystem.getObjects()[11].getVelocity()[0], solarsystem.getObjects()[11].getVelocity()[1]});
        landingModule.setHorizontalPositionLandingModule(1.363338344717167e9 - 1.3599747014853237e9);
        landingModule.setVerticalPositionLandingModule(-4.872274558557276e8 + 4.8526848501332206e8);
        landingModule.setHorizontalVelocity(0);
        landingModule.setVerticalVelocity(50);

        // Set the angle of landing module to 90 degrees, but in radians
        landingModule.setAngleOfRotation(Math.toRadians(0));
        landingModule.setAngularVelocity(0.0);

        return titanSpaceshipSolarSystem;

    }

}
