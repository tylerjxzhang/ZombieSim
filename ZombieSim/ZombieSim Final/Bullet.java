import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A bullet projectile that moves and hits any objects that are not humans or bases
 * 
 * @author Marco Ly
 * @version Oct 2014
 */
public class Bullet extends Projectile
{
    /**
     * Sets the velocities and angle of the bullet
     * 
     * @param velocityX the velocity in the X direction of bullet
     * @param velocityY the velocity in the Y direction of bullet
     * @param angle     the angle of the bullet when shot
     */
    public Bullet (double velocityX, double velocityY, int angle) {
        Vx = (int)velocityX;
        Vy = (int)velocityY;
        setRotation(angle);
    }

    /**
     * Act - do whatever the Bullet wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if (outOfBounds()) {getWorld().removeObject(this); 
        }else if (hasHit()){hitObject(25);
        }else if (hasHit() == false) {setLocation(getX() + Vx, getY() + Vy);}
    }    

    /**
     * Checks if there is an object between the projectile to the vertical and horizontal velocity. If there is, snaps to that object and lands.
     * 
     * @return boolean  whether or not the bullet has hit an object that is not a human or base
     */
    public boolean hasHit () {
        for (int i = 0; i <= height/2 + Vy; i++) {
            for (int h = 0; h <= Vx; h++) {     
                // checks for objects that are not humans or bases. lands on any object that are not humans or bases
                if (getOneObjectAtOffset(h, i, null) != null && getOneObjectAtOffset(h, i, Icons.class) == null && getOneObjectAtOffset(h, i, Humans.class) == null && getOneObjectAtOffset(h, i, Base.class) == null) {
                    setLocation(getOneObjectAtOffset(h,i,null).getX(), getOneObjectAtOffset(h,i,null).getY());
                    return true;
                }
            }
            for (int h = 0; h >= Vx; h--) {   
                // checks for objects that are not humans or bases. lands on any object that are not humans or bases
                if (getOneObjectAtOffset(h, i, null) != null && getOneObjectAtOffset(h, i, Icons.class) == null && getOneObjectAtOffset(h, i, Humans.class) == null && getOneObjectAtOffset(h, i, Base.class) == null) {
                    setLocation(getOneObjectAtOffset(h,i,null).getX(), getOneObjectAtOffset(h,i,null).getY());
                    return true;
                }
            }
        } 
        for (int i = 0; i >= height/2 + Vy; i--) {
            for (int h = 0; h <= Vx; h++) {
                // checks for objects that are not humans or bases. lands on any object that are not humans or bases
                if (getOneObjectAtOffset(h, i, null) != null && getOneObjectAtOffset(h, i, Icons.class) == null && getOneObjectAtOffset(h, i, Humans.class) == null && getOneObjectAtOffset(h, i, Base.class) == null) {
                    setLocation(getOneObjectAtOffset(h,i,null).getX(), getOneObjectAtOffset(h,i,null).getY());
                    return true;
                }
            }
            for (int h = 0; h >= Vx; h--) {        
                // checks for objects that are not humans or bases. lands on any object that are not humans or bases
                if (getOneObjectAtOffset(h, i, null) != null && getOneObjectAtOffset(h, i, Icons.class) == null && getOneObjectAtOffset(h, i, Humans.class) == null && getOneObjectAtOffset(h, i, Base.class) == null) {
                    setLocation(getOneObjectAtOffset(h,i,null).getX(), getOneObjectAtOffset(h,i,null).getY());
                    return true;
                }
            }
        } 
        return false; 
    }
}