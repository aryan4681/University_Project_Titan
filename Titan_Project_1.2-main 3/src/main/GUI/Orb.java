package src.main.GUI;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.LinkedList;

public class Orb extends Circle {

    //Orb is a class that will be a basic structure for a planet

    public final double strokeWidth = 2;
    final Color backgroundColor = Style.getBackgroundColor();
    final Color lineColor = Style.getLineColor();
    final RouteQueue<double[]> routeCoordinates = new RouteQueue<>();
    final LinkedList<Rectangle> routePoints = new LinkedList<>();

    public Orb() {
        this.setFill(backgroundColor);
        this.setStrokeWidth(strokeWidth);
        this.setStroke(lineColor);
    }

    public void setCoordinates(double x, double y) {
        this.setCenterX(x);
        this.setCenterY(y);
    }

    public RouteQueue<double[]> getRouteCoordinates() {
        return routeCoordinates;
    }

    public LinkedList<Rectangle> getRoutePoints() {
        return routePoints;
    }

}
