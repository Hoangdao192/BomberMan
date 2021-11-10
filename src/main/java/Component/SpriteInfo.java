package Component;

import javafx.geometry.Rectangle2D;

public class SpriteInfo {
    public final int STAR_FRAME;
    public final int END_FRAME;
    public final int FRAME_WIDTH;
    public final int FRAME_HEIGHT;

    /*
	*--------------------------------------------------------------------------
	* Bomb Sprites
	*--------------------------------------------------------------------------
	*/
    public static final String BOMB_SPRITESHEET = "Graphic/Entity/bomb.png";
    //  Bomb
    public static final SpriteInfo BOMB_NORMAL = new SpriteInfo(21, 23, 16, 16);
    //  Bomb flame
    public static final SpriteInfo FLAME_CENTER = new SpriteInfo(0, 2, 16, 16);
    public static final SpriteInfo FLAME_HORIZON = new SpriteInfo(18, 20, 16, 16);
    public static final SpriteInfo FLAME_LEFT = new SpriteInfo(3, 5, 16, 16);
    public static final SpriteInfo FLAME_RIGHT = new SpriteInfo(6, 8, 16, 16);
    public static final SpriteInfo FLAME_VERTICAL = new SpriteInfo(9, 11, 16, 16);
    public static final SpriteInfo FLAME_UP = new SpriteInfo(12, 14, 16, 16);
    public static final SpriteInfo FLAME_DOWN = new SpriteInfo(15, 17, 16, 16);
    /*
     *--------------------------------------------------------------------------
     * Brick
     *--------------------------------------------------------------------------
    */
    public static final String BRICK_SPRITESHEET = "Graphic/Map/brick.png";
    //  Normal
    public static final Rectangle2D BRICK_NORMAL = new Rectangle2D(0, 0, 16, 16);
    //  Explode
    public static final SpriteInfo BRICK_EXPLODE = new SpriteInfo(1, 3, 16, 16);


    public SpriteInfo(int startFrame, int endFrame, int frameWidth, int frameHeight) {
        STAR_FRAME = startFrame;
        END_FRAME = endFrame;
        FRAME_WIDTH = frameWidth;
        FRAME_HEIGHT = frameHeight;
    }

}
