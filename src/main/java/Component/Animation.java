package Component;

import Entities.Entity;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Animation {
    private SpriteSheet spriteSheet;
    private int speed;
    private final int MAX_SPEED;

    private int startFrame;
    private int endFrame;
    private int frame;

    private boolean isPlay;

    private Entity entity;

    private Rectangle2D framePosition;
    private Image image;

    public Animation(Entity entity, Image image, int width, int height, int maxSpeed, int startFrame, int endFrame) {
        this.entity = entity;
        this.startFrame = startFrame;
        this.endFrame = endFrame;
        this.image = image;
        spriteSheet = new SpriteSheet(image, width, height);
        MAX_SPEED = maxSpeed;
        speed = 0;
        frame = startFrame;
        framePosition = new Rectangle2D(0, 0, 0, 0);
        isPlay = true;
    }

    public Animation(Entity entity, SpriteSheet spriteSheet, int maxSpeed, int startFrame, int endFrame) {
        this.entity = entity;
        this.spriteSheet = spriteSheet;
        MAX_SPEED = maxSpeed;
        this.startFrame = startFrame;
        this.endFrame = endFrame;
        this.image = spriteSheet.getImage();
        framePosition = new Rectangle2D(0, 0, 0, 0);
        speed = 0;
        frame = startFrame;
        isPlay = true;
    }

    public void play() {
        isPlay = true;
    }

    public void stop() {
        isPlay = false;
    }

    public void update() {
        if (!isPlay) {
            return;
        }

        if (speed < MAX_SPEED) {
            ++speed;
        } else {
            speed = 0;
            framePosition = spriteSheet.getSprite(frame);
            ++frame;
            if (frame > endFrame) {
                frame = startFrame;
            }
        }
    }

    public void reset() {
        frame = startFrame;
    }

    public int getFrame() {
        return frame;
    }

    public int getEndFrame() {
        return endFrame;
    }

    public SpriteSheet getSpriteSheet() {
        return spriteSheet;
    }

    public void render(GraphicsContext graphicsContext, int x, int y) {
        graphicsContext.drawImage(
                image,
                framePosition.getMinX(), framePosition.getMinY(),
                spriteSheet.getSpriteWidth(), spriteSheet.getSpriteHeight(),
                x, y, entity.getWidth(), entity.getHeight()
        );
    }
}
