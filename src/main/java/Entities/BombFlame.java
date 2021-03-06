package Entities;

import Component.Animation;
import Component.Sprite;
import javafx.scene.canvas.GraphicsContext;

public class BombFlame extends StaticEntity {
    private Animation animation;
    public static final int FLAME_CENTER = 0;
    public static final int FLAME_HORIZON = 1;
    public static final int FLAME_LEFT = 2;
    public static final int FLAME_RIGHT = 3;
    public static final int FLAME_VERTICAL = 4;
    public static final int FLAME_UP = 5;
    public static final int FLAME_DOWN = 6;

    //  CONSTRUCTOR
    public BombFlame(int x, int y, int width, int height, int gridSize, int flameType) {
        super(x, y, width, height, gridSize, null);
        collision = true;
        createAnimation(flameType);
        createHitBox(flameType);
    }

    private void createHitBox(int flameType) {
        switch (flameType) {
            case FLAME_CENTER:
                createHitBox(0, 0, width, height);
                break;
            case FLAME_HORIZON:
                createHitBox(0, width / 4, width, height / 2);
                break;
            case FLAME_RIGHT:
                createHitBox(0, width / 4, 22 * width / 32, height / 2);
                break;
            case FLAME_LEFT:
                createHitBox(10 * width / 32, width / 4, 22 * width / 32, height / 2);
                break;
            case FLAME_VERTICAL:
                createHitBox(width / 4, 0, width / 2, height);
                break;
            case FLAME_UP:
                createHitBox(width / 4, 10 * width / 32, width / 2, 22 * width / 32);
                break;
            case FLAME_DOWN:
                createHitBox(width / 4, 0, width / 2, 22 * width / 32);
                break;
        }
    }

    private void createAnimation(int flameType) {
        switch (flameType) {
            //  Center
            case 0: {
                animation = new Animation(
                        this, this.width, this.height, 1,
                        Sprite.BOMB_FLAME_CENTER_1, Sprite.BOMB_FLAME_CENTER_2,
                        Sprite.BOMB_FLAME_CENTER_3, Sprite.BOMB_FLAME_CENTER_2,
                        Sprite.BOMB_FLAME_CENTER_1
                );
                break;
            }
            //  Horizon
            case 1: {
                animation = new Animation(
                        this, this.width, this.height, 1,
                        Sprite.BOMB_FLAME_HORIZON_1, Sprite.BOMB_FLAME_HORIZON_2,
                        Sprite.BOMB_FLAME_HORIZON_3, Sprite.BOMB_FLAME_HORIZON_2,
                        Sprite.BOMB_FLAME_HORIZON_1
                );
                break;
            }
            //  Left
            case 2: {
                animation = new Animation(
                        this, this.width, this.height, 1,
                        Sprite.BOMB_FlAME_LEFT_1, Sprite.BOMB_FlAME_LEFT_2,
                        Sprite.BOMB_FlAME_LEFT_3, Sprite.BOMB_FlAME_LEFT_2,
                        Sprite.BOMB_FlAME_LEFT_1
                );
                break;
            }
            //  Right
            case 3: {
                animation = new Animation(
                        this, this.width, this.height, 1,
                        Sprite.BOMB_FLAME_RIGHT_1, Sprite.BOMB_FLAME_RIGHT_2,
                        Sprite.BOMB_FLAME_RIGHT_3, Sprite.BOMB_FLAME_RIGHT_2,
                        Sprite.BOMB_FLAME_RIGHT_1
                );
                break;
            }
            //  Vertical
            case 4: {
                animation = new Animation(
                        this, this.width, this.height, 1,
                        Sprite.BOMB_FLAME_VERTICAL_1, Sprite.BOMB_FLAME_VERTICAL_2,
                        Sprite.BOMB_FLAME_VERTICAL_3, Sprite.BOMB_FLAME_VERTICAL_2,
                        Sprite.BOMB_FLAME_VERTICAL_1
                );
                break;
            }
            //  Up
            case 5: {
                animation = new Animation(
                        this, this.width, this.height, 1,
                        Sprite.BOMB_FLAME_TOP_1, Sprite.BOMB_FLAME_TOP_2,
                        Sprite.BOMB_FLAME_TOP_3, Sprite.BOMB_FLAME_TOP_2,
                        Sprite.BOMB_FLAME_TOP_1
                );
                break;
            }
            //  Down
            case 6: {
                animation = new Animation(
                        this, this.width, this.height, 1,
                        Sprite.BOMB_FLAME_BOTTOM_1, Sprite.BOMB_FLAME_BOTTOM_2,
                        Sprite.BOMB_FLAME_BOTTOM_3, Sprite.BOMB_FLAME_BOTTOM_2,
                        Sprite.BOMB_FLAME_BOTTOM_1
                );
                break;
            }
        }
    }

    @Override
    public boolean ifCollideDo(Entity other) {
        if (collision(other) || hitBox.contains(other.hitBox)) {
            if (other instanceof Portal) {
                int x = ((Portal) other).getHasBomExplosion();
                ((Portal) other).setHasBomExplosion(++x);
            }

            other.die();
            return true;
        }
        return false;
    }

    @Override
    public void update() {
        if (animation.getCurrentFrame() == animation.getNumberOfFrame() - 1) {
            destroy();
        }
        animation.update();
        hitBox.update();
    }

    public Animation getAnimation() {
        return animation;
    }

    public void render(int x, int y, GraphicsContext graphicsContext) {
        //hitBox.render(x + hitBox.getOffsetX(), y + hitBox.getOffsetY(), graphicsContext);
        animation.render(x, y, graphicsContext);
    }
}
