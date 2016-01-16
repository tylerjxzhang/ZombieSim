import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;

/**
 * Turret object to be used for firing projectiles
 * 
 * @author Marco Ly
 * @version Oct 2014
 */
public class Turret extends Actor
{
    // initializing instance variables
    private int width = getImage().getWidth();
    private int height = getImage().getHeight();
    private int shootRange;
    private List<Zombie> targets; // a list of zombies in firing range
    /**
     * Mutator that sets the shoot range of the turret
     * 
     * @param range    the range of firing for the turret
     */
    public void setShootRange(int range) {
        shootRange = range;
    }
    
    /**
     * Returns the rotation of the turret in radians
     * 
     * @return double   the rotation of the turret in radians
     */
    public double getRadRotation() {
        return Math.toRadians(getRotation());
    }

    /**
     * Returns the length of the turret
     * 
     * @return int  the length of the turret
     */
    public int getLength() {
        return width/2;
    }

    /**
     * Finds zombie targets within range shootRange. Finds the nearest target and has the turret turn towards that target
     * 
     * @return boolean  whether or not there are zombie targets within the range of the turret
     */
    public boolean findTargets () {
        targets = getObjectsInRange(shootRange, Zombie.class);
        if (targets.size() == 0) {
            return false;
        } else {
            Zombie target = getNearestTarget(targets);
            turnTowards(target.getX(),target.getY());
            return true;
        }
    }

    /**
     * Return the nearest zombie to the turret from the targets list
     * 
     * @param targets   a list of zombie objects 
     * @return Zombie   the closest zombie from the targets list to the base
     */
    private Zombie getNearestTarget(List<Zombie> targets)
    {
        Zombie nearTarget = null;
        // sets an impossible nearest distance to be corrected later
        double nearestDistance = 9999;
        for (int i = 0; i < targets.size(); i++) {
            double distance = getDistance(targets.get(i));
            if (distance < nearestDistance) {
                // finds the nearest distance between all objects in targets
                nearTarget = targets.get(i);
                nearestDistance = distance;
            }
        }
        return nearTarget;
    }

    /**
     * Return the distance from this to another object.
     * 
     * @param actor     the actor whose distance is being determined
     * @return double   distance from this object to actor
     */
    private double getDistance(Actor actor) {
        return Math.hypot(actor.getX() - getX(), actor.getY() - getY());
    }
}
