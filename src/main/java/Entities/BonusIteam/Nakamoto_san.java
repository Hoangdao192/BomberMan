package Entities.BonusIteam;

import Component.Sprite;
import Entities.Bomber;
import Entities.Entity;
import Map.Map;

public class Nakamoto_san extends Bonus {
//    public Nakamoto_san(int x, int y, int width, int height) {
//        super(Sprite.NAKAMOTO_SAN, "Nakamoto_san", x, y, width, height, 10000000);
//    }

    public Nakamoto_san(int x, int y, int width, int height, int gridSize, Map map) {
        super(x, y, width, height, gridSize, null, map, 10000000, "Nakamoto_san");
        this.sprite = Sprite.SPRITE_TRANSPARENT;
        collision = true;
    }

    @Override
    public void update() {
        if (checkBonus) {
            this.sprite = Sprite.NAKAMOTO_SAN;
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