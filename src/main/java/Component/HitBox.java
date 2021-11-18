package Component;

import Entities.Entity;
import Utils.Vector2i;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class HitBox {
    private final Entity entity;

    //  Tọa độ trong map.
    private int left;
    private int top;
    private int right;
    private int bottom;

    private final int width;
    private final int height;
    private final int offsetX;
    private final int offsetY;

    //  CONSTRUCTOR
    public HitBox(Entity entity, int offsetX, int offsetY, int width, int height) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.entity = entity;
        this.height = height;
        this.width = width;
        update();
    }

    //  SETTER
    public void setLeft(int left) {
        this.left = left;
        right = this.left + width - 1;
    }

    public void setTop(int top) {
        this.top = top;
        bottom = this.top + height - 1;
    }

    //  GETTER
    public int getLeft() {
        return left;
    }

    public int getTop() {
        return top;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getRight() {
        return right;
    }

    public int getBottom() {
        return bottom;
    }

    public int getOffsetX() {
        return offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }

    public Vector2i getCenter() {
        int x = (left + right) / 2;
        int y = (top + bottom) / 2;
        return new Vector2i(x, y);
    }

    //  FUNCTIONS
    public void update() {
       setLeft(entity.getX() + offsetX);
       setTop(entity.getY() + offsetY);
    }

    /**
     * Lấy vị trí tiếp theo của HitBox khi Entity di chuyển.
     */
    public HitBox getNextPosition(Movement movement) {
        HitBox nextPosition = new HitBox(entity, offsetX, offsetY, width, height);
        nextPosition.setLeft(movement.getNextPosition().x + offsetX);
        nextPosition.setTop(movement.getNextPosition().y + offsetY);
        return nextPosition;
    }

    /**
     * HitBox này chứa một HitBox khác
     */
    public boolean contains(HitBox other) {
        if (this.left <= other.left && other.right <= this.right
            && this.top <= other.top && other.bottom <= this.bottom) {
            return true;
        }
        return false;
    }

    /**
     * Hitbox giao nhau.
     */
    public boolean intersects(HitBox other) {
        if (this.left <= other.right && this.right >= other.left
            && this.top <= other.bottom && this.bottom >= other.top) {
            return true;
        }
        return false;
    }

    /**
     * Render tại tọa độ x y.
     */
    public void render(int x, int y, GraphicsContext graphicsContext) {
        /*Paint old = graphicsContext.getFill();
        graphicsContext.setStroke(Color.BLACK);
        graphicsContext.strokeRect(x, y, width, height);
        graphicsContext.setStroke(old);*/
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillRect(x, y, width, height);
        /*
        if (entity instanceof Stone) {
            graphicsContext.setFill(Color.WHITE);
        }
        if (entity instanceof Brick) {
            graphicsContext.setFill(Color.DODGERBLUE);
        }
        if (entity instanceof Balloon) {
            graphicsContext.setFill(Color.RED);
        }
        if (entity instanceof Bomber) {
            graphicsContext.setFill(Color.DODGERBLUE);
        }
        graphicsContext.fillRect(x, y, width, height);
        */
    }

    /**
     * Check va chạm 2 đối tượng bất kỳ
     * @param entity1
     * @param entity2
     * @return
     */
    public static boolean checkHixbox(Entity entity1, Entity entity2) {
        HitBox hitBox1 = entity1.getHitBox();
        HitBox hitBox2 = entity2.getHitBox();
        int HIT = 0;
        int xLeft1 = hitBox1.left;
        int xRight1 = hitBox1.right;
        int yUp1 = hitBox1.top;
        int yDown1 = hitBox1.bottom;

        int xLeft2 = hitBox2.left;
        int xRight2 = hitBox2.right;
        int yUp2 = hitBox2.top;
        int yDown2 = hitBox2.bottom;

        if (xLeft2 >= (xLeft1 + HIT) && xLeft2 <= (xRight1 - HIT) && yUp2 >= (yUp1 + HIT) && yUp2 <= (yDown1 - HIT)) {
            return true;
        } else if (xRight2 >= (xLeft1 + HIT) && xRight2 <= (xRight1 - HIT) && yUp2 >= (yUp1 + HIT) && yUp2 <= (yDown1 - HIT)) {
            return true;
        } else if (xLeft2 >= (xLeft1 + HIT) && xLeft2 <= (xRight1 - HIT) && yDown2 >= (yUp1 + HIT) && yDown2 <= (yDown1 - HIT)) {
            return true;
        } else if (xRight2 >= (xLeft1 + HIT) && xRight2 <= (xRight1 - HIT) && yDown2 >= (yUp1 + HIT) && yDown2 <= (yDown1 - HIT)) {
            return true;
        }

        if (xLeft1 >= (xLeft2 + HIT) && xLeft1 <= (xRight2 - HIT) && yUp1 >= (yUp2 + HIT) && yUp1 <= (yDown2 - HIT)) {
            return true;
        } else if (xRight1 >= (xLeft2 + HIT) && xRight1 <= (xRight2 - HIT) && yUp1 >= (yUp2 + HIT) && yUp1 <= (yDown2 - HIT)) {
            return true;
        } else if (xLeft1 >= (xLeft2 + HIT) && xLeft1 <= (xRight2 - HIT) && yDown1 >= (yUp2 + HIT) && yDown1 <= (yDown2 - HIT)) {
            return true;
        } else if (xRight1 >= (xLeft2 + HIT) && xRight1 <= (xRight2 - HIT) && yDown1 >= (yUp2 + HIT) && yDown1 <= (yDown2 - HIT)) {
            return true;
        }

        if (xLeft1 == (xLeft2 + HIT) && xLeft1 == (xRight2 - HIT) && yUp1 == (yUp2 + HIT) && yUp1 == (yDown2 - HIT)) {
            return true;
        } else if (xRight1 == (xLeft2 + HIT) && xRight1 == (xRight2 - HIT) && yUp1 == (yUp2 + HIT) && yUp1 == (yDown2 - HIT)) {
            return true;
        } else if (xLeft1 == (xLeft2 + HIT) && xLeft1 == (xRight2 - HIT) && yDown1 == (yUp2 + HIT) && yDown1 == (yDown2 - HIT)) {
            return true;
        } else if (xRight1 == (xLeft2 + HIT) && xRight1 == (xRight2 - HIT) && yDown1 == (yUp2 + HIT) && yDown1 == (yDown2 - HIT)) {
            return true;
        }

        return false;
    }
}
