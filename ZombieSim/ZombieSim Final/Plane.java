import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
/**
 * A plane that flies across the screen and drops a random number of parachute markers on the screen and shoots zombies while passing by
 * 
 * @author Marco Ly
 * @version Oct 2014
 */
public class Plane extends SupplySpawning
{
    // Initializing Instance variables
    private int speed; // speed of plane
    private int turretSpeed; // speed of turret attatched to plane
    private int parachuteNum; // max number of parachutes to be spawned by plane
    
    private int shotDelay; // delay of shots fired by turrets
    private int shootRange; // range of shooting
    private int rateOfFire; // rate of fire for bullets (lower ROF means more frequent firing)
    private int bulletSpeed; // speed that bullets travel
    
    private Turret turret1; // turret object 1
    private Turret turret2; // turret object 2
    /**
     * Constructor for plane. Responsible for orienting the plane in the right direction and adding values to instance variables
     * 
     * @param facingRight   whether or not the plane is facing right
     */
    public Plane (boolean facingRight) {
        // corrects orientation of the plane and sets speed for turretspeed 
        if (facingRight == false) {
            turn(180);
            turretSpeed = -8;
        } else {turretSpeed = 8;}
        speed = 8; // sets speed to absolute value of turretspeed
        parachuteNum = Greenfoot.getRandomNumber(10) + 6; // generates a random number of allowed parachute drops
        // creates instances of turrets
        turret1 = new Turret(); 
        turret2 = new Turret();
        // sets the variables for shooting
        shotDelay = 0;
        shootRange = 200;
        rateOfFire = 2;
        bulletSpeed = 20;
    }

    /**
     * Adds turrets to world when plane is added to world
     */
    public void addedToWorld (World ZombieWorld) {
        // adds turrets to the world and corrects their positioning to match up with the plane's location
        ZombieWorld.addObject(turret1, getX(), getY() - 90);
        ZombieWorld.addObject(turret2, getX(), getY() + 90);
        turret1.setRotation(this.getRotation());
        turret2.setRotation(this.getRotation());
        turret1.setLocation(getX() + 5*turretSpeed, turret1.getY());
        turret2.setLocation(getX() + 5*turretSpeed, turret2.getY());
        turret1.setShootRange(shootRange);
        turret2.setShootRange(shootRange);
    }

    /**
     * Act - do whatever the Plane wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        // Moves the plane and the turrets along with it
        move(speed);
        turret1.setLocation(turret1.getX() + turretSpeed, turret1.getY());
        turret2.setLocation(turret2.getX() + turretSpeed, turret2.getY());
        // increases shotDelay counter
        shotDelay++;
        if (turret1.findTargets() && turret2.findTargets()) {
            shoot();
        }
        if (getX() >= 959 || getX() <= 1) {
            dropParachuteMarkers();
            getWorld().removeObject(turret1);
            getWorld().removeObject(turret2);
            getWorld().removeObject(this);
        }
    }    

    /**
     * Creates the max number of parachute markers allowed at randomized locations
     */
    private void dropParachuteMarkers() {
        for (int i = 0; i < parachuteNum; i++) {
            getWorld().addObject(new ParachuteMarker(), randomizeX(), randomizeY());
        }
    }

    /**
     * randomizes an X value 
     * 
     * @return int  The random X-Value generated
     */
    private int randomizeX() {
        return Greenfoot.getRandomNumber(960);
    }

    /**
     * randomizes a Y value
     * 
     * @return int  The randomized Y-Value generated. Limited to approximately between the bases
     */
    private int randomizeY() {
        return Greenfoot.getRandomNumber(270) + 180;
    }
    
    /**
     * Creates a plane bullet for each turret in the direction that the turret is pointing, at a speed of the value bulletSpeed
     */
    private void shoot() {
        if (shotDelay >= rateOfFire) {
            PlaneBullet bullet1 = new PlaneBullet(bulletSpeed*Math.cos(turret1.getRadRotation()), bulletSpeed*Math.sin(turret1.getRadRotation()), turret1.getRotation());
            getWorld().addObject(bullet1, turret1.getX() + (int)(turret1.getLength()*Math.cos(turret1.getRadRotation())), turret1.getY() + (int)(turret1.getLength()*Math.sin(turret1.getRadRotation())));
            PlaneBullet bullet2 = new PlaneBullet(bulletSpeed*Math.cos(turret2.getRadRotation()), bulletSpeed*Math.sin(turret2.getRadRotation()), turret2.getRotation());
            getWorld().addObject(bullet2, turret2.getX() + (int)(turret2.getLength()*Math.cos(turret2.getRadRotation())), turret2.getY() + (int)(turret2.getLength()*Math.sin(turret2.getRadRotation())));
            shotDelay = 0;
        }
    }
}
