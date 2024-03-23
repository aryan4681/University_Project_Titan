package src.main.GUI.panes;

import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import src.main.calculations.SolarSystem;
import src.main.GUI.Style;

public class PaneDate extends StackPane {

    static final SolarSystem system = PaneSystem.getSystem();
    static final String dateString = 0 + String.valueOf(system.getTimeEngine().timeDate[3]) + "-" + 0 + system.getTimeEngine().timeDate[4] + "-" + system.getTimeEngine().timeDate[5];
    static Text dateText;
    final String styleString = Style.getStyleString();
    final Font labelsFont = Style.getDateFont();
    final Color lineColor = Style.getLineColor();
    final ImageView templateButton = Style.getTemplateButton();

    PaneDate() {
        this.setMaxSize(150, 37.5);
        this.setMinSize(150, 37.5);
        this.setStyle(styleString);

        templateButton.setFitHeight(37.5);
        templateButton.setFitWidth(150);


        dateText = new Text(dateString);
        dateText.setFill(lineColor);
        dateText.setFont(labelsFont);

        this.setAlignment(Pos.CENTER);
        this.getChildren().add(templateButton);
        this.getChildren().add(dateText);
    }

    public static void updateDateString() {
        int day = system.getTimeEngine().getTimeDate()[3];
        int month = system.getTimeEngine().getTimeDate()[4];
        int year = system.getTimeEngine().getTimeDate()[5];

        String updateString = "";

        if (day < 10) {
            updateString += 0;
        }

        updateString += day + "-";

        if (month < 10) {
            updateString += 0;
        }

        updateString += month + "-" + year;

        dateText.setText(updateString);

    }

}
