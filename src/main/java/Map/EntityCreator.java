package Map;

import Entities.BonusIteam.*;
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

    public Entity createPassEnemy(int x, int y, int width, int height) {
        Pass pass = new Pass(x, y, width, height, map);
        return pass;
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

    public Entity createBonusTarget(int x, int y, int width, int height) {
        BonusTarget bonusTarget = new BonusTarget(x, y, width, height, map.getGridSize(), map);
        return bonusTarget;
    }

    public Entity createColaBottle(int x, int y, int width, int height) {
        ColaBottle colaBottle = new ColaBottle(x, y, width, height, map.getGridSize(), map);
        return colaBottle;
    }

    public Entity createDezeniman_san(int x, int y, int width, int height) {
        Dezeniman_san dezeniman_san = new Dezeniman_san(x, y, width, height, map.getGridSize(), map);
        return dezeniman_san;
    }

    public Entity createFamicom(int x, int y, int width, int height) {
        Famicom famicom = new Famicom(x, y, width, height, map.getGridSize(), map);
        return famicom;
    }

    public Entity createGoddessMask(int x, int y, int width, int height) {
        GoddessMask goddessMask = new GoddessMask(x, y, width, height, map.getGridSize(), map);
        return goddessMask;
    }

    public Entity createNakamoto_san(int x, int y, int width, int height) {
        Nakamoto_san nakamoto_san = new Nakamoto_san(x, y, width, height, map.getGridSize(), map);
        return nakamoto_san;
    }
}
