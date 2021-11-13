package Entities.Enemy;

import Component.*;
import Entities.Bomber;
import Entities.Entity;
import Map.Map;
import Utils.RandomInt;
import Utils.Vector2i;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class Balloon extends Enemy {
    private final int DEFAULT_SPEED = 3;
    private Vector2i currentDirection;
    AnimationManager animationManager;

    public Balloon(int x, int y, int width, int height, Map map) {
        super(x, y, width, height, null, map);
        createAnimation();
        collision = true;
        createHitBox();
        createMovement();
    }

    private void createAnimation() {
        animationManager = new AnimationManager(this);
        this.sprite = Sprite.BALLOON_MOVE_RIGHT_1;
        Animation movingLeft = new Animation(
                this, this.width, this.height, 2,
                Sprite.BALLOON_MOVE_LEFT_1, Sprite.BALLOON_MOVE_LEFT_2,
                Sprite.BALLOON_MOVE_LEFT_3
        );

        Animation movingRight = new Animation(
                this, this.width, this.height, 2,
                Sprite.BALLOON_MOVE_RIGHT_1, Sprite.BALLOON_MOVE_RIGHT_2,
                Sprite.BALLOON_MOVE_RIGHT_3
        );

        Animation dead = new Animation(
                this, this.width, this.height, 5,
                Sprite.BALLOON_DIE, Sprite.ENEMY_DIE_1, Sprite.ENEMY_DIE_2,
                Sprite.ENEMY_DIE_3
        );

        animationManager.addAnimation("MOVING LEFT", movingLeft);
        animationManager.addAnimation("MOVING RIGHT", movingRight);
        animationManager.addAnimation("DEAD", dead);
        animationManager.play("MOVING LEFT");
    }

    private void createHitBox() {
        hitBox = new HitBox(
                this, this.width / sprite.getWidth(), 0,
                14 * this.width / sprite.getWidth(),
                16 * this.height / sprite.getHeight());
        //hitBox = new HitBox(this, 0, 0, 32,32);
    }

    private void createMovement() {
        movement.setSpeed(DEFAULT_SPEED);
        currentDirection = new Vector2i(1, 0);
    }

    public boolean collisionWithMap() {
        final ArrayList<ArrayList<ArrayList<Entity>>> staticEntityList = map.getStaticEntityList();
        boolean collide = false;

        int startX = this.gridX - 1;
        int endX = this.gridX + 1;
        int startY = this.gridY - 1;
        int endY = this.gridY + 1;
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
        for (int row = startY; row <= endY; ++row) {
            for (int col = startX; col <= endX; ++col) {
                for (int k = 0; k < staticEntityList.get(row).get(col).size(); ++k) {
                    Entity entity = staticEntityList.get(row).get(col).get(k);
                    if (!entity.collisionAble()) {
                        continue;
                    }
                    if (entity.ifCollideDo(this)) {
                        collide = true;
                    }
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
            ((Bomber) other).die();
        }
        return true;
    }

    private void changeMoveDirection() {
        int tempX = 1;
        int tempY = 0;
        ArrayList<ArrayList<ArrayList<Entity>>> staticEntityList = map.getStaticEntityList();
        //  Kiểm tra các hướng có thể di chuyển
        boolean canMoveRight = true;
        for (int i = 0; i < staticEntityList.get(gridY).get(gridX + 1).size(); ++i) {
            if (staticEntityList.get(gridY).get(gridX + 1).get(i).collisionAble()) {
                canMoveRight = false;
                break;
            }
        }
        boolean canMoveLeft = true;
        for (int i = 0; i < staticEntityList.get(gridY).get(gridX - 1).size(); ++i) {
            if (staticEntityList.get(gridY).get(gridX - 1).get(i).collisionAble()) {
                canMoveLeft = false;
                break;
            }
        }
        boolean canMoveUp = true;
        for (int i = 0; i < staticEntityList.get(gridY - 1).get(gridX).size(); ++i) {
            if (staticEntityList.get(gridY - 1).get(gridX).get(i).collisionAble()) {
                canMoveUp = false;
                break;
            }
        }
        boolean canMoveDown = true;
        for (int i = 0; i < staticEntityList.get(gridY + 1).get(gridX).size(); ++i) {
            if (staticEntityList.get(gridY + 1).get(gridX).get(i).collisionAble()) {
                canMoveDown = false;
                break;
            }
        }

        //  Chọn 1 hướng có thể di chuyển để đi theo hướng đó
        boolean canMove = false;
        if (canMoveLeft || canMoveRight || canMoveUp || canMoveDown) {
            while (!canMove) {
                int random = RandomInt.random(0, 4);
                switch (random) {
                    case 0:
                        canMove = canMoveLeft;
                        tempX = -1;
                        tempY = 0;
                        break;
                    case 1:
                        canMove = canMoveRight;
                        tempX = 1;
                        tempY = 0;
                        break;
                    case 2:
                        canMove = canMoveUp;
                        tempX = 0;
                        tempY = -1;
                        break;
                    case 3:
                        canMove = canMoveDown;
                        tempX = 0;
                        tempY = 1;
                        break;
                }
            }
        }
        currentDirection.x = tempX;
        currentDirection.y = tempY;
    }

    @Override
    protected void updateMovement() {
        //  Đổi hướng di chuyển khi va chạm với một vật thể nào đó.
        movement.update(currentDirection.x, currentDirection.y);
        if (collisionWithMap()) {
            changeMoveDirection();
        } else {
            //  Xác suất đổi chiều tự động là 20%
            if (hitBox.getLeft() == gridX * gridSize && hitBox.getTop() == gridY * gridSize) {
                changeMoveDirection();
                movement.update(currentDirection.x, currentDirection.y);
            }
            movement.move();
        }
    }

    private void updateAnimation() {
        animationManager.update();
        if (animationManager.getCurrentAnimationKey().equals("DEAD")) {
            movement.setSpeed(0);
            if (animationManager.get("DEAD").getCurrentFrame() == animationManager.get("DEAD").getNumberOfFrame() - 1) {
                destroy();
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
        animationManager.play("DEAD");
    }

    @Override
    public void update() {
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
