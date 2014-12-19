package view;

import java.awt.event.ActionEvent;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.JTextField;

import model.GameBoard;
import model.Point;

public class HintListener extends AbstractSolveListener {

    private GameBoard solution;
    private LinkedList<Point> hintsToPlace;

    public HintListener(JTextField[][] jtf) {
        super(jtf);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (solution == null) {
            solution = solveBoard();
            hintsToPlace = new LinkedList<Point>();
            //data is inherited from parent
            for (int j = 0; j < data.length; j++) {
                for (int i = 0; i < data[0].length; i++) {

                    if (data[j][i].getText().length() < 1) {
                        hintsToPlace.add(new Point(i, j));
                    }

                }
            }
        }

        Random rand = new Random();

        Point hint = hintsToPlace.remove(rand.nextInt(hintsToPlace.size()));

        data[hint.y][hint.x].setText("" + solution.get(hint.x, hint.y));
    }

}
