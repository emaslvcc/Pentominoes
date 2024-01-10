/**
 * @author Department of Data Science and Knowledge Engineering (DKE)
 * @version 2022.0
 */
/**
 * This class includes the methods to support the search of a solution.
 */
public class Builder
{
    public static int width = 5;
    public static int length = 33;
    public static int height = 8;
    public static int capacity = width*length*height;

    public static Parcel A = new Parcel('A');
    public static Parcel B = new Parcel('B');
    public static Parcel C = new Parcel('C');

    public static int[] w = {A.getWeight(), B.getWeight(), C.getWeight()};
    public static int[] v = {A.getValue(), B.getValue(), C.getValue()};

    public static int[] w1 = {1, 3, 4};
    public static int[] v1 = {2, 7, 10};

    

    

    public static int[][][] box = new int[width][length][height];
    

    public void addParcel(Parcel p){
        
    }


    public static int calculateMax(){

        int[][] dp = new int[3+1][capacity+1];  // columns = capacity of truck, rows = amount of parcels
        int[][] parcels = new int[3+1][capacity+1]; // how many times a parcel would be used (if picked)
        int[] used = new int[3]; // how many parcels of each type would be used to get the maximum value

        for(int i=1; i<4; i++){
            int weight = w[i-1];
            int value = v[i-1];
            // determine weight and value of current item

            for(int j=1; j<=capacity; j++){
                
                // if we dont pick this item :
                dp[i][j] = dp[i-1][j];

                // check if we can have a more profitable pick with this item
                if(j >= weight){
                    if(j%weight==0){
                        dp[i][j] = Math.max(dp[i][j], value * (j/weight));
                    }
                    else if(j%weight>0){
                        dp[i][j] = Math.max(dp[i][j], (value * (j/weight)) + dp[i-1][j%weight]);
                    }
                    // save amount of parcels used (if this parcel would be picked)
                    parcels[i][j] = j/weight;
                }

            }

        }

        // calculate how many parcels of each type were used


        int row=3;
        int col=capacity;
        while(row>0){
            if(dp[row][col]!=dp[row-1][col]){
                used[row-1] = parcels[row][col];
                col = col - (used[row-1]*w[row-1]);
            }

            row--;

        }

        System.out.println("Truck capacity : "+capacity);
        System.out.println("Max value : "+dp[3][capacity]);
        System.out.println("Amount of parcel A : "+used[0]);
        System.out.println("Amount of parcel B : "+used[1]);
        System.out.println("Amount of parcel C : "+used[2]);
        

        

        return 0;
    }
    public static void main(String[] args) {
        calculateMax();
    }




}
