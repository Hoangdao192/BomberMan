package Entities.PowerUp;

import Component.Sprite;
import Entities.Bomber;
import Entities.Entity;
import Map.Map;

public class WallPass extends PowerUp {
    public WallPass(int x, int y, int width, int height, int gridSize, Map map) {
        super(x, y, width, height, gridSize, Sprite.WALL_PASS, map);
        collision = true;
    }

    @Override
    public void update() {

    }

    @Override
    public boolean ifCollideDo(Entity other) {
        if (hasBrick()) {
            return false;
        }
        if (collision(other) && other instanceof Bomber) {
            ((Bomber) other).setWallPass(true);
            destroy();
            return true;
        }
        return false;
    }
}