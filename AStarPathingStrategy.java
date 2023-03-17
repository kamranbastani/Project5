import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class AStarPathingStrategy implements PathingStrategy {
    public List<Point> computePath(Point start, Point end,
                                   Predicate<Point> canPassThrough,
                                   BiPredicate<Point, Point> withinReach,
                                   Function<Point, Stream<Point>> potentialNeighbors)
    {
        List<Point> path = new LinkedList<Point>();
        Node current = new Node(0, 0, start);
        PriorityQueue<Node> open = new PriorityQueue<>(Comparator.comparing(Node::getF));
        List<Node> closed = new ArrayList<>();

        while(!withinReach.test(current.getLoc(), end)){
            for(Point p: potentialNeighbors.apply(current.getLoc()).filter(canPassThrough).collect(Collectors.toList())){
                Node n = new Node(p);
                n.setG(current.getG() + 1);
                n.setH(Math.abs(n.getLoc().getX() - end.getX()) + Math.abs(n.getLoc().getY() - end.getY()));
                n.setF();
                n.setPrevNode(current);
                if(!closed.contains(n)){
                    if(open.contains(n)) {
                        if(open.removeIf(node -> n.equals(node) && n.getG() < node.getG())){
                            open.add(n);
                        }
                    }
                    else {
                        open.add(n);
                    }
                }
            }

            open.remove(current);
            closed.add(current);
            current = open.peek();
            if(current == null){
                break;
            }
        }

        if(current == null){
            return path;
        }

        while(current.getPrevNode() != null){
            path.add(0, current.getLoc());
            current = current.getPrevNode();
        }

        return path;
    }
}
