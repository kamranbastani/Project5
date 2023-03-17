import processing.core.PImage;

import java.util.List;

public abstract class Health extends Animates {
    private int health;

    public Health(String id, Point position, List<PImage> images, double animationPeriod, int health){
        super(id, position, images, animationPeriod);
        this.health = health;
    }
    public int getHealth() {
        return health;
    }

    public void decHealth() {
        health--;
    }

    public void incHealth() {
        health++;
    }
}
