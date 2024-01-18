import java.util.Random;

public class GreedyPentominoes {

    // Create parcel objects
    Pentominoes T = new Pentominoes('T');
    Pentominoes P = new Pentominoes('P');
    Pentominoes L = new Pentominoes('L');

    public final int WIDTH = 5;
    public final int LENGTH = 33;
    public final int HEIGHT = 8;
    public int[][][] truck = new int[this.WIDTH][this.LENGTH][this.HEIGHT];
    public int score = 0;
    public int added = 0;
    private Random rand = new Random();

    public void fillTruck() {
        Pentominoes[] pentominoes = {this.T, this.P, this.L};

        for (Pentominoes pentomino : pentominoes) {
            for (int rotation = this.rand.nextInt(11); rotation < 10; rotation++) {
                while(this.placementAttempt(pentomino, pentomino.rotate.get(rotation))) {
                    this.added++;
                }
            }
        }
    }

    public boolean placementAttempt(Pentominoes pentominoObj, int[][][] pentomino) {
        for (int x = 0; x < this.truck.length - pentomino.length + 1; x++) {
            for (int y = 0; y < this.truck[0].length - pentomino[0].length+1; y++) {
                for (int z = 0; z < this.truck[0][0].length - pentomino[0][0].length+1; z++) {
                    if (this.canAdd(pentominoObj, pentomino, x, y, z)) {
                        this.addParcel(pentominoObj, pentomino, x, y, z);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean canAdd(Pentominoes pentominoObj, int[][][] pentomino, int x, int y, int z) {
        for (int i = x; i < x + pentomino.length; i++) {
            for (int j = y; j < y + pentomino[0].length; j++) {
                for (int k = z; k < z + pentomino[0][0].length; k++) {
                    if (this.truck[i][j][k] != 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public int addParcel(Pentominoes pentominoObj, int[][][] pentomino, int x, int y, int z) {
        for (int i = x; i < x + pentomino.length; i++) {
            for (int j = y; j < y + pentomino[0].length; j++) {
                for (int k = z; k < z + pentomino[0][0].length; k++) {
                    if(pentomino[i-x][j-y][k-z]!=0)
                    this.truck[i][j][k] = pentominoObj.getNum();
                }
            }
        }
        this.score += pentominoObj.getValue();
        return this.score;
    }

    public void printMatrix() {
        System.out.println("Final Truck Matrix:");
        for (int i = 0; i < this.truck.length; i++) {
            for (int j = 0; j < this.truck[0].length; j++) {
                for (int k = 0; k < this.truck[0][0].length; k++) {
                    System.out.print(this.truck[i][j][k] + " ");
                }
                System.out.println();
            }
            System.out.println();
        }
        System.out.println(this.score);
    }

    public static void main(String[] args) {
        GreedyPentominoes alg = new GreedyPentominoes();
        alg.fillTruck();
        alg.printMatrix();
    }
}

