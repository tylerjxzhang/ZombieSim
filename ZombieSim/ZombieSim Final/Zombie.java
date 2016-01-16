import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
/**
 * The zombie that will wander around the map, however when encounters a human, it will chase after it.
 * And attack the human once caught up.
 * 
 * @author Tyler Zhang
 * @version Oct 2014
 */
public class Zombie extends Char
{
    //Field of instance variables
    private Humans target; //The target that the zombie is currently chasing.
    private int hungerLevel; //The current hunger level of zombie decrease with time.
    private int dmg; //Damage that zombie does on an attact.
    private int attackCounter; 
    private int hungerCounter;
    private GreenfootSound zombieMoan; // a moaning sound the zombie makes when it has been converted
    private GreenfootSound zombieGrowl; // a growl sound a zombie makes when eating a human
    public Zombie()
    {
        health = 100;
        speed = 2;
        target = null;
        hungerLevel = 10;
        dmg = 10;
        attackCounter = 0;
        hungerCounter = 0;
        // setting the sounds of the zombies
        zombieMoan = new GreenfootSound("Zombie Moan.mp3");
        zombieGrowl = new GreenfootSound("Zombie Growl.mp3");
        zombieMoan.setVolume(50);
        zombieGrowl.setVolume(50);
    }

    /**
     * What the zombie does everytime act is pressed.
     */
    public void act()
    {
        if(target == null){
            avoidObs(true);
            wander();
        }else{
            chaseHuman(); //Chase after the human and attack is caughtII
        }

        if (hungerCounter >= 250){
            hungerLevel--;
            if(hungerLevel < 0){
                hungerLevel = 0;
                health -= 10;
            }
        }else{
            hungerCounter++;   
        }

        try{
            getTarget();
        }catch(Exception e){
        }
    }

    /**
     * Chase after the targeted human.
     * And attact the human once caught up.
     */
    private void chaseHuman(){
        if (target != null){
            avoidObs(true);
            turnTowards(target.getX(),target.getY());
            move(speed);
            if(caughtHuman()){
                if (attackCounter > 10){
                    attack();
                    attackCounter = 0;
                }else{
                    attackCounter++;
                }
            }
        }
    }

    /**
     * Return the reference to the target that the zombie is currently tracking.
     * 
     * @return Humans get the target that the zombie is currently chasing after.
     */
    public Humans returnTarget()
    {
        return target;
    }

    /**
     * Set the target as null, clear the chasing target.
     */
    public void clearTarget()
    {
        target = null;
    }

    /**
     * Attack the human object in contact.
     * When the human run out of health point, kill/eat the human if hungerLevel is lower than 6.
     */
    private void attack()
    {
        myWorld = (ZombieWorld)getWorld();
        if (hungerLevel < 6){
            if (target.getHit(dmg, false)){ //If the human is killed.
                myWorld.humanKilled();
                hungerLevel += 5;
                zombieGrowl.play(); // plays the growl sound when eating a human
                target = null;
                if (hungerLevel > 10){
                    hungerLevel = 10;   
                }
            }
        }else{
            if (target.getHit(dmg, true)){ // Convert human to zombie.
                myWorld.humanConverted();
                zombieMoan.play(); // plays the moan sound when converting a human
                target = null;
            }
        }
    }

    /**
     * Check if the zombie is in contact with a human.
     * 
     * @return boolean True, if caught a human, else return false.
     */
    private boolean caughtHuman()
    {
        return this.intersects(target);
    }

    /**
     * Set the nearest human in a range as the target.
     */
    private void getTarget()
    {
        List<Humans> men = getObjectsInRange(150, Humans.class);
        if (men.size() == 0){
            target = null;
        }else{
            target = (Humans)getNearestHuman(men);
        }
    }

    /**
     * Return the nearest human.
     * 
     * @Param men A list of human objects within a range of the zombie.
     */
    private Humans getNearestHuman(List<Humans> men)
    {
        Humans nearMan = null;
        double nearestDistance = 9999;
        for (int i = 0; i < men.size(); i++) {
            double distance = getDistance(men.get(i));
            if (distance < nearestDistance) {
                nearMan = men.get(i);
                nearestDistance = distance;
            }
        }
        return nearMan;
    }

    /**
     * Return the distance from this to another object.
     * 
     * @return double the distance of between two actors.
     */
    private double getDistance(Actor actor) {
        return Math.hypot(actor.getX() - getX(), actor.getY() - getY());
    }

    /**
     * Called when zombie is shot, decrease the health by a certain number.
     * If health run out, die.
     * 
     * @param damage The value of damage done.
     */
    public void loseHealth(int damage){
        if (damage < health){
            health -= damage;
        }else{
            myWorld = (ZombieWorld)getWorld();
            myWorld.oneZombieDie();
            myWorld.zombieKilled();
            getWorld().removeObject(this);
        }
    }
}