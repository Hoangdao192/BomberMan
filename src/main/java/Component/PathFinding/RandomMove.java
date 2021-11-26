package Component.PathFinding;

import Component.HitBox;
import Component.Movement;
import Entities.DynamicEntity;
import Entities.Entity;
import Map.Map;
import Utils.RandomInt;
import Utils.Vector2i;

import java.util.ArrayList;

public class RandomMove {
    protected DynamicEntity entity;
    protected Movement movement;
    protected Vector2i currentDirection;
    protected Map map;
    ArrayList<ArrayList<ArrayList<Entity>>> staticEntityList;

    public RandomMove(DynamicEntity entity, Map map) {
        this.map = map;
        this.entity = entity;
        this.movement = entity.getMovement();
        currentDirection = new Vector2i(1, 0);
    }

    private boolean canMoveLeft() {
        int gridX = entity.getGridX();
        int gridY = entity.getGridY();
        for (int i = 0; i < staticEntityList.get(gridY).get(gridX - 1).size(); ++i) {
            Entity currentEntity = staticEntityList.get(gridY).get(gridX - 1).get(i);
            if (entity.canCollideWithStaticEntity(currentEntity)) {
                return false;
            }
        }
        Vector2i oldDirection = entity.getMovement().getDirection().clone();
        entity.getMovement().update(-1, 0);
        if (entity.collisionWithMap()) {
            entity.getMovement().update(oldDirection.x, oldDirection.y);
            return false;
        }
        entity.getMovement().update(oldDirection.x, oldDirection.y);

        return true;
    }

    private boolean canMoveRight() {
        int gridX = entity.getGridX();
        int gridY = entity.getGridY();
        //  Kiểm tra các hướng có thể di chuyển
        for (int i = 0; i < staticEntityList.get(gridY).get(gridX + 1).size(); ++i) {
            Entity currentEntity = staticEntityList.get(gridY).get(gridX + 1).get(i);
            if (entity.canCollideWithStaticEntity(currentEntity)) {
                return false;
            }
        }

        Vector2i oldDirection = entity.getMovement().getDirection().clone();
        entity.getMovement().update(1, 0);
        if (entity.collisionWithMap()) {
            entity.getMovement().update(oldDirection.x, oldDirection.y);
            return false;
        }
        entity.getMovement().update(oldDirection.x, oldDirection.y);
        return true;
    }

    private boolean canMoveUp() {
        int gridX = entity.getGridX();
        int gridY = entity.getGridY();
        for (int i = 0; i < staticEntityList.get(gridY - 1).get(gridX).size(); ++i) {
            Entity currentEntity = staticEntityList.get(gridY - 1).get(gridX).get(i);
            if (entity.canCollideWithStaticEntity(currentEntity)) {
                return false;
            }
        }

        Vector2i oldDirection = entity.getMovement().getDirection().clone();
        entity.getMovement().update(0, -1);
        if (entity.collisionWithMap()) {
            entity.getMovement().update(oldDirection.x, oldDirection.y);
            return false;
        }
        entity.getMovement().update(oldDirection.x, oldDirection.y);
        return true;
    }

    private boolean canMoveDown() {
        int gridX = entity.getGridX();
        int gridY = entity.getGridY();
        boolean canMoveDown = true;
        for (int i = 0; i < staticEntityList.get(gridY + 1).get(gridX).size(); ++i) {
            Entity currentEntity = staticEntityList.get(gridY + 1).get(gridX).get(i);
            if (entity.canCollideWithStaticEntity(currentEntity)) {
                return false;
            }
        }

        Vector2i oldDirection = entity.getMovement().getDirection().clone();
        entity.getMovement().update(0, 1);
        if (entity.collisionWithMap()) {
            entity.getMovement().update(oldDirection.x, oldDirection.y);
            return false;
        }
        entity.getMovement().update(oldDirection.x, oldDirection.y);
        return true;
    }

    private void changeMoveDirection() {
        staticEntityList = map.getStaticEntityList();
        int tempX = 1;
        int tempY = 0;
        //  Kiểm tra các hướng có thể di chuyển
        boolean canMoveRight = canMoveRight();
        boolean canMoveLeft = canMoveLeft();
        boolean canMoveUp = canMoveUp();
        boolean canMoveDown = canMoveDown();

        //  Chọn 1 hướng có thể di chuyển để đi theo hướng đó
        boolean canMove = false;
        if (canMoveLeft || canMoveRight || canMoveUp || canMoveDown) {
            while (!canMove) {
                int random = RandomInt.random(0, 4);
                switch (random) {
                    case 0:
                        canMove = canMoveLeft;
                        tempX = -1;
                        tempY = 0;
                        break;
                    case 1:
                        canMove = canMoveRight;
                        tempX = 1;
                        tempY = 0;
                        break;
                    case 2:
                        canMove = canMoveUp;
                        tempX = 0;
                        tempY = -1;
                        break;
                    case 3:
                        canMove = canMoveDown;
                        tempX = 0;
                        tempY = 1;
                        break;
                }
            }
        }
        currentDirection.x = tempX;
        currentDirection.y = tempY;
        entity.collisionWithMap();
    }

    protected void calculatePath() {
        if (entity.collisionWithMap()) {
            changeMoveDirection();
        } else {
            HitBox hitBox = entity.getHitBox();
            int gridX = entity.getGridX();
            int gridY = entity.getGridY();
            int gridSize = map.getGridSize();
            if (RandomInt.random(0, 5) == 0 && hitBox.getLeft() == gridX * gridSize && hitBox.getTop() == gridY * gridSize) {
                changeMoveDirection();
            }
        }
    }

    public void update() {
        entity.getMovement().update(currentDirection.x, currentDirection.y);
        calculatePath();
    }

    public void setCurrentDirection(int x, int y) {
        currentDirection.x = x;
        currentDirection.y = y;
    }
}
