package Entities.PowerUp;

import Component.Sprite;
import Entities.Brick;
import Entities.StaticEntity;
import Entities.Entity;
import Map.Map;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;

public abstract class PowerUp extends StaticEntity {
    protected Brick brickBound = null;
    protected Map map;

    private MediaPlayer mediaPlayer;

    public PowerUp(int x, int y, int width, int height, int gridSize, Sprite sprite, Map map) {
        super(x, y, width, height, gridSize, sprite);
        this.map = map;
        createHitBox(0, 0, width, height);
        createSound();
    }

    private void createSound() {
        Media media = new Media(new File("src/main/resources/Sound/power_up.wav").toURI().toString());
        mediaPlayer = new MediaPlayer(media);
    }

    public void playSound() {
        mediaPlayer.seek(Duration.ZERO);
        mediaPlayer.play();
    }

    public void setBrickBound(Brick brickBound) {
        this.brickBound = brickBound;
    }

    protected boolean isBrickExist() {
        if (brickBound == null) {
            return true;
        }
        return brickBound.isExist();
    }

    public boolean hasBrick() {
        int x = gridX;
        int y = gridY;
        ArrayList<Entity> arrayList = map.getStaticEntityList().get(y).get(x);
        for (Entity entity : arrayList) {
            if (entity instanceof Brick) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void destroy() {
        super.destroy();
        playSound();
    }
}
