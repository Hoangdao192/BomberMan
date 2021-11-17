package Map;

import Entities.*;
import Entities.Enemy.*;
import Entities.PowerUp.BombUp;
import Entities.PowerUp.Fire;
import Entities.PowerUp.PowerUp;
import Utils.Vector2i;
import javafx.scene.canvas.GraphicsContext;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Map được chia thành các ô vuông có kích thước là gridSize
 */
/**
 * Map Entity
 * #: Wall (Stone)
 * *: Brick
 * Enemies:
 * 1: Balloon (Speed: slow, Smart: low)
 * 2: Oneal (Speed: normal, Smart: normal)
 * 3: Doll (Speed: normal, Smart: low)
 * 4: Minvo (Speed: fast, Smart: normal)
 * 5: Ovapi (Speed: slow, Smart: normal, Special: wall pass)
 * Items:
 * f: PowerUp Fire
 * b: PowerUp BombUp
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
    private Bomber player;

    public Map(String path, int cameraWidth, int cameraHeight) {
        entities = new ArrayList<>();
        staticEntityList = new ArrayList<>();
        dynamicEntityList = new ArrayList<>();
        loadFromFile(path);
        printList();
        createCamera(cameraWidth, cameraHeight);
    }

    public void setPlayer(Bomber player) {
        this.player = player;
    }

    public Bomber getPlayer() {
        return player;
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

    public ArrayList<Entity> getDynamicEntityList() {
        return dynamicEntityList;
    }

    public int getMapGridHeight() {
        return mapGridHeight;
    }

    public int getMapGridWidth() {
        return mapGridWidth;
    }

    public void loadFromFile(String path) {
        try {
            Scanner scanner = new Scanner(new FileReader(path));
            gridSize = scanner.nextInt();
            mapGridWidth = scanner.nextInt();
            mapGridHeight = scanner.nextInt();
            System.out.println(scanner.nextLine());
            for (int rowIndex = 0; rowIndex < mapGridHeight; ++rowIndex) {
                staticEntityList.add(new ArrayList<>());
                String line = scanner.nextLine();
                for (int colIndex = 0; colIndex < line.length(); ++colIndex) {
                    staticEntityList.get(rowIndex).add(new ArrayList<>());
                    switch (line.charAt(colIndex)) {
                        case '#': {
                            addEntity(createStoneEntity(colIndex * gridSize, rowIndex * gridSize));
                            break;
                        }
                        case '*': {
                            addEntity(createBrickEntity(colIndex * gridSize, rowIndex * gridSize));
                            break;
                        }
                        case '1': {
                            addEntity(createOnealEnemy(colIndex * gridSize, rowIndex * gridSize));
                            break;
                        }
                        case '3': {
                            addEntity(createDollEnemy(colIndex * gridSize, rowIndex * gridSize));
                            break;
                        }
                        case '4': {
                            addEntity(createMinvoEnemy(colIndex * gridSize, rowIndex * gridSize));
                            break;
                        }
                        case '5': {
                            addEntity(createOvapiEnemy(colIndex * gridSize, rowIndex * gridSize));
                            break;
                        }
                        case 'b': {
                            addEntity(createBombUpPowerUp(colIndex * gridSize, rowIndex * gridSize));
                            addEntity(createBrickEntity(colIndex * gridSize, rowIndex * gridSize));
                            break;
                        }
                        case 'f': {
                            addEntity(createFirePowerUp(colIndex * gridSize, rowIndex * gridSize));
                            addEntity(createBrickEntity(colIndex * gridSize, rowIndex * gridSize));
                            break;
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Không tìm thấy file cấu hình: " + path);
        }
    }

    /*
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
                        newEntity = createOnealEnemy(col * gridSize, row * gridSize);
                    }
                    else if (tileStyle == 3) {
                        newEntity = createBrickEntity(col * gridSize, row * gridSize);
                    } else if (tileStyle == 4) {
                        newEntity = createFirePowerUp(col * gridSize, row * gridSize);
                    } else if (tileStyle == 5) {
                        newEntity = createBombUpPowerUp(col * gridSize, row * gridSize);
                    }
                    if (newEntity != null) {
                        entities.add(newEntity);
                        if (newEntity instanceof StaticEntity) {
                            staticEntityList.get(row).get(col).add(newEntity);
                        } else {
                            dynamicEntityList.add(newEntity);
                        }
                        if (newEntity instanceof PowerUp) {
                            Entity brick = createBrickEntity(col * gridSize, row * gridSize);
                            entities.add(brick);
                            staticEntityList.get(row).get(col).add(brick);
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Không tìm thấy file: " + path);
        }
    }
     */

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

    //  POWER UP CREATOR
    public Entity createFirePowerUp(int x, int y) {
        Fire fire = new Fire(x, y, gridSize, gridSize, gridSize);
        return fire;
    }

    public Entity createBombUpPowerUp(int x, int y) {
        BombUp bombUp = new BombUp(x, y, gridSize, gridSize, gridSize);
        return bombUp;
    }

    //  ENTITY CREATOR.
    public Entity createOnealEnemy(int x, int y) {
        Oneal oneal = new Oneal(x, y, 32, 32, this);
        return oneal;
    }

    public Entity createDollEnemy(int x, int y) {
        Doll doll = new Doll(x, y, 32, 32, this);
        return doll;
    }

    public Entity createMinvoEnemy(int x, int y) {
        Minvo minvo = new Minvo(x, y, 32, 32, this);
        return minvo;
    }

    public Entity createOvapiEnemy(int x, int y) {
        Ovapi ovapi = new Ovapi(x, y, 32, 32, this);
        return ovapi;
    }

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