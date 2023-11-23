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
import java.lang.Math;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.UIManager;

/**
* This class deal with the second bot
*/
public class Bot2 extends JPanel implements KeyListener {
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
    private static ArrayList<Integer> scoreList = new ArrayList<>();

     
    public Bot2(int x, int y, int _size) {
        this.random = new Random();
        this.shuffleOrder(); // Shuffles database order
        this.currentPentominoIndex = this.random.nextInt(PentominoDatabase.data.length);
        
        // Performs the action specified every 300 milliseconds
        this.looper = new Timer(400, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if(Bot2.this.startx == 0 && Bot2.this.starty == 0) Bot2.this.bestScore();

                // Checks if the pentomino has reached the end of the grid
                if (Bot2.this.starty + Bot2.this.currentPentomino[0].length == 15 ) {

                    Bot2.this.advanceToNextPentomino(); // Advances to the next pentomino in the database

                } else {
                    // Check if pentomino is going to collide with any other pentomino
                    boolean collide = false;

                    for (int i = Bot2.this.startx; i < Bot2.this.currentPentomino.length + Bot2.this.startx; i++) {
                        for (int j = Bot2.this.starty; j < Bot2.this.currentPentomino[0].length + Bot2.this.starty; j++) {
                            if (Bot2.this.currentPentomino[i - Bot2.this.startx][j - Bot2.this.starty] == 1) {
                                if (j < 14 && Bot2.this.state[i][j+1] != -1) {
                                    // Check if game over
                                    if ((Bot2.this.startx <= 0 && Bot2.this.starty <= 0)) {
                                        Bot2.this.looper.stop();
                                        Bot2.this.started = false;
                                        scoreList.add(Bot2.this.score);
                                        Collections.sort(scoreList);
                                        Collections.reverse(scoreList);
                                        
                                        String gameOverMessage = 
                                                                                                                                                                      
                                                     
                                         
                                         " _____ ____  _      _____   ____  _     _____ ____ \n" +
                                         "/  __//  _ \\/ \\__/|/  __/  /  _ \\/ \\ |\\/  __//  __\\\n" +
                                         "| |  _| / \\|| |\\/|||  \\    | / \\|| | //|  \\  |  \\/|\n" +
                                         "| |_//| |-||| |  |||  /_   | \\_/|| \\// |  /_ |    /\n" +
                                         "\\____\\_/ \\|\\_/  \\|\\____\\  \\____/\\__/  \\____\\/_/\\_\\";
                                     
                                    
                                                                                                                                                
                                                                                                                                                                           
                                      
 
                                                                                                                                      
                                                                                                                                      
 
                                    
                            // Create a JLabel with HTML formatting
                            JLabel label = new JLabel("<html><pre>" + gameOverMessage + "</pre></html>");
                            label.setForeground(Color.WHITE); // Set text color to white

                            // Customize JOptionPane with a green background
                            UIManager.put("OptionPane.background", new Color(145, 0, 12)); // Green background
                            UIManager.put("Panel.background", new Color(145, 0, 12)); // Green background

                            // Create and show JOptionPane
                            JOptionPane optionPane = new JOptionPane(label, JOptionPane.PLAIN_MESSAGE);
                            optionPane.setPreferredSize(new Dimension(400, 180));

                            JDialog dialog = optionPane.createDialog("Game Over");
                            dialog.setVisible(true);

                            // Reset the game or perform other actions as needed
                            Bot2.this.reset();
                            return;

                                    } 
                                    collide = true;
                                    break;
                                }
                            }
                        }
                    }
                    if(!collide){
                        if(Bot2.this.starty < 14 && Bot2.this.state[Bot2.this.startx][Bot2.this.starty+1] == -1)
                        Bot2.this.starty++; // Pentomino descends one line
                    }
                    
                    else{
                        Bot2.this.advanceToNextPentomino(); // Advances to the next pentomino in the database
                    }
                }
                if(Bot2.this.started) Bot2.this.repaint();
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
    * This function is called BY THE SYSTEM if required for a new frame, uses the state stored by the UI class.
    * @param g 
    * @return void
    */
    public void paintComponent(Graphics g) {
        Graphics2D localGraphics2D = (Graphics2D) g;

        if(!this.started){

            
            localGraphics2D.setColor(Color.black);
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
            "      '               '    )/         '               '     '      '       '    '      ";
    

            localGraphics2D.setColor(Color.white);
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
        }

        else {
            localGraphics2D.setColor(Color.lightGray);
            localGraphics2D.fill(this.getVisibleRect());

            // Paints the Tetris grid
            localGraphics2D.setColor(Color.BLACK);
            for (int i = 0; i <= this.state.length; i++) {
                localGraphics2D.drawLine(i * this.size, 0, i * this.size, this.state[0].length * this.size);
            }
            for (int i = 0; i <= this.state[0].length; i++) {
                localGraphics2D.drawLine(0, i * this.size, this.state.length * this.size, i * this.size);
            } 
        
            localGraphics2D.drawString("NEXT PENTOMINO", 450, 200);
            localGraphics2D.drawString("Current score :  " + this.score, 450, 500);
        
            for(int i=0 ; i<this.state.length; i++){
                for(int j=0 ; j<this.state[0].length; j++){
                    if(this.state[i][j] != -1){
                        g.setColor(this.GetColorOfID(this.state[i][j]));
                        localGraphics2D.fill(new Rectangle2D.Double(i * this.size + 1, j * this.size + 1, this.size - 1, this.size - 1));
                    }
                }
            }

            // Prints the current pentomino at the positions they go through
            this.currentPentomino = PentominoDatabase.data[this.currentPentominoIndex][this.mutation];

            for (int i = 0; i < this.currentPentomino.length; i++) {
                for (int j = 0; j < this.currentPentomino[0].length; j++) {
                    if (this.currentPentomino[i][j] == 1) {
                        if(this.state[i+this.startx][j+this.starty] != -1){
                            this.starty--;
                            i=100;
                            j=100;
                            break;
                        }                                     
                    }
                }
            }

            for (int i = 0; i < this.currentPentomino.length; i++) {
                for (int j = 0; j < this.currentPentomino[0].length; j++) {
                    if (this.currentPentomino[i][j] == 1) {
                        if(this.started){
                        g.setColor(this.GetColorOfID(this.currentPentominoIndex));
                        localGraphics2D.fill(new Rectangle2D.Double(i * this.size + this.startx * this.size + 1, j * this.size + this.starty * this.size + 1, this.size - 1, this.size - 1));
                        }                                     
                    }
                }
            }

            this.nextIndex = this.currentPentominoIndex+1;
            if(this.nextIndex == PentominoDatabase.data.length) this.nextIndex = 0;
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
                        if(this.started){
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
                    for(int t = 0; t < 5; t++) {
                        this.state[t][i] = -1;
                        g.setColor(Color.lightGray);
                        localGraphics2D.fill(new Rectangle2D.Double(t * this.size + 1, i * this.size + 1, this.size - 1, this.size - 1));
                    }
                    // Drop pentominos if lines where removed
                    for (int t = i; t > 0; t--) {
                        for (int u = 0; u < 5 ; u++) {
                            if (this.state[u][t-1] != -1) {
                                this.state[u][t] = this.state[u][t-1];
                                this.state[u][t-1] = -1;
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
        //Aadd pentomino to state
        for (int i = this.startx; i < this.currentPentomino.length + this.startx; i++) {
            for (int j = this.starty; j < this.currentPentomino[0].length + this.starty; j++) {
                if (this.currentPentomino[i-this.startx][j-this.starty] == 1) {
                    this.state[i][j] = this.currentPentominoIndex;
                } 
            }
        }
        this.currentPentominoIndex++; // Move to the next pentomino in your PentominoDatabase
        this.mutation = 0;

        // Reposition next pentomino at the beginning of the grid
        Bot2.this.startx = 0;
        Bot2.this.starty = 0;

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
        else if (i == 8) {return new Color(0, 0, 0);}
        else if (i == 9) {return new Color(0, 0, 100);}
        else if (i == 10) {return new Color(100, 0,0);}
        else if (i == 11) {return new Color(0, 100, 0);}
        else {return Color.LIGHT_GRAY;}
    }

    public void reset() {
        
        for(int i=0; i < this.state.length ; i++){
            for(int j=0; j < this.state[0].length ; j++)
                this.state[i][j] = -1;
                this.looper.stop();
        }
        this.startx = 0;
        this.starty = 0;
        this.currentPentominoIndex = this.random.nextInt(PentominoDatabase.data.length);
        this.started = false;
        this.score = 0;

        this.repaint();
    }

    public void start() {
        this.started = true;
        this.looper.start();
        this.repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(this.startx < 0 || this.starty<0) return;
        if (e.getKeyCode() == 80) {
            if(!this.pause){
                this.looper.stop();
                this.pause = true;

            }
            else if(this.pause){
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

    public boolean moveRight(){
        for (int i = this.startx; i < this.startx + this.currentPentomino.length ; i++) {
            for (int j = this.starty; j < this.starty + this.currentPentomino[0].length; j++) {
                if (i + 1 == 5) return false;
                else {
                    if (this.state[i + 1][j] != -1) return false;
                }
            }
        }
        return true;
    }

    public boolean moveLeft(){
        for (int i = this.startx; i < this.startx + this.currentPentomino.length ; i++) {
            for (int j = this.starty; j < this.starty + this.currentPentomino[0].length; j++) {
                if (i - 1 == -1) return false;
                else {
                    if(this.state[i - 1][j] != -1) return false;
                }
            }
        }
        return true;
    }

    public boolean moveDown(){
        for (int i = this.startx; i < this.startx + this.currentPentomino.length; i++) {
            for (int j = this.starty; j < this.starty + this.currentPentomino[0].length; j++) {
                if (j + 1 == 15) return false;
                else{
                    if(this.state[i][j+1] != -1 && this.currentPentomino[i-this.startx][j-this.starty] == 1) { 
                        return false;
                    } 
                }
            }
        }
        return true;
    }

    public void rotate(){
        int lastmutation = this.mutation;

        this.mutation++;
        if (this.mutation >= PentominoDatabase.data[this.currentPentominoIndex].length) this.mutation = 0;
        int[][] curr = PentominoDatabase.data[this.currentPentominoIndex][this.mutation];

            for (int i = this.startx; i < this.startx + curr.length ; i++) {
                for (int j = this.starty; j < this.starty + curr[0].length; j++) {
                    if (j == 15 || i==5) {
                        if (this.mutation > 0)
                            this.mutation = lastmutation;
                        System.out.println("rotate failed");
                        return;
                    } 
                    else if (this.state[i][j] != -1){
                            this.mutation = lastmutation;
                        System.out.println("rotate failed");
                        return;
                    }
                }
            }
    }

   public static void highScores(){
       String mess = "High Scores: \n";
       for(int i = 0; i < scoreList.size(); i++) mess += ((i+1) + ". " + scoreList.get(i) + "\n");
       JOptionPane optionPane = new JOptionPane(mess, JOptionPane.PLAIN_MESSAGE);
       optionPane.setPreferredSize(new Dimension(700, 380)); // Set your preferred size here
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

    public void bestScore() {

        int[][] testState = new int[5][15];
        double[] currentProperties = new double[4];
        double score = 0;
        double bestScore = 100000;
        int mutation = 0;
        int x = 0;
        int y = 0;

        while (true) {
            this.currentPentomino = PentominoDatabase.data[this.currentPentominoIndex][this.mutation];
             
            for (int i = 0; i < this.state.length; i++) {
                for (int j = 0;j < this.state[0].length;j++) {
                    testState[i][j] = this.state[i][j];
                }
            }

            while (this.moveDown()) {    
                this.starty++;
            }
            
            // Places a possible option in test state
            for (int i = this.starty; i < this.starty + this.currentPentomino[0].length; i++) {
                for (int j = this.startx; j < this.startx + this.currentPentomino.length; j++) {
                    if (this.currentPentomino[j-this.startx][i-this.starty] == 1) {
                        testState[j][i] = this.currentPentominoIndex;
                    }
                }
            }
            
            currentProperties[0] = gapCount(testState);
            currentProperties[1] = averageHeight(testState);
            currentProperties[2] = columnHeightDifference(testState);
            currentProperties[3] = consecutiveHeightDifference(testState);

            score = calculateScore(currentProperties);
            if (score < bestScore) {
                bestScore = score;
                mutation = this.mutation;
                x = this.startx;
                y = this.starty;
            }
            
            score = 0;
            this.starty = 0;
            if (!this.moveRight()) {
                this.startx = 0;
                this.mutation++;
                if (this.mutation >= PentominoDatabase.data[this.currentPentominoIndex].length) {
                    this.mutation = 0;
                    break;
                }
            }
            else{
                this.startx++;
                this.starty = 0;
            }
            
        }
        this.startx = x;
        this.starty = y;
        this.mutation = mutation;
        this.currentPentomino = PentominoDatabase.data[this.currentPentominoIndex][this.mutation];
    }

    public int gapCount(int[][] testState) {
        
        // Iterate over columns
        int gapCount = 0;
        for (int col = 0; col < testState[0].length; col++) {
            
            boolean foundNonEmptyCell = false;
            for (int row = 0; row < testState.length; row++) {
                if (testState[row][col] == 1) foundNonEmptyCell = true;
                else if (testState[row][col] == 0 && foundNonEmptyCell) gapCount++;
            }
        }
        return gapCount;
    }

    public double averageHeight(int[][] testState) {

        int totalHeight = 0;
        for (int col = 0; col < testState[0].length; col++) {
            for (int row = 0; row < testState.length; row++) {
                if (testState[row][col] == 1) totalHeight += (15 - row);
            }
        }
        return totalHeight / 5;
    }

    public int columnHeightDifference(int[][] testState) {
        
        int maxHeight = 0;
        int minHeight = 0;

        for (int col = 0; col < testState[0].length; col++) {
            for (int row = 0; row < testState.length; row++) {
                if (testState[row][col] == 1) {
                    if (15 - row > maxHeight) maxHeight = 15 - row;
                    else if (15 - row < minHeight) minHeight = 15 - row;
                }
            }
        }
        return maxHeight - minHeight;
    }

    public int consecutiveHeightDifference(int [][] testState) {
        
        int height1 = 0;
        int height2 = 0;
        int height3 = 0;
        int height4 = 0;
        int height5 = 0;

        for (int row = 0; row < testState.length; row++) {
            if (testState[row][0] == 1) height1 = 15 - row;
            else if (testState[row][1] == 1) height2 = 15 - row;
            else if (testState[row][2] == 1) height3 = 15 - row;
            else if (testState[row][3] == 1) height4 = 15 - row;
            else if (testState[row][4] == 1) height5 = 15 - row;
        }

        int heightDiff1 = Math.abs(height2 - height1);
        int heightDiff2 = Math.abs(height3 - height2);
        int heightDiff3 = Math.abs(height4 - height3);
        int heightDiff4 = Math.abs(height5 - height4);

        int max = 0;
        if (heightDiff1 > max) max = heightDiff1;
        if (heightDiff2 > max) max = heightDiff2;
        if (heightDiff3 > max) max = heightDiff3;
        if (heightDiff4 > max) max = heightDiff4;

        return max;
    }

    public double calculateScore(double[] array) {
        double score = array[0] * 0.15 + array[1] * 0.25 + array[2] * 0.45 + array[3] * 0.15;
        return score;
    }
}