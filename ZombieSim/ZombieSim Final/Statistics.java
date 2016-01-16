import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color; 
import java.awt.Font;
/**
 * The statistics display for a given base. The display information includes the population, food, and weapons 
 * inside the base. The statstics can be made visible by clicking the bar, which will be created to be on top
 * of their respective bases
 * 
 * @author Marco Ly
 * @version Oct 2014
 */
public class Statistics extends DisplayBars
{
    // Initializing instance variables
    private int population; // the population of the base
    private int food; // the food supply of the base
    private int weapons; // the weapons supply of the base
    
    private Color backgroundColor; 
    private Color textColorPop;// population value's color
    private Color textColorFood; // food value's color
    private Color textColorWeap; // weapons value's color
    private Font textFont;
    private GreenfootImage stats; // the base image that all other images will be drawn onto
    private GreenfootImage background; // the background of the statistics
    private GreenfootImage foodIcon; // the image of the food icon
    private GreenfootImage weaponsIcon; // the image of the weapons icon
    private GreenfootImage populationIcon; // the image of a scout that represents the population
    private GreenfootImage foodValue; // the amount of food of the base
    private GreenfootImage weaponsValue; // the amount of weapons in the base
    private GreenfootImage populationValue; // the total population in the base
    public Statistics (int population, int food, int weapons) {     
        // setting starting values
        isShowing = false;
        this.population = population;
        this.food = food;
        this.weapons = weapons;
        // setting the unchanging images
        stats = new GreenfootImage(234, 125);
        background = new GreenfootImage(234,125);
        foodIcon = new GreenfootImage("Food Icon.png");
        weaponsIcon = new GreenfootImage("Weapon Icon.png");
        populationIcon = new GreenfootImage("Default Scout.png");
        // setting the images that will be changed along with their fonts and colors
        textColorPop = new Color(0, 255, 100);
        textColorFood = new Color(0, 255, 100);
        textColorWeap = new Color(255, 0, 0);
        textFont = new Font("Courrier", Font.BOLD, 20);
        populationValue = new GreenfootImage("=   " + population, 20, textColorPop, null);
        foodValue = new GreenfootImage("=   " + food, 20, textColorFood, null);
        weaponsValue = new GreenfootImage("=   " + weapons, 20, textColorWeap, null);
        populationValue.setFont(textFont);
        foodValue.setFont(textFont);
        weaponsValue.setFont(textFont);
        // filling the background
        backgroundColor = new Color(255, 255, 255);
        background.setColor(backgroundColor);
        background.setTransparency(125);
        background.fill();
        // drawing all images onto the basic stats image
        stats.drawImage(background, 0, 0);
        stats.drawImage(populationIcon, 57, 8);
        stats.drawImage(foodIcon, 57, 51);
        stats.drawImage(weaponsIcon, 57, 94);
        stats.drawImage(populationValue, 96, 8);
        stats.drawImage(foodValue, 96, 51);
        stats.drawImage(weaponsValue, 96, 94);
        // hiding the stats image initially and setting the Statistics object to be stats
        stats.setTransparency(0);
        this.setImage(stats);
    }

    /**
     * Act - do whatever the Statistics wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        showStatistics();
        updateImage();
    }    

    /**
     * Mutator that updates the display with all information from their base
     * 
     * @param population    the population value given by the base
     * @param food          the food value given by the base
     * @param weapons       the weapons value given by the base
     */
    public void updateValues (int population, int food, int weapons) {
        this.population = population;
        this.food = food;
        this.weapons = weapons;
        if (population <= 10) {
            textColorPop = new Color(255, 0, 0);
        } else {
            textColorPop = new Color(0, 255, 100);
        }
        if (food <= 25) {
            textColorFood = new Color(255, 0, 0);
        } else {
            textColorFood = new Color(0, 255, 100);
        }
        if (weapons <= 2) {
            textColorWeap = new Color(255, 0, 0);
        } else {
            textColorWeap = new Color(0, 255, 100);
        }
    }

    /**
     * Makes the stats image visible or invisible when clicked on by the mouse
     */
    private void showStatistics() {
        if(Greenfoot.mouseClicked(this) && isShowing == true) {
            stats.setTransparency(0); // makes stats invisible
            isShowing = false;
        } else if (Greenfoot.mouseClicked(this) && isShowing == false) {
            stats.setTransparency(255); // makes stats fully visible 
            isShowing = true;
        }
    }

    /**
     * Updates the object's image with any new values by clearing previous images and updating them with the new values
     */
    private void updateImage() {
        // Clearing all images
        populationValue.clear();
        foodValue.clear();
        weaponsValue.clear();
        stats.clear();
        // Re-setting the changing images along with their fonts
        populationValue = new GreenfootImage("=   " + population, 20, textColorPop, null);
        foodValue = new GreenfootImage("=   " + food, 20, textColorFood, null);
        weaponsValue = new GreenfootImage("=   " + weapons, 20, textColorWeap, null);
        populationValue.setFont(textFont);
        foodValue.setFont(textFont);
        weaponsValue.setFont(textFont);
        // Re-draws all images onto the stats image
        stats.drawImage(background, 0, 0);
        stats.drawImage(populationIcon, 57, 8);
        stats.drawImage(foodIcon, 57, 51);
        stats.drawImage(weaponsIcon, 57, 94);
        stats.drawImage(populationValue, 96, 8);
        stats.drawImage(foodValue, 96, 51);
        stats.drawImage(weaponsValue, 96, 94);
        // Sets the Statistics object image to the stats image
        this.setImage(stats);
    }
}
