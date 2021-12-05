package Entities.BonusIteam;

import Component.Sprite;
import Entities.Bomber;
import Entities.Entity;
import Map.Map;

public class Famicom extends Bonus {

    public Famicom(int x, int y, int width, int height, int gridSize, Map map) {
        super(x, y, width, height, gridSize, Sprite.FAMICOM, map, 500000, "Famicom");
        collision = true;
    }

    @Override
    public void update() {
    }

    public boolean ifCollideDo(Entity other) {
        if (collision(other) && other instanceof Bomber) {
            ((Bomber) other).getScore().addScore(this.score);
            destroy();
            return true;
        }
        return false;
    }
}