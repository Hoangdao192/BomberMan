package State;

import UI.SettingPane;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.Stack;

public class SettingState extends BaseState {
    private SettingPane settingPane;
    private AnchorPane mainContainer;

    public SettingState(Stage mainStage, Stack<BaseState> states) {
        super(mainStage, states);

        mainContainer = new AnchorPane();

        settingPane = new SettingPane(0, 0,
                (int) mainStage.getScene().getWidth(),
                (int) mainStage.getScene().getHeight());
        settingPane.getCloseButton().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                running = false;
            }
        });

        mainContainer.getChildren().add(settingPane);
        mainContainer.setLeftAnchor(settingPane, 0.0);
        mainContainer.setRightAnchor(settingPane, 0.0);
        mainContainer.setTopAnchor(settingPane, 0.0);
        mainContainer.setBottomAnchor(settingPane, 0.0);
        scene = new Scene(mainContainer);
    }

    @Override
    public void update() {

    }

    @Override
    public void render() {

    }
}
