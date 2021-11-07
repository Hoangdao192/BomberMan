import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Game game = new Game();
        game.run();
        primaryStage = game.getMainStage();
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
