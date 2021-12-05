package Entities;

import Component.*;
import Entities.PowerUp.PowerUp;
import Map.Map;
import Setting.Setting;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;

public class Bomber extends DynamicEntity {

    AnimationManager animationManager;
    int previousDirection = 1;
    private final int DIRECTION_RIGHT = 1;
    private final int DIRECTION_LEFT = 2;
    private final int DIRECTION_DOWN = 3;
    private final int DIRECTION_UP = 4;
    boolean moveRight = false;
    boolean moveLeft = false;
    boolean moveUp = false;
    boolean moveDown = false;

    BombManager bombManager;
    private final int MAX_BOMB = 10;
    private final int MAX_BOMB_EXPLODE_RADIUS = 10;
    private final int MAX_SPEED = 10;

    //Speed up.
    private final int BASE_SPEED = 2;
    private int speed = 2;

    //  Chức năng
    //  Có thể đi xuyên tường
    private boolean wallPass = false;
    //  Có thể đi xuyên bom
    private boolean bombPass = false;
    //  Miễn nhiễm với bom nổ
    private boolean flamePass = false;
    //  Miễn ảnh hưởng khi va chạm với Enemy
    private boolean enemyPass = true;
    //  Trạng thái bất diệt miễn nhiễm với mọi sát thương
    private boolean immortal = false;
    private final int MAX_IMMORTAL_TIME = 10;
    //  Đếm thời gian immortal
    private int immortalTimeCount = 0;

    private final int DEFAULT_SPRITE_SIZE = 16;

    //  Các chỉ số
    private Score score;
    private int HP;
    private boolean alive;

    // Kiểm tra bomber có đi qua portal ?
    private boolean passOverPortal = false;

    private MediaPlayer mediaPlayer;
    private MediaPlayer damagedSoundPlayer;
    private MediaPlayer eatPowerUpSoundPlayer;

    //  CONSTRUCTOR
    public Bomber(int x, int y, Map map) {
        super(x, y, map.getGridSize(), map.getGridSize(), map.getGridSize(), null, map);
        createAnimation();
        createMovement();
        createHitBox();
        createBombManager();
        setMap(map);

        alive = true;
        score = new Score();
        HP = 1;
        createSound();
    }

    //  INITIALIZER
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

        Animation standDownAnimationFlicker = new Animation(
                this, this.width, this.height, 2,
                Sprite.BOMBER_WALK_DOWN_1, Sprite.SPRITE_TRANSPARENT,
                Sprite.BOMBER_WALK_DOWN_1, Sprite.SPRITE_TRANSPARENT,
                Sprite.BOMBER_WALK_DOWN_1, Sprite.SPRITE_TRANSPARENT
        );

        Animation standUpAnimationFlicker = new Animation(
                this, this.width, this.height, 2,
                Sprite.BOMBER_WALK_UP_1, Sprite.SPRITE_TRANSPARENT,
                Sprite.BOMBER_WALK_UP_1, Sprite.SPRITE_TRANSPARENT,
                Sprite.BOMBER_WALK_UP_1, Sprite.SPRITE_TRANSPARENT
        );

        Animation standLeftAnimationFlicker = new Animation(
                this, this.width, this.height, 2,
                Sprite.BOMBER_WALK_LEFT_1, Sprite.SPRITE_TRANSPARENT,
                Sprite.BOMBER_WALK_LEFT_1, Sprite.SPRITE_TRANSPARENT,
                Sprite.BOMBER_WALK_LEFT_1, Sprite.SPRITE_TRANSPARENT
        );

        Animation standRightAnimationFlicker = new Animation(
                this, this.width, this.height, 2,
                Sprite.BOMBER_WALK_RIGHT_1, Sprite.SPRITE_TRANSPARENT,
                Sprite.BOMBER_WALK_RIGHT_1, Sprite.SPRITE_TRANSPARENT,
                Sprite.BOMBER_WALK_RIGHT_1, Sprite.SPRITE_TRANSPARENT
        );

        animationManager.addAnimation("WALK_UP", walkUpAnimation);
        animationManager.addAnimation("WALK_DOWN", walkDownAnimation);
        animationManager.addAnimation("WALK_LEFT", walkLeftAnimation);
        animationManager.addAnimation("WALK_RIGHT", walkRightAnimation);
        animationManager.addAnimation("WALK_UP_FLICKER", walkUpAnimationFlicker);
        animationManager.addAnimation("WALK_DOWN_FLICKER", walkDownAnimationFlicker);
        animationManager.addAnimation("WALK_LEFT_FLICKER", walkLeftAnimationFlicker);
        animationManager.addAnimation("WALK_RIGHT_FLICKER", walkRightAnimationFlicker);
        animationManager.addAnimation("STAND_UP_FLICKER", standUpAnimationFlicker);
        animationManager.addAnimation("STAND_DOWN_FLICKER", standDownAnimationFlicker);
        animationManager.addAnimation("STAND_LEFT_FLICKER", standLeftAnimationFlicker);
        animationManager.addAnimation("STAND_RIGHT_FLICKER", standRightAnimationFlicker);
        animationManager.addAnimation("DEAD", deadAnimation);
        //  Default animation
        animationManager.play("WALK_DOWN");
    }

    private void createMovement() {
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
    }

    private void createHitBox() {
        createHitBox( 0, 0, (DEFAULT_SPRITE_SIZE - 6)  * 2, height);
    }

    private void createBombManager() {
        bombManager = new BombManager(this, map, 10, 10);
        bombManager.disableDetonator();
    }

    private void createSound() {
        Media moveSound = new Media(new File("src/main/resources/Sound/move_sound.mp3").toURI().toString());
        mediaPlayer = new MediaPlayer(moveSound);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);

        Media damageSound = new Media(new File("src/main/resources/Sound/take_damage.mp3").toURI().toString());
        damagedSoundPlayer = new MediaPlayer(damageSound);

        Media eatPowerUpSound = new Media(new File("src/main/resources/Sound/power_up.wav").toURI().toString());
        eatPowerUpSoundPlayer = new MediaPlayer(eatPowerUpSound);
    }

    private void createBomb() {
        bombManager.createBomb();
    }

    //  INCREASE ATTRIBUTE
    public void increaseNumberOfBomb() {
        if (bombManager.getMaxBomb() < MAX_BOMB) {
            bombManager.increaseNumberOfBomb();
        }
    }

    public void increaseBombRadius() {
        if (bombManager.getBombExplodeRadius() < MAX_BOMB_EXPLODE_RADIUS) {
            bombManager.increaseBombRadius();
        }
    }

    public void increaseHP() {
        ++HP;
    }

    public void increaseSpeed() {
        if (movement.getSpeed() < MAX_SPEED) {
            ++speed;
            movement.setSpeed(speed * BASE_SPEED);
        }
    }

    /**
     * Kiểm tra entity truyền vào có thể va chạm với this hay không
     */
    @Override
    public boolean canCollideWithStaticEntity(Entity entity) {
        if (entity instanceof Stone
            || (entity instanceof Brick && !wallPass)
            || (entity instanceof Bomb && !bombPass)
            || (entity instanceof BombFlame && !flamePass && !immortal)
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
        if (!enemyPass && !immortal) {
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
    @Override
    public void setMap(Map map) {
        super.setMap(map);
        bombManager.setMap(map);
    }

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

    public void setImmortal(boolean immortal) {
        this.immortal = immortal;
        if (immortal) {
            immortalTimeCount = map.getTime().countSecond();
        }
    }

    public void setPassOverPortal(boolean passOverPortal) {
        this.passOverPortal = passOverPortal;
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

    public boolean isPassOverPortal() {
        return passOverPortal;
    }

    public boolean isEnemyPass() {
        return enemyPass;
    }

    public boolean isFlamePass() {
        return flamePass;
    }

    public boolean isBombPass() {
        return bombPass;
    }

    public boolean isWallPass() {
        return wallPass;
    }

    public boolean isDetonatorEnable() {
        return bombManager.isDetonatorEnable();
    }

    @Override
    public void die() {
        if (immortal) {
            return;
        }
        HP--;
        if (Setting.isSoundOn()) {
            damagedSoundPlayer.seek(Duration.ZERO);
            damagedSoundPlayer.play();
        }
        if (HP <= 0) {
            mediaPlayer.stop();
            HP = 0;
            alive = false;
            movement.setSpeed(0);
        } else {
            immortalTimeCount = map.getTime().countSecond();
            immortal = true;
        }
    }

    @Override
    public void update() {
        if (immortal) {
            if (map.getTime().countSecond() - immortalTimeCount > MAX_IMMORTAL_TIME) {
                immortal = false;
            }
        }

        updateMovement();
        updateHitBox();
        updateAnimation();
        updateGridPosition();

        bombManager.update();
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
        int directionX;
        int directionY;
        if (moveRight) {
            directionX = 1;
            previousDirection = DIRECTION_RIGHT;
        }
        else if (moveLeft) {
            directionX = -1;
            previousDirection = DIRECTION_LEFT;
        }
        else {
            directionX = 0;
        }
        if (moveDown) {
            directionY = 1;
            previousDirection = DIRECTION_DOWN;
        }
        else if (moveUp) {
            previousDirection = DIRECTION_UP;
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

        movement.update(movement.getDirection().x, movement.getDirection().y);
        collisionWithMapEntities();
        movement.move();
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
        else if (immortal) {
            if (moveUp) {
                animationManager.play("WALK_UP_FLICKER");
            } else if (moveDown) {
                animationManager.play("WALK_DOWN_FLICKER");
            } else if (moveLeft) {
                animationManager.play("WALK_LEFT_FLICKER");
            } else if (moveRight) {
                animationManager.play("WALK_RIGHT_FLICKER");
            } else if (previousDirection == DIRECTION_RIGHT) {
                animationManager.play("STAND_RIGHT_FLICKER");
            } else if (previousDirection == DIRECTION_LEFT) {
                animationManager.play("STAND_LEFT_FLICKER");
            } else if (previousDirection == DIRECTION_DOWN) {
                animationManager.play("STAND_DOWN_FLICKER");
            } else if (previousDirection == DIRECTION_UP) {
                animationManager.play("STAND_UP_FLICKER");
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
