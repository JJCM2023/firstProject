import java.awt.Graphics;
import java.awt.Rectangle;
/**
 * Abstract class GameObject - write a description of the class here
 * 
 * @author (your name here)
 * @version (version number or date here)
 */
public abstract class GameObject
{
   protected int x,y,z;
   protected ID id;
   protected int velocityX, velocityY, velocityZ;
   protected boolean sheilding=false;
   protected boolean recharge=false;
   public GameObject(int x1, int y1,int z1, ID id1)
   {
       x=x1;
       y=y1;
       z=z1;
       id=id1;
   }
   public abstract void tick();
   public abstract void render(Graphics g, boolean top);
   public abstract Rectangle getBounds();
   public abstract void reset();
   public int getX()
   {
       return x;
   }
   public int getY()
   {
       return y;
   }
   public ID getID()
   {
       return id;
   }
   public void setX(int x1)
   {
       x=x1;
   }
   public void setY(int y1)
   {
       y=y1;
   }
   public void setID(ID id1)
   {
       id=id1;
   }
   public void setVX(int VX)
   {
       velocityX=VX;
   }
   public void setVY(int VY)
   {
       velocityY=VY;
   }
   public int getVX()
   {
       return velocityX;
   }
   public int getVY()
   {
       return velocityY;
   }
   public int getVZ()
   {
       return velocityZ;
   }
   public int getZ()
   {
       return z;
   }
   public void switchSheild(boolean swtch)
   {
        if(!sheilding)
        {
            sheilding=swtch;
        }
   }
   public boolean getRecharge()
   {
       return recharge;
   }
   public boolean isSheilding()
   {
       return sheilding;
   }
}
