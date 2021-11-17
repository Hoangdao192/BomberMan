package Component.PathFinding;

import Entities.*;
import Map.Map;
import Utils.MinHeap;

import java.util.ArrayList;

public class PathFinding {
    private MinHeap<MapNode> heap = new MinHeap<>();
    private Bomber bomber;
    private DynamicEntity entity;
    private Map map;
    private MapNode startNode;
    private MapNode endNode;
    private ArrayList<ArrayList<ArrayList<Entity>>> mapStaticEntity;

    public PathFinding(DynamicEntity entity, Map map) {
        this.entity = entity;
        this.map = map;
        this.bomber = map.getPlayer();
        mapStaticEntity = map.getStaticEntityList();
        loadNode();
    }

    private void loadNode() {
        startNode = new MapNode(entity);
        endNode = new MapNode(bomber);
    }

    private boolean ifEntityIs(Entity entity) {
        if (entity instanceof Stone || entity instanceof Brick || entity instanceof Bomb) {
            return true;
        }
        return false;
    }

    public void findPath() {
        ArrayList<MapNode> openSet = new ArrayList<>();
        ArrayList<MapNode> closeSet = new ArrayList<>();
        openSet.add(startNode);
        heap.add(startNode);

        while (openSet.size() > 0) {
            MapNode currentNode = heap.getRoot();
            int startX = entity.getGridX() - 1;
            int endX = entity.getGridX() + 1;
            int startY = entity.getGridY() - 1;
            int endY = entity.getGridY() + 1;

            for (int rowIndex = startY; rowIndex <= endY; ++rowIndex) {
                if (rowIndex < 0 || rowIndex > map.getMapGridHeight()) {
                    continue;
                }
                for (int colIndex = startX; colIndex < endX; ++colIndex) {
                    if (colIndex < 0 || colIndex > map.getMapGridWidth()) {
                        continue;
                    }
                    if (rowIndex == entity.getGridY() && colIndex == entity.getGridX()) {
                        continue;
                    }
                    boolean canMove = true;
                    for (int i = 0; i < mapStaticEntity.get(rowIndex).get(colIndex).size(); ++i) {
                        Entity currentEntity = mapStaticEntity.get(rowIndex).get(colIndex).get(i);
                        if (ifEntityIs(currentEntity)) {
                            canMove = false;
                            break;
                        }
                    }
                    if (canMove) {
                        MapNode neighbor = new MapNode(colIndex * map.getGridSize(), rowIndex * map.getGridSize());
                        neighbor.gCost = currentNode.gCost + neighbor.getDistance(currentNode);
                        neighbor.hCost = neighbor.getDistance(endNode);
                        neighbor.fCost = neighbor.hCost + neighbor.gCost;
                    }
                }
            }
        }
    }
}
