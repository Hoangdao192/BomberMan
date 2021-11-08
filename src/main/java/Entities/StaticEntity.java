package Entities;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class StaticEntity extends Entity {
    public StaticEntity(int x, int y, int width, int height, Image image) {
        super(x, y, width, height, image);
    }

    public StaticEntity(int x, int y, int width, int height, Image image, Rectangle2D imageOffset) {
        super(x, y, width, height, image, imageOffset);
    }
}
