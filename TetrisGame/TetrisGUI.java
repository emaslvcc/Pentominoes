import javax.swing.*;
import java.awt.*;

// This class extends "JPanel", which is a swing component used for creating panels in the GUI
public class TetrisGUI extends JPanel {

    // This constructor initialiazes the Tetris GUI by setting up the layout manager and creating UI components
    public TetrisGUI() {

        // This divides the panel into five regions: North, South, East, West, and Center
        this.setLayout(new BorderLayout());
        
        // A new JPanel object named menuPanel is created and is set to contain 4 rows and 1 column
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(4, 1));

        // An instance of the Game class is created. This is where the game is played
        Game mainContentPanel = new Game(5,15, 45);
        this.addKeyListener(mainContentPanel); // This handles keyboard input for game control

        // Start button implementation
        JButton startButton = this.createMenuButton("Start");
        startButton.addActionListener(e -> {

            // Start game logic goes here
            mainContentPanel.start();
        });
        menuPanel.add(startButton);

        // Reset button implementation
        JButton resetButton = this.createMenuButton("Reset");
        resetButton.addActionListener(e -> {

            // Reset game logic goes here
            mainContentPanel.reset();
        });
        menuPanel.add(resetButton);

        // High Score button implementation
        JButton highScoreButton = this.createMenuButton("High Scores");
        highScoreButton.addActionListener(e -> {

            // Display high scores logic goes here
            Game.highScores();
        });
        menuPanel.add(highScoreButton);

        // Instruction button implementation
        JButton instructionButton = this.createMenuButton("Instructions");
        instructionButton.addActionListener(e -> {

            this.getInstructions();
        });
        menuPanel.add(instructionButton);

        // Adds the menu panel to the left of the GUI
        this.add(menuPanel, BorderLayout.WEST);

        // Adds the main content panel to the center of the GUI
        this.add(mainContentPanel, BorderLayout.CENTER);
    }

    /** 
     * Creates a button
     * @param text is the message to be displayed in the button
     * @return the button
     */
    private JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(200, 0));
        button.setFocusable(false);
        return button;
    }

     /** 
     * Displays the game's instructions
     * @return the instructions for the game to be played
     */
    public void getInstructions() {
        
        // Set background color of the option pane and panel
        UIManager.put("OptionPane.background", new Color(145, 0, 12));
        UIManager.put("Panel.background", new Color(145, 0, 12));

        String instructions = "<html><font color='white'>Tetris Instructions: A = left, D = right, S = down, Space = fast-forward, SHIFT= rotate</font></html>";

        JLabel label = new JLabel(instructions);
        label.setFont(new Font("SansSerif", Font.PLAIN, 20)); // You can adjust the font if needed
        label.setForeground(Color.WHITE);

        JOptionPane.showMessageDialog(this, label, "Tetris Instructions", JOptionPane.INFORMATION_MESSAGE);

        // Reset UIManager settings after the dialog is closed
        UIManager.put("OptionPane.background", UIManager.get("OptionPane.background"));
        UIManager.put("Panel.background", UIManager.get("Panel.background"));
    }
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            JFrame frame = new JFrame("Tetris"); // A JFrame named frame is created
            frame.setSize(980, 710); // Size is set to 440 width and 710 height
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // When the user closes the window, the application is terminated
            frame.setResizable(false); // It cannot be resized by the user
            frame.setFocusable(false); // The ability to focus the frame is disabled
        
            TetrisGUI tetrisGUI = new TetrisGUI(); // A TetrisGUI object named testrisGUI is created
            frame.getContentPane().add(tetrisGUI); // The tetrisGUI instance is added to the frame

            frame.setVisible(true); // The game is visible to the user
        });
    }
}

