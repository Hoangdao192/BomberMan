package Component;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Sprite {

    /**
     * Sprite sheet
     */
    public static final String SPRITE_SHEET_PATH = "Graphic/sprite_sheet.png";
    public static final Image SPRITE_SHEET_IMAGE = new Image(SPRITE_SHEET_PATH);
    public static final int SPRITE_SIZE = 16;
    public static final SpriteSheet SPRITE_SHEET = new SpriteSheet(SPRITE_SHEET_IMAGE, SPRITE_SIZE, SPRITE_SIZE);

    /**
     * Bomber
     */
    //  Walk left
    public static final Sprite BOMBER_WALK_LEFT_1 = SPRITE_SHEET.getSprite(3, 0);
    public static final Sprite BOMBER_WALK_LEFT_2 = SPRITE_SHEET.getSprite(3, 1);
    public static final Sprite BOMBER_WALK_LEFT_3 = SPRITE_SHEET.getSprite(3, 2);
    //  Walk right
    public static final Sprite BOMBER_WALK_RIGHT_1 = SPRITE_SHEET.getSprite(1, 0);
    public static final Sprite BOMBER_WALK_RIGHT_2 = SPRITE_SHEET.getSprite(1, 1);
    public static final Sprite BOMBER_WALK_RIGHT_3 = SPRITE_SHEET.getSprite(1, 2);
    //  Walk up
    public static final Sprite BOMBER_WALK_UP_1 = SPRITE_SHEET.getSprite(0, 0);
    public static final Sprite BOMBER_WALK_UP_2 = SPRITE_SHEET.getSprite(0, 1);
    public static final Sprite BOMBER_WALK_UP_3 = SPRITE_SHEET.getSprite(0, 2);
    //  Walk down
    public static final Sprite BOMBER_WALK_DOWN_1 = SPRITE_SHEET.getSprite(2, 0);
    public static final Sprite BOMBER_WALK_DOWN_2 = SPRITE_SHEET.getSprite(2, 1);
    public static final Sprite BOMBER_WALK_DOWN_3 = SPRITE_SHEET.getSprite(2, 2);
    //  Die
    public static final Sprite BOMBER_DIE_1 = SPRITE_SHEET.getSprite(4, 2);
    public static final Sprite BOMBER_DIE_2 = SPRITE_SHEET.getSprite(5, 2);
    public static final Sprite BOMBER_DIE_3 = SPRITE_SHEET.getSprite(6, 2);

    /**
     *  Bomb
     */
    //  Bomb normal
    public static final Sprite BOMB_NORMAL_1 = SPRITE_SHEET.getSprite(0, 3);
    public static final Sprite BOMB_NORMAL_2 = SPRITE_SHEET.getSprite(1, 3);
    public static final Sprite BOMB_NORMAL_3 = SPRITE_SHEET.getSprite(2, 3);
    //  Bomb flame center
    public static final Sprite BOMB_FLAME_CENTER_1 = SPRITE_SHEET.getSprite(0, 4);
    public static final Sprite BOMB_FLAME_CENTER_2 = SPRITE_SHEET.getSprite(0, 5);
    public static final Sprite BOMB_FLAME_CENTER_3 = SPRITE_SHEET.getSprite(0, 6);
    //  Bomb flame horizon
    public static final Sprite BOMB_FLAME_HORIZON_1 = SPRITE_SHEET.getSprite(1, 7);
    public static final Sprite BOMB_FLAME_HORIZON_2 = SPRITE_SHEET.getSprite(1, 8);
    public static final Sprite BOMB_FLAME_HORIZON_3 = SPRITE_SHEET.getSprite(1, 9);
    //  Bomb flame left
    public static final Sprite BOMB_FlAME_LEFT_1 = SPRITE_SHEET.getSprite(0, 7);
    public static final Sprite BOMB_FlAME_LEFT_2 = SPRITE_SHEET.getSprite(0, 8);
    public static final Sprite BOMB_FlAME_LEFT_3 = SPRITE_SHEET.getSprite(0, 9);
    //  Bomb flame right
    public static final Sprite BOMB_FLAME_RIGHT_1 = SPRITE_SHEET.getSprite(2, 7);
    public static final Sprite BOMB_FLAME_RIGHT_2 = SPRITE_SHEET.getSprite(2, 8);
    public static final Sprite BOMB_FLAME_RIGHT_3 = SPRITE_SHEET.getSprite(2, 9);
    //  Bomb flame vertical
    public static final Sprite BOMB_FLAME_VERTICAL_1 = SPRITE_SHEET.getSprite(1, 5);
    public static final Sprite BOMB_FLAME_VERTICAL_2 = SPRITE_SHEET.getSprite(2, 5);
    public static final Sprite BOMB_FLAME_VERTICAL_3 = SPRITE_SHEET.getSprite(3, 5);
    //  Bomb flame top
    public static final Sprite BOMB_FLAME_TOP_1 = SPRITE_SHEET.getSprite(1, 4);
    public static final Sprite BOMB_FLAME_TOP_2 = SPRITE_SHEET.getSprite(2, 4);
    public static final Sprite BOMB_FLAME_TOP_3 = SPRITE_SHEET.getSprite(3, 4);
    //  Bomb flame bottom
    public static final Sprite BOMB_FLAME_BOTTOM_1 = SPRITE_SHEET.getSprite(1, 6);
    public static final Sprite BOMB_FLAME_BOTTOM_2 = SPRITE_SHEET.getSprite(2, 6);
    public static final Sprite BOMB_FLAME_BOTTOM_3 = SPRITE_SHEET.getSprite(3, 6);

    /**
     * Wall
     */
    public static final Sprite WALL = SPRITE_SHEET.getSprite(5, 0);

    /**
     * Brick
     */
    //  Normal
    public static final Sprite BRICK_NORMAL = SPRITE_SHEET.getSprite(7, 0);
    //  Explode
    public static final Sprite BRICK_EXPLODE_1 = SPRITE_SHEET.getSprite(7, 1);
    public static final Sprite BRICK_EXPLODE_2 = SPRITE_SHEET.getSprite(7, 2);
    public static final Sprite BRICK_EXPLODE_3 = SPRITE_SHEET.getSprite(7, 3);

    /**
     * Enemy die
     */
    public static final Sprite ENEMY_DIE_1 = SPRITE_SHEET.getSprite(15, 0);
    public static final Sprite ENEMY_DIE_2 = SPRITE_SHEET.getSprite(15, 1);
    public static final Sprite ENEMY_DIE_3 = SPRITE_SHEET.getSprite(15, 2);

    /**
     * Balloon
     */
    //  Move left
    public static final Sprite BALLOON_MOVE_LEFT_1 = SPRITE_SHEET.getSprite(9, 0);
    public static final Sprite BALLOON_MOVE_LEFT_2 = SPRITE_SHEET.getSprite(9, 1);
    public static final Sprite BALLOON_MOVE_LEFT_3 = SPRITE_SHEET.getSprite(9, 2);
    //  Move right
    public static final Sprite BALLOON_MOVE_RIGHT_1 = SPRITE_SHEET.getSprite(10, 0);
    public static final Sprite BALLOON_MOVE_RIGHT_2 = SPRITE_SHEET.getSprite(10, 1);
    public static final Sprite BALLOON_MOVE_RIGHT_3 = SPRITE_SHEET.getSprite(10, 2);
    //  Die
    public static final Sprite BALLOON_DIE = SPRITE_SHEET.getSprite(9, 3);
    /**
     * Oneal
     */
    //  Move left
    public static final Sprite ONEAL_MOVE_LEFT_1 = SPRITE_SHEET.getSprite(11, 0);
    public static final Sprite ONEAL_MOVE_LEFT_2 = SPRITE_SHEET.getSprite(11, 1);
    public static final Sprite ONEAL_MOVE_LEFT_3 = SPRITE_SHEET.getSprite(11, 2);
    //  Move right
    public static final Sprite ONEAL_MOVE_RIGHT_1 = SPRITE_SHEET.getSprite(12, 0);
    public static final Sprite ONEAL_MOVE_RIGHT_2 = SPRITE_SHEET.getSprite(12, 1);
    public static final Sprite ONEAL_MOVE_RIGHT_3 = SPRITE_SHEET.getSprite(12, 2);
    //  Die
    public static final Sprite ONEAL_DIE = SPRITE_SHEET.getSprite(11, 3);

    /**
     * Power up
     */
    //  Fire - Tăng phạm vi nổ
    public static final Sprite FIRE = SPRITE_SHEET.getSprite(1, 10);
    //  Bomb up - Tăng số lượng bom
    public static final Sprite BOMB_UP = SPRITE_SHEET.getSprite(0, 10);

    private Image image;
    private int x;
    private int y;
    private int width;
    private int height;

    public Sprite(Image image, int x, int y, int width, int height) {
        this.image = image;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Image getImage() {
        return image;
    }

    /**
     *
     * @param x: destination x
     * @param y: destination y
     * @param width: destination width
     * @param height: destination height
     * @param graphicsContext
     */
    public void render(int x, int y, int width, int height, GraphicsContext graphicsContext) {
        graphicsContext.drawImage(
                //  Origin
                this.image, this.x, this.y, this.width, this.height,
                //  Destination
                x, y, width, height
        );
    }
}
