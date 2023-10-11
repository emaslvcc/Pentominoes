import java.util.*;

public class Search {
    public static final int horizontalGridSize = 12;
    public static final int verticalGridSize = 5;
    public static char[] input = {'W', 'Y', 'I', 'T', 'Z', 'L', 'X', 'U', 'V', 'P', 'N', 'F'};
    public static UI ui = new UI(horizontalGridSize, verticalGridSize, 50);
    public static boolean[] used = new boolean[input.length];
    private static Set<String> encounteredStates = new HashSet<>();
    private static int iteration = 0;

    public static void main(String[] args) {
        search();
    }

    public static void search() {
        int[][] field = new int[horizontalGridSize][verticalGridSize];
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                field[i][j] = -1;
            }
        }
        encounteredStates.clear();
        iteration = 0;
        Arrays.fill(used, false);
        solve(field, 0);
    }

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

    private static void delay() {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

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

    private static boolean solve(int[][] field, int pentIndex) {
        if (pentIndex == input.length) {
            return true;
        }
        for (int x = 0; x < field.length; x++) {
            for (int y = 0; y < field[x].length; y++) {
                if (field[x][y] == -1) {
                    for (int i = 0; i < input.length; i++) {
                        if (!used[i]) {
                            int pentID = characterToID(input[i]);
                            int[][][] pieces = PentominoDatabase.data[pentID];
                            for (int[][] piece : pieces) {
                                if (canAdd(field, piece, x, y)) {
                                    int[][] newField = addPieceWithClone(field, piece, pentID, x, y);
                                    used[i] = true;
                                    String state = boardToString(newField);
                                    if (!encounteredStates.contains(state)) {
                                        encounteredStates.add(state);
                                        ui.setState(newField);
                                        delay();
                                        if (solve(newField, pentIndex + 1)) {
                                            return true;
                                        }
                                    }
                                    used[i] = false;
                                }
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }
}