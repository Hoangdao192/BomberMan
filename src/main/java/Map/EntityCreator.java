package Map;

import Entities.Brick;
import Entities.Enemy.*;
import Entities.Entity;
import Entities.Portal;
import Entities.PowerUp.*;
import Entities.Stone;

public class EntityCreator {
    private Map map;

    public EntityCreator(Map map) {
        this.map = map;
    }

    public Entity createFirePowerUp(int x, int y, int width, int height) {
        Fire fire = new Fire(x, y, width, height, map.getGridSize(), map);
        return fire;
    }

    public Entity createBombUpPowerUp(int x, int y, int width, int height) {
        BombUp bombUp = new BombUp(x, y, width, height, map.getGridSize(), map);
        return bombUp;
    }

    public Entity createSpeedUpPowerUp(int x, int y, int width, int height) {
        SpeedUp speedUp = new SpeedUp(x, y, width, height, map.getGridSize(), map);
        return speedUp;
    }

    public Entity createBombPassPowerUp(int x, int y, int width, int height) {
        BombPass bombPass = new BombPass(x, y, width, height, map.getGridSize(), map);
        return bombPass;
    }

    public Entity createDetonatorPowerUp(int x, int y, int width, int height) {
        Detonator detonator = new Detonator(x, y, width, height, map.getGridSize(), map);
        return detonator;
    }

    public Entity createMysteryPowerUp(int x, int y, int width, int height) {
        Mystery mystery = new Mystery(x, y, width, height, map.getGridSize(), map);
        return mystery;
    }

    public Entity createFlamePassPowerUp(int x, int y, int width, int height) {
        FlamePass flamePass = new FlamePass(x, y, width, height, map.getGridSize(), map);
        return flamePass;
    }

    public Entity createWallPassPowerUp(int x, int y, int width, int height) {
        WallPass wallPass = new WallPass(x, y, width, height, map.getGridSize(), map);
        return wallPass;
    }

    //  ENEMY CREATOR.
    public Entity createOnealEnemy(int x, int y, int width, int height) {
        Oneal oneal = new Oneal(x, y, width, height, map);
        return oneal;
    }

    public Entity createDollEnemy(int x, int y, int width, int height) {
        Doll doll = new Doll(x, y, width, height, map);
        return doll;
    }

    public Entity createMinvoEnemy(int x, int y, int width, int height) {
        Minvo minvo = new Minvo(x, y, width, height, map);
        return minvo;
    }

    public Entity createOvapiEnemy(int x, int y, int width, int height) {
        Ovapi ovapi = new Ovapi(x, y, width, height, map);
        return ovapi;
    }

    public Entity createBalloonEnemy(int x, int y, int width, int height) {
        Balloon balloon = new Balloon(x, y, width, height, map);
        return balloon;
    }

    public Entity createPontanEnemy(int x, int y, int width, int height) {
        Pontan pontan = new Pontan(x, y, width, height, map);
        return pontan;
    }

    public Entity createKondoriaEnemy(int x, int y, int width, int height) {
        Kondoria kondoria = new Kondoria(x, y, width, height, map);
        return kondoria;
    }

    // OTHER ENTITY CREATOR
    public Entity createBrickEntity(int x, int y, int width, int height) {
        Brick brick = new Brick(x, y, width, height, map.getGridSize());
        brick.createHitBox(0, 0, 32, 32);
        return brick;
    }

    public Entity createPortalEntity(int x, int y, int width, int height) {
        Portal portal = new Portal(x, y, width, height, map.getGridSize(), map);
        return portal;
    }

    public Entity createStoneEntity(int x, int y, int width, int height) {
        Stone stone = new Stone(x, y, width, height, map.getGridSize());
        return stone;
    }
}
