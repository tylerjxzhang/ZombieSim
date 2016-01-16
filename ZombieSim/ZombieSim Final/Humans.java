import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * The abstract parent class for all human, including scout and soldier.
 * 
 * @author Tyler Zhang
 * @version Sept 2014
 */
public abstract class Humans extends Char
{
    //Protected field of human class.
    protected int baseId;  //Which one of the four base does the human belong to.
    protected boolean target;  //If the character is heading to a target.
    protected int targetX;
    protected int targetY;
    protected int foodCarry;
    protected int weaponCarry;
    protected ZombieWorld myWorld; // The reference to the world.
    
    /**
     * Called when the human got hit by a zombie.
     * The human will take the damage if he have enough hp, otherwise, he will either be convert to a zombie or be killed.
     * 
     * @param dmg The amount of damage that was done.
     * @param convert True if the human will be convert to zombie, false if the human will die after the hp run out.
     * 
     * @return boolean True if the human is dead or converted to zombie, false if the human is still healthy.
     */
    public boolean getHit(int dmg, boolean convert){
        if (health > dmg){
            loseHealth(dmg);
            return false;
        }else{
            removeReference();
            if (convert){
                zombieConvert();
            }else{
                killed();
            }
            return true;
        }
    }

    /**
     * Remove all the reference passed out of this human object.
     */
    protected void removeReference()
    {
        List<Zombie> zoms= getWorld().getObjects(Zombie.class);
        for (Zombie z:zoms){
            if(z.returnTarget() == this){
                z.clearTarget();
            }
        }
        List<Icons> icons = getObjectsInRange(50, Icons.class);
        for (Icons i:icons){
            if(i.getTarget() == this){
                getWorld().removeObject(i);
            }
        }
    }

    /**
     * Removes object and all objects owned by it from the world. Interfaces with base as necessary
     */
    protected void hasReturned() {
        if (getOneObjectAtOffset(0,0, Base.class) != null) {
            Base base = (Base)getOneObjectAtOffset(0,0, Base.class);
            base.humanReturned();
            if (this.getClass() == Scouts.class){
                base.addFood(foodCarry);
                base.addWeapon(weaponCarry);
            }
            removeReference(); 
            getWorld().removeObject(this);
        }
    }

    /**
     * Return the base number that the human belong to.
     * 
     * @return int The index for the base that the human belong to.
     */
    public int getBase(){
        return baseId;
    }

    /**
     * Reduce the current health of human by a value.
     * 
     * @param damage How much damage is done to the player.
     */
    protected void loseHealth(int damage){
        health -= damage;
    }

    /**
     * Replace the current human actor with a new zombie.
     */
    protected void zombieConvert()
    {
        getWorld().addObject(new Zombie (),getX(),getY());
        killed();
    }

    /**
     * Remove the current human actor from world.
     */
    protected void killed()
    {
        getWorld().removeObject(this);
    }

    /**
     * Find way back to the base.
     */
    protected void returnToBase()
    {
        myWorld = (ZombieWorld)getWorld();
        Base base = myWorld.getBase(baseId);
        setTarget(base.getX(),base.getY()); //Set the base as target.
    }

    /**
     * Set the x,y values of the target.
     */
    protected void setTarget(int x, int y)
    {
        targetX = x;
        targetY = y;
    }
}

