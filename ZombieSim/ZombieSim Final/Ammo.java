import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * An ammunition icon made by soldiers that is meant to indicate if the soldier carries ammunition
 * 
 * @author Marco Ly
 * @version Oct 2014
 */
public class Ammo extends Icons
{
    /**
     * Create an ammunition icon that will follow its creator.
     * 
     * @param par The parent actor that creates the icon.
     */
    public Ammo (Soldiers par)
    {
        parent = par;
    }  
}
