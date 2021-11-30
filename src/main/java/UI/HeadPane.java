package UI;

import Map.Map;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import Component.Time;

import java.awt.*;

public class HeadPane extends AnchorPane {
    private final int DEFAULT_FONT_SIZE = 25;
    private final int DEFAULT_WIDTH = 800;
    private final int DEFAULT_HEIGHT = 70;

    private Map map;
    private int width;
    private int height;
    private Time myTime;
    private Label time;
    private Label score;
    private Label hp;
    private Label alert;

    private boolean transfer = false;

    public HeadPane(Map map, int width, int height) {
        this.map = map;
        this.height = height;
        this.width = width;
        this.myTime = new Time();

        setPrefHeight(height);

        BackgroundFill backgroundFill = new BackgroundFill(Color.BLACK, null, new Insets(-1));
        setBackground(new Background(backgroundFill));
        AnchorPane blankPane = new AnchorPane();
        //setLeftAnchor(blankPane, 0.0);
        //setRightAnchor(blankPane, 0.0);
        getChildren().add(blankPane);

        time = new Label("Time: " + map.getTime().countSecond());
        time.setFont(Font.font("Segoe UI Black", FontWeight.BOLD, DEFAULT_FONT_SIZE));
        time.setTextFill(Color.RED);
        time.setLayoutX(30);
        DoubleProperty doubleProperty = new SimpleDoubleProperty(10);
        //time.layoutXProperty().bind(doubleProperty);
        time.setLayoutY(height / 4);


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

        alert = new Label("Congratulation");
        alert.setFont(Font.font("Segoe UI Black", FontWeight.BOLD, DEFAULT_FONT_SIZE));
        alert.setLayoutX(width / 10 * 4);
        alert.setLayoutY(height / 4);
        alert.setTextFill(Color.RED);

        getChildren().addAll(time, score, hp);
    }

    public void resize(int newWidth, int newHeight) {
        width = newWidth;
        height = newHeight;

        //setWidth(width);
        setHeight(height);

        time.setFont(Font.font("Segoe UI Black", FontWeight.BOLD, DEFAULT_FONT_SIZE));
        //time.setLayoutX(30);
        //time.setLayoutY(height / 4);

        score.setFont(Font.font("Segoe UI Black", FontWeight.BOLD, DEFAULT_FONT_SIZE));
        //score.setLayoutX(width / 3);
        score.setLayoutY(height / 4);

        hp.setFont(Font.font("Segoe UI Black", FontWeight.BOLD, DEFAULT_FONT_SIZE));
        //hp.setLayoutX(width / 4 * 3);
        hp.setLayoutY(height / 4);
    }

    public boolean isTransfer() {
        return transfer;
    }

    public void setTransfer(boolean transfer) {
        this.transfer = transfer;
    }

    public void reset() {
        if (!transfer) {
            getChildren().remove(alert);
            getChildren().addAll(score, time, hp);
        } else {
            myTime.reset();
            getChildren().removeAll(score, time, hp);
            getChildren().add(alert);
        }
    }

    public void update() {
        if (!transfer) {
            time.setText("Time: " + (map.getMaxTime() - map.getTime().countSecond()));
            score.setText("Score: " + map.getPlayer().getScore().getScore());
            hp.setText("HP: " + map.getPlayer().getHP());
        } else {
            alert.setText("Congratulation");
            if (myTime.countSecond() >= 5) {
                alert.setText("Next level");
            }
        }
    }

    //  GETTER, SETTER

}
