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

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.UIManager;

/**
 * This class implementsthe game play for BestOrderBot
 */
public class BestOrderBot extends JPanel implements KeyListener {
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
    private Random random;
    private int score = 0;
    private int destx=0;
    private int desty=0;
    private int destmut=0;
    private int[] pentominoOrder = {5, 2, 10, 11, 1, 9, 6, 3, 7, 8, 0, 4};
    private int[][] spots = {{2,12,2},{1,12,0},{0,11,3},{2,12,5},{0,10,1},{1,12,3},{2,12,3},{2,12,2},{1,11,3},{0,11,3},{2,12,0},{2,12,0}}; // x y mut
    private static ArrayList<Integer> scoreList = new ArrayList<>();

    public BestOrderBot(int x, int y, int _size) {
        this.currentPentominoIndex = this.pentominoOrder[0]; // Picks the first pentomino in the chosen ordering
        this.nextIndex = 1;


        this.looper = new Timer(400, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(BestOrderBot.this.startx == 0 && BestOrderBot.this.starty == 0) BestOrderBot.this.placePentomino();

                // Checks if the pentomino has reached the end of the grid
                if (BestOrderBot.this.starty + BestOrderBot.this.currentPentomino[0].length == 15) {
                    BestOrderBot.this.advanceToNextPentomino(); // Advances to the next pentomino in the database
                } else {
                    // Check if pentomino is going to collide with any other pentomino
                    boolean collide = false;
                    for (int i = BestOrderBot.this.startx; i < BestOrderBot.this.currentPentomino.length + BestOrderBot.this.startx; i++) {
                        for (int j = BestOrderBot.this.starty; j < BestOrderBot.this.currentPentomino[0].length + BestOrderBot.this.starty; j++) {
                            if (BestOrderBot.this.currentPentomino[i - BestOrderBot.this.startx][j - BestOrderBot.this.starty] == 1) {
                                if (j < 14 && BestOrderBot.this.state[i][j + 1] != -1) {
                                    
                                    // Check if game is over
                                    if ((BestOrderBot.this.startx <= 0 && BestOrderBot.this.starty <= 0)) {
                                        BestOrderBot.this.looper.stop();
                                        BestOrderBot.this.started = false;
                                        scoreList.add(BestOrderBot.this.score);
                                        Collections.sort(scoreList);
                                        Collections.reverse(scoreList);
                                        
                                        String gameOverMessage = 
                                                                                                                                                                      
                                                     
                                         
                                        "   _____          __  __ ______    ______      ________ _____  \n" +
                                        "  / ____|   /\\   |  \\/  |  ____|  / __ \\ \\    / /  ____|  __ \\ \n" +
                                        " | | |_ | / /\\ \\ | |\\/| |  __|   | |  | |\\ \\/ / |  __| |  _  / \n" +
                                        " | |__| |/ ____ \\| |  | | |____  | |__| | \\  /  | |____| | \\ \\ \n" +
                                        "  \\_____/_/    \\_\\_|  |_|______|  \\____/   \\/   |______|_|  \\_\\";
                                     
                                    
                                                                                                                                                
                                                                                                                                                                           
                                      
 
                                                                                                                                      
                                                                                                                                      
 
                                    
                                        // Create a JLabel with HTML formatting for a Game Over message
                                        JLabel label = new JLabel("<html><pre>" + gameOverMessage + "</pre></html>");
                                        label.setForeground(Color.GREEN); // Set text color to green

                                        // Customize JOptionPane with a green background
                                        UIManager.put("OptionPane.background", Color.BLACK); // Set background color of the option pane to black
                                        UIManager.put("Panel.background", Color.BLACK); // Set background color of the panel to black

                                        // Create and show JOptionPane and JDialog
                                        JOptionPane optionPane = new JOptionPane(label, JOptionPane.PLAIN_MESSAGE);
                                        optionPane.setPreferredSize(new Dimension(550, 220));
                                        JDialog dialog = optionPane.createDialog("Game Over");
                                        dialog.setVisible(true);

                                        // Reset the game or perform other actions as needed
                                        BestOrderBot.this.reset();
                                        return;
                                    } 
                                    collide = true;
                                    break;
                                }
                            }
                        }
                    }
                    if (!collide) {
                        if (BestOrderBot.this.mutation != BestOrderBot.this.destmut) BestOrderBot.this.mutation = BestOrderBot.this.destmut;
                        if (BestOrderBot.this.startx != BestOrderBot.this.destx) BestOrderBot.this.startx++;
                        if (BestOrderBot.this.starty < 14 && BestOrderBot.this.state[BestOrderBot.this.startx][BestOrderBot.this.starty + 1] == -1) BestOrderBot.this.starty++; // Pentomino descends one line
                        if (BestOrderBot.this.starty < BestOrderBot.this.desty && BestOrderBot.this.mutation == BestOrderBot.this.destmut && BestOrderBot.this.startx == BestOrderBot.this.destx) BestOrderBot.this.starty++;
                    } else BestOrderBot.this.advanceToNextPentomino(); // Advances to the next pentomino in the database
                }
                if(BestOrderBot.this.started) BestOrderBot.this.repaint();
            }
        });
        this.size = _size;
        this.setPreferredSize(new Dimension(x * this.size, y * this.size));
        this.setFocusable(true);
        this.addKeyListener(this);

        this.state = new int[x][y];
        for (int i = 0; i < this.state.length; i++) {
            for (int j = 0; j < this.state[i].length; j++) {
                this.state[i][j] = -1;
            }
        }
    }

    /**
    * This function is called by the system if required for a new frame
    * @param g 
    * @return void
    */
    public void paintComponent(Graphics g) {
        Graphics2D localGraphics2D = (Graphics2D) g;

        if (!this.started) {
            localGraphics2D.setColor(Color.BLACK);
            localGraphics2D.fill(this.getVisibleRect());

            // ASCII art for "START SCREEN"
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
    

            localGraphics2D.setColor(Color.GREEN);
            Font font = new Font("Monospaced", Font.PLAIN, 12);
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
        
            localGraphics2D.drawString("NEXT PENTOMINO", 450, 200);
            localGraphics2D.drawString("Current score :  " + this.score, 450, 500);
        
            for (int i = 0; i < this.state.length; i++) {
                for (int j = 0; j < this.state[0].length; j++) {
                    if (this.state[i][j] != -1) {
                        g.setColor(this.GetColorOfID(this.state[i][j]));
                        localGraphics2D.fill(new Rectangle2D.Double(i * this.size + 1, j * this.size + 1, this.size - 1, this.size - 1));
                    }
                }
            }

            // Prints the current pentomino at the positions they go through
            this.currentPentomino = BestOrderDatabase.data[this.currentPentominoIndex][this.mutation];

            // extra check if a pentomino won't collide, if it collides put pentomino one step back upwards
            for (int i = 0; i < this.currentPentomino.length; i++) {
                for (int j = 0; j < this.currentPentomino[0].length; j++) {
                    if (this.currentPentomino[i][j] == 1) {
                        if(this.state[i + this.startx][j + this.starty] != -1){
                            this.starty--;
                            i = 100;
                            j = 100;
                            break;
                        }                                     
                    }
                }
            }

            for (int i = 0; i < this.currentPentomino.length; i++) {
                for (int j = 0; j < this.currentPentomino[0].length; j++) {
                    if (this.currentPentomino[i][j] == 1) {
                        if  (this.started)  {
                            g.setColor(this.GetColorOfID(this.currentPentominoIndex));
                            localGraphics2D.fill(new Rectangle2D.Double(i * this.size + this.startx * this.size + 1, j * this.size + this.starty * this.size + 1, this.size - 1, this.size - 1));
                        }                                     
                    }
                }
            }

            if  (this.nextIndex == this.pentominoOrder.length) this.nextIndex = 0;
            this.nextPentomino = BestOrderDatabase.data[this.pentominoOrder[this.nextIndex]][0];

            // Paint next grid
            localGraphics2D.setColor(Color.BLACK);
            for (int i = 0; i <= this.nextPentomino.length; i++) {
                localGraphics2D.drawLine((i * this.size) + 450 , 220, (i * this.size) + 450, (this.nextPentomino[0].length * this.size) + 220);   
            }
            for (int i = 0; i <= this.nextPentomino[0].length; i++) {
                localGraphics2D.drawLine(450, (i * this.size)+220, (this.nextPentomino.length * this.size) + 450, (i * this.size) + 220);
            } 

            // Calculate next index
            this.nextPentomino = BestOrderDatabase.data[this.pentominoOrder[this.nextIndex]][0];

            // Paint next pentomino
            for (int i = 0; i < this.nextPentomino.length; i++) {
                for (int j = 0; j < this.nextPentomino[0].length; j++) {
                    if (this.nextPentomino[i][j] == 1) {
                        if (this.started) {
                            g.setColor(this.GetColorOfID(this.pentominoOrder[this.nextIndex])); // Use pentominoOrder[this.nextIndex]
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
                        for (int u = 0; u < 5; u++) {
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
        // add pentomino to state
        for(int i=this.startx; i<this.currentPentomino.length+this.startx; i++){
            for(int j=this.starty; j<this.currentPentomino[0].length+this.starty; j++){
                if(this.currentPentomino[i-this.startx][j-this.starty] == 1){
                    this.state[i][j] = this.currentPentominoIndex;
                } 
            }
        }
        this.currentPentominoIndex = this.pentominoOrder[this.nextIndex];
        this.nextIndex++;
        this.mutation = 0;

        // Reposition next pentomino at the beginning of the grid
        BestOrderBot.this.startx = 0;
        BestOrderBot.this.starty = 0;
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
        else {return Color.BLACK;}
    }

    /**
     * Resets the game
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
        this.currentPentominoIndex = this.pentominoOrder[0];
        this.nextIndex = 1;
        this.mutation = 0;
        this.started = false;
        this.score = 0;
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
     * Handles keyvboard input "P" (pause)
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (this.startx < 0 || this.starty < 0) return;
        if (e.getKeyCode() == 80) {
            if (!this.pause) {
                this.looper.stop();
                this.pause = true;
            } else if (this.pause) {
                this.looper.start();
                this.pause = false;
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyReleased(KeyEvent e) {
         //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Checks if the pentomino can move to the right
     * @return if the pentomino can move to the right (true) or if it cannot (false)
     */
    public boolean moveRight(){
        for (int i = this.startx; i < this.startx + this.currentPentomino.length; i++) {
            for (int j = this.starty; j < this.starty + this.currentPentomino[0].length; j++) {
                if (i + 1 == 5) return false;
                else {
                    if(this.state[i + 1][j] != -1) return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks if the pentomino can move to the left
     * @return if the pentomino can move to the left (true) or if it cannot (false)
     */
    public boolean moveLeft() {
        for (int i = this.startx; i < this.startx + this.currentPentomino.length; i++) {
            for (int j = this.starty; j < this.starty + this.currentPentomino[0].length; j++) {
                if (i - 1 == -1) return false;
                else {
                    if(this.state[i - 1][j] != -1) return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks if the pentomino can move downwards
     * @return if the pentomino can move downwards (true) or if it cannot (false)
     */
    public boolean moveDown() {
        for (int i = this.startx; i < this.startx + this.currentPentomino.length; i++) {
            for (int j = this.starty; j < this.starty + this.currentPentomino[0].length; j++) {
                if (j + 1 == 15) return false;
                else {
                    if (this.state[i][j + 1] != -1 && this.currentPentomino[i - this.startx][j - this.starty] == 1) { 
                        return false;
                    } 
                }
            }
        }
        return true;
    }

    /**
     * Rotates pentomino
     * @return void
     */
    public void rotate(){
        int lastmutation = this.mutation;
        this.mutation++;
        if(this.mutation >= BestOrderDatabase.data[this.currentPentominoIndex].length) this.mutation = 0;
        int[][] curr = BestOrderDatabase.data[this.currentPentominoIndex][this.mutation];

        for (int i = this.startx; i < this.startx + curr.length; i++) {
            for (int j = this.starty; j < this.starty + curr[0].length; j++) {
                if (j == 15 || i == 5) {
                    if (this.mutation > 0) {
                        this.mutation = lastmutation ;
                        System.out.println("rotate failed");
                        return;
                    }
                } else if(this.state[i][j] != -1) {
                    this.mutation = lastmutation;
                    System.out.println("rotate failed");
                    return;
                }
            }
        }
    }

    /**
     * Display High Scores
     * @return High Score string 
     */
    public static String highScores(){
        String mess = "High Scores: \n";
        for (int i = 0; i < scoreList.size(); i++) {
            mess += ((i + 1) + ". " + scoreList.get(i) + "\n");
        }
        return mess;
    }

    /**
     * Places the pentomino at its designated position
     * @return void
     */
    public void placePentomino() {
        int curr = this.nextIndex - 1;
        if(curr == -1) curr = 11;
        this.destx = this.spots[curr][0];
        this.desty = this.spots[curr][1];
        this.destmut = this.spots[curr][2];
    }
}