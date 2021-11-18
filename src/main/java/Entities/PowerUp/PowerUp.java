package Entities.PowerUp;

import Component.Sprite;
import Entities.Brick;
import Entities.Entity;
import Entities.StaticEntity;
import Map.Map;

import java.util.ArrayList;

public abstract class PowerUp extends StaticEntity {
    protected Map map = null;
     public PowerUp(int x, int y, int width, int height, int gridSize, Sprite sprite, Map map) {
        super(x, y, width, height, gridSize, sprite);
        this.map = map;
        createHitBox(0, 0, width, height);
    }
    public boolean hasBrick() {
         int x = gridX;
         int y = gridY;
         ArrayList<Entity> arrayList = map.getStaticEntityList().get(y).get(x);
         for (Entity entity : arrayList) {
             if (entity instanceof Brick) {
                 return true;
             }
         }
         return false;
    }
}
