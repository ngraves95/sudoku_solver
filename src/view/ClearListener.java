package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

public class ClearListener implements ActionListener {

    private JTextField[][] grid;

    public ClearListener(JTextField[][] grid) {
        this.grid = grid;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (JTextField[] row : grid) {
            for (JTextField cell : row) {
                cell.setText("");
            }
        }

    }

}
