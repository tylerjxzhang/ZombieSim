import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.image.WritableRaster;
import java.awt.Color;

/**
 * FastImage class that is instrumental towards the functionality of the transition fade.  
 * Full credit for this particular class goes to Builderboy2005.
 * <p>
 * A deep thank you for making our simulation look better. (Marco L., Ahrenn S., Jasper T., Tyler Z.) 
 * 
 * @author Builderboy2005
 * @version Greenfoot v2.4
 */
public class FastImage
{
    // Declaration of necessary instance variables.
    private WritableRaster raster;
    private byte[][] imageRed;
    private byte[][] imageGreen;
    private byte[][] imageBlue;
    private int[] color = {0,0,0,255};
    private int[] black = {0,0,0,255};
    private int[] tempColor = {0,0,0,255};
    private int width,height;

    /**
     * Constructor for FastImage class that accepts a Greenfoot image.
     */
    public FastImage(GreenfootImage image)
    {
        raster = image.getAwtImage().getRaster();
        imageRed   = new byte[image.getWidth()][image.getHeight()];
        imageGreen = new byte[image.getWidth()][image.getHeight()];
        imageBlue  = new byte[image.getWidth()][image.getHeight()];
        for(int x = 0; x < image.getWidth(); x++)
        {
            for(int y = 0; y < image.getHeight(); y++)
            {
                imageRed[x][y]   = (byte)(image.getColorAt(x,y).getRed()-128);
                imageGreen[x][y] = (byte)(image.getColorAt(x,y).getGreen()-128);
                imageBlue[x][y]  = (byte)(image.getColorAt(x,y).getBlue()-128);
            }
        }
        height = image.getHeight();
        width = image.getWidth();
    }

    /**
     * Sets integer colour array in accordance to another array (c) for future use - for overloading. 
     * Accepts this other array, called "c".
     * 
     * @param   c An array that stores an integer in accordance to the index of the preexisting colour array. 
     */
    public void setColor(int[] c)
    {
        color[0] = c[0];
        color[1] = c[1];
        color[2] = c[2];
    }

    /**
     * Overloaded method that sets the integer colour array in accordance to one of the primary base colours: red, green, blue (RGB).
     * Accepts a colour.
     * 
     * @param   c A Greenfoot Color that allows one to obtain a type of color (ex. Red). 
     */
    public void setColor(Color c)
    {
        color[0] = c.getRed();
        color[1] = c.getGreen();
        color[2] = c.getBlue();
    }

    /**
     * Accepts an integer value and sets an index of the integer colour array to this.
     * 
     * @param   red An integer that represents the colour red.
     */
    public void setRed(int red){
        color[0] = red;
    }

    /**
     * Accepts an integer value and sets an index of the integer colour array to this.
     * 
     * @param   green An integer that represents the colour green.
     */
    public void setGreen(int green)
    {
        color[1] = green;
    }

    /**
     * Accepts an integer value and sets an index of the integer colour array to this.
     * 
     * @param   blue An integer that represents the colour blue.
     */
    public void setBlue(int blue)
    {
        color[2] = blue;
    }

    /**
     * Sets the pixels to a particular colour (red, green, blue) and accepts 2 integer values of x and y.
     * 
     * @param   x Integer that represents the width.
     * @param   y Integer that represents the height.
     */
    public void setPixel(int x, int y)
    {
        if(x < 0 || x >= width  || y < 0 || y >= height)
        {
            return;
        }
        raster.setPixel(x,y,color);
        imageRed[x][y]   = (byte)(color[0]-128);
        imageGreen[x][y] = (byte)(color[1]-128);
        imageBlue[x][y]  = (byte)(color[2]-128);
    }

    /**
     * Overloaded method that sets pixels to a particular colour (red, green, blue) through the acceptance of 2 integer values and an array.
     * 
     * @param   c An array that stores an integer in accordance to the index of the preexisting colour array. 
     * @param   x Integer that represents the width.
     * @param   y Integer that represents the height.
     */
    public void setPixel(int x, int y, int[] c)
    {
        if(x < 0 || x >= width || y < 0 || y >= height)
        {
            return;
        }
        raster.setPixel(x,y,c);
        imageRed[x][y]   = (byte)(c[0]-128);
        imageGreen[x][y] = (byte)(c[1]-128);
        imageBlue[x][y]  = (byte)(c[2]-128);
    }

    /**
     * Overloaded method that sets pixels to a particular colour (red, green, blue) through the acceptance of 2 integer values and a Color.
     * 
     * @param   x Integer that represents the width.
     * @param   y Integer that represents the height.
     * @param   c A Greenfoot Color that allows one to obtain a type of color (ex. Red). 
     */
    public void setPixel(int x, int y, Color c)
    {
        if(x < 0 || x >= width || y < 0 || y >= height)
        {
            return;
        }
        tempColor[0] = c.getRed();
        tempColor[1] = c.getGreen();
        tempColor[2] = c.getBlue();
        raster.setPixel(x,y,tempColor);
        imageRed[x][y]   = (byte)(c.getRed()-128);
        imageGreen[x][y] = (byte)(c.getGreen()-128);
        imageBlue[x][y]  = (byte)(c.getBlue()-128);
    }

    /**
     * Accessor that obtains a particular colour at a certain set of x and y coordinates. 
     * 
     * @param   x Integer that represents the width.
     * @param   y Integer that represents the height.
     */
    public int[] getColorAt(int x,int y)
    {
        if(x < 0 || x >= width || y < 0 || y >= height)
        {
            return black;
        }
        tempColor[0] = imageRed[x][y]+128;
        tempColor[1] = imageGreen[x][y]+128;
        tempColor[2] = imageBlue[x][y]+128;
        return tempColor;
    }

    /**
     * Accessor that obtains the colour red, at a certain set of x and y coordinates. 
     * 
     * @param   x Integer that represents the width.
     * @param   y Integer that represents the height.
     */
    public int getRedAt(int x, int y)
    {
        if(x < 0 || x >= width || y < 0 || y >= height)
        {
            return 0;
        }
        return imageRed[x][y]+128;
    }

    /**
     * Accessor that obtains the colour green, at a certain set of x and y coordinates. 
     * 
     * @param   x Integer that represents the width.
     * @param   y Integer that represents the height.
     */
    public int getGreenAt(int x, int y)
    {
        if(x < 0 || x >= width || y < 0 || y >= height)
        {
            return 0;
        }
        return imageGreen[x][y]+128;
    }

    /**
     * Accessor that obtains the colour blue, at a certain set of x and y coordinates. 
     * 
     * @param   x Integer that represents the width.
     * @param   y Integer that represents the height.
     */
    public int getBlueAt(int x, int y)
    {
        if(x < 0 || x >= width || y < 0 || y >= height)
        {
            return 0;
        }
        return imageBlue[x][y]+128;
    }
}
