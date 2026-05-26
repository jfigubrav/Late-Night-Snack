public class Rect {

    public int left;
    public int top;
    public int right;
    public int bottom;

    public Rect(int left, int top, int right, int bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }
    public int width() {
        return right - left;
    }
    public int height() {
        return bottom - top;
    }
    // Collision check
    public boolean intersects(Rect other) {
        return !(right < other.left ||
                 left > other.right ||
                 bottom < other.top ||
                 top > other.bottom);
    }

    public void move(int dx, int dy) {
        left += dx;
        right += dx;
        top += dy;
        bottom += dy;
    }
}