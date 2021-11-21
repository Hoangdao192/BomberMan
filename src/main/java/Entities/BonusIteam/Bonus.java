package Entities.BonusIteam;

import Component.Sprite;
import javafx.scene.canvas.GraphicsContext;

public abstract class Bonus {
    protected Sprite sprite;
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected int score;
    protected String name;
    protected boolean checkBonus = false;

    public Bonus(Sprite sprite, String name, int x, int y, int width, int height, int score) {
        this.sprite = sprite;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.score = score;
        this.name = name;
    }

    public void render(GraphicsContext gc) {
        if (sprite != null) {
            sprite.render(x, y, width, height, gc);
        }
    }

    public boolean isCheckBonus() {
        return checkBonus;
    }

    public void setCheckBonus(boolean checkBonus) {
        this.checkBonus = checkBonus;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
