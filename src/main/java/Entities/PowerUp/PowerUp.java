package Entities.PowerUp;

import Component.Sprite;
import Entities.StaticEntity;

public abstract class PowerUp extends StaticEntity {
    public PowerUp(int x, int y, int width, int height, int gridSize, Sprite sprite) {
        super(x, y, width, height, gridSize, sprite);
        createHitBox(0, 0, width, height);
    }
}
