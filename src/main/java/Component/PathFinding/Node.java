package Component.PathFinding;

import Utils.Vector2i;

public class Node implements Comparable<Node>{
    private int x;
    private int y;
    private int hCost = 0;
    private int gCost = 0;
    private int fCost = 0;
    private Node parent = null;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setCost(int hCost, int gCost) {
        this.hCost = hCost;
        this.gCost = gCost;
        this.fCost = hCost + gCost;
    }

    public int getFCost() {
        return fCost;
    }

    public int getGCost() {
        return gCost;
    }

    public int getHCost() {
        return hCost;
    }

    public int getDistance(Node other) {
        int deltaX = other.x - x;
        int deltaY = other.y - y;
        int distance = (int) (Math.sqrt(deltaX * deltaX + deltaY * deltaY) * 10);
        return distance;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public Node getParent() {
        return parent;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Vector2i getPosition() {
        return new Vector2i(x, y);
    }

    @Override
    public int compareTo(Node other) {
        if (fCost > other.fCost) {
            return 1;
        } else if (fCost < other.fCost) {
            return -1;
        }

        if (gCost > other.gCost) {
            return 1;
        } else if (gCost < other.gCost) {
            return  -1;
        }
        return 0;
    }

    public boolean equals(Node other) {
        if (x == other.x && y == other.y) {
            return true;
        }
        return false;
    }
}
