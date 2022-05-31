package doctor;

public class Node {
    private Doctor date;
    private int height;
    public Node left;
    public Node right;

    public Node(Doctor date){
        this.date = date;
    }

    public Doctor getDate() {
        return date;
    }

    public int getHeight() {
        return height;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setDate(Doctor date) {
        this.date = date;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public void setRight(Node right) {
        this.right = right;
    }
}
