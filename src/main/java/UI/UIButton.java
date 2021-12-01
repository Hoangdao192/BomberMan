package UI;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.File;
import java.io.FileInputStream;

public class UIButton extends Button {
    public static final int GREEN_1 = 1;
    public static final int GREEN_2 = 2;
    public static final int GREEN_3 = 3;
    public static final int BLUE_1 = 4;
    public static final int BLUE_2 = 5;
    public static final int BLUE_3 = 6;
    public static final int RED_1 = 7;
    public static final int RED_2 = 8;
    public static final int YELLOW_1 = 9;
    public static final int YELLOW_2 = 10;
    public static final int YELLOW_3 = 11;

    private String idleImagePath;
    private String pressedImagePath;
    private Image idleImage = new Image(
            new File("src/main/resources/Graphic/UI/Button/button_green_idle.png").toURI().toString());
    private Image pressedImage = new Image(
            new File("src/main/resources/Graphic/UI/Button/button_green_pressed.png").toURI().toString());
    private BackgroundImage backgroundImageIdle;
    private BackgroundImage backgroundImagePressed;
    private BackgroundSize backgroundSize;
    private Background background;

    private double width;
    private double height;

    public UIButton(double width, double height, String text) {
        setText(text);
        setTextFill(Color.GREEN);
        try {
            setFont(Font.loadFont(
                    new FileInputStream("src/main/resources/Font/kenvector_future_thin.ttf"),
                    20 * height / 49));
        } catch (Exception e) {}

        this.width = width;
        this.height = height;
        setPrefWidth(width);
        setPrefHeight(height);
        setStyle(1);
        setStyleIdle();
        createEventHandle();
    }

    public void setStyle(int style) {
        switch (style) {
            case GREEN_1: {
                idleImagePath = "src/main/resources/Graphic/UI/Button/green_button_style_1_idle.png";
                pressedImagePath = "src/main/resources/Graphic/UI/Button/green_button_style_1_pressed.png";
                break;
            }
            case GREEN_2: {
                idleImagePath = "src/main/resources/Graphic/UI/Button/green_button_style_2_idle.png";
                pressedImagePath = "src/main/resources/Graphic/UI/Button/green_button_style_2_pressed.png";
                break;
            }
            case GREEN_3: {
                idleImagePath = "src/main/resources/Graphic/UI/Button/green_button_style_3_idle.png";
                pressedImagePath = "src/main/resources/Graphic/UI/Button/green_button_style_3_pressed.png";
                break;
            }
            case BLUE_1: {
                idleImagePath = "src/main/resources/Graphic/UI/Button/blue_button_style_1_idle.png";
                pressedImagePath = "src/main/resources/Graphic/UI/Button/blue_button_style_1_pressed.png";
                break;
            }
            case BLUE_2: {
                idleImagePath = "src/main/resources/Graphic/UI/Button/blue_button_style_2_idle.png";
                pressedImagePath = "src/main/resources/Graphic/UI/Button/blue_button_style_2_pressed.png";
                break;
            }
            case BLUE_3: {
                idleImagePath = "src/main/resources/Graphic/UI/Button/blue_button_style_3_idle.png";
                pressedImagePath = "src/main/resources/Graphic/UI/Button/blue_button_style_3_pressed.png";
                break;
            }
            case RED_1: {
                idleImagePath = "src/main/resources/Graphic/UI/Button/red_button_style_1_idle.png";
                pressedImagePath = "src/main/resources/Graphic/UI/Button/red_button_style_1_pressed.png";
                break;
            }
            case RED_2: {
                idleImagePath = "src/main/resources/Graphic/UI/Button/red_button_style_2_idle.png";
                pressedImagePath = "src/main/resources/Graphic/UI/Button/red_button_style_2_pressed.png";
                break;
            }
            case YELLOW_1: {
                idleImagePath = "src/main/resources/Graphic/UI/Button/yellow_button_style_1_idle.png";
                pressedImagePath = "src/main/resources/Graphic/UI/Button/yellow_button_style_1_pressed.png";
                break;
            }
            case YELLOW_2: {
                idleImagePath = "src/main/resources/Graphic/UI/Button/yellow_button_style_2_idle.png";
                pressedImagePath = "src/main/resources/Graphic/UI/Button/yellow_button_style_2_pressed.png";
                break;
            }
            case YELLOW_3: {
                idleImagePath = "src/main/resources/Graphic/UI/Button/yellow_button_style_3_idle.png";
                pressedImagePath = "src/main/resources/Graphic/UI/Button/yellow_button_style_3_pressed.png";
                break;
            }
            default: {
                idleImagePath = "src/main/resources/Graphic/UI/Button/green_button_style_1_idle.png";
                pressedImagePath = "src/main/resources/Graphic/UI/Button/green_button_style_1_pressed.png";
                break;
            }
        }
        createStyle();
    }

    private void createStyle() {
        idleImage = new Image(new File(idleImagePath).toURI().toString());
        pressedImage = new Image(new File(pressedImagePath).toURI().toString());
        backgroundSize = new BackgroundSize(1.0, 1.0, true, true, false, false);
        backgroundImageIdle = new BackgroundImage(
                idleImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT, backgroundSize);
        backgroundImagePressed = new BackgroundImage(
                pressedImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT, backgroundSize);

        setStyleIdle();
    }

    private void setStyleIdle() {
        setPrefHeight(height);
        setBackground(new Background(backgroundImageIdle));
    }

    private void setStylePressed() {
        setPrefHeight(height - 4);
        setBackground(new Background(backgroundImagePressed));
    }

    private void createEventHandle() {
        setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setStyleIdle();
            }
        });
        setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setStylePressed();
            }
        });
    }
}
