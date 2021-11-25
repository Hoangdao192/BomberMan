package UI;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Panel extends AnchorPane {
    public final int CORNER_RADIUS = 30;

    public Rectangle contentField;
    public Rectangle background;
    private Color borderColor = Color.rgb(153, 153, 153);
    private Color backgroundColor = Color.rgb(255, 255, 255);
    private Color contentColor = Color.rgb(253, 253, 253);

    public Panel(int width, int height) {
        setWidth(width);
        setHeight(height);

        background = new Rectangle(0, 0, width, height);
        background.setArcWidth(8);
        background.setArcHeight(8);
        background.setFill(backgroundColor);
        background.setStrokeWidth(2);
        background.setStroke(borderColor);
        getChildren().add(background);

        contentField = new Rectangle(3, 3, width - 6, height - 6);
        contentField.setArcWidth(4);
        contentField.setArcHeight(4);
        contentField.setFill(contentColor);
        getChildren().add(contentField);
    }

    public void setColor(Color mainColor, Color backgroundColor, Color contentColor) {
        this.borderColor = mainColor;
        this.backgroundColor = backgroundColor;
        this.contentColor = contentColor;
        background.setFill(backgroundColor);
        background.setStroke(borderColor);
        contentField.setFill(contentColor);
    }
}
