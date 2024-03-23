package src.main.calculations.landingOpenController;

public class LandingModule implements Cloneable {

    private double horizontalPositionLandingModule; // in km
    private double verticalPositionLandingModule; // in  km
    private double angleOfRotation; // in radians
    private double horizontalVelocity; // in km/s
    private double verticalVelocity; // in km/s
    private double angularVelocity; // in rad/s

    public LandingModule(double horizontalPositionLandingModule,
                         double verticalPositionLandingModule, double angleOfRotation, double horizontalVelocity, double verticalVelocity,
                         double angularVelocity) {
        this.horizontalPositionLandingModule = horizontalPositionLandingModule;
        this.verticalPositionLandingModule = verticalPositionLandingModule;
        this.angleOfRotation = angleOfRotation;
        this.horizontalVelocity = horizontalVelocity;
        this.verticalVelocity = verticalVelocity;
        this.angularVelocity = angularVelocity;

    }

    // Getters
    public double getHorizontalPositionLandingModule() {
        return horizontalPositionLandingModule;
    }

    // Setters
    public void setHorizontalPositionLandingModule(double horizontalPositionLandingModule) {
        this.horizontalPositionLandingModule = horizontalPositionLandingModule;
    }

    public double getVerticalPositionLandingModule() {
        return verticalPositionLandingModule;
    }

    public void setVerticalPositionLandingModule(double verticalPositionLandingModule) {
        this.verticalPositionLandingModule = verticalPositionLandingModule;
    }

    public double getAngleOfRotation() {
        return angleOfRotation;
    }

    public void setAngleOfRotation(double angleOfRotation) {
        this.angleOfRotation = angleOfRotation;
    }

    public double getHorizontalVelocity() {
        return horizontalVelocity;
    }

    public void setHorizontalVelocity(double horizontalVelocity) {
        this.horizontalVelocity = horizontalVelocity;
    }

    public double getVerticalVelocity() {
        return verticalVelocity;
    }

    public void setVerticalVelocity(double verticalVelocity) {
        this.verticalVelocity = verticalVelocity;
    }

    public double getAngularVelocity() {
        return angularVelocity;
    }

    public void setAngularVelocity(double angularVelocity) {
        this.angularVelocity = angularVelocity;
    }

    @Override
    public LandingModule clone() {
        try {
            return (LandingModule) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
