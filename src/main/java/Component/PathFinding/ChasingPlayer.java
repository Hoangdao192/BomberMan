package Component.PathFinding;

import Entities.*;
import Map.Map;

import java.util.ArrayList;

public class ChasingPlayer extends RandomMove{
    private Bomber bomber;
    //  Phạm vi phát hiện Player (tính theo số tile)
    private int chasingRadius;

    //  Không nhìn thấy player
    public final int DETECT_FAILED = 0;
    public final int PLAYER_ON_LEFT = 1;
    public final int PLAYER_ON_RIGHT = 2;
    public final int PLAYER_ON_TOP = 3;
    public final int PLAYER_ON_BOTTOM = 4;

    public ChasingPlayer(DynamicEntity entity, Map map, int chasingRadius) {
        super(entity, map);
        this.chasingRadius = chasingRadius;
        bomber = map.getPlayer();
    }

    //  Kiểm tra các Entity có thể cản trở di chuyển
    private boolean ifEntityIs(Entity entity) {
        if (entity instanceof Brick || entity instanceof Stone || entity instanceof Bomb) {
            return true;
        }
        return false;
    }

    private int detectPlayer() {
        if (bomber == null) {
            bomber = map.getPlayer();
        }
        int gridX = entity.getGridX();
        int gridY = entity.getGridY();

        ArrayList<ArrayList<ArrayList<Entity>>> staticEntityList = map.getStaticEntityList();
        if (bomber.getGridY() == gridY && bomber.getGridX() - gridX <= 5
                && bomber.getGridX() - gridX > 0) {
            for (int i = 0; i < staticEntityList.get(gridY).get(gridX + 1).size(); ++i) {
                Entity entity = staticEntityList.get(gridY).get(gridX + 1).get(i);
                if (ifEntityIs(entity)) {
                    return DETECT_FAILED;
                }
            }
            return PLAYER_ON_RIGHT;
        }
        if (bomber.getGridY() == gridY && gridX - bomber.getGridX() <= 5
                && gridX - bomber.getGridX() > 0) {
            for (int i = 0; i < staticEntityList.get(gridY).get(gridX - 1).size(); ++i) {
                Entity entity = staticEntityList.get(gridY).get(gridX - 1).get(i);
                if (ifEntityIs(entity)) {
                    return DETECT_FAILED;
                }
            }
            return PLAYER_ON_LEFT;
        }
        if (bomber.getGridX() == gridX && gridY - bomber.getGridY() <= 5
                && gridY - bomber.getGridY() > 0) {
            for (int i = 0; i < staticEntityList.get(gridY - 1).get(gridX).size(); ++i) {
                Entity entity = staticEntityList.get(gridY - 1).get(gridX).get(i);
                if (ifEntityIs(entity)) {
                    return DETECT_FAILED;
                }
            }
            return PLAYER_ON_TOP;
        }
        if (bomber.getGridX() == gridX && bomber.getGridY() - gridY <= 5
                && bomber.getGridY() - gridY > 0) {
            for (int i = 0; i < staticEntityList.get(gridY + 1).get(gridX).size(); ++i) {
                Entity entity = staticEntityList.get(gridY + 1).get(gridX).get(i);
                if (ifEntityIs(entity)) {
                    return DETECT_FAILED;
                }
            }
            return PLAYER_ON_BOTTOM;
        }
        return DETECT_FAILED;
    }

    //  Đuổi theo Player về bên phải nếu không có vật cản
    private boolean canMoveRight() {
        ArrayList<ArrayList<ArrayList<Entity>>> staticEntityList = map.getStaticEntityList();
        for (int i = entity.getGridX() + 1; i < bomber.getGridX(); ++i) {
            for (int j = 0; j < staticEntityList.get(entity.getGridY()).get(i).size(); ++j) {
                Entity currentEntity = staticEntityList.get(entity.getGridY()).get(i).get(j);
                if (ifEntityIs(currentEntity)) {
                    return false;
                }
            }
        }
        return true;
    }

    //  Đuổi theo Player về bên trái nếu không có vật cản
    private boolean canMoveLeft() {
        ArrayList<ArrayList<ArrayList<Entity>>> staticEntityList = map.getStaticEntityList();
        for (int i = entity.getGridX() - 1; i > bomber.getGridX(); --i) {
            for (int j = 0; j < staticEntityList.get(entity.getGridY()).get(i).size(); ++j) {
                Entity currentEntity = staticEntityList.get(entity.getGridY()).get(i).get(j);
                if (ifEntityIs(currentEntity)) {
                    return false;
                }
            }
        }
        return true;
    }

    //  Đuổi theo Player về bên trên nếu không có vật cản
    private boolean canMoveUp() {
        ArrayList<ArrayList<ArrayList<Entity>>> staticEntityList = map.getStaticEntityList();
        for (int i = entity.getGridY() - 1; i > bomber.getGridY(); --i) {
            for (int j = 0; j < staticEntityList.get(i).get(entity.getGridX()).size(); ++j) {
                Entity currentEntity = staticEntityList.get(i).get(entity.getGridX()).get(j);
                if (ifEntityIs(currentEntity)) {
                    return false;
                }
            }
        }
        return true;
    }

    //  Đuổi theo Player về bên dưới nếu không có vật cản
    private boolean canMoveDown() {
        ArrayList<ArrayList<ArrayList<Entity>>> staticEntityList = map.getStaticEntityList();
        for (int i = entity.getGridY() + 1; i < bomber.getGridY(); ++i) {
            for (int j = 0; j < staticEntityList.get(i).get(entity.getGridX()).size(); ++j) {
                Entity currentEntity = staticEntityList.get(i).get(entity.getGridX()).get(j);
                if (ifEntityIs(currentEntity)) {
                    return false;
                }
            }
        }
        return true;
    }

    protected void calculatePath() {
        int playerPosition = detectPlayer();
        int desGridX = bomber.getGridX();
        int desGridY = bomber.getGridY();
        ArrayList<ArrayList<ArrayList<Entity>>> staticEntityList = map.getStaticEntityList();
        if (playerPosition == DETECT_FAILED) {
            super.calculatePath();
        }
        else if (playerPosition == PLAYER_ON_RIGHT) {
            if (canMoveRight()) {
                entity.getMovement().update(1, 0);
            }
        }
        else if (playerPosition == PLAYER_ON_LEFT) {
            if (canMoveLeft()) {
                entity.getMovement().update(-1, 0);
            }
        }
        else if (playerPosition == PLAYER_ON_TOP) {
            if (canMoveUp()) {
                entity.getMovement().update(0, -1);
            }
        }
        else if (playerPosition == PLAYER_ON_BOTTOM) {
            if (canMoveDown()) {
                entity.getMovement().update(0, 1);
            }
        }
        entity.collisionWithMap();
    }
    /*public void update() {

    }*/
}
