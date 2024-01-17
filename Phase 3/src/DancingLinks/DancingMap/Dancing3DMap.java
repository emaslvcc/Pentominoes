package DancingLinks.DancingMap;

public class Dancing3DMap {
    private DancingNode root;
    private int dimensionX;
    private int dimensionY;
    private int dimensionZ;

    public Dancing3DMap(int dimensionX, int dimensionY, int dimensionZ) {
        new DancingNode();
        this.dimensionX = dimensionX;
        this.dimensionY = dimensionY;
        this.dimensionZ = dimensionZ;

        DancingNode[][][] tempMap = new DancingNode[dimensionX][dimensionY][dimensionZ];
        for (int x = 0; x < dimensionX; x++) {
            for (int y = 0; y < dimensionY; y++) {
                for (int z = 0; z < dimensionZ; z++) {
                    tempMap[x][y][z] = new DancingNode();
                }
            }
        }
        root = tempMap[0][0][0];
        for (int x = 0; x < dimensionX; x++) {
            for (int y = 0; y < dimensionY; y++) {
                for (int z = 0; z < dimensionZ; z++) {
                    if (x > 0) {
                        tempMap[x][y][z].setLeft(tempMap[x - 1][y][z]);
                    }
                    if (x < dimensionX - 1) {
                        tempMap[x][y][z].setRight(tempMap[x + 1][y][z]);
                    }
                    if (y > 0) {
                        tempMap[x][y][z].setBottom(tempMap[x][y - 1][z]);
                    }
                    if (y < dimensionY - 1) {
                        tempMap[x][y][z].setTop(tempMap[x][y + 1][z]);
                    }
                    if (z > 0) {
                        tempMap[x][y][z].setBack(tempMap[x][y][z - 1]);
                    }
                    if (z < dimensionZ - 1) {
                        tempMap[x][y][z].setFront(tempMap[x][y][z + 1]);
                    }
                }
            }
        }
        System.out.println(root);

    }

    public DancingNode getRoot() {
        return root;
    }
    public int[][][] toTruckIntArray() {
        int[][][] truckArray = new int[dimensionX][dimensionY][dimensionZ];
        DancingNode currentNode = root;

        for (int x = 0; x < dimensionX; x++) {
            DancingNode rowStartNode = currentNode;

            for (int y = 0; y < dimensionY; y++) {
                DancingNode colStartNode = currentNode;

                for (int z = 0; z < dimensionZ; z++) {
                    if (currentNode.getOccupant() == null)
                        truckArray[x][y][z] = -1;
                    else
                        truckArray[x][y][z] = currentNode.getOccupant().getNum();

                    // Move to the next node in the Z-axis, if available
                    if (z < dimensionZ - 1) {
                        currentNode = currentNode.getFront();
                    }
                }

                // Move to the next row in the Y-axis
                if (y < dimensionY - 1) {
                    currentNode = colStartNode.getTop();
                }
            }

            // Move to the next layer in the X-axis
            if (x < dimensionX - 1) {
                currentNode = rowStartNode.getRight();
            }
        }

        return truckArray;
    }
}   