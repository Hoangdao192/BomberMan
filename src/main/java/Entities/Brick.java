package Entities;

import Component.Animation;
import Component.Sprite;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Brick extends StaticEntity {
    private Animation animation;
    private boolean explode = false;

    public Brick(int x, int y, int width, int height, int gridSize) {
        super(x, y, width, height, gridSize, Sprite.BRICK_NORMAL);
        createAnimation();
        collision = true;
    }

    private void createAnimation() {
        animation = new Animation(
                this, this.width, this.height, 2,
                Sprite.BRICK_EXPLODE_1, Sprite.BRICK_EXPLODE_2, Sprite.BRICK_EXPLODE_3
        );
    }

    @Override
    public void die() {
        explode = true;
    }

    @Override
    public void update() {
        if (explode) {
            animation.play();
            if (animation.getCurrentFrame() == animation.getNumberOfFrame() - 1) {
                exist = false;
            }
            animation.update();
        }
    }

    @Override
    public void render(int x, int y, GraphicsContext graphicsContext) {
        if (!explode) {
            //  hitBox.render(x, y, graphicsContext);
            sprite.render(x, y, this.width, this.height, graphicsContext);
        } else {
            animation.render(x, y, graphicsContext);
        }
    }
}
