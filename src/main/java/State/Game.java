package State;

import Component.MapManager;
import Entities.Bomber;
import Map.Map;
import Setting.Setting;
import SupportMap.TransferMap;
import UI.GameOverPane;
import UI.HeadPane;
import UI.MiniMap;
import UI.PauseMenu;
import Utils.Vector2i;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;
import java.util.Stack;

public class Game extends BaseState{
    //  Kích thước cửa sổ mặc định
    private final int DEFAULT_WIDTH = 800;
    private final int DEFAULT_HEIGHT = 416;
    //  Kích thước cửa sổ
    private double screenWidth;
    private double screenHeight;

    private int FPS;
    private long frameDelayTime;

    private boolean gameOver = false;

    private AnchorPane mainContainer;
    private Canvas mainCanvas;
    private GraphicsContext graphicsContext;

    //  Level
    private int level = 3;
    private MapManager mapManager;

    // Support Map
    //private Canvas HpCavas;
    private Canvas MiniMapCavas;
    //private GraphicsContext graphicsComponent;
    //Transfer Map
    private TransferMap transferMap;
    //Front
    //private Font font;

    //  UI
    private PauseMenu pauseMenu;
    private HeadPane headPane;
    private final int HEADPANE_DEFAULT_HEIGHT = 60;
    private Vector2i headPanePosition = new Vector2i(0, 0);
    private Vector2i mainCanvasPosition = new Vector2i(0 , 0);

    private MediaPlayer mediaPlayer;

    private static boolean soundOn = false;

    //  Bottom Pane
    private boolean showMinimap = true;
    private MiniMap miniMap;
    private final int BOTTOM_PANE_DEFAULT_HEIGHT = 180;

    //  Game Over Pane
    private GameOverPane gameOverPane;
    private boolean createGameOver = false;

    Bomber bomber;
    Map map;

    private boolean pause = false;

    public Game(Stage mainStage, Stack<BaseState> states) {
        super(mainStage, states);
        screenHeight = (double) DEFAULT_HEIGHT;
        screenWidth = (double) DEFAULT_WIDTH;

        mainCanvas = new Canvas(screenWidth, screenHeight);
        graphicsContext = mainCanvas.getGraphicsContext2D();
        graphicsContext.setImageSmoothing(false);
        mainCanvasPosition.x = 0;
        mainCanvasPosition.y = HEADPANE_DEFAULT_HEIGHT;

        mainContainer = new AnchorPane();

        scene = new Scene(mainContainer, screenWidth, screenHeight + 60);

        mainCanvas.setScaleX(1);
        mainCanvas.setScaleY(1);
        mainCanvas.setLayoutX(mainCanvasPosition.x);
        mainCanvas.setLayoutY(mainCanvasPosition.y);

        createMapManager();
        createNewGame();

        createResizeEventHandle();
        initEventHandler();
        playBackgroundMusic();
    }

    /*private void createHeadMap() {
        headMap = new HeadMap(bomber, map, (int) screenWidth, (int) screenHeight / 10);
    }*/

    private void createTransferMap() {
        transferMap = new TransferMap((int) screenWidth, (int) screenHeight, map.getGridSize());
    }

    //  UI initializer
    private void createUI() {
        createHeadPane();
        createMiniMap();
        createPauseMenu();
        //createGameOverPane();
    }

    private void createHeadPane() {
        headPane = new HeadPane(map, (int) screenWidth, HEADPANE_DEFAULT_HEIGHT);
        mainContainer.setLeftAnchor(headPane, 0.0);
        mainContainer.setRightAnchor(headPane, 1.0);
        mainContainer.getChildren().add(headPane);


        headPane.getPauseButton().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                pause = true;
            }
        });
    }

    private void createMiniMap() {
        miniMap = new MiniMap(map, (int) 200, 50);
        miniMap.setLayoutY(HEADPANE_DEFAULT_HEIGHT);
        miniMap.setOpacity(0.8);
        mainContainer.setRightAnchor(miniMap, 0.0);
        mainContainer.getChildren().add(miniMap);
    }

    private void createPauseMenu() {
        pauseMenu = new PauseMenu(100, 100, 200, 230);
        mainContainer.getChildren().add(pauseMenu);
        pauseMenu.getContinueButton().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                pause = false;
            }
        });

        pauseMenu.getRestartButton().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                createNewGame();
                pause = false;
            }
        });

        pauseMenu.getMenuButton().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                running = false;
            }
        });
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
        scene.widthProperty().addListener((obs, oldVal, newVal) -> {
            resizeCanvas();
            resizeUI();
            resizeCamera();
        });

        scene.heightProperty().addListener((obs, oldVal, newVal) -> {
            resizeCanvas();
            resizeUI();
            resizeCamera();
        });
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
        headPane.resize((int) scene.getWidth(), (int) headPane.getHeight());
    }

    private void resizeCanvas() {
        double mapRenderWidth = mainCanvas.getScaleX() * map.getSize().x;
        double mapRenderHeight = mainCanvas.getScaleY() * map.getSize().y;

        double scaleRatio = mainCanvas.getScaleX();

        double scaleRatioX = scene.getWidth() / mainCanvas.getWidth();
        double scaleRatioY = (scene.getHeight() - HEADPANE_DEFAULT_HEIGHT)/ mainCanvas.getHeight();
        if (mainCanvas.getWidth() * scaleRatioX <= map.getSize().x) {
            scaleRatioX = 1;
            mainCanvas.setWidth(scene.getWidth());
        } else {
            mainCanvas.setWidth(map.getSize().x);
        }

        if (mainCanvas.getHeight() * scaleRatioY <= map.getSize().y) {
            scaleRatioY = 1;
            mainCanvas.setHeight(scene.getHeight() - HEADPANE_DEFAULT_HEIGHT);
        } else {
            mainCanvas.setHeight(map.getSize().y);
        }

        if (scaleRatioX > scaleRatioY) {
            scaleRatio = scaleRatioX;
        } else {
            scaleRatio = scaleRatioY;
        }

        mainCanvas.setScaleX(scaleRatio);
        mainCanvas.setScaleY(scaleRatio);
        mainCanvas.setLayoutX(mainCanvasPosition.x + (mainCanvas.getWidth() * (mainCanvas.getScaleX() - 1))/2);
        mainCanvas.setLayoutY(mainCanvasPosition.y + (mainCanvas.getHeight() * (mainCanvas.getScaleY() - 1))/2);

        /*if (mainScene.getWidth() <= map.getSize().x) {
            mainCanvas.setWidth(mainScene.getWidth());
        }
        else if (mainScene.getWidth() > map.getSize().x) {
            mainCanvas.setWidth(map.getSize().x);
        }
        mainCanvas.setScaleX(mainScene.getWidth() / mainCanvas.getWidth());
        mainCanvas.setScaleY(mainScene.getWidth() / mainCanvas.getWidth());
        mainCanvas.setLayoutX(mainCanvasPosition.x + (mainCanvas.getWidth() * (mainCanvas.getScaleX() - 1))/2);
        mainCanvas.setLayoutY(mainCanvasPosition.y + (mainCanvas.getHeight() * (mainCanvas.getScaleY() - 1))/2);*/
    }

    private void resizeCamera() {
        //  Sai số
        final int ERROR = 1;

        //  Kích thước của map khi in ra màn hình
        double mapRenderWidth = mainCanvas.getScaleX() * map.getSize().x;
        double mapRenderHeight = mainCanvas.getScaleY() * map.getSize().y;

        //  Tỉ lệ giữa kích của phần màn hình để hiển thị game và kích thước của map khi vẽ ra màn hình
        double widthRatio = scene.getWidth() / mapRenderWidth;
        double heightRatio = (scene.getHeight() - HEADPANE_DEFAULT_HEIGHT) / mapRenderHeight;

        //  RESIZE WIDTH
        int newCameraWidth = (int) (map.getSize().x * widthRatio + ERROR);
        if (newCameraWidth > map.getSize().x) {
            newCameraWidth = map.getSize().x;
        }
        //  RESIZE HEIGHT
        int newCameraHeight = (int) (map.getSize().y * heightRatio + ERROR);
        if (newCameraHeight > map.getSize().y) {
            newCameraHeight = map.getSize().y;
        }

        map.getCamera().setSize(newCameraWidth, newCameraHeight);
        System.out.println(map.getCamera().getSize().y);
    }

    private void resizeHeight() {
        if (scene.getHeight() - HEADPANE_DEFAULT_HEIGHT <= map.getSize().y) {
            map.getCamera().setSize(map.getCamera().getSize().x, (int) scene.getHeight() - HEADPANE_DEFAULT_HEIGHT);
        } else if (scene.getHeight() - HEADPANE_DEFAULT_HEIGHT > map.getSize().y) {
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

    private void createMapManager() {
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
    }

    private void createMap() {
        map = mapManager.loadCurrentLevel();
        //map = new Map("src/main/resources/Map/map.txt", DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    private void createPlayer() {
        bomber = new Bomber(100, 200, map);
        map.getCamera().setCenter(100, 200);
    }

    public void initEventHandler() {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
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
        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                bomber.updateInput(keyEvent, false);
            }
        });
    }

    private boolean checkPlayerBehindMiniMap() {
        int bomberScreenX = bomber.getX() - map.getCamera().getStart().x;
        int bomberScreenY = bomber.getY() - map.getCamera().getStart().y;
        Rectangle2D bomberRect = new Rectangle2D(bomberScreenX, bomberScreenY, bomber.getWidth(), bomber.getHeight());
        Rectangle2D miniMapRect = new Rectangle2D(
                miniMap.getLayoutX(), miniMap.getLayoutY(), miniMap.getWidth(), miniMap.getHeight()
        );

        return (miniMapRect.contains(bomberRect) || miniMapRect.intersects(bomberRect));
    }

    public void run() {
        AnimationTimer gameTimer = new AnimationTimer() {
            private long lastUpdate = 0;
            @Override
            public void handle(long now) {
                if (now - lastUpdate >= frameDelayTime) {
                    if (running && !pause) {
                        scene.getRoot().requestFocus();
                        if (map.getTime().isStop()) {
                            map.getTime().present();
                        }
                        if (pauseMenu.isVisible()) {
                            pauseMenu.setVisible(false);
                            if (!headPane.isActionEnable()) {
                                headPane.enableAction();
                            }
                        }

                        update();
                        render();
                    }
                    else if (pause) {
                        if (!map.getTime().isStop()) {
                            map.getTime().stop();
                        }
                        map.getTime().countSecond();
                        pauseMenu.toFront();
                        pauseMenu.setVisible(true);
                        headPane.disableAction();
                    } else
                    {
                        updateGameOverPane();
                        gameOverPane.toFront();
                        gameOverPane.setVisible(true);
                        /*mapManager.nextLevel();
                        map = mapManager.loadCurrentLevel();
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
        if (!gameOver) {
            if (!pause) {
                //  UPDATE GAME
                scene.getRoot().requestFocus();
                if (map.getTime().isStop()) {
                    map.getTime().present();
                }
                if (pauseMenu.isVisible()) {
                    pauseMenu.setVisible(false);
                    if (!headPane.isActionEnable()) {
                        headPane.enableAction();
                    }
                }
                updateGame();
            } else {
                //  UPDATE PAUSE MENU
                if (!map.getTime().isStop()) {
                    map.getTime().stop();
                }
                map.getTime().countSecond();
                pauseMenu.toFront();
                pauseMenu.setVisible(true);
                headPane.disableAction();
            }
        } else {
            updateGameOverPane();
            gameOverPane.toFront();
            gameOverPane.setVisible(true);
        }
    }

    private void updateGame() {
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
        } else {
            if (!transferMap.isLoading()) {
                transferMap.reset(map.getPlayer(), map.getTime().countSecond(), map.getBonusArrayList());
            }
            if (transferMap.getPercent() >= 100) {
                map.newMap();
                map.setTransfer(false);
                transferMap.setLoading(false);
            }
            miniMap.newMiniMap();
            transferMap.update();
        }
        updateUI();
    }

    public void createNewGame() {
        mainContainer.getChildren().clear();
        createMap();
        createPlayer();
        createTransferMap();
        map.setPlayer(bomber);
        createUI();
        mainContainer.getChildren().add(mainCanvas);
        resizeCamera();
    }

    private void updateGameOverPane() {
        if (!createGameOver) {
            map.newMap();
            transferMap.reset(map.getPlayer(), map.getTime().countSecond(), map.getBonusArrayList());
            map.setTransfer(false);
            transferMap.setLoading(false);
            createGameOverPane();
        } else {
            gameOverPane.update();
        }
    }

    private void updateUI() {
        if (headPane.isTransfer() != map.isTransfer()) {
            headPane.setTransfer(map.isTransfer());
            headPane.reset();
        }
        headPane.update();
        miniMap.update();
        if (checkPlayerBehindMiniMap()) {
            miniMap.setVisible(false);
        } else {
            miniMap.setVisible(showMinimap);
        }
    }

    public void render() {
        mainContainer.getChildren().remove(mainCanvas);
        mainContainer.getChildren().add(mainCanvas);
        miniMap.toFront();
        pauseMenu.toFront();

        if (!map.isTransfer()) {
            graphicsContext.clearRect(0, 0, mainCanvas.getWidth(), mainCanvas.getHeight());
            /*Camera camera = map.getCamera();
            graphicsContext.strokeRect(0, 0, camera.getSize().x, camera.getSize().y);*/
            map.render(graphicsContext);
            bomber.render(graphicsContext);
        } else {
            transferMap.render(graphicsContext);
        }
    }
}
