package State;

import UI.SettingPane;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.Stack;

public class SettingState extends BaseState {
    private SettingPane settingPane;

    public SettingState(Stage mainStage, Stack<BaseState> states) {
        super(mainStage, states);

        settingPane = new SettingPane(0, 0,
                (int) mainStage.getScene().getWidth(),
                (int) mainStage.getScene().getHeight());
        settingPane.getCloseButton().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                running = false;
            }
        });

        scene = new Scene(settingPane);
    }

    @Override
    public void update() {

    }

    @Override
    public void render() {

    }
}
