package view;

import java.awt.event.ActionEvent;

import javax.swing.text.JTextComponent;

import model.GameBoard;

public class SolveListener extends AbstractSolveListener {

    public SolveListener(JTextComponent[][] jtc) {
        super(jtc);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        GameBoard solution = solveBoard();

        for (int j = 0; j < data.length; j++) {
            for (int i = 0; i < data[0].length; i++) {
                data[j][i].setText("" + solution.get(i, j));
            }
        }

    }

}
