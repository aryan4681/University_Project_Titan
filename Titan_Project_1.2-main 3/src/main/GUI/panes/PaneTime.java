package src.main.GUI.panes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import src.main.GUI.Style;

public class PaneTime extends Pane {

    final Font labelsFont = Style.getDateFont();
    final Color lineColor = Style.getLineColor();


    public PaneTime() {

        Text tSimulation = new Text("      Time\nmanagement:");
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

        buttonPause.setOnMouseClicked(e -> PaneLanding.pauseTime());
        buttonOnce.setOnMouseClicked(e -> PaneLanding.playSpeedOnce());
        buttonTwice.setOnMouseClicked(e -> PaneLanding.playSpeedTwice());
        buttonThrice.setOnMouseClicked(e -> PaneLanding.playSpeedThrice());


        VBox container = new VBox();
        container.setAlignment(Pos.CENTER);
        container.setSpacing(30);
        container.getChildren().addAll(
                tSimulation,
                buttonPause,
                buttonOnce,
                buttonTwice,
                buttonThrice);
        this.getChildren().add(container);
    }

}