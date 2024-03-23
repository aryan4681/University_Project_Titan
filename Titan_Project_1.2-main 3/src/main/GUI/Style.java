package src.main.GUI;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Style {

    //This is a class in which I keep styling elements

    static final String styleString = "-fx-background-color: black;";
    static final Color backgroundColor = Color.BLACK;
    static final Color lineColor = Color.LIMEGREEN;
    static final Font pixelFont = Font.loadFont("file:src/main/resources/COMMP___.TTF", 14);
    static final Font dateFont = Font.loadFont("file:src/main/resources/COMMP___.TTF", 18);
    static final Image templateButtonImage = new Image("src/main/resources/TemplateButton.png");
    static final ImageView templateButton = new ImageView(templateButtonImage);

    public static String getStyleString() {
        return styleString;
    }

    public static Color getBackgroundColor() {
        return backgroundColor;
    }

    public static Color getLineColor() {
        return lineColor;
    }

    public static Font getPixelFont() {
        return pixelFont;
    }

    public static Font getDateFont() {
        return dateFont;
    }

    public static ImageView getTemplateButton() {
        return templateButton;
    }
}
