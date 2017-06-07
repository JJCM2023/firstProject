import java.awt.Graphics;
import java.awt.Color;
import java.awt.Rectangle;
/**
 * Write a description of class BossEnemy here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BossEnemy extends GameObject
{
   Handler handler;
   HeadsUpDisplay hud;
   public BossEnemy(int x,int y, int z, ID id, int VY, HeadsUpDisplay h1, Handler h)
   {
       super(x,y,z, id);
       handler=h;
       velocityY=VY;
       hud=h1;
       hud.setIsBoss(true);
   }
   public void tick()
   {
       y+=velocityY;
       if(y>=Game.HEIGHT-60)
       {
           velocityY*=-1;
       }
       if(y<=0)
       {
           velocityY*=-1;
       }
       int spawn=(int)(Math.random()*20);
       if(spawn==5)
       {
        handler.addObject(new Enemy(x-50,y,z, ID.Enemy,-10 , handler));
       }
       collision();
       if(hud.BossHealth==0)
       {
           handler.objects.remove((GameObject)this);
       }
   }
   public void reset()
    {
        hud.BossHealth=200;
    }
   public void render(Graphics g, boolean top)
   {
        if(top)
        {
            g.setColor(Color.gray);
            g.fillOval(x,y,40,20);
            g.setColor(Color.black);
            g.fillRect(x+15,y-10,18,40);
            g.fillRect(x+16,y-20,17,60);
            g.fillRect(x+17,y-30,16,80);
            g.fillRect(x+18,y-40,15,100);
            g.fillRect(x+19,y-50,14,120);
            g.fillRect(x+20,y-55,13,130);
            g.fillRect(x+21,y-57,12,134);
            g.fillRect(x+22,y-59,11,138);
            g.fillRect(x+23,y-60,10,140);
        }
        if(!top)
        {
            g.setColor(Color.gray);
            g.fillOval(x,y,40,20);
            g.setColor(Color.black);
            g.fillRect(x+16,y,20,5);
        }
   }
   private void collision()
    {
        for(int i=0;i<handler.objects.size();i++)
        {
            GameObject tempObj= handler.objects.get(i);
            if(tempObj.getID()==ID.Enemy)
            {
                if(getBounds().intersects(tempObj.getBounds()))
                {
                    HeadsUpDisplay.BossHealth-=5;
                    handler.objects.remove(i);
                    if(handler.objects.size()>0)
                    {
                        i--;
                    }
                }
            }
        }
    }
   public Rectangle getBounds()
   {
       return new Rectangle(x,y,40,20);
   }
}
