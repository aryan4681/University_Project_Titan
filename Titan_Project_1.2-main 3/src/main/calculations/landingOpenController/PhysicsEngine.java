package src.main.calculations.landingOpenController;

import src.main.calculations.SolarSystem;

/**
 * This class represents the physics engine of the landing module, which is responsible for updating the state of the landing module.
 */
public class PhysicsEngine {

    public static final double GRAVITY_TITAN = 1.352e-3; // km/s^2

    /**
     * This method updates the state of the landing module, by using the ODE solver.
     * It updates the horizontal position, vertical position and angle of rotation of the landing module, all paired with their respective velocities.
     *
     * @param solarsystem        The solar system, which contains the landing module
     * @param accelMainThruster  The acceleration of the main thruster
     * @param torqueSideThruster The torque of the side thruster
     */
    public static void updateLandingModuleState(SolarSystem solarsystem, double accelMainThruster, double torqueSideThruster) {

        SpaceshipOpenController spaceshipOpenController = null;
        for (CelestialObjectOpenController object : solarsystem.getObjects()) {
            if (object instanceof SpaceshipOpenController) {
                spaceshipOpenController = (SpaceshipOpenController) object;
            }
        }
        assert spaceshipOpenController != null;

        LandingModule landingModule = spaceshipOpenController.getLandingModule();

        // x'' state (horizontal position)
        double newHorVel = ControllerODESolver.eulerSolver(landingModule.getHorizontalVelocity(),
                (accelMainThruster * Math.sin(landingModule.getAngleOfRotation())));
        landingModule.setHorizontalVelocity(newHorVel);
        double newHorPos = ControllerODESolver.eulerSolver(landingModule.getHorizontalPositionLandingModule(),
                landingModule.getHorizontalVelocity());
        landingModule.setHorizontalPositionLandingModule(newHorPos);

        // y'' state (vertical position)
        double newVerVel;
        if (landingModule.getVerticalPositionLandingModule() < 0) {
            newVerVel = ControllerODESolver.eulerSolver(landingModule.getVerticalVelocity(),
                    (accelMainThruster * Math.cos(landingModule.getAngleOfRotation()) + GRAVITY_TITAN));
        } else {
            newVerVel = ControllerODESolver.eulerSolver(landingModule.getVerticalVelocity(),
                    (accelMainThruster * Math.cos(landingModule.getAngleOfRotation()) - GRAVITY_TITAN));
        }
        landingModule.setVerticalVelocity(newVerVel);
        double newVerPos = ControllerODESolver.eulerSolver(landingModule.getVerticalPositionLandingModule(),
                landingModule.getVerticalVelocity());
        landingModule.setVerticalPositionLandingModule(newVerPos);

        // θ'' state (angle of rotation)
        double newAngVel = landingModule.getAngularVelocity() + torqueSideThruster;
        landingModule.setAngularVelocity(newAngVel);
        double newAngPos = landingModule.getAngleOfRotation() + landingModule.getAngularVelocity();
        landingModule.setAngleOfRotation(newAngPos);
    }
}
