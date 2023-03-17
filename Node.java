public class Node {
    private int g;
    private double h;
    private double f;
    private Point loc;
    private Node prevNode;

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public void setH(double h) {
        this.h = h;
    }

    public double getF() {
        return f;
    }

    public void setF() {
        f = g + h;
    }

    public Point getLoc() {
        return loc;
    }

    public Node getPrevNode() {
        return prevNode;
    }

    public void setPrevNode(Node prevNode) {
        this.prevNode = prevNode;
    }

    public Node(int g, double h, Point loc){
        this.g = g;
        this.h = h;
        f = g + h;
        this.loc = loc;
        prevNode = null;
    }

    public Node(Point loc){
        this.loc = loc;
        prevNode = null;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null || obj.getClass() != this.getClass()){
            return false;
        }
        Node n = (Node)obj;
        return n.getLoc().equals(loc);
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash = hash * 31 + loc.hashCode();
        return hash;
    }

    @Override
    public String toString() {
        return loc.toString();
    }
}
