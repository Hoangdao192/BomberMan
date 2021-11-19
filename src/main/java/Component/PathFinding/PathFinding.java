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
            boolean skipLoop = false;
            for (int i = 0; i < closeSet.size(); ++i) {
                if (currentNode.equals(closeSet.get(i))) {
                    heap.removeRoot();
                    skipLoop = true;
                    break;
                }
            }
            if (skipLoop) {
                continue;
            }
            int gridX = currentNode.x / map.getGridSize();
            int gridY = currentNode.y / map.getGridSize();
            int startX = gridX - 1;
            int startY = gridY - 1;
            int endX = gridX + 1;
            int endY = gridY + 1;
            for (int row = startY; row <= endY; ++row) {
                if (row < 0 || row >= map.getMapGridHeight()) {
                    return;
                }
                for (int col = startX; col <= endX; ++col) {
                    if (row == gridY && col == gridX) {
                        continue;
                    }
                    if (col < 0 || col >= map.getMapGridWidth()) {
                        continue;
                    }
                    boolean canMove = true;
                    //  Kiểm tra ô hiện tại có thể di chuyển qua không
                    for (int i = 0; i < mapStaticEntity.get(gridY).get(gridX).size(); ++i) {
                        Entity entity = mapStaticEntity.get(gridY).get(gridX).get(i);
                        if (ifEntityIs(entity)) {
                            canMove = false;
                            break;
                        }
                    }

                    if (canMove) {
                        MapNode newNode = new MapNode(col * map.getGridSize(), row * map.getGridSize(), map.getGridSize());
                        newNode.gCost = currentNode.gCost + newNode.getDistance(currentNode);
                        newNode.hCost = currentNode.getDistance(endNode);
                        newNode.fCost = newNode.gCost + newNode.hCost;
                        newNode.parent = currentNode;
                        if (heap.indexOf(newNode) != -1) {
                            heap.set(heap.indexOf(newNode), newNode);
                        } else {
                            heap.add(newNode);
                        }
                    }
                }
            }
            closeSet.add(currentNode);
            heap.removeRoot();
        }
    }
}
