package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.text.JTextComponent;

import model.GameBoard;
import model.SudokuSolver;

public abstract class AbstractSolveListener implements ActionListener {

    protected JTextComponent[][] data;


    public AbstractSolveListener(JTextComponent[][] jtc) {
        data = jtc;
    }

    protected GameBoard solveBoard() {

        GameBoard gb = new GameBoard(data.length);

        for (int j = 0; j < data.length; j++) {
            for (int i = 0; i < data[0].length; i++) {

                if (data[j][i].getText().length() > 0) {
                    gb.set(Integer.parseInt(data[j][i].getText()), i, j);
                } else {
                    gb.set(0, i, j);
                }
            }
        }

        return new SudokuSolver(gb).solve();
    }

    @Override
    public abstract void actionPerformed(ActionEvent e);

}
