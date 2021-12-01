package Entities.BonusIteam;

import Component.Sprite;
import Component.Time;
import Entities.Bomber;
import Entities.Entity;
import Map.Map;

public class BonusTarget extends Bonus {
    public BonusTarget(int x, int y, int width, int height, int gridSize, Map map) {
        super(x, y, width, height, gridSize, null, map, 10000, "BonusTarget");
        this.sprite = Sprite.SPRITE_TRANSPARENT;
        collision = true;
    }

    @Override
    public void update() {
        if (checkBonus) {
            this.sprite = Sprite.BONUS_TARGET;
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
//    public BonusTarget(int x, int y, int width, int height) {
//        super(Sprite.BONUS_TARGET, "BonusTarget", x, y, width, height, 10000);
//    }
}