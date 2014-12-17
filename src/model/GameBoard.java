package model;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Scanner;

/**
 * The game board used to hold all of the data about the state of the Sudoku puzzle
 *
 * @author ngraves3
 *
 */
public class GameBoard implements Iterable<Point> {

    /**
     * Backing data for the game board. Initialized to 0 because primitive array
     */
    private int[][] backing;

    /**
     * Initialize a <size> x <size> gameboard
     *
     * @param size
     */
    public GameBoard(int size) {
        backing = new int[size][size];
    }

    /**
     * Initializes a 9 x 9 gameboard
     */
    public GameBoard() {
        this(9);
    }

    /**
     * Creates a gameboard from a file of the format: an integer representing size, then a size x
     * size space delimited matrix. 0 is used to show an unknown space
     *
     * @param input
     */
    public GameBoard(Scanner input) {
        this(input.nextInt());

        for (int y = 0; y < backing.length; y++) {
            for (int x = 0; x < backing[0].length; x++) {
                int value = input.nextInt();

                if (value > 0)
                    set(value, x, y);
            }
        }

        input.close();

    }

    private GameBoard(int[][] old) {

        backing = new int[old.length][old[0].length];

        for (int y = 0; y < old.length; y++) {
            for (int x = 0; x < old[0].length; x++) {
                backing[y][x] = old[y][x];
            }
        }

    }

    /**
     * Gets the value at an (x, y) pair
     *
     * @param x
     *        x coord
     * @param y
     *        y coord
     * @return the value at the coordinated
     */
    public int get(int x, int y) {
        return backing[y][x];
    }

    /**
     * Gets the value at the (x,y) pair specified by the Point
     *
     * @param p
     *        the (x, y) pair
     * @return the value at that pair
     */
    public int get(Point p) {
        return get(p.x, p.y);
    }

    /**
     * Sets the value at an (x, y) pair
     *
     * @param num
     *        the value to set
     * @param x
     *        the x coord
     * @param y
     *        the y coord
     */
    public void set(int num, int x, int y) {
        backing[y][x] = num;
    }

    /**
     * Sets the value at an (x,y) pair
     *
     * @param num
     *        the value to set
     * @param p
     *        the position to set
     */
    public void set (int num, Point p) {
        set(num, p.x, p.y);
    }

    /**
     * Wrapper for validMoves(int x, int y)
     *
     * @param p
     *        the point to check
     * @return the Collection<Integer> of valid digits for the position
     */
    public Collection<Integer> validMoves(Point p) {
        return validMoves(p.x, p.y);
    }

    /**
     * Returns a collection of valid moves for the position(x, y)
     *
     * @param x
     *        the x coord
     * @param y
     *        the y coord
     * @return Collection<Integer> of valid digits for the position (x, y)
     */
    public Collection<Integer> validMoves(int x, int y) {

        Collection<Integer> retval = new HashSet<Integer>();

        // Gets size of a sudoku block
        int size = (int) Math.sqrt(backing.length);

        int blockX = size * (x / size);
        int blockY = size * (y / size);

        for (int i = 1; i <= backing.length; i++) {
            retval.add(i);
        }


        for (int i = 0; i < backing.length; i++) {

            retval.remove(get(i, y)); // row
            retval.remove(get(x, i)); // col

        }

        if (!retval.isEmpty()) {

            for (int i = blockX; i < blockX + size; i++) {
                for (int j = blockY; j < blockY + size; j++) {

                    retval.remove(get(i, j));

                }
            }
        }

        return retval;
    }

    /**
     * Returns whether a move is valid or not
     *
     * @param num
     *        the number to place
     * @param x
     *        the x coord
     * @param y
     *        the y coord
     * @return boolean whether the move is valid or not
     */
    public boolean isValid(int num, int x, int y) {
        return validMoves(x, y).contains(num);
    }

    /**
     * Returns whether or not a move is valid
     *
     * @param num
     *        the number to place
     * @param p
     *        the position to place the number
     * @return whether or not the move is valid
     */
    public boolean isValid(int num, Point p) {
        return validMoves(p).contains(num);
    }

    /**
     * Checks the current state of the board to see if it is a valid configuration
     *
     * @TODO could set an error message to say where the board is invalid
     * @return boolean whether the board is valid or not
     */
    public boolean checkBoardValidity() {

        Queue<ValuePoint> toPlace = new LinkedList<>();

        for (int j = 0; j < backing.length; j++) {
            for (int i = 0; i < backing[0].length; i++) {
                if (backing[j][i] > 0) {
                    toPlace.offer(new ValuePoint(backing[j][i], i, j));
                    backing[j][i] = 0;
                }
            }
        }

        while (!toPlace.isEmpty()) {

            ValuePoint vp = toPlace.poll();

            if (!isValid(vp.val, vp.point)) {
                return false;
            }

            set(vp.val, vp.point);

        }

        return true;
    }

    /**
     * Returns the size of the board (the side length)
     *
     * @return
     */
    public int size() {
        return backing.length;
    }

    /**
     * Checks if all characters have been placed.
     *
     * @return true iff every cell is full, false otherwise
     */
    public boolean isFull() {

        for (int[] row : backing) {
            for (int item : row) {
                if (item == 0) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public String toString() {

        StringBuilder retval = new StringBuilder();

        StringBuilder vsep = new StringBuilder();
        for (int x = 0; x < backing[0].length; x++) {
            vsep.append("+---");
        }
        vsep.append("+\n");

        String row = vsep.toString();

        for (int i = 0; i < backing.length; i++) {

            retval.append(row);

            for (int j = 0; j < backing.length; j++){

                if (backing[i][j] == 0) {
                    retval.append("| ").append(' ').append(' ');
                } else {

                    retval.append("| ").append(backing[i][j]).append(' ');
                }
            }

            retval.append('|').append('\n');

        }

        retval.append(row);

        return retval.toString();
    }

    /**
     * Returns a copy of this GameBoard
     *
     * @return a copy of the GameBoard
     */
    @Override
    public GameBoard clone() {
        return new GameBoard(backing);
    }

    @Override
    public Iterator<Point> iterator() {
        return new Innerator();
    }

    private class Innerator implements Iterator<Point> {

        private Point _next;

        public Innerator() {
            _next = findNext();
        }

        private Point findNext() {

            int x = 0;
            int y = 0;

            // iterate from upper-left to find the starting position
            while (backing[y][x] != 0) {

                x++;
                if (x >= backing.length) { // check if x is overincremented
                    x = 0;
                    y++;
                }
            }

            if (y >= backing.length && x >= backing.length) {
                return null;
            }

            return new Point(x, y);

        }

        @Override
        public boolean hasNext() {
            return _next != null;
        }

        @Override
        public Point next() {

            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            Point retval = _next;
            _next = findNext();

            return retval;
        }

    }

    private class ValuePoint {

        private final Point point;
        private final int val;

        public ValuePoint(int val, int x, int y) {
            point = new Point(x, y);
            this.val = val;
        }
    }

}
