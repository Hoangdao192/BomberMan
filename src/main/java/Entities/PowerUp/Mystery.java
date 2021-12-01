package Entities.PowerUp;

import Component.Sprite;
import Entities.Bomber;
import Entities.Entity;
import Map.Map;

/**
 * Vô hiệu hóa vụ nổ và mọi ảnh hưởng của quái trong 10s.
 */

public class Mystery extends PowerUp {
    public Mystery(int x, int y, int width, int height, int gridSize, Map map) {
        super(x, y, width, height, gridSize, Sprite.MYSTERY, map);
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
            ((Bomber) other).setImmortal(true);
            destroy();
            return true;
        }
        return false;
    }
}