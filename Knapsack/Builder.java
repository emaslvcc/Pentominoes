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
    public static int height = 9;
    public static int capacity = width*length*height;

    public static Parcel A = new Parcel('A');
    public static Parcel B = new Parcel('B');
    public static Parcel C = new Parcel('C');

    public static int[] w = {A.getWeight(), B.getWeight(), C.getWeight()};
    public static int[] v = {A.getValue(), B.getValue(), C.getValue()};
    public static int[] ratio = {v[0]/w[0] , v[1]/w[1] , v[2]/w[2]};

    

    

    public static int[][][] box = new int[width][length][height];
    

    public void addParcel(Parcel p){
        
    }


    // dp 
    public static int calculateMax(){

        int[][] dp = new int[3+1][capacity+1];  // columns = capacity of truck, rows = amount of parcels

        for(int i=1; i<4; i++){
            int weight = w[i-1];
            int value = v[i-1];
            // determine weight and value of current item

            for(int j=1; j<=capacity; j++){
                
                // if we dont pick this element :
                dp[i][j] = dp[i-1][j];

                // check if we can have a more profitable pick
                if(j >= weight && dp[i-1][j-weight] + value > dp[i][j])
                dp[i][j] = dp[i-1][j-weight] + value;

            }

        }

        System.out.println(dp[3][capacity]);

        return 0;
    }
    public static void main(String[] args) {
        calculateMax();
    }




}
