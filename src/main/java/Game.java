import Entities.Bomber;
import Map.Map;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class Game {
    //  Kích thước cửa sổ mặc định
    private final int DEFAULT_WIDTH = 800;
    private final int DEFAULT_HEIGHT = 600;
    //  Kích thước cửa sổ
    private double screenWidth;
    private double screenHeight;

    private int FPS;
    private long frameDelayTime;

    private Stage mainStage;
    private Scene mainScene;
    private Group mainContainer;
    private Canvas mainCanvas;
    private GraphicsContext graphicsContext;

    Bomber bomber;
    Map map;

    public Game() {
        screenHeight = (double) DEFAULT_HEIGHT;
        screenWidth = (double) DEFAULT_WIDTH;

        mainCanvas = new Canvas(screenWidth, screenHeight);
        graphicsContext = mainCanvas.getGraphicsContext2D();
        graphicsContext.setImageSmoothing(false);

        mainContainer = new Group();
        mainContainer.getChildren().add(mainCanvas);

        mainScene = new Scene(mainContainer, screenWidth, screenHeight);
        mainStage = new Stage();
        mainStage.setScene(mainScene);

        setFPS(30);
        createMap();
        createPlayer();
        initEventHandler();
    }

    public void setFPS(int fps) {
        FPS = fps;
        frameDelayTime = 1000000000 / fps;
    }

    public Stage getMainStage() {
        return mainStage;
    }

    private void createMap() {
         map = new Map("src/main/resources/Map/map1.txt", (int) screenWidth, (int) screenHeight);
    }

    private void createPlayer() {
        bomber = new Bomber(100, 200, map);

    }

    public void initEventHandler() {
        mainScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                bomber.updateInput(keyEvent, true);
            }
        });
        mainScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                bomber.updateInput(keyEvent, false);
            }
        });
    }

    public void run() {
        AnimationTimer gameTimer = new AnimationTimer() {
            private long lastUpdate = 0;
            @Override
            public void handle(long now) {
                if (now - lastUpdate >= frameDelayTime) {
                    update();
                    render();
                    lastUpdate = now;
                }
            }
        };
        gameTimer.start();
    }

    public void update() {
        bomber.update();
        map.update();
    }

    public void render() {
        graphicsContext.clearRect(0, 0, screenWidth, screenHeight);
        graphicsContext.setFill(Paint.valueOf("Blue"));
        graphicsContext.fillRect(0, 0, screenWidth, screenHeight);

        map.render(graphicsContext);
        bomber.render(graphicsContext);
    }
}
