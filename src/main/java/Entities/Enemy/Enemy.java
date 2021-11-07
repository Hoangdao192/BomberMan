package Entities.Enemy;

import Entities.DynamicEntity;
import Map.Map;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;

public abstract class Enemy extends DynamicEntity {
    public Enemy(int x, int y, int width, int height, Image image, Map map) {
        super(x, y, width, height, image, map);
    }

    public Enemy(int x, int y, int width, int height, Image image, Rectangle2D imageOffset, Map map) {
        super(x, y, width, height, image, imageOffset, map);
    }
}
