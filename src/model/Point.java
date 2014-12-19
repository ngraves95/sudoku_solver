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

    @Override
    public boolean equals(Object o) {

        if (o == null || !(o instanceof Point)) {
            return false;
        } else if (o == this) {
            return true;
        }

        Point p = (Point) o;

        return x == p.x && y == p.y;
    }

    @Override
    public int hashCode() {
        return (x * 31) + y;
    }

}
