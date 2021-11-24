package UI;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.FileInputStream;

public class GameOverPane extends AnchorPane {
    private final int DEFAULT_WIDTH = 100;
    private final int DEFAULT_HEIGHT = 100;

    private Panel mainPanel;

    public GameOverPane(int x, int y, int width, int height) {
        setLayoutX(x);
        setLayoutY(y);
        mainPanel = new Panel(width, height, Color.GRAY);
        getChildren().add(mainPanel);

        Label gameOverLabel = new Label("GAME OVER");
        try {
            gameOverLabel.setFont(Font.loadFont(new FileInputStream("src/main/resources/Font/kenvector_future.ttf"), 25));
        } catch (Exception e) {

        }
        gameOverLabel.setTextFill(Color.ORANGE);

        getChildren().add(gameOverLabel);
    }

}
