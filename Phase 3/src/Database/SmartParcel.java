package Database;

import DancingLinks.DancingMap.DancingNode;

import java.util.HashSet;
import java.util.Set;

public class SmartParcel extends Parcel {
    /**
     * A constructor that creates a parcel object
     *
     * @param c a character correspondent to a parcel (A, B or C)
     **/
    public SmartParcel(char c) {
        super(c);
    }
    protected DancingNode parcelRootNode;
    public DancingNode getParcelMap() {
        return parcelRootNode;
    }

    @Override
    public boolean rotate(){
        if (super.rotate()) {
            //stolen logic from Parcel class, this is not an ideal implementation
            DancingNode parcelRootNodeTop, parcelRootNodeBottom, parcelRootNodeLeft, parcelRootNodeRight, parcelRootNodeFront, parcelRootNodeBack;

            switch(getRotation()){
                case 0: //default
                    return true;
                case 1:
                    // Already implemented in your example
                    parcelRootNodeTop = parcelRootNode.getTop();
                    parcelRootNodeBottom = parcelRootNode.getBottom();
                    parcelRootNode.setTop(parcelRootNode.getFront());
                    parcelRootNode.setBottom(parcelRootNode.getBack());
                    parcelRootNode.setFront(parcelRootNodeTop);
                    parcelRootNode.setBack(parcelRootNodeBottom);
                    break;
                case 2:
                    parcelRootNodeTop = parcelRootNode.getTop();
                    parcelRootNodeBottom = parcelRootNode.getBottom();
                    parcelRootNodeLeft = parcelRootNode.getLeft();
                    parcelRootNodeRight = parcelRootNode.getRight();
                    parcelRootNode.setTop(parcelRootNodeRight);
                    parcelRootNode.setBottom(parcelRootNodeLeft);
                    parcelRootNode.setLeft(parcelRootNodeBottom);
                    parcelRootNode.setRight(parcelRootNodeTop);
                    break;
                case 3:
                    parcelRootNodeLeft = parcelRootNode.getLeft();
                    parcelRootNodeRight = parcelRootNode.getRight();
                    parcelRootNodeFront = parcelRootNode.getFront();
                    parcelRootNodeBack = parcelRootNode.getBack();
                    parcelRootNode.setLeft(parcelRootNodeFront);
                    parcelRootNode.setRight(parcelRootNodeBack);
                    parcelRootNode.setFront(parcelRootNodeLeft);
                    parcelRootNode.setBack(parcelRootNodeRight);
                    break;
                case 4:
                    parcelRootNodeLeft = parcelRootNode.getLeft();
                    parcelRootNodeRight = parcelRootNode.getRight();
                    parcelRootNodeTop = parcelRootNode.getTop();
                    parcelRootNodeBottom = parcelRootNode.getBottom();
                    parcelRootNodeBack = parcelRootNode.getBack();
                    parcelRootNodeFront = parcelRootNode.getFront();
                    parcelRootNode.setLeft(parcelRootNodeBack);
                    parcelRootNode.setRight(parcelRootNodeFront);
                    parcelRootNode.setTop(parcelRootNodeBottom);
                    parcelRootNode.setBottom(parcelRootNodeTop);
                    parcelRootNode.setFront(parcelRootNodeRight);
                    parcelRootNode.setBack(parcelRootNodeLeft);
                    break;
                case 5:
                    parcelRootNodeFront = parcelRootNode.getFront();
                    parcelRootNodeBack = parcelRootNode.getBack();
                    parcelRootNodeLeft = parcelRootNode.getLeft();
                    parcelRootNodeRight = parcelRootNode.getRight();
                    parcelRootNode.setFront(parcelRootNodeRight);
                    parcelRootNode.setBack(parcelRootNodeLeft);
                    parcelRootNode.setLeft(parcelRootNodeBack);
                    parcelRootNode.setRight(parcelRootNodeFront);
                    break;
                default:
                    return false;
            }
            return true;
        }
        return false;
    }

    

    public boolean attemptToPlace(DancingNode targetRootNode) {
        Set<DancingNode> visited = new HashSet<>();
        if (!canPlace(parcelRootNode, targetRootNode, visited)) {
            return false;
        }

        visited.clear(); // Reset visited set for actual placement
        placeParcel(parcelRootNode, targetRootNode, visited);
        return true;
    }

    private boolean canPlace(DancingNode parcelNode, DancingNode targetNode, Set<DancingNode> visited) {
        if (parcelNode == null || visited.contains(parcelNode)) {
            return true;
        }

        if (targetNode == null /* or targetNode does not meet placement conditions */) {
            return false;
        }

        visited.add(parcelNode); // Mark the current parcel node as visited

        // Check all six directions
        return (parcelNode.getLeft() == null || canPlace(parcelNode.getLeft(), targetNode.getLeft(), visited)) &&
                (parcelNode.getRight() == null || canPlace(parcelNode.getRight(), targetNode.getRight(), visited)) &&
                (parcelNode.getTop() == null || canPlace(parcelNode.getTop(), targetNode.getTop(), visited)) &&
                (parcelNode.getBottom() == null || canPlace(parcelNode.getBottom(), targetNode.getBottom(), visited)) &&
                (parcelNode.getFront() == null || canPlace(parcelNode.getFront(), targetNode.getFront(), visited)) &&
                (parcelNode.getBack() == null || canPlace(parcelNode.getBack(), targetNode.getBack(), visited));
    }

    private void placeParcel(DancingNode parcelNode, DancingNode targetNode, Set<DancingNode> visited) {
        if (parcelNode == null || visited.contains(parcelNode)) {
            return;
        }

        visited.add(parcelNode); // Mark as visited

        // Assume some method exists to update targetNode with parcel's data
        targetNode.setOccupant(this);

        // Recursively place the rest of the parcel, checking all six directions
        if (parcelNode.getLeft() != null) {
            placeParcel(parcelNode.getLeft(), targetNode.getLeft(), visited);
        }
        if (parcelNode.getRight() != null) {
            placeParcel(parcelNode.getRight(), targetNode.getRight(), visited);
        }
        if (parcelNode.getTop() != null) {
            placeParcel(parcelNode.getTop(), targetNode.getTop(), visited);
        }
        if (parcelNode.getBottom() != null) {
            placeParcel(parcelNode.getBottom(), targetNode.getBottom(), visited);
        }
        if (parcelNode.getFront() != null) {
            placeParcel(parcelNode.getFront(), targetNode.getFront(), visited);
        }
        if (parcelNode.getBack() != null) {
            placeParcel(parcelNode.getBack(), targetNode.getBack(), visited);
        }
    }
}
