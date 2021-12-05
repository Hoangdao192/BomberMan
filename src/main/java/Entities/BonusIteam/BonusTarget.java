package Entities.BonusIteam;

import Component.Sprite;
import Component.Time;
import Entities.Bomber;
import Entities.Entity;
import Map.Map;

public class BonusTarget extends Bonus {
    public BonusTarget(int x, int y, int width, int height, int gridSize, Map map) {
        super(x, y, width, height, gridSize, Sprite.BONUS_TARGET, map, 10000, "BonusTarget");
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