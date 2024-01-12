public class Parcel {
    public int x;
    public int y;
    public int z;
    public int val;
    public int weight;

    /**
     * A constructor that creates a parcel object
     * @param c a character correspondent to a parcel (A, B or C)
     **/
    public Parcel(char c){
        if (c == 'A') {
            this.x = 2;
            this.y = 2;
            this.z = 4;
            this.weight = 2 * 2 * 4;
            this.val = 3;
        } 
        else if (c == 'B') {
            this.x = 2;
            this.y = 3;
            this.z = 4;
            this.weight = 2 * 3 * 4;
            this.val = 4;
        }
        else if (c == 'C') {
            this.x = 3;
            this.y = 3;
            this.z = 3;
            this.weight = 3 * 3 * 3;
            this.val = 5;
        }
    }

    /**
     * Getter method that returns a parcel's weight
     * @return parcel's weight
     */
    public int getWeight(){
        return this.weight;
    }

    /**
     * Getter method that returns a parcel's value
     * @return parcel's value
     */
    public int getValue(){
        return this.val;
    }

    public int[][][] parcelArray(int value) {
        int scale = 70;
        int[][][] A = new int[1 * scale][1 * scale][2 * scale];
        int[][][] B = new int[1 * scale][105][2 * scale];
        int[][][] C = new int[105][105][105];

        switch (value) {
            case 3:
                for (int length = 0; length < 1 * scale; length++) {
                    for (int height = 0; height < 1 * scale; height++) {
                        for (int width = 0; width < 2 * scale; width++) {
                            A[length][height][width] = 1;
                        }
                    }
                }
                return A;
            case 4:
                for (int length = 0; length < 1 * scale; length++) {
                    for (int height = 0; height < 105; height++) {
                        for (int width = 0; width < 2 * scale; width++) {
                            B[length][height][width] = 1;
                        }
                    }
                }
                return B;
            case 5:
                for (int length = 0; length < 105; length++) {
                    for (int height = 0; height < 105; height++) {
                        for (int width = 0; width < 105; width++) {
                            C[length][height][width] = 1;
                        }
                    }
                }
                return C;
            default:
                return null;
        }
    }
}