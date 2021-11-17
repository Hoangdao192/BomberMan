package Component.PathFinding;

import Entities.Entity;

public class MapNode implements Comparable<MapNode>{
    /**
     * Tất cả khoảng cách được tính theo số hàng số cột của map
     */
    //  Khoảng cách đến điểm kết thúc
    public int hCost = 0;
    //  Khoảng cách đến điểm bắt đầu
    public int gCost = 0;
    //  = gCost + hCost
    public int fCost = 0;
    public int x = 0;
    public int y = 0;
    private Entity entity;
    public MapNode parent = null;

    public MapNode(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public MapNode(Entity entity) {
        this.entity = entity;
        x = entity.getX();
        y = entity.getY();
    }

    public int getDistance(MapNode other) {
        double x = other.x - this.x;
        double y = other.y - this.y;
        return (int) Math.sqrt(x * x + y * y);
    }

    @Override
    public int compareTo(MapNode o) {
        if (fCost > o.fCost) {
            return 1;
        } else if (fCost < o.fCost) {
            return -1;
        }

        if (gCost > o.gCost) {
            return 1;
        } else if (gCost < o.gCost) {
            return  -1;
        }
        return 0;
    }
}
