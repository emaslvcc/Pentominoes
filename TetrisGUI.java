import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TetrisGUI extends JPanel {

    public TetrisGUI(JFrame parentFrame) {

        setLayout(new BorderLayout());
        setBackground(Color.BLACK);

        // Menu Panel on the Left
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(4, 1));
        menuPanel.setBackground(Color.BLACK);

        // Start Button
        JButton startButton = createMenuButton("Start");
        startButton.addActionListener(e -> {
            // Navigate to GameModeScreen
            parentFrame.setContentPane(new GameModeScreen(parentFrame));
            parentFrame.revalidate();
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
        instructionButton.addActionListener(e -> JOptionPane.showMessageDialog(this, getInstructions(), "Instructions", JOptionPane.INFORMATION_MESSAGE));
        menuPanel.add(instructionButton);

        add(Box.createRigidArea(new Dimension(60 , 0)), BorderLayout.WEST);
        add(menuPanel, BorderLayout.WEST);

        // Main Content Panel
        JPanel mainContentPanel = new JPanel();
        mainContentPanel.setLayout(new BorderLayout());
        mainContentPanel.setBackground(Color.BLACK);

        // Content Panel (Placeholder for Game)
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(Color.BLACK);

        // Load and display the GIF
        // Load and display the GIF
        ImageIcon tetrisGif = new ImageIcon("9pII.gif");  // Ensure 45N.gif is in the correct path
        if (tetrisGif.getImageLoadStatus() == MediaTracker.ERRORED) {
            System.err.println("Error loading GIF");
        } else if (tetrisGif.getIconWidth() == -1) {
            System.err.println("Could not find GIF file");
        } else {
            System.out.println("GIF loaded successfully");
        }
        JLabel gifLabel = new JLabel(tetrisGif);
        contentPanel.add(gifLabel);


        mainContentPanel.add(contentPanel, BorderLayout.CENTER);

        // Add the main content panel to the center
        add(mainContentPanel, BorderLayout.CENTER);
    }
    private JButton createMenuButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(Color.BLACK);
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.setColor(new Color(0, 255, 0));  // Matrix green
                g2.setRenderingHint(
                        RenderingHints.KEY_TEXT_ANTIALIASING,
                        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(text)) / 2;
                int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2.drawString(text, x, y);
                g2.dispose();
            }
        };
        button.setOpaque(false);  // Make sure the button paints its background
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setForeground(new Color(0, 255, 0));  // Set text color to Matrix green
        return button;
    }


    private String getInstructions() {
        return """
                Tetris Instructions:
                Use arrow keys to move and rotate the falling block.
                Have fun!""";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Set the Nimbus Look and Feel
                UIManager.setLookAndFeel("javax.swing.play.nimbus.NimbusLookAndFeel");

                // Customize the default settings for Nimbus
                UIManager.put("Button.background", Color.BLACK);
                UIManager.put("Button.foreground", Color.WHITE);
                UIManager.put("Button[MouseOver].background", Color.DARK_GRAY);  // Optional: Change color on mouse over
            } catch (Exception e) {
                // Nimbus Look and Feel may not be available, default will be used
                e.printStackTrace();
            }

            JFrame frame = new JFrame("Tetris Lobby");
            frame.setSize(700, 600);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            TetrisGUI TetrisGUI = new TetrisGUI(frame);
            frame.getContentPane().add(TetrisGUI);

            frame.setVisible(true);
        });
    }

    static class GameModeScreen extends JPanel implements ActionListener {

        public GameModeScreen(JFrame parentFrame) {
            setLayout(new BorderLayout());
            setBackground(Color.BLACK);

            // Game Mode Buttons
            JPanel centerPanel = new JPanel(new GridLayout(4, 1));
            centerPanel.setBackground(Color.BLACK);
            JButton buttonEasy = createButton("Easy");
            JButton buttonMedium = createButton("Medium");
            JButton buttonHard = createButton("Hard");
            JButton buttonBrutal = createButton("Brutal");
            centerPanel.add(buttonEasy);
            centerPanel.add(buttonMedium);
            centerPanel.add(buttonHard);
            centerPanel.add(buttonBrutal);
            add(centerPanel, BorderLayout.CENTER);

            // Back Button with Unicode left arrow
            // Back Button with Unicode left arrow
            JButton buttonBack = getjButton(parentFrame);

            add(buttonBack, BorderLayout.NORTH);

        }

        private static JButton getjButton(JFrame parentFrame) {
            JButton buttonBack = new JButton("← Back");
            buttonBack.setForeground(new Color(0, 255, 0));  // Set text color to Matrix green
            buttonBack.setBackground(Color.BLACK);  // Set background color to black
            buttonBack.addActionListener(e -> {
                // Navigate back to TetrisLobby
                parentFrame.setContentPane(new TetrisGUI(parentFrame));
                parentFrame.revalidate();
            });
            return buttonBack;
        }

        private JButton createButton(String text) {
            JButton button = new JButton(text) {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setColor(getBackground());  // Use the button's background color
                    g2.fillRect(0, 0, getWidth(), getHeight());
                    g2.setColor(getForeground());  // Use the button's text color
                    g2.setRenderingHint(
                            RenderingHints.KEY_TEXT_ANTIALIASING,
                            RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                    g2.setFont(getFont());
                    FontMetrics fm = g2.getFontMetrics();
                    int x = (getWidth() - fm.stringWidth(text)) / 2;
                    int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                    g2.drawString(text, x, y);
                    g2.dispose();
                }
            };
            button.setOpaque(true);  // Make sure the button paints its background
            button.setBackground(Color.BLACK);  // Set button background color to black
            button.setForeground(new Color(0, 255, 0));  // Set text color to Matrix green
            button.addActionListener(this);
            return button;
        }



        @Override
        public void actionPerformed(ActionEvent e) {
            // Implement game mode selection logic here
            String command = e.getActionCommand();
            JOptionPane.showMessageDialog(this, "Selected Mode: " + command, "Mode Selected", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}