package Component;

import javafx.scene.image.Image;

public class SpriteSheet {
    private final Image image;
    private final int spriteWidth;
    private final int spriteHeight;
    private final double sheetWidth;
    private final double sheetHeight;

    //  Số hàng và số cột của sheet
    private final int numberOfSprite;
    private final int numberOfCollum;
    private final int numberOfRow;

    public SpriteSheet(Image image, int spriteWidth, int spriteHeight) {
        this.spriteHeight = spriteHeight;
        this.spriteWidth = spriteWidth;

        this.image = image;
        sheetWidth = image.getWidth();
        sheetHeight = image.getHeight();

        numberOfCollum = (int) sheetWidth / spriteWidth;
        numberOfRow = (int) sheetHeight / spriteHeight;
        numberOfSprite = numberOfCollum * numberOfRow;
    }

    public int getNumberOfSprite() {
        return numberOfSprite;
    }

    public int getSpriteHeight() {
        return spriteHeight;
    }

    public int getSpriteWidth() {
        return spriteWidth;
    }

    public Image getImage() {
        return image;
    }

    public Sprite getSprite(int col, int row) {
        return new Sprite(image, col * spriteWidth, row * spriteHeight, spriteWidth, spriteHeight);
    }
}
