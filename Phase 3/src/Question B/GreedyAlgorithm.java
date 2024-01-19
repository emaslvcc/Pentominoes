

public class GreedyAlgorithm implements Runnable{

    Parcel parcelA = new Parcel('A');
    Parcel parcelB = new Parcel('B');
    Parcel parcelC = new Parcel('C');

    public final int WIDTH = 5;
    public final int LENGTH = 33;
    public final int HEIGHT = 8;
    public int[][][] truck = new int[this.WIDTH][this.LENGTH][this.HEIGHT];
    public int score = 0;

    private static JavaFX visualizer;

    public static void setVisualizer(JavaFX visualizer) {
        GreedyAlgorithm.visualizer = visualizer;
    }
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


    private boolean isTruckFilled() {
        for (int i = 0; i < this.WIDTH; i++) {
            for (int j = 0; j < this.LENGTH; j++) {
                for (int k = 0; k < this.HEIGHT; k++) {
                    if (this.truck[i][j][k] == 0) {
                        return false;
                    }
                }
            }
        }
        return true;
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
        System.out.println("Total score: " + this.score);
    }

    public void run() {
        fillTruck();
         // Add this line if you have a method to update visualization.
        printMatrix();
    }
}
