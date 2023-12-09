/**
 * @author Department of Data Science and Knowledge Engineering (DKE)
 * @version 2022.0
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.Timer;
import javax.swing.UIManager;

/**
 * This class takes care of all the graphics to display a certain state.
 */
public class Game extends JPanel implements KeyListener {
    private int[][] state;
    private int size;
    private Timer looper;
    private boolean started = false;
    private int startx;
    private boolean pause = false;
    private int starty;
    private int currentPentominoIndex;
    private int mutation = 0;
    private int[][] currentPentomino;
    private int[][] nextPentomino;
    private int nextIndex;
    private Random random = new Random();
    private int score = 0;
    private static ArrayList<Integer> scoreList = new ArrayList<>();

    /**
     * This is the Game constructor that handles the general Tetris logic
     * @param x width of the Tetris board
     * @param y height of the Tetris board
     * @param _size size of the Tetris board
     */
    public Game(int x, int y, int _size) {
        
        this.shuffleOrder();
        // Starts at the first index of the Pentomino Order array
        this.currentPentominoIndex = this.random.nextInt(PentominoDatabase.data.length);

        // Performs the action specified every 600 milliseconds
        this.looper = new Timer(600, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                // Checks if the pentomino has reached the end of the grid
                if (Game.this.starty + Game.this.currentPentomino[0].length == 15) {
                    Game.this.advanceToNextPentomino(); // Advances to the next pentomino in the database
                } else {
                    
                    // Check if pentomino is going to collide with any other pentomino
                    boolean collide = false;
                    for (int i = Game.this.startx; i < Game.this.currentPentomino.length + Game.this.startx; i++) {
                        for (int j = Game.this.starty; j < Game.this.currentPentomino[0].length + Game.this.starty; j++) {
                            if (Game.this.currentPentomino[i - Game.this.startx][j - Game.this.starty] == 1) {
                                if (j < 14 && Game.this.state[i][j + 1] != -1) {
                                    
                                    // Checks if game is over
                                    if ((Game.this.startx <= 0 && Game.this.starty <= 0)) {
                                        Game.this.looper.stop();
                                        Game.this.started = false;
                                        scoreList.add(Game.this.score);
                                        Collections.sort(scoreList);
                                        Collections.reverse(scoreList);
                                        
                                        String gameOverMessage = 
                                                                                                                                                                      
                                                     
                                         
                                        "   _____          __  __ ______    ______      ________ _____  \n" +
                                        "  / ____|   /\\   |  \\/  |  ____|  / __ \\ \\    / /  ____|  __ \\ \n" +
                                        " | | |_ | / /\\ \\ | |\\/| |  __|   | |  | |\\ \\/ / |  __| |  _  / \n" +
                                        " | |__| |/ ____ \\| |  | | |____  | |__| | \\  /  | |____| | \\ \\ \n" +
                                        "  \\_____/_/    \\_\\_|  |_|______|  \\____/   \\/   |______|_|  \\_\\";
                                     
                                    
                                                                                                                                                
                                                                                                                                                                           
                                      
 
                                                                                                                                      
                                                                                                                                      
 
                                    
                                         
                                        // Create a JLabel with HTML formatting to display a Game Over message
                                        JLabel label = new JLabel("<html><pre>" + gameOverMessage + "</pre></html>");
                                        label.setForeground(Color.GREEN); // Set text color to white

                                        // Customize background to be red
                                        UIManager.put("OptionPane.background", new Color(0, 0, 0));
                                        UIManager.put("Panel.background", new Color(0, 0, 0)); 
                            
                                        // Display message
                                        JOptionPane optionPane = new JOptionPane(label, JOptionPane.PLAIN_MESSAGE);
                                        optionPane.setPreferredSize(new Dimension(550, 220));
                                        JDialog dialog = optionPane.createDialog("Game Over");
                                        dialog.setVisible(true);

                                        // Reset the game or perform other actions as needed
                                        Game.this.reset();
                                        return;

                                    } 
                                    collide = true;
                                    break;
                                }
                            }
                        }
                    }
                    if (!collide) { // If the pentomino did not collide, it will check if it can descend
                        if (Game.this.starty < 14 && Game.this.state[Game.this.startx][Game.this.starty+1] == -1) {
                            Game.this.starty++; // Pentomino descends one line
                        }    
                    } else {
                    Game.this.advanceToNextPentomino(); // Advances to the next pentomino in the Pentomino Order array
                    }
                }
                if(Game.this.started) Game.this.repaint();
            }
        });
        this.size = _size;
        this.setPreferredSize(new Dimension(x * this.size, y * this.size));
        this.setFocusable(true);
        this.addKeyListener(this);

        // Create an empty board
        this.state = new int[x][y];
        for (int i = 0; i < this.state.length; i++) {
            for (int j = 0; j < this.state[i].length; j++) {
                this.state[i][j] = -1;
            }
        }
    }

    /**
     * This function is called by the system if required for a new frame, and uses the state stored by the UI class
     * @param g 
     * @return void
     */
    public void paintComponent(Graphics g) {

        Graphics2D localGraphics2D = (Graphics2D) g;

        // Generates a start screen
        if (!this.started) {  
            localGraphics2D.setColor(Color.black);
            localGraphics2D.fill(this.getVisibleRect());

            // ASCII lettering for a start screen
            String startScreenArt = 
        
            "                                                                                       \n" +
            " _________________      ______   _________________      _____    ____          ______  \n" +
            "/                 \\ ___|\\     \\ /                 \\ ___|\\    \\  |    |     ___|\\     \\\n" +
            "\\______     ______/|     \\     \\\\______     ______/|    |\\    \\ |    |    |    |\\     \\\n" +
            "   \\( /    /  )/   |     ,_____/|  \\( /    /  )/   |    | |    ||    |    |    |/____/|\n" +
            "    ' |   |   '    |     \\--'\\_|/   ' |   |   '    |    |/____/ |    | ___|    \\|   | |\n" +
            "      |   |        |     /___/|       |   |        |    |\\    \\ |    ||    \\    \\___|/ \n" +
            "     /   //        |     \\____|\\     /   //        |    | |    ||    ||    |\\     \\    \n" +
            "    /___//         |____ '     /|   /___//         |____| |____||____||\\ ___\\|_____|   \n" +
            "   |`   |          |    /_____/ |  |`   |          |    | |    ||    || |    |     |   \n" +
            "   |____|          |____|     | /  |____|          |____| |____||____| \\|____|_____|   \n" +
            "     \\(              \\( |_____|/     \\(              \\(     )/    \\(      \\(    )/     \n" +
            "      '               '    )/         '               '     '      '       '    '      \n"+


        
        
        
        
        
            "                        Created by Group 5                          Version 1.0                                                       ";
    

            localGraphics2D.setColor(Color.GREEN); // Set letters to white
            Font font = new Font("Monospaced", Font.BOLD, 12);
            localGraphics2D.setFont(font);
    
            // Calculate the starting position to center the ASCII art
            int artWidth = 70; // Adjust the width based on the actual width of ASCII art
            int artHeight = 15; // Adjust the height based on the actual height of ASCII art
            int windowWidth = this.getWidth(); // Adjust this based on the width of the window
            int windowHeight = this.getHeight(); // Adjust this based on the height of the window
    
            int startX = (windowWidth - artWidth * 9) / 2; // Adjust 9 based on the character width in your font
            int startY = (windowHeight - artHeight * 15) / 2; // Adjust 15 based on the character height in your font
            String[] lines = startScreenArt.split("\n");
            for (int i = 0; i < lines.length; i++) {
                localGraphics2D.drawString(lines[i], startX, startY + i * 15);
            }
        } else {
            localGraphics2D.setColor(Color.BLACK);
            localGraphics2D.fill(this.getVisibleRect());

            // Paints the Tetris grid
            localGraphics2D.setColor(Color.GREEN);
            for (int i = 0; i <= this.state.length; i++) {
                localGraphics2D.drawLine(i * this.size, 0, i * this.size, this.state[0].length * this.size);
            }
            for (int i = 0; i <= this.state[0].length; i++) {
                localGraphics2D.drawLine(0, i * this.size, this.state.length * this.size, i * this.size);
            } 
        
            // Generates a component that shows the user what pentomino is coming up and what the current score is
            localGraphics2D.drawString("NEXT PENTOMINO", 450, 200);
            localGraphics2D.drawString("Current score: " + this.score, 450, 500);
        
            for (int i = 0; i < this.state.length; i++) {
                for (int j = 0; j < this.state[0].length; j++) {
                    if (this.state[i][j] != -1) {
                        g.setColor(this.GetColorOfID(this.state[i][j]));
                        localGraphics2D.fill(new Rectangle2D.Double(i * this.size + 1, j * this.size + 1, this.size - 1, this.size - 1));
                    }
                }
            }

            // extra out of bounds or collide check.
            this.currentPentomino = PentominoDatabase.data[this.currentPentominoIndex][this.mutation];
            for (int i = 0; i < this.currentPentomino.length; i++) {
                for (int j = 0; j < this.currentPentomino[0].length; j++) {
                    if (this.currentPentomino[i][j] == 1) {
                        if(i+this.startx >= 5){
                            this.startx--;
                            if(i+this.startx >= 5) this.startx = 4-i;
                        }
                        if(j+this.starty >= 15){
                            this.starty--;
                            if(this.starty>= 15) this.starty = 14-j;
                        }
                        if (this.state[i + this.startx][j + this.starty] != -1) {
                            this.starty--;
                            i = 100;
                            j = 100;
                            break;
                        }                                     
                    }
                }
            }
            // Prints the current pentomino at the positions it goes through
            for (int i = 0; i < this.currentPentomino.length; i++) {
                for (int j = 0; j < this.currentPentomino[0].length; j++) {
                    if (this.currentPentomino[i][j] == 1) {
                        if (this.started) {
                        g.setColor(this.GetColorOfID(this.currentPentominoIndex));
                        localGraphics2D.fill(new Rectangle2D.Double(i * this.size + this.startx * this.size + 1, j * this.size + this.starty * this.size + 1, this.size - 1, this.size - 1));
                        }                                     
                    }
                }
            }

            this.nextIndex = this.currentPentominoIndex+1;
            if (this.nextIndex == PentominoDatabase.data.length) this.nextIndex = 0;
                this.nextPentomino = PentominoDatabase.data[this.nextIndex][0];

            // Paint next grid
            localGraphics2D.setColor(Color.BLACK);
            for (int i = 0; i <= this.nextPentomino.length; i++) {
                localGraphics2D.drawLine((i * this.size) + 450 , 220, (i * this.size) + 450, (this.nextPentomino[0].length * this.size) + 220);
            }
            for (int i = 0; i <= this.nextPentomino[0].length; i++) {
                localGraphics2D.drawLine(450, (i * this.size)+220, (this.nextPentomino.length * this.size) + 450, (i * this.size) + 220);
            } 

            // Paint next pentomino
            for (int i = 0; i < this.nextPentomino.length; i++) {
                for (int j = 0; j < this.nextPentomino[0].length; j++) {
                    if (this.nextPentomino[i][j] == 1) {
                        if (this.started) {
                            g.setColor(this.GetColorOfID(this.nextIndex));
                            localGraphics2D.fill(new Rectangle2D.Double(i * this.size + 1 + 450, j * this.size + 1 + 220, this.size - 1, this.size - 1));
                        }                                
                    }
                }
            }

            // Check if horizontal lines should be removed 
            for (int i = 0; i < this.state[0].length; i++) {
                boolean filledline = true;
                for (int j = 0; j < this.state.length; j++) {
                    if (this.state[j][i] == -1) {
                        filledline = false;
                        break;
                    }
                }
                if (filledline) {
                    this.score++;
                    for (int t = 0; t < 5; t++) {
                        this.state[t][i] = -1;
                        g.setColor(Color.BLACK);
                        localGraphics2D.fill(new Rectangle2D.Double(t * this.size + 1, i * this.size + 1, this.size - 1, this.size - 1));
                    }
                    // Drop pentominos if lines where removed
                    for (int t = i; t > 0; t--) {
                        for (int u = 0; u < 5 ; u++) {
                            if (this.state[u][t - 1] != -1) {
                                this.state[u][t] = this.state[u][t - 1];
                                this.state[u][t - 1] = -1;
                            } 
                        }
                    }
                }   
            }
        }
    }

    /**
     * Advances to the following pentomino in the database
     * @return void
     */
    public void advanceToNextPentomino() {
        
        // Add pentomino to state
        for (int i = this.startx; i < this.currentPentomino.length + this.startx; i++) {
            for (int j = this.starty; j < this.currentPentomino[0].length + this.starty; j++) {
                if (this.currentPentomino[i - this.startx][j - this.starty] == 1) {
                    this.state[i][j] = this.currentPentominoIndex;
                } 
            }
        }
        this.currentPentominoIndex = this.nextIndex;
        this.mutation = 0;

        // Reposition next pentomino at the beginning of the grid
        Game.this.startx = 0;
        Game.this.starty = 0;

        // Check if all the pentominos have been reached to go back to the beginning
        if (this.currentPentominoIndex >= PentominoBuilder.basicDatabase.length) {
            this.currentPentominoIndex = 0;
        }
    }
 
    /**
     * Decodes the ID of a pentomino into a color
     * @param i ID of the pentomino to be colored
     * @return the color to represent the pentomino. It uses the class Color (more in ICS2 course in Period 2)
     */
    private Color GetColorOfID(int i) {
        if (i == 0) {return Color.BLUE;}
        else if (i == 1) {return Color.ORANGE;}
        else if (i == 2) {return Color.CYAN;}
        else if (i == 3) {return Color.GREEN;}
        else if (i == 4) {return Color.MAGENTA;}
        else if (i == 5) {return Color.PINK;}
        else if (i == 6) {return Color.RED;}
        else if (i == 7) {return Color.YELLOW;}
        else if (i == 8) {return new Color(75, 50, 50);}
        else if (i == 9) {return new Color(0, 0, 100);}
        else if (i == 10) {return new Color(100, 0,0);}
        else if (i == 11) {return new Color(0, 100, 0);}
        else {return Color.LIGHT_GRAY;}
    }

    /**
     * Resets the game to its initial state
     * @return void
     */
    public void reset() {

        for (int i = 0; i < this.state.length; i++) {
            for (int j = 0; j < this.state[0].length; j++) {
                this.state[i][j] = -1;
                this.looper.stop();
            }
        }
        this.startx = 0;
        this.starty = 0;
        this.currentPentominoIndex = this.random.nextInt(PentominoDatabase.data.length);
        this.nextIndex = this.currentPentominoIndex+1;
        this.mutation = 0;
        this.started = false;
        this.score = 0;
        this.shuffleOrder();

        this.repaint();
    }

    /**
     * Starts the game
     * @return void
     */
    public void start() {
        this.started = true;
        this.looper.start();
        this.repaint();
    }

    /**
     * Handles keyboard input from the user
     * @return void
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (this.startx < 0 || this.starty < 0) return;

        // KeyCode 80 = P
        if (e.getKeyCode() == 80) {
            if (!this.pause) {
                this.looper.stop();
                this.pause = true;
            }
            else if (this.pause) {
                this.looper.start();
                this.pause = false;
            }
        }

        // KeyCode 65 = A
        if (e.getKeyCode() == 65 && this.started) {
            if (this.moveLeft())
            this.startx--;
        }

        // KeyCode 68 = D
        if (e.getKeyCode() == 68 && this.started) {
            if (this.moveRight())
            this.startx++;
        }

        // KeyCode 32 = Space Bar
        if (e.getKeyCode() == 32  && this.started) {
            if (this.moveDown())
            this.starty++;
        }

        // KeyCode 16 = Shift
        if (e.getKeyCode() == 16  && this.started) {
            this.rotate();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Body unchanged
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Body unchanged
    }

    /**
     * Checks if the pentomino piece can move to the right
     * @return boolean
     */
    public boolean moveRight() {
        for (int i = this.startx; i < this.startx + this.currentPentomino.length; i++) {
            for (int j = this.starty; j < this.starty + this.currentPentomino[0].length; j++) {
                if (i + 1 == 5) return false;
                else {
                    if (this.state[i + 1][j] != -1) return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks if the pentomino piece can move to the left
     * @return boolean
     */
    public boolean moveLeft(){
        for (int i = this.startx; i < this.startx + this.currentPentomino.length; i++) {
            for (int j = this.starty; j < this.starty + this.currentPentomino[0].length; j++) {
                if (i - 1 == -1) return false;
                else {
                    if (this.state[i - 1][j] != -1) return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks if the pentomino piece can move downwards
     * @return boolean
     */
    public boolean moveDown(){
        for (int i = this.startx; i < this.startx + this.currentPentomino.length; i++) {
            for (int j = this.starty; j < this.starty + this.currentPentomino[0].length; j++) {
                if (j + 1 == 15) return false;
                else {
                    if(this.state[i][j+1] != -1) return false;
                }
            }
        }
        return true;
    }

    /**
     * Rotates pentomino
     * @return void
     */
    public void rotate() {

        int lastmutation = this.mutation;
        this.mutation++;

        if (this.mutation >= PentominoDatabase.data[this.currentPentominoIndex].length) this.mutation = 0;
        int[][] curr = PentominoDatabase.data[this.currentPentominoIndex][this.mutation];

        for (int i = this.startx; i < this.startx + curr.length; i++) {
            for (int j = this.starty; j < this.starty + curr[0].length; j++) {
                if (j == 15 || i == 5) {
                    if (this.mutation > 0) {
                        this.mutation = lastmutation;
                        System.out.println("Rotate failed.");
                        return;
                    }
                } else if (this.state[i][j] != -1) {
                    this.mutation = lastmutation;
                    System.out.println("Rotate failed.");
                    return;
                }
            }
        }
    }

    /**
     * Display High Scores
     * @return void
     */
    public static void highScores() {
        StringBuilder mess = new StringBuilder("High Scores:\n");
        for (int i = 0; i < scoreList.size(); i++) {
            mess.append((i + 1)).append(". ").append(scoreList.get(i)).append("\n");
        }

        JTextArea textArea = new JTextArea(mess.toString());
        textArea.setBackground(new Color(0, 0, 0)); // Set background color to black
        textArea.setForeground(Color.GREEN); // Set text color to green
        textArea.setFont(new Font("Monospaced", Font.BOLD, 12)); // 
        textArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(textArea);

        UIManager.put("OptionPane.background", new Color(0, 0, 0)); // Set background color of the option pane to red
        UIManager.put("Panel.background", new Color(0, 0, 0)); // Set background color of the panel to red
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        JOptionPane optionPane = new JOptionPane(scrollPane, JOptionPane.PLAIN_MESSAGE);
        JDialog dialog = optionPane.createDialog("High scores");
        dialog.setVisible(true);
    }

    public void shuffleOrder(){
        int[][][][] data = PentominoDatabase.data;

        for (int i = 0; i < data.length; i++) {
            int randomPosition = this.random.nextInt(data.length);
            int[][][] temp = data[i];
            data[i] = data[randomPosition];
            data[randomPosition] = temp;
        } 
    }
}