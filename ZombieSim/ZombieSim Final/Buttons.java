import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Buttons parent class for the buttons of this simulation.
 * 
 * @author Jasper Tu
 * @version Oct 2014
 */
public class Buttons extends Actor
{
    // Declaration of instance variables. 
    // A protected boolean, that is supposed to contain information of whether or not the button has been pressed.
    protected boolean clicked;      

    /**
     * The default constructor for the Buttons class.
     */
    public Buttons()
    {
        // Upon creation, the button in the game starts off as unclicked.
        clicked = false;        
    }

    /**
     * Returns a boolean that tells whether or not the button has been clicked.
     */
    public boolean isPressed()
    {
        if(clicked == true) 
        {
            clicked = false; 
            return true;      
        } 
        else 
        {
            return false;
        }
    }
}
