public class QuestionA {

    private static final double TRUCK_VOLUME = 165.0;

    private static final double VOLUME_A = 2.0;
    private static final double VOLUME_B = 3.0;
    private static final double VOLUME_C = 3.375;

    private static int bestParcelA = 0;
    private static int bestParcelB = 0;
    private static int bestParcelC = 0;

    private static boolean validCombinationFound = false;

    public static void main(String[] args) {
        TruckFiller(0, 0, 0);

        if (validCombinationFound) {
            System.out.println("Best combination: Parcel A - " + bestParcelA + ", Parcel B - " + bestParcelB + ", Parcel C - " + bestParcelC);
        } else {
            System.out.println("No valid combination found to fill the truck.");
        }
    }

    public static void TruckFiller(int currentParcelA, int currentParcelB, int currentParcelC) {
        double usedCapacity = currentParcelA * VOLUME_A + currentParcelB * VOLUME_B + currentParcelC * VOLUME_C;

        if (usedCapacity == TRUCK_VOLUME) {
            bestParcelA = currentParcelA;
            bestParcelB = currentParcelB;
            bestParcelC = currentParcelC;
            validCombinationFound = true;
            return;
        }

        if (usedCapacity > TRUCK_VOLUME) {
            return;
        }

        if (!validCombinationFound && currentParcelA < TRUCK_VOLUME / VOLUME_A) {
            TruckFiller(currentParcelA + 1, currentParcelB, currentParcelC);
        }
        if (!validCombinationFound && currentParcelB < TRUCK_VOLUME / VOLUME_B) {
            TruckFiller(currentParcelA, currentParcelB + 1, currentParcelC);
        }
        if (!validCombinationFound && currentParcelC < TRUCK_VOLUME / VOLUME_C) {
            TruckFiller(currentParcelA, currentParcelB, currentParcelC + 1);
        }
    }
}

