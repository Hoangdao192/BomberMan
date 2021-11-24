package UI;

import Map.Map;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.awt.*;

public class HeadPane extends AnchorPane {
    private final int DEFAULT_FONT_SIZE = 25;
    private final int DEFAULT_WIDTH = 800;
    private final int DEFAULT_HEIGHT = 70;

    private Map map;
    private int width;
    private int height;
    private Rectangle background;
    private Label time;
    private Label score;
    private Label hp;

    public HeadPane(Map map, int width, int height) {
        this.map = map;
        this.height = height;
        this.width = width;

        setWidth(this.width);
        setHeight(height);

        background = new Rectangle(0, 0, width, height);

        time = new Label("Time: " + map.getTime().countSecond());
        time.setFont(Font.font("Segoe UI Black", FontWeight.BOLD, DEFAULT_FONT_SIZE));
        time.setLayoutX(30);
        time.setLayoutY(height / 4);
        time.setTextFill(Color.RED);

        score = new Label("Score: " + map.getPlayer().getScore().getScore());
        score.setFont(Font.font("Segoe UI Black", FontWeight.BOLD, DEFAULT_FONT_SIZE));
        score.setLayoutX(width / 3);
        score.setLayoutY(height / 4);
        score.setTextFill(Color.RED);

        hp = new Label("Life: " + map.getPlayer().getHP());
        hp.setFont(Font.font("Segoe UI Black", FontWeight.BOLD, DEFAULT_FONT_SIZE));
        hp.setLayoutX(width / 4 * 3);
        hp.setLayoutY(height / 4);
        hp.setTextFill(Color.RED);

        getChildren().addAll(background, time, score, hp);
    }

    public void resize(int newWidth, int newHeight) {
        width = newWidth;
        height = newHeight;

        setWidth(width);
        setHeight(height);

        background.setWidth(width);
        background.setHeight(height);

        time.setFont(Font.font("Segoe UI Black", FontWeight.BOLD, DEFAULT_FONT_SIZE));
        time.setLayoutX(30);
        time.setLayoutY(height / 4);

        score.setFont(Font.font("Segoe UI Black", FontWeight.BOLD, DEFAULT_FONT_SIZE));
        score.setLayoutX(width / 3);
        score.setLayoutY(height / 4);

        hp.setFont(Font.font("Segoe UI Black", FontWeight.BOLD, DEFAULT_FONT_SIZE));
        hp.setLayoutX(width / 4 * 3);
        hp.setLayoutY(height / 4);
    }

    public void update() {
        time.setText("Time: " + (map.getMaxTime() - map.getTime().countSecond()));
        score.setText("Score: " + map.getPlayer().getScore().getScore());
        hp.setText("HP: " + map.getPlayer().getHP());
    }
}