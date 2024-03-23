package src.main.GUI;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class Probe extends Circle {

    final double xOrigin;
    final double yOrigin;
    final double innerSize;

    public Probe(double radius) {
        super(radius);
        xOrigin = radius / 2;
        yOrigin = radius / 2;
        innerSize = radius;
        Image image = new Image("src/main/resources/ModuleOfLanding.png");
        setFill(new ImagePattern(image));
        setStroke(null);
    }
}