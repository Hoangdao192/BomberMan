package Entities.BonusIteam;

import Component.Sprite;
import Entities.Bomber;
import Entities.Entity;
import Map.Map;

public class GoddessMask extends Bonus {
//    public GoddessMask(int x, int y, int width, int height) {
//        super(Sprite.GODDESS_MASK, "Goddess Mask", x, y, width, height, 20000);
//    }

    public GoddessMask(int x, int y, int width, int height, int gridSize, Map map) {
        super(x, y, width, height, gridSize, null, map, 20000, "Goddess Mask");
        this.sprite = Sprite.SPRITE_TRANSPARENT;
        collision = true;
    }

    @Override
    public void update() {
        if (checkBonus) {
            this.sprite = Sprite.GODDESS_MASK;
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
}