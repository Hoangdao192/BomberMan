package Entities.Enemy;

import Component.Sprite;
import Entities.Bomber;
import Entities.DynamicEntity;
import Entities.Entity;
import Map.Map;

public abstract class Enemy extends DynamicEntity {
    public Enemy(int x, int y, int width, int height, Sprite sprite, Map map) {
        super(x, y, width, height, map.getGridSize(), sprite, map);
        collision = true;
    }

    @Override
    public boolean ifCollideDo(Entity other) {
        if (!collision(other)) {
            return false;
        }
        if (other instanceof Bomber) {
            ((Bomber) other).die();
        }
        return true;
    }
}
