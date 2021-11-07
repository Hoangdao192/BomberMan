package Map;

import Component.HitBox;
import Entities.DynamicEntity;
import Entities.Enemy.Balloon;
import Entities.Entity;
import Entities.StaticEntity;
import Entities.Stone;
import Utils.Vector2i;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Map được chia thành các ô vuông có kích thước là gridSize
 */
public class Map {
    private int mapGridWidth;
    private int mapGridHeight;
    private int gridSize;
    private Camera camera;
    //  Danh sách các Entity có trong map.
    private ArrayList<ArrayList<Entity>> entities;

    public Map(String path, int cameraWidth, int cameraHeight) {
        loadMapFromFile(path);
        createCamera(cameraWidth, cameraHeight);
    }

    //  GETTER
    public Vector2i getSize() {
        return new Vector2i(mapGridWidth * gridSize, mapGridHeight * gridSize);
    }

    public Camera getCamera() {
        return camera.clone();
    }

    //  FUNCTIONS
    public void loadMapFromFile(String path) {
        try {
            Scanner scanner = new Scanner(new FileReader(path));
            gridSize = scanner.nextInt();
            mapGridWidth = scanner.nextInt();
            mapGridHeight = scanner.nextInt();

            Image tileSheet = new Image("Graphic/Map/wall.png");
            if (tileSheet == null) System.out.println("false");

            entities = new ArrayList<>();
            for (int row = 0; row < mapGridHeight; ++row) {
                entities.add(new ArrayList<Entity>());
                for (int col = 0; col < mapGridWidth; ++col) {
                    int tileStyle = scanner.nextInt();
                    Entity newEntity;
                    if (tileStyle == 1) {
                        newEntity = createStoneEntity(col * gridSize, row * gridSize);
                    }
                    else if (tileStyle == 2) {
                        newEntity = createBalloonEnemy(col * gridSize, row * gridSize);
                    }
                    else {
                        newEntity = new StaticEntity(
                                col * gridSize, row * gridSize,
                                gridSize, gridSize, null
                        );
                    }
                    entities.get(row).add(newEntity);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Không tìm thấy file: " + path);
        }
    }

    //  ENTITY CREATOR.
    /**
     * Tạo tảng đá.
     */
    public Entity createStoneEntity(int x, int y) {
        Image tileSheet = new Image("Graphic/Map/wall.png");
        Stone stone = new Stone(
                x, y, gridSize, gridSize,
                tileSheet, new Rectangle2D(0, 0, 32, 32)
        );
        stone.setCollision(true);
        stone.createHitBox(0, 0, 32, 32);
        return stone;
    }

    /**
     * Tạo kẻ địch Balloon.
     */
    public Entity createBalloonEnemy(int x, int y) {
        Balloon balloon = new Balloon(x, y, 24, 24, this);
        balloon.createHitBox(0,0, 24, 24);
        ((DynamicEntity) balloon).getMovement().setSpeed(10);
        return balloon;
    }

    public void createCamera(int width, int height) {
        this.camera = new Camera(
                0, 0, width, height,
                gridSize * mapGridWidth,
                gridSize * mapGridHeight
        );
    }

    public ArrayList<ArrayList<Entity>> getEntityList() {
        return entities;
    }

    public void moveCamera(Vector2i velocity) {
        camera.move(velocity);
    }

    public void updateEntity() {
        for (int row = 0; row < entities.size(); ++row) {
            for (int col = 0; col < entities.get(row).size(); ++col) {
                Entity currentEntity = entities.get(row).get(col);
                currentEntity.update();
            }
        }
    }

    public void update() {
        camera.update();
        updateEntity();
    }

    /**
     * Render tất cả các entity nằm trong vùng camera.
     */
    public void render(GraphicsContext graphicsContext) {
        for (int row = 0; row < entities.size(); ++row) {
            for (int col = 0; col < entities.get(row).size(); ++col) {
                Entity currentEntity = entities.get(row).get(col);
                //  Không render nếu Entity nằm ngoài vùng camera
                if (currentEntity.getX() >= camera.getEnd().x
                    || currentEntity.getX() + currentEntity.getWidth() <= camera.getStart().x
                    || currentEntity.getY() >= camera.getEnd().y
                    || currentEntity.getY() + currentEntity.getHeight() <= camera.getStart().y) {
                    continue;
                }

                currentEntity.render(
                        currentEntity.getX() - camera.getStart().x,
                        currentEntity.getY() - camera.getStart().y,
                        graphicsContext
                );
            }
        }
    }
}
