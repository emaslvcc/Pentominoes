package Database;

import DancingLinks.DancingMap.DancingNode;

public class ParcelF extends SmartParcel {

    public DancingNode parcelRootNode;

    /**
     * A constructor that creates a parcel F object
     **/
    public ParcelF() {
        super('F');
        parcelRootNode = new DancingNode();

        // The most lower cube is root
        // The inner middle cube:
        DancingNode middleNode = new DancingNode();
        middleNode.setBottom(parcelRootNode);
        parcelRootNode.setTop(middleNode);

        // The middle left cube
        DancingNode middleLeftNode = new DancingNode();
        middleLeftNode.setRight(middleNode);
        middleNode.setLeft(middleLeftNode);

        // The middle top cube
        DancingNode topNode = new DancingNode();
        topNode.setBottom(middleNode);
        middleNode.setTop(topNode);

        // The top right cube
        DancingNode topRightNode = new DancingNode();
        topRightNode.setLeft(topNode);
        topNode.setRight(topRightNode);
    }
}
