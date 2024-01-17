package Database;

public class Parcel {
    private int x;
    private int y;
    private int z;
    private int val;
    private int weight;
    private int rotation;
    public int num;

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
            this.num = 1;
            this.rotation = 0;

        } 
        else if (c == 'B') {
            this.x = 2;
            this.y = 3;
            this.z = 4;
            this.weight = 2 * 3 * 4;
            this.val = 4;
            this.num = 2;
            this.rotation = 0;
        }
        else if (c == 'C') {
            this.x = 3;
            this.y = 3;
            this.z = 3;
            this.weight = 3 * 3 * 3;
            this.val = 5;
            this.num = 3;
            this.rotation = 0;
        }
        else if (c == 'F') {
            this.num = 7;
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

    public int getNum(){
        return this.num;
    }

    public int getRotation () { return rotation; }
    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }
    public int getZ(){
        return this.z;
    }
    

    public int[][][] getParcelArray() {
        switch(this.rotation){
            case 0:
                return new int[this.x][this.y][this.z];
            case 1:
                return new int[this.x][this.z][this.y];
            case 2:
                return new int[this.y][this.x][this.z];
            case 3:
                return new int[this.y][this.z][this.x];
            case 4:
                return new int[this.z][this.y][this.x];
            case 5:
                return new int[this.z][this.x][this.y];
            default:
                return new int[0][0][0];
        }

    }

    public boolean rotate(){
        if(this.rotation<5){
            this.rotation++;
            return true;
        }
        else{
            return false;
        }
    }
    public boolean rotate(int newRotation){
        if(newRotation <= 5){
            this.rotation = newRotation;
            return true;
        }
        else{
            return false;
        }
    }
    public void resetRotation(){
        this.rotation = 0;
    }

    
}