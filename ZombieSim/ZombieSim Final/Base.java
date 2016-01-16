import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;

/**
 * Base objects spawn humans, keep track of the bases supplies and population, and uses a turret to shoot at zombie objects
 * 
 * @author Marco Ly
 * @version Oct 2014
 */
public class Base extends Structure
{
    // Variables that define base and help with the creation of base
    private int baseId; // the identification of the base, from 1-4
    private int height;
    private int width;
    // Variables responsible for spawning humans
    private int randSpawnRate; // random integer for spawning individual humans
    private int spawnXDefault; // the X-coordinate for the corner of the base closest to the center of the world
    private int spawnYDefault; // the Y-coordinate for the corner of the base closest to the center of the world
    private int spawnX; // the specific X coordinate for spawning a single human
    private int spawnY; // the specific Y coordinate for spawning a single human
    // Private information for the functioning of the base
    private int population; // total population inside base
    private int bulletSpeed; // speed of bullets to be fired
    private int rateOfFire; // rate of fire for bullets (lower ROF means more frequent firing) In other words, the required value for ShotDelay before shooting is allowed
    private int shootRange; // range of shooting 
    // Objects and lists the base will own or use
    private GreenfootSound shot;
    private Turret turret; // the turret object to be owned by base
    private Statistics statBar; // the statistics display of the base's important information to be viewed on-screen
    private int statBarX; // the specific X coordinate for the statBar
    private int statBarY; // the specific Y coordinate for the statBar
    private List<Supplies> supplies; // a list of supplies on the screen
    private List<Zombie> totalZombies; // a list of all zombies on the screen
    // Variables used for counting and for delays 
    private int consumptionDelay; // counter for the consumption of food
    private int consumptionRate; // the required value for consumptionDelay before food consumption will be allowed
    private int deathDelay; // counter for dying inside the base due to starvation
    private int deathRate; // the required value for deathDelay before starvation may occur
    private int shotDelay; // the counter used in shooting projectiles
    private int populateCounter; // the counter used for increasing population
    private int populateRate; // the required value for populateCounter before an increase in population is allowed
    private int massSpawnDelay; // delay between instances of spawning a mass of humans
    /**
     * Creates a new base object. 
     * 
     * @param baseNumber    an identification number for which base this is, to set the correct location to spawn humans
     * @param population    the population of the base in number of humans
     * @param startingFood  the starting amount of food the base has
     */
    public Base (int baseNumber, int startingPopulation, int startingFood, int startingWeapons) {
        // sets the image to the appropriate image based on baseNumber
        setImage("Base "+baseNumber+" New.png");
        // assigns values to all important variables
        height = getImage().getHeight();
        width = getImage().getWidth();
        baseId = baseNumber;
        population = startingPopulation;
        food = startingFood;
        weapons = startingWeapons;
        // creates an instance of turret and statBar
        turret = new Turret(); 
        statBar = new Statistics(population, food, weapons);
        // sets the counters and limits of the counterVariables
        consumptionDelay = 0;
        consumptionRate = 100;
        deathDelay = 0;
        deathRate = 85;
        shotDelay = 0;
        populateCounter = 0;
        populateRate = 250;
        // sets variables pretaining to shooting
        shot = new GreenfootSound("New M4 Carbine.mp3");
        shot.setVolume(40);
        bulletSpeed = 15;
        rateOfFire = 20;
        shootRange = 150;
    }

    /**
     * Runs necessary code upon an instance of this object being added to world ZombieWorld
     * 
     * @param ZombieWorld the world this base resides in
     */
    public void addedToWorld(World ZombieWorld) {        
        // finds the corner spawn of the base and the spawn coordinates for statBar
        determineSpawnCoordinates();
        // orients the turret to be looking in the correct default direction
        if (baseId == 1 || baseId == 4) {
            turret.setRotation(180);
        }
        turret.setShootRange(shootRange);
        // finds the appropriate coordinates to spawn statBar
        // adds the turret and statBar objects to world when the base is added to the world
        ZombieWorld.addObject(turret , getX(), getY());
        ZombieWorld.addObject(statBar, statBarX, statBarY);
    }

    /**
     * Act - do whatever the Base wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if (checkEmpty() == false) {
            if (turret.findTargets()) {
                shoot();
            }
            deathDelay++;
        }
        shotDelay++;
        consumeFood();
        populate();
        checkSpawn();
        statBar.updateValues(population, food, weapons);
    }    

    /**
     * Returns the total population of humans in the base
     * 
     * @return int  the population value of base
     */
    public int getPopulation() {
        return population;
    }

    /**
     * Returns the baseId of the base
     * 
     * @return int  the baseId of the base
     */
    public int getBaseID() {
        return baseId;
    }

    /**
     * Adds food to the total food stockpile
     */
    public void addFood(int foodAmount) {
        if (foodAmount > 0) {
            food += foodAmount;
        }
    }

    /**
     * Adds weapons to the total weapon cache
     */
    public void addWeapon(int weaponAmount) {
        if (weaponAmount > 0) {
            weapons += weaponAmount;
        }
    }

    /**
     * Adds a human to the population
     */
    public void humanReturned() {
        population++;
    }

    /**
     * Creates soldiers based on the spawnNumber integer
     * 
     * @param spawnNumber   the number of soldiers to be spawned at once
     */
    private void sendSoldiers(int spawnNumber) {
        for (int i = 0; i < spawnNumber; i++) {
            // if there are people and weapons left in the base to send out
            if (population > 0 && weapons > 0) {
                // take the necessary resources to spawn a soldier and spawn one in world
                weapons--;
                population--;
                randomizeSpawnCoordinates();
                getWorld().addObject(new Soldiers(baseId), spawnX, spawnY);
            }
        }
    }

    /**
     * Creats scouts based on the spawnNumber integer
     * 
     * @param spawnNumber   the number of scouts to be spawned at once
     */
    private void sendScouts(int spawnNumber) {
        supplies = getWorld().getObjects(Supplies.class);
        // if there are any supplies in the world, continue
        if (supplies.size() != 0) {
            for (int i = 0; i < spawnNumber; i++) {
                // if there are people left in the base, continue
                if (population > 0 && food < population) { // If there is less food than there are people, send scouts that prioritize food over weapons
                    population--;
                    randomizeSpawnCoordinates();
                    getWorld().addObject(new Scouts(baseId, true), spawnX, spawnY);
                } else if (population > 0 && food > population) { // If there is less food than there are people, send scouts that prioritize weapons over food
                    // take the necessary resources to spawn a scout and spawn one in world
                    population--;
                    randomizeSpawnCoordinates();
                    getWorld().addObject(new Scouts(baseId, false), spawnX, spawnY);
                }
            }
        }
    }

    /**
     * Randomly spawns scouts and soldiers, and creates masses of soldiers and scouts if certain parameters are met
     */
    private void checkSpawn() {
        massSpawnDelay++;
        randSpawnRate = Greenfoot.getRandomNumber(180);
        totalZombies = getWorld().getObjects(Zombie.class);
        if (weapons >= 3 && population >= 5 && massSpawnDelay >= 350 && totalZombies.size() >= 20) { // if there are at least 3 weapons and 5 people, and there are at least 20 zombies
            // mass spawn 3 soldiers
            sendSoldiers(2);
            massSpawnDelay = 0;
        } else if (food <= population/2 && massSpawnDelay >= 420) { // if there is no more food left in the base
            // mass spawn 3 soldiers and 5 scouts. the soldiers are spawned to try to cover the scouts so they won't get immediately eaten by zombies
            sendScouts(5);   
            sendSoldiers(3);
            massSpawnDelay = 0;
        }
        // spawn individual scouts regularly
        if (randSpawnRate == 0) {
            sendScouts(1);
        } else if (randSpawnRate == 1) {
            sendSoldiers(1);
        }
    }

    /**
     * Decreases food stockpile by the a tenth of the population (1 if less than 10) if the consumptionDelay reaches the consumptionRate. If there is not enough food, sets the food value
     * to 0 and calls the starving method
     */
    private void consumeFood() {
        consumptionDelay++;
        if (food > 0 && population < 10 && consumptionDelay >= consumptionRate) {
            food--;
            consumptionDelay = 0;
            deathDelay = 0;
        } else if (food > 0  && population > 10 && consumptionDelay >= consumptionRate) { // decreases food if there is enough for the population
            food -= population/10;
            consumptionDelay = 0;
            deathDelay = 0;
        }
        if (food <= 0) {
            food = 0;
            starving();
        }
    }

    /**
     * Decreases the population by one if deathDelay reaches the deathRate value, then resets deathDelay to start over again
     */
    private void starving() {
        if (deathDelay >= deathRate) {
            population--;
            deathDelay = 0;
        }
    }

    /**
     * Increases the population of the base gradually
     */
    private void populate() {
        populateCounter++;
        if (population >= 2 && population <= 50 && populateCounter >= populateRate) {
            if (population < 10 && food >= 1) {
                population++;
                food--;
                populateCounter = 0;
            } else if (population >= 10 && food >= population/10) {
                population += population/10;
                food -= population/10;
                populateCounter = 0;
            }
        }
    }

    /**
     * Creates a bullet in the direction that the turret is pointing, at a speed of the value bulletSpeed
     */
    private void shoot() {
        if (shotDelay >= rateOfFire) {
            shot.play();
            Bullet bullet = new Bullet(bulletSpeed*Math.cos(turret.getRadRotation()), bulletSpeed*Math.sin(turret.getRadRotation()), turret.getRotation());
            getWorld().addObject(bullet, turret.getX() + (int)(turret.getLength()*Math.cos(turret.getRadRotation())), turret.getY() + (int)(turret.getLength()*Math.sin(turret.getRadRotation())));
            shotDelay = 0;
        }
    }

    /**
     * Determines the corner of the base to spawn humans and the coordinates to spawn the base's statBar based on the baseId
     * 
     */
    private void determineSpawnCoordinates() {
        if (baseId == 1) {
            spawnXDefault = getX() - width/2 - 6;
            spawnYDefault = getY() + height/2 + 6;
            statBarX = getX() + 29;
            statBarY = getY() - 27;
        } else if (baseId == 2) {
            spawnXDefault = getX() + width/2 + 6;
            spawnYDefault = getY() + height/2 + 6;
            statBarX = getX() - 29;
            statBarY = getY() - 27;
        } else if (baseId == 3) {
            spawnXDefault = getX() + width/2 + 6;
            spawnYDefault = getY() - height/2 - 6;
            statBarX = getX() - 28;
            statBarY = getY() + 27;
        } else if (baseId == 4) {
            spawnXDefault = getX() - width/2 - 6;
            spawnYDefault = getY() - height/2 - 6;
            statBarX = getX() + 28;
            statBarY = getY() + 27;
        }
    }

    /**
     * Randomizes spawn co-ordinates along the walls of the base by manipulating either the x or y coordinate of the corner base spawn of spawnXDefault and spawnYDefault
     */
    private void randomizeSpawnCoordinates() {
        int changeXorY = Greenfoot.getRandomNumber(2);
        int randomCoordinateOffset;
        if (changeXorY == 0) {
            randomCoordinateOffset = Greenfoot.getRandomNumber(280);
            if (baseId == 1) {
                spawnX = spawnXDefault + randomCoordinateOffset;
                spawnY = spawnYDefault;
            } else if (baseId == 2) {
                spawnX = spawnXDefault - randomCoordinateOffset;
                spawnY = spawnYDefault;
            } else if (baseId == 3) {
                spawnX = spawnXDefault - randomCoordinateOffset;
                spawnY = spawnYDefault;
            } else if (baseId == 4) {
                spawnX = spawnXDefault + randomCoordinateOffset;
                spawnY = spawnYDefault;
            }
        } else if (changeXorY == 1) {
            randomCoordinateOffset = Greenfoot.getRandomNumber(177);
            if (baseId == 1) {
                spawnY = spawnYDefault - randomCoordinateOffset;
                spawnX = spawnXDefault;
            } else if (baseId == 2) {
                spawnY = spawnYDefault - randomCoordinateOffset;
                spawnX = spawnXDefault;
            } else if (baseId == 3) {
                spawnY = spawnYDefault + randomCoordinateOffset;
                spawnX = spawnXDefault;
            } else if (baseId == 4) {
                spawnY = spawnYDefault + randomCoordinateOffset;
                spawnX = spawnXDefault;
            }
        }
    }

    /**
     * Checks if the base has run out of inhabitants
     * 
     * @return boolean  whether or not the base is empty
     */
    private boolean checkEmpty() {
        if (population <= 0) {
            population = 0;
            return true;
        }
        return false;
    }
}