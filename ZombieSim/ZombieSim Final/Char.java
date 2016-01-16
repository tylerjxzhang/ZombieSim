import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
/**
 * The abstract parent class for all characters including humans and zombies.
 * Including abstract methods like wander, avoid obstacle, etc.
 * This abstract super class will outline the basic behaviour of all characters in the simulation.
 * 
 * @author Tyler Zhang
 * @version Sept 2014
 */

public abstract class Char extends Actor
{
    //Protected field of variables.
    protected int health; //Current hp that the character have.,
    protected int speed; //The moving speed of the character.
    protected ZombieWorld myWorld; //The reference to the world in which the actor lives in.

    /**
     * Make the character wander aroudn the map randomly.
     * The character will move in a straight line, with a small possiblity of turning.
     */
    protected void wander(){
        if (Greenfoot.getRandomNumber(200) == 1){ // There will be a 1% chance for the character to change direction.
            turn(Greenfoot.getRandomNumber(360) - 180); // The character will turn by an angle between the range of 90 to -90.
        } 
        move(speed); // Move in a straight line.
    }

    /**
     * If there is an obstcle in front of the character, avoid by walking above it or walking below it.
     * If the character is encountering a edge or a base, turn around.
     * 
     * @param goFromTop Whether the character will avoid the obstacle from top or from bottom.
     */
    protected void avoidObs(boolean goFromTop)
    {
        int moveCounter = 0; // Keep track of the number of steps that the actor have moved in one act.
        if (atWorldEdge()){ //While the actor is in proximity of the world edge or one of the base.
            turnTowards(480,320); //Turn towards the center of the screen.
            while(atWorldEdge() && moveCounter < 5){ //Move at most 5 times per act.
                move(speed);
                moveCounter++;
            }
        }

        int turnCoefficient = 0; // The coefficient that will be multiplied to the turning angle.
        if(goFromTop){ // The actor will avoid the obstacle by going above it.
            turnCoefficient = 1;
        }else{
            turnCoefficient = -1;
        }

        while(ObsInWay() && moveCounter < 5){ // Turn and move as long as an obstacle is in front of the character.
            turn(turnCoefficient * (Greenfoot.getRandomNumber(90) + 30)); //Turn a random angle.
            move(speed);
            moveCounter++;
        }
    }

    /**
     * If there is an obstcle in front of the character, avoid by walking above it (default).
     * If the character is encountering a edge or a base, turn around.
     */
    protected void avoidObs()
    {
        int moveCounter = 0; // Keep track of the number of steps that the actor have moved in one act.
        if (atWorldEdge()){ //While the actor is in proximity of the world edge or one of the base.
            turnTowards(480,320); //Turn towards the center of the screen.
            while(atWorldEdge()){ //Move at most 5 times per act.
                move(speed);
                moveCounter++;
            }
        }

        int turnCoefficient = 1; // The coefficient that will be multiplied to the turning angle.

        while(ObsInWay()){ // Turn and move as long as an obstacle is in front of the character.
            turn(turnCoefficient * 15); //Turn a random angle.
            move(speed);
            moveCounter++;
        }
    }

    /**
     * Check is there is obstacle in the path of the actor.
     * 
     * @return boolean True if there is an obstacle in front of the character, else, return false.
     */
    public boolean ObsInWay()
    {
        List obs = getObjectsAtOffset(speed*2, speed*2, Obstacle.class);
        if (obs.size() == 0){
            return false;
        }else{
            return true;
        }
    }

    /**
     * Test if the actor is close to one of the edges of the world. Return true if it is.
     */
    public boolean atWorldEdge()
    {
        myWorld = (ZombieWorld)getWorld();
        int baseWidth = myWorld.getBase(1).getImage().getWidth();
        int baseHeight = myWorld.getBase(1).getImage().getHeight();
        if(getX() < baseWidth - speed && getY() < baseHeight - speed){
            return true;
        }else if(getX() < baseWidth - speed + 1 && getY() > myWorld.getHeight() - baseHeight + speed - 1){
            return true;
        }else if(getX() > myWorld.getWidth() - baseWidth + speed - 1 && getY() < baseHeight - speed + 1){
            return true;
        }else if(getX() > myWorld.getWidth() - baseWidth + speed - 1 && getY() > myWorld.getHeight() - baseHeight + speed - 1){
            return true;
        }else if(getX() < 5 || getX() > myWorld.getWidth() - 5 || getY() < 5 || getY() > myWorld.getHeight() - 5){
            return true;
        }else{
            return false;
        }
    }
}
