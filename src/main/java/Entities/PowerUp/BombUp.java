package Entities.PowerUp;

import Component.Sprite;
import Entities.Bomber;
import Entities.Entity;

public class BombUp extends PowerUp{
    public BombUp(int x, int y, int width, int height, int gridSize) {
        super(x, y, width, height, gridSize, Sprite.BOMB_UP);
        collision = true;
    }

    @Override
    public void update() {

    }

    @Override
    public boolean ifCollideDo(Entity other) {
        if (collision(other) && other instanceof Bomber) {
            ((Bomber) other).increaseNumberOfBomb();
            destroy();
            return true;
        }
        return false;
    }
}
