package UI;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class CheckBox extends Button {
    private boolean checked = false;
    private String checkStyle =
            "-fx-background-color: transparent;" +
            "-fx-background-image: url('Graphic/UI/CheckBox/green_boxCheckmark.png');" +
            "-fx-background-repeat: no-repeat; -fx-background-size: cover";
    private String crossStyle =
            "-fx-background-color: transparent;" +
            "-fx-background-image: url('Graphic/UI/CheckBox/green_boxCross.png');" +
            "-fx-background-repeat: no-repeat; -fx-background-size: cover";

    public CheckBox(int size) {
        setPrefSize(size, size - 2);
        setChecked(checked);
        setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (checked) {
                    checked = false;
                    setStyle(crossStyle);
                } else {
                    checked = true;
                    setStyle(checkStyle);
                }
            }
        });
    }


    public void setChecked(boolean checked) {
        this.checked = checked;
        if (checked) {
            setStyle(checkStyle);
        } else {
            setStyle(crossStyle);
        }
    }

    public boolean isChecked() {
        return checked;
    }
}
