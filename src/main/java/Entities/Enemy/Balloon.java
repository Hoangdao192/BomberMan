package Entities.Enemy;

import Component.*;
import Entities.Bomber;
import Entities.Entity;
import Map.Map;
import Utils.RandomInt;
import Utils.Vector2i;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class Balloon extends Enemy {
    private final String IMAGE_PATH = "Graphic/Entity/Enemy/Balloon.png";
    private final int SPRITE_WIDTH = 16;
    private final int SPRITE_HEIGHT = 16;
    private final int DEFAULT_SPEED = 3;
    private Vector2i currentDirection;
    AnimationManager animationManager;

    public Balloon(int x, int y, int width, int height, Map map) {
        super(x, y, width, height, null, map);
        createAnimation();
        movement.setSpeed(DEFAULT_SPEED);
        currentDirection = new Vector2i(1, 0);
        collision = true;
    }

    public void createAnimation() {
        animationManager = new AnimationManager(this);

        Animation movingLeft = new Animation(this, null, this.width, this.height, 2);
        movingLeft.addSprite(Sprite.BALLOON_MOVE_LEFT_1);
        movingLeft.addSprite(Sprite.BALLOON_MOVE_LEFT_2);
        movingLeft.addSprite(Sprite.BALLOON_MOVE_LEFT_3);

        Animation movingRight = new Animation(this, null, this.width, this.height, 2);
        movingRight.addSprite(Sprite.BALLOON_MOVE_RIGHT_1);
        movingRight.addSprite(Sprite.BALLOON_MOVE_RIGHT_2);
        movingRight.addSprite(Sprite.BALLOON_MOVE_RIGHT_3);

        Animation dead = new Animation(this, null, this.width, this.height, 5);
        dead.addSprite(Sprite.BALLOON_DIE);
        dead.addSprite(Sprite.ENEMY_DIE_1);
        dead.addSprite(Sprite.ENEMY_DIE_2);
        dead.addSprite(Sprite.ENEMY_DIE_3);

        animationManager.addAnimation("MOVING LEFT", movingLeft);
        animationManager.addAnimation("MOVING RIGHT", movingRight);
        animationManager.addAnimation("DEAD", dead);
        animationManager.play("MOVING LEFT");
    }

    public boolean collisionWithMap() {
        /**
         * Xét vị trí tiếp theo có va chạm với bản đồ hay không.
         */
        final ArrayList<Entity> entities = map.getEntityList();
        //  Hitbox của bomber ở vị trí tiếp theo khi di chuyển.
        HitBox nextPositionHitbox = hitBox.getNextPosition(movement);
        boolean collide = false;
        for (int i = 0; i < entities.size(); ++i) {
            if (!entities.get(i).collisionAble()
                || entities.get(i) instanceof Enemy) {
                continue;
            }
            if (entities.get(i).ifCollideDo(this)) {
                collide = true;
            }
        }
        return collide;
    }

    @Override
    public boolean ifCollideDo(Entity other) {
        if (!collision(other)) {
            return false;
        }
        if (other instanceof Bomber) {
            System.out.println("GAME OVER");
            ((Bomber) other).die();
        }
        return true;
    }

    @Override
    protected void updateMovement() {
        //  Đổi hướng di chuyển khi va chạm với một vật thể nào đó.
        movement.update(currentDirection.x, currentDirection.y);
        if (collisionWithMap()) {
            int tempX = currentDirection.x;
            int tempY = currentDirection.y;
            while (tempX == currentDirection.x) {
                tempX = RandomInt.random(-1, 2);
            }
            if (tempX == 0) {
                while (tempY == currentDirection.y) {
                    tempY = RandomInt.random(-1, 2);
                }
            } else tempY = 0;
            currentDirection.x = tempX;
            currentDirection.y = tempY;
        } else {
            movement.move();
        }
    }

    private void updateAnimation() {
        animationManager.update();
        if (animationManager.getCurrentAnimationKey().equals("DEAD")) {
            movement.setSpeed(0);
            if (animationManager.get("DEAD").getCurrentFrame() == animationManager.get("DEAD").getNumberOfFrame() - 1) {
                exist = false;
            }
        }
        else if (currentDirection.x > 0) {
            animationManager.play("MOVING RIGHT");
        } else {
            animationManager.play("MOVING LEFT");
        }
    }

    @Override
    public void die() {
        System.out.println("Balloon die");
        animationManager.play("DEAD");
    }

    @Override
    public void update() {
        updateMovement();
        updateGridPosition();
        hitBox.update();
        updateAnimation();
    }

    @Override
    public void render(int x, int y, GraphicsContext graphicsContext) {
        if (exist)
        animationManager.render(graphicsContext, x, y);
    }
}
