package Entities;

import Component.Animation;
import Component.Sprite;
import Component.SpriteInfo;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Brick extends StaticEntity {
    private Animation animation;
    private boolean explode = false;

    public Brick(int x, int y, int width, int height, int gridSize) {
        super(x, y, width, height, gridSize, null, SpriteInfo.BRICK_NORMAL);
        createAnimation();
        collision = true;
    }

    private void createAnimation() {
        sprite = Sprite.BRICK_NORMAL;
        animation = new Animation(this, null, this.width, this.height, 2);
        animation.addSprite(Sprite.BRICK_EXPLODE_1);
        animation.addSprite(Sprite.BRICK_EXPLODE_2);
        animation.addSprite(Sprite.BRICK_EXPLODE_3);
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
            sprite.render(x, y, this.width, this.height, graphicsContext);
        } else {
            animation.render(x, y, graphicsContext);
        }
    }
}
