package UI;

import Map.Map;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundSize;
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
    private Label time;
    private Label score;
    private Label hp;

    private UIButton pauseButton;

    public HeadPane(Map map, int width, int height) {
        this.map = map;
        this.height = height;
        this.width = width;

        setPrefHeight(height);

        BackgroundFill backgroundFill = new BackgroundFill(Color.BLACK, null, new Insets(-1));
        setBackground(new Background(backgroundFill));
        createLabel();
        createButton();
    }

    private void createLabel() {
        time = new Label("Time: " + map.getTime().countSecond());
        time.setFont(Font.font("Segoe UI Black", FontWeight.BOLD, DEFAULT_FONT_SIZE));
        time.setTextFill(Color.RED);
        time.setLayoutX(30);
        time.setLayoutY(height / 4);

        score = new Label("Score: " + map.getPlayer().getScore().getScore());
        score.setFont(Font.font("Segoe UI Black", FontWeight.BOLD, DEFAULT_FONT_SIZE));
        score.setLayoutX(width / 3);
        score.setLayoutY(height / 4);
        score.setTextFill(Color.RED);

        hp = new Label("Life: " + map.getPlayer().getHP());
        hp.setFont(Font.font("Segoe UI Black", FontWeight.BOLD, DEFAULT_FONT_SIZE));
        hp.setLayoutX(width / 4 * 2.5);
        hp.setLayoutY(height / 4);
        hp.setTextFill(Color.RED);

        getChildren().addAll(time, score, hp);
    }

    private void createButton() {
        pauseButton = new UIButton(133, 34, "PAUSE");
        pauseButton.setLayoutY(height / 4);
        setRightAnchor(pauseButton, 30.0);
        setBottomAnchor(pauseButton, 10.0);
        getChildren().add(pauseButton);
    }

    public void disableAction() {
        pauseButton.setDisable(true);
    }

    public void enableAction() {
        pauseButton.setDisable(false);
    }

    public void resize(int newWidth, int newHeight) {
        width = newWidth;
        height = newHeight;

        setWidth(width);
        setHeight(height);

        time.setFont(Font.font("Segoe UI Black", FontWeight.BOLD, DEFAULT_FONT_SIZE));
        time.setLayoutY(height / 4);

        score.setFont(Font.font("Segoe UI Black", FontWeight.BOLD, DEFAULT_FONT_SIZE));
        score.setLayoutX(width / 3);
        score.setLayoutY(height / 4);

        hp.setFont(Font.font("Segoe UI Black", FontWeight.BOLD, DEFAULT_FONT_SIZE));
        hp.setLayoutX(width / 4 * 2.5);
        hp.setLayoutY(height / 4);
    }

    public void update() {
        time.setText("Time: " + (map.getMaxTime() - map.getTime().countSecond()));
        score.setText("Score: " + map.getPlayer().getScore().getScore());
        hp.setText("HP: " + map.getPlayer().getHP());
    }

    public boolean isActionEnable() {
        return !pauseButton.isDisable();
    }

    public UIButton getPauseButton() {
        return pauseButton;
    }
}
