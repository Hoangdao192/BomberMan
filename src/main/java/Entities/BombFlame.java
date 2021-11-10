package Entities;

import Component.Animation;
import Component.SpriteInfo;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class BombFlame extends StaticEntity {
    Animation animation;
    public static final int FLAME_CENTER = 0;
    public static final int FLAME_HORIZON = 1;
    public static final int FLAME_LEFT = 2;
    public static final int FLAME_RIGHT = 3;
    public static final int FLAME_VERTICAL = 4;
    public static final int FLAME_UP = 5;
    public static final int FLAME_DOWN = 6;

    public BombFlame(int x, int y, int width, int height, int gridSize) {
        super(x, y, width, height, gridSize, null);
        collision = true;
    }

    public BombFlame(int x, int y, int width, int height, int gridSize, Image image, int flameType) {
        super(x, y, width, height, gridSize, image);
        collision = true;
        createHitBox(0, 0, gridSize, gridSize);
        createAnimation(image, flameType);
    }

    private void createAnimation(Image image, int flameType) {
        switch (flameType) {
            //  Center
            case 0: animation = new Animation(
                    this,
                    image, SpriteInfo.FLAME_CENTER.FRAME_WIDTH, SpriteInfo.FLAME_CENTER.FRAME_HEIGHT,
                    2, SpriteInfo.FLAME_CENTER.STAR_FRAME , SpriteInfo.FLAME_CENTER.END_FRAME);
                break;
            //  Horizon
            case 1: animation = new Animation(
                    this,
                    image, SpriteInfo.FLAME_HORIZON.FRAME_WIDTH, SpriteInfo.FLAME_HORIZON.FRAME_HEIGHT,
                    2, SpriteInfo.FLAME_HORIZON.STAR_FRAME , SpriteInfo.FLAME_HORIZON.END_FRAME);
                break;
            //  Left
            case 2: animation = new Animation(
                    this,
                    image, SpriteInfo.FLAME_LEFT.FRAME_WIDTH, SpriteInfo.FLAME_LEFT.FRAME_HEIGHT,
                    2, SpriteInfo.FLAME_LEFT.STAR_FRAME , SpriteInfo.FLAME_LEFT.END_FRAME);
                break;
            //  Right
            case 3: animation = new Animation(
                    this,
                    image, SpriteInfo.FLAME_RIGHT.FRAME_WIDTH, SpriteInfo.FLAME_RIGHT.FRAME_HEIGHT,
                    2, SpriteInfo.FLAME_RIGHT.STAR_FRAME , SpriteInfo.FLAME_RIGHT.END_FRAME);
                break;
            //  Vertical
            case 4: animation = new Animation(
                    this,
                    image, SpriteInfo.FLAME_VERTICAL.FRAME_WIDTH, SpriteInfo.FLAME_VERTICAL.FRAME_HEIGHT,
                    2, SpriteInfo.FLAME_VERTICAL.STAR_FRAME , SpriteInfo.FLAME_VERTICAL.END_FRAME);
                break;
            //  Up
            case 5: animation = new Animation(
                    this,
                    image, SpriteInfo.FLAME_UP.FRAME_WIDTH, SpriteInfo.FLAME_UP.FRAME_HEIGHT,
                    2, SpriteInfo.FLAME_UP.STAR_FRAME , SpriteInfo.FLAME_UP.END_FRAME);
                break;
            //  Down
            case 6: animation = new Animation(
                    this,
                    image, SpriteInfo.FLAME_DOWN.FRAME_WIDTH, SpriteInfo.FLAME_DOWN.FRAME_HEIGHT,
                    2, SpriteInfo.FLAME_DOWN.STAR_FRAME , SpriteInfo.FLAME_DOWN.END_FRAME);
        }
    }

    @Override
    public boolean ifCollideDo(Entity other) {
        if (collision(other) || hitBox.contains(other.hitBox)) {
            other.die();
        }
        return false;
    }

    @Override
    public void update() {
        animation.update();
    }

    public void render(int x, int y, GraphicsContext graphicsContext) {
        animation.render(graphicsContext, x, y);
    }
}
