/**
 * @author Department of Data Science and Knowledge Engineering (DKE)
 * @version 2022.0
 */
import java.util.Scanner;
/**
 * This class includes the methods to support the search of a solution.
 */
public class Search
{
    public static int horizontalGridSize;
    public static int verticalGridSize = 5;
    
    public static final char[] possibleinput = {'T','I','Z','Y','W','L','P','X','F','U','N','V'};
	public static boolean[] usedLetters = new boolean[possibleinput.length];
	public static char[] input = {};
	public static UI ui;
    
    //Static UI class to display the board
    

	/**
	 * Helper function which starts a basic search algorithm
	 */

	public void checkSpace(int pentID)
	{
		
	}
	
    public static void search()
    {
		ui = new UI(horizontalGridSize, verticalGridSize, 50);
        // Initialize an empty board
        int[][] field = new int[horizontalGridSize][verticalGridSize];

        for(int i = 0; i < field.length; i++)
        {
            for(int j = 0; j < field[i].length; j++)
            {
                // -1 in the state matrix corresponds to empty square
                // Any positive number identifies the ID of the pentomino
            	field[i][j] = -1;
            }
        }
        //Start the basic search
        basicSearch(field);
    }
	
	/**
	 * Get as input the character representation of a pentomino and translate it into its corresponding numerical value (ID)
	 * @param character a character representating a pentomino
	 * @return	the corresponding ID (numerical value)
	 */
    private static int characterToID(char character) {
    	int pentID = -1; 
    	if (character == 'X') {
    		pentID = 0;
    	} else if (character == 'I') {
    		pentID = 1;
    	} else if (character == 'Z') {
    		pentID = 2;
    	} else if (character == 'T') {
    		pentID = 3;
    	} else if (character == 'U') {
    		pentID = 4;
     	} else if (character == 'V') {
     		pentID = 5;
     	} else if (character == 'W') {
     		pentID = 6;
     	} else if (character == 'Y') {
     		pentID = 7;
    	} else if (character == 'L') {
    		pentID = 8;
    	} else if (character == 'P') {
    		pentID = 9;
    	} else if (character == 'N') {
    		pentID = 10;
    	} else if (character == 'F') {
    		pentID = 11;
    	} 
    	return pentID;
    }
	
	/**
	 * Basic implementation of a search algorithm. It is not a bruto force algorithms (it does not check all the posssible combinations)
	 * but randomly takes possible combinations and positions to find a possible solution.
	 * The solution is not necessarily the most efficient one
	 * This algorithm can be very time-consuming
	 * @param field a matrix representing the board to be fulfilled with pentominoes
	 */
    private static void basicSearch(int[][] field){

		recurse(field, usedLetters);
		
    }
	public static boolean canAdd(int[][] field , int[][] pieceToPlace , int x, int y){
		boolean startset = false;

		// is pieceToPlace within boundaries of field?
		if (verticalGridSize >= pieceToPlace[0].length+y && horizontalGridSize >= pieceToPlace.length+x){ 

			for(int i=0; i < pieceToPlace.length; i++){

				for(int j=0; j < pieceToPlace[i].length; j++){

					if(pieceToPlace[i][j] == 1){
						if(!startset){
							x = x-i;
							y = y-j;
							startset = true;
						}

						if(i+x < 0 || j+y < 0 || i+x > field.length || j+y > field[i+x].length || field[i+x][j+y] != -1) return false;
					}
				}
			}
		}
		else return false;

		return true;
	}

	public static boolean recurse(int[][] field , boolean[] used){
		int x=-1;
		int y=-1;
		boolean stop=false;
		// search for first empty index
		for(int i=0; i<field.length; i++){
			if(stop==true) break;
			for(int j=0; j<field[i].length; j++){
				if(field[i][j] == -1){
					x = i;
					y = j;
					stop = true;
					break;
				}
				
			}
		}
		

		// if x and y are -1 means there was no empty index , field is filled 
		if(x==-1 && y==-1){
			ui.setState(field);
			System.out.println("done");
			return true;
		}


		// create a copy of the field
		int[][] copy = new int[field.length][field[0].length];
		// create a copy of the used array
		boolean[] used1 = used.clone();
		



		// search for a pentomino
		for(int i=0; i < input.length ; i++){
			int pentID = characterToID(input[i]);
			int mutations = PentominoDatabase.data[pentID].length;

			// search for a mutation of the pentomino
			for(int j=0; j < mutations; j++){
				// if the pentomino is already used stop searching for a mutation, try next pentomino
				if(used[i] == true) break;
				int mutation = j;

				int[][] pieceToPlace = PentominoDatabase.data[pentID][mutation];
				
				// if a fitting piece is found, check if we can add it, then add it 
				if (canAdd(field, pieceToPlace, x, y))  {

						// set the copy to original field before adding
						for(int t=0; t<field.length ; t++){
							for(int u=0 ; u<field[t].length ; u++){
								copy[t][u] = field[t][u];
							}
						}						

						addPiece(copy, pieceToPlace, pentID, x, y);
						used1[i] = true;
						if(recurse(copy , used1)) return true;
						used1[i] = false;
				}
			}
		}
		return false;
		
	
	}
    

    
	/**
	 * Adds a pentomino to the position on the field (overriding current board at that position)
	 * @param field a matrix representing the board to be fulfilled with pentominoes
	 * @param piece a matrix representing the pentomino to be placed in the board
	 * @param pieceID ID of the relevant pentomino
	 * @param x x position of the pentomino
	 * @param y y position of the pentomino
	 */
    public static void addPiece(int[][] field, int[][] piece, int pieceID, int x, int y)
    {
		boolean startset = false;
        for(int i = 0; i < piece.length; i++) // loop over x position of pentomino
        {
            for (int j = 0; j < piece[i].length; j++) // loop over y position of pentomino
            {
                if (piece[i][j] == 1)
                {
                    // Add the ID of the pentomino to the board if the pentomino occupies this square
					if(!startset){
						x = x-i;
						y = y-j;
						startset = true;
					}
                    field[x + i][y + j] = pieceID;
                }
            }
        }
    }

	// This method checks if a pentomino is valid
	public static boolean isPentominoValid(char pentomino) {
		
		for (char validPentomino : possibleinput) {
			if (validPentomino == pentomino) {
				return true; // Pentomino is valid
			}
		}
		return false; // Pentomino is not valid
	}

	/**
	 * Main function. Needs to be executed to start the basic search algorithm
	 */
    public static void main(String[] args)
    {
		long startTime = 0;
		Scanner scanner = new Scanner(System.in);

		// Get grid size from user
		System.out.println("Enter horizontal length of grid: ");
		horizontalGridSize = scanner.nextInt();

		// Check if grid size is valid
		if (horizontalGridSize == 0) {
			System.out.println("This grid is invalid.");
		} else {
			// Get pentominoes from user
			System.out.println("Enter pentominoes: ");
			String pentominoesInput = scanner.next();
			char[] input1 = new char[pentominoesInput.length()];

			// Check if pentominoes are valid
			boolean isValid = true;
			int i=0;
			for (int pent = 0; pent < pentominoesInput.length(); pent++) {
				char pentominoChar = pentominoesInput.charAt(pent);

				if (!isPentominoValid(pentominoChar)) {
					isValid = false;
					System.out.println(pentominoChar + " is invalid!");
					break;
				}
				input1[i++] = pentominoChar;
				
		}
		input = input1;
		
		 startTime = System.nanoTime();
		if (isValid) {
			
				search();
				
				

				
			

			} else {
				System.out.println("Please enter valid pentominoes: \n" +
			                   	   "T, I, Z, Y, W, L, P, X, F, U, N, V");
			}

		scanner.close();
		}
		long endTime = System.nanoTime();
		long duration = (endTime - startTime);
		System.out.println(duration / 1000000 + "ms");
    }
}