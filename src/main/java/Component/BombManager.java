package Component;

import Entities.Bomb;
import Entities.Bomber;

import java.util.ArrayList;
import Map.Map;

public class BombManager {
    private Bomber bomber;
    private int maxBomb;
    private int bombExplodeRadius;
    private Map map;
    private ArrayList<Bomb> bombList;
    private long WAIT_TO_EXPLODE_TIME = 2000000000L;
    private boolean detonatorEnable = false;

    public BombManager(Bomber bomber, Map map, int maxBomb, int bombExplodeRadius) {
        bombList = new ArrayList<>();
        this.map = map;
        this.maxBomb = maxBomb;
        this.bombExplodeRadius = bombExplodeRadius;
        this.bomber = bomber;
    }

    public void createBomb() {
        if (bombList.size() >= maxBomb) {
            return;
        }
        Bomb bomb = new Bomb(
                bombExplodeRadius,
                //  Lấy chỉ số hàng cột của ô hiện tại đang đứng
                bomber.getHitBox().getCenter().x / map.getGridSize() * bomber.getGridSize()
                        + (bomber.getHitBox().getLeft() % map.getGridSize() != 0 ? 1 : 0),
                bomber.getHitBox().getCenter().y / map.getGridSize() * bomber.getGridSize()
                        + (bomber.getHitBox().getTop() % map.getGridSize() != 0 ? 1 : 0),
                32, 32, map);
        if (detonatorEnable) {
            bomb.setWaitTime(Long.MAX_VALUE);
        }
        bombList.add(bomb);
        map.addEntity(bomb);
    }

    public void increaseNumberOfBomb() {
        ++maxBomb;
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
            } else {
                ++i;
            }
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
}
