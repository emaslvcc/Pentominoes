
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;



public class TetrisGUI extends JPanel {

    

    public TetrisGUI() {
        this.setLayout(new BorderLayout());

        // Menu Panel on the Left
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(4, 1));

        // Main Content Panel
        Game mainContentPanel = new Game(5,15, 30);

        // Start Button
        JButton startButton = this.createMenuButton("Start");
        startButton.addActionListener(e -> {
            // Start game logic goes here

            JOptionPane.showMessageDialog(this, "test", "test", JOptionPane.INFORMATION_MESSAGE);
            

        });
        menuPanel.add(startButton);

        // Reset Button
        JButton resetButton = this.createMenuButton("Reset");
        resetButton.addActionListener(e -> {
            // Reset game logic goes here


            JOptionPane.showMessageDialog(this, "Game Reset: Not implemented yet", "Game Reset", JOptionPane.INFORMATION_MESSAGE);
        });
        menuPanel.add(resetButton);

        // High Score Button
        JButton highScoreButton = this.createMenuButton("High Scores");
        highScoreButton.addActionListener(e -> {
            // Display high scores logic goes here


            JOptionPane.showMessageDialog(this, "High Scores: Not implemented yet", "High Scores", JOptionPane.INFORMATION_MESSAGE);
        });
        menuPanel.add(highScoreButton);

        // Instruction Button
        JButton instructionButton = this.createMenuButton("Instructions");
        instructionButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, this.getInstructions(), "Instructions", JOptionPane.INFORMATION_MESSAGE);
        });
        menuPanel.add(instructionButton);

        this.add(menuPanel, BorderLayout.WEST);



        // Add the main content panel to the center
        this.add(mainContentPanel, BorderLayout.CENTER);
    }

    private JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(200, 0));
        return button;
    }

    private String getInstructions() {
        return "Tetris Instructions:\n" +
                "Use arrow keys to move and rotate the falling block.\n" +
                "Have fun!";
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Tetris");
            frame.setSize(700, 500);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);

            TetrisGUI tetrisGUI = new TetrisGUI();
            frame.getContentPane().add(tetrisGUI);

            frame.setVisible(true);
        });
    }
}

