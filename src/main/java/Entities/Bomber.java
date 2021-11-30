package Entities;

import Component.*;
import Entities.Enemy.Enemy;
import Entities.PowerUp.PowerUp;
import Map.Map;
import Setting.Setting;
import Utils.Vector2i;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
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

    //Speed up.
    private int BASE_SPEED = 2;
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


    //  Các chỉ số
    private Score score;
    private int HP = 1;

    private boolean newDied = false;
    private int timedied = 0; // thời gian bị mất mạng gần nhất

    // Tọa độ ban đầu của bomber;
    int count_feed = 0;

    private MediaPlayer mediaPlayer;

    //  CONSTRUCTOR
    public Bomber(int x, int y, Map map) {
        super(x, y, map.getGridSize(), map.getGridSize(), map.getGridSize(), null, map);
        createAnimation();
        setMap(map);
        movement = new Movement(this, speed * BASE_SPEED) {
            @Override
            public void stopX() {
                setVelocityX(0);
            }

            public void stopY() {
                setVelocityY(0);
            }
        };
        movement.setSpeed(speed * BASE_SPEED);
        createHitBox();
        alive = true;
        bombManager = new BombManager(this, map, 1, 1);
        score = new Score();
        bombManager.disableDetonator();

        createSound();
    }

    private void createSound() {
        Media moveSound = new Media(new File("src/main/resources/Sound/move_sound.mp3").toURI().toString());
        mediaPlayer = new MediaPlayer(moveSound);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    }

    private void createHitBox() {
        createHitBox( 0, 0, (SPRITE_WIDTH - 6)  * 2, height);
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

        Animation walkUpAnimationFlicker = new Animation(
                this, this.width, this.height, 2,
                Sprite.BOMBER_WALK_UP_1, Sprite.SPRITE_TRANSPARENT,
                Sprite.BOMBER_WALK_UP_2, Sprite.SPRITE_TRANSPARENT,
                Sprite.BOMBER_WALK_UP_3, Sprite.SPRITE_TRANSPARENT
        );

        Animation walkDownAnimationFlicker = new Animation(
                this, this.width, this.height, 2,
                Sprite.BOMBER_WALK_DOWN_1, Sprite.SPRITE_TRANSPARENT,
                Sprite.BOMBER_WALK_DOWN_2, Sprite.SPRITE_TRANSPARENT,
                Sprite.BOMBER_WALK_DOWN_3, Sprite.SPRITE_TRANSPARENT
        );

        Animation walkLeftAnimationFlicker = new Animation(
                this, this.width, this.height, 2,
                Sprite.BOMBER_WALK_LEFT_1, Sprite.SPRITE_TRANSPARENT,
                Sprite.BOMBER_WALK_LEFT_2, Sprite.SPRITE_TRANSPARENT,
                Sprite.BOMBER_WALK_LEFT_3, Sprite.SPRITE_TRANSPARENT
        );

        Animation walkRightAnimationFlicker = new Animation (
                this, this.width, this.height, 2,
                Sprite.BOMBER_WALK_RIGHT_1, Sprite.SPRITE_TRANSPARENT,
                Sprite.BOMBER_WALK_RIGHT_2, Sprite.SPRITE_TRANSPARENT,
                Sprite.BOMBER_WALK_RIGHT_3, Sprite.SPRITE_TRANSPARENT
        );

        animationManager.addAnimation("WALK_UP", walkUpAnimation);
        animationManager.addAnimation("WALK_DOWN", walkDownAnimation);
        animationManager.addAnimation("WALK_LEFT", walkLeftAnimation);
        animationManager.addAnimation("WALK_RIGHT", walkRightAnimation);
        animationManager.addAnimation("WALK_UP_FLICKER", walkUpAnimationFlicker);
        animationManager.addAnimation("WALK_DOWN_FLICKER", walkDownAnimationFlicker);
        animationManager.addAnimation("WALK_LEFT_FLICKER", walkLeftAnimationFlicker);
        animationManager.addAnimation("WALK_RIGHT_FLICKER", walkRightAnimationFlicker);
        animationManager.addAnimation("DEAD", deadAnimation);
        //  Default animation
        animationManager.play("WALK_DOWN");
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

    public void increaseHP() {
        ++HP;
    }

    public void increaseSpeed() {
        ++speed;
        movement.setSpeed(speed * BASE_SPEED);
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
            directionX = 1;
        }
        else if (moveLeft) {
            directionX = -1;
        }
        else {
            directionX = 0;
        }
        if (moveDown) {
            directionY = 1;
        }
        else if (moveUp) {
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
        if (Setting.isSoundOn()) {
            if (moveLeft || moveRight || moveUp || moveDown) {
                mediaPlayer.play();
            } else {
                mediaPlayer.stop();
            }
        }
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
        else if (newDied) {
            if (newDied) {
                int checkTime = map.getTime().countMilliSecond() - timedied * 1000;
                if ((checkTime / 200) % 2 == 1) {
                    if (moveUp) {
                        animationManager.play("WALK_UP_FLICKER");
                    } else if (moveDown) {
                        animationManager.play("WALK_DOWN_FLICKER");
                    } else if (moveLeft) {
                        animationManager.play("WALK_LEFT_FLICKER");
                    } else if (moveRight) {
                        animationManager.play("WALK_RIGHT_FLICKER");
                    }
                }
            }
        } else if (moveUp) {
            animationManager.play("WALK_UP");
        } else if (moveDown) {
            animationManager.play("WALK_DOWN");
        } else if (moveLeft) {
            animationManager.play("WALK_LEFT");
        } else if (moveRight) {
            animationManager.play("WALK_RIGHT");
        }
        else {
            animationManager.reset();
            animationManager.getCurrentAnimation().stop();
        }
        animationManager.update();
    }

    /**
     * Kiểm tra entity truyền vào có thể va chạm với this hay không
     */
    @Override
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
        //  Va chạm với vật thể tĩnh
        boolean collide = collisionWithMap();
        //  Va chạm với vật thể động
        if (!enemyPass) {
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
        }
        return collide;
    }

    //  SETTER
    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public void setFlamePass(boolean flamePass) {
        this.flamePass = flamePass;
    }

    public void setEnemyPass(boolean enemyPass) {
        this.enemyPass = enemyPass;
    }

    public void setWallPass(boolean wallPass) {
        this.wallPass = wallPass;
    }

    public void setDetonator(boolean enableDetonator) {
        if (enableDetonator) {
            bombManager.enableDetonator();
        } else {
            bombManager.disableDetonator();
        }
    }

    public void setBombPass(boolean bombPass) {
        this.bombPass = bombPass;
    }

    public void setEatenFlamePass(boolean eatenFlamePass) {
        this.eatenFlamePass = eatenFlamePass;
    }

    public void setEatenEnemyPass(boolean eatenEnemyPass) {
        this.eatenEnemyPass = eatenEnemyPass;
    }

    //  GETTER
    public BombManager getBombManager() {
        return bombManager;
    }

    public Score getScore() {
        return score;
    }

    public int getHP() {
        return HP;
    }

    public boolean isMoveRight() {
        return moveRight;
    }

    public boolean isMoveLeft() {
        return moveLeft;
    }

    public boolean isMoveUp() {
        return moveUp;
    }

    public boolean isMoveDown() {
        return moveDown;
    }

    public boolean isAlive() {
        return alive;
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

    public boolean isEnemyPass() {
        return enemyPass;
    }

    public boolean isFlamePass() {
        return flamePass;
    }

    public void reback() {
        HP = 1;
        movement.setSpeed(BASE_SPEED * speed);
        alive = true;
        exist = true;
        animationManager.play("WALK_RIGHT");
        bombManager.getBombList().clear();
        bombManager = new BombManager(this, map, 1, 1);
    }

    @Override
    public void die() {
        System.out.println("Die");
        if (newDied) {
            return;
        }
        if (HP <= 0) {
            newDied = false;
            alive = false;
            movement.setSpeed(0);
        } else {
            HP--;
            timedied = map.getTime().countSecond();
            newDied = true;
            count_feed = 0;
        }
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
        updateHitBox();
        updateAnimation();
        updateGridPosition();

        bombManager.update();
    }

    public void render(GraphicsContext graphicsContext) {
        /*if (!exist) {
            return;
        }*/
        animationManager.render(graphicsContext,
                this.x - map.getCamera().getStart().x,
                this.y - map.getCamera().getStart().y);

          /*  hitBox.render(hitBox.getLeft() - map.getCamera().getStart().x,
                hitBox.getTop() - map.getCamera().getStart().y,
                graphicsContext);*/

    }
}
