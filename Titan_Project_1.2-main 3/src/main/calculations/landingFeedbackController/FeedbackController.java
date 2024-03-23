package src.main.calculations.landingFeedbackController;

import src.main.calculations.StochasticWindModel;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class FeedbackController {

    final private static double GRAVITY_TITAN = 1.352e-3; // km/s^2
    final private static double HORIZONTAL_MARGIN = 0.0001;
    static double setOnce = 0;
    final private double[] TITAN_POSITION = {0, -2574, 0};
    final private double[] LANDING_PAD_POSITION = {0, 0, 0};
    final private double MAX_THRUST = 10 * 1.352e-3; // u_max = 10g
    final private double MAX_TORQUE = 1; // v_max = 1 rad/s^2
    final private double DELTA_Y = 0.0001;
    private final double[] self_assignedInitialPosition = {0, 0};
    private final double[] self_assignedInitialVelocity = {0, 0};
    final CelestialObjectFeedbackController Titan = new CelestialObjectFeedbackController(null, null);
    final SpaceshipFeedbackController LandingModule = new SpaceshipFeedbackController(null, 0,
            self_assignedInitialPosition, self_assignedInitialVelocity, 1,
            0);
    private double U;
    private double angularAcceleration;
    private double halfwayPointY;
    final Queue<double[]> states = new LinkedList<>();
    final StochasticWindModel windModel = new StochasticWindModel(0.0002,1);


    public FeedbackController() {
        initialiseTheController();
    }


    public double[] getNextStep(){
        return states.remove();
    }

    public boolean nextStepExists(){
        return !states.isEmpty();
    }

    private void initialiseTheController(){
        double[] position = {100, 150, 0};
        double[] velocity = {-1, 0, 0};
        double angleOfRotation = ((1 * Math.PI) / 2);
        double timeStep = 1;
        while (!(this.didItLand())) {
            this.simulateLandingToTitan(position, velocity, angleOfRotation, timeStep);
        }
    }

    private void applyWind(SpaceshipFeedbackController landingModule) {

        landingModule.setHorizontalPosition(landingModule.getHorizontalPosition() +
                windModel.windSpeedWithImpactOfAltitude(landingModule.getVerticalPosition(),
                        windModel.getWindSpeed()));
    }

    /**
     * This method simulates the movement of the landing process from its initial
     * position to the y-axis and rotates it to the desired angle.
     *
     * @param initialPosition The initial position of the landing module in the
     *                        solar system.
     * @param initialVelocity The initial velocity of the landing module.
     * @param angleOfRotation The initial angle of rotation of the landing module.
     * @param timeStep        The time step used for the simulation.
     */
    public void reachYaxis(double[] initialPosition, double[] initialVelocity,
                           double angleOfRotation, double timeStep) {

        double[] initial_position = initialPosition.clone();

        if (setOnce == 0) {
            double max_initial_velocity = Math.sqrt((5 * GRAVITY_TITAN) * (2 * Math.abs(initialPosition[0])));
            if (initialVelocity[0] > max_initial_velocity) {
                initialVelocity[0] = max_initial_velocity - 0.0001;
                System.out.println(" too high horizontal velocity, default: " + initialVelocity[0]);
            }

            // height too low
            if (initialPosition[1] < 23) {
                initialVelocity[0] = max_initial_velocity - 0.0001;

            }

            LandingModule.setPosition(initial_position);
            LandingModule.setAngleOfRotation(angleOfRotation);
            LandingModule.setVelocity(initialVelocity);
            LandingModule.setAngularVelocity(0);
            LandingModule.setHorizontalVelocity(initialVelocity[0]);
            LandingModule.setVerticalVelocity(initialVelocity[1]);
            LandingModule.setHorizontalPosition(initialPosition[0]);
            LandingModule.setVerticalPosition(initialPosition[1]);

            Titan.setPosition(TITAN_POSITION);
            setOnce++;
        }
        U = calculateU(angleOfRotation, timeStep);

        double Vertical_acceleration = calculateVerticalAcceleration(U, angleOfRotation);

        if (!(-HORIZONTAL_MARGIN < LandingModule.getPosition()[0] && LandingModule.getPosition()[0] < HORIZONTAL_MARGIN)) {
            LandingModule.setVerticalVelocity(LandingModule.getVerticalVelocity() + Vertical_acceleration);
            LandingModule.setHorizontalPosition(LandingModule.getHorizontalPosition() + LandingModule.getHorizontalVelocity());
            LandingModule.setVerticalPosition(LandingModule.getVerticalPosition() + LandingModule.getVerticalVelocity());

            // deaccelerate here
            double decelDistance = calculateDeaccelerationDistance(initialPosition, initialVelocity);
            if (initialPosition[0] < 0) {
                deaccelerateFromLeftToRight(decelDistance);
            } else if (initialPosition[0] > 0) {
                deaccelerateFromRightToLeft(decelDistance);
            }
        }
        // rotating
        if (-HORIZONTAL_MARGIN < LandingModule.getPosition()[0] && LandingModule.getPosition()[0] < HORIZONTAL_MARGIN) {
            if (!(LandingModule.getAngleOfRotation() == 2 * Math.PI)) {
                angularAcceleration = calculateAngularAcceleration(2 * Math.PI, timeStep);
                if (2 * Math.PI == LandingModule.getAngleOfRotation()) {
                    angularAcceleration = -LandingModule.getAngularVelocity() / timeStep;
                    LandingModule.setAngularVelocity(LandingModule.getAngularVelocity() + angularAcceleration);

                } else if (LandingModule.getAngularVelocity() > 0) {
                    double end_rotation = -1 * LandingModule.getAngularVelocity();
                    if (Math.abs(end_rotation) <= MAX_TORQUE) {
                        angularAcceleration = end_rotation / timeStep;
                        LandingModule.setAngularVelocity(LandingModule.getAngularVelocity() + end_rotation);
                    } else {
                        angularAcceleration = -1 * MAX_TORQUE;
                        LandingModule.setAngularVelocity(LandingModule.getAngularVelocity() - MAX_TORQUE);
                    }
                } else {
                    double angleDifference = (2 * Math.PI - LandingModule.getAngleOfRotation());
                    if (angleDifference < 0) {
                        angleDifference += (2 * Math.PI);
                    }
                    double tempRotationVelocity = (angleDifference / timeStep);

                    double tempRotationAcceleration = tempRotationVelocity / timeStep;

                    angularAcceleration = Math.min(MAX_TORQUE, tempRotationAcceleration);

                    LandingModule.setAngularVelocity(LandingModule.getAngularVelocity() + angularAcceleration);
                }
                LandingModule.setAngleOfRotation(LandingModule.getAngleOfRotation() + LandingModule.getAngularVelocity());
            }
        }
    }

    /**
     * This method is used to calculate the acceleration from the main thruster
     *
     * @param timeStep        current time step
     * @param angleOfRotation current angle of rotation
     * @return a double value representing the acceleration from the main thruster
     */
    public double calculateU(double angleOfRotation, double timeStep) {
        double U;

        if ((-HORIZONTAL_MARGIN < LandingModule.getPosition()[0] && LandingModule.getPosition()[0] < HORIZONTAL_MARGIN)
                && angleOfRotation != 2 * Math.PI) {
            U = 0;
            return U;
        }
        double temp_Velocity = 0;
        if (angleOfRotation == Math.PI / 2 || angleOfRotation == 3 * Math.PI / 2) {
            temp_Velocity = ((LANDING_PAD_POSITION[0] - LandingModule.getPosition()[0]) / timeStep);
        }
        if (angleOfRotation == Math.PI || angleOfRotation == 2 * Math.PI) {
            temp_Velocity = ((LANDING_PAD_POSITION[1] - LandingModule.getPosition()[1]) / timeStep);
        }
        U = temp_Velocity / timeStep;
        if (U > 0) {
            U = Math.min(U, MAX_THRUST);
        }
        if (U < 0) {
            U = Math.max(U, -MAX_THRUST);
        }
        return U;
    }

    /**
     * This method is used to calculate the vertical acceleration of the landing
     * module
     *
     * @param U               acceleration from the main thruster
     * @param angleOfRotation current angle of rotation
     * @return a double value representing the vertical acceleration
     */
    public double calculateVerticalAcceleration(double U, double angleOfRotation) {
        return U * Math.cos(angleOfRotation) - GRAVITY_TITAN;
    }

    /**
     * This method is used to calculate the horizontal acceleration of the landing
     * module
     *
     * @param U               acceleration from the main thruster
     * @param angleOfRotation current angle of rotation
     * @return a double value representing the horizontal acceleration
     */
    public double calculateHorizontalAcceleration(double U, double angleOfRotation) {
        return U * Math.sin(angleOfRotation);
    }

    /**
     * This method is used to calculate the angular acceleration of the landing
     * module
     *
     * @param Goal_angleOfRotation Requested angle of rotation
     * @param timeStep          current time step
     * @return a double value representing the angular acceleration
     */
    public double calculateAngularAcceleration(double Goal_angleOfRotation, double timeStep) {

        double Rotational_Velocity = (Goal_angleOfRotation - LandingModule.getAngleOfRotation()) / timeStep;

        double angularAcceleration = Rotational_Velocity / timeStep;

        angularAcceleration = Math.min(angularAcceleration, MAX_TORQUE);

        if (angularAcceleration > 0) {
            angularAcceleration = Math.min(angularAcceleration, MAX_TORQUE);
        }
        if (angularAcceleration < 0) {
            angularAcceleration = Math.max(angularAcceleration, -MAX_TORQUE);
        }
        return angularAcceleration;
    }

    /**
     * This method is used to deaccelerate the landing module when it comes from
     * left to right
     *
     * @param distance a double value representing the distance from where to start decelerating
     */
    public void deaccelerateFromLeftToRight(double distance) {

        if (LandingModule.getHorizontalPosition() >= -distance) {
            LandingModule.setHorizontalVelocity(LandingModule.getHorizontalVelocity() - MAX_THRUST);
            if (LandingModule.getHorizontalVelocity() == MAX_THRUST) {
                U = 0;
                double Horizontal_acceleration = calculateHorizontalAcceleration(U, LandingModule.getAngleOfRotation());
                LandingModule.setHorizontalVelocity(LandingModule.getHorizontalVelocity() + Horizontal_acceleration);
            } else if (LandingModule.getHorizontalVelocity() < MAX_THRUST) {
                U = -(MAX_THRUST - LandingModule.getHorizontalVelocity());
                double Horizontal_acceleration = calculateHorizontalAcceleration(U,
                        LandingModule.getAngleOfRotation());
                LandingModule.setHorizontalVelocity(LandingModule.getHorizontalVelocity() + Horizontal_acceleration);
            }
            // getting x velocity to zero
            if ((LandingModule.getHorizontalVelocity() - U + LandingModule.getHorizontalPosition()) > 0.0001) {
                while ((LandingModule.getHorizontalVelocity() - U + LandingModule.getHorizontalPosition()) > 0.00005) {
                    U += 0.00001;
                }
                double Horizontal_acceleration = calculateHorizontalAcceleration(U, LandingModule.getAngleOfRotation());
                LandingModule.setHorizontalVelocity(LandingModule.getHorizontalVelocity() + Horizontal_acceleration);
            }
        }
    }

    /**
     * This method is used to deaccelerate the landing module when it comes from
     * right to left
     *
     * @param distance a double value representing the distance from where to start decelerating
     */
    public void deaccelerateFromRightToLeft(double distance) {
        double Deceleration_distance = Math.abs(distance);

        if (LandingModule.getHorizontalPosition() <= Deceleration_distance) {
            LandingModule.setHorizontalVelocity(LandingModule.getHorizontalVelocity() + MAX_THRUST);
            if (LandingModule.getHorizontalVelocity() == -MAX_THRUST) {
                U = 0;
                double Horizontal_acceleration = calculateHorizontalAcceleration(U, LandingModule.getAngleOfRotation());
                LandingModule.setHorizontalVelocity(LandingModule.getHorizontalVelocity() - Horizontal_acceleration);
            } else if (LandingModule.getHorizontalVelocity() > -MAX_THRUST) {
                U = (MAX_THRUST + LandingModule.getHorizontalVelocity());
                double Horizontal_acceleration = calculateHorizontalAcceleration(U, LandingModule.getAngleOfRotation());
                LandingModule.setHorizontalVelocity(LandingModule.getHorizontalVelocity() - Horizontal_acceleration);
            }
            if ((LandingModule.getHorizontalVelocity() - U + LandingModule.getHorizontalPosition()) < -0.0001) {
                while ((LandingModule.getHorizontalVelocity() - U + LandingModule.getHorizontalPosition()) < -0.00005) {
                    U -= 0.00001;
                }
                double Horizontal_acceleration = calculateHorizontalAcceleration(U, LandingModule.getAngleOfRotation());
                LandingModule.setHorizontalVelocity(LandingModule.getHorizontalVelocity() - Horizontal_acceleration);
            }
        }
    }

    /**
     * calculates the distance from where to start decelerating
     *
     * @param initialPosition First position of the landing module
     * @param initialVelocity First velocity of the landing module
     * @return a double value representing the distance from where to start decelerating
     */
    public double calculateDeaccelerationDistance(double[] initialPosition, double[] initialVelocity) {

        if (initialPosition[0] < 0) {
            double max_initial_velocity = Math.sqrt((5 * GRAVITY_TITAN) * (2 * Math.abs(initialPosition[0])));
            if (initialVelocity[0] > max_initial_velocity) {
                initialVelocity[0] = max_initial_velocity - 0.0001;
            }
            double tempVelocity = initialVelocity[0];
            double distance = 0;
            while (tempVelocity > 0.0001) {
                tempVelocity = tempVelocity - Math.min(MAX_THRUST, tempVelocity);
                distance = distance + tempVelocity;
            }
            return distance;
        } else if (initialPosition[0] > 0) {
            double max_initial_velocity = -Math.sqrt((5 * GRAVITY_TITAN) * (2 * Math.abs(initialPosition[0])));
            if (initialVelocity[0] < max_initial_velocity) {
                initialVelocity[0] = max_initial_velocity + 0.0001;
            }
            double tempVelocity = initialVelocity[0];
            double distance = 0;
            while (tempVelocity < -0.0001) {
                tempVelocity = tempVelocity + Math.min(MAX_THRUST, Math.abs(tempVelocity));
                distance = distance + tempVelocity;
            }
            return distance;
        }
        return 0;
    }

    /**
     * Simulates the landing process when the landing module reaches the y-axis.
     */
    public void landingWhenReachedYaxis() {

        applyWind(LandingModule);

        if (LandingModule.getAngleOfRotation() == 2 * Math.PI && LandingModule.getAngularVelocity() == 0) {
            if (LandingModule.getVerticalPosition() > halfwayPointY) {
                U = -6 * GRAVITY_TITAN;
            } else {
                if (-DELTA_Y < LandingModule.getVerticalPosition() && LandingModule.getVerticalPosition() < DELTA_Y) {
                    U = -LandingModule.getVerticalVelocity() + GRAVITY_TITAN;
                }
                if (LandingModule.getVerticalVelocity() < MAX_THRUST) {
                    U = -LandingModule.getVerticalVelocity();
                    if ((LandingModule.getVerticalVelocity() + U - GRAVITY_TITAN
                            + LandingModule.getVerticalPosition()) < -0.0001) {
                        while ((LandingModule.getVerticalVelocity() + U - GRAVITY_TITAN
                                + LandingModule.getVerticalPosition()) < 0) {
                            U += 0.00001;
                        }
                    }
                } else {
                    U = MAX_THRUST;
                }
            }
            U = Math.min(U, MAX_THRUST);
            double Vertical_acceleration = calculateVerticalAcceleration(U, LandingModule.getAngleOfRotation());
            LandingModule.setVerticalVelocity(LandingModule.getVerticalVelocity() + Vertical_acceleration);
            LandingModule.setVerticalPosition(LandingModule.getVerticalPosition() + LandingModule.getVerticalVelocity());
        }
    }

    /**
     * Simulates the landing process of the landing module on Titan.
     *
     * @param initialPosition The initial position of the landing module in the
     *                        solar system.
     * @param initialVelocity The initial velocity of the landing module.
     * @param angleOfRotation The initial angle of rotation of the landing module.
     * @param timeStep        The time step used for the simulation.
     *
     *
     */
    public void simulateLandingToTitan(double[] initialPosition,
                                       double[] initialVelocity,
                                       double angleOfRotation, double timeStep) {

        if (LandingModule.getAngleOfRotation() == 2 * Math.PI) {
            if (LandingModule.getAngularVelocity() > 0) {
                if (2 * Math.PI == LandingModule.getAngleOfRotation()) {
                    angularAcceleration = -LandingModule.getAngularVelocity() / timeStep;
                    LandingModule.setAngularVelocity(LandingModule.getAngularVelocity() + angularAcceleration);
                    halfwayPointY = LandingModule.getVerticalPosition() / 2;
                }
            }
            landingWhenReachedYaxis();
        } else {
            reachYaxis(initialPosition, initialVelocity,
                    angleOfRotation, timeStep);
        }
        double[] result = new double[3];
        result[0] = LandingModule.getHorizontalPosition();
        result[1] = LandingModule.getVerticalPosition();
        result[2] = LandingModule.getAngleOfRotation();
        states.add(result);

    }


    /**
     * Checks if the landing module has successfully landed.
     *
     * @return true if the landing module is within the acceptable range for landing
     */
    public boolean didItLand() {

        double DELTA_X = 0.0001;
        boolean withinX = Math.abs(LandingModule.getHorizontalPosition() - LANDING_PAD_POSITION[0]) <= DELTA_X;
        boolean withinY = Math.abs(LandingModule.getVerticalPosition() - LANDING_PAD_POSITION[1]) <= DELTA_Y;
        double DELTA_ANGLE = 0.02;
        boolean withinAngle = Math.abs(LandingModule.getAngleOfRotation() % (2 * Math.PI)) <= DELTA_ANGLE;
        double EPSILON_X = 0.0001;
        boolean withinXVel = Math.abs(LandingModule.getHorizontalVelocity()) <= EPSILON_X;
        double EPSILON_Y = 0.0001;
        boolean withinYVel = Math.abs(LandingModule.getVerticalVelocity()) <= EPSILON_Y;
        double EPSILON_ANGLE = 0.01;
        boolean withinAngularVel = Math.abs(LandingModule.getAngularVelocity()) <= EPSILON_ANGLE;

        return withinX && withinY && withinAngle && withinXVel && withinYVel && withinAngularVel;
    }

    public static void main(String[] args) {

        FeedbackController controller = new FeedbackController();
            controller.initialiseTheController();
        while(controller.nextStepExists()){
            System.out.println(Arrays.toString(controller.getNextStep()));
        }
    }

}