package src.main.calculations.landingFeedbackController;

public class CelestialObjectFeedbackController {

    private double[] position;
    private double[] velocity;

    public CelestialObjectFeedbackController(double[] position, double[] velocity) {
        this.position = position;
        this.velocity = velocity;
    }

    //  --- Getters and Setters ---

    public double[] getPosition() {
        return position;
    }

    public void setPosition(double[] value) {
        this.position = value;
    }

    public double[] getVelocity() {
        return velocity;
    }

    public void setVelocity(double[] value) {
        this.velocity = value;
    }

}