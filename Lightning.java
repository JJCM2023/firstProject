import java.awt.Graphics;
import java.awt.Color;
import java.awt.Rectangle;
/**
 * Write a description of class Lightning here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Lightning extends GameObject
{
    Handler handler;
    private int lightningCounter=0;
    public Lightning(int x, int y, int z, ID id, int VX, Handler h)
    {
        super(x,y,z,id);
        velocityZ=0;
        velocityX=VX;
        velocityY=0;
        handler=h;
    }
    public Rectangle getBounds()
    {
        return new Rectangle(x,y,1000,40);
    }
    public void tick()
    {
        x+=velocityX;
        y+=velocityY;
        lightningCounter++;
        if(lightningCounter==5)
        {
             handler.objects.remove((GameObject)this);
        }
    }
    public void reset()
    {
        handler.objects.remove(this);
    }
    public void render(Graphics g, boolean top)
    {
        g.setColor(Color.yellow);
        g.fillRect(x,y,1000,40);
    }  
}
