package src.main.GUI.panes;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import src.main.calculations.Data;
import src.main.calculations.rocketManipulation.RouteControl;
import src.main.calculations.SolarSystem;
import src.main.calculations.solvers.EulerSolver;
import src.main.GUI.Orb;
import src.main.GUI.Style;

import java.util.ArrayList;

public class PaneSystem extends Pane {

    // PaneSystem is a Pane that will be showing our Solar Panel

    public static int centerOrb = 0;
    public static int timeScalar = 1;
    public static boolean isPaused = false;
    //Managing route displayal
    public static final boolean showRoute = true;
    static final double width = 800;
    static final double height = 750;
    static final double xCenter = width / 2;
    static final double yCenter = height / 2;
    static SolarSystem system;
    static RouteControl routeControl;
    static final double initialScalar = 1 / 1E7 * 2.2;
    static final double scalar = initialScalar;
    static double zoomFactor = 1;
    static ArrayList<Text> texts;
    static PaneCenters centersPane;
    static Pane orbsPane;
    static PaneInfo infoPane;
    static double centerShiftX = 0;
    static double centerShiftY = 0;

    //Managing time
    static Timeline time;
    static ArrayList<Orb> orbs;
    final String styleString = Style.getStyleString();
    final Font labelsFont = Style.getPixelFont();
    final Color lineColor = Style.getLineColor();

    public PaneSystem() {

        system = new SolarSystem();
        routeControl = new RouteControl(system);

        // Setting the size of the rectangular pane and the styling
        this.setMaxSize(width, height);
        this.setMinSize(width, height);
        this.setStyle(styleString);

        Image frameImage = new Image("src/main/resources/Frame.png");
        ImageView frame = new ImageView(frameImage);
        frame.setFitHeight(height);
        frame.setFitWidth(width);
        this.getChildren().add(frame);

        orbsPane = new Pane();
        PaneDate datePane = new PaneDate();
        datePane.setLayoutX(20);
        datePane.setLayoutY(20);

        centersPane = new PaneCenters();
        this.getChildren().add(centersPane);
        centersPane.setLayoutX(-200);
        centersPane.setLayoutY(20);
        centersPane.toFront();

        infoPane = new PaneInfo();
        this.getChildren().add(infoPane);
        infoPane.setLayoutX(850);
        infoPane.setLayoutY(20);

        // Creating the Orbs
        orbs = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            orbs.add(new Orb());
            orbs.get(i).setRadius(1);
        }

        // Adding the orbs to the pane
        orbsPane.getChildren().addAll(orbs);

        // ---LABELS---
        Text tSun = new Text("sun");
        Text tMercury = new Text("mercury");
        Text tVenus = new Text("venus");
        Text tEarth = new Text("earth");
        Text tMars = new Text("mars");
        Text tJupiter = new Text("jupiter");
        Text tSaturn = new Text("saturn");
        Text tUranus = new Text("uranus");
        Text tNeptune = new Text("neptune");
        Text tMoon = new Text("moon");
        Text tTitan = new Text("titan");
        Text tProbe = new Text("rocket");

        texts = new ArrayList<>();
        texts.add(tSun);
        texts.add(tMercury);
        texts.add(tVenus);
        texts.add(tEarth);
        texts.add(tMars);
        texts.add(tJupiter);
        texts.add(tSaturn);
        texts.add(tUranus);
        texts.add(tNeptune);
        texts.add(tMoon);
        texts.add(tTitan);
        texts.add(tProbe);

        for (Text text : texts) {
            text.setFill(lineColor);
            text.setFont(labelsFont);
        }

        orbsPane.getChildren().addAll(texts);
        orbsPane.getChildren().remove(tMoon);
        orbsPane.getChildren().remove(tTitan);
        // orbsPane.getChildren().remove(tSun);
        this.getChildren().add(orbsPane);
        this.getChildren().add(datePane);


        // Zooming
        this.addEventHandler(ScrollEvent.ANY, (ScrollEvent event) -> {
            zoomFactor *= Math.pow(1.01, event.getDeltaY());
            repaintView();
        });

        // Pausing the simulation
        this.addEventFilter(KeyEvent.KEY_RELEASED, event -> {
            if (event.getCode() == KeyCode.SPACE) {
                pauseTime();
            }
        });

        // Setting first instance of planet positions
        orbs.get(0).setCoordinates(xCenter, yCenter);
        for (int j = 0; j < orbs.size(); j++) {
            orbs.get(j).setCoordinates(
                    xCenter + centerShiftX + system.getObjects()[j].getPosition()[0] * scalar * zoomFactor,
                    yCenter + centerShiftY + system.getObjects()[j].getPosition()[1] * scalar * zoomFactor);
            if (!(j == Data.indMoon || j == Data.indTitan)) {
                positionTextRelativeToOrb(texts.get(j), orbs.get(j));
            }

        }

        // Initializing route points
        for (int j = 1; j < orbs.size(); j++) {

            for (int k = 0; k < 100; k++) {

                Rectangle rectangle = new Rectangle(
                        orbs.get(0).getCenterX(),
                        orbs.get(0).getCenterY(),
                        2, 2);
                rectangle.setFill(lineColor);
                rectangle.setVisible(false);
                rectangle.toBack();

                orbs.get(j).getRoutePoints().add(rectangle);

            }

            orbsPane.getChildren().addAll(orbs.get(j).getRoutePoints());
        }

        // Initializing and running the simulation
        runSimulation();

    }

    private static void repaintView() {
        switch (centerOrb) {
            case 0 -> {
                centerShiftX = 0;
                centerShiftY = 0;
            }
            case 3 -> {
                centerShiftX = -system.getObjects()[Data.indEarth].getPosition()[0] * scalar * zoomFactor;
                centerShiftY = -system.getObjects()[Data.indEarth].getPosition()[1] * scalar * zoomFactor;
            }
            case 6 -> {
                centerShiftX = -system.getObjects()[Data.indSaturn].getPosition()[0] * scalar * zoomFactor;
                centerShiftY = -system.getObjects()[Data.indSaturn].getPosition()[1] * scalar * zoomFactor;
            }
            case 11 -> {
                centerShiftX = -system.getObjects()[Data.indSpaceship].getPosition()[0] * scalar * zoomFactor;
                centerShiftY = -system.getObjects()[Data.indSpaceship].getPosition()[1] * scalar * zoomFactor;
            }
        }

        repaintOrbs();

        displayRoutePoints();
        PaneInfo.updateDistanceStrings();

        infoPane.toFront();
        centersPane.toFront();
    }

    private static void repaintOrbs() {
        for (int j = 0; j < orbs.size(); j++) {
            orbs.get(j).setCoordinates(
                    xCenter + centerShiftX + system.getObjects()[j].getPosition()[0] * scalar * zoomFactor,
                    yCenter + centerShiftY + system.getObjects()[j].getPosition()[1] * scalar * zoomFactor);
            if (!(j == Data.indMoon || j == Data.indTitan)) {
                positionTextRelativeToOrb(texts.get(j), orbs.get(j));
            }
        }
    }

    public static void addRoutePoints() {
        for (int j = 1; j < orbs.size(); j++) {
            orbs.get(j).getRouteCoordinates().add(new double[]{
                    system.getObjects()[j].getPosition()[0],
                    system.getObjects()[j].getPosition()[1]}
            );

            orbsPane.getChildren().remove(orbs.get(j).getRoutePoints().getFirst());
            orbs.get(j).getRoutePoints().removeFirst();

            orbs.get(j).getRoutePoints().add(
                    new Rectangle(
                            xCenter + centerShiftX + orbs.get(j).getRouteCoordinates().getLast()[0] * scalar * zoomFactor,
                            yCenter + centerShiftY + orbs.get(j).getRouteCoordinates().getLast()[1] * scalar * zoomFactor,
                            2, 2)
            );
            orbsPane.getChildren().add(orbs.get(j).getRoutePoints().getLast());
        }
    }

    private static void displayRoutePoints() {

        for (int i = 1; i < orbs.size(); i++) {

            Orb orb = orbs.get(i);
            for (int j = 0; j < orb.getRouteCoordinates().size(); j++) {

                Rectangle point = orb.getRoutePoints().get(j);
                point.setFill(Color.LIMEGREEN);
                point.setOpacity(100 / ((double) orb.getRouteCoordinates().size() + 1) / 100 * (j + 1));

                point.setX(xCenter + centerShiftX + orb.getRouteCoordinates().get(j)[0] * scalar * zoomFactor);
                point.setY(yCenter + centerShiftY + orb.getRouteCoordinates().get(j)[1] * scalar * zoomFactor);

                point.setVisible(showRoute);
                point.toBack();

            }

        }

    }

    static void positionTextRelativeToOrb(Text text, Orb orb) {
        text.setX(orb.getCenterX() + 10);
        text.setY(orb.getCenterY() + 10);
    }

    public static void setRelativeObject(int i) {
        centerOrb = i;
        repaintView();

    }

    public static void pauseTime() {
        if (!isPaused) {
            isPaused = true;
            time.pause();
        } else {
            isPaused = false;
            time.play();
        }
    }

    public static void playTime() {
        isPaused = false;
        time.play();
    }

    public static void playSpeedOnce() {
        timeScalar = 1;
        playTime();
    }

    public static void playSpeedTwice() {
        timeScalar = 2;
        playTime();
    }

    public static void playSpeedThrice() {
        timeScalar = 5;
        playTime();
    }

    public static SolarSystem getSystem() {
        return system;
    }

    private void runSimulation() {
        time = new Timeline();
        time.setCycleCount(Timeline.INDEFINITE);

        KeyFrame frame = new KeyFrame(Duration.seconds(0.2), event -> {

            for (int j = 0; j < (24 * timeScalar * 50); j++) {
                routeControl.checkControl();
                EulerSolver.updatePositions(system);
                if (system.getTimeEngine().getTimeDate()[5] == 2025 && system.getTimeEngine().getTimeDate()[4] == 4) {
                    time.pause();
                    System.out.println("Distance Rocket - Earth: " + system.calculateDistanceEarthRocket());
                    System.out.println("Fuel consumption: " + routeControl.getConsumedFuel());
                    break;
                }
            }

            switch (centerOrb) {
                case 0 -> {
                    centerShiftX = 0;
                    centerShiftY = 0;
                }
                case 3 -> {
                    centerShiftX = -system.getObjects()[Data.indEarth].getPosition()[0] * scalar * zoomFactor;
                    centerShiftY = -system.getObjects()[Data.indEarth].getPosition()[1] * scalar * zoomFactor;
                }
                case 6 -> {
                    centerShiftX = -system.getObjects()[Data.indSaturn].getPosition()[0] * scalar * zoomFactor;
                    centerShiftY = -system.getObjects()[Data.indSaturn].getPosition()[1] * scalar * zoomFactor;
                }
                case 11 -> {
                    centerShiftX = -system.getObjects()[Data.indSpaceship].getPosition()[0] * scalar * zoomFactor;
                    centerShiftY = -system.getObjects()[Data.indSpaceship].getPosition()[1] * scalar * zoomFactor;
                }
            }

            repaintOrbs();

            displayRoutePoints();
            PaneInfo.updateDistanceStrings();

            infoPane.toFront();
            centersPane.toFront();

        });

        time.getKeyFrames().add(frame);
        time.playFromStart();
    }

}
