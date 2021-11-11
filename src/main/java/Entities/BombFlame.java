package Entities;

import Component.Animation;
import Component.Sprite;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class BombFlame extends StaticEntity {
    private Animation animation;
    public static final int FLAME_CENTER = 0;
    public static final int FLAME_HORIZON = 1;
    public static final int FLAME_LEFT = 2;
    public static final int FLAME_RIGHT = 3;
    public static final int FLAME_VERTICAL = 4;
    public static final int FLAME_UP = 5;
    public static final int FLAME_DOWN = 6;

    public BombFlame(int x, int y, int width, int height, int gridSize, int flameType) {
        super(x, y, width, height, gridSize, null);
        collision = true;
        createHitBox(0, 0, gridSize, gridSize);
        createAnimation(flameType);
    }

    private void createAnimation(int flameType) {
        switch (flameType) {
            //  Center
            case 0: {
                animation = new Animation(this, null, this.width, this.height, 2);
                animation.addSprite(Sprite.BOMB_FLAME_CENTER_1);
                animation.addSprite(Sprite.BOMB_FLAME_CENTER_2);
                animation.addSprite(Sprite.BOMB_FLAME_CENTER_3);
                animation.addSprite(Sprite.BOMB_FLAME_CENTER_2);
                animation.addSprite(Sprite.BOMB_FLAME_CENTER_1);
                break;
            }
            //  Horizon
            case 1: {
                animation = new Animation(this, null, this.width, this.height, 2);
                animation.addSprite(Sprite.BOMB_FLAME_HORIZON_1);
                animation.addSprite(Sprite.BOMB_FLAME_HORIZON_2);
                animation.addSprite(Sprite.BOMB_FLAME_HORIZON_3);
                animation.addSprite(Sprite.BOMB_FLAME_HORIZON_2);
                animation.addSprite(Sprite.BOMB_FLAME_HORIZON_1);
                break;
            }
            //  Left
            case 2: {
                animation = new Animation(this, null, this.width, this.height, 2);
                animation.addSprite(Sprite.BOMB_FlAME_LEFT_1);
                animation.addSprite(Sprite.BOMB_FlAME_LEFT_2);
                animation.addSprite(Sprite.BOMB_FlAME_LEFT_3);
                animation.addSprite(Sprite.BOMB_FlAME_LEFT_2);
                animation.addSprite(Sprite.BOMB_FlAME_LEFT_1);
                break;
            }
            //  Right
            case 3: {
                animation = new Animation(this, null, this.width, this.height, 2);
                animation.addSprite(Sprite.BOMB_FLAME_RIGHT_1);
                animation.addSprite(Sprite.BOMB_FLAME_RIGHT_2);
                animation.addSprite(Sprite.BOMB_FLAME_RIGHT_3);
                animation.addSprite(Sprite.BOMB_FLAME_RIGHT_2);
                animation.addSprite(Sprite.BOMB_FLAME_RIGHT_1);
                break;
            }
            //  Vertical
            case 4: {
                animation = new Animation(this, null, this.width, this.height, 2);
                animation.addSprite(Sprite.BOMB_FLAME_VERTICAL_1);
                animation.addSprite(Sprite.BOMB_FLAME_VERTICAL_2);
                animation.addSprite(Sprite.BOMB_FLAME_VERTICAL_3);
                animation.addSprite(Sprite.BOMB_FLAME_VERTICAL_2);
                animation.addSprite(Sprite.BOMB_FLAME_VERTICAL_1);
                break;
            }
            //  Up
            case 5: {
                animation = new Animation(this, null, this.width, this.height, 2);
                animation.addSprite(Sprite.BOMB_FLAME_TOP_1);
                animation.addSprite(Sprite.BOMB_FLAME_TOP_2);
                animation.addSprite(Sprite.BOMB_FLAME_TOP_3);
                animation.addSprite(Sprite.BOMB_FLAME_TOP_2);
                animation.addSprite(Sprite.BOMB_FLAME_TOP_1);
                break;
            }
            //  Down
            case 6: {
                animation = new Animation(this, null, this.width, this.height, 2);
                animation.addSprite(Sprite.BOMB_FLAME_BOTTOM_1);
                animation.addSprite(Sprite.BOMB_FLAME_BOTTOM_2);
                animation.addSprite(Sprite.BOMB_FLAME_BOTTOM_3);
                animation.addSprite(Sprite.BOMB_FLAME_BOTTOM_2);
                animation.addSprite(Sprite.BOMB_FLAME_BOTTOM_1);
            }
        }
    }

    @Override
    public boolean ifCollideDo(Entity other) {
        if (collision(other) || hitBox.contains(other.hitBox)) {
            other.die();
            return true;
        }
        return false;
    }

    @Override
    public void update() {
        animation.update();
    }

    public Animation getAnimation() {
        return animation;
    }

    public void render(int x, int y, GraphicsContext graphicsContext) {
        animation.render(x, y, graphicsContext);
    }
}
