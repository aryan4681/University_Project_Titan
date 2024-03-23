package src.main.GUI.panes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import src.main.GUI.ButtonHandler;
import src.main.GUI.Style;

public class PaneStart extends StackPane {

    static Button flightButton;
    static Button landingButton;
    static ImageView flightView;
    static ImageView landingView;
    final double width = 800;
    final double height = 750;
    final String styleString = Style.getStyleString();
    final Font labelsFont = Style.getPixelFont();
    final Color lineColor = Style.getLineColor();


    public PaneStart() {
        this.setMaxSize(width, height);
        this.setMinSize(width, height);
        this.setStyle(styleString);
        this.setAlignment(Pos.CENTER);

        flightView = new ImageView(new Image("src/main/resources/FlightButton.png"));
        landingView = new ImageView(new Image("src/main/resources/LandingButton.png"));
        Image logo = new Image("src/main/resources/Logo-Color.png");

        flightButton = new Button();
        flightButton.setGraphic(flightView);
        flightButton.setPadding(Insets.EMPTY);
        landingButton = new Button();
        landingButton.setGraphic(landingView);
        landingButton.setPadding(Insets.EMPTY);

        ImageView logoView = new ImageView(logo);

        Text tCenter = new Text("  T h e   R o c k e t   L a u n c h e r\nb y   7   W o n d e r s   O f   T i t a n");
        tCenter.setFill(lineColor);
        tCenter.setFont(labelsFont);

        flightView.setFitHeight(50);
        flightView.setFitWidth(200);
        landingView.setFitHeight(50);
        landingView.setFitWidth(200);
        logoView.setFitHeight(150);
        logoView.setFitWidth(150);

        VBox buttonsContainer = new VBox();
        HBox onlyButtons = new HBox();


        buttonsContainer.setAlignment(Pos.CENTER);
        buttonsContainer.setSpacing(50);
        onlyButtons.setAlignment(Pos.CENTER);
        onlyButtons.setSpacing(25);

        onlyButtons.getChildren().addAll(flightButton, landingButton);

        buttonsContainer.getChildren().addAll(logoView, tCenter, onlyButtons);
        this.getChildren().add(buttonsContainer);

        flightButton.setOnMouseClicked(e -> ButtonHandler.flightButtonClicked());
        landingButton.setOnMouseClicked(e -> ButtonHandler.landingButtonClicked());

    }

}