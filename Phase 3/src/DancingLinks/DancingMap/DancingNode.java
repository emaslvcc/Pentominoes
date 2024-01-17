package DancingLinks.DancingMap;

import Database.SmartParcel;

public class DancingNode implements Chained {
    private SmartParcel occupant = null;
    private DancingNode Left;
    private DancingNode Right;
    private DancingNode Top;
    private DancingNode Bottom;
    private DancingNode Front;
    private DancingNode Back;

    // Getters for each direction
    public DancingNode getLeft() {
        return Left;
    }
    public DancingNode getRight() {
        return Right;
    }
    public DancingNode getTop() {
        return Top;
    }
    public DancingNode getBottom() {
        return Bottom;
    }
    public DancingNode getFront() {
        return Front;
    }
    public DancingNode getBack() {
        return Back;
    }
    // Setters 
    public void setLeft(DancingNode left) {
        Left = left;
    }
    public void setRight(DancingNode right) {
        Right = right;
    }
    public void setTop(DancingNode top) {
        Top = top;
    }
    public void setBottom(DancingNode bottom) {
        Bottom = bottom;
    }
    public void setFront(DancingNode front) {
        Front = front;
    }
    public void setBack(DancingNode back) {
        Back = back;
    }

    public void setOccupant(SmartParcel newOccupant) {
        occupant = newOccupant;
    }

    public SmartParcel getOccupant() {
        return occupant;
    }

    public DancingNode () {

    }
}