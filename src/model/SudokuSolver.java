package model;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Scanner;

/**
 * This class is the driver to solve a Sudoku game.
 *
 * @author ngraves3
 *
 */
public class SudokuSolver {

    /**
     * The solved GameBoard.
     */
    private GameBoard solution;

    /**
     * The original GameBoard.
     */
    private GameBoard original;

    public SudokuSolver(GameBoard gb) {
        original = gb;
    }

    public void solve() {
        solution = original.clone();

        if (!solution.checkBoardValidity()) {
            throw new IllegalStateException("Board is in illegal state");
        }

        solveHelper(solution);
    }

    /**
     * Helps solve a sudoku puzzle
     *
     * @param gb
     *        the GameBoard to solve
     */
    private boolean solveHelper(GameBoard gb) {

        if (gb.isFull()) {
            return true;
        }

        Collection<Integer> digitsTried = new HashSet<>();
        Collection<Integer> validDigits;
        Move potential;


        for (Point next : gb) {

            validDigits = gb.validMoves(next);

            if (validDigits.isEmpty()) {
                return false;
            }

            potential = new Move(gb, next);

            for (Integer i : validDigits) {

                if (!digitsTried.contains(i)) {
                    potential.place(i);

                    if (solveHelper(gb)) {
                        solution = gb;
                        return true;
                    }

                    digitsTried.add(i);

                    potential.clear();
                }

            }

            return false;

        }

        return false;
    }

    public GameBoard getSolution() {
        return solution;
    }

    public static void main(String[] args) {
        try {
            GameBoard gb = new GameBoard(new Scanner(new File("sample.txt")));
            SudokuSolver solver  = new SudokuSolver(gb);


            solver.solve();
            System.out.println("Original:");
            System.out.println(gb);
            System.out.println("Solved:");
            System.out.println(solver.getSolution());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
