package Map;

import Component.Time;
import Entities.*;
import Entities.Enemy.*;
import Entities.PowerUp.*;
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
 * gridSize, width, height
 * $: player
 * Map Entity
 * #: Wall (Stone)
 * *: Brick
 * p: Portal
 * Enemies:
 * 1: Balloon (Speed: slow, Smart: low)
 * 2: Oneal (Speed: normal, Smart: normal)
 * 3: Doll (Speed: normal, Smart: low)
 * 4: Minvo (Speed: fast, Smart: normal)
 * 5: Ovapi (Speed: slow, Smart: normal, Special: wall pass)
 * 6: Pontan (Speed: fast, Smart: high, Special: wall pass)
 * 7: Kondoria (Speed: slowest, Smart: high, Special: wall pass)
 * Items:
 * f: PowerUp Fire
 * b: PowerUp BombUp
 * s: PowerUp SpeedUp
 * B: BombPass
 * D: Detonator
 * M: Mystery
 * W: WallPass
 * F: FlamePass
 */
public class Map {
    private String path = "";
    private int mapGridWidth;
    private int mapGridHeight;
    private int gridSize;
    private Camera camera;
    private EntityCreator entityCreator;

    //  Danh sách các Entity có trong map.
    private ArrayList<Entity> entities;
    private ArrayList<ArrayList<ArrayList<Entity>>> staticEntityList;
    private ArrayList<Entity> dynamicEntityList;

    private Vector2i playerStartPosition = new Vector2i(100, 100);
    private Bomber player;

    //Check transfer
    private boolean transfer = false;

    private Time time;

    //check Bonus
    private boolean[] checkBonus = new boolean[6];

    private boolean checkTarget = false;
    private boolean checkColaBottle = false;
    private boolean checkDezeniman_san = false;
    private boolean checkFamicom = false;
    private boolean checkGoddessMask = false;
    private boolean checkNakamoto_san = false;

    private int numEnemyDie = 0;
    private int numEnemyExist = 0;
    private int numBrickDestoy = 0;
    private int numBrickExist = 0;

    private boolean[][] checkMap;

    public Map(String path, int cameraWidth, int cameraHeight) {
        this.path = path;
        entities = new ArrayList<>();
        staticEntityList = new ArrayList<>();
        dynamicEntityList = new ArrayList<>();
        entityCreator = new EntityCreator(this);
        loadFromFile(path);
        printList();
        createCamera(cameraWidth, cameraHeight);
        time = new Time();
    }

    public void setPlayer(Bomber player) {
        this.player = player;
        player.setX(playerStartPosition.x);
        player.setY(playerStartPosition.y);
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

    public Vector2i getPlayerStartPosition() {
        return playerStartPosition;
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

    public boolean isTransfer() {
        return transfer;
    }

    public void setTransfer(boolean transfer) {
        this.transfer = transfer;
    }

    public Time getTime() {
        return time;
    }

    public ArrayList<Entity> getEntityList() {
        return entities;
    }

    // Check Bonus
    public boolean[] getCheckBonus() {
        return checkBonus;
    }

    //  FUNCTIONS
    public void newMap() {
        //  Tính toán bonus trước khi chuyển map
        if (!transfer) {
            update();
            checkBonus[0] = checkTarget;
            checkBonus[1] = checkColaBottle;
            checkBonus[2] = checkDezeniman_san;
            checkBonus[3] = checkFamicom;
            checkBonus[4] = checkGoddessMask;
            checkBonus[5] = checkNakamoto_san;
            transfer = true;
            return;
        }
        time.reset();

        entities.clear();
        staticEntityList.clear();
        dynamicEntityList.clear();
        //resetBonus();
        loadFromFile(path);
    }

    public void loadFromFile(String path) {
        try {
            Scanner scanner = new Scanner(new FileReader(path));
            gridSize = scanner.nextInt();
            mapGridWidth = scanner.nextInt();
            mapGridHeight = scanner.nextInt();
            checkMap = new boolean[mapGridWidth][mapGridHeight];
            for (int i = 0; i < mapGridWidth; i++) {
                for (int j = 0; j < mapGridHeight; j++) {
                    checkMap[i][j] = false;
                }
            }
            scanner.nextLine();
            for (int rowIndex = 0; rowIndex < mapGridHeight; ++rowIndex) {
                staticEntityList.add(new ArrayList<>());
                String line = scanner.nextLine();
                for (int colIndex = 0; colIndex < line.length(); ++colIndex) {
                    staticEntityList.get(rowIndex).add(new ArrayList<>());
                    createEntity(line.charAt(colIndex), colIndex, rowIndex);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Không tìm thấy file cấu hình: " + path);
        }
    }

    private void createEntity(char type, int gridX, int gridY) {
        switch (type) {
            case '$': {
                playerStartPosition.x = gridX * gridSize;
                playerStartPosition.y = gridY * gridSize;
                break;
            }
            case '#': {
                addEntity(entityCreator.createStoneEntity(
                        gridX * gridSize, gridY * gridSize, gridSize, gridSize));
                break;
            }
            case '*': {
                addEntity(entityCreator.createBrickEntity(
                        gridX * gridSize, gridY * gridSize, gridSize, gridSize));
                break;
            }
            //  ENEMIES
            case '1': {
                addEntity(entityCreator.createBalloonEnemy(
                        gridX * gridSize, gridY * gridSize, gridSize, gridSize));
                break;
            }
            case '2': {
                addEntity(entityCreator.createOnealEnemy(
                        gridX * gridSize, gridY * gridSize, gridSize, gridSize));
                break;
            }
            case '3': {
                addEntity(entityCreator.createDollEnemy(
                        gridX * gridSize, gridY * gridSize, gridSize, gridSize));
                break;
            }
            case '4': {
                addEntity(entityCreator.createMinvoEnemy(
                        gridX * gridSize, gridY * gridSize, gridSize, gridSize));
                break;
            }
            case '5': {
                addEntity(entityCreator.createOvapiEnemy(
                        gridX * gridSize, gridY * gridSize, gridSize, gridSize));
                break;
            }
            case '6': {
                addEntity(entityCreator.createPontanEnemy(
                        gridX * gridSize, gridY * gridSize, gridSize - 2, gridSize - 2));
                break;
            }
            case '7': {
                addEntity(entityCreator.createKondoriaEnemy(
                        gridX * gridSize, gridY * gridSize, gridSize - 2, gridSize - 2));
                break;
            }
            //  POWER UPS
            case 'b': {
                addEntity(entityCreator.createBombUpPowerUp(
                        gridX * gridSize, gridY * gridSize, gridSize, gridSize));
                addEntity(entityCreator.createBrickEntity(
                        gridX * gridSize, gridY * gridSize, gridSize, gridSize));
                break;
            }
            case 's': {
                addEntity(entityCreator.createSpeedUpPowerUp(
                        gridX * gridSize, gridY * gridSize, gridSize, gridSize));
                addEntity(entityCreator.createBrickEntity(
                        gridX * gridSize, gridY * gridSize, gridSize, gridSize));
                break;
            }
            case 'f': {
                addEntity(entityCreator.createFirePowerUp(
                        gridX * gridSize, gridY * gridSize, gridSize, gridSize));
                addEntity(entityCreator.createBrickEntity(
                        gridX * gridSize, gridY * gridSize, gridSize, gridSize));
                break;
            }
            case 'p': {
                addEntity(entityCreator.createPortalEntity(
                        gridX * gridSize, gridY * gridSize, gridSize, gridSize));
                addEntity(entityCreator.createBrickEntity(
                        gridX * gridSize, gridY * gridSize, gridSize, gridSize));
                break;
            }
            case 'B': {
                addEntity(entityCreator.createBombPassPowerUp(
                        gridX * gridSize, gridY * gridSize, gridSize, gridSize));
                addEntity(entityCreator.createBrickEntity(
                        gridX * gridSize, gridY * gridSize, gridSize, gridSize));
                break;
            }
            case 'F': {
                addEntity(entityCreator.createFlamePassPowerUp(
                        gridX * gridSize, gridY * gridSize, gridSize, gridSize));
                addEntity(entityCreator.createBrickEntity(
                        gridX * gridSize, gridY * gridSize, gridSize, gridSize));
                break;
            }
            case 'W': {
                addEntity(entityCreator.createWallPassPowerUp(
                        gridX * gridSize, gridY * gridSize, gridSize, gridSize));
                addEntity(entityCreator.createBrickEntity(
                        gridX * gridSize, gridY * gridSize, gridSize, gridSize));
                break;
            }
            case 'D': {
                addEntity(entityCreator.createDetonatorPowerUp(
                        gridX * gridSize, gridY * gridSize, gridSize, gridSize));
                addEntity(entityCreator.createBrickEntity(
                        gridX * gridSize, gridY * gridSize, gridSize, gridSize));
                break;
            }
            case 'M': {
                addEntity(entityCreator.createMysteryPowerUp(
                        gridX * gridSize, gridY * gridSize, gridSize, gridSize));
                addEntity(entityCreator.createBrickEntity(
                        gridX * gridSize, gridY * gridSize, gridSize, gridSize));
                break;
            }
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

    public void moveCamera(Vector2i velocity) {
        camera.move(velocity);
    }

    public void checkTarget() {
        if (numEnemyDie == 0) {
            checkTarget = true;
        } else {
            checkTarget = false;
        }
    }

    public void checkNakamoto_san() {
        if (numEnemyExist == 0 && numBrickDestoy == 0) {
            checkNakamoto_san = true;
        } else {
            checkNakamoto_san = false;
        }
    }

    public void checkGoddessMask() {
        if (numEnemyExist != 0) {
            return;
        }
        for (int i = 0; i < mapGridWidth; i++) {
            for (int j = 0; j < mapGridHeight; j++) {
                if (j == 1 && i != 0 && i != mapGridWidth - 1) {
                    if (!checkMap[i][j]) {
//                        System.out.println(i + ":" + j);
                        return;
                    }
                }
                if (j == mapGridHeight - 2 && i != 0 && i != mapGridWidth - 1) {
                    if (!checkMap[i][j]) {
//                        System.out.println(i + ":" + j);
                        return;
                    }
                }
                if (i == 1 && j != 0 && j != mapGridHeight - 1) {
                    if (!checkMap[i][j]) {
//                        System.out.println(i + ":" + j);
                        return;
                    }
                }
                if (i == mapGridWidth - 2 && j != 0 && j != mapGridHeight - 1) {
                    if (!checkMap[i][j]) {
//                        System.out.println(i + ":" + j);
                        return;
                    }
                }
//                if (checkMap[i][j] == true) {
//                    System.out.print("T ");
//                }
//                else {
//                    System.out.println("  ");
//                }
            }
//            System.out.println();
        }
        checkGoddessMask = true;
    }

    public void checkColaBottle() {
        checkColaBottle = Portal.checkColaBottle;
        return;
    }

    public void checkDezeniman_san() {
        checkDezeniman_san = true;
        return;
    }

    public void checkFamicom() {
        checkFamicom = true;
        return;
    }

    public void checkBooleanMap(int x, int y) {
        checkMap[ (x + gridSize / 2) / gridSize][ (y + gridSize / 2) / gridSize] = true;
    }

    public void resetBooleanMap() {
        for (int i = 0; i < mapGridWidth; i++) {
            for (int j = 0; j < mapGridHeight; j++) {
                checkMap[i][j] = false;
            }
        }
    }

    public void resetBonus() {
        numEnemyDie = 0;
        numBrickDestoy = 0;
        checkTarget = false;
        checkColaBottle = false;
        checkDezeniman_san = false;
        checkFamicom = false;
        checkGoddessMask = false;
        checkNakamoto_san = false;
    }

    public void updateBonus() {
        checkTarget();
        checkDezeniman_san();
        if (numEnemyExist == 0) {
            checkBooleanMap(player.getX(), player.getY());
            checkColaBottle();
            checkGoddessMask();
            checkNakamoto_san();
            checkFamicom();
        } else {
            resetBooleanMap();
        }
    }

    public void updateEntity() {
        numBrickExist = 0;
        numEnemyExist = 0;
        for (int i = 0; i < entities.size(); ) {
            Entity currentEntity = entities.get(i);
            if (!currentEntity.isExist()) {
                if (currentEntity instanceof Enemy) {
                    player.getScore().addScore(((Enemy) currentEntity).getScore());
                    numEnemyDie++;
                }
                entities.remove(i);
            } else {
                currentEntity.update();
                ++i;
            }
        }
        //  Dynamic entity
        for (int i = 0; i < dynamicEntityList.size();) {
            if (!dynamicEntityList.get(i).isExist()) {
                if (dynamicEntityList.get(i) instanceof Enemy) {
                    numEnemyExist ++;
                }
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
                        if (staticEntityList.get(i).get(j).get(k) instanceof Brick) {
                            numBrickDestoy ++;
                        }
                        staticEntityList.get(i).get(j).remove(k);
                    } else {
                        if (staticEntityList.get(i).get(j).get(k) instanceof Brick) {
                            numBrickExist ++;
                        }
                        ++k;
                    }
                }
            }
        }
    }

    public void update() {
        camera.update();
        updateEntity();
        updateBonus();
    }

    /**
     * Render tất cả các entity nằm trong vùng camera.
     */
    public void render(GraphicsContext graphicsContext) {
       /* for (int i = 0; i < entities.size(); ++i) {
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
        }*/

        for (int i = 0; i < staticEntityList.size(); ++i) {
            for (int j = 0; j < staticEntityList.get(i).size(); ++j) {
                for (int k = 0; k < staticEntityList.get(i).get(j).size(); ++k) {
                    Entity currentEntity = staticEntityList.get(i).get(j).get(k);
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
        for (int i = 0; i < dynamicEntityList.size(); ++i) {
            Entity currentEntity = dynamicEntityList.get(i);
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