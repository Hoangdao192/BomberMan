package Component;

import Entities.Entity;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class Animation {
    private ArrayList<Sprite> spriteList;
    private int delayTime;
    private int delayCount;

    private int currentFrame;
    private int numberOfFrame;
    private final int frameWidth;
    private final int frameHeight;

    private Entity entity;

    private boolean playing = true;
    private boolean paused = false;

    public Animation(Entity entity, ArrayList<Sprite> spriteList, int width, int height, int delayTime) {
        this.entity = entity;
        this.spriteList = new ArrayList<>();
        if (spriteList != null) {
            this.spriteList.addAll(spriteList);
        }
        numberOfFrame = this.spriteList.size();
        this.frameWidth = width;
        this.frameHeight = height;
        this.currentFrame = 0;
        this.delayTime = delayTime;
        delayCount = 0;
    }

    public void addSprite(Sprite sprite) {
        this.spriteList.add(sprite);
        ++numberOfFrame;
    }

    public void update() {
        if (!playing) {
            return;
        }

        if (delayCount < delayTime) {
            ++delayCount;
        } else {
            delayCount = 0;
            ++currentFrame;
            if (currentFrame >= numberOfFrame) {
                currentFrame = 0;
            }
        }
    }

    public void play() {
        playing = true;
    }

    public void stop() {
        playing = false;
    }

    public void pause() {
        paused = true;
    }

    public void reset() {
        currentFrame = 0;
    }

    public int getCurrentFrame() {
        return currentFrame;
    }

    public int getNumberOfFrame() {
        return numberOfFrame;
    }

    public Sprite getSprite(int spriteIndex) {
        if (spriteIndex < spriteList.size() && spriteIndex >= 0) {
            return spriteList.get(spriteIndex);
        }
        return null;
    }

    public void render(int x, int y, GraphicsContext graphicsContext) {
        if (spriteList.size() > 0)
        spriteList.get(currentFrame).render(x, y, frameWidth, frameHeight, graphicsContext);
    }
}
