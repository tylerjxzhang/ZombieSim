import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Transition fade in between start screen and loading screen.
 * 
 * @author Jasper Tu
 * @version Oct 2014
 */
public class TransitionFade extends World
{
    // Declaration of necessary instance variables.
    GreenfootSound theme;
    GreenfootImage myImage;
    FastImage image;
    int[] color = {255,255,255,255};

    MouseInfo mouse;
    int mouseX,mouseY;
    int prevX,prevY;
    boolean pressed = false;
    int type=1;

    int redCycle=600,redVel=1;
    int greenCycle=400,greenVel=-1;
    int blueCycle=200,blueVel=1;

    private int counter = 0;  
    private boolean exists = true;  

    private ModificationStorage store;
    /**
     * Constructor for objects of class TransitionFade.
     * 
     * @param theme the theme music being passed from this world to the next
     * @param store the object carrying the modifiable variables from one world to the next
     */
    public TransitionFade(GreenfootSound theme, ModificationStorage store)
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(960, 640, 1); 
        myImage = getBackground();
        image = new FastImage(myImage);
        this.theme = theme;
        this.store = store;
    }

    /**
     * Act - do whatever TransitionFade wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        redCycle += redVel;
        if(redCycle == 0 || redCycle == 255)
        {
            redVel = -redVel;
        }

        greenCycle += greenVel;
        if(greenCycle == 0 || greenCycle == 255)
        {
            greenVel = -greenVel;
        }

        blueCycle += blueVel;
        if(blueCycle==0 || blueCycle==255)
        {
            blueVel = -blueVel;
        }

        color[0] = redCycle;
        color[1] = greenCycle;
        color[2] = blueCycle;

        if(type==1)
        {
            fadeImage();
        }

        timer();
    }    

    /**
     * Smoothly fades the image that is set to this world, into a black background.
     */
    public void fadeImage()
    {
        if(pressed)
        {
            image.setPixel(prevX,prevY,color);
            while(prevX != mouseX)
            {
                prevX += Math.abs(mouseX-prevX)/(mouseX-prevX);
                image.setPixel(prevX,prevY+1,color);
                image.setPixel(prevX,prevY-1,color);
                image.setPixel(prevX,prevY,color);
            }
            while(prevY != mouseY)
            {
                prevY += Math.abs(mouseY-prevY)/(mouseY-prevY);
                image.setPixel(prevX+1,prevY,color);
                image.setPixel(prevX,prevY,color);
                image.setPixel(prevX-1,prevY,color);
            }    
        }

        for(int x = 0; x < 960; x++)
        {
            for(int y = 0; y < 640; y++)
            {
                color[0] =  (int)((image.getRedAt(x+1,y+1) + image.getRedAt(x+1,y-1) + image.getRedAt(x-1,y+1) + image.getRedAt(x-1,y-1) ) / 4.01);
                color[1] =  (int)((image.getGreenAt(x+1,y+1) + image.getGreenAt(x+1,y-1) + image.getGreenAt(x-1,y+1) + image.getGreenAt(x-1,y-1) ) / 4.01) ;
                color[2] =  (int)((image.getBlueAt(x+1,y+1) + image.getBlueAt(x+1,y-1) + image.getBlueAt(x-1,y+1) + image.getBlueAt(x-1,y-1) ) / 4.01);
                image.setPixel(x,y,color);
            }
        }
    }

    /**
     * Counts up until a particular integer value, and then switches the screen into another world; that being LoadingScreen.
     * Essentially determines how long the transition fade will last.
     */
    public void timer()
    {
        if(exists == true)
        {  
            if(counter < 150)  
            {  
                counter++;  
            }  
            else 
            {  
                //System.out.println(population + " " + food + " "+ weapons + " "+maxZombieSpawn);
                Greenfoot.setWorld(new LoadingScreen(theme, store));  
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
