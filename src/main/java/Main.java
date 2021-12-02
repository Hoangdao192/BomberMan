import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        /*Game game = new Game();
        game.run();
        primaryStage = game.getMainStage();*/

        Menu menu = new Menu();
        primaryStage = menu.getMainStage();

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
