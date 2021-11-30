import Component.MapManager;
import Entities.Bomber;
import Map.Map;
import Map.Camera;
import Setting.Setting;
import SupportMap.HeadMap;
import SupportMap.TransferMap;
import UI.BottomPane;
import UI.GameOverPane;
import UI.HeadPane;
import Utils.Vector2i;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.File;

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
    private AnchorPane mainContainer;
    private Canvas mainCanvas;
    private GraphicsContext graphicsContext;

    //  Level
    private int level = 3;
    private MapManager mapManager;

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

    private MediaPlayer mediaPlayer;

    private static boolean soundOn = false;

    //  Bottom Pane
    private BottomPane bottomPane;
    private final int BOTTOM_PANE_DEFAULT_HEIGHT = 180;

    //  Game Over Pane
    private GameOverPane gameOverPane;
    private boolean createGameOver = false;

    Bomber bomber;
    Map map;

    private boolean running = true;
    private boolean pause = false;

    public Game() {
        screenHeight = (double) DEFAULT_HEIGHT;
        screenWidth = (double) DEFAULT_WIDTH;

        mainCanvas = new Canvas(screenWidth, screenHeight);
        graphicsContext = mainCanvas.getGraphicsContext2D();
        graphicsContext.setImageSmoothing(false);
        mainCanvasPosition.x = 0;
        mainCanvasPosition.y = HEADPANE_DEFAULT_HEIGHT;

        mainContainer = new AnchorPane();

        //mainScene = new Scene(mainContainer, screenWidth, screenHeight);
        //mainScene = new Scene(mainContainer, screenWidth, screenHeight + 60);
        mainScene = new Scene(mainContainer, screenWidth, screenHeight + 60 + 130);
        mainStage = new Stage();
        mainStage.setMinHeight(DEFAULT_HEIGHT + 30);
        mainStage.setMinWidth(DEFAULT_WIDTH);
        mainStage.setScene(mainScene);

        mainCanvas.setScaleX(1);
        mainCanvas.setScaleY(1);
        mainCanvas.setLayoutX(mainCanvasPosition.x);
        mainCanvas.setLayoutY(mainCanvasPosition.y);
        /*mainContainer.setLeftAnchor(mainCanvas, 0.0);
        mainContainer.setRightAnchor(mainCanvas, 0.0);*/
        setFPS(30);
        createMap();
        createPlayer();
        createHeadMap();
        createTransferMap();
        map.setPlayer(bomber);
        createResizeEventHandle();

        createUI();
        initEventHandler();
        mainContainer.getChildren().add(mainCanvas);

        /*SettingPane settingPane = new SettingPane(50,50, 300,250);
        mainContainer.getChildren().add(settingPane);
        settingPane.toFront();*/

        playBackgroundMusic();
    }

    private void createHeadMap() {
        headMap = new HeadMap(bomber, map, (int) screenWidth, (int) screenHeight / 10);
    }

    private void createTransferMap() {
        transferMap = new TransferMap((int) screenWidth, (int) screenHeight, map.getGridSize());
    }

    private void createUI() {
        createHeadPane();
        createBottomPane();
        //createGameOverPane();
    }

    private void createHeadPane() {
        headPane = new HeadPane(map, (int) screenWidth, HEADPANE_DEFAULT_HEIGHT);
        mainContainer.setLeftAnchor(headPane, 0.0);
        mainContainer.setRightAnchor(headPane, 1.0);
        mainContainer.getChildren().add(headPane);
    }

    private void createBottomPane() {
        bottomPane = new BottomPane(map, (int) mainScene.getWidth(), BOTTOM_PANE_DEFAULT_HEIGHT);
        bottomPane.setLayoutX(mainCanvasPosition.x);
        bottomPane.setLayoutY(mainCanvasPosition.y + screenHeight);
        mainContainer.getChildren().add(bottomPane);
    }

    private void createGameOverPane() {
        gameOverPane = new GameOverPane((int) mainStage.getWidth() / 4, (int) mainStage.getHeight() / 4,
                (int) mainStage.getWidth() / 2, (int) mainStage.getHeight() / 2, transferMap);
        mainContainer.getChildren().add(gameOverPane);
        gameOverPane.setVisible(false);
        createGameOver = true;
    }

    //  RESIZE EVENT HANDLE
    private void createResizeEventHandle() {
        mainScene.widthProperty().addListener((obs, oldVal, newVal) -> {
            resizeCanvas();
            resizeUI();
            resizeCamera();
        });
        /*mainScene.heightProperty().addListener((obs, oldVal, newVal) -> {
            resizeHeight();
        });*/
    }

    private void playBackgroundMusic() {
        String musicFile = "src/main/resources/Sound/background_song.mp3";     // For example

        Media sound = new Media(new File(musicFile).toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        if (Setting.isSoundOn()) {
            mediaPlayer.play();
        }
    }

    private void resizeUI() {
        //headPane.resize((int) mainScene.getWidth(), (int) headPane.getHeight());
        bottomPane.resize((int) mainScene.getWidth(), (int) bottomPane.getHeight());
        bottomPane.setLayoutY(mainCanvasPosition.y + mainCanvas.getHeight() * mainCanvas.getScaleY());
    }

    private void resizeCanvas() {
        if (mainScene.getWidth() <= map.getSize().x) {
            mainCanvas.setWidth(mainScene.getWidth());
        }
        else if (mainScene.getWidth() > map.getSize().x) {
            mainCanvas.setWidth(map.getSize().x);
        }
        mainCanvas.setScaleX(mainScene.getWidth() / mainCanvas.getWidth());
        mainCanvas.setScaleY(mainScene.getWidth() / mainCanvas.getWidth());
        mainCanvas.setLayoutX(mainCanvasPosition.x + (mainCanvas.getWidth() * (mainCanvas.getScaleX() - 1))/2);
        mainCanvas.setLayoutY(mainCanvasPosition.y + (mainCanvas.getHeight() * (mainCanvas.getScaleY() - 1))/2);
    }

    private void resizeCamera() {
        //  RESIZE WIDTH
        System.out.println("Before: " + map.getCamera().getSize().y);
        if (mainScene.getWidth() <= map.getSize().x) {
            map.getCamera().setSize((int)mainScene.getWidth(), map.getCamera().getSize().y);
        }
        else if (mainScene.getWidth() > map.getSize().x) {
            map.getCamera().setSize(map.getSize().x, map.getCamera().getSize().y);
        }

        if (mainCanvas.getScaleY() > 1.0) {
            map.getCamera().setSize(map.getCamera().getSize().x,
                    (int) (mainCanvas.getHeight() * Math.pow(2.0 - mainCanvas.getScaleY(), 1.0/3.0)));
        }
        System.out.println("Last: " + map.getCamera().getSize().y);
    }

    private void resizeHeight() {
        if (mainScene.getHeight() - HEADPANE_DEFAULT_HEIGHT <= map.getSize().y) {
            map.getCamera().setSize(map.getCamera().getSize().x, (int) mainScene.getHeight() - HEADPANE_DEFAULT_HEIGHT);
        } else if (mainScene.getHeight() - HEADPANE_DEFAULT_HEIGHT > map.getSize().y) {
            map.getCamera().setSize(map.getCamera().getSize().x, map.getSize().y);
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
        mapManager = new MapManager((int) screenWidth, (int) screenHeight);
        mapManager.setCurrentLevel(1);
        mapManager.getMapPathList().add("src/main/resources/Map/Map_lv1.txt");
        mapManager.getMapPathList().add("src/main/resources/Map/Map_lv2.txt");
        mapManager.getMapPathList().add("src/main/resources/Map/Map_lv3.txt");
        mapManager.getMapPathList().add("src/main/resources/Map/Map_lv4.txt");
        mapManager.getMapPathList().add("src/main/resources/Map/Map_lv5.txt");
        mapManager.getMapPathList().add("src/main/resources/Map/Map_lv6.txt");
        mapManager.getMapPathList().add("src/main/resources/Map/Map_lv7.txt");
        mapManager.getMapPathList().add("src/main/resources/Map/Map_lv8.txt");
        mapManager.getMapPathList().add("src/main/resources/Map/Map_lv9.txt");
        mapManager.getMapPathList().add("src/main/resources/Map/Map_lv10.txt");
        map = mapManager.loadCurrentLevel();
    }

    private void createPlayer() {
        bomber = new Bomber(100, 200, map);
        map.getCamera().setCenter(100, 200);
    }

    public void initEventHandler() {
        mainScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode().equals(KeyCode.P)) {
                    if (!pause) {
                        pause = true;
                    } else pause = false;
                }
                bomber.updateInput(keyEvent, true);
            }
        });
        mainScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                bomber.updateInput(keyEvent, false);
            }
        });

        bottomPane.getHomeButton().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                bottomPane.clickHomeButton();
            }
        });

        bottomPane.getStopButton().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                bottomPane.clickStopButton();
            }
        });
    }

    public void run() {
        AnimationTimer gameTimer = new AnimationTimer() {
            private long lastUpdate = 0;
            @Override
            public void handle(long now) {
                if (now - lastUpdate >= frameDelayTime) {
                    if (running && !pause) {
                        update();
                        render();
                    }
                    else if (pause) {

                    } else
                    {

                        updateGameOverPane();
                        gameOverPane.toFront();
                        gameOverPane.setVisible(true);
                        /*mapManager.nextLevel();
                        map = mapManager.loadCurrentLevel();
                        createHeadMap();
                        createTransferMap();
                        bomber.setMap(map);
                        map.setPlayer(bomber);
                        bomber.reback();
                        running = true;*/
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
            if (map.getMaxTime() - map.getTime().countSecond() <= 0) {
                bomber.setExist(false);
            }

            if (!bomber.isExist()) {
                running = false;
            }
            bomber.update();
            //map.getCamera().move(bomber.getMovement().getVelocity());
            map.getCamera().setCenter(bomber.getX(), bomber.getY());
            //map.getCamera().setPosition(0, 0);
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
            bottomPane.newMiniMap();
            transferMap.update();
        }
        updateUI();
    }

    private void updateGameOverPane() {
        if (!createGameOver) {
            map.newMap();
            //Entity.Stop = true;
            transferMap.reset(map.getPlayer(), headMap.getMaxTime() - headMap.getTime(), map.getCheckBonus());
            map.setTransfer(false);
            transferMap.setLoading(false);
            createGameOverPane();
        } else {
            gameOverPane.update();
        }
    }

    private void updateUI() {
        headPane.update();
        bottomPane.update();
    }

    public void render() {
        mainContainer.getChildren().remove(mainCanvas);
        mainContainer.getChildren().add(mainCanvas);

        if (!map.isTransfer()) {
            graphicsContext.clearRect(0, 0, mainCanvas.getWidth(), mainCanvas.getHeight());
            /*Camera camera = map.getCamera();
            graphicsContext.strokeRect(0, 0, camera.getSize().x, camera.getSize().y);*/
            map.render(graphicsContext);
            bomber.render(graphicsContext);
            //headMap.render(graphicsComponent);
        } else {
            //headMap.render(graphicsComponent);
            transferMap.render(graphicsContext);
        }
    }
}
