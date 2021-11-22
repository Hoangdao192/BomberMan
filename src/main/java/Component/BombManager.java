package Component;

import Entities.Bomb;
import Entities.Bomber;

import java.util.ArrayList;

import Entities.Brick;
import Entities.Entity;
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

    private boolean ifEntityIs(Entity entity) {
        if (entity instanceof Brick || entity instanceof Bomb) {
            return true;
        }
        return false;
    }

    public void createBomb() {
        if (bombList.size() >= maxBomb) {
            return;
        }
        //  Lấy vị trí và chỉ số hàng cột của ô hiện tại bomber đang đứng
        int gridX = bomber.getHitBox().getCenter().x / map.getGridSize();
        int gridY = bomber.getHitBox().getCenter().y / map.getGridSize();
        int x = gridX * bomber.getGridSize();
        int y = gridY * bomber.getGridSize();
        ArrayList<ArrayList<ArrayList<Entity>>> staticEntityList = map.getStaticEntityList();
        for (int i = 0; i < staticEntityList.get(gridY).get(gridX).size(); ++i) {
            if (ifEntityIs(staticEntityList.get(gridY).get(gridX).get(i))) {
                return;
            }
        }
        Bomb bomb = new Bomb(bombExplodeRadius, x, y, 32, 32, map);
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
