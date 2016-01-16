import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The class for the click button of the simulation's start screen; a child class of the parent Button class.
 * 
 * @author Jasper Tu
 * @version Oct 2014
 */
public class StartScreenButton extends Buttons
{
    // Declaration of instance variables.
    private GreenfootImage texts = new GreenfootImage("ModText.png"); 
    private GreenfootImage button;
    private GreenfootImage buttonHover;
    private GreenfootSound zombieMoan;
    private StartScreen myWorld;
    private ModificationStorage store;
    /**
     * Constructor for the "StartScreenButton" class.
     */
    public StartScreenButton() 
    {
        // Import pictures into Greenfoot.
        button = new GreenfootImage("Start Screen Button.png");     
        buttonHover = new GreenfootImage("Start Screen Hover.png");
        buttonHover.drawImage(texts, 50, 50);

        zombieMoan = new GreenfootSound ("Zombie Moan.mp3");
        button.setTransparency(200);
        setImage(button);    
        zombieMoan.setVolume(50);
        store = new ModificationStorage();
    }    

    /**
     * Act - do whatever StartScreenButton wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        // If the mouse hovered on the button, change the picture to make it look special.
        if(Greenfoot.mouseMoved(this)) 
        {      
            getWorld().getBackground().drawImage(buttonHover, 0, 0);  
            zombieMoan.play();
        }

        // If the mouse moved off the button, restore the button to its original appearance.
        if(Greenfoot.mouseMoved(getWorld())) 
        {      
            getWorld().getBackground().drawImage(button, 442, 510);  
        }

        // If the button was clicked, variable becomes true and image is changed.
        if(Greenfoot.mouseClicked(this)) 
        {   
            clicked = true;
            myWorld = (StartScreen)getWorld();
            // updates store with the most recent values upon clicking the button
            store.updateValues(myWorld.getStartPop(), myWorld.getStartFood(), myWorld.getStartWeapon(), myWorld.getMaxZombieSpawn());
            Greenfoot.setWorld(new TransitionFade(myWorld.getTheme(), store));
        }
    }
}
