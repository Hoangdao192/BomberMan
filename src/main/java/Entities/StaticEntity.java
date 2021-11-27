package Entities;

import Component.HitBox;
import Component.Movement;
import Component.Sprite;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;

public abstract class StaticEntity extends Entity {

    public StaticEntity(int x, int y, int width, int height, int gridSize, Sprite sprite) {
        super(x, y, width, height, gridSize, sprite);
    }

    @Override
    public boolean ifCollideDo(Entity other) {
        if (!(other instanceof DynamicEntity)) {
            return false;
        }

        if (other instanceof Bomber) {
            return collideWithBomber(other);
        }
        //  Hitbox của other ở vị trí tiếp theo khi di chuyển.
        HitBox nextEntityPositionHitbox =
                other.getHitBox().getNextPosition(((DynamicEntity) other).getMovement());
        if (!hitBox.intersects(nextEntityPositionHitbox)) {
            return false;
        }
        //  Va chạm trên (other ở trên, this ở dưới)
        if (other.getHitBox().getBottom() <= hitBox.getTop()
                && other.getHitBox().getTop() < hitBox.getTop()) {
            other.setHitBoxPositionY(hitBox.getTop() - other.getHitBox().getHeight());
            ((DynamicEntity) other).getMovement().stopY();
            if (other.getHitBox().getRight() - this.hitBox.getLeft() <= gridSize / 2) {
                //  Lượng trùng nhau xét theo trục x
                int deltaX = 1; //1; //other.getHitBox().getRight() - this.hitBox.getLeft() - 1;
                other.setHitBoxPositionX(other.getHitBox().getLeft() - deltaX);
            } else if (this.hitBox.getRight() - other.getHitBox().getLeft() <= gridSize / 2) {
                //  Lượng trùng nhau xét theo trục x
                int deltaX = 1; //this.hitBox.getRight() - other.getHitBox().getLeft() + 1;
                other.setHitBoxPositionX(other.getHitBox().getLeft() + deltaX);
            }
        }
        //  Va chạm dưới
        else if (other.getHitBox().getBottom() > hitBox.getBottom()
                && other.getHitBox().getTop() >= hitBox.getBottom()) {
            other.setHitBoxPositionY(hitBox.getBottom() + 1);
            ((DynamicEntity) other).getMovement().stopY();
            if (other.getHitBox().getRight() - this.hitBox.getLeft() <= gridSize / 2) {
                int deltaX = 1; //other.getHitBox().getRight() - this.hitBox.getLeft() - 1;
                other.setHitBoxPositionX(other.getHitBox().getLeft() - deltaX);
            } else if (this.hitBox.getRight() - other.getHitBox().getLeft() <= gridSize / 2) {
                int deltaX = 1; //this.hitBox.getRight() - other.getHitBox().getLeft() + 1;
                other.setHitBoxPositionX(other.getHitBox().getLeft() + deltaX);
            }
        }
        //  Va chạm phải
        else if (other.getHitBox().getLeft() >= hitBox.getRight()
                && other.getHitBox().getRight() > hitBox.getRight()) {
            other.setHitBoxPositionX(hitBox.getRight() + 1);
            ((DynamicEntity) other).getMovement().stopX();
            if (other.getHitBox().getBottom() - this.hitBox.getTop() <= gridSize / 2) {
                //  Lượng trùng nhau theo trục y
                int deltaY = 1; //other.getHitBox().getBottom() - this.hitBox.getTop() - 1;
                other.setHitBoxPositionY(other.getHitBox().getTop() - deltaY);
            } else if (this.hitBox.getBottom() - other.getHitBox().getTop() <= gridSize / 2) {
                //  Lượng trùng nhau theo trục y
                int deltaY = 1; //this.hitBox.getBottom() - other.getHitBox().getTop() + 1;
                other.setHitBoxPositionY(other.getHitBox().getTop() + deltaY);
            }
        }
        //  Va chạm trái
        else if (other.getHitBox().getRight() <= hitBox.getLeft()
                && other.getHitBox().getLeft() < hitBox.getLeft()) {
            other.setHitBoxPositionX(hitBox.getLeft() - other.getHitBox().getWidth());
            ((DynamicEntity) other).getMovement().stopX();
            if (other.getHitBox().getBottom() - this.hitBox.getTop() <= gridSize / 2) {
                //  Lượng trùng nhau theo trục y
                int deltaY = 1; //other.getHitBox().getBottom() - this.hitBox.getTop() - 1;
                other.setHitBoxPositionY(other.getHitBox().getTop() - deltaY);
            } else if (this.hitBox.getBottom() - other.getHitBox().getTop() <= gridSize / 2) {
                //  Lượng trùng nhau theo trục y
                int deltaY = 1; //this.hitBox.getBottom() - other.getHitBox().getTop() + 1;
                other.setHitBoxPositionY(other.getHitBox().getTop() + deltaY);
            }
        }
        return true;
    }

    public boolean collideWithBomber(Entity other) {
        //  Hitbox của other ở vị trí tiếp theo khi di chuyển.
        HitBox nextEntityPositionHitbox =
                other.getHitBox().getNextPosition(((DynamicEntity) other).getMovement());
        if (!hitBox.intersects(nextEntityPositionHitbox)) {
            return false;
        }
        //  Va chạm trên (other ở trên, this ở dưới)
        if (other.getHitBox().getBottom() <= hitBox.getTop()
                && other.getHitBox().getTop() < hitBox.getTop()) {
            other.setHitBoxPositionY(hitBox.getTop() - other.getHitBox().getHeight());
            ((DynamicEntity) other).getMovement().stopY();
            if (other.getHitBox().getRight() - this.hitBox.getLeft() <= gridSize / 2) {
                //  Lượng trùng nhau xét theo trục x
                int deltaX = other.getHitBox().getRight() - this.hitBox.getLeft();
                if (other.getHitBox().getRight() - this.hitBox.getLeft() > ((DynamicEntity) other).getMovement().getSpeed()) {
                    deltaX = ((DynamicEntity) other).getMovement().getSpeed();
                }
                other.setHitBoxPositionX(other.getHitBox().getLeft() - deltaX - 1);
            } else if (this.hitBox.getRight() - other.getHitBox().getLeft() <= gridSize / 2) {
                //  Lượng trùng nhau xét theo trục x
                int deltaX = this.hitBox.getRight() - other.getHitBox().getLeft();
                if (this.hitBox.getRight() - other.getHitBox().getLeft() > ((DynamicEntity) other).getMovement().getSpeed()) {
                    deltaX = ((DynamicEntity) other).getMovement().getSpeed();
                }
                other.setHitBoxPositionX(other.getHitBox().getLeft() + deltaX + 1);
            }
        }
        //  Va chạm dưới
        else if (other.getHitBox().getBottom() > hitBox.getBottom()
                && other.getHitBox().getTop() >= hitBox.getBottom()) {
            other.setHitBoxPositionY(hitBox.getBottom() + 1);
            ((DynamicEntity) other).getMovement().stopY();
            if (other.getHitBox().getRight() - this.hitBox.getLeft() <= gridSize / 2) {
                int deltaX = other.getHitBox().getRight() - this.hitBox.getLeft();
                if (other.getHitBox().getRight() - this.hitBox.getLeft() > ((DynamicEntity) other).getMovement().getSpeed()) {
                    deltaX = ((DynamicEntity) other).getMovement().getSpeed();
                }
                other.setHitBoxPositionX(other.getHitBox().getLeft() - deltaX - 1);
            } else if (this.hitBox.getRight() - other.getHitBox().getLeft() <= gridSize / 2) {
                int deltaX = this.hitBox.getRight() - other.getHitBox().getLeft();
                if (this.hitBox.getRight() - other.getHitBox().getLeft() > ((DynamicEntity) other).getMovement().getSpeed()) {
                    deltaX = ((DynamicEntity) other).getMovement().getSpeed();
                }
                other.setHitBoxPositionX(other.getHitBox().getLeft() + deltaX + 1);
            }
        }
        //  Va chạm phải
        else if (other.getHitBox().getLeft() >= hitBox.getRight()
                && other.getHitBox().getRight() > hitBox.getRight()) {
            other.setHitBoxPositionX(hitBox.getRight() + 1);
            ((DynamicEntity) other).getMovement().stopX();
            if (other.getHitBox().getBottom() - this.hitBox.getTop() <= gridSize / 2) {
                //  Lượng trùng nhau theo trục y
                int deltaY = other.getHitBox().getBottom() - this.hitBox.getTop();
                if (other.getHitBox().getBottom() - this.hitBox.getTop() > ((DynamicEntity) other).getMovement().getSpeed()) {
                    deltaY = ((DynamicEntity) other).getMovement().getSpeed();
                }
                other.setHitBoxPositionY(other.getHitBox().getTop() - deltaY);
            } else if (this.hitBox.getBottom() - other.getHitBox().getTop() <= gridSize / 2) {
                //  Lượng trùng nhau theo trục y
                int deltaY = this.hitBox.getBottom() - other.getHitBox().getTop();
                if (this.hitBox.getBottom() - other.getHitBox().getTop() > ((DynamicEntity) other).getMovement().getSpeed()) {
                    deltaY = ((DynamicEntity) other).getMovement().getSpeed();
                }
                other.setHitBoxPositionY(other.getHitBox().getTop() + deltaY);
            }
        }
        //  Va chạm trái
        else if (other.getHitBox().getRight() <= hitBox.getLeft()
                && other.getHitBox().getLeft() < hitBox.getLeft()) {
            other.setHitBoxPositionX(hitBox.getLeft() - other.getHitBox().getWidth());
            ((DynamicEntity) other).getMovement().stopX();
            if (other.getHitBox().getBottom() - this.hitBox.getTop() <= gridSize / 2) {
                //  Lượng trùng nhau theo trục y
                int deltaY = other.getHitBox().getBottom() - this.hitBox.getTop();
                if (other.getHitBox().getBottom() - this.hitBox.getTop() > ((DynamicEntity) other).getMovement().getSpeed()) {
                    deltaY = ((DynamicEntity) other).getMovement().getSpeed();
                }
                other.setHitBoxPositionY(other.getHitBox().getTop() - deltaY);
            } else if (this.hitBox.getBottom() - other.getHitBox().getTop() <= gridSize / 2) {
                //  Lượng trùng nhau theo trục y
                int deltaY = this.hitBox.getBottom() - other.getHitBox().getTop();
                if (this.hitBox.getBottom() - other.getHitBox().getTop() > ((DynamicEntity) other).getMovement().getSpeed()) {
                    deltaY = ((DynamicEntity) other).getMovement().getSpeed();
                }
                other.setHitBoxPositionY(other.getHitBox().getTop() + deltaY);
            }
        }
        return true;
    }
}
