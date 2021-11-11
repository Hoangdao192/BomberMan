package Component;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;

public class SpriteSheet {
    private Image image;
    private int spriteWidth;
    private int spriteHeight;
    private double sheetWidth;
    private double sheetHeight;
    private int numberOfSprite;
    //  Số hàng và số cột của sheet.
    private int numberOfCollum;
    private int numberOfRow;

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

    /**
     * Sprite index bắt đầu từ 0.
     */
    public Rectangle2D getSprite(int spriteIndex) {
        int xPosition = (spriteIndex % numberOfCollum) * spriteWidth;
        int yPosition = (spriteIndex / numberOfCollum) * spriteHeight;
        return new Rectangle2D(xPosition, yPosition, spriteWidth, spriteHeight);
    }

    public Sprite getSpriteAtIndex(int spriteIndex) {
        int xPosition = (spriteIndex % numberOfCollum) * spriteWidth;
        int yPosition = (spriteIndex / numberOfCollum) * spriteHeight;
        return new Sprite(image, xPosition * spriteWidth, yPosition * spriteHeight, spriteWidth, spriteHeight);
    }

    public Sprite getSpriteAtPosition(int col, int row) {
        return new Sprite(image, col * spriteWidth, row * spriteHeight, spriteWidth, spriteHeight);
    }
}
