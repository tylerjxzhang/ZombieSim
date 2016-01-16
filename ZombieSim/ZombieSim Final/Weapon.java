import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The weapon icon will be used to indicate that the scout is currently carrying weapon supply.
 * 
 * @author Tyler Zhang
 * @version Oct 2014
 */
public class Weapon extends Icons
{
    /**
     * Create a weapon icon that will follow its creator.
     * 
     * @param par The parent actor that creates the icon.
     */
    public Weapon(Scouts par)
    {
        parent = par;
    }
}

