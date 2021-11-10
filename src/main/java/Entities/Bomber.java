package Entities;

import Component.Animation;
import Component.AnimationManager;
import Component.HitBox;
import Map.Map;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;

public class Bomber extends DynamicEntity {

    AnimationManager animationManager;
    boolean moveRight = false;
    boolean moveLeft = false;
    boolean moveUp = false;
    boolean moveDown = false;

    private final String SPRITE_SHEET_PATH = "";
    private static final int SPRITE_WIDTH = 16;
    private static final int SPRITE_HEIGHT = 16;

    private boolean alive;

    //  CONSTRUCTOR
    public Bomber(int x, int y, Map map) {
        super(x, y, SPRITE_WIDTH * 2, SPRITE_HEIGHT * 2, map.getGridSize(), null, map);
        image = new Image("Graphic/Entity/Bomber/bomber.png");
        createAnimation();
        setMap(map);
        movement.setSpeed(4);
        createHitBox(2, 0, (SPRITE_WIDTH - 6)  * 2, (SPRITE_HEIGHT - 2) * 2);
        alive = true;
    }

    private void createAnimation() {
        animationManager = new AnimationManager(this);
        Animation walkUpAnimation = new Animation(this, image, SPRITE_WIDTH, SPRITE_HEIGHT, 3, 0, 2);
        Animation walkDownAnimation = new Animation(this, walkUpAnimation.getSpriteSheet(), 3, 3, 5);
        Animation walkLeftAnimation = new Animation(this, walkUpAnimation.getSpriteSheet(), 2, 6, 8);
        Animation walkRightAnimation = new Animation(this, walkUpAnimation.getSpriteSheet(), 2, 9, 11);
        animationManager.addAnimation("WALK_UP", walkUpAnimation);
        animationManager.addAnimation("WALK_DOWN", walkDownAnimation);
        animationManager.addAnimation("WALK_LEFT", walkLeftAnimation);
        animationManager.addAnimation("WALK_RIGHT", walkRightAnimation);
        //  Default animation
        animationManager.play("WALK_DOWN");
    }

    private void createBomb() {
        Entity bomb = new Bomb(
                1,
                //  Lấy chỉ số hàng cột của ô hiện tại đang đứng
                hitBox.getCenter().x / map.getGridSize() * map.getGridSize()
                        + (hitBox.getLeft() % map.getGridSize() != 0 ? 1 : 0),
                hitBox.getCenter().y / map.getGridSize() * map.getGridSize()
                        + (hitBox.getTop() % map.getGridSize() != 0 ? 1 : 0),
                32, 32, map);
        map.addEntity(bomb);
    }

    /**
     * keyPressed: false: Key released
     * keyPressed: true: Key pressed
     */
    public void updateInput(KeyEvent keyEvent, boolean isKeyPressed) {
        if (keyEvent.getCode() == KeyCode.A && isKeyPressed) {
            createBomb();
        }
        if (keyEvent.getCode() == KeyCode.RIGHT) {
            moveRight = isKeyPressed;
        }
        if (keyEvent.getCode() == KeyCode.LEFT) {
            moveLeft = isKeyPressed;
        }
        if (keyEvent.getCode() == KeyCode.UP) {
            moveUp = isKeyPressed;
        }
        if (keyEvent.getCode() == KeyCode.DOWN) {
            moveDown = isKeyPressed;
        }
        int directionX = 0, directionY = 0;
        if (moveRight) {
            directionX = 1;
        }
        if (moveLeft) {
            directionX = -1;
        }
        if (moveDown) {
            directionY = 1;
        }
        if (moveUp) {
            directionY = -1;
        }
        movement.update(directionX, directionY);
    }

    /**
     * Cập nhập vị trí mới.
     */
    protected void updateMovement() {
        movement.move();
        map.moveCamera(movement.getVelocity());
    }

    /**
     * Cập nhập vị trí Hit box.
     */
    private void updateHitBox() {
        hitBox.update();
    }

    /**
     * Cập nhập animation.
     */
    private void updateAnimation() {
        if (moveUp) {
            animationManager.play("WALK_UP");
        } else if (moveDown) {
            animationManager.play("WALK_DOWN");
        }
        else if (moveLeft) {
            animationManager.play("WALK_LEFT");
        }
        else if (moveRight) {
            animationManager.play("WALK_RIGHT");
        } else {
            animationManager.reset();
        }
        animationManager.update();
    }

    /**
     * Va chạm với bản đồ.
     */
    public boolean collisionWithMapEntities() {
        /**
         * Xét vị trí tiếp theo có va chạm với bản đồ hay không.
         */
        final ArrayList<Entity> entities = map.getEntityList();
        //  Hitbox của bomber ở vị trí tiếp theo khi di chuyển.
        HitBox nextPositionHitbox = hitBox.getNextPosition(movement);
        boolean collide = false;
        for (int i = 0; i < entities.size(); ++i) {
            if (!entities.get(i).collisionAble()) {
                continue;
            }
            collide = entities.get(i).ifCollideDo(this);
        }
        return collide;
    }

    @Override
    public void die() {
        System.out.println("collide");
    }

    public boolean isAlive() {
        return alive;
    }

    @Override
    public void update() {
        collisionWithMapEntities();
        updateMovement();
        updateGridPosition();
        updateHitBox();
        updateAnimation();
    }

    @Override
    public void render(GraphicsContext graphicsContext) {
        animationManager.render(graphicsContext,
                this.x - map.getCamera().getStart().x,
                this.y - map.getCamera().getStart().y);
        hitBox.render(hitBox.getLeft() - map.getCamera().getStart().x,
                hitBox.getTop() - map.getCamera().getStart().y,
                graphicsContext);
    }
}
