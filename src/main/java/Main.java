import State.BaseState;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.Stack;

public class Main extends Application {
    public static final int DEFAULT_WIDTH = 800;
    public static final int DEFAULT_HEIGHT = 416;
    private static final int FPS = 30;
    private static long frameDelayTime;

    @Override
    public void start(Stage primaryStage) throws Exception {
        frameDelayTime = 1000000000 / FPS;

        Stack<BaseState> stateManager = new Stack<>();
        Menu menu = new Menu(primaryStage, stateManager);
        stateManager.push(menu);
        primaryStage.setMinHeight(DEFAULT_HEIGHT + 30);
        primaryStage.setMinWidth(DEFAULT_WIDTH);
        primaryStage.show();

        AnimationTimer animationTimer = new AnimationTimer() {
            private long lastUpdate = 0;
            @Override
            public void handle(long now) {
                if (now - lastUpdate >= frameDelayTime) {
                    if (stateManager.lastElement().isRunning()) {
                        if (primaryStage.getScene() != stateManager.lastElement().getScene()) {
                            primaryStage.setScene(stateManager.lastElement().getScene());
                        }
                        stateManager.lastElement().update();
                        stateManager.lastElement().render();
                    } else {
                        stateManager.pop();
                    }
                    lastUpdate = now;
                }
            }
        };
        animationTimer.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
