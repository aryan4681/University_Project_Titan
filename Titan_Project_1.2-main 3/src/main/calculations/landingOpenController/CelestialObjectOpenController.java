package src.main.calculations.landingOpenController;

public class CelestialObjectOpenController {

    private final String name;
    private final double mass;
    private double[] position;
    private double[] velocity;

    public CelestialObjectOpenController(String name, double mass, double[] position, double[] velocity) {
        this.name = name;
        this.mass = mass;
        this.position = position;
        this.velocity = velocity;
    }

    /**
     * This method is used in solvers. It edits the position parameter of an object
     *
     * @param index         Which one of the position in the position parameter you want to append
     * @param appendedValue What value yoy want to append to the position
     */
    public void addToPosition(int index, double appendedValue) {
        this.getPosition()[index] += appendedValue;
    }

    /**
     * This method is used in solvers. It edits the velocity parameter of an object
     *
     * @param index         Which one of the velocity in the velocity parameter you want to append
     * @param appendedValue What value yoy want to append to the velocity
     */
    public void addToVelocity(int index, double appendedValue) {
        this.getVelocity()[index] += appendedValue;
    }

    //  --- Getters and Setters ---

    public String getName() {
        return name;
    }

    public double getMass() {
        return mass;
    }

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
