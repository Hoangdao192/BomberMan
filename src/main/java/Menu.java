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

import UI.UIButton;

public class Menu {
    // Kích thước cửa sổ mặc định
    private final int DEFAULT_WIDTH = 800;
    private final int DEFAULT_HEIGHT = 416;
    //  Kích thước cửa sổ
    private double screenWidth;
    private double screenHeight;

    private Stage mainStage;
    private Scene mainScene;
    private ImageViewPane viewPane;
    private UIButton button1;
    private UIButton button2;
    private UIButton button3;

    public Menu() throws FileNotFoundException {
        Image image = new Image(new FileInputStream("src/main/resources/Graphic/bomberman_menu.png"));
        ImageView imageView = new ImageView();
        imageView.setImage(image);

        viewPane = new ImageViewPane(imageView);

        creatUIButton();

        VBox box = new VBox(30, button1, button2, button3);
        box.setAlignment(Pos.CENTER);
        VBox.setVgrow(button1, Priority.ALWAYS);
        VBox.setVgrow(button2, Priority.ALWAYS);
        VBox.setVgrow(button3, Priority.ALWAYS);


        StackPane root = new StackPane();
        root.getChildren().addAll(viewPane, box);
        mainScene = new Scene(root, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        mainStage = new Stage();
        mainStage.setTitle("BomberMan Game");
        mainStage.setScene(mainScene);
    }

    public Stage getMainStage() {
        return mainStage;
    }

    private void creatUIButton() {
        button1 = new UIButton(250, 50, "New Game");
        button1.setStyle(UIButton.GREEN_1);
        button2 = new UIButton(250, 50, "Setting");
        button2.setStyle(UIButton.GREEN_1);
        button3 = new UIButton(250, 50, "Exit");
        button3.setStyle(UIButton.GREEN_1);

        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Game game = new Game();
                game.run();
                mainStage = game.getMainStage();
                mainStage.show();
            }
        });

        button2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SettingPane settingPane = new SettingPane(0, 0, DEFAULT_WIDTH, DEFAULT_HEIGHT);
                Scene sceneSetting = new Scene(settingPane);
                mainStage.setScene(sceneSetting);
                mainStage.show();
            }
        });

        button3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mainStage.close();
            }
        });
    }
}