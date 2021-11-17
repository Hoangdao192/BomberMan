package Component;

import Entities.Entity;
import javafx.scene.canvas.GraphicsContext;

import java.util.HashMap;

public class AnimationManager {
    private HashMap<String, Animation> animationMap;
    private Animation currentAnimation = null;
    private Entity entity;

    //  CONSTRUCTOR
    public AnimationManager(Entity entity) {
        this.entity = entity;
        animationMap = new HashMap<String, Animation>();
    }

    public boolean addAnimation(String key, Animation animation) {
        if (animationMap.containsKey(key)) {
            return false;
        }
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

    public Animation getCurrentAnimation() {
        return currentAnimation;
    }

    /**
     * Play animation
     */
    public void play(String animationKey) {
        if (currentAnimation != animationMap.get(animationKey)) {
            currentAnimation = animationMap.get(animationKey);
            currentAnimation.reset();
        }
        currentAnimation.play();
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
        if (animationKey.contains(animationKey)) {
            animationMap.get(animationKey).reset();
        }
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
        currentAnimation.render(x, y, graphicsContext);
    }
}
