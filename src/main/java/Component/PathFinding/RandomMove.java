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

    public RandomMove(DynamicEntity entity, Map map) {
        this.map = map;
        this.entity = entity;
        this.movement = entity.getMovement();
        currentDirection = new Vector2i(1, 0);
    }

    private void changeMoveDirection() {
        int gridX = entity.getGridX();
        int gridY = entity.getGridY();
        int tempX = 1;
        int tempY = 0;
        ArrayList<ArrayList<ArrayList<Entity>>> staticEntityList = map.getStaticEntityList();
        //  Kiểm tra các hướng có thể di chuyển
        boolean canMoveRight = true;
        for (int i = 0; i < staticEntityList.get(gridY).get(gridX + 1).size(); ++i) {
            Entity currentEntity = staticEntityList.get(gridY).get(gridX + 1).get(i);
            if (entity.canCollideWithStaticEntity(currentEntity)) {
                canMoveRight = false;
                break;
            }
        }
        boolean canMoveLeft = true;
        for (int i = 0; i < staticEntityList.get(gridY).get(gridX - 1).size(); ++i) {
            Entity currentEntity = staticEntityList.get(gridY).get(gridX - 1).get(i);
            if (entity.canCollideWithStaticEntity(currentEntity)) {
                canMoveLeft = false;
                break;
            }
        }
        boolean canMoveUp = true;
        for (int i = 0; i < staticEntityList.get(gridY - 1).get(gridX).size(); ++i) {
            Entity currentEntity = staticEntityList.get(gridY - 1).get(gridX).get(i);
            if (entity.canCollideWithStaticEntity(currentEntity)) {
                canMoveUp = false;
                break;
            }
        }
        boolean canMoveDown = true;
        for (int i = 0; i < staticEntityList.get(gridY + 1).get(gridX).size(); ++i) {
            Entity currentEntity = staticEntityList.get(gridY + 1).get(gridX).get(i);
            if (entity.canCollideWithStaticEntity(currentEntity)) {
                canMoveDown = false;
                break;
            }
        }

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
    }

    protected void calculatePath() {
        if (entity.collisionWithMap()) {
            changeMoveDirection();
        } else {
            HitBox hitBox = entity.getHitBox();
            int gridX = entity.getGridX();
            int gridY = entity.getGridY();
            int gridSize = map.getGridSize();
            if (hitBox.getLeft() == gridX * gridSize && hitBox.getTop() == gridY * gridSize) {
                changeMoveDirection();
            }
        }
    }

    public void update() {
        entity.getMovement().update(currentDirection.x, currentDirection.y);
        calculatePath();
    }
}
