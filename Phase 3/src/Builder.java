import SuperParcels.Parcel;

/**
 * This class includes the methods to support the search of a solution.
 */
public class Builder {

    public static int width = 5;
    public static int length = 33;
    public static int height = 8;
    public static int capacity = width * length * height;
    public static String test;

    public static int[] used = new int[3];

    public static Parcel A = new Parcel('A');
    public static Parcel B = new Parcel('B');
    public static Parcel C = new Parcel('C');

    public static int[] w = {A.getWeight(), B.getWeight(), C.getWeight()};
    public static int[] v = {A.getValue(), B.getValue(), C.getValue()};

    public static int[][][] truck = new int[width][length][height];
    public static int[][][] emptytruck = new int[width][length][height];

    public static void calculateMax(){

        int[][] dp = new int[3 + 1][capacity + 1]; // Columns = capacity of truck, Rows = amount of parcels
        int[][] parcels = new int[3 + 1][capacity + 1]; // How many times a parcel would be used (if picked)
        int[] used1 = new int[3]; // How many parcels of each type would be used to get the maximum value

        for (int i = 1; i < 4; i++) {
            int weight = w[i - 1]; // Determine weight of current item
            int value = v[i - 1]; // Determine value of current item

            for (int j = 1; j <= capacity; j++) {
                dp[i][j] = dp[i - 1][j]; // If we don't pick this item

                // Check if we can have a more profitable pick with this item
                if (j >= weight) {
                    if (j % weight == 0) {
                        dp[i][j] = Math.max(dp[i][j], value * (j / weight));
                    }
                    else if (j % weight > 0) {
                        dp[i][j] = Math.max(dp[i][j], (value * (j / weight)) + dp[i - 1][j % weight]);
                    }
                    parcels[i][j] = j / weight; // Save the amount of parcels used (if this parcel was picked)
                }
            }
        }

        // Calculate how many parcels of each type were used
        int row = 3;
        int col = capacity;
        while (row > 0) {
            if (dp[row][col] != dp[row - 1][col]) {
                used1[row - 1] = parcels[row][col];
                col = col - (used[row - 1] * w[row - 1]);
            }
            row--;
        }

        System.out.println("Truck capacity: " + capacity);
        System.out.println("Max value: " + dp[3][capacity]);
        System.out.println("Amount of parcel A: " + used1[0]);
        System.out.println("Amount of parcel B: " + used1[1]);
        System.out.println("Amount of parcel C: " + used1[2]);
        
        used = used1;
    }

    public static boolean fillTruck(int[][][] field, int[] used){

        

        int x=-1;
        int y=-1;
        int z=-1;

        // search for empty space
        boolean stop = false;
        for(int a=0; a<field.length; a++){
            if(stop) break;
            for(int b=0; b<field[0].length; b++){
                if(stop) break;
                for(int c=0; c<field[0][0].length; c++){
                    if(field[a][b][c] == 0){
                        x=a;
                        y=b;
                        z=c;
                        stop=true;
                        break;
                        
                    }
                }
            }
        }
        System.out.println(x + " " + y + " " + z + "   " + used[0]);
        if(used[0]==43){
            System.out.println(field.length);
            for(int a=0; a<field.length; a++){
                for(int b=0; b<field[0].length; b++){
                    for(int c=0; c<field[0][0].length; c++){
                        truck[a][b][c] = field[a][b][c];
                    }
                }
            }
            return true;
        
        }

        if(x==-1 && y==-1 && z==-1){
                for(int a=0; a<field.length; a++){
                    for(int b=0; b<field[0].length; b++){
                        for(int c=0; c<field[0][0].length; c++){
                            truck[a][b][c] = field[a][b][c];
                            
                        }
                    }
                }
                return true;
            
        }

        int[][][] fieldcopy = new int[field.length][field[0].length][field[0][0].length];

        int[] usedcopy = used.clone();

        // look for a usable parcel type to place
        for(int i=0; i<used.length; i++){
            Parcel curr = A;
                if(i==1) curr = B;
                else if(i==2) curr = C;

            if(used[i]!=0){
            boolean bool=true;
                while(bool){
                    
                    if(canAdd(field, curr, x, y, z)){
                        // Set the copy of the field to the original field before adding the parcel
                        for(int a=0; a<field.length; a++){
                            for(int b=0; b<field[0].length; b++){
                                for(int c=0; c<field[0][0].length; c++){
                                    fieldcopy[a][b][c] = field[a][b][c];
                                }
                            }
                        }
                        
                        addParcel(fieldcopy, curr, x, y, z);

                        usedcopy[i]--;

                        int temp = curr.getRotation();

                        curr.resetRotation();


                        if(fillTruck(fieldcopy, usedcopy)) return true;

                        curr.rotate(temp);
    
                        usedcopy[i]++;


                    }
                    if(!curr.rotate()){
                        curr.resetRotation();
                        bool=false;
                        break;
                    }
                    //curr.resetRotation();
                }
            
            }
        }
        return false;



        
    }

    public static void addParcel(int[][][] field, Parcel curr, int x, int y, int z){

        int[][][] arr = curr.getParcelArray();

        for(int a=x; a<x+arr.length; a++){
            for(int b=y; b<y+arr[0].length; b++){
                for(int c=z; c<z+arr[0][0].length; c++){
                    field[a][b][c] = curr.getNum();
                }
            }
        }

    }

    public static boolean canAdd(int[][][] field, Parcel curr, int x, int y, int z){

        int[][][] arr = curr.getParcelArray();

        // check if it out of bounds or not
        if(x+arr.length>field.length || y+arr[0].length>field[0].length || z+arr[0][0].length>field[0][0].length){
            return false;
        }

        // check for overlap
        else{
            for(int a=x; a<x+arr.length; a++){
                for(int b=y; b<y+arr[0].length; b++){
                    for(int c=z; c<z+arr[0][0].length; c++){
                        if(field[a][b][c] != 0) return false;
                    }
                }
            }
        }

        return true;
    }

    
    public static void main(String[] args) {
        calculateMax();

        System.out.println(fillTruck(emptytruck, used));
        
        for(int i=0; i<truck.length; i++){
            for(int j=0; j<truck[0].length; j++){
                for(int k=0; k<truck[0][0].length; k++){
                    if(truck[i][j][k]!=0) System.out.println(truck[i][j][k]);
                }
            }
        }

       // for(int i=0; i<truck.length; i++){
          //  for(int j=0; j<truck[0].length; j++){
             //   for(int k=0; k<truck[0][0].length; k++){
              //      System.out.println(truck[i][j][k]);
              //  }
           // }
        //}

        
    }
}