package Entities.BonusIteam;

import Component.Sprite;
import Entities.Bomber;
import Entities.Entity;
import Map.Map;

public class Nakamoto_san extends Bonus {
    public Nakamoto_san(int x, int y, int width, int height, int gridSize, Map map) {
        super(x, y, width, height, gridSize, Sprite.NAKAMOTO_SAN, map, 10000000, "Nakamoto_san");
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