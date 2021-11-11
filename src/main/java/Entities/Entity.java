package Entities;

import Component.HitBox;
import Component.Sprite;
import Entities.Enemy.Balloon;
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
    //  Tọa độ theo Grid
    protected int gridX;
    protected int gridY;
    protected int gridSize;
    //  Hình ảnh của đối tượng
    protected Sprite sprite;

    protected boolean collision = false;

    protected HitBox hitBox;

    protected boolean exist;

    //  CONSTRUCTOR
    public Entity(int x, int y, int width, int height, int gridSize, Sprite sprite) {
        id = "Entity";
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
        exist = true;
    }

    //  SETTER
    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

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
    public void die() {
        exist = false;
    }

    public abstract void update();

    public void updateGridPosition() {
        if (hitBox.getWidth() == 0) {
            this.gridX = x / this.gridSize;
            this.gridY = y / this.gridSize;
        } else {
            this.gridX = hitBox.getLeft() / this.gridSize;
            this.gridY = hitBox.getTop() / this.gridSize;
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
        sprite.render(x, y, width, height, graphicsContext);
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
