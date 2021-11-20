package Entities;

import Component.HitBox;
import Component.Sprite;
import Entities.Enemy.Enemy;
import Entities.PowerUp.PowerUp;
import Map.Map;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class Portal extends PowerUp {

    public Portal(int x, int y, int width, int height, int gridSize, Map map) {
        super(x, y, width, height, gridSize, Sprite.PORTAL, map);
        collision = true;
    }

    @Override
    public void update() {

    }

    public boolean ifCollideDo(Entity other) {
        if (hasBrick()) {
            return false;
        }
        if (other instanceof Bomber) {
            if (HitBox.checkHixbox(other, this)) {
//                ArrayList<Entity> arrayList = map.getEntityList();
//                int numEnemy = 0;
//                for (Entity entity : arrayList) {
//                    if (entity instanceof Enemy) {
//                        numEnemy++;
//                    }
//                }
//                if (numEnemy != 0) {
//                    System.out.println("num = " + numEnemy);
//                    return false;
//                } else {
//                    System.out.println("portal");
                    map.newMap();
//                }
                return true;
            }
        }
        return false;
    }
}