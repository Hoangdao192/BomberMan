package Entities.PowerUp;

import Component.Sprite;
import Entities.Bomber;
import Entities.Entity;
import Map.Map;

public class SpeedUp extends PowerUp {
    public SpeedUp(int x, int y, int width, int height, int gridSize, Map map) {
        super(x, y, width, height, gridSize, Sprite.SPEED_UP, map);
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
            ((Bomber) other).increaseSpeed();
            System.out.println("Speed");
            destroy();
            return true;
        }
        return false;
    }
}
