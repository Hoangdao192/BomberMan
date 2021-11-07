package Entities;

import Component.HitBox;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;

public class Stone extends StaticEntity{
    public Stone(int x, int y, int width, int height, Image image) {
        super(x, y, width, height, image);
    }

    public Stone(int x, int y, int width, int height, Image image, Rectangle2D imageOffset) {
        super(x, y, width, height, image, imageOffset);
    }

    @Override
    public boolean ifCollideDo(Entity other) {
        if (!(other instanceof DynamicEntity)) {
            return false;
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
            other.setHitBoxPosition(
                    other.getHitBox().getLeft(),
                    hitBox.getTop() - 1 - other.getHitBox().getHeight()
            );
            ((DynamicEntity) other).getMovement().getVelocity().y = 0;
        }
        //  Va chạm dưới
        else if (other.getHitBox().getBottom() > hitBox.getBottom()
                && other.getHitBox().getTop() >= hitBox.getBottom()) {
            other.setHitBoxPosition(
                    other.getHitBox().getLeft(),
                    hitBox.getBottom() + 1
            );
            ((DynamicEntity) other).getMovement().getVelocity().y = 0;
        }
        //  Va chạm phải
        else if (other.getHitBox().getLeft() >= hitBox.getRight()
                && other.getHitBox().getRight() > hitBox.getRight()) {
            other.setHitBoxPosition(
                    hitBox.getRight() + 1,
                    other.getHitBox().getTop()
            );
            ((DynamicEntity) other).getMovement().getVelocity().x = 0;
        }
        //  Va chạm trái
        else if (other.getHitBox().getRight() <= hitBox.getLeft()
                && other.getHitBox().getLeft() < hitBox.getLeft()) {
            other.setHitBoxPosition(
                    hitBox.getLeft() - 1 - other.getHitBox().getWidth(),
                    other.getHitBox().getTop()
            );
            ((DynamicEntity) other).getMovement().getVelocity().x = 0;
        }
        return true;
    }
}
