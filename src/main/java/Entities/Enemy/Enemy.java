package Entities.Enemy;

import Component.Sprite;
import Entities.DynamicEntity;
import Map.Map;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;

public abstract class Enemy extends DynamicEntity {
    public Enemy(int x, int y, int width, int height, Sprite sprite, Map map) {
        super(x, y, width, height, map.getGridSize(), sprite, map);
    }
}
