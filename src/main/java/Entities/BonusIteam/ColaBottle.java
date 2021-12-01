package Entities.BonusIteam;

import Component.Sprite;
import Entities.Bomber;
import Entities.Entity;
import Map.Map;

public class ColaBottle extends Bonus {
    public ColaBottle(int x, int y, int width, int height, int gridSize, Map map) {
        super(x, y, width, height, gridSize, null, map, 30000, "Cola Bottle");
        this.sprite = Sprite.SPRITE_TRANSPARENT;
        collision = true;
    }

    @Override
    public void update() {
        if (checkBonus) {
            this.sprite = Sprite.COLA_BOTTLE;
        }
    }

    public boolean ifCollideDo(Entity other) {
        if (checkBonus) {
            if (collision(other) && other instanceof Bomber) {
                ((Bomber) other).getScore().addScore(this.score);
                destroy();
                return true;
            }
        }
        return false;
    }

//    public ColaBottle(int x, int y, int width, int height) {
//        super(Sprite.COLA_BOTTLE, "Cola Bottle", x, y, width, height, 30000);
//    }
}