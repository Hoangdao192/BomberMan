package Entities;

import Component.Animation;
import Component.AnimationManager;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Bomb extends StaticEntity {
    private static final String IMAGE_PATH = "Graphic/Entity/bomb.png";
    private AnimationManager animationManager;

    public Bomb(int x, int y, int width, int height, Image image) {
        super(x, y, width, height, image);
        createAnimation();
    }

    public Bomb(int x, int y, int width, int height, Image image, Rectangle2D imageOffset) {
        super(x, y, width, height, image, imageOffset);
        createAnimation();
    }

    @Override
    public void update() {

    }

    public void createAnimation() {
        Image image = new Image(IMAGE_PATH);
        Animation mainAnimation = new Animation(this, image, 30, 30, 2, 0, 2);
        animationManager = new AnimationManager(this);
        animationManager.addAnimation("MAIN", mainAnimation);
        animationManager.play("MAIN");
    }

    @Override
    public void render(int x, int y, GraphicsContext graphicsContext) {
        animationManager.render(graphicsContext, x, y);
    }
}
