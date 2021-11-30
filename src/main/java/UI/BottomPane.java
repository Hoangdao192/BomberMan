package UI;

import Entities.*;
import Entities.Enemy.Enemy;
import Map.Map;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class BottomPane extends AnchorPane {
    private final int DEFAULT_FONT_SIZE = 25;
    private final int DEFAULT_WIDTH = 800;
    private final int DEFAULT_HEIGHT = 70;

    private Map map;
    private Bomber bomber;
    private int width;
    private int height;
    private Rectangle background;

    private Button stopButton;
    private Button homeButton;
    private Button soundButton;
    private Button newMapButton;

    //Mini Map
    private int widthMiniMap;
    private int heightMiniMap;
    private int gridHeight;
    private int gridWidth;
    private int gridSize;
    private final int BASIC_GRID_SIZE = 10;

    Image image_player;
    Image image_brick;
    Image image_enemy;
    Rectangle miniMapRectangle;
    private ArrayList<ArrayList<ImageView>> imageView;
    private ImageView imageViewBomber;
    private ArrayList<ImageView> imageViewEnemy;

    private ArrayList<ArrayList<ArrayList<Entity>>> staticEntityList;
    private ArrayList<Entity> dynamicEntityList;


    // Box Stop
    double widthBox = 400;
    double heightBox = 200;


    //  GETTER, SETTER
    public Button getStopButton() {
        return stopButton;
    }

    public Button getHomeButton() {
        return homeButton;
    }

    public BottomPane(Map map, int width, int height) {
        this.map = map;
        this.bomber = map.getPlayer();
        this.height = height;
        this.width = width;

        this.gridHeight = map.getMapGridHeight();
        this.gridWidth = map.getMapGridWidth();
        this.gridSize = width / 70;
        this.widthMiniMap = gridSize * gridWidth;
        this.heightMiniMap = gridSize * gridHeight;

        setWidth(this.width);
        setHeight(height);

        // Khởi tạo cho miniMap

        try {
            this.image_player = new Image(new FileInputStream("src\\main\\resources\\Graphic\\player_down.png"));
            this.image_brick = new Image(new FileInputStream("src\\main\\resources\\Graphic\\wall.png"));
            this.image_enemy = new Image(new FileInputStream("src\\main\\resources\\Graphic\\balloom_left1.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        this.staticEntityList = map.getStaticEntityList();
        this.dynamicEntityList = map.getDynamicEntityList();

        imageView = new ArrayList< ArrayList<ImageView> >();
        imageViewEnemy = new ArrayList<ImageView>();

        createImage();

        background = new Rectangle(0, 0, width, height);
        background.setFill(Color.CHOCOLATE);

        miniMapRectangle = new Rectangle(0, 0, this.widthMiniMap, this.heightMiniMap);
        miniMapRectangle.setFill(Color.GREEN);

        getChildren().addAll(background, miniMapRectangle);

        for (int i = 0; i < imageView.size(); i++) {
            ArrayList<ImageView> imageViewArrayList = imageView.get(i);
            for (ImageView image_view : imageViewArrayList) {
                getChildren().add(image_view);
            }
        }


        for (ImageView imageView : imageViewEnemy) {
            getChildren().add(imageView);
        }

        getChildren().add(imageViewBomber);


        // Khởi tạo các button
        stopButton = new Button("Stop");
        stopButton.setTextFill(Color.RED);
        stopButton.setLayoutX(this.widthMiniMap + (width - this.widthMiniMap) / 4);
        stopButton.setLayoutY(gridHeight * gridSize / 2 - stopButton.getHeight() / 2);
        stopButton.setFocusTraversable(false);

        homeButton = new Button("Home");
        homeButton.setTextFill(Color.BLUE);
        homeButton.setLayoutX(this.widthMiniMap + (width - this.widthMiniMap) / 4 * 3);
        homeButton.setLayoutY(gridHeight * gridSize / 2 - homeButton.getHeight() / 2);
        homeButton.setFocusTraversable(false);

        soundButton = new Button("Sound");
        soundButton.setTextFill(Color.BLUE);

        newMapButton = new Button("New Map");
        newMapButton.setTextFill(Color.CHOCOLATE);


        getChildren().addAll(stopButton, homeButton);

    }

    public void clickHomeButton() {
        System.out.println("Home Button");
    }

    public void clickStopButton() {

        System.out.println("Stop Button");

        map.getTime().stop();
        //Entity.Stop = true;

        AnchorPane anchorPane = new AnchorPane();
        Rectangle box = new Rectangle(widthBox, heightBox);
        box.setFill(Color.BLUE);

        System.out.println(newMapButton.getLayoutX() + " " + newMapButton.getLayoutY());

        anchorPane.getChildren().addAll(box, soundButton, newMapButton);

        Scene scene = new Scene(anchorPane, widthBox, heightBox);
        Stage newStage = new Stage();
        newStage.setScene(scene);
        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.setMaxWidth(widthBox);
        newStage.setMaxHeight(heightBox);
        newStage.setMinWidth(widthBox);
        newStage.setMinHeight(heightBox);
//        newStage.initOwner(); - thiết lập cửa sổ cha
        newStage.show();

        soundButton.setLayoutX(widthBox / 4 - soundButton.getWidth() / 2);
        soundButton.setLayoutY(heightBox / 2 - soundButton.getHeight() / 2);

        newMapButton.setLayoutX(widthBox / 4 * 3 - newMapButton.getWidth() / 2);
        newMapButton.setLayoutY(heightBox / 2 - newMapButton.getHeight() / 2);

        newStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                map.getTime().present();
                //Entity.Stop = false;
            }
        });
    }

    public void createImage() {

        for (int j = 0; j < gridHeight; j++) {
            for (int i = 0; i < gridWidth; i++) {
                int check = checkMap(i, j);
                int staticEntity = check % 10;

                ArrayList<ImageView> image_view = new ArrayList<>();

                if (staticEntity == 1) {
                    ImageView imageViewStone = new ImageView(image_brick);
                    image_view.add(imageViewStone);
                }

                imageView.add(image_view);
            }
        }

        for (int j = 0; j < gridHeight; j++) {
            for (int i = 0; i < gridWidth; i++) {
                ArrayList<ImageView> imageViewArrayList = imageView.get(i + j * gridWidth);
                for (ImageView image_view : imageViewArrayList) {
                    image_view.setX(i * gridSize);
                    image_view.setY(j * gridSize);
                    image_view.setFitHeight(gridSize);
                    image_view.setFitWidth(gridSize);
                }
            }
        }

        for (Entity entity : dynamicEntityList) {
            if (entity instanceof Enemy) {
                ImageView image_view_enemy = new ImageView(image_enemy);
                image_view_enemy.setX(entity.getGridX() * gridSize);
                image_view_enemy.setY(entity.getGridY() * gridSize);
                image_view_enemy.setFitHeight(gridSize);
                image_view_enemy.setFitWidth(gridSize);
                imageViewEnemy.add(image_view_enemy);
            }
        }

        imageViewBomber = new ImageView(image_player);
        imageViewBomber.setFitHeight(gridSize);
        imageViewBomber.setFitWidth(gridSize);
        imageViewBomber.setX(bomber.getGridX() * gridSize);
        imageViewBomber.setY(bomber.getGridY() * gridSize);
    }

    public int checkMap(int gridX, int gridY) {
        int x = gridX;
        int y = gridY;
        int result = 0;
        int staticEntity = 0;

        ArrayList<Entity> arrayList = staticEntityList.get(y).get(x);
        for (Entity entity : arrayList) {
            if (entity instanceof Stone) {
                staticEntity = 1;
            } else if (entity instanceof Brick) {
                staticEntity = 1;
            }
        }

        result = result * 10 + staticEntity;


        return result;
    }

    public void newMiniMap() {
        imageView.clear();
        imageViewEnemy.clear();
        createImage();
        getChildren().clear();
        getChildren().addAll(background, miniMapRectangle);

        for (int i = 0; i < imageView.size(); i++) {
            ArrayList<ImageView> imageViewArrayList = imageView.get(i);
            for (ImageView image_view : imageViewArrayList) {
                getChildren().add(image_view);
            }
        }


        for (ImageView imageView : imageViewEnemy) {
            getChildren().add(imageView);
        }

        getChildren().add(imageViewBomber);

        getChildren().addAll(stopButton, homeButton);
    }

    public void resize(int newWidth, int newHeight) {
        gridSize = newWidth / 70;

        if (gridSize < BASIC_GRID_SIZE) {
            gridSize = BASIC_GRID_SIZE;
        }

        width = newWidth;
        height = newHeight;

        setWidth(width);
        setHeight(height);

        background.setWidth(width);
        background.setHeight(height);

        widthMiniMap = gridWidth * gridSize;
        heightMiniMap = gridHeight * gridSize;
        miniMapRectangle.setWidth(widthMiniMap);
        miniMapRectangle.setHeight(heightMiniMap);

        stopButton.setLayoutX(this.widthMiniMap + (width - this.widthMiniMap) / 4);
        stopButton.setLayoutY(gridHeight * gridSize / 2 - stopButton.getHeight() / 2);

        homeButton.setLayoutX(this.widthMiniMap + (width - this.widthMiniMap) / 4 * 3);
        homeButton.setLayoutY(gridHeight * gridSize / 2 - homeButton.getHeight() / 2);

        imageViewBomber.setFitHeight(gridSize);
        imageViewBomber.setFitWidth(gridSize);

        for (int j = 0; j < gridHeight; j++) {
            for (int i = 0; i < gridWidth; i++) {
                ArrayList<ImageView> arrayList = imageView.get(i + j * gridWidth);
                for (ImageView imageView : arrayList) {
                    imageView.setFitHeight(gridSize);
                    imageView.setFitWidth(gridSize);
                    imageView.setX(i * gridSize);
                    imageView.setY(j * gridSize);
                }
            }
        }

        int numEnemy = 0;
        for (Entity entity : dynamicEntityList) {
            if (entity instanceof Enemy) {
                if (numEnemy >= imageViewEnemy.size()) {
                    ImageView image_view_enemy = new ImageView(image_enemy);
                    image_view_enemy.setX(entity.getGridX() * gridSize);
                    image_view_enemy.setY(entity.getGridY() * gridSize);
                    image_view_enemy.setFitHeight(gridSize);
                    image_view_enemy.setFitWidth(gridSize);
                    imageViewEnemy.add(image_view_enemy);
                    getChildren().add(image_view_enemy);
                } else {
                    imageViewEnemy.get(numEnemy).setFitHeight(gridSize);
                    imageViewEnemy.get(numEnemy).setFitWidth(gridSize);
                }
                numEnemy++;
            }
        }

    }

    public void update() {

        imageViewBomber.setX(bomber.getGridX() * gridSize);
        imageViewBomber.setY(bomber.getGridY() * gridSize);

        int numEnemy = 0;

        for (Entity entity : dynamicEntityList) {
            if (entity instanceof Enemy) {
                if (numEnemy >= imageViewEnemy.size()) {
                    ImageView image_view_enemy = new ImageView(image_enemy);
                    image_view_enemy.setX(entity.getGridX() * gridSize);
                    image_view_enemy.setY(entity.getGridY() * gridSize);
                    image_view_enemy.setFitHeight(gridSize);
                    image_view_enemy.setFitWidth(gridSize);
                    imageViewEnemy.add(image_view_enemy);
                    getChildren().add(image_view_enemy);
                } else {
                    imageViewEnemy.get(numEnemy).setX(entity.getGridX() * gridSize);
                    imageViewEnemy.get(numEnemy).setY(entity.getGridY() * gridSize);
                }
                numEnemy++;
            }
        }

        for (int i = numEnemy; i < imageViewEnemy.size(); i++) {
            getChildren().remove(imageViewEnemy.get(i));
            imageViewEnemy.remove(i);
        }

        for (int j = 0; j < gridHeight; j++) {
            for (int i = 0; i < gridWidth; i++) {
                int check = checkMap(i, j);
                int staticEntity = check % 10;

                if (staticEntity == 0) {
                    ArrayList<ImageView> arrayList = imageView.get(i + j * gridWidth);
                    for (int t = 0; t < arrayList.size(); t++) {
                        if (arrayList.get(t).getImage() == image_brick) {
                            getChildren().remove(arrayList.get(t));
                            arrayList.remove(t);
                        }
                    }
                }
            }
        }
    }
}