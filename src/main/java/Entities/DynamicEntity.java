package Entities;

import Component.Movement;
import Component.Sprite;
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

    public boolean collisionWithMap() {
        final ArrayList<ArrayList<ArrayList<Entity>>> staticEntityList = map.getStaticEntityList();
        boolean collide = false;

        int startX = this.gridX - 1;
        int endX = this.gridX + 1;
        int startY = this.gridY - 1;
        int endY = this.gridY + 1;
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
                    if (!entity.collisionAble()) {
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
}
