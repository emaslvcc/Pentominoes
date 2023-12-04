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
 * This class takes care of all the graphics to display a certain state.
 */
public class Game3 extends JPanel implements KeyListener {
    private int[][] state;
    private int size;
    private Timer looper;
    private boolean started = false;
    private int startx;
    private boolean pause = false;
    private int starty;
    private int currentPentominoIndex;
    private int mutation = 0;
    private int nextMutation = 0;
    private int[][] currentPentomino;
    private int[][] nextPentomino;
    private int nextIndex;
    private Random random;
    private int score = 0;
    private int destx=0;
    private int desty=0;
    private int destmut=0;
    private static ArrayList<Integer> scoreList = new ArrayList<>();


    public Game3(int x, int y, int _size) {
        this.random = new Random();
        this.shuffleOrder(); // shuffle order of database
        this.currentPentominoIndex = this.random.nextInt(PentominoDatabase.data.length); // Starts with the first pentomino in the database


        // Performs the action specified every 300 milliseconds
        this.looper = new Timer(500, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if(Game3.this.startx == 0 && Game3.this.starty == 0) Game3.this.CheckBestOption();
                // Checks if the pentomino has reached the end of the grid
                if (Game3.this.starty + Game3.this.currentPentomino[0].length == 15 ) {

                    Game3.this.advanceToNextPentomino(); // Advances to the next pentomino in the database

                } else {
                    // Check if pentomino is going to collide with any other pentomino
                    boolean collide = false;

                    for(int i=Game3.this.startx; i<Game3.this.currentPentomino.length + Game3.this.startx; i++){
                        for(int j=Game3.this.starty; j<Game3.this.currentPentomino[0].length + Game3.this.starty; j++){
                            if(Game3.this.currentPentomino[i-Game3.this.startx][j-Game3.this.starty] == 1){
                                if(j<14 && Game3.this.state[i][j+1] != -1){
                                    // check if game over
                                    if((Game3.this.startx<=0 && Game3.this.starty<=0)){
                                        Game3.this.looper.stop();
                                        Game3.this.started = false;
                                        scoreList.add(Game3.this.score);
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
                                        Game3.this.reset();
                                        return;

                                    }
                                    collide = true;
                                    break;
                                }
                            }
                        }
                    }
                    if(!collide){
                        if(Game3.this.startx != Game3.this.destx) Game3.this.startx++;
                        if(Game3.this.mutation != Game3.this.destmut) Game3.this.rotate();


                        if(Game3.this.starty < 14 && Game3.this.state[Game3.this.startx][Game3.this.starty+1] == -1)
                            Game3.this.starty++; // Pentomino descends one line

                        if(Game3.this.starty < Game3.this.desty && Game3.this.mutation==Game3.this.destmut && Game3.this.startx==Game3.this.destx) Game3.this.starty++;
                    }

                    else{
                        Game3.this.advanceToNextPentomino(); // Advances to the next pentomino in the database
                    }
                }
                if(Game3.this.started) Game3.this.repaint();
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
        else{

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

            // this.pen2 = PentominoDatabase.data[(nextIndex + 1) % PentominoDatabase.data.length][0];
            // this.pen3 = PentominoDatabase.data[(nextIndex + 2) % PentominoDatabase.data.length][0];



            // Paint next grid
            localGraphics2D.setColor(Color.BLACK);
            for (int i = 0; i <= this.nextPentomino.length; i++) {
                localGraphics2D.drawLine((i * this.size) + 450 , 220, (i * this.size) + 450, (this.nextPentomino[0].length * this.size) + 220);

            }
            for (int i = 0; i <= this.nextPentomino[0].length; i++) {
                localGraphics2D.drawLine(450, (i * this.size)+220, (this.nextPentomino.length * this.size) + 450, (i * this.size) + 220);
            }


            // paint next pentomino
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
        this.mutation = 0;
        this.nextMutation = 0;

        // Reposition next pentomino at the beginning of the grid
        Game3.this.startx = 0;
        Game3.this.starty = 0;

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
        this.nextIndex = this.currentPentominoIndex+1;
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
                    if(this.state[i][j+1] != -1 && this.currentPentomino[i-this.startx][j-this.starty] == 1){
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
        if(this.mutation >= PentominoDatabase.data[this.currentPentominoIndex].length) this.mutation = 0;
        int[][] curr = PentominoDatabase.data[this.currentPentominoIndex][this.mutation];


        for(int i=this.startx; i<this.startx+curr.length ; i++){
            for(int j=this.starty; j<this.starty+curr[0].length; j++){
                if( j == 15 || i==5 ){
                    if(this.mutation>0)
                        this.mutation = lastmutation ;
                    System.out.println("rotate failed");
                    return;
                }
                else if(this.state[i][j] != -1){
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

    public void CheckBestOption(){
        double max = 0;
        double curr = 0;
        int mut = 0;
        int x = 0;
        int y = 0;
        int[][] teststate = new int[5][15];

        while(true){
            this.currentPentomino = PentominoDatabase.data[this.currentPentominoIndex][this.mutation];

            for(int i=0;i<this.state.length;i++){
                for(int j=0;j<this.state[0].length;j++){
                    teststate[i][j] = this.state[i][j];
                }
            }
            while(this.moveDown()){
                this.starty++;
            }
            // erase possible full row!
            for(int i=0; i<teststate[0].length; i++){
                boolean filledline = true;
                for(int j=0; j<teststate.length; j++){
                    if(teststate[j][i] == -1){
                        filledline = false;
                        break;
                    }
                }
                if(filledline){
                    for(int t=0; t<5; t++){
                        teststate[t][i] = -1;
                    }
                    // drop pentominos if lines where removed
                    for(int t=i; t>0; t--){
                        for(int u=0; u<5 ; u++){
                            if(teststate[u][t-1] != -1){
                                teststate[u][t] = teststate[u][t-1];
                                teststate[u][t-1] = -1;
                            }
                        }
                    }
                }
            }

            // places a possible option in teststate
            for(int i=this.starty; i<this.starty + this.currentPentomino[0].length; i++){
                for(int j=this.startx; j<this.startx + this.currentPentomino.length; j++){
                    if(this.currentPentomino[j-this.startx][i-this.starty] == 1){
                        teststate[j][i] = this.currentPentominoIndex;
                    }
                }
            }
            // counts the value of this state
            curr = this.EvaluateNextThreePentominoes(teststate);

            // if the value is higher than max, safe the mutation and update max
            if(curr > max){
                max = curr;
                mut = this.mutation;
                x = this.startx;
                y = this.starty;
            }

            curr = 0;
            this.starty = 0;
            if(!this.moveRight()){
                this.startx = 0;
                this.mutation++;
                if(this.mutation >= PentominoDatabase.data[this.currentPentominoIndex].length){
                    break;
                }
            }
            else{
                this.startx++;
                this.starty=0;
            }
        }
        this.destx = x;
        this.desty = y;
        this.destmut = mut;

        
    }

    public double EvaluateNextThreePentominoes(int[][] teststate) {
        double nextMax = 0;
        double nextCurr = 0;
        int x = 0;
        int y = 0;
        int mutation = 0;
        int index = (this.currentPentominoIndex + 1) % PentominoDatabase.data.length;

        int[][] nextteststate = new int[5][15];

        for (int i = 0; i < 3; i++) {
            this.nextPentomino = PentominoDatabase.data[index][mutation];

            for (int j = 0; j < teststate.length; j++) {
                for (int k = 0; k < teststate[0].length; k++) {
                    nextteststate[j][k] = teststate[j][k];
                }
            }
            while (this.nextMoveDown(teststate, x, y)) {
                y++;
            }
            // erase possible full row!
            for (int j = 0; j < nextteststate[0].length; j++) {
                boolean filledline = true;
                for (int k = 0; k < nextteststate.length; k++) {
                    if (nextteststate[k][j] == -1) {
                        filledline = false;
                        break;
                    }
                }
                if (filledline) {
                    for (int t = 0; t < 5; t++) {
                        nextteststate[t][j] = -1;
                    }
                    // drop pentominos if lines where removed
                    for (int t = j; t > 0; t--) {
                        for (int u = 0; u < 5; u++) {
                            if (nextteststate[u][t - 1] != -1) {
                                nextteststate[u][t] = nextteststate[u][t - 1];
                                nextteststate[u][t - 1] = -1;
                            }
                        }
                    }
                }
            }

            // places a possible option in nextteststate
            for (int j = y; j < y + this.nextPentomino[0].length; j++) {
                for (int k = x; k < x + this.nextPentomino.length; k++) {
                    if (this.nextPentomino[k - x][j - y] == 1) {
                        nextteststate[k][j] = index;
                    }
                }
            }
            // counts the value of this state
            for (int j = 0; j < nextteststate[0].length; j++) {
                int rowval = 0;
                for (int k = 0; k < nextteststate.length; k++) {
                    if (nextteststate[k][j] == -1) {
                        rowval++;
                    }
                }
                nextCurr += rowval * rowval;
            }

            // if the value is higher than max, safe the mutation and update max
            if (nextCurr > nextMax) {
                nextMax = nextCurr;
            }

            nextCurr = 0;
            y = 0;
            if (!this.nextMoveRight(teststate, x, y)) {
                x = 0;
                mutation++;
                if (mutation >= PentominoDatabase.data[index].length) {
                    break;
                }
            } else {
                x++;
                y = 0;
            }
        }
        mutation = 0;

        this.nextPentomino = PentominoDatabase.data[index][mutation];
        return nextMax;
    }

    public boolean nextMoveRight(int[][] teststate, int x, int y){
        for(int i=x; i<x+this.nextPentomino.length ; i++){
            for(int j=y; j<y+this.nextPentomino[0].length; j++){
                if(i + 1 == 5) return false;
                else{
                    if(teststate[i + 1][j] != -1) return false;
                }
            }
        }
        return true;
    }

    public boolean nextMoveDown(int[][] teststate, int x, int y){
        for(int i=x; i<x+this.nextPentomino.length ; i++){
            for(int j=y; j<y+this.nextPentomino[0].length; j++){
                if(j + 1 == 15) return false;
                else{
                    if(teststate[i][j+1] != -1 && this.nextPentomino[i-x][j-y] == 1){
                        return false;
                    }
                }
            }
        }
        return true;
    }
}   
 