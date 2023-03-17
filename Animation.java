/**
 * An action that can be taken by an entity
 */
public final class Animation extends Action {
    private final int repeatCount;

    public Animation(Entity entity, int repeatCount) {
        super(entity);
        this.repeatCount = repeatCount;
    }

    public void executeAction(EventScheduler scheduler) {
        getEntity().nextImage();

        if (repeatCount != 1) {
            scheduler.scheduleEvent(getEntity(), ((Animates)getEntity()).createAnimationAction(Math.max(repeatCount - 1, 0)), ((Animates)getEntity()).getAnimationPeriod());
        }
    }
}
