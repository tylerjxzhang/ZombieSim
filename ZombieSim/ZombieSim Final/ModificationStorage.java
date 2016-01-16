import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A storage object class for storing the modification variables at the start of the simulation
 * Stores initial population, food, weapons, and max zombie spawns allowed
 * 
 * @author Marco Ly
 * @version Oct 2014
 */
public class ModificationStorage extends Actor
{
    private int population;
    private int food;
    private int weapons;
    private int maxZombieSpawn;
    /**
     * Constructor that stores the variables in the object
     * 
     */
    public ModificationStorage() {
        this.population = 25;
        this.food = 100;
        this.weapons = 0;
        this.maxZombieSpawn = 400;
    }
    
    /**
     * Accessor for starting population
     * 
     * @return int  the starting population stored
     */
    public int getPopulation() {
        return population;
    }
    
    /**
     * Accessor for starting food
     * 
     * @return int  the starting food stored
     */
    public int getFood() {
        return food;
    }
    
    /**
     * Accessor for starting weapons
     * 
     * @return int  the starting weapons stored
     */
    public int getWeapons() {
        return weapons;
    }
    
    /**
     * Accessor for maxZombieSpawn
     * 
     * @return int  the max zombie spawn stored
     */
    public int getMaxZombieSpawn() {
        return maxZombieSpawn;
    }    
    
    /**
     * Mutator that updates the stored values
     * 
     * @param population    the starting population given
     * @param food          the starting food given
     * @param weapons       the starting weapons given
     * @param maxZombieSpawn    the max zombie spawn given
     */
    public void updateValues(int population, int food, int weapons, int maxZombieSpawn) {
        this.population = population;
        this.food = food;
        this.weapons = weapons;
        this.maxZombieSpawn = maxZombieSpawn;
    }
}
