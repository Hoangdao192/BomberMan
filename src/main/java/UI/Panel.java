package UI;

import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Panel extends AnchorPane {
    public final int CORNER_RADIUS = 30;

    public Rectangle contentField;
    public Rectangle background;

    public Panel(int width, int height, Color color) {
        setWidth(width);
        setHeight(height);

        background = new Rectangle(0, 0, width, height);
        background.setArcWidth(15);
        background.setArcHeight(15);
        background.setFill(Color.rgb(255, 255, 255));
        background.setStrokeWidth(2);
        background.setStroke(Color.rgb(155, 155, 155));
        getChildren().add(background);

        contentField = new Rectangle(2, 2, width - 4, height - 4);
        contentField.setArcWidth(10);
        contentField.setArcHeight(10);
        contentField.setFill(Color.rgb(238, 238, 238));
        getChildren().add(contentField);
    }
}
