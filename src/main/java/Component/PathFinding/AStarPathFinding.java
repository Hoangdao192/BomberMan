package Component.PathFinding;

import java.util.ArrayList;
import Utils.MinHeap;

public class AStarPathFinding {

    private ArrayList<ArrayList<Integer>> map;
    private final int mapWidth;
    private final int mapHeight;
    private ArrayList<Node> path;

    public AStarPathFinding(ArrayList<ArrayList<Integer>> map, int mapWidth, int mapHeight) {
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.map = map;
        path = new ArrayList<>();
    }

    private void generatePath(Node endNode) {
        path.clear();
        while (endNode != null) {
            path.add(0, endNode);
            endNode = endNode.getParent();
        }
    }

    public ArrayList<Node> findPath(Node startNode, Node endNode) {
        MinHeap<Node> openSet = new MinHeap<>();
        ArrayList<Node> closeSet = new ArrayList<>();
        openSet.add(startNode);

        while (openSet.size() > 0) {
            //  Lấy node nhỏ nhất
            Node currentNode = openSet.getRoot();
            /*for (int i = 0; i < openSet.size(); ++i) {
                if (currentNode.compareTo(openSet.get(i)) == 1) {
                    currentNode = openSet.get(i);
                }
            }*/

            //  Duyệt các Node hàng xóm
            int startX = currentNode.getX() - 1;
            int endX = currentNode.getX() + 1;
            int startY = currentNode.getY() - 1;
            int endY = currentNode.getY() + 1;
            for (int row = startY; row <= endY; ++row) {
                if (row < 0 || row >= mapHeight) {
                    continue;
                }
                for (int col = startX; col <= endX; ++col) {
                    if (col < 0 || col >= mapWidth) {
                        continue;
                    }
                    if (col == currentNode.getX() && row == currentNode.getY()) {
                        continue;
                    }

                    if (col != currentNode.getX() && row != currentNode.getY()) {
                        continue;
                    }
                    //  Node không đi qua được
                    if (map.get(row).get(col) == 1) {
                        continue;
                    }

                    //  Kiểm tra xem node đã đóng hay chưa
                    boolean isClose = false;
                    for (int i = 0; i < closeSet.size(); ++i) {
                        if (closeSet.get(i).getX() == col && closeSet.get(i).getY() == row) {
                            isClose = true;
                            break;
                        }
                    }
                    if (isClose) {
                        continue;
                    }

                    Node newNode = new Node(col, row);
                    int hCost = newNode.getDistance(endNode);
                    int gCost = currentNode.getGCost() + newNode.getDistance(currentNode);
                    newNode.setCost(hCost, gCost);
                    newNode.setParent(currentNode);

                    //  nếu tìm thấy endNode thì kết thúc
                    if (newNode.equals(endNode)) {
                        endNode = newNode;
                        generatePath(endNode);
                        return path;
                    }

                    boolean existNode = false;
                    //  Kiểm tra xem Node đã duyệt chưa
                    for (int i = 0; i < openSet.getHeap().size(); ++i) {
                        if (newNode.equals(openSet.getHeap().get(i))) {
                            existNode = true;
                            //  Nếu duyệt rồi và tìm được con đường ngắn hơn thì thay thế
                            if (newNode.compareTo(openSet.getHeap().get(i)) == -1) {
                                openSet.set(i, newNode);
                            }
                        }
                    }
                    //  Nếu Node chưa duyệt thì thêm nó vào OpenSet
                    if (!existNode) {
                        openSet.add(newNode);
                    }
                }
            }

            //  Đặt node hiện tại là đóng
            openSet.removeRoot();
            closeSet.add(currentNode);
        }
        return null;
    }

    public void setMap(ArrayList<ArrayList<Integer>> map) {
        this.map = map;
    }
}
