import processing.core.PImage;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public abstract class Dude extends Health{
    private double actionPeriod;
    private int resourceLimit;

    public Dude(String id, Point position, List<PImage> images, double animationPeriod, int health, double actionPeriod, int resourceLimit) {
        super(id, position, images, animationPeriod, health);
        this.actionPeriod = actionPeriod;
        this.resourceLimit = resourceLimit;
    }

    public double getActionPeriod(){
        return actionPeriod;
    }

    public int getResourceLimit(){
        return resourceLimit;
    }

    public Point nextPositionDude(Point destPos, WorldModel worldModel) {
        /*PathingStrategy strat = new SingleStepPathingStrategy();
        int horiz = Integer.signum(destPos.getX() - getPosition().getX());
        Point newPos = new Point(getPosition().getX() + horiz, getPosition().getY());
        Predicate<Point> canPass = p -> horiz == 0 || worldModel.isOccupied(newPos) && worldModel.getOccupancyCell(newPos).getClass() != Stump.class;
        BiPredicate<Point, Point> isAdj = (Point p1, Point p2) -> p1.adjacent(p2);
        List<Point> path = strat.computePath(getPosition(), destPos, canPass, isAdj, PathingStrategy.CARDINAL_NEIGHBORS);
        return path.get(0); // add a check for if path is null / empty -> current pos if so

        int horiz = Integer.signum(destPos.getX() - getPosition().getX());
        Point newPos = new Point(getPosition().getX() + horiz, getPosition().getY());

        if (horiz == 0 || worldModel.isOccupied(newPos) && worldModel.getOccupancyCell(newPos).getClass() != Stump.class) {
            int vert = Integer.signum(destPos.getY() - getPosition().getY());
            newPos = new Point(getPosition().getX(), getPosition().getY() + vert);

            if (vert == 0 || worldModel.isOccupied(newPos) && worldModel.getOccupancyCell(newPos).getClass() != Stump.class) {
                newPos = getPosition();
            }
        }

        return newPos;*/

        PathingStrategy strat = new AStarPathingStrategy();
        Predicate<Point> canPass = p -> worldModel.withinBounds(p) &&
                (!worldModel.isOccupied(p) || worldModel.getOccupancyCell(p).getClass() == Stump.class);
        BiPredicate<Point, Point> isAdj = (Point p1, Point p2) -> p1.adjacent(p2);
        List<Point> path = strat.computePath(getPosition(), destPos, canPass, isAdj, PathingStrategy.CARDINAL_NEIGHBORS);

        if (path.size()!=0){
            return path.get(0);
        }

        return getPosition();
    }

    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this, this.createActivityAction(world, imageStore), getActionPeriod());
        super.scheduleActions(scheduler, world, imageStore);
    }

    public boolean move(WorldModel world, Entity target, EventScheduler scheduler){
        Point nextPos = nextPositionDude(target.getPosition(), world);

        if (!getPosition().equals(nextPos)) {
            world.moveEntity(scheduler, this, nextPos);
        }
        return false;
    }

    void transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        Entity dude = Functions.createDudeNotFull(getId(), getPosition(), getActionPeriod(), getAnimationPeriod(), getResourceLimit(), getImages());

        world.removeEntity(scheduler, this);

        world.addEntity(dude);
        ((Animates)dude).scheduleActions(scheduler, world, imageStore);
    }
}
