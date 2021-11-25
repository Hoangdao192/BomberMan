package UI;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class UIButton extends Button {
    private final String BUTTON_IDLE_STYLE =
            "-fx-background-color: transparent; -fx-background-image: url('/Graphic/UI/button_green_idle.png')";
    private final String BUTTON_PRESSED_STYLE =
            "-fx-background-color: transparent; -fx-background-image: url('/Graphic/UI/button_green_pressed.png')";


    public UIButton(int width) {
        setText("Hello");
        setPrefWidth(190);
        setPrefHeight(49);
        setStyleIdle();
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

    private void setStyleIdle() {
        setStyle(BUTTON_IDLE_STYLE);
    }

    private void setStylePressed() {
        setStyle(BUTTON_PRESSED_STYLE);
    }
}
