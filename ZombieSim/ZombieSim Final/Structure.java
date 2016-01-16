import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * abstract class for structures; Bases and supply crates
 * 
 * @author Marco Ly
 * @version Sept 2014
 */
public abstract class Structure extends Actor
{
    // instance variables
    protected int weapons; // total weapons owned
    protected int food; // total food owned
    /**
     * Returns amount of food this structure owns
     * 
     * @return int  total food stockpile of the structure
     */
    protected int getFood() {
        return food;
    }

    /**
     * Returns amount of weapons this structure owns
     * 
     * @return int  total weapons cache of the structure
     */
    protected int getWeapons() {
        return weapons;
    }
}
