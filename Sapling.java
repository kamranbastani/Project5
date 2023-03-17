import processing.core.PImage;

import java.util.List;

/**
 * An entity that exists in the world. See EntityKind for the
 * different kinds of entities that exist.
 */
public final class Sapling extends Plant {
    private final int healthLimit;

    public Sapling(String id, Point position, List<PImage> images, double actionPeriod, double animationPeriod, int health, int healthLimit) {
        super(id, position, images, animationPeriod, health, actionPeriod);
        this.healthLimit = healthLimit;
    }

    public boolean transformPlant(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        if (getHealth() <= 0) {
            super.transformPlant(world, scheduler, imageStore);
        } else if (getHealth() >= healthLimit) {
            Entity tree = Functions.createTree(Functions.TREE_KEY + "_" + getId(), getPosition(), Functions.getNumFromRange(Functions.TREE_ACTION_MAX, Functions.TREE_ACTION_MIN), Functions.getNumFromRange(Functions.TREE_ANIMATION_MAX, Functions.TREE_ANIMATION_MIN), Functions.getIntFromRange(Functions.TREE_HEALTH_MAX, Functions.TREE_HEALTH_MIN), imageStore.getImageList(Functions.TREE_KEY));

            world.removeEntity(scheduler, this);

            world.addEntity(tree);
            ((Animates)tree).scheduleActions(scheduler, world, imageStore);

            return true;
        }

        return false;
    }


    public void executeSaplingActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        incHealth();
        executeActivity(world, imageStore, scheduler);
    }
}