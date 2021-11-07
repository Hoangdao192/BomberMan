package Entities;

import Component.HitBox;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class Entity {
    protected String id;
    //  Tọa độ theo map.
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    //  Hình ảnh của đối tượng
    protected Image image;
    //  Vị trí của phần hình ảnh cần lấy
    protected Rectangle2D imageOffset;

    protected boolean collision = false;

    protected HitBox hitBox;

    protected boolean exist;

    //  CONSTRUCTOR
    public Entity(int x, int y, int width, int height, Image image) {
        id = "Entity";
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.image = image;
        //  Hit box mặc định
        hitBox = new HitBox(this, 0, 0,0,0);
        exist = true;
        if (image != null) {
            imageOffset = new Rectangle2D(0, 0, image.getWidth(), image.getHeight());
        } else {
            imageOffset = new Rectangle2D(0, 0, width, height);
        }
    }

    public Entity(int x, int y, int width, int height, Image image, Rectangle2D imageOffset) {
        this(x, y, width, height, image);
        this.imageOffset = imageOffset;
    }

    //  SETTER
    public void setId(String id) {
        this.id = id;
    }

    public void setCollision(boolean collision) {
        this.collision = collision;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setExist(boolean exist) {
        this.exist = exist;
    }

    //  GETTER
    public boolean isExist() {
        return exist;
    }

    public String getId() {
        return id;
    }

    public HitBox getHitBox() {
        return hitBox;
    }

    public boolean collisionAble() {
        return collision;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    //  FUNCTIONS
    public abstract void update();

    public void createHitBox(int offsetX, int offsetY, int width, int height) {
        hitBox = new HitBox(this, offsetX, offsetY, width, height);
    }

    /**
     * Đặt vị trí cho Hit box và đặt vị trí cho Entity theo Hit box.
     */
    public void setHitBoxPosition(int x, int y) {
        hitBox.setLeft(x);
        hitBox.setTop(y);
        this.x = x - hitBox.getOffsetX();
        this.y = y - hitBox.getOffsetY();
    }

    /**
     * Kiểm tra va chạm với Entity khác.
     */
    public boolean collision(Entity other) {
        if (hitBox.intersects(other.hitBox)) {
            return true;
        }
        return false;
    }

    /**
     * Hành động xảy ra khi va chạm với một Entity khác.
     */
    public boolean ifCollideDo(Entity other) {
        return false;
    }

    //  Render theo tọa độ map.
    public void render(GraphicsContext graphicsContext) {
        if (image != null) {
            graphicsContext.drawImage(
                    image,
                    imageOffset.getMinX(), imageOffset.getMinY(),
                    imageOffset.getWidth(), imageOffset.getHeight(),
                    x, y, width, height
            );
        }
    }

    /**
     * Render theo tọa độ cửa sổ
     */
    public void render(int x, int y, GraphicsContext graphicsContext) {
        if (image != null) {
            graphicsContext.drawImage(
                    image,
                    imageOffset.getMinX(), imageOffset.getMinY(),
                    imageOffset.getWidth(), imageOffset.getHeight(),
                    x, y, width, height
            );
        }
    }
}
