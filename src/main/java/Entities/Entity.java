package Entities;

import Component.HitBox;
import Component.Sprite;
import Utils.Vector2i;
import javafx.scene.canvas.GraphicsContext;

public abstract class Entity {
    //  Tọa độ theo map.
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    //  Tọa độ theo Grid
    protected int gridX;
    protected int gridY;
    protected int gridSize;
    //  Hình ảnh của đối tượng
    protected Sprite sprite;

    protected boolean collision = false;

    protected HitBox hitBox;

    protected boolean exist = true;

    //  CONSTRUCTOR
    public Entity(int x, int y, int width, int height, int gridSize, Sprite sprite) {
        this.sprite = sprite;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        this.gridSize = gridSize;
        gridX = x / gridSize;
        gridY = y / gridSize;

        //  Hit box mặc định
        hitBox = new HitBox(this, 0, 0,0,0);
    }

    public void destroy() {
        collision = false;
        exist = false;
    }

    //  SETTER
    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
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

    public int getGridSize() {
        return gridSize;
    }

    public int getGridX() {
        return gridX;
    }

    public int getGridY() {
        return gridY;
    }

    public Vector2i getGridPosition() {
        return new Vector2i(gridX, gridY);
    }

    //  FUNCTIONS
    public void die() {
        exist = false;
    }

    public abstract void update();

    public void updateGridPosition() {
        if (hitBox.getWidth() == 0) {
            this.gridX = x / this.gridSize;
            this.gridY = y / this.gridSize;
        } else {
            this.gridX = (hitBox.getLeft() + hitBox.getWidth() / 2) / this.gridSize;
            this.gridY = (hitBox.getTop() + hitBox.getHeight() / 2) / this.gridSize;
        }
    }

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

    public void setHitBoxPositionX(int x) {
        hitBox.setLeft(x);
        this.x = x - hitBox.getOffsetX();
    }

    public void setHitBoxPositionY(int y) {
        hitBox.setTop(y);
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

    /**
     * Render theo tọa độ cửa sổ
     */
    public void render(int x, int y, GraphicsContext graphicsContext) {
        if (sprite != null) {
            sprite.render(x, y, this.width, this.height, graphicsContext);
        }
    }

    public void render(int x, int y, int width, int height, GraphicsContext graphicsContext) {
        if (sprite != null) {
            sprite.render(x, y, width, height, graphicsContext);
        }
    }
}
