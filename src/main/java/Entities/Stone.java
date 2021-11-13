package Entities;

import Component.Sprite;
import javafx.scene.canvas.GraphicsContext;

public class Stone extends StaticEntity{
    public Stone(int x, int y, int width, int height, int gridSize) {
        super(x, y, width, height, gridSize, null);
        sprite = Sprite.WALL;
    }

    @Override
    public void update() {

    }

    @Override
    public void render(int x, int y, GraphicsContext graphicsContext) {
        //  hitBox.render(x, y, graphicsContext);
        sprite.render(x, y, this.width, this.height, graphicsContext);
    }
}
