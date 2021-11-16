package Component;

import Entities.Entity;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.Arrays;

public class Animation {
    //  Entity mà animation này thuộc về
    private Entity entity;
    //  Danh sách các Sprite
    private ArrayList<Sprite> spriteList;

    //  Thời gian chờ để animation chuyển sang sprite tiếp theo
    private int delayTime;
    //  Đếm thời gian chờ
    private int delayCount;

    private int currentFrame;
    private int numberOfFrame;
    //  Kích thước của Sprite khi render ra màn hình
    private final int frameWidth;
    private final int frameHeight;

    private boolean playing = true;
    private boolean paused = false;

    public Animation(Entity entity, int frameWidth, int frameHeight, int delayTime, Sprite... spriteList) {
        this.entity = entity;
        numberOfFrame = spriteList.length;
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.currentFrame = 0;
        this.delayTime = delayTime;
        this.delayCount = 0;

        this.spriteList = new ArrayList<>();
        this.spriteList.addAll(Arrays.asList(spriteList));
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

    public boolean isPlaying() {
        return playing;
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

    //  GETTERS
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
        if (spriteList.size() > 0) {
            spriteList.get(currentFrame).render(x, y, frameWidth, frameHeight, graphicsContext);
        }
    }
}
