package Entities.BonusIteam;

import Component.Sprite;

public class BonusTarget extends Bonus {
    public BonusTarget(int x, int y, int width, int height) {
        super(Sprite.BONUS_TARGET, "BonusTarget", x, y, width, height, 10000);
    }
}