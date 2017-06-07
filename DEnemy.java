import java.awt.Graphics;
import java.awt.Color;
import java.awt.Rectangle;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
/**
 * Write a description of class DEnemy here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class DEnemy extends GameObject
{
   Handler handler;
    private BufferedImage img;
    public DEnemy(int x, int y, int z, ID id, int VX,int VY, Handler h)
    {
        super(x,y,z,id);
        velocityZ=0;
        velocityX=VX;
        velocityY=VY;
        handler=h;
        try {
            img = ImageIO.read(getClass().getResource("DEnemy.png"));
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }
    public Rectangle getBounds()
    {
            return new Rectangle(x,y,20,20);
    }
    public void tick()
    {
        x+=velocityX;
        z+=velocityZ;
        y+=velocityY;
        if(y>=Game.HEIGHT-60)
            {
                y=Game.HEIGHT-60;
                velocityY*=-1;
            }
        if(y<=0)
            {   
                y=0;
                velocityY*=-1;
            }
        if(x<=10)
        {
            HeadsUpDisplay.Health--;
            handler.objects.remove(this);
        }
        if(x>=740)
        {
            HeadsUpDisplay.CinHealth-=20;
            handler.objects.remove(this);
        }
    }
    public void reset()
    {
        handler.objects.remove(this);
    }
    public void render(Graphics g, boolean top)
    {
        g.setColor(Color.magenta);
        if(x<=31)
        {
            g.fillRect(0,0,100,Game.HEIGHT);
        }
        g.drawImage(img,x,y,Window.getFrame());
    }
}
