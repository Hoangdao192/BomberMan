package Entities.PowerUp;

import Component.Sprite;
import Entities.Bomber;
import Entities.Entity;

public class SpeedUp extends PowerUp {
    public SpeedUp(int x, int y, int width, int height, int gridSize) {
        super(x, y, width, height, gridSize, Sprite.SPEED_UP);
        collision = true;
    }

    @Override
    public void update() {

    }

    @Override
    public boolean ifCollideDo(Entity other) {
        if (collision(other) && other instanceof Bomber) {
            ((Bomber) other).increaseSpeed();
            System.out.println("Speed");
            destroy();
            return true;
        }
        return false;
    }
}
