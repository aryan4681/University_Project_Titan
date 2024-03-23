package src.main.calculations;

public class Data {

    //  ---   This class holds all masses, radius's, initial positions and velocities   ---

    public static final double TIME_STEP = 60; // time step in seconds

    private static final double RAD_SUN = 696000;
    private static final double RAD_MERCURY = 2440;
    private static final double RAD_VENUS = 6052;
    private static final double RAD_EARTH = 6370;
    private static final double RAD_MARS = 3397;
    private static final double RAD_JUPITER = 71492;
    private static final double RAD_SATURN = 54364;
    private static final double RAD_URANUS = 25559;
    private static final double RAD_NEPTUNE = 24766;
    private static final double RAD_MOON = 1740;
    private static final double RAD_TITAN = 2574;
    private static final double RAD_SPACESHIP = 0;
    public static final double[] RADIUS =
            {
                    RAD_SUN,
                    RAD_MERCURY,
                    RAD_VENUS,
                    RAD_EARTH,
                    RAD_MARS,
                    RAD_JUPITER,
                    RAD_SATURN,
                    RAD_URANUS,
                    RAD_NEPTUNE,
                    RAD_MOON,
                    RAD_TITAN,
                    RAD_SPACESHIP
            };
    // masses of the planets in kilograms
    private static final double MASS_SUN = 1.99e30;
    private static final double MASS_MERCURY = 3.30e23;
    private static final double MASS_VENUS = 4.87e24;
    private static final double MASS_EARTH = 5.97e+24;
    private static final double MASS_MARS = 6.42e23;
    private static final double MASS_JUPITER = 1.90e27;
    private static final double MASS_SATURN = 5.68e26;
    private static final double MASS_URANUS = 8.68e25;
    private static final double MASS_NEPTUNE = 1.02e26;
    private static final double MASS_MOON = 7.35e22;
    private static final double MASS_TITAN = 1.35e23;
    private static final double MASS_SPACESHIP = 5000;
    public static final double[] MASS =
            {
                    MASS_SUN,
                    MASS_MERCURY,
                    MASS_VENUS,
                    MASS_EARTH,
                    MASS_MARS,
                    MASS_JUPITER,
                    MASS_SATURN,
                    MASS_URANUS,
                    MASS_NEPTUNE,
                    MASS_MOON,
                    MASS_TITAN,
                    MASS_SPACESHIP
            };
    // initial positions of the planets
    private static final double[] POS_SUN = {0.00E+00, 0.00E+00, 0.00E+00};
    private static final double[] POS_MERCURY = {7.83E+06, 4.49E+07, 2.87E+06};
    private static final double[] POS_VENUS = {-2.82E+07, 1.04E+08, 3.01E+06};
    private static final double[] POS_EARTH = {-1.48E+08, -2.78E+07, 3.37E+04};
    private static final double[] POS_MARS = {-1.59E+08, 1.89E+08, 7.87E+06};
    private static final double[] POS_JUPITER = {6.93E+08, 2.59E+08, -1.66E+07};
    private static final double[] POS_SATURN = {1.25E+09, -7.60E+08, -3.67E+07};
    private static final double[] POS_URANUS = {1.96E+09, 2.19E+09, -1.72E+07};
    private static final double[] POS_NEPTUNE = {4.45E+09, -3.98E+08, -9.45E+07};
    private static final double[] POS_MOON = {-1.48E+08, -2.75E+07, 7.02E+04};
    private static final double[] POS_TITAN = {1.25E+09, -7.61E+08, -3.63E+07};
    private static final double[] POS_SPACESHIP = {(-1.48E+08), (-2.78E+07), (3.37E+04) + 6370};
    public static final double[][] POSITION =
            {
                    POS_SUN,
                    POS_MERCURY,
                    POS_VENUS,
                    POS_EARTH,
                    POS_MARS,
                    POS_JUPITER,
                    POS_SATURN,
                    POS_URANUS,
                    POS_NEPTUNE,
                    POS_MOON,
                    POS_TITAN,
                    POS_SPACESHIP
            };
    // initial velocities of the planets
    private static final double[] VEL_SUN = {0.00E+00, 0.00E+00, 0.00E+00};
    private static final double[] VEL_MERCURY = {-5.75E+01, 1.15E+01, 6.22E+00};
    private static final double[] VEL_VENUS = {-3.40E+01, -8.97E+00, 1.84E+00};
    private static final double[] VEL_EARTH = {5.05E+00, -2.94E+01, 1.71E-03};
    private static final double[] VEL_MARS = {-1.77E+01, -1.35E+01, 1.52E-01};
    private static final double[] VEL_JUPITER = {-4.71E+00, 1.29E+01, 5.22E-02};
    private static final double[] VEL_SATURN = {4.47E+00, 8.24E+00, -3.21E-01};
    private static final double[] VEL_URANUS = {-5.13E+00, 4.22E+00, 8.21E-02};
    private static final double[] VEL_NEPTUNE = {4.48E-01, 5.45E+00, -1.23E-01};
    private static final double[] VEL_MOON = {4.34E+00, -3.00E+01, -1.16E-02};
    private static final double[] VEL_TITAN = {9.00E+00, 1.11E+01, -2.25E+00};
    private static final double[] VEL_SPACESHIP = {(5.05E+00), (-2.94E+01), (1.71E-03)};
    public static final double[][] VELOCITY =
            {
                    VEL_SUN,
                    VEL_MERCURY,
                    VEL_VENUS,
                    VEL_EARTH,
                    VEL_MARS,
                    VEL_JUPITER,
                    VEL_SATURN,
                    VEL_URANUS,
                    VEL_NEPTUNE,
                    VEL_MOON,
                    VEL_TITAN,
                    VEL_SPACESHIP
            };

    private static final String NAM_SUN = "Sun";
    private static final String NAM_MERCURY = "Mercury";
    private static final String NAM_VENUS = "Venus";
    private static final String NAM_EARTH = "Earth";
    private static final String NAM_MARS = "Mars";
    private static final String NAM_JUPITER = "Jupiter";
    private static final String NAM_SATURN = "Saturn";
    private static final String NAM_URANUS = "Uranus";
    private static final String NAM_NEPTUNE = "Neptune";
    private static final String NAM_MOON = "Moon";
    private static final String NAM_TITAN = "Titan";
    private static final String NAM_SPACESHIP = "SpaceshipOpenController";

    public static final String[] NAME =
            {
                    NAM_SUN,
                    NAM_MERCURY,
                    NAM_VENUS,
                    NAM_EARTH,
                    NAM_MARS,
                    NAM_JUPITER,
                    NAM_SATURN,
                    NAM_URANUS,
                    NAM_NEPTUNE,
                    NAM_MOON,
                    NAM_TITAN,
                    NAM_SPACESHIP
            };

    // Collection of ID's
    public static final int indSun = 0;
    public static final int indMercury = 1;
    public static final int indVenus = 2;
    public static final int indEarth = 3;
    public static final int indMars = 4;
    public static final int indJupiter = 5;
    public static final int indSaturn = 6;
    public static final int indUranus = 7;
    public static final int indNeptune = 8;
    public static final int indMoon = 9;
    public static final int indTitan = 10;
    public static final int indSpaceship = 11;

    /**
     * This method helps to find indexes of celestial objects by their names
     *
     * @param name of the celestial object to return
     * @return id of the celestial object in objects[] array
     */
    public static int returnPlanetID(String name) {
        return switch (name.toLowerCase()) {
            case "sun" -> indSun;
            case "mercury" -> indMercury;
            case "venus" -> indVenus;
            case "earth" -> indEarth;
            case "mars" -> indMars;
            case "jupiter" -> indJupiter;
            case "saturn" -> indSaturn;
            case "uranus" -> indUranus;
            case "neptune" -> indNeptune;
            case "moon" -> indMoon;
            case "titan" -> indTitan;
            case "probe" -> indSpaceship;
            default -> -1;
        };
    }

    //  --- Getters and Setters ---

    public static double getRadius(int id) {
        try {
            return RADIUS[id];
        } catch (Exception e) {
            System.out.println("There is no such data for radius.");
        }

        return -1;
    }

}
