package Entities.Enemy;

import Component.Animation;
import Component.AnimationManager;
import Component.HitBox;
import Entities.Bomber;
import Entities.Entity;
import Map.Map;
import Utils.Vector2i;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class Balloon extends Enemy {
    private final String IMAGE_PATH = "Graphic/Entity/Enemy/Balloon.png";
    private final int SPRITE_WIDTH = 16;
    private final int SPRITE_HEIGHT = 16;
    private Vector2i currentDirection;
    AnimationManager animationManager;

    public Balloon(int x, int y, int width, int height, Map map) {
        super(x, y, width, height, null, map);
        createAnimation();
        movement.setSpeed(5);
        currentDirection = new Vector2i(1, 0);
        collision = true;
    }

    public void createAnimation() {
        Image spriteSheet = new Image(IMAGE_PATH);
        animationManager = new AnimationManager(this);
        Animation movingLeft = new Animation(this, spriteSheet, SPRITE_WIDTH, SPRITE_HEIGHT, 2, 0, 2);
        Animation movingRight = new Animation(this, movingLeft.getSpriteSheet(), 2, 3, 5);
        Animation dead = new Animation(this, movingLeft.getSpriteSheet(), 7, 6, 9);
        animationManager.addAnimation("MOVING LEFT", movingLeft);
        animationManager.addAnimation("MOVING RIGHT", movingRight);
        animationManager.addAnimation("DEAD", dead);
        animationManager.play("MOVING LEFT");
    }

    public boolean collisionWithMap() {
        /**
         * Xét vị trí tiếp theo có va chạm với bản đồ hay không.
         */
        final ArrayList<ArrayList<Entity>> entities = map.getEntityList();
        //  Hitbox của bomber ở vị trí tiếp theo khi di chuyển.
        HitBox nextPositionHitbox = hitBox.getNextPosition(movement);
        boolean collide = false;
        for (int i = 0; i < entities.size(); ++i) {
            for (int j = 0; j < entities.get(i).size(); ++j) {
                if (!entities.get(i).get(j).collisionAble()
                    || entities.get(i).get(j) == this) {
                    continue;
                }
                if (entities.get(i).get(j).ifCollideDo(this)) {
                    collide = true;
                }
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
            animationManager.play("DEAD");
        }
        return true;
    }

    @Override
    protected void updateMovement() {
        //  Đổi hướng di chuyển khi va chạm với một vật thể nào đó.
        movement.update(currentDirection.x, currentDirection.y);
        if (collisionWithMap()) {
            if (currentDirection.x == 1 && currentDirection.y == 0) {
                currentDirection.x = -1;
            }
            else if (currentDirection.x == -1 && currentDirection.y == 0) {
                currentDirection.x = 0;
                currentDirection.y = 1;
            }
            else if (currentDirection.x == 0 && currentDirection.y == 1) {
                currentDirection.y = -1;
            }
            else if (currentDirection.x == 0 && currentDirection.y == -1) {
                currentDirection.x = 1;
                currentDirection.y = 0;
            }
        } else {
            movement.move();
        }
    }

    private void updateAnimation() {
        animationManager.update();
        if (animationManager.getCurrentAnimationKey().equals("DEAD")) {
            movement.setSpeed(0);
            if (animationManager.get("DEAD").getFrame() == animationManager.get("DEAD").getEndFrame()) {
                exist = false;
            }
        }
    }

    @Override
    public void update() {
        updateMovement();
        hitBox.update();
        updateAnimation();
    }

    @Override
    public void render(int x, int y, GraphicsContext graphicsContext) {
        if (exist)
        animationManager.render(graphicsContext, x, y);
    }
}
