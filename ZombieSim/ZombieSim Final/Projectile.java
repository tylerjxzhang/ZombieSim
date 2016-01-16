import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * An abstract category for projectiles that stores common methods and variables
 * 
 * @author Marco Ly
 * @version Oct 2014
 */
public abstract class Projectile extends Actor
{
    // initializing protected instance variables
    protected int width = getImage().getWidth();
    protected int height = getImage().getHeight();
    protected int Vx; // the velocity of the projectile in the X direction
    protected int Vy; // the velocity of the projectile in the Y direction
    protected Zombie target; // the targeted zombie object of projectile
    
    /**
     * Creating abstract hasHit class that will detect when the projectile hits  certain objects
     * 
     * @return boolean  whether or not it has hit 
     */
    abstract boolean hasHit();

    /**
     * Inflicts damage on hit object if it is a zombie object then removes the projectile
     * 
     * @param damage    the damage to be dealt
     */
    protected void hitObject (int damage) {
        // checks if the projectile is in contact with a zombie object
        if (getOneObjectAtOffset(0, 0, Zombie.class) != null) {
            // creates a reference to the zombie
            target = (Zombie)getOneObjectAtOffset(0, 0, Zombie.class);
            // interacts with target to cause it to lose health
            target.loseHealth(damage);
        }
        // removes the projectile
        getWorld().removeObject(this);
    }

    /**
     * Returns true if it is out of the bounds of the world
     * 
     * @return boolean  whether or not the bullet has reached the edge of the world
     */
    protected boolean outOfBounds() { 
        if(getX() >= 959 || getX() <= 0) {
            return true;
        } else if (getY() >= 639 || getY() <= 0) {
            return true;
        }
        return false;
    }
}
