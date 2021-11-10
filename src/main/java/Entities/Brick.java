package Entities;

import Component.Animation;
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
        Image image = new Image(SpriteInfo.BRICK_SPRITESHEET);
        setImage(image);
        animation = new Animation(
                this, image,
                SpriteInfo.BRICK_EXPLODE.FRAME_WIDTH, SpriteInfo.BRICK_EXPLODE.FRAME_HEIGHT, 0,
                SpriteInfo.BRICK_EXPLODE.STAR_FRAME, SpriteInfo.BRICK_EXPLODE.END_FRAME);
    }

    @Override
    public void die() {
        explode = true;
    }

    @Override
    public void update() {
        animation.play();
        if (explode) {
            if (animation.getCurrentFrame() == animation.getEndFrame()) {
                exist = false;
            }
            animation.update();
        }
    }

    @Override
    public void render(int x, int y, GraphicsContext graphicsContext) {
        if (!explode) {
            super.render(x, y, graphicsContext);
        } else {
            animation.render(graphicsContext, x, y);
        }
    }
}
