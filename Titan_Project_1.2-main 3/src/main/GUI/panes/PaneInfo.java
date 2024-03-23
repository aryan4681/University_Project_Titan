package src.main.GUI.panes;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import src.main.GUI.Style;

public class PaneInfo extends Pane {

    static Text distanceEarth;
    static Text distanceTitan;
    final Font labelsFont = Style.getDateFont();
    final Color lineColor = Style.getLineColor();


    PaneInfo() {

        Image image = new Image("src/main/resources/InfoTable.png");
        ImageView background = new ImageView(image);

        background.setFitHeight(557.5);
        background.setFitWidth(150);

        Text title = new Text("Data:");
        Text earthTitle = new Text("Distance\nEarth\n-\nRocket:");
        Text titanTitle = new Text("Distance\nTitan\n-\nRocket:");
        distanceEarth = new Text((int) PaneSystem.getSystem().calculateDistanceEarthRocket() + "km");
        distanceTitan = new Text((int) PaneSystem.getSystem().calculateDistanceEarthRocket() + "km");

        title.setFill(lineColor);
        earthTitle.setFill(lineColor);
        titanTitle.setFill(lineColor);
        distanceEarth.setFill(lineColor);
        distanceTitan.setFill(lineColor);

        title.setFont(labelsFont);
        earthTitle.setFont(labelsFont);
        titanTitle.setFont(labelsFont);
        distanceEarth.setFont(labelsFont);
        distanceTitan.setFont(labelsFont);

        earthTitle.setTextAlignment(TextAlignment.CENTER);
        titanTitle.setTextAlignment(TextAlignment.CENTER);
        distanceEarth.setTextAlignment(TextAlignment.CENTER);
        distanceTitan.setTextAlignment(TextAlignment.CENTER);

        VBox distanceContainer = new VBox();
        distanceContainer.setAlignment(Pos.CENTER);
        distanceContainer.setSpacing(50);
        distanceContainer.getChildren().addAll(
                earthTitle,
                distanceEarth,
                titanTitle,
                distanceTitan
        );

        StackPane holder = new StackPane();
        holder.setAlignment(Pos.CENTER);
        holder.getChildren().add(background);
        holder.getChildren().add(distanceContainer);


        VBox container = new VBox();
        container.setAlignment(Pos.CENTER);
        container.setSpacing(30);
        container.getChildren().addAll(
                title,
                holder
        );

        this.getChildren().add(container);
    }

    public static void updateDistanceStrings() {

        distanceEarth.setText((int) PaneSystem.getSystem().calculateDistanceEarthRocket() + "km");
        distanceTitan.setText((int) PaneSystem.getSystem().calculateDistanceTitanRocket() + "km");

    }

}
