package Map;

import Component.Time;
import Entities.*;
import Entities.BonusIteam.*;
import Entities.Enemy.*;
import Utils.RandomInt;
import Utils.Vector2i;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;

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
 * 8: Pass (Speed: fast, Smart: high)
 * Items:
 * f: PowerUp Fire
 * b: PowerUp BombUp
 * s: PowerUp SpeedUp
 * B: BombPass
 * D: Detonator
 * M: Mystery
 * W: WallPass
 * F: FlamePass
 * I: Bonus Item
 * !: Bonus Target
 * @: Cola Bottle
 * /: Dezeniman_san
 * %: Famicom
 * ^: Goddess Mask
 * &: Nakamoto_san
 */
public class Map {
    private String path = "";

    private int mapGridWidth;
    private int mapGridHeight;
    private int gridSize;
    private Camera camera;
    private EntityCreator entityCreator;
    private int maxTime = 200;
    private boolean timeOver = false;

    //  Danh sách các Entity có trong map.
    private ArrayList<Entity> entities;
    private ArrayList<ArrayList<ArrayList<Entity>>> staticEntityList;
    private ArrayList<Entity> dynamicEntityList;

    private Vector2i playerStartPosition = new Vector2i(100, 100);
    private Bomber player;
    private Portal portal;

    private boolean levelPass = false;

    private Time time;

    //check Bonus
    private boolean[] checkBonus = new boolean[6];

    private boolean checkTarget = true;
    private boolean checkColaBottle = true;
    private boolean checkDezeniman_san = true;
    private boolean checkFamicom = true;
    private boolean checkGoddessMask = true;
    private boolean checkNakamoto_san = true;

    private int numEnemyDie = 0;
    private int numEnemyExist = 0;
    private int numBrickDestoy = 0;
    private int numBrickExist = 0;

    private boolean[][] checkMap;

    //Đếm số bomb nổ sau khi giết hết boss
    private int numBombExplosion = 0;
    private int timeBombExplosion = 0;
    private boolean hasBombExplosionBefore = false;

    public Map(String path, int cameraWidth, int cameraHeight) {
        this.path = path;
        entities = new ArrayList<>();
        staticEntityList = new ArrayList<>();
        dynamicEntityList = new ArrayList<>();
        entityCreator = new EntityCreator(this);
        loadFromFile(path);
        //printList();
        createCamera(cameraWidth, cameraHeight);
        time = new Time();
    }

    //  SETTER
    public void setLevelPass(boolean levelPass) {
        this.levelPass = levelPass;
        if (levelPass) {
            time.stop();
        }
    }

    public void setPlayer(Bomber player) {
        this.player = player;
        player.setX(playerStartPosition.x);
        player.setY(playerStartPosition.y);
    }

    public void setNumBombExplosion(int numBombExplosion) {
        this.numBombExplosion = numBombExplosion;
    }

    public void setTimeBombExplosion(int timeBombExplosion) {
        this.timeBombExplosion = timeBombExplosion;
    }

    public void setHasBombExplosionBefore(boolean hasBombExplosionBefore) {
        this.hasBombExplosionBefore = hasBombExplosionBefore;
    }

    public Bomber getPlayer() {
        return player;
    }

    //  GETTER
    public boolean isLevelPass() {
        return levelPass;
    }

    public int getNumEnemyExist() {
        return numEnemyExist;
    }

    public int getNumBombExplosion() {
        return numBombExplosion;
    }

    public int getTimeBombExplosion() {
        return timeBombExplosion;
    }

    public boolean isHasBombExplosionBefore() {
        return hasBombExplosionBefore;
    }

    public int getMaxTime() {
        return maxTime;
    }

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

    public void createEntity(char type, int gridX, int gridY) {
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
                        gridX * gridSize, gridY * gridSize, gridSize - 2, gridSize - 2));
                break;
            }
            case '2': {
                addEntity(entityCreator.createOnealEnemy(
                        gridX * gridSize, gridY * gridSize, gridSize - 2, gridSize - 2));
                break;
            }
            case '3': {
                addEntity(entityCreator.createDollEnemy(
                        gridX * gridSize, gridY * gridSize, gridSize - 2, gridSize - 2));
                break;
            }
            case '4': {
                addEntity(entityCreator.createMinvoEnemy(
                        gridX * gridSize, gridY * gridSize, gridSize - 2, gridSize - 2));
                break;
            }
            case '5': {
                addEntity(entityCreator.createOvapiEnemy(
                        gridX * gridSize, gridY * gridSize, gridSize - 2, gridSize - 2));
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
            case '8': {
                addEntity(entityCreator.createPassEnemy(
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
                portal = (Portal) entityCreator.createPortalEntity(
                        gridX * gridSize, gridY * gridSize, gridSize, gridSize);
                addEntity(portal);
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
            case '!': {
                addEntity(entityCreator.createBonusTarget(
                        gridX * gridSize, gridY * gridSize, gridSize, gridSize));
                break;
            }
            case '@': {
                addEntity(entityCreator.createColaBottle(
                        gridX * gridSize, gridY * gridSize, gridSize, gridSize));
                break;
            }
            case '/': {
                addEntity(entityCreator.createDezeniman_san(
                        gridX * gridSize, gridY * gridSize, gridSize, gridSize));
                break;
            }
            case '%': {
                addEntity(entityCreator.createFamicom(
                        gridX * gridSize, gridY * gridSize, gridSize, gridSize));
                break;
            }
            case '^': {
                addEntity(entityCreator.createGoddessMask(
                        gridX * gridSize, gridY * gridSize, gridSize, gridSize));
                break;
            }
            case '&': {
                addEntity(entityCreator.createNakamoto_san(
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

    private void whenTimeToZero() {
        if (timeOver) {
            return;
        }
        timeOver = true;
        final int NUMBER_OF_ENEMY = 10;
        int enemyCount = 0;

        //  Chuyển các Enemy thành Pontan
        for (int i = 0; i < dynamicEntityList.size();) {
            if (!(dynamicEntityList.get(i) instanceof Pontan)) {
                createEntity('6', dynamicEntityList.get(i).getGridX(), dynamicEntityList.get(i).getGridY());
                dynamicEntityList.remove(i);
                ++enemyCount;
            } else {
                ++i;
            }
        }

        //  Nếu số lượng Pontan chưa đủ thì thêm vào
        for (int i = enemyCount; i <= NUMBER_OF_ENEMY; ++i) {
            Vector2i blankTile = getRandomBlankTile();
            if (blankTile != null) {
                createEntity('6', blankTile.x, blankTile.y);
            }
        }
    }

    private Vector2i getRandomBlankTile() {
        int gridX = -1;
        int gridY = -1;
        boolean continueRandom = true;
        int randomCount = 0;
        while (continueRandom) {
            ++randomCount;
            if (randomCount >= mapGridWidth * mapGridHeight) {
                continueRandom = false;
            }
            int tempX = RandomInt.random(0, mapGridWidth - 1);
            int tempY = RandomInt.random(0, mapGridHeight - 1);
            if (staticEntityList.get(tempY).get(tempX).size() == 0) {
                gridX = tempX;
                gridY = tempY;
                continueRandom = false;
            }
        }

        if (gridX == -1 || gridY == -1) {
            return null;
        }
        return new Vector2i(gridX, gridY);
    }

    // Kiểm tra các Bonus
    public void checkTarget() {
        if (checkTarget) {
            if (numEnemyDie == 0 && player.isPassOverPortal()) {
                Vector2i blankTile = getBlankTile();
                if (blankTile != null) {
                    createEntity('!',blankTile.x, blankTile.y);
                }
                checkTarget = false;
            }
        }
    }

    public void checkNakamoto_san() {
        if (checkNakamoto_san) {
            if (numEnemyExist == 0 && numBrickDestoy == 0) {
                Vector2i blankTile = getBlankTile();
                if (blankTile != null) {
                    createEntity('&',blankTile.x, blankTile.y);
                }
                checkNakamoto_san = false;
            }
        }
    }

    public void checkGoddessMask() {
        if (checkGoddessMask) {
            if (numEnemyExist != 0) {
                return;
            }
            for (int i = 0; i < mapGridWidth; i++) {
                for (int j = 0; j < mapGridHeight; j++) {
                    if (j == 1 && i != 0 && i != mapGridWidth - 1) {
                        if (!checkMap[i][j]) {
                            return;
                        }
                    }
                    if (j == mapGridHeight - 2 && i != 0 && i != mapGridWidth - 1) {
                        if (!checkMap[i][j]) {
                            return;
                        }
                    }
                    if (i == 1 && j != 0 && j != mapGridHeight - 1) {
                        if (!checkMap[i][j]) {
                            return;
                        }
                    }
                    if (i == mapGridWidth - 2 && j != 0 && j != mapGridHeight - 1) {
                        if (!checkMap[i][j]) {
                            return;
                        }
                    }
                }
            }
            Vector2i blankTile = getBlankTile();
            if (blankTile != null) {
                createEntity('^',blankTile.x, blankTile.y);
            }
            checkGoddessMask = false;
        }
    }

    public void checkColaBottle() {
        if (checkColaBottle) {
            if(portal.isCheckColaBottle()) {
                Vector2i blankTile = getBlankTile();
                if (blankTile != null) {
                    createEntity('@',blankTile.x, blankTile.y);
                }
                checkColaBottle = false;
            }
            return;
        }
    }

    public void checkDezeniman_san() {
        if (checkDezeniman_san) {
            if (portal.getNumBomExplosion() >= 3 && numEnemyDie == 0 && numBrickExist == 0) {
                Vector2i blankTile = getBlankTile();
                if (blankTile != null) {
                    createEntity('/',blankTile.x, blankTile.y);
                }
                checkDezeniman_san = false;
            }
            return;
        }
    }

    public void checkFamicom() {
        if (checkFamicom) {
            if (numEnemyExist == 0 && numBombExplosion >= 100) {
                Vector2i blankTile = getBlankTile();
                if (blankTile != null) {
                    createEntity('%',blankTile.x, blankTile.y);
                }
                checkFamicom = false;
            }
            return;
        }
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
        checkTarget = true;
        checkColaBottle = true;
        checkDezeniman_san = true;
        checkFamicom = true;
        checkGoddessMask = true;
        checkNakamoto_san = true;
    }

    public void updateBonus() {
        checkTarget();
        checkDezeniman_san();
        checkColaBottle();
        if (numEnemyExist == 0) {
            checkBooleanMap(player.getX(), player.getY());
            checkGoddessMask();
            checkNakamoto_san();
            checkFamicom();
        } else {
            resetBooleanMap();
        }
    }

    public Vector2i getBlankTile() {
        for (int i = 0; i < staticEntityList.size(); ++i) {
            for (int j = 0; j < staticEntityList.get(i).size(); ++j) {
                if (staticEntityList.get(i).get(j).size() == 0) {
                    return new Vector2i(j, i);
                }
            }
        }
        return null;
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
                dynamicEntityList.remove(i);
            } else {
                if (dynamicEntityList.get(i) instanceof Enemy) {
                    numEnemyExist ++;
                }
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

        if (numEnemyExist == 0 && player.getBombManager().getMaxBomb() < 100) {
            player.getBombManager().setMaxBomb(100);
        }
        if (numEnemyExist > 0 && player.getBombManager().getMaxBomb() >= 100) {
            int x = player.getBombManager().getNumBomb();
            player.getBombManager().setMaxBomb(x);
        }
    }

    public void update() {
        if (time.countSecond() >= maxTime) {
            time.stop();
            whenTimeToZero();
        }
        camera.update();
        updateEntity();
        updateBonus();
    }

    /**
     * Render tất cả các entity nằm trong vùng camera.
     */
    public void render(GraphicsContext graphicsContext) {
        graphicsContext.setFill(Paint.valueOf("Green"));
        graphicsContext.fillRect(0, 0, camera.getSize().x, camera.getSize().y);
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

    public boolean isTimeOver() {
        return timeOver;
    }
}