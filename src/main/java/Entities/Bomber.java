package Entities;

import Component.*;
import Map.Map;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;

public class Bomber extends DynamicEntity {

    AnimationManager animationManager;
    boolean moveRight = false;
    boolean moveLeft = false;
    boolean moveUp = false;
    boolean moveDown = false;

    private static final int SPRITE_WIDTH = 16;
    private static final int SPRITE_HEIGHT = 16;

    private boolean alive;
    // Số bom nhiều nhất có thể đặt
    private int bombExplodeRadius = 1;
    private int maxBomb = 1;
    private ArrayList<Bomb> bombList = new ArrayList<>();

    //  CONSTRUCTOR
    public Bomber(int x, int y, Map map) {
        super(x, y, SPRITE_WIDTH * 2, SPRITE_HEIGHT * 2, map.getGridSize(), null, map);
        createAnimation();
        setMap(map);
        movement.setSpeed(4);
        createHitBox(2, 0, (SPRITE_WIDTH - 6)  * 2, (SPRITE_HEIGHT - 2) * 2);
        alive = true;
    }

    private void createAnimation() {
        animationManager = new AnimationManager(this);

        Animation walkUpAnimation = new Animation(
                this, this.width, this.height, 3,
                Sprite.BOMBER_WALK_UP_1, Sprite.BOMBER_WALK_UP_2, Sprite.BOMBER_WALK_UP_3
        );

        Animation walkDownAnimation = new Animation(
                this, this.width, this.height, 3,
                Sprite.BOMBER_WALK_DOWN_1, Sprite.BOMBER_WALK_DOWN_2, Sprite.BOMBER_WALK_DOWN_3
        );

        Animation walkLeftAnimation = new Animation(
                this, this.width, this.height, 1,
                Sprite.BOMBER_WALK_LEFT_1, Sprite.BOMBER_WALK_LEFT_2, Sprite.BOMBER_WALK_LEFT_3
        );

        Animation walkRightAnimation = new Animation(
                this, this.width, this.height, 1,
                Sprite.BOMBER_WALK_RIGHT_1, Sprite.BOMBER_WALK_RIGHT_2, Sprite.BOMBER_WALK_RIGHT_3
        );

        animationManager.addAnimation("WALK_UP", walkUpAnimation);
        animationManager.addAnimation("WALK_DOWN", walkDownAnimation);
        animationManager.addAnimation("WALK_LEFT", walkLeftAnimation);
        animationManager.addAnimation("WALK_RIGHT", walkRightAnimation);
        //  Default animation
        animationManager.play("WALK_DOWN");
    }

    private void createBomb() {
        if (bombList.size() >= maxBomb) {
            return;
        }
        Entity bomb = new Bomb(
                bombExplodeRadius,
                //  Lấy chỉ số hàng cột của ô hiện tại đang đứng
                hitBox.getCenter().x / map.getGridSize() * map.getGridSize()
                        + (hitBox.getLeft() % map.getGridSize() != 0 ? 1 : 0),
                hitBox.getCenter().y / map.getGridSize() * map.getGridSize()
                        + (hitBox.getTop() % map.getGridSize() != 0 ? 1 : 0),
                32, 32, map);
        map.addEntity(bomb);
        bombList.add((Bomb) bomb);
    }

    public void increaseNumberOfBomb() {
        ++maxBomb;
    }

    public void increaseBombRadius() {
        ++bombExplodeRadius;
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
        }
        if (moveDown) {
            animationManager.play("WALK_DOWN");
        }
        if (moveLeft) {
            animationManager.play("WALK_LEFT");
        }
        if (moveRight) {
            animationManager.play("WALK_RIGHT");
        }
        if (!moveUp && !moveDown && !moveRight && !moveLeft){
            animationManager.reset();
            animationManager.getCurrentAnimation().stop();
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

        int startX = this.gridX - 2;
        int endX = this.gridX + 2;
        int startY = this.gridY - 2;
        int endY = this.gridY + 2;
        if (startX < 0) {
            startX = 0;
        }
        if (startY < 0) {
            startY = 0;
        }
        if (endX >= map.getMapGridWidth()) {
            endX = map.getMapGridWidth() - 1;
        }
        if (endY >= map.getMapGridHeight()) {
            endY = map.getMapGridHeight() - 1;
        }
        ArrayList<ArrayList<ArrayList<Entity>>> staticEntityList = map.getStaticEntityList();
        for (int i = startY; i <= endY; ++i) {
            for (int j = startX; j <= endX; ++j) {
                for (int k = 0; k < staticEntityList.get(i).get(j).size(); ++k) {
                    Entity entity = staticEntityList.get(i).get(j).get(k);
                    if (!entity.collisionAble()) {
                        continue;
                    }
                    if (entity.ifCollideDo(this)) {
                        collide = true;
                    }
                }
            }
        }
        /*
        for (int i = 0; i < entities.size(); ++i) {
            if (!entities.get(i).collisionAble()) {
                continue;
            }
            collide = entities.get(i).ifCollideDo(this);
        }*/
        return collide;
    }

    public void updateBombList() {
        for (int i = 0; i < bombList.size();) {
            if (!bombList.get(i).isExist()) {
                bombList.remove(i);
            } else {
                ++i;
            }
        }
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
        updateAnimation();
        updateGridPosition();
        updateHitBox();

        updateBombList();
    }

    public void render(GraphicsContext graphicsContext) {
        animationManager.render(graphicsContext,
                this.x - map.getCamera().getStart().x,
                this.y - map.getCamera().getStart().y);
        /*
            hitBox.render(hitBox.getLeft() - map.getCamera().getStart().x,
                hitBox.getTop() - map.getCamera().getStart().y,
                graphicsContext);
         */
    }
}
