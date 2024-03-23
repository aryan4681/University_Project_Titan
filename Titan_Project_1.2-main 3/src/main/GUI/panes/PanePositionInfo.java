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

public class PanePositionInfo extends Pane {

    static Text distanceValue;
    static Text xValue;
    static Text yValue;
    static Text rotationValue;
    final Font labelsFont = Style.getDateFont();
    final Color lineColor = Style.getLineColor();


    PanePositionInfo() {

        Image image = new Image("src/main/resources/SmallInfoTable.png");
        ImageView background = new ImageView(image);

        background.setFitHeight(300);
        background.setFitWidth(150);

        Text title = new Text("Data:");
        Text distanceTitle = new Text("Distance\nRocket\n-\nSurface:");
        distanceValue = new Text("km");
        xValue = new Text("X: 0 km");
        yValue = new Text("Y: 0 km");
        rotationValue = new Text("Angle: 0");

        title.setFill(lineColor);
        distanceTitle.setFill(lineColor);
        distanceValue.setFill(lineColor);
        distanceValue.setFill(lineColor);
        xValue.setFill(lineColor);
        yValue.setFill(lineColor);
        rotationValue.setFill(lineColor);

        title.setFont(labelsFont);
        distanceTitle.setFont(labelsFont);
        distanceValue.setFont(labelsFont);
        distanceValue.setFont(labelsFont);
        xValue.setFont(labelsFont);
        yValue.setFont(labelsFont);
        rotationValue.setFont(labelsFont);

        distanceTitle.setTextAlignment(TextAlignment.CENTER);
        distanceValue.setTextAlignment(TextAlignment.CENTER);
        distanceValue.setTextAlignment(TextAlignment.CENTER);
        xValue.setTextAlignment(TextAlignment.CENTER);
        yValue.setTextAlignment(TextAlignment.CENTER);
        rotationValue.setTextAlignment(TextAlignment.CENTER);

        VBox distanceContainer = new VBox();
        distanceContainer.setAlignment(Pos.CENTER);
        distanceContainer.setSpacing(10);
        distanceContainer.getChildren().addAll(
                distanceTitle,
                distanceValue,
                new Text(" "),
                xValue,
                yValue,
                rotationValue
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

    public static void updateInfoTexts(){
        double[] currentPosition = PaneLanding.getCurrentPosition();

        xValue.setText("X: " + ((double)((int)(currentPosition[0]*1000)))/1000 +"km");
        yValue.setText("Y: " + ((double)((int)(currentPosition[1]*1000)))/1000 +"km");
        rotationValue.setText("Angle: " + (((double)((int)(currentPosition[2]*1000)))/1000 % 6.283));
        
        double distance = ((double)((int)(Math.sqrt(Math.pow(currentPosition[0], 2) + Math.pow(currentPosition[1], 2))*1000)))/1000;
        distanceValue.setText(distance + "km");
    }

}