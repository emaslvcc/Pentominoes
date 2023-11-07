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

import javax.swing.JPanel;
import javax.swing.Timer;

/**
* This class takes care of all the graphics to display a certain state.
*/
public class Game extends JPanel implements KeyListener {
    private int[][] state;
    private int size;
    private int[][] start;
    private Timer looper;
    private int startx;
    private int starty;
    private int[][] current;
    private int currentPentominoIndex;
     
    public Game(int x, int y, int _size) {
        currentPentominoIndex = 0;
        this.looper = new Timer(300, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Game.this.starty + PentominoDatabase.data[currentPentominoIndex][1].length == 15) {
                    Game.this.starty = 0;
                    advanceToNextPentomino();
                } else {
                    Game.this.starty++;
                }
                Game.this.repaint();
            }
        });
        //this.looper.start();
        
        this.size = _size;
        this.setPreferredSize(new Dimension(x * this.size, y * this.size));
        this.setFocusable(true);
        this.addKeyListener(this);

        this.state = new int[x][y];
        this.start = new int[x][y];
        for (int i = 0; i < this.state.length; i++) {
            for (int j = 0; j < this.state[i].length; j++) {
                this.state[i][j] = -1;
                this.start[i][j] = -1;
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
 
        //draw lines
        localGraphics2D.setColor(Color.BLACK);
        for (int i = 0; i <= this.state.length; i++) {
            localGraphics2D.drawLine(i * this.size, 0, i * this.size, this.state[0].length * this.size);
        }
        for (int i = 0; i <= this.state[0].length; i++) {
            localGraphics2D.drawLine(0, i * this.size, this.state.length * this.size, i * this.size);
        }
        
        int[][] currentPentomino = PentominoDatabase.data[currentPentominoIndex][0];
        for (int i = 0; i < currentPentomino.length; i++) {
            for (int j = 0; j < currentPentomino[0].length; j++) {
                if (currentPentomino[i][j] == 1) {
                    g.setColor(this.GetColorOfID(currentPentomino[i][j]));
                    g.fillRect(i * this.size + this.startx * this.size, j * this.size + this.starty * this.size , this.size, this.size);
                }
            }
        }
        g.dispose();
    }

    /**
     * Advances to the following pentomino in the database
     * @return void
     */
    public void advanceToNextPentomino() {
        currentPentominoIndex++; // Move to the next pentomino in your PentominoDatabase
        // Optionally, you can check if you have reached the end of the pentominoes and reset the index to 0.
        if (currentPentominoIndex >= PentominoDatabase.data.length) {
            currentPentominoIndex = 0;
        }
        // Reset the starting position for the new pentomino
        startx = 0; // You may want to adjust this based on your game logic
        starty = 0; // You may want to adjust this based on your game logic
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
 
    /**
    * This function should be called to update the displayed state (makes a copy)
    * @param _state information about the new state of the GUI
    */
    public void setState(int[][] _state) {
        for (int i = 0; i < this.state.length; i++)
        {
            for (int j = 0; j < this.state[i].length; j++)
            {
                this.state[i][j] = _state[i][j];
            }
        }
        // Tells the system a frame update is required
        this.repaint();
    }

    public void reset() {
        // not implemented yet
        this.repaint();
    }

    public void start() {
        this.looper.start();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            
        }
        if (e.getKeyCode() == 65) {
            if (this.startx >= 1)
            this.startx--;
        }
        if (e.getKeyCode() == 68 ) {
            if (this.startx + this.current.length <= 4)
            this.startx++;
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
}
 