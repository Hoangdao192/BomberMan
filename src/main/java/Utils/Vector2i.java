package Utils;

public class Vector2i {
    public int x;
    public int y;

    public Vector2i() {
        x = 0;
        y = 0;
    }

    public Vector2i(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Copy constructor.
     */
    public Vector2i(Vector2i source) {
        x = source.x;
        y = source.y;
    }

    @Override
    public Vector2i clone() {
        return new Vector2i(this);
    }

    public boolean equals(Vector2i other) {
        return (this.x == other.x && this.y == other.y);
    }
}
