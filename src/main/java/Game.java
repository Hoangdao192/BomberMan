import Entities.Bomber;
import Map.Map;
import Map.Camera;
import SupportMap.HeadMap;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
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

    // Heap map
    private HeadMap headMap;
    private Canvas HpCavas;
    private Canvas MiniMapCavas;
    private GraphicsContext graphicsContext;
    private GraphicsContext graphicsComponent;

    //Front
    private Font font;

    //  UI
    private SubScene subScene;
    private Label timeCounter;

    private long startTime;
    private long countTime;
    private long maxTime;

    Bomber bomber;
    Map map;

    private boolean running = true;

    public Game() {
        screenHeight = (double) DEFAULT_HEIGHT;
        screenWidth = (double) DEFAULT_WIDTH;

        mainCanvas = new Canvas(screenWidth, screenHeight);
        graphicsContext = mainCanvas.getGraphicsContext2D();
        graphicsContext.setImageSmoothing(false);


        GridPane mainPain = new GridPane();
        mainPain.setHgap(3);
        mainPain.setVgap(1);


        Pane headPane = new Pane();
        HpCavas = new Canvas(screenWidth, screenHeight / 10);
        graphicsComponent = HpCavas.getGraphicsContext2D();
        headPane.getChildren().add(HpCavas);

        graphicsComponent.setFill(Color.BLACK);
        graphicsComponent.fillRect(0, 0, screenWidth, screenHeight / 10);

        Font font = Font.font("Segoe UI Black", FontWeight.BOLD, 25);

        graphicsComponent.setFill(Color.RED);
        graphicsComponent.setFont(font);
        graphicsComponent.fillText("Time: ",30, 30);

        graphicsComponent.setFill(Color.RED);
        graphicsComponent.setFont(font);
        graphicsComponent.fillText("Score: ",screenWidth / 3, 30);


        mainPain.add(headPane, 0, 0);
        mainPain.add(mainCanvas, 0, 1);


        mainContainer = new Group();
        mainContainer.getChildren().addAll(mainPain);

        mainScene = new Scene(mainContainer, screenWidth, screenHeight + 100);
        mainStage = new Stage();
        mainStage.setMinHeight(DEFAULT_HEIGHT + 30);
        mainStage.setMinWidth(DEFAULT_WIDTH);
        mainStage.setScene(mainScene);

        mainCanvas.setScaleX(1);
        mainCanvas.setScaleY(1);
        mainCanvas.setLayoutX(0);
        mainCanvas.setLayoutY(0);
        setFPS(30);
        createMap();
        createPlayer();
        createdHeadMap();
        map.setPlayer(bomber);
        initEventHandler();
        createResizeEventHandle();

        //createGameTime();
        //createUI();
    }

    private void createGameTime() {
        maxTime = 200000000000L;
        startTime = System.nanoTime();
        countTime = maxTime;
    }

    private void createUI() {
        AnchorPane anchorPane = new AnchorPane();
        subScene = new SubScene(anchorPane, DEFAULT_WIDTH, 100);

        timeCounter = new Label(String.format("%d", countTime  / 1000000000));
        try {
            timeCounter.setFont(Font.loadFont(new FileInputStream("src/main/resources/Font/kenvector_future.ttf"), 50));
        } catch (Exception e) {
            System.out.println("cannot");
        }
        timeCounter.setTextFill(Color.WHITE);
        timeCounter.setLayoutX(100);
        timeCounter.setLayoutY(0);

        Shape shape = new Rectangle(0, 0, 800, 100);
        anchorPane.getChildren().add(shape);
        anchorPane.getChildren().add(timeCounter);

        subScene.setLayoutX(0);
        subScene.setLayoutY(500);
        mainContainer.getChildren().add(subScene);
    }

    private void createResizeEventHandle() {
        mainScene.widthProperty().addListener((obs, oldVal, newVal) -> {
            double scaleRatio = (double) newVal / (double) oldVal;
            mainCanvas.setScaleX(mainScene.getWidth() / mainCanvas.getWidth());
            mainCanvas.setScaleY(mainScene.getWidth() / mainCanvas.getWidth());
            mainCanvas.setLayoutX((mainCanvas.getWidth() * (mainCanvas.getScaleX() - 1))/2);
            mainCanvas.setLayoutY((mainCanvas.getHeight() * (mainCanvas.getScaleY() - 1))/2);

            int deltaHeight = (int) (mainCanvas.getHeight() * mainCanvas.getScaleY() - mainCanvas.getHeight());
            map.getCamera().setSize(
                    map.getCamera().getSize().x,
                    (int) (mainCanvas.getHeight() - deltaHeight / 1.5)
                    );
            map.getCamera().setCenter(bomber.getX(), bomber.getY());
        });
        /*mainScene.heightProperty().addListener((obs, oldVal, newVal) -> {
            double scaleRatio = (double) newVal / (double) oldVal;
            mainCanvas.setScaleX(mainScene.getHeight() / mainCanvas.getHeight());
            mainCanvas.setScaleY(mainScene.getHeight() / mainCanvas.getHeight());
            mainCanvas.setLayoutX((mainCanvas.getWidth() * (mainCanvas.getScaleX() - 1))/2);
            mainCanvas.setLayoutY((mainCanvas.getHeight() * (mainCanvas.getScaleY() - 1))/2);
        });*/
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

    private void createdHeadMap() {
        headMap = new HeadMap(bomber, map, (int) screenWidth, (int) screenHeight / 10);
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
        bomber.update();
        //map.getCamera().move(bomber.getMovement().getVelocity());
        map.getCamera().setCenter(bomber.getX(), bomber.getY());
        map.update();
        headMap.update();
        //updateGameTime();
        //updateUI();
    }

    private void updateGameTime() {
        countTime = maxTime - (System.nanoTime() - startTime);
    }

    private void updateUI() {
        timeCounter.setText(String.format("%d", countTime / 1000000000));
    }

    public void render() {
        graphicsContext.clearRect(0, 0, screenWidth, screenHeight);
        graphicsContext.setFill(Paint.valueOf("Green"));
        graphicsContext.fillRect(0, 0, screenWidth, screenHeight);
        Camera camera = map.getCamera();
        graphicsContext.strokeRect(0, 0, camera.getSize().x, camera.getSize().y);
        map.render(graphicsContext);
        bomber.render(graphicsContext);
        headMap.render(graphicsComponent);
    }
}
