package src.main.calculations.landingFeedbackController;

public class SpaceshipFeedbackController extends CelestialObjectFeedbackController {


    private double angleOfRotation; // in radians
    private double angularVelocity; // in rad/s

    public SpaceshipFeedbackController(String name, double mass, double[] position, double[] velocity, double angleOfRotation,
                                       double angularVelocity) {

        super(position, velocity);

        this.angleOfRotation = angleOfRotation;

        this.angularVelocity = angularVelocity;

    }

    public double getHorizontalPosition() {
        return this.getPosition()[0];
    }

    public void setHorizontalPosition(double horizontalPosition) {
        this.getPosition()[0] = horizontalPosition;
    }

    public double getVerticalPosition() {
        return this.getPosition()[1];
    }

    public void setVerticalPosition(double verticalPosition) {
        this.getPosition()[1] = verticalPosition;
    }

    public double getAngleOfRotation() {
        return angleOfRotation;
    }

    public void setAngleOfRotation(double angleOfRotation) {
        this.angleOfRotation = angleOfRotation;
    }

    public double getHorizontalVelocity() {
        return this.getVelocity()[0];
    }

    public void setHorizontalVelocity(double horizontalVelocity) {
        this.getVelocity()[0] = horizontalVelocity;
    }

    public double getVerticalVelocity() {
        return this.getVelocity()[1];
    }

    public void setVerticalVelocity(double verticalVelocity) {
        this.getVelocity()[1] = verticalVelocity;
    }

    public double getAngularVelocity() {
        return angularVelocity;
    }

    public void setAngularVelocity(double angularVelocity) {
        this.angularVelocity = angularVelocity;
    }


}