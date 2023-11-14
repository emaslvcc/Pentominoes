/**
 * @author Department of Data Science and Knowledge Engineering (DKE)
 * @version 2022.0
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.util.Random;
import javax.swing.JOptionPane;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

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
    private int[][] currentPentomino;
    private Random random;
    private int score=0;

     
    public Game(int x, int y, int _size) {
        this.random = new Random();
        this.currentPentominoIndex = this.random.nextInt(PentominoDatabase.data.length); // Starts with the first pentomino in the database
 

        // Performs the action specified every 300 milliseconds
        this.looper = new Timer(300, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Checks if the pentomino has reached the end of the grid
                if (Game.this.starty + Game.this.currentPentomino[0].length == 15 ) {

                    Game.this.advanceToNextPentomino(); // Advances to the next pentomino in the database

                } else {
                    // Check if pentomino is going to collide with any other pentomino
                    boolean collide = false;

                    for(int i=Game.this.startx; i<Game.this.currentPentomino.length + Game.this.startx; i++){
                        for(int j=Game.this.starty; j<Game.this.currentPentomino[0].length + Game.this.starty; j++){
                            if(Game.this.currentPentomino[i-Game.this.startx][j-Game.this.starty] == 1){
                                if(j<14 && Game.this.state[i][j+1] != -1){
                                    // check if game over
                                    if((Game.this.startx==0 && Game.this.starty==0)){
                                        Game.this.looper.stop();
                                        Game.this.started = false;
                                        
                                         String gameOverMessage = 
                                        "  /$$$$$$                                                                   /$$$$$$                                                  \n" +
                                        " /$$__  $$                                                                 /$$__  $$                                                 \n" +
                                        "| $$  \\__/        /$$$$$$        /$$$$$$/$$$$         /$$$$$$             | $$  \\ $$       /$$    /$$        /$$$$$$         /$$$$$$ \n" +
                                        "| $$ /$$$$       |____  $$      | $$_  $$_  $$       /$$__  $$            | $$  | $$      |  $$  /$$/       /$$__  $$       /$$__  $$\n" +
                                        "| $$|_  $$        /$$$$$$$      | $$ \\ $$ \\ $$      | $$$$$$$$            | $$  | $$       \\  $$/$$/       | $$$$$$$$      | $$  \\__/\n" +
                                        "| $$  \\ $$       /$$__  $$      | $$ | $$ | $$      | $$_____/            | $$  | $$        \\  $$$/        | $$_____/      | $$     \n" +
                                        "|  $$$$$$/      |  $$$$$$$      | $$ | $$ | $$      |  $$$$$$$            |  $$$$$$/         \\  $/         |  $$$$$$$      | $$     \n" +
                                        " \\______/        \\_______/      |__/ |__/ |__/       \\_______/             \\______/           \\_/           \\_______/      |__/     \n" 
                                                                                                                                                                              
                                                                                                                                                                          
                                                                                                                                                                             
                                        ;
                                    
                                                                                                                                                
                                                                                                                                                                           
                                      
 
                                                                                                                                      
                                                                                                                                      
 
                                    
                                         
                                         JOptionPane optionPane = new JOptionPane(gameOverMessage, JOptionPane.PLAIN_MESSAGE);
                                         optionPane.setPreferredSize(new Dimension(1000, 880)); // Set your preferred size here

                                         JDialog dialog = optionPane.createDialog("Game Over");
                                         dialog.setVisible(true);
                                        return;

                                    } 
                                    collide = true;
                                    break;
                                }
                            }
                        }
                    }
                    if(!collide){
                        if(Game.this.starty < 14 && Game.this.state[Game.this.startx][Game.this.starty+1] == -1)
                        Game.this.starty++; // Pentomino descends one line
                    }
                    
                    else{
                    Game.this.advanceToNextPentomino(); // Advances to the next pentomino in the database
                    }
                }
                Game.this.repaint();
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

        for(int i=0 ; i<this.state.length; i++){
            for(int j=0 ; j<this.state[0].length; j++){
                if(this.state[i][j] != -1){
                    g.setColor(this.GetColorOfID(this.state[i][j]));
                    localGraphics2D.fill(new Rectangle2D.Double(i * this.size + 1, j * this.size + 1, this.size - 1, this.size - 1));
                }
            }
        }

        // Prints the current pentomino at the positions they go through
        this.currentPentomino = PentominoDatabase.data[this.currentPentominoIndex][0];
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
        // check if horizontal lines should be removed 
               for(int i=0; i<this.state[0].length; i++){
                boolean filledline = true;
                for(int j=0; j<this.state.length; j++){
                    if(this.state[j][i] == -1){
                        filledline = false;
                        break;
                    }
                }
                if(filledline){
                    this.score++;
                    System.out.println("+1 " + "score : " + this.score);
                    for(int t=0; t<5; t++){
                        this.state[t][i] = -1;
                        g.setColor(Color.lightGray);
                        localGraphics2D.fill(new Rectangle2D.Double(t * this.size + 1, i * this.size + 1, this.size - 1, this.size - 1));
                    }
                    // drop pentominos if lines where removed
                    for(int t=i; t>0; t--){
                        for(int u=0; u<5 ; u++){
                            if(this.state[u][t-1] != -1){
                                this.state[u][t] = this.state[u][t-1];
                                this.state[u][t-1] = -1;
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
        this.currentPentominoIndex++; // Move to the next pentomino in your PentominoDatabase

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
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE && this.started) {
            if(!this.pause){
                this.looper.stop();
                this.pause = true;

            }
            else if(this.pause){
                this.looper.start();
                this.pause = false;
            }
        }
        if (e.getKeyCode() == 65 && this.started) {
            if (this.moveLeft())
            this.startx--;
        }
        if (e.getKeyCode() == 68 && this.started) {
            if (this.moveRight())
            this.startx++;
        }
        if (e.getKeyCode() == 83  && this.started) {
            if (this.moveDown())
            this.starty++;
        }
        if (e.getKeyCode() == 16  && this.started) {
            this.rotate();
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
        for(int i=this.startx; i<this.startx+this.currentPentomino.length ; i++){
            for(int j=this.starty; j<this.starty+this.currentPentomino[0].length; j++){
                if(i + 1 == 5) return false;
                else{
                    if(this.state[i + 1][j] != -1) return false;
                }
            }
        }
        return true;
    }
    public boolean moveLeft(){
        for(int i=this.startx; i<this.startx+this.currentPentomino.length ; i++){
            for(int j=this.starty; j<this.starty+this.currentPentomino[0].length; j++){
                if(i - 1 == -1) return false;
                else{
                    if(this.state[i - 1][j] != -1) return false;
                }
            }
        }
        return true;
    }
    public boolean moveDown(){
        for(int i=this.startx; i<this.startx+this.currentPentomino.length ; i++){
            for(int j=this.starty; j<this.starty+this.currentPentomino[0].length; j++){
                if(j + 1 == 15) return false;
                else{
                    if(this.state[i][j+1] != -1) return false;
                }
            }
        }
        return true;
   }
   public void rotate(){
        
   }
}   
 