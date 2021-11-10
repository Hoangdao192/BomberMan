package Entities;

import Component.HitBox;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;

public class Stone extends StaticEntity{
    public Stone(int x, int y, int width, int height, int gridSize, Image image) {
        super(x, y, width, height, gridSize, image);
    }

    public Stone(int x, int y, int width, int height, int gridSize, Image image, Rectangle2D imageOffset) {
        super(x, y, width, height, gridSize, image, imageOffset);
    }

    @Override
    public void update() {

    }
}
