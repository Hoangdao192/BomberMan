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
}
