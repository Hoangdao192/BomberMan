package Map;

import Utils.Vector2i;

public class Camera {
    private Vector2i start;
    private Vector2i end;
    private Vector2i velocity;
    //  Tọa độ tối đa của end.x và end.y
    private int maxX;
    private int maxY;
    private int width;
    private int height;

    //  CONSTRUCTORS
    /**
     * Các tọa độ là tọa độ trong Map, không phải tọa độ Window.
     */
    public Camera(int startX, int startY, int width, int height, int maxX, int maxY) {
        this.maxX = maxX;
        this.maxY = maxY;
        this.width = width;
        this.height = height;
        start = new Vector2i(startX, startY);
        end = new Vector2i(startX + width, startY + height);
        velocity = new Vector2i(0, 0);
    }

    /**
     * Copy constructor.
     */
    public Camera(Camera source) {
        start = new Vector2i(source.start);
        end = new Vector2i(source.end);
        velocity = new Vector2i(source.velocity);
        maxX = source.maxX;
        maxY = source.maxY;
        width = source.width;
        height = source.height;
    }

    //  SETTERS
    public void setCenter(int x, int y) {
        start.x = x - width / 2;
        start.y = y - height / 2;
        end.x = start.x + width;
        end.y = start.y + height;
    }

    public void setPosition(int startX, int startY) {
        start.x = startX;
        start.y = startY;
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
        update();
    }

    //  GETTERS
    public Vector2i getEnd() {
        return end;
    }

    public Vector2i getStart() {
        return start;
    }

    public Vector2i getSize() {
        return new Vector2i(width, height);
    }

    //  FUNCTIONS
    /**
     * Cập nhập velocity (độ thay đổi của x và y)
     */
    public void move(Vector2i velocity) {
        //this.velocity = velocity;
        this.velocity.x = 0;
        this.velocity.y = 0;
    }

    public void update() {
        //start.x += velocity.x;
        //start.y += velocity.y;
        //  X
        if (start.x < 0) {
            start.x = 0;
        }
        else if (start.x + width > maxX) {
            //  Trường hợp chiều rộng bản đồ nhỏ hơn chiều rộng camera.
            if (maxX < width) {
                start.x = 0;
            } else {
                start.x = maxX - width;
            }
        }
        //  Y
        if (start.y < 0) {
            start.y = 0;
        }
        else if (start.y + height > maxY) {
            //  Trường hợp chiều cao bản đồ nhỏ hơn chiều cao camera.
            if (maxY < height) {
                start.y = 0;
            } else {
                start.y = maxY - height;
            }
        }

        end.x = start.x + width;
        end.y = start.y + height;
    }

    @Override
    public Camera clone() {
        return new Camera(this);
    }
}
