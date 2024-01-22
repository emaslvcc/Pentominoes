/**
 * This class implements a Greedy Algorithm to place cubic parcels in a 3D space (truck).
 */
public class GreedyAlgorithm implements Runnable{

    Parcel parcelA = new Parcel('A');
    Parcel parcelB = new Parcel('B');
    Parcel parcelC = new Parcel('C');

    public final int WIDTH = 5;
    public final int LENGTH = 33;
    public final int HEIGHT = 8;
    public int[][][] truck = new int[this.WIDTH][this.LENGTH][this.HEIGHT];
    public int score = 0;

    /**
     * Starts the Greedy Algorithm and calls the necessary methods to fill the truck.
     */
    public void fillTruck() {
        Parcel[] parcels = {this.parcelC, this.parcelB, this.parcelA};

        for (Parcel parcel : parcels) {
            for (int rotation = 0; rotation < 6; rotation++) {
                parcel.resetRotation();
                for (int i = 0; i < rotation; i++) {
                    parcel.rotate();
                }

                boolean placementDone;
                do {
                    placementDone = this.tryPlaceParcel(parcel);
                } while (placementDone);
            }
        }
    }

    /**
     * Tries to place a parcel at an available position.
     * 
     * @param parcel Parcel type to be consider.
     * @return True if the parcel can be placed, false if not.
     */
    private boolean tryPlaceParcel(Parcel parcel) {
        int[][][] array = parcel.getParcelArray();
        for (int x = 0; x <= this.truck.length - array.length; x++) {
            for (int y = 0; y <= this.truck[0].length - array[0].length; y++) {
                for (int z = 0; z <= this.truck[0][0].length - array[0][0].length; z++) {
                    if (this.canAdd(parcel, x, y, z)) {
                        this.addParcel(parcel, x, y, z);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Evaluates overlapping pieces, to check if the parcel can be added to that position.
     * 
     * @param parcel Parcel to be considered.
     * @param x X coordinate.
     * @param y Y coordinate.
     * @param z Z coordinate.
     * @return True if the parcel can be placed, false if not.
     */
    private boolean canAdd(Parcel parcel, int x, int y, int z) {
        int[][][] array = parcel.getParcelArray();
        for (int i = x; i < x + array.length; i++) {
            for (int j = y; j < y + array[0].length; j++) {
                for (int k = z; k < z + array[0][0].length; k++) {
                    if (this.truck[i][j][k] != 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Adds a parcel to the truck's 3D array.
     * 
     * @param parcel Parcel to be considered.
     * @param x X coordinate.
     * @param y Y coordinate.
     * @param z Z coordinate.
     * @return parcel's value
     */
    private void addParcel(Parcel parcel, int x, int y, int z) {
        int[][][] array = parcel.getParcelArray();
        for (int i = x; i < x + array.length; i++) {
            for (int j = y; j < y + array[0].length; j++) {
                for (int k = z; k < z + array[0][0].length; k++) {
                    this.truck[i][j][k] = parcel.getNum();
                }
            }
        }
        this.score += parcel.getValue();
    }

    /**
     * Prints the truck's matrix.
     */
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
    }

    /**
     * Prints the final score.
     */
    public void printScore() {
        System.out.println("Question B Total score: " + this.score);
    }

    /**
     * Executes the run operation, which involves filling the truck and printing the resulting matrix.
     */
    public void run() {
        this.fillTruck();
        this.printMatrix();
    }
}
