package Entities;

import Component.Movement;
import Map.Map;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;

public abstract class DynamicEntity extends Entity {
    protected Map map;
    protected Movement movement;

    public DynamicEntity(int x, int y, int width, int height, Image image, Map map) {
        super(x, y, width, height, image);
        this.map = map;
        movement = new Movement(this, 0);
    }

    public DynamicEntity(int x, int y, int width, int height, Image image, Rectangle2D imageOffset, Map map) {
        super(x, y, width, height, image, imageOffset);
        this.map = map;
        movement = new Movement(this, 0);
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public Movement getMovement() {
        return movement;
    }

    protected abstract void updateMovement();
}
