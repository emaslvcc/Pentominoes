public class Parcel{
    private int x;
    private int y;
    private int z;
    private int val;
    private int weight;

    public Parcel(char c){
        if(c == 'A'){
            this.x = 2;
            this.y = 2;
            this.z = 4;
            this.weight = 2*2*4;
            this.val = 3;
        }
        if(c == 'B'){
            this.x = 2;
            this.y = 3;
            this.z = 4;
            this.weight = 2*3*4;
            this.val = 4;
        }
        if(c == 'C'){
            this.x = 3;
            this.y = 3;
            this.z = 3;
            this.weight = 3*3*3;
            this.val = 5;
        }
    
    }

    public int getWeight(){
        return this.weight;
    }

    public int getValue(){
        return this.val;
    }


}