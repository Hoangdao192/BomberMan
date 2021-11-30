package Entities.BonusIteam;

import Component.Sprite;
import Entities.PowerUp.PowerUp;
import Map.Map;
import javafx.scene.canvas.GraphicsContext;

public abstract class Bonus extends PowerUp {
    protected int score;
    protected String name;
    protected boolean checkBonus = false;

    public Bonus(int x, int y, int width, int height, int gridSize, Sprite sprite, Map map, int score, String name) {
        super(x, y, width, height, gridSize, sprite, map);
        this.score = score;
        this.name = name;
    }

    public boolean isCheckBonus() {
        return checkBonus;
    }

    public void setCheckBonus(boolean checkBonus) {
        this.checkBonus = checkBonus;
        System.out.println(name);
    }

    public int getScore() {
        return score;
    }
}