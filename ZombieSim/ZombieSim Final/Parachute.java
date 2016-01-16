import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Object that shrinks untill a certain size and spawns a supply crate. Used to simulate a parachute falling gradually towards the ground
 * 
 * @author Marco Ly
 * @version Oct 2014
 */
public class Parachute extends SupplySpawning
{
    // Initializing instance variables
    private int width = getImage().getWidth();
    private int height = getImage().getHeight();
    private int counter;
    public Parachute () {
        // Sets the default image to a more appropriate size
        width -= 40;
        height -= 40;
        getImage().scale(width, height);
    }

    /**
     * Act - do whatever the Parachute wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        counter++;
        // Decreases the width and height of the parachute image every 3 actions
        if (counter >= 3) {
            width -= 2;
            height -= 2;
            counter = 0;
        }
        // When the parachute has reached the appropriate size (the approximate size of the supply crate image) then it will run dropSupplies() and remove itself from the world
        if (height <= 29 || width <= 28) {
            dropSupplies();
            getWorld().removeObject(this);
        }
        // Udates the image 
        updateImage();
    }    

    private void updateImage() {
        // Clears image and sets it to a new parachute image scaled to width and height, as width and height may continually be changing
        getImage().clear();
        setImage("Parachute.png");
        getImage().scale(width, height);
    }

    private void dropSupplies() {
        // Drops a supply crate to the parachute's coordinates
        getWorld().addObject(new Supplies(), getX(), getY());
    }
}
