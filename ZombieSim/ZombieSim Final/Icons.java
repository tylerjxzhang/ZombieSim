import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The icon that will be used to indicate the type of supply carried by a human.
 * The icon, once created will be located above its creator.
 * 
 * @author Tyler Zhang
 * @version Oct 2014
 */
public class Icons extends Actor
{
    protected Humans parent; //The target that the icon will follow. 
    
    /**
     * Set the location of the icon to be above the human character.
     */
    public void act() 
    {
        try{
            setLocation(parent.getX(), parent.getY()-30);
        }catch(Exception e){ //If there is an error, remove this icon.
            getWorld().removeObject(this);
        }
    }

    /**
     * Return the target that the icon is following.
     * 
     * @return Humans The human object being followed
     */
    public Humans getTarget()
    {
        return parent; //Return the reference to the human object.
    }
}
