package State;

import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Stack;

public abstract class BaseState {
    protected Scene scene;
    protected Stage mainStage;
    protected boolean running;

    protected Stack<BaseState> states;

    public BaseState(Stage mainStage, Stack<BaseState> states) {
        this.mainStage = mainStage;
        running = true;
        this.states = states;
    }

    public abstract void update();
    public abstract void render();

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void stop() {
        running = false;
    }

    public Scene getScene() {
        return scene;
    }
}
