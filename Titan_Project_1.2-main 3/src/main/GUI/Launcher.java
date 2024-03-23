package src.main.GUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import src.main.GUI.panes.PaneLanding;
import src.main.GUI.panes.PaneStart;
import src.main.GUI.panes.PaneSystem;

public class Launcher extends Application {

    // Launcher is the base Application that we'll run in order to show GUI. Launch
    // it to see the GUI.

    static BorderPane mainPane;
    static PaneSystem systemPane;
    static PaneStart startPane;
    static PaneLanding landingPane;
    static Scene scene;
    static boolean isGUILaunched = false;

    public static void main(String[] args) {
        launch(args);
    }

    public static boolean isGUILaunched() {
        return isGUILaunched;
    }

    @Override
    public void start(Stage stage) {

        isGUILaunched = true;

        // Main settings
        stage.setTitle("7 Wonders of Titan - SpaceshipOpenController Launcher");
        stage.setMaximized(true);
        stage.setResizable(true);
        stage.setMinWidth(1000);
        stage.setMinHeight(750);

        stage.getIcons().add(new Image("src/main/resources/Logo-Icon.png"));

        // Closing on ESC
        stage.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
            if (KeyCode.ESCAPE == event.getCode()) {
                stage.close();
                System.exit(0);
            }
        });

        // Closing on x
        stage.setOnCloseRequest(event -> {
            stage.close();
            System.exit(0);
        });

        // ---PANES---
        mainPane = new BorderPane();
        startPane = new PaneStart();

        // Scene
        scene = new Scene(startPane);
        stage.setScene(scene);
        scene.setFill(Color.BLACK);

        // Making it visible
        stage.show();
    }

}