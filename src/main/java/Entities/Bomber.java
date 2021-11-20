package Entities;

import Component.*;
import Entities.Enemy.Enemy;
import Entities.PowerUp.PowerUp;
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

    BombManager bombManager;

    private static final int SPRITE_WIDTH = 16;
    private static final int SPRITE_HEIGHT = 16;

    private boolean alive;
    private boolean newDied = false;
    private int timedied = 0; // thời gian bị mất mạng gần nhất

    //Score
    private Score Score = null;

    //HP
    private int HP = 3;

    // Tọa độ ban đầu của bomber;
    int count_feed = 0;
    private int beforeX = 0;
    private int beforeY = 0;


    //Speed up.
    private int BASIS_SPEED = 2;
    private int speed = 2;

    //  Chức năng
    //  Có thể đi xuyên tường
    private boolean wallPass = false;
    //  Có thể đi xuyên bom
    private boolean bombPass = false;
    //  Miễn nhiễm với bom nổ
    private boolean flamePass = false;
    private boolean eatenFlamePass = false;
    //  Miễn ảnh hưởng khi va chạm với Enemy
    private boolean enemyPass = false;
    private boolean eatenEnemyPass = false;

    //  CONSTRUCTOR
    public Bomber(int x, int y, Map map) {
        super(x, y, SPRITE_WIDTH * 2, SPRITE_HEIGHT * 2, map.getGridSize(), null, map);
        createAnimation();
        setMap(map);
        movement.setSpeed(speed * BASIS_SPEED);
        createHitBox();
        alive = true;
        bombManager = new BombManager(this, map, 1, 1);
        bombManager.disableDetonator();
        Score = new Score();
        beforeX = x;
        beforeY = y;
    }

    private void createHitBox() {
        createHitBox(2, 0, (SPRITE_WIDTH - 6)  * 2, SPRITE_HEIGHT * 2);
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
                this, this.width, this.height, 2,
                Sprite.BOMBER_WALK_LEFT_1, Sprite.BOMBER_WALK_LEFT_2, Sprite.BOMBER_WALK_LEFT_3
        );

        Animation walkRightAnimation = new Animation(
                this, this.width, this.height, 2,
                Sprite.BOMBER_WALK_RIGHT_1, Sprite.BOMBER_WALK_RIGHT_2, Sprite.BOMBER_WALK_RIGHT_3
        );

        Animation deadAnimation = new Animation(this, this.width, this.height, 2,
                Sprite.BOMBER_DIE_1, Sprite.BOMBER_DIE_2, Sprite.BOMBER_DIE_3
        );

        animationManager.addAnimation("WALK_UP", walkUpAnimation);
        animationManager.addAnimation("WALK_DOWN", walkDownAnimation);
        animationManager.addAnimation("WALK_LEFT", walkLeftAnimation);
        animationManager.addAnimation("WALK_RIGHT", walkRightAnimation);
        animationManager.addAnimation("DEAD", deadAnimation);
        //  Default animation
        animationManager.play("WALK_DOWN");
    }

    /**
     * setHp
     */

    public int getHP() {
        return HP;
    }

    /**
     * Set beforeX, beforeY
     */

    public int getCount_feed() {
        return count_feed;
    }

    public void setCount_feed(int count_feed) {
        this.count_feed = count_feed;
    }

    /**
     * Set Score
     */

    public Component.Score getScore() {
        return Score;
    }

    /**
     * Set Item.
     */

    public boolean isEatenFlamePass() {
        return eatenFlamePass;
    }

    public boolean isEatenEnemyPass() {
        return eatenEnemyPass;
    }

    public void setEatenFlamePass(boolean eatenFlamePass) {
        this.eatenFlamePass = eatenFlamePass;
    }

    public void setEatenEnemyPass(boolean eatenEnemyPass) {
        this.eatenEnemyPass = eatenEnemyPass;
    }

    public boolean isEnemyPass() {
        return enemyPass;
    }

    public void setEnemyPass(boolean enemyPass) {
        this.enemyPass = enemyPass;
    }

    public boolean isFlamePass() {
        return flamePass;
    }

    public void setWallPass(boolean wallPass) {
        this.wallPass = wallPass;
    }

    public void setBombPass(boolean bombPass) {
        this.bombPass = bombPass;
    }

    public void setFlamePass(boolean flamePass) {
        this.flamePass = flamePass;
    }

    private void createBomb() {
        bombManager.createBomb();
    }

    public void increaseNumberOfBomb() {
        bombManager.increaseNumberOfBomb();
    }

    public void increaseBombRadius() {
        bombManager.increaseBombRadius();
    }

    public void increaseSpeed() {
        ++speed;
        movement.setSpeed(speed * BASIS_SPEED);
    }

    public void setDetonator() {
        if (bombManager.isDetonatorEnable()) {
            bombManager.disableDetonator();
        } else {
            bombManager.enableDetonator();
        }
    }

    /**
     * keyPressed: false: Key released
     * keyPressed: true: Key pressed
     */
    public void updateInput(KeyEvent keyEvent, boolean isKeyPressed) {
        if (keyEvent.getCode() == KeyCode.A && isKeyPressed) {
            createBomb();
        }
        if (keyEvent.getCode() == KeyCode.S && isKeyPressed) {
            bombManager.explodeLatestBomb();
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
        int directionX = movement.getDirection().x;
        int directionY = movement.getDirection().y;
        if (moveRight) {
//            count_feed ++;
            directionX = 1;
        }
        else if (moveLeft) {
//            count_feed ++;
            directionX = -1;
        }
        else {
            directionX = 0;
        }
        if (moveDown) {
//            count_feed ++;
            directionY = 1;
        }
        else if (moveUp) {
//            count_feed ++;
            directionY = -1;
        }
        else {
            directionY = 0;
        }
        movement.update(directionX, directionY);

    }

    /**
     * Cập nhập vị trí mới.
     */
    protected void updateMovement() {
        movement.move();
        //map.moveCamera(movement.getVelocity());
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
        if (!alive) {
            animationManager.play("DEAD");
            if (animationManager.getCurrentAnimation().getCurrentFrame()
                    == animationManager.getCurrentAnimation().getNumberOfFrame() - 1) {
                exist = false;
            }
        }
        else if (moveUp) {
            animationManager.play("WALK_UP");
        }
        else if (moveDown) {
            animationManager.play("WALK_DOWN");
        }
        else if (moveLeft) {
            animationManager.play("WALK_LEFT");
        }
        else if (moveRight) {
            animationManager.play("WALK_RIGHT");
        }
        //if (!moveUp && !moveDown && !moveRight && !moveLeft){
        else {
            animationManager.reset();
            animationManager.getCurrentAnimation().stop();
        }
        animationManager.update();
    }




    /**
     * Kiểm tra entity truyền vào có thể va chạm với this hay không
     */
    public boolean canCollideWithStaticEntity(Entity entity) {
        if (entity instanceof Stone
                || (entity instanceof Brick && !wallPass)
                || (entity instanceof Bomb && !bombPass)
                || (entity instanceof BombFlame && !flamePass)
                || entity instanceof PowerUp) {
            return true;
        }
        return false;
    }

    /**
     * Va chạm với bản đồ.
     */
    public boolean collisionWithMapEntities() {
        /**
         * Xét vị trí tiếp theo có va chạm với bản đồ hay không.
         */
        boolean collide = collisionWithMap();
        if (enemyPass) {
            return !enemyPass;
        }
        //  Va chạm với vật thể động
        ArrayList<Entity> dynamicEntityList = map.getDynamicEntityList();
        for (int i = 0; i < dynamicEntityList.size(); ++i) {
            Entity entity = dynamicEntityList.get(i);
            if (!entity.collisionAble()) {
                continue;
            }
            if (entity.ifCollideDo(this)) {
                collide = true;
            }
        }
        return collide;
    }

    @Override
    public void die() {
        if (newDied) {
            return;
        }
        if (HP <= 0) {
            newDied = false;
            alive = false;
            movement.setSpeed(0);
        } else {
            HP--;
            if (HP != 0) {
                x = beforeX;
                y = beforeY;
            }
            timedied = map.getTime().countSecond();
            newDied = true;
            count_feed = 0;
        }
    }

    public boolean isAlive() {
        return alive;
    }

    @Override
    public void update() {
        if (HP <= 0) {
            newDied = false;
            alive = false;
        }
        if (newDied) {
            if (map.getTime().countSecond() - timedied <= 10) {
                enemyPass = true;
                flamePass = true;
            } else {
                if (!eatenFlamePass) {
                    flamePass = false;
                }
                if (!eatenEnemyPass) {
                    enemyPass = false;
                }
                newDied = false;
            }
        }
        movement.update(movement.getDirection().x, movement.getDirection().y);
        collisionWithMapEntities();
        updateMovement();
        updateAnimation();
        updateGridPosition();
        updateHitBox();

        //updateBombList();
        bombManager.update();

    }

    public void render(GraphicsContext graphicsContext) {
        /*if (!exist) {
            return;
        }*/
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
