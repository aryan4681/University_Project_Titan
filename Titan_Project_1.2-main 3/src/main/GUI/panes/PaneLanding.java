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
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import src.main.calculations.landingFeedbackController.FeedbackController;
import src.main.calculations.landingOpenController.OpenController;
import src.main.calculations.SolarSystem;
import src.main.GUI.Probe;
import src.main.GUI.Style;

public class PaneLanding extends Pane {

    //Managing route displayal
    static final double width = 800;
    static final double height = 750;
    static final double xCenter = width / 2;
    static final double yCenter = 325*2;
    static final double initialScalar = 2.5;
    static final double scalar = initialScalar;
    static double zoomFactor = 1;
    static double[] currentPosition = {0, 0, 0};
    
    //Managing time
    static Timeline time;
    static int timeScalar = 1;
    public static boolean isPaused = false;

    //Style
    final String styleString = Style.getStyleString();
    final Font labelsFont = Style.getPixelFont();
    final Color lineColor = Style.getLineColor();

    //Elements
    final Probe probe = new Probe(50);
    final Text text = new Text("Landing Module");
    final ImageView spotOfLanding = new ImageView(new Image("src/main/resources/Ground.png"));
    final ImageView landscape = new ImageView(new Image("src/main/resources/Landscape.png"));

    final ImageView frame;
    final Text yText;
    static PaneTime timePane;
    static PanePositionInfo infoPane;

    //Simulation OpenController
    SolarSystem system = new SolarSystem();
    OpenController controller = new OpenController(system);
// FeedbackController controller = new FeedbackController();

    public PaneLanding() {

        // Setting the size of the rectangular pane and the styling
        this.setMaxSize(width, height);
        this.setMinSize(width, height);
        this.setStyle(styleString);

        Image frameImage = new Image("src/main/resources/FrameClear.png");
        frame = new ImageView(frameImage);
        frame.setFitHeight(height);
        frame.setFitWidth(width);
        this.getChildren().add(frame);

        Image frameImageBack = new Image("src/main/resources/Frame.png");
        ImageView frameBack = new ImageView(frameImageBack);
        frameBack.setFitHeight(height);
        frameBack.setFitWidth(width);
        this.getChildren().add(frameBack);
        
        probe.setCenterX(xCenter);
        probe.setCenterY(yCenter);
        probe.setRotate(0);
        probe.setRadius(zoomFactor*10);
        this.getChildren().add(probe);

        resizeSpotOfLanding();
        this.getChildren().add(landscape);
        this.getChildren().add(spotOfLanding);

        text.setFill(lineColor);
        text.setFont(labelsFont);
        setTextRelativeToProbe();
        this.getChildren().add(text);

        Line ground = new Line();
        ground.setStartX(-width);
        ground.setEndX(width + width);
        ground.setStartY(yCenter);
        ground.setEndY(yCenter);
        ground.setStroke(lineColor);
        ground.toBack();
        this.getChildren().add(ground);

        Line xAxis = new Line();
        xAxis.getStrokeDashArray().addAll(8d, 20d);
        xAxis.setStartX(xCenter);
        xAxis.setEndX(xCenter);
        xAxis.setStartY(5);
        xAxis.setEndY(height);
        xAxis.setStroke(lineColor);
        xAxis.toBack();
        xAxis.setOpacity(0.5);
        this.getChildren().add(xAxis);

        Line yAttitude = new Line();
        yAttitude.getStrokeDashArray().addAll(8d, 20d);
        yAttitude.setStartX(5);
        yAttitude.setEndX(width);
        yAttitude.setStartY(height/2);
        yAttitude.setEndY(height/2);
        yAttitude.setStroke(lineColor);
        yAttitude.toBack();
        yAttitude.setOpacity(0.5);
        this.getChildren().add(yAttitude);

        Text xText = new Text("x = 0km");
        xText.setFill(lineColor);
        xText.setFont(labelsFont);
        xText.setLayoutX(xCenter + 10);
        xText.setLayoutY(25);
        this.getChildren().add(xText);

        double yValue = (Math.abs(height/2 - yCenter))/scalar;
        yText = new Text("y = " + yValue + "km");
        yText.setFill(lineColor);
        yText.setFont(labelsFont);
        yText.setLayoutX(20);
        yText.setLayoutY(height/2 - 10);
        this.getChildren().add(yText);

        timePane = new PaneTime();
        this.getChildren().add(timePane);
        timePane.setLayoutX(-200);
        timePane.setLayoutY(150);
        timePane.toFront();

        infoPane = new PanePositionInfo();
        this.getChildren().add(infoPane);
        infoPane.setLayoutX(850);
        infoPane.setLayoutY(140);

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

        runSimulation();

    }

    private void setTextRelativeToProbe(){
        text.setX(probe.getCenterX() + probe.getRadius());
        text.setY(probe.getCenterY() - probe.getRadius());
    }

    private void runSimulation(){
        time = new Timeline();
        time.setCycleCount(Timeline.INDEFINITE);
    
        KeyFrame frame = new KeyFrame(Duration.seconds(0.1), event -> updateView());

        time.getKeyFrames().add(frame);
        time.playFromStart();
    }

    private void updateView(){
        for(int i = 0; i < timeScalar; i++){
        if(!controller.nextStepExists()){
            System.out.println("Simulation finished");
            time.pause();
            return;
        }
        currentPosition = controller.getNextStep();}
        
        PanePositionInfo.updateInfoTexts();
        repaintView();
    }

    private void repaintView(){
        probe.setRadius(zoomFactor*10);
        probe.setCenterX(xCenter + currentPosition[0]*scalar*zoomFactor);
        probe.setCenterY(yCenter - currentPosition[1]*scalar*zoomFactor);
        probe.setRotate(-Math.toDegrees(currentPosition[2]));

        resizeSpotOfLanding();

        landscape.toFront();
        spotOfLanding.toFront();
        probe.toFront();
        frame.toFront();
        updateYText();
        setTextRelativeToProbe();
    }

    private void updateYText(){
        double yValue = ((double) ((int) (Math.abs(height/2 - yCenter)/(scalar*zoomFactor)*1000)))/1000;
        yText.setText("y = " + yValue + "km");
    }

    private void resizeSpotOfLanding(){
        spotOfLanding.setFitHeight(probe.getRadius()*2);
        spotOfLanding.setFitWidth(probe.getRadius()*2);
        spotOfLanding.setX(xCenter - probe.getRadius());
        spotOfLanding.setY(yCenter - probe.getRadius()*1.01);

        landscape.setFitHeight(probe.getRadius());
        landscape.setFitWidth(probe.getRadius()/15*5000);
        landscape.setX(xCenter- probe.getRadius()/30*5000);
        landscape.setY(yCenter - probe.getRadius()/2);

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

    public static double[] getCurrentPosition(){
        return currentPosition;
    }



}