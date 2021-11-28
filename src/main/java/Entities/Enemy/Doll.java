package Entities.Enemy;

import Component.Animation;
import Component.AnimationManager;
import Component.HitBox;
import Component.PathFinding.RandomMove;
import Component.Sprite;
import Map.Map;
import javafx.scene.canvas.GraphicsContext;

public class Doll extends Enemy {
    private final int DEFAULT_SPEED = 4;
    private final int SCORE = 400;
    AnimationManager animationManager;
    RandomMove randomMove;

    public Doll(int x, int y, int width, int height, Map map) {
        super(x, y, width, height, null, map);
        createAnimation();
        collision = true;
        createHitBox();
        createMovement();
        setScore(SCORE);
    }

    private void createAnimation() {
        animationManager = new AnimationManager(this);
        this.sprite = Sprite.BALLOON_MOVE_RIGHT_1;
        Animation movingLeft = new Animation(
                this, this.width, this.height, 2,
                Sprite.DOLL_MOVE_LEFT_1, Sprite.DOLL_MOVE_LEFT_2,
                Sprite.DOLL_MOVE_LEFT_3
        );

        Animation movingRight = new Animation(
                this, this.width, this.height, 2,
                Sprite.DOLL_MOVE_RIGHT_1, Sprite.DOLL_MOVE_RIGHT_2,
                Sprite.DOLL_MOVE_RIGHT_3
        );

        Animation dead = new Animation(
                this, this.width, this.height, 5,
                Sprite.DOLL_DIE, Sprite.ENEMY_DIE_1, Sprite.ENEMY_DIE_2,
                Sprite.ENEMY_DIE_3
        );

        animationManager.addAnimation("MOVING LEFT", movingLeft);
        animationManager.addAnimation("MOVING RIGHT", movingRight);
        animationManager.addAnimation("DEAD", dead);
        animationManager.play("MOVING LEFT");
    }

    private void createHitBox() {
        /*hitBox = new HitBox(
                this, this.width / sprite.getWidth(), 0,
                14 * this.width / sprite.getWidth(),
                16 * this.height / sprite.getHeight());*/
        hitBox = new HitBox(this, 0, 0, width,height);
    }

    private void createMovement() {
        movement.setSpeed(DEFAULT_SPEED);
        randomMove = new RandomMove(this, map);
    }

    @Override
    protected void updateMovement() {
        randomMove.update();
        movement.move();
    }

    private void updateAnimation() {
        if (animationManager.getCurrentAnimationKey().equals("DEAD")) {
            movement.setSpeed(0);
            if (animationManager.get("DEAD").getCurrentFrame() == animationManager.get("DEAD").getNumberOfFrame() - 1) {
                destroy();
            }
        }
        else if (movement.getDirection().x > 0) {
            animationManager.play("MOVING RIGHT");
        } else {
            animationManager.play("MOVING LEFT");
        }
        animationManager.update();
    }

    @Override
    public void die() {
        animationManager.play("DEAD");
        collision = false;
        movement.setSpeed(0);
        movement.update(0, 0);
    }

    @Override
    public void update() {
        if (Stop) {
            return;
        }
        updateMovement();
        hitBox.update();
        updateGridPosition();
        updateAnimation();
    }

    @Override
    public void render(int x, int y, GraphicsContext graphicsContext) {
        // hitBox.render(x + hitBox.getOffsetX(), y + hitBox.getOffsetY(), graphicsContext);
        if (exist) {
            animationManager.render(graphicsContext, x, y);
        }
    }
}
