import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TetrisGUI extends JPanel {

    public TetrisGUI() {
        setLayout(new BorderLayout());

        // Menu Panel on the Left
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(4, 1));

        // Start Button
        JButton startButton = createMenuButton("Start");
        startButton.addActionListener(e -> {
            // Start game logic goes here

            JOptionPane.showMessageDialog(this, "Game Started: Not implemented yet", "Game Started", JOptionPane.INFORMATION_MESSAGE);
        });
        menuPanel.add(startButton);

        // Reset Button
        JButton resetButton = createMenuButton("Reset");
        resetButton.addActionListener(e -> {
            // Reset game logic goes here


            JOptionPane.showMessageDialog(this, "Game Reset: Not implemented yet", "Game Reset", JOptionPane.INFORMATION_MESSAGE);
        });
        menuPanel.add(resetButton);

        // High Score Button
        JButton highScoreButton = createMenuButton("High Scores");
        highScoreButton.addActionListener(e -> {
            // Display high scores logic goes here


            JOptionPane.showMessageDialog(this, "High Scores: Not implemented yet", "High Scores", JOptionPane.INFORMATION_MESSAGE);
        });
        menuPanel.add(highScoreButton);

        // Instruction Button
        JButton instructionButton = createMenuButton("Instructions");
        instructionButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, getInstructions(), "Instructions", JOptionPane.INFORMATION_MESSAGE);
        });
        menuPanel.add(instructionButton);

        // Add empty space to the left of the menu buttons
        add(Box.createRigidArea(new Dimension(60 , 0)), BorderLayout.WEST);
        add(menuPanel, BorderLayout.WEST);

        // Main Content Panel
        JPanel mainContentPanel = new JPanel();
        mainContentPanel.setLayout(new BorderLayout());

        // Content Panel (Placeholder for Game)
        JPanel contentPanel = new JPanel();
        contentPanel.add(new JLabel("Game Content Goes Here"));

        mainContentPanel.add(contentPanel, BorderLayout.CENTER);

        // Add the main content panel to the center
        add(mainContentPanel, BorderLayout.CENTER);
    }

    private JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(120, 0));
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
            frame.setSize(700, 600);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            TetrisGUI tetrisGUI = new TetrisGUI();
            frame.getContentPane().add(tetrisGUI);

            frame.setVisible(true);
        });
    }
}

