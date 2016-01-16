import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The loading screen before the simulation is run
 * 
 * @author Jasper Tu
 * @version Oct 2014
 */
public class LoadingScreen extends World
{
    // Declaration of necessary instance variables.
    private int counter = 0;  
    private boolean exists = true;  
    private GreenfootSound theme;
    
    private int population;
    private int food;
    private int weapons;
    private int maxZombieSpawn;
    
    private ModificationStorage store;
    /**
     * Constructor for objects of class LoadingScreen.
     * 
     * @param theme the theme music being passed from this world to the next
     * @param store the object carrying the modifiable variables from one world to the next
     */
    public LoadingScreen(GreenfootSound theme,  ModificationStorage store)
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(960, 640, 1); 
        this.theme = theme;
        this.store = store;
    }

    /**
     * Act - do whatever LoadingScreen wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        timer();
    }

    /**
     * Counts up until a particular integer value, and then switches the screen into another world; that being ZombieWorld.
     * Essentially determines how long the loading screen will last.
     */
    public void timer()
    {
        if(exists == true)
        {  
            if(counter < 300)  
            {  
                counter++;  
            }  
            else 
            {  
                Greenfoot.setWorld(new ZombieWorld(theme, store));  
                exists = false;  
            }  
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
     * Stops playing the theme when the simulation is not running.
     */
    public void stopped()
    {
        theme.pause();
    }
}
