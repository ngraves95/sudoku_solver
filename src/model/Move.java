package model;
/**
 * A class representing a move on the game board. Follows the command pattern by allowing a move and
 * an unmove
 *
 * @author ngraves3
 *
 */
public class Move {

    /**
     * The coordinates of where to place a number
     */
    private int x, y;

    /**
     * The game board to make moves on
     */
    private GameBoard gb;

    public Move(GameBoard gb, int x, int y) {
        this.gb = gb;
        this.x = x;
        this.y = y;
    }

    public Move(GameBoard gb, Point p) {
        this(gb, p.x, p.y);
    }

    public void place(int num) {
        gb.set(num, x, y);
    }

    public void clear() {
        place(0);
    }

}
