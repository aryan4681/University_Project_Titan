package src.main.GUI;

import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import src.main.GUI.panes.PaneLanding;
import src.main.GUI.panes.PaneSystem;

public class ButtonHandler {

    public static void flightButtonClicked() {
        Launcher.scene.setRoot(Launcher.mainPane);
        Launcher.systemPane = new PaneSystem();
        Launcher.mainPane.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

        Image frameImage = new Image("src/main/resources/Frame.png");
        ImageView frame = new ImageView(frameImage);
        frame.setFitHeight(1000);
        frame.setFitWidth(1600);
        frame.setX(-20);
        frame.setY(-20);
        frame.setOpacity(0.7);
        Launcher.mainPane.getChildren().add(frame);

        Launcher.mainPane.setCenter(Launcher.systemPane);
    }

    public static void landingButtonClicked() {
        Launcher.scene.setRoot(Launcher.mainPane);
        Launcher.mainPane.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        Launcher.landingPane = new PaneLanding();

        Image frameImage = new Image("src/main/resources/Frame.png");
        ImageView frame = new ImageView(frameImage);
        frame.setFitHeight(1000);
        frame.setFitWidth(1600);
        frame.setX(-20);
        frame.setY(-20);
        frame.setOpacity(0.7);
        Launcher.mainPane.getChildren().add(frame);

        Launcher.mainPane.setCenter(Launcher.landingPane);
    }

    public static void sunButton() {
        PaneSystem.setRelativeObject(0);
    }

    public static void earthButton() {
        PaneSystem.setRelativeObject(3);
    }

    public static void saturnButton() {
        PaneSystem.setRelativeObject(6);
    }

    public static void probeButton() {
        PaneSystem.setRelativeObject(11);
    }

}
