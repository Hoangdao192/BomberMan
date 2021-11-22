package Component.PathFinding;

import Component.HitBox;
import Entities.Bomber;
import Entities.DynamicEntity;
import Entities.Entity;
import Map.Map;
import Utils.Vector2i;

import java.util.ArrayList;

public class ChasingPlayerLevel2 extends RandomMove {
    private AStarPathFinding pathFinding = null;
    private Bomber bomber;
    private ArrayList<Node> path;
    private Vector2i bomberOldGridPosition;
    private Node destinationNode;
    private boolean passDestinationNode = false;

    private boolean followingPath = true;

    public ChasingPlayerLevel2(DynamicEntity entity, Map map) {
        super(entity, map);
        bomberOldGridPosition = new Vector2i(0, 0);
    }

    /**
     * Chuyển đổi bản đồ thành ma trân thể diện sự va chạm.
     * ô 0: Entity có thể đi
     * ô 1: Entity không thể đi
     */
    private ArrayList<ArrayList<Integer>> parseGameMapToCollideMatrix() {
        ArrayList<ArrayList<ArrayList<Entity>>> staticEntityList = map.getStaticEntityList();
        ArrayList<ArrayList<Integer>> collideMatrix = new ArrayList<>();
        int matrixWidth = map.getMapGridWidth();
        int matrixHeight = map.getMapGridHeight();
        for (int row = 0; row < matrixHeight; ++row) {
            collideMatrix.add(new ArrayList<>());
            for (int col = 0; col < matrixWidth; ++col) {
                int collide = 0;
                for (int i = 0; i < staticEntityList.get(row).get(col).size(); ++i) {
                    Entity currentEntity = staticEntityList.get(row).get(col).get(i);
                    if (entity.canCollideWithStaticEntity(currentEntity)) {
                        collide = 1;
                        break;
                    }
                }
                collideMatrix.get(row).add(collide);
            }
        }
        return collideMatrix;
    }

    private void generatePath() {
        if (bomber == null) {
            bomber = map.getPlayer();
        }
        if (pathFinding == null) {
            ArrayList<ArrayList<Integer>> collideMatrix = parseGameMapToCollideMatrix();
            pathFinding = new AStarPathFinding(collideMatrix, map.getMapGridWidth(), map.getMapGridHeight());
        }
        ArrayList<ArrayList<Integer>> collideMatrix = parseGameMapToCollideMatrix();
        pathFinding.setMap(collideMatrix);
        Node endNode = new Node(bomber.getGridX(), bomber.getGridY());
        Node startNode = new Node(entity.getGridX(), entity.getGridY());
        path = pathFinding.findPath(startNode, endNode);
        bomberOldGridPosition.x = bomber.getGridX();
        bomberOldGridPosition.y = bomber.getGridY();
    }

    private void pathFollow() {
        for (int i = 0; i < path.size();) {
            if (entity.getGridPosition().equals(path.get(i).getPosition())) {
                path.remove(i);
            } else {
                Node currentNode = path.get(i);
                if (currentNode.getY() == entity.getGridY()) {
                    if (currentNode.getX() > entity.getGridX()) {
                        currentDirection.x = 1;
                        currentDirection.y = 0;
                    } else {
                        currentDirection.x = -1;
                        currentDirection.y = 0;
                    }
                }
                else if (currentNode.getX() == entity.getGridX()) {
                    if (currentNode.getY() > entity.getGridY()) {
                        currentDirection.x = 0;
                        currentDirection.y = 1;
                    } else {
                        currentDirection.x = 0;
                        currentDirection.y = -1;
                    }
                }
                break;
            }
        }
    }

    @Override
    public void calculatePath() {
        entity.getMovement().update(currentDirection.x, currentDirection.y);
        //if (!bomberOldGridPosition.equals(bomber.getGridPosition()) || !followingPath) {
            generatePath();
        //}

        if (path != null && path.size() != 0) {
            pathFollow();
            followingPath = true;
        } else {
            followingPath = false;
            super.calculatePath();
        }

        if (entity.collisionWithMap()) {
            System.out.println("collide map");
            followingPath = false;
            super.calculatePath();
        };
    }

    @Override
    public void update() {
        if (bomber == null) {
            bomber = map.getPlayer();
        }
        calculatePath();
    }
}
