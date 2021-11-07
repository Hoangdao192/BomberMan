package Entities;

import Component.AnimationManager;
import javafx.geometry.Rectangle2D;
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

    public void createAnimation() {

    }
}
