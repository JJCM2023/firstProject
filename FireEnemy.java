import java.awt.Graphics;
import java.awt.Rectangle;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
/**
 * Write a description of class FireEnemy here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class FireEnemy extends GameObject
{
    Handler handler;
    private BufferedImage img;
    public FireEnemy(int x, int y, int z, ID id, int VX, Handler h)
    {
        super(x,y,z,id);
        velocityZ=0;
        velocityX=VX;
        velocityY=0;
        handler=h;
        try {
            img = ImageIO.read(getClass().getResource("fire.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Rectangle getBounds()
    {
        return new Rectangle(x,y,20,20);
    }
    public void reset()
    {
        handler.objects.remove(this);
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
    }
    public void render(Graphics g, boolean top)
    {
            g.drawImage(img,x,y,Window.getFrame());
    }
}
