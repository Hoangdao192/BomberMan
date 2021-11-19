package Entities.PowerUp;

import Component.Sprite;
import Entities.Brick;
import Entities.StaticEntity;

public abstract class PowerUp extends StaticEntity {
    protected Brick brickBound = null;

    public PowerUp(int x, int y, int width, int height, int gridSize, Sprite sprite) {
        super(x, y, width, height, gridSize, sprite);
        createHitBox(0, 0, width, height);
    }

    public void setBrickBound(Brick brickBound) {
        this.brickBound = brickBound;
    }

    protected boolean isBrickExist() {
        if (brickBound == null) {
            return true;
        }
        return brickBound.isExist();
    }
}
