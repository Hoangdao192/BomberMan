package Component;

import Entities.Entity;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.HashMap;

public class AnimationManager {
    private HashMap<String, Animation> animationMap;
    private Animation currentAnimation;
    private Entity entity;

    public AnimationManager(Entity entity) {
        animationMap = new HashMap<String, Animation>();
    }

    public boolean addAnimation(String key, Animation animation) {
        if (animationMap.containsKey(key)) {
            return false;
        }
        animationMap.put(key, animation);
        return true;
    }

    public boolean addAnimation(String key, Image image, int width, int height, int maxSpeed, int startFrame, int endFrame) {
        if (animationMap.containsKey(key)) {
            return false;
        }
        Animation animation = new Animation(entity, image, width, height, maxSpeed, startFrame, endFrame);
        animationMap.put(key, animation);
        return true;
    }

    public boolean addAnimation(String key, SpriteSheet spriteSheet, int width, int height, int maxSpeed, int startFrame, int endFrame) {
        if (animationMap.containsKey(key)) {
            return false;
        }
        Animation animation = new Animation(entity, spriteSheet, maxSpeed, startFrame, endFrame);
        animationMap.put(key, animation);
        return true;
    }

    public String getCurrentAnimationKey() {
        for (String key : animationMap.keySet()) {
            if (currentAnimation == animationMap.get(key)) {
                return key;
            }
        }
        return null;
    }

    /**
     * Set animation hiện tại.
     */
    public void play(String animationKey) {
        if (currentAnimation != animationMap.get(animationKey)) {
            currentAnimation = animationMap.get(animationKey);
            currentAnimation.reset();
        }
    }

    /**
     * Update animation hiện tại
     */
    public void update() {
        currentAnimation.update();
    }

    /**
     * Reset animation hiện tại.
     */
    public void reset() {
        currentAnimation.reset();
    }

    /**
     * Reset một animation cụ thể.
     */
    public void reset(String animationKey) {
        animationMap.get(animationKey).reset();
    }

    public Animation get(String animationKey) {
        if (animationMap.containsKey(animationKey)) {
            return animationMap.get(animationKey);
        }
        return null;
    }

    /**
     * Render animation hiện tại.
     */
    public void render(GraphicsContext graphicsContext, int x, int y) {
        currentAnimation.render(graphicsContext, x, y);
    }
}
