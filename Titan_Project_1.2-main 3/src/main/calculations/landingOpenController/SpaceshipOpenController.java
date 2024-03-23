package src.main.calculations.landingOpenController;

public class SpaceshipOpenController extends CelestialObjectOpenController {

    private final LandingModule landingModule;

    public SpaceshipOpenController(String name, double mass, double[] position, double[] velocity, LandingModule landingModule) {

        super(name, mass, position, velocity);
        this.landingModule = landingModule;
    }

    public LandingModule getLandingModule() {
        return landingModule;
    }

}
