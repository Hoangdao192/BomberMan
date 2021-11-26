package Component;

import Entities.DynamicEntity;
import Utils.Vector2i;

public class Movement {
    private int speed;
    private Vector2i velocity;
    private Vector2i direction;
    private final DynamicEntity entity;

    //  CONSTRUCTOR
    public Movement(DynamicEntity entity, int speed) {
        this.speed = speed;
        this.entity = entity;
        velocity = new Vector2i();
        direction = new Vector2i();
    }

    //  SETTER
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    //  GETTER
    public int getSpeed() {
        return speed;
    }

    public Vector2i getVelocity() {
        return velocity;
    }

    public Vector2i getDirection() {
        return direction;
    }

    //  FUNCTION
    public void update(int directionX, int directionY) {
        direction.x = directionX;
        direction.y = directionY;
        velocity.x = directionX * speed;
        velocity.y = directionY * speed;
    }

    /**
     * Tọa độ ở vị trí tiếp theo.
     */
    public Vector2i getNextPosition() {
        return new Vector2i(
                entity.getX() + velocity.x,
                entity.getY() + velocity.y
        );
    }

    public void stopX() {
        //direction.x = 0;
        velocity.x = 0;
    }

    public void stopY() {
        //direction.y = 0;
        velocity.y = 0;
    }

    public void move() {
        entity.setX(entity.getX() + velocity.x);
        entity.setY(entity.getY() + velocity.y);
        if (entity.getX() < 0) {
            entity.setX(0);
        }
        if (entity.getY() < 0) {
            entity.setY(0);
        }
    }
}
