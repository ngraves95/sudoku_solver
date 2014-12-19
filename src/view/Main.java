package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Main {

    private JFrame mainframe;
    private JPanel grid;
    private JPanel control;
    private JPanel solveButtons;
    private JLabel statusBar;
    private JButton solve;
    private JButton hint;
    private JButton clear;

    private HashMap<String, String[][]> configs;
    private Vector<String> nameData;
    private JComboBox<String> nameBox;

    private HintListener hl;

    private final String DEFAULT_STATUS = " ";

    private JTextField[][] inputs;

    public Main() {
        this(9);
    }

    public Main(int size) {

        nameData = new Vector<String>();
        nameBox = new JComboBox<String>(nameData);

        inputs = new JTextField[size][size];

        grid = new JPanel();
        grid.setLayout(new GridLayout(size, size, 5, 5));

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                inputs[i][j] = new JTextField(1);
                inputs[i][j].setFont(new Font("Futura", Font.BOLD, 20));
                grid.add(inputs[i][j]);
            }
        }

        statusBar = new JLabel(DEFAULT_STATUS);

        solve = new JButton("Solve and show");
        hint = new JButton("Show a hint");
        clear = new JButton("Clear cells");
        hl = new HintListener(inputs);

        clear.addActionListener(new ClearListener(inputs));
        solve.addActionListener(new SolveListener(inputs));
        hint.addActionListener(hl);

        solveButtons = new JPanel();
        solveButtons.add(solve, BorderLayout.EAST);
        solveButtons.add(hint, BorderLayout.WEST);

        control = new JPanel();
        control.setLayout(new BorderLayout(5, 5));
        control.add(solveButtons, BorderLayout.EAST);
        control.add(clear, BorderLayout.WEST);
        control.add(statusBar, BorderLayout.NORTH);

        mainframe = new JFrame();
        mainframe.setSize(new Dimension(500, 500));
        mainframe.setLayout(new BorderLayout(5, 5));
        mainframe.add(grid, BorderLayout.CENTER);
        mainframe.add(control, BorderLayout.NORTH);
        mainframe.setTitle("SUDOKU SOLVER");
        mainframe.setResizable(false);
        mainframe.setVisible(true);
        mainframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    }

    private void save(String name) {

        String[][] data = new String[inputs.length][inputs.length];

        for (int j = 0; j < inputs.length; j++) {
            for (int i = 0; i < inputs.length; i++) {
                data[j][i] = inputs[j][i].getText();
            }
        }

        configs.put(name, data);
        nameData.add(name);
    }

    private void load(String name) {

        String[][] data = configs.get(name);

        for (int j = 0; j < inputs.length; j++) {
            for (int i = 0; i < inputs.length; i++) {
                inputs[j][i].setText(data[j][i]);
            }
        }
    }

    public static void main(String[] args) {
        Main frame = new Main();
    }

}
