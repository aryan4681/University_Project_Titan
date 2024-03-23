package src.main.GUI.panes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import src.main.GUI.ButtonHandler;
import src.main.GUI.Style;

public class PaneCenters extends Pane {

    final Font labelsFont = Style.getDateFont();
    final Color lineColor = Style.getLineColor();


    public PaneCenters() {
        Text tCenter = new Text("Center on:");
        tCenter.setFill(lineColor);
        tCenter.setFont(labelsFont);

        Image image = new Image("src/main/resources/TemplateButton.png");

        ImageView suView = new ImageView(image);
        ImageView eaView = new ImageView(image);
        ImageView saView = new ImageView(image);
        ImageView prView = new ImageView(image);

        suView.setFitHeight(37.5);
        eaView.setFitHeight(37.5);
        saView.setFitHeight(37.5);
        prView.setFitHeight(37.5);
        suView.setFitWidth(150);
        eaView.setFitWidth(150);
        saView.setFitWidth(150);
        prView.setFitWidth(150);

        Button sunButton = new Button();
        sunButton.setGraphic(suView);
        sunButton.setPadding(Insets.EMPTY);

        Button earthButton = new Button();
        earthButton.setGraphic(eaView);
        earthButton.setPadding(Insets.EMPTY);

        Button saturButton = new Button();
        saturButton.setGraphic(saView);
        saturButton.setPadding(Insets.EMPTY);

        Button probeButton = new Button();
        probeButton.setGraphic(prView);
        probeButton.setPadding(Insets.EMPTY);

        StackPane apane = new StackPane();
        StackPane bpane = new StackPane();
        StackPane cpane = new StackPane();
        StackPane dpane = new StackPane();

        Text ta = new Text("Sun");
        Text tb = new Text("Earth");
        Text tc = new Text("Saturn");
        Text td = new Text("Rocket");

        ta.setFill(lineColor);
        tb.setFill(lineColor);
        tc.setFill(lineColor);
        td.setFill(lineColor);

        ta.setFont(labelsFont);
        tb.setFont(labelsFont);
        tc.setFont(labelsFont);
        td.setFont(labelsFont);

        apane.getChildren().addAll(sunButton, ta);
        bpane.getChildren().addAll(earthButton, tb);
        cpane.getChildren().addAll(saturButton, tc);
        dpane.getChildren().addAll(probeButton, td);

        sunButton.setOnMouseClicked(e -> ButtonHandler.sunButton());
        earthButton.setOnMouseClicked(e -> ButtonHandler.earthButton());
        saturButton.setOnMouseClicked(e -> ButtonHandler.saturnButton());
        probeButton.setOnMouseClicked(e -> ButtonHandler.probeButton());

        Text tSimulation = new Text("  Simulation\nmanagement:");
        tSimulation.setFill(lineColor);
        tSimulation.setFont(labelsFont);

        Image imagePause = new Image("src/main/resources/PauseSpeedButton.png");
        Image imageOnce = new Image("src/main/resources/OnceSpeedButton.png");
        Image imageTwice = new Image("src/main/resources/TwiceSpeedButton.png");
        Image imageThrice = new Image("src/main/resources/ThriceSpeedButton.png");

        ImageView viewPause = new ImageView(imagePause);
        ImageView viewOnce = new ImageView(imageOnce);
        ImageView viewTwice = new ImageView(imageTwice);
        ImageView viewThrice = new ImageView(imageThrice);

        viewPause.setFitHeight(37.5);
        viewOnce.setFitHeight(37.5);
        viewTwice.setFitHeight(37.5);
        viewThrice.setFitHeight(37.5);
        viewPause.setFitWidth(150);
        viewOnce.setFitWidth(150);
        viewTwice.setFitWidth(150);
        viewThrice.setFitWidth(150);

        Button buttonPause = new Button();
        Button buttonOnce = new Button();
        Button buttonTwice = new Button();
        Button buttonThrice = new Button();

        buttonPause.setGraphic(viewPause);
        buttonOnce.setGraphic(viewOnce);
        buttonTwice.setGraphic(viewTwice);
        buttonThrice.setGraphic(viewThrice);

        buttonPause.setPadding(Insets.EMPTY);
        buttonOnce.setPadding(Insets.EMPTY);
        buttonTwice.setPadding(Insets.EMPTY);
        buttonThrice.setPadding(Insets.EMPTY);

        buttonPause.setOnMouseClicked(e -> PaneSystem.pauseTime());
        buttonOnce.setOnMouseClicked(e -> PaneSystem.playSpeedOnce());
        buttonTwice.setOnMouseClicked(e -> PaneSystem.playSpeedTwice());
        buttonThrice.setOnMouseClicked(e -> PaneSystem.playSpeedThrice());


        VBox container = new VBox();
        container.setAlignment(Pos.CENTER);
        container.setSpacing(30);
        container.getChildren().addAll(tCenter,
                apane,
                bpane,
                cpane,
                dpane,
                tSimulation,
                buttonPause,
                buttonOnce,
                buttonTwice,
                buttonThrice);
        this.getChildren().add(container);
    }

}
