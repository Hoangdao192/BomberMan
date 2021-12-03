package State;

import UI.ImageViewPane;
import UI.SettingPane;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Stack;

import UI.UIButton;

public class Menu extends BaseState {
    // Kích thước cửa sổ mặc định
    private final int DEFAULT_WIDTH = 800;
    private final int DEFAULT_HEIGHT = 416;

    private ImageViewPane viewPane;
    private UIButton newGameButton;
    private UIButton settingButton;
    private UIButton exitButton;

    public Menu(Stage mainStage, Stack<BaseState> states) throws FileNotFoundException {
        super(mainStage, states);
        Image image = new Image(new FileInputStream("src/main/resources/Graphic/bomberman_menu.png"));
        ImageView imageView = new ImageView();
        imageView.setImage(image);

        viewPane = new ImageViewPane(imageView);

        creatUIButton();

        VBox box = new VBox(30, newGameButton, settingButton, exitButton);
        box.setAlignment(Pos.CENTER);
        VBox.setVgrow(newGameButton, Priority.ALWAYS);
        VBox.setVgrow(settingButton, Priority.ALWAYS);
        VBox.setVgrow(exitButton, Priority.ALWAYS);

        StackPane root = new StackPane();
        root.getChildren().addAll(viewPane, box);
        scene = new Scene(root, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    private void creatUIButton() {
        newGameButton = new UIButton(250, 50, "New Game");
        newGameButton.setStyle(UIButton.GREEN_1);
        settingButton = new UIButton(250, 50, "Setting");
        settingButton.setStyle(UIButton.GREEN_1);
        exitButton = new UIButton(250, 50, "Exit");
        exitButton.setStyle(UIButton.GREEN_1);

        newGameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                states.push(new Game(mainStage, states));
            }
        });

        settingButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SettingPane settingPane = new SettingPane(0, 0, DEFAULT_WIDTH, DEFAULT_HEIGHT);
                Scene sceneSetting = new Scene(settingPane);
                mainStage.setScene(sceneSetting);
                mainStage.show();
            }
        });

        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mainStage.close();
            }
        });
    }

    @Override
    public void update() {

    }

    @Override
    public void render() {

    }
}