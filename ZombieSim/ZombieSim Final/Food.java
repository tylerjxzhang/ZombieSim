import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Used to indicate the type of supply that the character is carrying.
 * 
 * @author Tyler Zhang
 * @version Oct 2014
 */
public class Food extends Icons
{   
    /**
     * Create a food icon that will follow its creator.
     * 
     * @param par The parent actor that creates the icon.
     */
    public Food(Scouts par)
    {
        parent = par;
    }
}
