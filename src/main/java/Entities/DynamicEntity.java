package Entities;

import Component.Movement;
import Component.Sprite;
import Entities.Enemy.Enemy;
import Map.Map;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;

import java.util.ArrayList;

public abstract class DynamicEntity extends Entity {
    protected Map map;
    protected Movement movement;

    public DynamicEntity(int x, int y, int width, int height, int gridSize, Sprite sprite, Map map) {
        super(x, y, width, height, gridSize, sprite);
        this.map = map;
        movement = new Movement(this, 0);
    }

    /**
     * Kiểm tra entity truyền vào có thể va chạm với this hay không
     */
    public boolean canCollideWithStaticEntity(Entity entity) {
        if (entity instanceof Stone || entity instanceof Brick
            || entity instanceof Bomb) {
            return true;
        }
        return false;
    }

    public boolean collisionWithMap() {
        final ArrayList<ArrayList<ArrayList<Entity>>> staticEntityList = map.getStaticEntityList();
        boolean collide = false;

        int startX = this.gridX - 2;
        int endX = this.gridX + 2;
        int startY = this.gridY - 2;
        int endY = this.gridY + 2;
        if (startX < 0) {
            startX = 0;
        }
        if (startY < 0) {
            startY = 0;
        }
        if (endX >= map.getMapGridWidth()) {
            endX = map.getMapGridWidth() - 1;
        }
        if (endY >= map.getMapGridHeight()) {
            endY = map.getMapGridHeight() - 1;
        }
        for (int row = startY; row <= endY; ++row) {
            for (int col = startX; col <= endX; ++col) {
                for (int k = 0; k < staticEntityList.get(row).get(col).size(); ++k) {
                    Entity entity = staticEntityList.get(row).get(col).get(k);
                    if (!entity.collisionAble() || !canCollideWithStaticEntity(entity)) {
                        continue;
                    }
                    if (entity.ifCollideDo(this)) {
                        collide = true;
                    }
                }
            }
        }
        return collide;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public Movement getMovement() {
        return movement;
    }

    protected abstract void updateMovement();

    public Map getMap() {
        return map;
    }
}
