import java.awt.Graphics;
import java.awt.Rectangle;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
/**
 * Write a description of class Enemy here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Enemy extends GameObject
{
    Handler handler;
    private BufferedImage img;
    public Enemy(int x, int y, int z, ID id, int VX, Handler h)
    {
        super(x,y,z,id);
        velocityZ=0;
        velocityX=VX;
        velocityY=0;
        handler=h;
        try {
            img = ImageIO.read(getClass().getResource("a.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void reset()
    {
        handler.objects.remove(this);
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
    }
    public void render(Graphics g, boolean top)
    {
            g.drawImage(img,x,y,Window.getFrame());
    }
}
