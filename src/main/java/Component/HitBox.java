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
        Paint old = graphicsContext.getFill();
        graphicsContext.setStroke(Color.BLACK);
        graphicsContext.strokeRect(x, y, width, height);
        graphicsContext.setStroke(old);
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
}
