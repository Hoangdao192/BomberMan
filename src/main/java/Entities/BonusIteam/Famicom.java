package Entities.BonusIteam;

import Component.Sprite;
import Entities.Bomber;
import Entities.Entity;
import Map.Map;

public class Famicom extends Bonus {

//    public Famicom(int x, int y, int width, int height) {
//        super(Sprite.FAMICOM, "Famicom", x, y, width, height, 500000);
//    }

    public Famicom(int x, int y, int width, int height, int gridSize, Map map) {
        super(x, y, width, height, gridSize, null, map, 500000, "Famicom");
        this.sprite = Sprite.SPRITE_TRANSPARENT;
        collision = true;
    }

    @Override
    public void update() {
        if (checkBonus) {
            this.sprite = Sprite.FAMICOM;
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