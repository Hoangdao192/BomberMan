import Entities.Bomber;
import Map.Map;
import Map.Camera;
import SupportMap.HeadMap;
import SupportMap.TransferMap;
import UI.GameOverPane;
import UI.HeadPane;
import UI.Panel;
import Utils.RandomInt;
import Utils.Vector2i;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ZoomEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.annotation.processing.Messager;
import java.io.FileInputStream;

public class Game {
    //  Kích thước cửa sổ mặc định
    private final int DEFAULT_WIDTH = 800;
    private final int DEFAULT_HEIGHT = 416;
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

    // Support Map
    // Heap map
    private HeadMap headMap;
    //private Canvas HpCavas;
    private Canvas MiniMapCavas;
    //private GraphicsContext graphicsComponent;
    //Transfer Map
    private TransferMap transferMap;
    //Front
    //private Font font;

    //  UI
    private HeadPane headPane;
    private final int HEADPANE_DEFAULT_HEIGHT = 60;
    private Vector2i headPanePosition = new Vector2i(0, 0);
    private Vector2i mainCanvasPosition = new Vector2i(0 , 0);

    Bomber bomber;
    Map map;

    private boolean running = true;

    public Game() {
        screenHeight = (double) DEFAULT_HEIGHT;
        screenWidth = (double) DEFAULT_WIDTH;

        mainCanvas = new Canvas(screenWidth, screenHeight);
        graphicsContext = mainCanvas.getGraphicsContext2D();
        graphicsContext.setImageSmoothing(false);
        mainCanvasPosition.x = 0;
        mainCanvasPosition.y = HEADPANE_DEFAULT_HEIGHT + 1;

        mainContainer = new Group();

        //mainScene = new Scene(mainContainer, screenWidth, screenHeight);
        mainScene = new Scene(mainContainer, screenWidth, screenHeight + 60);
        mainStage = new Stage();
        mainStage.setMinHeight(DEFAULT_HEIGHT + 30);
        mainStage.setMinWidth(DEFAULT_WIDTH);
        mainStage.setScene(mainScene);

        mainCanvas.setScaleX(1);
        mainCanvas.setScaleY(1);
        mainCanvas.setLayoutX(mainCanvasPosition.x);
        mainCanvas.setLayoutY(mainCanvasPosition.y);
        setFPS(30);
        createMap();
        createPlayer();
        createHeadMap();
        createTransferMap();
        map.setPlayer(bomber);
        initEventHandler();
        createResizeEventHandle();

        createHeadPane();
        mainContainer.getChildren().add(headPane);
        mainContainer.getChildren().add(mainCanvas);

        GameOverPane gameOverPane = new GameOverPane(50, 50, 265, 230, 1000);
        mainContainer.getChildren().add(gameOverPane);
    }

    private void createHeadPane() {
        headPane = new HeadPane(map, (int) screenWidth, HEADPANE_DEFAULT_HEIGHT);
    }

    private void createHeadMap() {
        headMap = new HeadMap(bomber, map, (int) screenWidth, (int) screenHeight / 10);
    }

    private void createTransferMap() {
        transferMap = new TransferMap((int) screenWidth, (int) screenHeight, map.getGridSize());
    }

    private void createUI() {
    }

    //  RESIZE EVENT HANDLE
    private void createResizeEventHandle() {
        mainScene.widthProperty().addListener((obs, oldVal, newVal) -> {
            resizeCanvas();
            resizeUI();
            resizeCamera();
        });
        mainScene.setOnZoom(new EventHandler<ZoomEvent>() {
            @Override
            public void handle(ZoomEvent zoomEvent) {
                resizeCanvas();
                resizeUI();
                resizeCamera();
            }
        });
    }

    private void resizeUI() {
        headPane.resize((int) mainScene.getWidth(), (int) headPane.getHeight());
    }

    private void resizeCanvas() {
        if (mainScene.getWidth() <= map.getSize().x) {
            mainCanvas.setWidth(mainScene.getWidth());
            return;
        }
        if (mainScene.getWidth() > map.getSize().x) {
            mainCanvas.setWidth(map.getSize().x);
        }
        mainCanvas.setScaleX(mainScene.getWidth() / mainCanvas.getWidth());
        mainCanvas.setScaleY(mainScene.getWidth() / mainCanvas.getWidth());
        mainCanvas.setLayoutX(mainCanvasPosition.x + (mainCanvas.getWidth() * (mainCanvas.getScaleX() - 1))/2);
        mainCanvas.setLayoutY(mainCanvasPosition.y + (mainCanvas.getHeight() * (mainCanvas.getScaleY() - 1))/2);
    }

    private void resizeCamera() {
        if (mainScene.getWidth() <= map.getSize().x) {
            map.getCamera().setSize((int)mainScene.getWidth(), map.getCamera().getSize().y);
        }
        else if (mainScene.getWidth() > map.getSize().x) {
            map.getCamera().setSize(map.getSize().x, map.getCamera().getSize().y);
        }
    }

    public void setFPS(int fps) {
        FPS = fps;
        frameDelayTime = 1000000000 / fps;
    }

    public Stage getMainStage() {
        return mainStage;
    }

    private void createMap() {
         map = new Map("src/main/resources/Map/Map_lv2.txt", (int) screenWidth, (int) screenHeight);
    }

    private void createPlayer() {
        bomber = new Bomber(100, 200, map);
        map.getCamera().setCenter(100, 200);
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
                    if (running) {
                        update();
                        render();
                    } else {
                        System.out.println("Game over");
                    }
                    lastUpdate = now;
                }
            }
        };
        gameTimer.start();
    }

    public void update() {
        if (!bomber.isExist()) {
            running = false;
        }

        if (!map.isTransfer()) {
            if (!bomber.isExist()) {
                running = false;
            }
            bomber.update();
            //map.getCamera().move(bomber.getMovement().getVelocity());
            map.getCamera().setCenter(bomber.getX(), bomber.getY());
            map.update();
            headMap.update();
        } else {
            headMap.setTransfer(true);
            if (!transferMap.isLoading()) {
                transferMap.reset(map.getPlayer(), headMap.getMaxTime() - headMap.getTime(), map.getCheckBonus());
            }
            if (transferMap.getPercent() >= 100) {
                map.newMap();
                map.setTransfer(false);
                transferMap.setLoading(false);
                headMap.setTransfer(false);
            }
            transferMap.update();
        }
        updateUI();
    }

    private void updateUI() {
        headPane.update();
    }

    public void render() {
        /*graphicsContext.clearRect(0, 0, screenWidth, screenHeight);
        graphicsContext.setFill(Paint.valueOf("Blue"));
        graphicsContext.fillRect(0, 0, screenWidth, screenHeight);
        Camera camera = map.getCamera();
        graphicsContext.strokeRect(0, 0, camera.getSize().x, camera.getSize().y);
        map.render(graphicsContext);
        bomber.render(graphicsContext);*/

        if (!map.isTransfer()) {
            graphicsContext.clearRect(0, 0, screenWidth, screenHeight);
            graphicsContext.setFill(Paint.valueOf("Green"));
            graphicsContext.fillRect(0, 0, screenWidth, screenHeight);
            Camera camera = map.getCamera();
            graphicsContext.strokeRect(0, 0, camera.getSize().x, camera.getSize().y);
            map.render(graphicsContext);
            bomber.render(graphicsContext);
            //headMap.render(graphicsComponent);
        } else {
            //headMap.render(graphicsComponent);
            transferMap.render(graphicsContext);
        }
    }
}
