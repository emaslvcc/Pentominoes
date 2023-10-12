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
    public static int verticalGridSize;
    
    public static final char[] possibleinput = {'T','I','Z','Y','W','L','P','X','F','U','N','V'};
	public static boolean[] usedLetters = new boolean[possibleinput.length];
	public static char[] input = {};
	public static UI ui;
    //Static UI class to display the board
    
	/**
	 * Checks how much space a pentomino takes up
	 * @param inputchar a character representing a pentomino
	 * @return the total space taken by the selected pentomino
	 */
	public static int checkSpace (char inputchar) {
		int space = 0;
		int pentID = characterToID(inputchar);
		int[][] pentomino = PentominoDatabase.data[pentID][0];

		// Iterates over the pentomino
		for (int i = 0; i < pentomino.length; i++) {
			for (int j = 0; j < pentomino[i].length; j++) {

				// If the selected space is not empty, add 1 to the total space
				if (pentomino[i][j] == 1) space++;
			}
		}
		return space;
	}
	
	/**
	 * Starts a basic search algorithm
	 */
    public static void search() {
		ui = new UI(horizontalGridSize, verticalGridSize, 50);

        // Initialize an empty board
        int[][] field = new int[horizontalGridSize][verticalGridSize];

        for (int i = 0; i < field.length; i++) {
            for(int j = 0; j < field[i].length; j++) {
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
	 * Starts a recursive algorithm
	 * @param field a matrix representing the board to be fulfilled with pentominoes
	 */
    private static void basicSearch(int[][] field) {
		recurse(field, usedLetters);	
    }

	/**
	 * Checks if a selected pentomino can be placed
	 * @param field a matrix representing the board to be fulfilled with pentominoes
	 * @param pieceToPlace the selected pentomino that can or cannot be placed
	 * @param x x position of the field
	 * @param y y position of the field
	 */
	public static boolean canAdd(int[][] field, int[][] pieceToPlace, int x, int y) {
		boolean startset = false;

		// Checks if the pentomino is within boundaries of the field
		if (verticalGridSize >= pieceToPlace[0].length + y && horizontalGridSize >= pieceToPlace.length + x) { 

			for (int i = 0; i < pieceToPlace.length; i++) {
				for (int j=0; j < pieceToPlace[i].length; j++) {

					// Shift the top-left corner of the piece to align with the current (x, y) position
					if (pieceToPlace[i][j] == 1) {
						if (!startset) {
							x = x - i;
							y = y - j;
							startset = true;
						}

						if (i + x < 0 || j + y < 0 || i + x > field.length || j + y > field[i + x].length || field[i + x][j + y] != -1) return false;
					}
				}
			}
		} else return false;
		return true;
	}

	/**
	 * Recursive method that fills the field, if possible
	 * @param field a matrix representing the board to be fulfilled with pentominoes
	 * @param used a boolean array that stores what pentominoes have been placed
	 */
	public static boolean recurse(int[][] field, boolean[] used) {
		int x = -1;
		int y = -1;
		boolean stop = false;

		// Iterates over the rectangle's rows
		for (int i = 0; i < field.length; i++) {
			if (stop) break;

			// Iterates over rectangle's elements and searches for an empty space
			for (int j = 0; j < field[i].length; j++) {
				if (field[i][j] == -1) {
					x = i;
					y = j;
					stop = true;
					break;
				}
			}
		}

		// Checks if the grid is full: if x and y are equal to -1, there are no empty spaces
		if (x == -1 && y == -1) {
			ui.setState(field);
			System.out.println("Done!");
			return true;
		}

		// Creates a copy of the field
		int[][] fieldCopy = new int[field.length][field[0].length];

		// Creates a copy of the used array
		boolean[] usedCopy = used.clone();
		
		// Searches for a pentomino
		for (int i = 0; i < input.length ; i++) {
			int pentID = characterToID(input[i]);
			int mutations = PentominoDatabase.data[pentID].length;

			// Searches for a mutation of the pentomino
			for (int j = 0; j < mutations; j++) {
				// Checks if the pentomino has been used. If so, stop searching for a mutation and try the next pentomino
				if (used[i] == true) break;
				int mutation = j;

				// Selects the unused pentomino and the selected mutation to be placed
				int[][] pieceToPlace = PentominoDatabase.data[pentID][mutation];
				
				// If a fitting pentomino is found, checks if it can be added, and if so, adds it
				if (canAdd(field, pieceToPlace, x, y)) {

						// Set the copy of the field to the original field before adding the pentomino
						for (int t = 0; t < field.length; t++) {
							for (int u = 0; u < field[t].length; u++) {
								fieldCopy[t][u] = field[t][u];
							}
						}						

						// Adds the pentomino
						addPiece(fieldCopy, pieceToPlace, pentID, x, y);
						usedCopy[i] = true;

						// Recursive call
						if (recurse(fieldCopy, usedCopy)) return true;
                        // Backtracking
						usedCopy[i] = false;
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
    public static void addPiece(int[][] field, int[][] piece, int pieceID, int x, int y) {
		boolean startset = false;
        
		// loops over x position of the pentomino
		for(int i = 0; i < piece.length; i++) {

			// loops over y position of the pentomino
            for (int j = 0; j < piece[i].length; j++) {
                if (piece[i][j] == 1) {
                    
					// Add the ID of the pentomino to the board if the pentomino occupies this square
					if (!startset) {
						x = x - i;
						y = y - j;
						startset = true; 
					}

                    field[x + i][y + j] = pieceID;
                }
            }
        }
    }

	/**
	 * Checks if a selected pentomino is valid
	 * @param pentomino represents the selected pentomino
	 */
	public static boolean isPentominoValid(char pentomino) {
		
		// Goes over the array of possible pentomino characters
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
    public static void main(String[] args) {
		long startTime = 0;
		Scanner scanner = new Scanner(System.in);

		// Get grid size from user
		System.out.println("Enter horizontal length of grid: ");
		horizontalGridSize = scanner.nextInt();

		System.out.println("Enter vertical length of grid: ");
		verticalGridSize = scanner.nextInt();

		// Check if grid size is valid
		if (horizontalGridSize<= 0 || verticalGridSize <= 0) {
			
			System.out.println("This grid is invalid.");
			scanner.close();
		} 		 else{

			// Get pentominoes from user
			System.out.println("Enter pentominoes: ");
			String pentominoesInput = scanner.next();
			char[] input1 = new char[pentominoesInput.length()];

			// Check if pentominoes are valid
			boolean isValid = true;
			int i = 0;
			for (int pent = 0; pent < pentominoesInput.length(); pent++) {
				char pentominoChar = pentominoesInput.charAt(pent);

				// Pentomino is not valid
				if (!isPentominoValid(pentominoChar)) {
					isValid = false;
					System.out.println(pentominoChar + " is invalid!");
					break;
				}

				// Create an array of the valid pentomino characters
				input1[i++] = pentominoChar;
			}
			if (!isValid) {
				System.out.println("Please enter valid pentominoes: T, I, Z, Y, W, L, P, X, F, U, N, V");
				scanner.close();
				return; // Exit the program due to invalid input.
			}
		input = input1;

		// Calculates the total space used by the selected pentominoes
		int totalspace=0;
		for (char pentID : input){
			totalspace += checkSpace(pentID);
		}
		
		// Start time counting
		startTime = System.nanoTime();

		// Calls the search method if the pentominoes are valid and they can fill the grid
		if (isValid) {

			if (totalspace >= horizontalGridSize*verticalGridSize) {
				search(); 
			} else {
				System.out.println("These pentominoes can't fill this grid.");
			}
		} else {
			System.out.println("Please enter valid pentominoes: \n" +
			                   "T, I, Z, Y, W, L, P, X, F, U, N, V");
		}

		scanner.close();
		}

		// Display running time
		long endTime = System.nanoTime();
		long duration = (endTime - startTime);
		System.out.println(duration / 1000000 + "ms");
    }
}
