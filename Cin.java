import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Rectangle;
/**
 * Write a description of class Cin here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Cin extends GameObject

{
   Handler handler;
   private BufferedImage img;
   private BufferedImage img2;
   HeadsUpDisplay hud;
   boolean notSpawned=true;
   boolean spawn=true;
   int dEnemyCounter=0;
   int reflect=0;
   int move;
   static boolean hasMetPlayer=false;
   int numBoss=0;
   int numIon=0;
   public Cin(int x,int y, int z, ID id, int VY, HeadsUpDisplay h1, Handler h)
   {
       super(x,y,z, id);
       handler=h;
       velocityY=VY;
       hud=h1;
       hud.setIsCin(true);
       try {
               img = ImageIO.read(getClass().getResource("Cin.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
       try {
               img2 = ImageIO.read(getClass().getResource("OCin.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
   }
   public void reset()
    {
        hud.CinHealth=1200;
    }
   public void tick()
   {
       y+=velocityY;
       pongFight();
       dEnemyCounter=0;
       doubleBoss();
       ionSection();
       move();
       collision();
       if(hud.CinHealth<=0)
       {
           handler.objects.remove((GameObject)this);
       }
   }
   private void pongFight()
   {
       if(/*hud.CinHealth==1200&&*/velocityY==0)
       {
           velocityY=4;
       }
       if((hud.CinHealth>=500&&hud.CinHealth<841)||(hud.CinHealth>=1000&&hud.CinHealth<1201))
       {
           mainAttack(1);
       }
       else if(hud.CinHealth>=860&&hud.CinHealth<1000)
       {
           mainAttack(2);
       }
       else if(hud.CinHealth>0&&hud.CinHealth<500)
       {
           mainAttack(5);
       }
   }
   private void doubleBoss()
   {
       if(hud.CinHealth<=800&&hud.CinHealth>500&&numBoss==0)
       {
           handler.addObject(new FireBossEnemy(500,0,270, ID.FireBossEnemy, 5,hud , handler,true));
           handler.addObject(new IceBossEnemy(600,0,270, ID.IceBossEnemy, 6,hud , handler,true));
           numBoss++;
       }
       if(hud.FireBossHealth>0||hud.IceBossHealth>0)
       {
           if(hud.CinHealth<500)
           {
               hud.CinHealth=500;
           }
       }
   }
   private void ionSection()
   {
       if(hud.CinHealth<=1000&&hud.CinHealth>860&&numIon==0)
       {
           handler.addObject(new Ion(700,0,270, ID.Ion, 8,hud , handler,true));
           numIon++;
       }
       if(hud.IonHealth>0)
       {
           if(hud.CinHealth<860)
           {
               hud.CinHealth=860;
            }
       }
    }
   private void mainAttack(int n)
   {
          if(spawn)
           {
               int random=(int)(7-(Math.random()*14));
               int random2=(int)(-1*((Math.random()*3)+5));
               handler.addObject(new DEnemy(x-25,y,270, ID.DEnemy, random2 ,random, handler));
               spawn=false;
            }
            for(int i=0; i<handler.objects.size();i++)
            {
                GameObject tempObj=handler.objects.get(i);
                if(tempObj.getID()==ID.DEnemy)
                {
                    dEnemyCounter++;
                }
            }
            if(dEnemyCounter<=(n-1))
            {
                spawn=true;
            }
            for(int i=0; i<handler.objects.size();i++)
                {
                GameObject tempObj=handler.objects.get(i);
                    if(tempObj.getID()==ID.DEnemy)
                    {
                        if(tempObj.getX()>=700&&tempObj.getX()<=702&&tempObj.getVX()>0)
                        {
                            reflect = (int)(Math.random()*3);
                            if(reflect!=1)
                            {
                                tempObj.setVX((tempObj.getVX()*-1));
                            }
                    }
                }
            }
           doge();
   }
   private void doge()
   {
       int dodge = (int)(Math.random()*70);
           if(dodge==1&&y>=40&&y<=Game.HEIGHT-100)
           {
                for(int i =0;i<handler.objects.size();i++)
                {
                    GameObject tempObj=handler.objects.get(i);
                    if(tempObj.getID()==ID.Enemy||tempObj.getID()==ID.FireEnemy
                    ||tempObj.getID()==ID.IceEnemy||tempObj.getID()==ID.IceBouncer
                    ||tempObj.getID()==ID.ElecEnemy||tempObj.getID()==ID.DEnemy)
                    {
                        if(tempObj.getY()-y<=25&&tempObj.getY()-y>=0&&tempObj.getX()>700)
                        {
                            y-=30;
                        }
                        if(tempObj.getY()-y<=0&&tempObj.getY()-y>=-25&&tempObj.getX()>700)
                        {
                            y+=30;
                        }
                    }
                }
            }
   }
   private void move()
   {
       move=(int)(Math.random()*100+1);
       for(int i=0;i<handler.objects.size();i++)
       {
            GameObject tempObj= handler.objects.get(i);
            if(tempObj.getID()==ID.Player)
            {
                if(tempObj.getY()-y<=200 && tempObj.getY()-y>=-200)
                {
                    hasMetPlayer=true;
                }
                else
                {
                    hasMetPlayer=false;
                }
                if(((tempObj.getY()-y>200 || tempObj.getY()-y<-200)&& hasMetPlayer))
                {
                    velocityY*=-1;
                }
            }
       }
       if(move%100==0&& y<Game.HEIGHT+20&&y>80)
       {
           velocityY*=-1;
       }
       if(y>=Game.HEIGHT-60&&velocityY>0)
       {
           y=Game.HEIGHT-60;
           velocityY*=-1;
       }
       if(y<=0&&velocityY<0)
       {
           y=0;
           velocityY*=-1;
       }
       collision();
       if(hud.CinHealth<=0)
       {
           handler.objects.remove((GameObject)this);
       }
   }
   public void render(Graphics g, boolean top)
   {
            if(velocityY>=0)
            {
                g.drawImage(img,x-90,y-60,Window.getFrame());
            }
            else
            {
                g.drawImage(img2,x-90,y-60,Window.getFrame());
            }
            if(reflect!=1)
            {
                g.setColor(Color.magenta);
                g.fillRect(700,0,20,Game.HEIGHT);
            }
   }
   private void collision()
    {
        for(int i=0;i<handler.objects.size();i++)
        {
            GameObject tempObj= handler.objects.get(i);
            if(tempObj.getID()==ID.DEnemy)
            {
                if(getBounds().intersects(tempObj.getBounds()))
                {
                    hud.CinHealth-=50;
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
       return new Rectangle(x,y,40,40);
   }
}
