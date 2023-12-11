import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.plaf.metal.MetalButtonUI;

// This class extends "JPanel", which is a swing component used for creating panels in the GUI
public class TetrisGUI extends JPanel {

    private int selectedOption;

    // This constructor initialiazes the Tetris GUI by setting up the layout manager and creating UI components
    public TetrisGUI() {

        // This divides the panel into five regions: North, South, East, West, and Center
        this.setLayout(new BorderLayout());
        
        // A new JPanel object named menuPanel is created and is set to contain 4 rows and 1 column
        JPanel menuPanel = new JPanel();
        menuPanel.setBackground(Color.BLACK);
        menuPanel.setLayout(new GridLayout(4, 1, 0, 0));

        // Create an object of each bot
        Bot1 mainContentPanel = new Bot1(5, 15, 45);
        this.addKeyListener(mainContentPanel);
        Bot2 bot2 = new Bot2(5, 15,45);
        this.addKeyListener(bot2);
        Bot3 bot3 = new Bot3(5, 15, 45);
        this.addKeyListener(bot3); // This h
        BestOrderBot bot4 = new BestOrderBot(5, 15, 45);
        this.addKeyListener(bot4);

        ImageIcon gifIcon = new ImageIcon("gif.gif");
        final JLabel gifLabel = new JLabel(gifIcon);
        mainContentPanel.add(gifLabel);
        Timer gifTimer = new Timer(500000, e -> {
            gifLabel.setVisible(true);
        });
        gifTimer.setRepeats(true); // Set to false if you want it to run only once

        // Start button implementation
        JButton startButton = this.createMenuButton("Start");
        startButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show options when the start button is clicked
                this.showOptions();
            }

            private void showOptions() {
                String[] options = {"Bot 1", "Bot 2", "Bot 3", "Optimal Order Demo"};
                TetrisGUI.this.selectedOption = JOptionPane.showOptionDialog(null, "Choose an Option", "Game Options", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

                switch (TetrisGUI.this.selectedOption) {
                    case 0:
                        System.out.println("Bot 1 selected");
                        TetrisGUI.this.removeAll();
                        TetrisGUI.this.add(mainContentPanel, BorderLayout.CENTER);
                        TetrisGUI.this.add(menuPanel, BorderLayout.WEST);
                        gifLabel.setVisible(false);
                        mainContentPanel.start();
                        break;
                    case 1:
                        System.out.println("Bot 2 selected");
                        TetrisGUI.this.removeAll();
                        TetrisGUI.this.add(bot2, BorderLayout.CENTER);
                        TetrisGUI.this.add(menuPanel, BorderLayout.WEST);
                        gifLabel.setVisible(false);
                        bot2.start();
                        break;
                    case 2:
                        System.out.println("Bot 3 selected");
                        TetrisGUI.this.removeAll();
                        TetrisGUI.this.add(bot3, BorderLayout.CENTER);
                        TetrisGUI.this.add(menuPanel, BorderLayout.WEST);
                        gifLabel.setVisible(false);
                        bot3.start();
                        break;
                    case 3:
                        System.out.println("Bot 4 selected");
                        TetrisGUI.this.removeAll();
                        TetrisGUI.this.add(bot4, BorderLayout.CENTER);
                        TetrisGUI.this.add(menuPanel, BorderLayout.WEST);
                        gifLabel.setVisible(false);
                        bot4.start();
                        break;
                    default:
                        System.out.println("No option selected");
                        break;
                }
                TetrisGUI.this.revalidate();
                TetrisGUI.this.repaint();
            }
        });
        menuPanel.add(startButton);

        // Reset button implementation
        JButton resetButton = this.createMenuButton("Reset");
        resetButton.addActionListener(e -> {

            // Reset game logic goes here
            mainContentPanel.reset();
            bot2.reset();
            bot3.reset();
            bot4.reset();
        });
        menuPanel.add(resetButton);

        // High Score button implementation
        JButton highScoreButton = this.createMenuButton("High Scores");
        highScoreButton.addActionListener(e -> {

            // Display high scores logic goes here
            this.displayCombinedHighScores();
        });
        menuPanel.add(highScoreButton);

        // Instruction button implementation
        JButton instructionButton = this.createMenuButtonWithImage("???", "morphius.png");
        instructionButton.addActionListener(e -> this.showInstructions());
        menuPanel.add(instructionButton);

        // Adds the menu panel to the left of the GUI
        this.add(menuPanel, BorderLayout.WEST);

        // Adds the main content panel to the center of the GUI
        this.add(mainContentPanel, BorderLayout.CENTER);
    }

    /**
     * Displays the high scores for all the bots
     * @return void
     */
    private void displayCombinedHighScores() {
        JDialog dialog = new JDialog();
        dialog.setTitle("Scores:");

        // Getting high scores by game modes
        String highScoresBot1 = Bot1.highScores();
        String highScoresBot2 = Bot2.highScores();
        String highScoresBot3 = Bot3.highScores();

        JTextArea textArea = new JTextArea(6, 20);
        textArea.setText("Game 1 High Scores:\n" + highScoresBot1 + "\nGame 2 High Scores:\n" + highScoresBot2 + "\nGame 3 High Scores:\n" + highScoresBot3);
        textArea.setEditable(false);
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        textArea.setForeground(new Color(0, 255, 0)); // Matrix green text
        textArea.setBackground(Color.BLACK); // Black background
        textArea.setFont(new Font("Monospaced", Font.BOLD, 12)); // Monospaced font

        // Wrap the text area in a scroll pane
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(350, 150));
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
    
        // Add the scroll pane to the dialog
        dialog.add(scrollPane);
    
        // Set dialog properties
        dialog.pack();
        dialog.setLocationRelativeTo(null); // Center the dialog
        dialog.setVisible(true);
    }

    /**
     * Displaysthe game's instructions
     * @return void
     */
    private void showInstructions() {
        JDialog dialog = new JDialog();
        dialog.setTitle("Instructions");
    
        // Create a text area for the instructions
        JTextArea textArea = new JTextArea(6, 20);
        textArea.setText(("Welcome to Tetris, you are the chosen one!\n"
        + "This is a game of Tetris in matrix world.\n"
        + "You are on the Bot version where you can choose from Bot 1, Bot2 or Bot 3.\n"
        + "Press the \"P\" key for pause. Good Luck!"));
        textArea.setEditable(false);
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        textArea.setForeground(new Color(0, 255, 0)); // Matrix green text
        textArea.setBackground(Color.BLACK); // Black background
        textArea.setFont(new Font("Monospaced", Font.BOLD, 12)); // Monospaced font
    
        // Wrap the text area in a scroll pane
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(350, 150));
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
    
        // Add the scroll pane to the dialog
        dialog.add(scrollPane);
    
        // Set dialog properties
        dialog.pack();
        dialog.setLocationRelativeTo(null); // Center the dialog
        dialog.setVisible(true);
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
        button.setMargin(new Insets(0, 0, 0, 0));

        // Set the button colors
        button.setBackground(Color.BLACK);
        button.setForeground(Color.green);

        // Change the UI for the button
        button.setUI(new MetalButtonUI() {
        protected Color getSelectColor() {
            return Color.black; // This color is used when the button is clicked
        }});
        return button;
    }

    /**
     * Creates a buttn that can display a pre-defined image
     * @param text message to be displayed
     * @param imagePath image to be displayed
     * @return the final button
     */
    private JButton createMenuButtonWithImage(String text, String imagePath) {
        ImageIcon icon = new ImageIcon(imagePath); // Replace with your image path
        JButton button = new JButton(text, icon);
        button.setPreferredSize(new Dimension(200, 0));
        button.setMargin(new Insets(0, 0, 0, 0));
        button.setFocusable(false);
    
        // Set the button colors
        button.setBackground(Color.BLACK); // Set the background color
        button.setForeground(Color.GREEN); // Set the text color
            button.setUI(new BasicButtonUI());
    
        return button;
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
