import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This class mplements a brute-force algorithm to solve a pentomino puzzle.
 * It explores possible placements of pentomino pieces on a grid, attempting to find a solution.
 * The class uses recursion and backtracking to explore different combinations of placements.
 */
public class BruteForceAlgorithm {
    public static final int horizontalGridSize = 33;
    public static final int verticalGridSize = 5;

    public static char[] input = {'T','T','T','T','T','T','T','T','T', 'T','T','T','T','T','T','T','T','T','T','T','T','T','T', 'T','T','T','T','T','L','L','L','L','L','L','L','L','L','L','L', 'L','L','L','L','L','L','L','L','P', 'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P','P','P','P','P'};
    public static boolean[] used = new boolean[input.length];

    private static Set<String> encounteredStates = new HashSet<>();
    public static int[][] ans = new int[0][0];
    private static final int MAX_DEPTH = 1000; // Set a value based on expected maximum depth
    
    /**
     * The main method initiates the search process to solve the pentomino puzzle.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        search();
    }

    /**
     * Initiates the brute-force search for a solution to the pentomino puzzle.
     */
    public static void search() {
        int[][] field = new int[horizontalGridSize][verticalGridSize];
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                field[i][j] = -1;
            }
        }
        encounteredStates.clear();
        Arrays.fill(used, false);

        shuffleInput();  // Randomize the input
        solve(field, 0 ,0); // Begin the recursive solving process
    }

    /**
     * Shuffles the input.
     */
    private static void shuffleInput() {
        List<Character> inputList = new ArrayList<>();
        for (char c : input) {
            inputList.add(c);
        }
        Collections.shuffle(inputList);
        for (int i = 0; i < input.length; i++) {
            input[i] = inputList.get(i);
        }
    }

    /**
     * Translates a pentomino character to its corresponding ID.
     * 
     * @param character Char that represents a pentomino.
     * @return Number of the pentomino piece.
     */
    private static int characterToID(char character) {
        int pentID = -1;
        switch(character) {
            case 'X': pentID = 0; break;
            case 'I': pentID = 1; break;
            case 'Z': pentID = 2; break;
            case 'T': pentID = 3; break;
            case 'U': pentID = 4; break;
            case 'V': pentID = 5; break;
            case 'W': pentID = 6; break;
            case 'Y': pentID = 7; break;
            case 'L': pentID = 8; break;
            case 'P': pentID = 9; break;
            case 'N': pentID = 10; break;
            case 'F': pentID = 11; break;
        }
        return pentID;
    }

    /**
     * Checks if the board is filled.
     * 
     * @param field Filed to be checked.
     * @return True if it is filled, false if not.
     */
    private static boolean isBoardFull(int[][] field) {
        for (int[] row : field) {
            for (int cell : row) {
                if (cell == -1) return false;
            }
        }
        return true;
    }

    /**
     * Checks if a piece can be added to the field.
     * 
     * @param field Field where the piece should be palced.
     * @param piece Piece to be placed.
     * @param x X coordinate.
     * @param y Y coordinate.
     * @return True if the piece can be placed, false if not.
     */
    private static boolean canAdd(int[][] field, int[][] piece, int x, int y) {
        if (verticalGridSize >= piece[0].length + y && horizontalGridSize >= piece.length + x) {
            for (int i = 0; i < piece.length; i++) {
                for (int j = 0; j < piece[i].length; j++) {
                    if (piece[i][j] == 1) {
                        if (field[i + x][j + y] != -1) return false;
                    }
                }
            }
        } else return false;
        return true;
    }

    /**
     * Generates delay between steps, so the user can better visualize the process.
     */
    private static void delay() {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Converts the board to a String.
     * 
     * @param board Board to be converted.
     * @return Board as a String object.
     */
    private static String boardToString(int[][] board) {
        StringBuilder sb = new StringBuilder();
        for (int[] row : board) {
            for (int cell : row) {
                sb.append(cell).append(",");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Adds a pentomino piece to a cloned copy of the current grid state.
     * 
     * @param field Current state of the grid.
     * @param piece Piece to be palced on the cloned grid.
     * @param pieceID Identifier of the pentomino piece.
     * @param x X coordinate.
     * @param y Y coordinate.
     * @return Cloned gird with the new piece.
     */
    private static int[][] addPieceWithClone(int[][] field, int[][] piece, int pieceID, int x, int y) {
        int[][] newField = new int[field.length][];
        for (int i = 0; i < field.length; i++) {
            newField[i] = field[i].clone();
        }
        for (int i = 0; i < piece.length; i++) {
            for (int j = 0; j < piece[i].length; j++) {
                if (piece[i][j] == 1) {
                    newField[x + i][y + j] = pieceID;
                }
            }
        }
        return newField;
    }

    /**
     * Recursively attempts to find a solution by placing pentomino pieces on the grid.
     *
     * @param field Current state of the grid.
     * @param pentIndex Index of the pentomino piece to be placed.
     * @param depth Depth of the recursion.
     * @return True if a solution is found, false otherwise.
     */
    private static boolean solve(int[][] field, int pentIndex, int depth) {
        // Prevents going too deep in recursion
        if (depth > MAX_DEPTH) {
            return false; 
        }
        
        if (isBoardFull(field) || pentIndex == input.length) {
            ans = field;
            return true;
        }

        // Try to place a piece in each position of the board
        for (int x = 0; x < field.length; x++) {
            for (int y = 0; y < field[x].length; y++) {
                if (field[x][y] == -1) {
                    for (int i = 0; i < input.length; i++) {
                            int pentID = characterToID(input[i]);
                            int[][][] pieces = PentominoDatabase.data[pentID];

                            for (int[][] piece : pieces) {
                                if (canAdd(field, piece, x, y)) {
                                    int[][] newField = addPieceWithClone(field, piece, pentID, x, y);
                                    used[i] = true;
                                    String state = boardToString(newField);
                                    if (!encounteredStates.contains(state)) {
                                        encounteredStates.add(state);
                                        delay();
                                        if (solve(newField, pentIndex + 1, depth + 1)) { // Increments depth
                                            return true;
                                        }
                                    }
                                    used[i] = false;
                                }
                            }
                    }
                    // We couldn't place a piece in this position
                    return false;
                }
            }
        }
        // If the function hasn't returned anything by this point, it means the board is full
        return isBoardFull(field);
    }
}