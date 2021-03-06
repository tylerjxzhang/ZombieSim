import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;

/**
 * The start menu of the zombie simulation.
 * 
 * @author Jasper Tu, Tyler Zhang
 * @version Oct 2014
 */
public class StartScreen extends World
{
    // Declaration of necessary instance variables.
    private GreenfootSound theme = new GreenfootSound("The Walking Dead Theme.mp3");
    private GreenfootImage texts; 

    private Color textFieldColor;
    private Color textColor;

    private int startPop;
    private int startFood;
    private int startWeapon;
    private int maxZombieSpawn;

    private TextField t1;
    private TextField t2;
    private TextField t3;
    private TextField t4;
    /**
     * Constructor for objects of class StartScreen.
     * 
     */
    public StartScreen()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(960, 640, 1); 
        // Prepares the world with the start screen button.
        prepare();

        texts = new GreenfootImage("ModText.png");
        textFieldColor = new Color(0, 0, 0);
        textColor = new Color(255, 255, 255);
        //textFieldColor.setTransparency();
        startPop = 25;
        startFood = 100;
        startWeapon = 0;
        maxZombieSpawn = 400;

        t1 = new TextField(75, 35, textFieldColor, textColor, "" + startPop);
        t2 = new TextField(75, 35, textFieldColor, textColor, "" + startFood);
        t3 = new TextField(75, 35, textFieldColor, textColor, "" + startWeapon);
        t4 = new TextField(75, 35, textFieldColor, textColor, "" + maxZombieSpawn);

        GreenfootImage bg = getBackground();
        bg.drawImage(texts, 50, 50);
        setBackground(bg);
        addObject(t1, 400, 90);
        addObject(t2, 400, 130);
        addObject(t3, 400, 170);
        addObject(t4, 400, 225);
    }

    /**
     * Prepare the world for the start of the program. That is: create the initial
     * objects and add them to the world.
     */
    private void prepare()
    {
        // Generate instance of start screen button.
        StartScreenButton startscreenbutton = new StartScreenButton();
        // Add the start screen button object to the start screen world.
        addObject(startscreenbutton, 628, 554);
    }

    public int getStartPop() {
        return Integer.parseInt(t1.getText());
    }

    public int getStartFood() {
        return Integer.parseInt(t2.getText());
    }

    public int getStartWeapon() {
        return Integer.parseInt(t3.getText());
    }

    public int getMaxZombieSpawn() {
        return Integer.parseInt(t4.getText());
    }

    /**
     * Starts playing the opening theme of the zombie simulation.
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
    }

    /** 
     * Accessor that returns the GreenfootSound theme
     * 
     * @return GreenfootSound   the music theme being played
     */
    public GreenfootSound getTheme() {
        return theme;
    }
}