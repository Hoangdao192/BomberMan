package State;

import Component.MapManager;
import Entities.Bomber;
import Map.Map;
import Setting.Setting;
import SupportMap.TransferMap;
import UI.*;
import Utils.Vector2i;
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

    private boolean gameOver = false;

    private AnchorPane mainContainer;
    private Canvas mainCanvas;
    private GraphicsContext graphicsContext;

    private MapManager mapManager;

    private TransferMap transferMap;

    //  UI
    private TransferMapPane transferMapPane;
    private PauseMenu pauseMenu;
    private HeadPane headPane;
    private final int HEADPANE_DEFAULT_HEIGHT = 60;
    private Vector2i headPanePosition = new Vector2i(0, 0);
    private Vector2i mainCanvasPosition = new Vector2i(0 , 0);

    private MediaPlayer mediaPlayer;

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

    private void createTransferMap() {
        transferMap = new TransferMap((int) screenWidth, (int) screenHeight, map.getGridSize());
        transferMapPane = new TransferMapPane((int) scene.getWidth(), (int) scene.getHeight(), map, mapManager);
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
                stop();
            }
        });
    }

    private void createGameOverPane() {
        gameOverPane = new GameOverPane((int) mainStage.getWidth() / 4, (int) mainStage.getHeight() / 4,
                (int) mainStage.getWidth() / 2, (int) mainStage.getHeight() / 2, map);
        mainContainer.getChildren().add(gameOverPane);
        gameOverPane.setVisible(false);
        createGameOver = true;

        gameOverPane.getNewGameButton().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                createNewGame();
            }
        });

        gameOverPane.getMenuButton().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                stop();
            }
        });
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

    private void createMapManager() {
        mapManager = new MapManager((int) screenWidth, (int) screenHeight);
        //mapManager.setCurrentLevel(1);
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
        mapManager.setCurrentLevel(10);
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

    public void update() {
        if (!gameOver) {
            if (!pause) {
                //  UPDATE GAME
                scene.getRoot().requestFocus();
                if (map.getTime().isStop()  && !map.isLevelPass()) {
                    map.getTime().present();
                }
                if (pauseMenu.isVisible()) {
                    pauseMenu.setVisible(false);
                    if (!headPane.isActionEnable()) {
                        headPane.enableAction();
                    }
                }
                updateGame();
                if (!bomber.isAlive()) {
                    gameOver = true;
                }
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
            mediaPlayer.stop();
            headPane.disableAction();
            updateGameOverPane();
            gameOverPane.toFront();
            gameOverPane.setVisible(true);
        }
    }

    private void updateGame() {
        /*if (!bomber.isExist()) {
            running = false;
        }*/
        /*if (!map.isTransfer()) {
            if (map.getMaxTime() - map.getTime().countSecond() <= 0) {
                bomber.setExist(false);
            }

            if (!bomber.isExist()) {
                running = false;
            }
            bomber.update();
            map.getCamera().setCenter(bomber.getX(), bomber.getY());
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
        }*/

        if (!map.isLevelPass()) {
            transferMapPane.resetTime();
            bomber.update();
            map.getCamera().setCenter(bomber.getX(), bomber.getY());
            map.update();
            updateUI();
        } else {
            mediaPlayer.stop();
            mainContainer.getChildren().clear();
            transferMapPane.setTimeMap(map.getTime().countSecond());
            transferMapPane.setScorePlayer(bomber.getScore().getScore());
            mainContainer.getChildren().add(transferMapPane);
            transferMapPane.update();
            if (transferMapPane.isTransfer()) {
                if (mapManager.nextLevel()) {
                    nextLevel();
                    bomber.increaseHP();
                    mediaPlayer.play();
                } else {
                    stop();
                }
            }
        }
    }

    public void createNewGame() {
        gameOver = false;
        //mapManager.setCurrentLevel(1);
        mainContainer.getChildren().clear();
        createMap();
        createPlayer();
        map.setPlayer(bomber);

        createTransferMap();
        createUI();
        createGameOverPane();
        mainContainer.getChildren().add(mainCanvas);
        resizeCamera();
    }

    private void nextLevel() {
        gameOver = false;
        mainContainer.getChildren().clear();
        createMap();
        bomber.setMap(map);
        bomber.setPassOverPortal(false);
        map.setPlayer(bomber);

        createTransferMap();
        createUI();
        createGameOverPane();
        mainContainer.getChildren().add(mainCanvas);
        resizeCamera();
    }

    private void updateGameOverPane() {
        /*if (!createGameOver) {
            map.newMap();
            transferMap.reset(map.getPlayer(), map.getTime().countSecond(), map.getBonusArrayList());
            map.setTransfer(false);
            //transferMap.setLoading(false);
            createGameOverPane();
        } else {*/
            gameOverPane.toFront();
            gameOverPane.update();
        //}
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
        if (!gameOver) {
            renderGame();
        }

        /*if (!map.isTransfer()) {
            graphicsContext.clearRect(0, 0, mainCanvas.getWidth(), mainCanvas.getHeight());
            map.render(graphicsContext);
            bomber.render(graphicsContext);
        } else {
            transferMap.render(graphicsContext);
        }*/
    }

    private void renderGame() {
        if (!map.isLevelPass()) {
            mainContainer.getChildren().remove(mainCanvas);
            mainContainer.getChildren().add(mainCanvas);
            miniMap.toFront();
            pauseMenu.toFront();
            graphicsContext.clearRect(0, 0, mainCanvas.getWidth(), mainCanvas.getHeight());
            map.render(graphicsContext);
            bomber.render(graphicsContext);
        }
    }

    @Override
    public void stop() {
        super.stop();
        mediaPlayer.stop();
    }
}
