public class Tetris
{
    public static int horizontalGridSize = 10;
    public static int verticalGridSize = 22;
    
    public static final char[] possibleinput = {'T','I','Z','Y','W','L','P','X','F','U','N','V'};
	public static boolean[] usedLetters = new boolean[possibleinput.length];
	public static char[] input = {};
	public static UI ui = new UI(horizontalGridSize, verticalGridSize, 25);

    
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

	public static void main(String[] args) {
		
	}


}