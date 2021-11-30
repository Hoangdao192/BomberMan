package Component;

import Entities.Bomb;
import Entities.Bomber;

import java.io.File;
import java.util.ArrayList;

import Entities.Brick;
import Entities.Entity;
import Map.Map;
import Setting.Setting;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class BombManager {
    private Bomber bomber;
    private int maxBomb;
    private int numBomb;
    private int bombExplodeRadius;
    private Map map;
    private ArrayList<Bomb> bombList;
    private long WAIT_TO_EXPLODE_TIME = 2000000000L;
    private boolean detonatorEnable = false;

    private MediaPlayer mediaPlayer;

    public BombManager(Bomber bomber, Map map, int numBomb, int bombExplodeRadius) {
        bombList = new ArrayList<>();
        this.map = map;
        this.maxBomb = numBomb;
        this.numBomb = numBomb;
        this.bombExplodeRadius = bombExplodeRadius;
        this.bomber = bomber;
        createSound();
    }

    private void createSound() {
        Media setBombSound = new Media(new File("src/main/resources/Sound/set_bomb.mp3").toURI().toString());
        mediaPlayer = new MediaPlayer(setBombSound);
    }

    private void playSound() {
        if (Setting.isSoundOn()) {
            mediaPlayer.seek(Duration.ZERO);
            mediaPlayer.play();
        }
    }

    private boolean ifEntityIs(Entity entity) {
        if (entity instanceof Brick || entity instanceof Bomb) {
            return true;
        }
        return false;
    }

    public boolean createBomb() {
        if (bombList.size() >= maxBomb) {
            return false;
        }
        //  Lấy vị trí và chỉ số hàng cột của ô hiện tại bomber đang đứng
        int gridX = bomber.getHitBox().getCenter().x / map.getGridSize();
        int gridY = bomber.getHitBox().getCenter().y / map.getGridSize();
        int x = gridX * bomber.getGridSize();
        int y = gridY * bomber.getGridSize();
        //  Kiểm tra vị trí chuẩn bị đặt bom có vật cản không.
        ArrayList<ArrayList<ArrayList<Entity>>> staticEntityList = map.getStaticEntityList();
        for (int i = 0; i < staticEntityList.get(gridY).get(gridX).size(); ++i) {
            if (ifEntityIs(staticEntityList.get(gridY).get(gridX).get(i))) {
                return false;
            }
        }
        Bomb bomb = new Bomb(bombExplodeRadius, x, y, 32, 32, map);
        if (detonatorEnable) {
            bomb.setWaitTime(Long.MAX_VALUE);
        }
        bombList.add(bomb);
        map.addEntity(bomb);

        playSound();
        return true;
    }

    public void increaseNumberOfBomb() {
        ++numBomb;
        maxBomb = numBomb;
    }

    public void increaseBombRadius() {
        ++bombExplodeRadius;
    }

    /**
     *  Kích nổ quả bom được đặt lâu nhất.
     *  Hàm này chỉ chạy khi detonatorEnable = true;
     */
    public void explodeLatestBomb() {
        if (!detonatorEnable) {
            return;
        }
        if (bombList.size() > 0) {
            bombList.get(0).setWaitTime(0);
        }
    }

    public void update() {
        for (int i = 0; i < bombList.size();) {
            if (!bombList.get(i).isExist()) {
                bombList.remove(i);

                map.setHasBombExplosionBefore(true);
                map.setTimeBombExplosion(map.getTime().countMilliSecond());
                map.setNumBombExplosion(map.getNumBombExplosion() + 1);
            } else {
                ++i;
            }
        }

        if (map.isHasBombExplosionBefore()) {
            if (map.getTime().countMilliSecond() - map.getTimeBombExplosion() > 50) {
                map.setHasBombExplosionBefore(false);
                map.setNumBombExplosion(0);
            }
        }
        if(map.getNumBombExplosion() > 0) {
            System.out.println("BombExplosion" + map.getNumBombExplosion());
        }
    }

    //  Kích hoạt kíp điều khiển nổ
    public void enableDetonator() {
        detonatorEnable = true;
    }

    //  Vô hiệu kíp điều khiển nổ
    public void disableDetonator() {
        detonatorEnable = false;
    }

    public boolean isDetonatorEnable() {
        return detonatorEnable;
    }

    public int getMaxBomb() {
        return maxBomb;
    }

    public void setMaxBomb(int maxBomb) {
        this.maxBomb = maxBomb;
    }

    public int getNumBomb() {
        return numBomb;
    }

    public void setNumBomb(int numBomb) {
        this.numBomb = numBomb;
    }

    public ArrayList<Bomb> getBombList() {
        return bombList;
    }
}
