package model;
/**
 * A read only point for (x,y) pairs
 *
 * @author ngraves3
 *
 */
public class Point {

    public final int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

}
