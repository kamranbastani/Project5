import processing.core.PImage;

import java.util.List;

public abstract class Plant extends Health {
    private double actionPeriod;

    public Plant(String id, Point position, List<PImage> images, double animationPeriod, int health, double actionPeriod) {
        super(id, position, images, animationPeriod, health);
        this.actionPeriod = actionPeriod;
    }

    public boolean transformPlant(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        Entity stump = Functions.createStump(Functions.STUMP_KEY + "_" + getId(), getPosition(), imageStore.getImageList(Functions.STUMP_KEY));
        world.removeEntity(scheduler, this);
        world.addEntity(stump);
        return true;
    }

    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this, this.createActivityAction(world, imageStore), actionPeriod);
        super.scheduleActions(scheduler, world, imageStore);
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        if (!this.transformPlant(world, scheduler, imageStore)) {
            scheduler.scheduleEvent(this, this.createActivityAction(world, imageStore), actionPeriod);
        }
    }
}
