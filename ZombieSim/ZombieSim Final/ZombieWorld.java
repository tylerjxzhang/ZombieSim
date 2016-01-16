import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * The controller of the actors.
 * 
 * @author Tyler Zhang, Marco Ly, Jasper Tu, Ahrenn Sivananthan
 * @version Oct 2014
 */
public class ZombieWorld extends World
{
    private GreenfootSound theme; // the background theme music
    private GreenfootSound fly; // the sound of the plane flying overhead
    private StatBar statbar; //The general stat bar that will display statistics for the actors on screen.

    private List <Supplies> supplies; // the list that will hold references to all the supplies objects on screen
    private int totalSupply; //Keep track of the total number of supplies dropped.

    private int planeSpawnCounter; // a counter between plane spawns
    private int randomPlaneFacing; // random direction that the plane faces

    private int zombieOnScreen; // number of zombies on screen
    private int zombieDie; // number of zombie deaths since last spawn
    private int totalZombieSpawn; // number of zombie spawn in total

    private int scoutOnScreen; // number of scouts on screen.
    private int soldierOnScreen; // number of soldier on screen.

    private int humanKilledCount; // the total number of human death.
    private int humanConvertedCount; // the total number of human been convert to zombie.
    private int zombieKilledCount; // the total number of zombie that have been killed.
    private int maxZombieSpawn; // the maximum zombies allowed to be spawned

    // references to bases that will be added to world
    private Base base1; 
    private Base base2;
    private Base base3;
    private Base base4;
    /**
     * Creates the world in which the whole simulation will take place.
     * 
     * @param theme the theme music being passed from this world to the this one
     * @param store the object carrying the modifiable variables from one world to this one
     */
    public ZombieWorld(GreenfootSound theme, ModificationStorage store)
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(960, 640, 1); 
        this.theme = theme;
        theme.setVolume(35);
        fly = new GreenfootSound("airplane-fly-by-02.mp3"); // sets the fly sound
        // sets the paint order of all important objects
        setPaintOrder(StatBar.class, Statistics.class, Turret.class, Plane.class, Parachute.class, Projectile.class, Icons.class, Char.class, Turret.class, Structure.class, Obstacle.class);
        //Create general statbar and add it to world.
        statbar = new StatBar();
        addObject(statbar, 480, 80);
        // creates instances of bases
        base1 = new Base(1, store.getPopulation(), store.getFood(), store.getWeapons());
        base2 = new Base(2, store.getPopulation(), store.getFood(), store.getWeapons());
        base3 = new Base(3, store.getPopulation(), store.getFood(), store.getWeapons());
        base4 = new Base(4, store.getPopulation(), store.getFood(), store.getWeapons());
        maxZombieSpawn = store.getMaxZombieSpawn(); // sets the maxZombieSpawn
        // prepares world with bases, obastacles, and supplies
        prepare();
        // initialize all the instance variable.
        zombieOnScreen = 0;
        scoutOnScreen = 0;
        soldierOnScreen= 0;
        humanKilledCount = 0;
        zombieKilledCount = 0;
        humanConvertedCount = 0;
        totalZombieSpawn = 0;
        totalSupply = getObjects(Supplies.class).size();
        zombieDie = 0;
    }

    /**
     * What the world will do every time the program runs.
     */
    public void act()
    {
        //If there are less than 5 zombies on the screen, spawn a new zombie.
        //When there are less than 20 zombies on screen, everytime 5 zombie die, spawn a new zombie.
        if(totalZombieSpawn < maxZombieSpawn  && zombieOnScreen < 20 && (zombieOnScreen < 5 || zombieDie > 10)){
            zombieDie = 0; //Clear the zombie die counter.
            spawnZombie(); //Spawn new zombie.
        }

        updateValue(); //Update value for zombie, scout, soldier on screen.
        spawnSupplyPlane(); //Try spawning supply plane.
        if (planeSpawnCounter >= 350 && fly.isPlaying()) {
            fly.stop();
        }
        checkEnd();
    }

    /**
     * Increases the humanKilledCount by 1.
     */ 
    public void humanKilled()
    {
        humanKilledCount++;
    }

    /**
     * Increases the zombieKilledCount by 1.
     */
    public void zombieKilled()
    {
        zombieKilledCount++;
    }

    /**
     * One supply is addded to the screen.
     * Increases the totalSupply by 1.
     */
    public void supplyDropped()
    {
        totalSupply++;
    }

    /**
     * A number of supplies are added to the screen.
     * Increases the totalSupply by a number.
     * 
     * @param num The number of supplies added to the world.
     */
    public void supplyDropped(int num)
    {
        totalSupply += num;
    }

    /**
     * Increases the humanConvertedCount by 1.
     */
    public void humanConverted()
    {
        humanConvertedCount++;
    }

    /**
     * Updates the number of actors on screen currently.
     */
    private void updateValue()
    {
        zombieOnScreen = getObjects(Zombie.class).size();
        scoutOnScreen = getObjects(Scouts.class).size();
        soldierOnScreen = getObjects(Soldiers.class).size();
    }

    /**
     * Passes the reference to a base.
     * 
     * @param baseId The id for the base required.
     */
    public Base getBase(int baseId)
    {
        if (baseId == 1){
            return base1;
        }else if (baseId == 2){
            return base2;
        }else if (baseId == 3){
            return base3;
        }else{
            return base4;
        }
    }

    /**
     * Prepares the world for the start of the program. That is: creates the initial
     * objects and adds them to the world.
     */
    private void prepare()
    {
        addObject(base1, 820, 88);
        addObject(base2, 139, 85);
        addObject(base3, 141, 550);
        addObject(base4, 818, 551);

        addObject(new Rock(), 495, 335);
        addObject(new Rock(), 504, 241);
        addObject(new Rock(), 506, 137);
        addObject(new Rock(), 502, 449);
        addObject(new Rock(), 511, 566);
        addObject(new Rock(), 642, 344);
        addObject(new Rock(), 777, 348);
        addObject(new Rock(), 368, 345);
        addObject(new Rock(), 232, 353);
        addObject(new Rock(), 897, 277);    
        addObject(new Rock(), 892, 409);
        addObject(new Rock(), 111, 289);
        addObject(new Rock(), 115, 419);
        addObject(new Rock(), 376, 248);
        addObject(new Rock(), 644, 242);
        addObject(new Rock(), 375, 459);
        addObject(new Rock(), 646, 459);

        addObject(new Tree(), 414, 168);
        addObject(new Tree(), 627, 170);
        addObject(new Tree(), 757, 280);
        addObject(new Tree(), 750, 437);
        addObject(new Tree(), 608, 561);
        addObject(new Tree(), 435, 545);
        addObject(new Tree(), 294, 434);
        addObject(new Tree(), 303, 298);
        addObject(new Tree(), 116, 359);
        addObject(new Tree(), 900, 349);
        addObject(new Tree(), 519, 56);
        addObject(new Tree(), 378, 85);
        addObject(new Tree(), 349, 602);
        addObject(new Tree(), 213, 241);
        addObject(new Tree(), 575, 396);

        addObject(new Supplies(), 437, 266);
        addObject(new Supplies(), 696, 398);
        addObject(new Supplies(), 577, 289);
        addObject(new Supplies(), 436, 417);
        addObject(new Supplies(), 308, 366);
    }

    /**
     * Spawns a supply drop plane at the left or right extremities of the world
     */
    private void spawnSupplyPlane () {
        planeSpawnCounter++;
        randomPlaneFacing = Greenfoot.getRandomNumber(2);
        supplies = getObjects(Supplies.class);
        // If there is only 1 supply crate left with food or weapon values of 0
        if (supplies.size() == 1 && supplies.get(0).getFood() == 0 || supplies.size() == 1 && supplies.get(0).getWeapons() == 0 || supplies.size() == 0) {
            // If the planeSpawnCounter is great or equal to 850 and the random plane orientation is 0, create a right-facing plane at left side of world.
            // if the plane orientation is 1, spawn a left-facing plane at right side of world.
            if (randomPlaneFacing == 0 && planeSpawnCounter >= 850) {
                addObject(new Plane(true), 2, 320);
                fly.play();
                planeSpawnCounter = 0;
            } else if (randomPlaneFacing == 1 && planeSpawnCounter >= 850) {
                addObject(new Plane(false), 958, 320);
                fly.play();
                planeSpawnCounter = 0;
            }
        }
    }

    /**
     * Increases the value of zombieDie by one.
     */
    public void oneZombieDie()
    {
        zombieDie++;
        zombieOnScreen--;
    }

    /**
     * Spawns a zombie char at a random position around one of the four sides.
     */
    private void spawnZombie ()
    {
        int side = Greenfoot.getRandomNumber(4); //Choose which side, the zombie will come in from.

        //The x,y coordinate of the new zombie.
        int x; 
        int y;

        //Determin the x coordinate.
        if (side == 0){
            x = 5;
        }else if (side == 2){
            x = 955;
        }else{
            x = Greenfoot.getRandomNumber(440) + 260;
        }

        //Determine the y coordinate.
        if (side == 1){
            y = 5;
        }else if(side == 3){
            y = 635;
        }else{
            y = Greenfoot.getRandomNumber(345) + 150;
        }

        Zombie zom = new Zombie();
        addObject(zom, x, y); //Add the zombie to world.
        totalZombieSpawn++;
        zom.turnTowards(getWidth()/2, getHeight()/2); // Set the direction of the zombie to be towards the center of the screen.
    }

    /**
     * Returns the humanKilledCount.
     * 
     * @return int The total number of people killed by zombie.
     */
    public int getHumanKilledCount()
    {
        return humanKilledCount;
    }

    /**
     * Returns the humanConvertedCount.
     *
     * @return int The total number of people converted by zombie.
     */
    public int getHumanConvertedCount()
    {
        return humanConvertedCount;
    }

    /**
     * Returns the zombieKilledCount.
     * 
     * @return int The total number of zombie killed by bullet.
     */
    public int getZombieKilledCount()
    {
        return zombieKilledCount;
    }

    /**
     * Returns the scoutOnScreen.
     * 
     * @return int The total number of scout on screen currently.
     */
    public int getScoutOnScreen()
    {
        return scoutOnScreen;
    }

    /**
     * Returns the soldierOnScreen.
     * 
     * @return int The total number of soldier on screen currently.
     */
    public int getSoldierOnScreen()
    {
        return soldierOnScreen;
    }

    /**
     * Returns the zombieOnScreen.
     * 
     * @return int The total number of zombie on screen currently.
     */
    public int getZombieOnScreen()
    {
        return zombieOnScreen;
    }

    /**
     * Returns the total supply number.
     * 
     * @return int The total number of supply box ever spawned.
     */
    public int getTotalSupply()
    {
        return totalSupply;   
    }

    /**
     * Return the number of total zombie spawned.
     */
    public int getTotalZombieSpawn()
    {
        return totalZombieSpawn;
    }

    /**
     * Checks for the simulation ending and sets the appropriate end screen
     */
    private void checkEnd() {
        if (base1.getPopulation() == 0 && base2.getPopulation() == 0 && base3.getPopulation() == 0 && base4.getPopulation() == 0 && getObjects(Humans.class).size() == 0) {
            theme.stop();
            Greenfoot.setWorld(new EndScreen1());
        } else if (totalZombieSpawn >= maxZombieSpawn && getObjects(Zombie.class).size() == 0) {
            theme.stop();
            Greenfoot.setWorld(new EndScreen2());
        }
    }

    /**
     * Starts playing the theme of the zombie simulation.
     */
    public void started()  
    {  
        theme.playLoop();  
    }

    /**
     * Stops playing the theme when the simulation is not running
     */
    public void stopped()
    {
        theme.pause();
        fly.pause();
    }
}