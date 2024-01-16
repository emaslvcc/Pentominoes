import SuperParcels.Parcel;

public class GreedyAlgorithm {

    Parcel parcelA = new Parcel('A');
    Parcel parcelB = new Parcel('B');
    Parcel parcelC = new Parcel('C');

    public final int WIDTH = 5;
    public final int LENGTH = 33;
    public final int HEIGHT = 8;
    public int[][][] truck = new int[WIDTH][LENGTH][HEIGHT];
    public int score = 0;

    public void fillTruck() {
        Parcel[] parcels = {parcelC, parcelB, parcelA};

        for (Parcel parcel : parcels) {
            for (int rotation = 0; rotation < 6; rotation++) {
                parcel.resetRotation();
                for (int i = 0; i < rotation; i++) {
                    parcel.rotate();
                }

                boolean placementDone;
                do {
                    placementDone = tryPlaceParcel(parcel);
                } while (placementDone);
            }
        }
    }

    private boolean tryPlaceParcel(Parcel parcel) {
        int[][][] array = parcel.getParcelArray();
        for (int x = 0; x <= truck.length - array.length; x++) {
            for (int y = 0; y <= truck[0].length - array[0].length; y++) {
                for (int z = 0; z <= truck[0][0].length - array[0][0].length; z++) {
                    if (canAdd(parcel, x, y, z)) {
                        addParcel(parcel, x, y, z);
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
                    if (truck[i][j][k] != 0) {
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
                    truck[i][j][k] = parcel.getNum();
                }
            }
        }
        score += parcel.getValue();
    }


    private boolean isTruckFilled() {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < LENGTH; j++) {
                for (int k = 0; k < HEIGHT; k++) {
                    if (truck[i][j][k] == 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void printMatrix() {
        System.out.println("Final Truck Matrix:");
        for (int i = 0; i < truck.length; i++) {
            for (int j = 0; j < truck[0].length; j++) {
                for (int k = 0; k < truck[0][0].length; k++) {
                    System.out.print(truck[i][j][k] + " ");
                }
                System.out.println();
            }
            System.out.println();
        }
        System.out.println("Total score: " + score);
    }

    public static void main(String[] args) {
        GreedyAlgorithm alg = new GreedyAlgorithm();
        alg.fillTruck();
        alg.printMatrix();
    }
}