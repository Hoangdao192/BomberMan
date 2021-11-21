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
 * Bomber
 * P: Player
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

    //Check transfer
    private boolean transfer = false;

    //  Danh sách các Entity có trong map.
    private ArrayList<Entity> entities;
    private ArrayList<ArrayList<ArrayList<Entity>>> staticEntityList;
    private ArrayList<Entity> dynamicEntityList;
    private Bomber player;
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
        time = new Time();
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

    public boolean isTransfer() {
        return transfer;
    }

    public void setTransfer(boolean transfer) {
        this.transfer = transfer;
    }

    public Time getTime() {
        return time;
    }

    public Vector2i getSize() {
        return new Vector2i(mapGridWidth * gridSize, mapGridHeight * gridSize);
    }

    public Camera getCamera() {
        return camera;
    }

    public int getGridSize() {
        return gridSize;
    }

    public ArrayList<Entity> getEntityList() {
        return entities;
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

    public void newMap() {
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
        resetBonus();
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
                        case 's': {
                            addEntity(createSpeedUpPowerUp(colIndex * gridSize, rowIndex * gridSize));
                            addEntity(createBrickEntity(colIndex * gridSize, rowIndex * gridSize));
                            break;
                        }
                        case 'f': {
                            addEntity(createFirePowerUp(colIndex * gridSize, rowIndex * gridSize));
                            addEntity(createBrickEntity(colIndex * gridSize, rowIndex * gridSize));
                            break;
                        }
                        case 'p': {
                            addEntity(createPortalEntity(colIndex * gridSize, rowIndex * gridSize));
                            addEntity(createBrickEntity(colIndex * gridSize, rowIndex * gridSize));
                            break;
                        }
                        case 'B': {
                            addEntity(createBombPassPowerUp(colIndex * gridSize, rowIndex * gridSize));
                            addEntity(createBrickEntity(colIndex * gridSize, rowIndex * gridSize));
                            break;
                        }
                        case 'F': {
                            addEntity(createFlamePassPowerUp(colIndex * gridSize, rowIndex * gridSize));
                            addEntity(createBrickEntity(colIndex * gridSize, rowIndex * gridSize));
                            break;
                        }
                        case 'W': {
                            addEntity(createWallPassPowerUp(colIndex * gridSize, rowIndex * gridSize));
                            addEntity(createBrickEntity(colIndex * gridSize, rowIndex * gridSize));
                            break;
                        }
                        case 'D': {
                            addEntity(createDetonatorPowerUp(colIndex * gridSize, rowIndex * gridSize));
                            addEntity(createBrickEntity(colIndex * gridSize, rowIndex * gridSize));
                            break;
                        }
                        case 'M': {
                            addEntity(createMysteryPowerUp(colIndex * gridSize, rowIndex * gridSize));
                            addEntity(createBrickEntity(colIndex * gridSize, rowIndex * gridSize));
                            break;
                        }
                        case 'P': {
                            if (player != null) {
                                player.setX(colIndex * gridSize);
                                player.setY(rowIndex * gridSize);
                            }
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

    // Check Bonus
    public boolean[] getCheckBonus() {
        return checkBonus;
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

    //  POWER UP CREATOR
    public Entity createFirePowerUp(int x, int y) {
        Fire fire = new Fire(x, y, gridSize, gridSize, gridSize, this);
        return fire;
    }

    public Entity createBombUpPowerUp(int x, int y) {
        BombUp bombUp = new BombUp(x, y, gridSize, gridSize, gridSize, this);
        return bombUp;
    }

    public Entity createSpeedUpPowerUp(int x, int y) {
        SpeedUp speedUp = new SpeedUp(x, y, gridSize, gridSize, gridSize, this);
        return speedUp;
    }

    public Entity createBombPassPowerUp(int x, int y) {
        BombPass bombPass = new BombPass(x, y, gridSize, gridSize, gridSize, this);
        return bombPass;
    }

    public Entity createDetonatorPowerUp(int x, int y) {
        Detonator detonator = new Detonator(x, y, gridSize, gridSize, gridSize, this);
        return detonator;
    }

    public Entity createMysteryPowerUp(int x, int y) {
        Mystery mystery = new Mystery(x, y, gridSize, gridSize, gridSize, this);
        return mystery;
    }

    public Entity createFlamePassPowerUp(int x, int y) {
        FlamePass flamePass = new FlamePass(x, y, gridSize, gridSize, gridSize, this);
        return flamePass;
    }

    public Entity createWallPassPowerUp(int x, int y) {
        WallPass wallPass = new WallPass(x, y, gridSize, gridSize, gridSize, this);
        return wallPass;
    }

    //  ENTITY CREATOR.
    public Entity createOnealEnemy(int x, int y) {
        Oneal oneal = new Oneal(x, y, gridSize - 2, gridSize - 2, this);
        return oneal;
    }

    public Entity createDollEnemy(int x, int y) {
        Doll doll = new Doll(x, y, 32, 32, this);
        return doll;
    }

    public Entity createMinvoEnemy(int x, int y) {
        Minvo minvo = new Minvo(x, y, gridSize - 2, gridSize - 2, this);
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

    public Entity createPortalEntity(int x, int y) {
        Portal portal = new Portal(x, y, gridSize, gridSize, gridSize, this);
        return portal;
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

    public void moveCamera(Vector2i velocity) {
        camera.move(velocity);
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
                if (currentEntity instanceof Enemy) {
                    numEnemyExist ++;
                }
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
//        System.out.println("NumBrickDestroy : " + numBrickDestoy);
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