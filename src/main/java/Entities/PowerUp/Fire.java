package Entities.PowerUp;

import Component.Sprite;
import Entities.Bomber;
import Entities.Entity;
import Map.Map;

public class Fire extends PowerUp{
    public Fire(int x, int y, int width, int height, int gridSize, Map map) {
        super(x, y, width, height, gridSize, Sprite.FIRE, map);
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
            ((Bomber) other).increaseBombRadius();
            destroy();
            return true;
        }
        return false;
    }
}
