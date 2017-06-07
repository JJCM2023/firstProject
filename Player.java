import java.awt.Graphics;
import java.awt.Rectangle;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
/**
 * Write a description of class Player here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Player extends GameObject
{
    private BufferedImage img;
    private BufferedImage img1;
    Handler handler;
    int part=0;
    boolean burned=false;
    static int burnCount=0;
    static int sheildCount=0;
    public Player(int x, int y, int z, ID id, Handler h)
    {
        super(x,y,z,id);
        handler=h;
        velocityZ=0;
        try {
            img = ImageIO.read(getClass().getResource("Player.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            img1 = ImageIO.read(getClass().getResource("img1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void reset()
    {
        HeadsUpDisplay.Health=100;
    }
    public Rectangle getBounds()
    {
        if(!sheilding)
        {
            return new Rectangle(x,y,40,20);
        }
        else
        {
            return new Rectangle(x+50,y-40,20,80);
        }
    }
    public void tick()
    {
        if(HeadsUpDisplay.isBoss || HeadsUpDisplay.isFireBoss)
        {
            z=Game.clamp(z,270,270);
        }
        y=Game.clamp(y,20,490);
        x+=velocityX;
        y+=velocityY;
        part++;
        if(y>=Game.HEIGHT)
        {
            velocityY*=-1;
        }
        if(y<=0)
        {
            velocityY*=-1;
        }
        collision();
        if(burned)
        {
            burnCount++;
            if(burnCount%20==0)
            {
                HeadsUpDisplay.Health--;
            }
            if(burnCount>100)
            {
                burnCount=0;
                burned=false;
            }
        }
        /*if(sheilding)
        {
            sheildCount++;
            if(sheildCount==200)
            {
                sheildCount=0;
                sheilding=false;
                recharge=true;
            }
        }
        /*if(recharge)
        {
            sheildCount++;
            if(sheildCount==50)
            {
                recharge=false;
                sheildCount=0;
            }
        }*/
    }
    private void collision()
    {
        for(int i=0;i<handler.objects.size();i++)
        {
            GameObject tempObj= handler.objects.get(i);
            if(sheilding && (tempObj.getID()==ID.Enemy||tempObj.getID()==ID.FireEnemy
            ||tempObj.getID()==ID.IceEnemy||tempObj.getID()==ID.IceBouncer
            ||tempObj.getID()==ID.ElecEnemy||tempObj.getID()==ID.DEnemy))
            {
                if(getBounds().intersects(tempObj.getBounds())&&tempObj.getVX()<0)
                    {
                        tempObj.setVX(tempObj.getVX()*-1);
                    }
            }
            else if(sheilding && tempObj.getID()==ID.Lightning)
            {
                if(getBounds().intersects(tempObj.getBounds()))
                    {
                        handler.objects.remove(i);
                        if(handler.objects.size()>0)
                        {
                            i--;
                        }
                        //sheildCount=0;
                        //sheilding=false;
                        //recharge=true;
                    }
            }
            else
            {
                if(tempObj.getID()==ID.Enemy)
                {
                    if(getBounds().intersects(tempObj.getBounds()))
                    {
                        HeadsUpDisplay.Health--;
                        handler.objects.remove(i);
                        if(handler.objects.size()>0)
                        {
                            i--;
                        }
                    }
                }
                if(tempObj.getID()==ID.ElecEnemy)
                {
                    if(getBounds().intersects(tempObj.getBounds()))
                    {
                        HeadsUpDisplay.Health-=3;
                        handler.objects.remove(i);
                        if(handler.objects.size()>0)
                        {
                            i--;
                        }
                    }
                }
                if(tempObj.getID()==ID.Lightning)
                {
                    if(getBounds().intersects(tempObj.getBounds()))
                    {
                        HeadsUpDisplay.Health-=20;
                        handler.objects.remove(i);
                        if(handler.objects.size()>0)
                        {
                            i--;
                        }
                    }
                }
                if(tempObj.getID()==ID.FireEnemy)
                {
                    if(getBounds().intersects(tempObj.getBounds()))
                    {
                        HeadsUpDisplay.Health--;
                        burned=true;
                        handler.objects.remove(i);
                        if(handler.objects.size()>0)
                        {
                            i--;
                        }
                    }
                }
                if(tempObj.getID()==ID.IceEnemy||tempObj.getID()==ID.IceBouncer)
                {
                    if(getBounds().intersects(tempObj.getBounds()))
                    {
                        HeadsUpDisplay.Health--;
                        velocityY=0;
                        handler.objects.remove(i);
                        if(handler.objects.size()>0)
                        {
                            i--;
                        }
                    }
                }
                if(getBounds().intersects(tempObj.getBounds()))
                    {
                        if(tempObj.getID()==ID.DEnemy)
                        {
                            HeadsUpDisplay.Health-=50;
                            handler.objects.remove(i);
                            if(handler.objects.size()>0)
                            {
                                i--;
                            }
                        }
                    }
            }
        }
    }
    public void render(Graphics g, boolean top)
    {
            g.drawImage(img,x-50,y-80,Window.getFrame());
            if(sheilding)
            {
                g.drawImage(img1,x+50,y-40,Window.getFrame());
            }
    }
    public void switchSheild(boolean swtch)
    {
        sheilding=swtch;
    }
}

