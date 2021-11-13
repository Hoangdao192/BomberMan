package Entities.Enemy;

import Component.Sprite;
import Entities.DynamicEntity;
import Map.Map;

public abstract class Enemy extends DynamicEntity {
    public Enemy(int x, int y, int width, int height, Sprite sprite, Map map) {
        super(x, y, width, height, map.getGridSize(), sprite, map);
    }
}
