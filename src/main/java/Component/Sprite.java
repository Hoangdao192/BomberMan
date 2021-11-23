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

    public static final Sprite SPRITE_TRANSPARENT = SPRITE_SHEET.getSprite(10, 10);

    /*
	|--------------------------------------------------------------------------
	| Bomber
	|--------------------------------------------------------------------------
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

    /*
	|--------------------------------------------------------------------------
	| Bomb
	|--------------------------------------------------------------------------
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

    /*
	|--------------------------------------------------------------------------
	| Enemies
	|--------------------------------------------------------------------------
	 */
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
     * Doll
     */
    //  Move left
    public static final Sprite DOLL_MOVE_LEFT_1 = SPRITE_SHEET.getSprite(13, 0);
    public static final Sprite DOLL_MOVE_LEFT_2 = SPRITE_SHEET.getSprite(13, 1);
    public static final Sprite DOLL_MOVE_LEFT_3 = SPRITE_SHEET.getSprite(13, 2);
    //  Move right
    public static final Sprite DOLL_MOVE_RIGHT_1 = SPRITE_SHEET.getSprite(14, 0);
    public static final Sprite DOLL_MOVE_RIGHT_2 = SPRITE_SHEET.getSprite(14, 1);
    public static final Sprite DOLL_MOVE_RIGHT_3 = SPRITE_SHEET.getSprite(14, 2);
    //  Die
    public static final Sprite DOLL_DIE = SPRITE_SHEET.getSprite(13, 3);
    /**
     * Minvo
     */
    //  Move left
    public static final Sprite MINVO_MOVE_LEFT_1 = SPRITE_SHEET.getSprite(8, 5);
    public static final Sprite MINVO_MOVE_LEFT_2 = SPRITE_SHEET.getSprite(8, 6);
    public static final Sprite MINVO_MOVE_LEFT_3 = SPRITE_SHEET.getSprite(8, 7);
    //  Move right
    public static final Sprite MINVO_MOVE_RIGHT_1 = SPRITE_SHEET.getSprite(9, 5);
    public static final Sprite MINVO_MOVE_RIGHT_2 = SPRITE_SHEET.getSprite(9, 6);
    public static final Sprite MINVO_MOVE_RIGHT_3 = SPRITE_SHEET.getSprite(9, 7);
    //  Die
    public static final Sprite MINVO_DIE = SPRITE_SHEET.getSprite(8, 8);
    /**
     * Ovapi
     */
    //  Move left
    public static final Sprite OVAPI_MOVE_LEFT_1 = SPRITE_SHEET.getSprite(6, 5);
    public static final Sprite OVAPI_MOVE_LEFT_2 = SPRITE_SHEET.getSprite(6, 6);
    public static final Sprite OVAPI_MOVE_LEFT_3 = SPRITE_SHEET.getSprite(6, 7);
    //  Move right
    public static final Sprite OVAPI_MOVE_RIGHT_1 = SPRITE_SHEET.getSprite(7, 5);
    public static final Sprite OVAPI_MOVE_RIGHT_2 = SPRITE_SHEET.getSprite(7, 6);
    public static final Sprite OVAPI_MOVE_RIGHT_3 = SPRITE_SHEET.getSprite(7, 7);
    //  Die
    public static final Sprite OVAPI_DIE = SPRITE_SHEET.getSprite(6, 8);
    /**
     *  Pontan
     */
    //  Move left
    public static final Sprite PONTAN_MOVE_LEFT_1 = SPRITE_SHEET.getSprite(12, 5);
    public static final Sprite PONTAN_MOVE_LEFT_2 = SPRITE_SHEET.getSprite(12, 6);
    public static final Sprite PONTAN_MOVE_LEFT_3 = SPRITE_SHEET.getSprite(12, 7);
    //  Move right
    public static final Sprite PONTAN_MOVE_RIGHT_1 = SPRITE_SHEET.getSprite(13, 5);
    public static final Sprite PONTAN_MOVE_RIGHT_2 = SPRITE_SHEET.getSprite(13, 6);
    public static final Sprite PONTAN_MOVE_RIGHT_3 = SPRITE_SHEET.getSprite(13, 7);
    //  Die
    public static final Sprite PONTAN_DIE = SPRITE_SHEET.getSprite(12, 8);
    /**
     * Kondoria
     */
    //  Move left
    public static final Sprite KONDORIA_MOVE_LEFT_1 = SPRITE_SHEET.getSprite(10, 5);
    public static final Sprite KONDORIA_MOVE_LEFT_2 = SPRITE_SHEET.getSprite(10, 6);
    public static final Sprite KONDORIA_MOVE_LEFT_3 = SPRITE_SHEET.getSprite(10, 7);
    //  Move right
    public static final Sprite KONDORIA_MOVE_RIGHT_1 = SPRITE_SHEET.getSprite(11, 5);
    public static final Sprite KONDORIA_MOVE_RIGHT_2 = SPRITE_SHEET.getSprite(11, 6);
    public static final Sprite KONDORIA_MOVE_RIGHT_3 = SPRITE_SHEET.getSprite(11, 7);
    //  Die
    public static final Sprite KONDORIA_DIE = SPRITE_SHEET.getSprite(10, 8);
    /**
     * Pass
     */
    //  Move left
    public static final Sprite PASS_MOVE_LEFT_1 = SPRITE_SHEET.getSprite(4, 5);
    public static final Sprite PASS_MOVE_LEFT_2 = SPRITE_SHEET.getSprite(4, 6);
    public static final Sprite PASS_MOVE_LEFT_3 = SPRITE_SHEET.getSprite(4, 7);
    //  Move right
    public static final Sprite PASS_MOVE_RIGHT_1 = SPRITE_SHEET.getSprite(5, 5);
    public static final Sprite PASS_MOVE_RIGHT_2 = SPRITE_SHEET.getSprite(5, 6);
    public static final Sprite PASS_MOVE_RIGHT_3 = SPRITE_SHEET.getSprite(5, 7);
    //  Die
    public static final Sprite PASS_DIE = SPRITE_SHEET.getSprite(4, 8);

    /*
	|--------------------------------------------------------------------------
	| Power up
	|--------------------------------------------------------------------------
	 */
    //  Fire - Tăng phạm vi nổ
    public static final Sprite FIRE = SPRITE_SHEET.getSprite(1, 10);
    //  Bomb up - Tăng số lượng bom
    public static final Sprite BOMB_UP = SPRITE_SHEET.getSprite(0, 10);
    // Speed up - tăng tốc độ
    public static final Sprite SPEED_UP = SPRITE_SHEET.getSprite(2, 10);
    // BombPass - Xuyên Bom
    public static final Sprite BOMB_PASS = SPRITE_SHEET.getSprite(5, 10);
    // FlamePass - Không bị bom nổ ảnh hưởng
    public static final Sprite FLAME_PASS = SPRITE_SHEET.getSprite(6, 10);
    // WallPass - Xuyên tường
    public static final Sprite WALL_PASS = SPRITE_SHEET.getSprite(3, 10);
    //Detonator - Kip nổ boom
    public static final Sprite DETONATOR = SPRITE_SHEET.getSprite(4, 10);
    //Mystery - Vô hiệu hóa mọi ảnh hưởng xấu trong 10s
    public static final Sprite MYSTERY = SPRITE_SHEET.getSprite(7, 10);

    /*
	|--------------------------------------------------------------------------
	| Bonus Iteam
	|--------------------------------------------------------------------------
	 */
    // Bonus Target - Tìm lối ra mà không giết một boss nào
    public static final Sprite BONUS_TARGET = SPRITE_SHEET.getSprite(0, 11);
    // Goddess Mask - giết hết boss đi 1 vòng sân khấu
    public static final Sprite GODDESS_MASK = SPRITE_SHEET.getSprite(1, 11);
    // Cola Bottle - trước khi giết tất cả kẻ thù, xuất hiện trước lối ra và đi quanh 1 vòng
    public static final Sprite COLA_BOTTLE = SPRITE_SHEET.getSprite(4, 11);
    // Famicom - sau khi tiêu diệt mọi kẻ thù nổ 248 trái bom bằng phản ứng liên hoàn
    public static final Sprite FAMICOM = SPRITE_SHEET.getSprite(3, 11);
    // Nakamoto-san - giết tất cả kẻ thù mà không phá hủy một bức tường nào
    public static final Sprite NAKAMOTO_SAN = SPRITE_SHEET.getSprite(2, 11);
    // Dezeniman-san - ko giết kẻ thù nào, hãy phá hủy mọi bức tường trên map và kích nổ bom trên lối ra 3 lần
    public static final Sprite DEZENIMAN_SAN = SPRITE_SHEET.getSprite(5, 11);

    /**
     * Portal
     */
    // Portal - cổng ra
    public static final Sprite PORTAL = SPRITE_SHEET.getSprite(4, 0);

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
