public class Parcel {
    private int x;
    private int y;
    private int z;
    private int val;
    private int weight;

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
}