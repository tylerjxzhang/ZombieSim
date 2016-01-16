import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A marker for spawning parachutes whose sole purpose is to determine whether or not the supply crate dropped by a parachute will be touching an obstacle
 * 
 * @author Marco Ly
 * @version Oct 2014
 */
public class ParachuteMarker extends SupplySpawning
{
    // initializing instance variables
    private ZombieWorld myWorld;
    /**
     * Method that looks for if the marker is touching an object upon creation, and if so, removes itself from world before acting may occur.
     * Since the marker is the approximate size of a supply crate, the method isTouching suffices
     */
    public void addedToWorld(World ZombieWorld) {
        // removes itself if is touching an obstacle
        if (isTouching(Obstacle.class)){
            getWorld().removeObject(this);
        }
    }
    
    /**
     * Creates a parachute at this object's coordinates and removes the marker from the world
     */
    public void act () {
        myWorld = (ZombieWorld)getWorld();
        getWorld().addObject(new Parachute(), getX(), getY());
        myWorld.supplyDropped();
        getWorld().removeObject(this);
    }
}
