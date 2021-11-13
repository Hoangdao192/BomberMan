package Map;

import Entities.*;
import Entities.Enemy.Balloon;
import Utils.Vector2i;
import javafx.scene.canvas.GraphicsContext;

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
    private ArrayList<Entity> entities;
    private ArrayList<ArrayList<ArrayList<Entity>>> staticEntityList;
    private ArrayList<Entity> dynamicEntityList;

    public Map(String path, int cameraWidth, int cameraHeight) {
        staticEntityList = new ArrayList<>();
        dynamicEntityList = new ArrayList<>();
        loadMapFromFile(path);
        printList();
        createCamera(cameraWidth, cameraHeight);
    }

    //  GETTER
    public Vector2i getSize() {
        return new Vector2i(mapGridWidth * gridSize, mapGridHeight * gridSize);
    }

    public Camera getCamera() {
        return camera;
    }

    public int getGridSize() {
        return gridSize;
    }

    public ArrayList<ArrayList<ArrayList<Entity>>> getStaticEntityList() {
        return staticEntityList;
    }

    public int getMapGridHeight() {
        return mapGridHeight;
    }

    public int getMapGridWidth() {
        return mapGridWidth;
    }

    //  FUNCTIONS
    public void loadMapFromFile(String path) {
        try {
            Scanner scanner = new Scanner(new FileReader(path));
            gridSize = scanner.nextInt();
            mapGridWidth = scanner.nextInt();
            mapGridHeight = scanner.nextInt();

            entities = new ArrayList<>();
            for (int row = 0; row < mapGridHeight; ++row) {
                staticEntityList.add(new ArrayList<>());
                for (int col = 0; col < mapGridWidth; ++col) {
                    staticEntityList.get(row).add(new ArrayList<>());
                    int tileStyle = scanner.nextInt();
                    Entity newEntity = null;
                    if (tileStyle == 1) {
                        newEntity = createStoneEntity(col * gridSize, row * gridSize);
                    }
                    else if (tileStyle == 2) {
                        newEntity = createBalloonEnemy(col * gridSize, row * gridSize);
                    }
                    else if (tileStyle == 3) {
                        newEntity = createBrickEntity(col * gridSize, row * gridSize);
                    }
                    if (newEntity != null) {
                        entities.add(newEntity);
                        if (newEntity instanceof StaticEntity) {
                            staticEntityList.get(row).get(col).add(newEntity);
                        } else {
                            dynamicEntityList.add(newEntity);
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Không tìm thấy file: " + path);
        }
    }

    public void printList() {
        for (int i = 0; i < staticEntityList.size(); ++i) {
            for (int j = 0; j < staticEntityList.get(i).size(); ++j) {
                if (staticEntityList.get(i).get(j).size() == 0) {
                    System.out.print("0 ");
                } else {
                    System.out.print("1 ");
                }
            }
            System.out.println();
        }

        System.out.println(dynamicEntityList.size());
    }

    //  ENTITY CREATOR.
    public Entity createBrickEntity(int x, int y) {
        Brick brick = new Brick(x, y, gridSize, gridSize, gridSize);
        brick.createHitBox(0, 0, 32, 32);
        return brick;
    }

    /**
     * Tạo tảng đá.
     */
    public Entity createStoneEntity(int x, int y) {
        Stone stone = new Stone(x, y, gridSize, gridSize, gridSize);
        stone.setCollision(true);
        stone.createHitBox(0, 0, 32, 32);
        return stone;
    }

    /**
     * Tạo kẻ địch Balloon.
     */
    public Entity createBalloonEnemy(int x, int y) {
        Balloon balloon = new Balloon(x, y, 32, 32, this);
        return balloon;
    }

    public void addEntity(Entity entity) {
        if (entity == null) {
            return;
        }
        entities.add(entity);
        if (entity instanceof StaticEntity) {
            staticEntityList.get(entity.getGridY()).get(entity.getGridX()).add(entity);
        } else if (entity instanceof DynamicEntity) {
            dynamicEntityList.add(entity);
        }
    }

    public void createCamera(int width, int height) {
        this.camera = new Camera(
                0, 0, width, height,
                gridSize * mapGridWidth,
                gridSize * mapGridHeight
        );
    }

    public ArrayList<Entity> getEntityList() {
        return entities;
    }

    public void moveCamera(Vector2i velocity) {
        camera.move(velocity);
    }

    public void updateEntity() {
        for (int i = 0; i < entities.size(); ) {
            Entity currentEntity = entities.get(i);
            if (!currentEntity.isExist()) {
                entities.remove(i);
            } else {
                currentEntity.update();
                ++i;
            }
        }
        //  Dynamic entity
        for (int i = 0; i < dynamicEntityList.size();) {
            if (!dynamicEntityList.get(i).isExist()) {
                dynamicEntityList.remove(i);
            } else {
                ++i;
            }
        }
        //  Static entity
        for (int i = 0; i < staticEntityList.size(); ++i) {
            for (int j = 0; j < staticEntityList.get(i).size(); ++j) {
                for (int k = 0; k < staticEntityList.get(i).get(j).size();) {
                    if (!staticEntityList.get(i).get(j).get(k).isExist()) {
                        staticEntityList.get(i).get(j).remove(k);
                    } else {
                        ++k;
                    }
                }
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
        for (int i = 0; i < entities.size(); ++i) {
            Entity currentEntity = entities.get(i);
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