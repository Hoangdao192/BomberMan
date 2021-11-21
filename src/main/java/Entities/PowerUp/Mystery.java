package Entities.PowerUp;

import Component.Sprite;
import Component.Time;
import Entities.Bomber;
import Entities.Entity;
import Map.Map;

/**
 * Vô hiệu hóa vụ nổ và mọi ảnh hưởng của quái trong 10s.
 */

public class Mystery extends PowerUp {
    int hit = 1;
    boolean checkFlamePass = false;
    Time time = null;
    public Mystery(int x, int y, int width, int height, int gridSize, Map map) {
        super(x, y, width, height, gridSize, Sprite.MYSTERY, map);
        collision = true;
    }

    @Override
    public void update() {
        if (hit > 0) {
            return;
        }
        if (time.countSecond() == 10) {
            if (!checkFlamePass) {
                Bomber bomber = map.getPlayer();
                bomber.setFlamePass(checkFlamePass);
                bomber.setEnemyPass(false);
                bomber.setEatenEnemyPass(false);
            }
            destroy();
        }
    }

    @Override
    public boolean ifCollideDo(Entity other) {
        if (hit <= 0) {
            return false;
        }
        if (hasBrick()) {
            return false;
        }
        if (collision(other) && other instanceof Bomber) {
            Bomber bomber = (Bomber) other;
            hit --;
            time = new Time();
            checkFlamePass = bomber.isFlamePass();
            if (!checkFlamePass) {
                bomber.setFlamePass(true);
            }
            bomber.setEnemyPass(true);
            bomber.setEatenEnemyPass(true);
            this.sprite = Sprite.IMAGE_TRANSPARENT;
            return true;
        }
        return false;
    }
}
