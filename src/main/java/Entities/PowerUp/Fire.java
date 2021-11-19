package Entities.PowerUp;

import Component.Sprite;
import Entities.Bomber;
import Entities.Entity;

public class Fire extends PowerUp{
    public Fire(int x, int y, int width, int height, int gridSize) {
        super(x, y, width, height, gridSize, Sprite.FIRE);
        collision = true;
    }

    @Override
    public void update() {

    }

    @Override
    public boolean ifCollideDo(Entity other) {
        if (isBrickExist()) {
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
