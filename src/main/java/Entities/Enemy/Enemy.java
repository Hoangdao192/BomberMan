package Entities.Enemy;

import Component.Score;
import Component.Sprite;
import Entities.*;
import Map.Map;

public abstract class Enemy extends DynamicEntity {
    protected int Score;

    public Enemy(int x, int y, int width, int height, Sprite sprite, Map map) {
        super(x, y, width, height, map.getGridSize(), sprite, map);
        collision = true;
    }

    @Override
    public boolean canCollideWithStaticEntity(Entity entity) {
        if (entity instanceof Stone || entity instanceof Brick
            || entity instanceof Bomb || entity instanceof BombFlame) {
            return true;
        }
        return false;
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

    public void setScore(int score) {
        Score = score;
    }
    public int getScore() {
        return Score;
    }
}
