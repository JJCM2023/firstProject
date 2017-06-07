import java.awt.Graphics;
import java.awt.Rectangle;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
/**
 * Write a description of class Ion here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Ion extends GameObject
{
   Handler handler;
   HeadsUpDisplay hud;
   int move;
   static int moveCount=0;
   static boolean justMoved=false;
   static int attackCount=0;
   static boolean justAttacked=false;
   static int attackNum=0;
   static boolean attacking=false;
   static boolean hasMetPlayer=false;
   static boolean inBounds=true;
   static boolean attack1=false;
   private BufferedImage img;
   static int counter=0;
   boolean waitTimer=false;
   boolean create=false;
   int move2=0;
   boolean isPuppet;
   public Ion(int x,int y, int z, ID id, int VY, HeadsUpDisplay h1, Handler h, boolean b)
   {
       super(x,y,z, id);
       handler=h;
       velocityY=VY;
       hud=h1;
       hud.setIsIon(true);
       isPuppet=b;
       if(isPuppet)
       {
           try {
               img = ImageIO.read(getClass().getResource("PuppetIon.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else
        {
            try {
               img = ImageIO.read(getClass().getResource("Ion.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
   }
   public void tick()
   {
       y+=velocityY;
       if(hud.IonHealth>=550)
       {
           move1();
       }
       else if(hud.IonHealth>=275)
       {
           move2();
       }
       else if(hud.IonHealth>0)
       {
           move3();
       }
       else
       {
           handler.objects.remove((GameObject)this);
       }
       doge();
       collision();
    }
    private void doge()
    {
           int dodge = (int)(Math.random()*40);
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
                        if(((tempObj.getY()-y>200 || tempObj.getY()-y<-200)&& hasMetPlayer) &&justMoved==false )
                        {
                            velocityY*=-1;
                            justMoved=true;
                        }
                    }
                }
                if(justMoved)
                {
                    moveCount++;
                    if(moveCount==50)
                    {
                        moveCount=0;
                        justMoved=false;
                    }
                }
                if(move%100==0&& moveCount==0 && y<Game.HEIGHT+20&&y>80)
                {
                    velocityY*=-1;
                    justMoved=true;
                }
                if(y>=Game.HEIGHT-60)
                {
                    y=Game.HEIGHT-60;
                    velocityY*=-1;
                    justMoved=true;
                }
                if(y<=0)
                {
                    y=0;
                    velocityY*=-1;
                    justMoved=true;
                }
   }
   private void move1()
   {
            move();
            if(justAttacked)
            {
                attackCount++;
                if(attackCount>=15)
                {
                    justAttacked=false;
                    attackCount=0;
                }
            }
       
            int spawn=(int)(Math.random()*45);
            if(spawn==15)
            {
                justAttacked=false;
            }
            if(spawn%15==0)
            {
                attacking=true;
            }
            if(!justAttacked && attacking)
            {
                handler.addObject(new ElecEnemy(x-30,y,z, ID.ElecEnemy,-12 , handler));
                attackNum++;
                if(attackNum>=5)
                {
                    attackNum=0;
                    attacking=false;
                    justAttacked=true;
                }
            }
   }
   private void move2()
   {
       if(counter%250==0||waitTimer)
       {
           velocityY=0;
           waitTimer=true;
           if(counter%250==50)
           {
               waitTimer=false;
           }
       }
       else{
           if(velocityY==0)
           {
               velocityY=9;
            }
           if(justMoved)
            {
                moveCount++;
                if(moveCount==50)
                {
                    moveCount=0;
                    justMoved=false;
                }
            }
            if(move%100==0&& moveCount==0 && y<Game.HEIGHT+20&&y>80)
            {
                velocityY*=-1;
              justMoved=true;
           }
           if(y>=Game.HEIGHT-60)
           {
               y=Game.HEIGHT-60;
               velocityY*=-1;
               justMoved=true;
            }
            if(y<=0)
            {
                y=0;
                velocityY*=-1;
                justMoved=true;
            }
            int spawn=(int)(Math.random()*30);
            if(spawn==15)
            {
                justAttacked=false;
            }
            if(spawn%15==0)
            {
                attacking=true;
            }
            if(!justAttacked && attacking)
            {
                handler.addObject(new ElecEnemy(x-30,y,z, ID.ElecEnemy,-12 , handler));
                attackNum++;
                if(attackNum>=5)
                {
                    attackNum=0;
                    attacking=false;
                    justAttacked=true;
                }
            }
       }
       move2=(int)(Math.random()*Game.HEIGHT);
       counter++;
       if(counter%1000==0)
       {
           int lpos=0;
           for(int i=0;i<handler.objects.size();i++)
           {
               GameObject tempObj= handler.objects.get(i);
               if(tempObj.getID()==ID.Player)
               {
                   y=tempObj.getY(); 
                   lpos=tempObj.getY();
                   handler.addObject(new Lightning(0,lpos,z, ID.Lightning,0 , handler));
               }
            }

       }
       else if(counter%250==0)
       {
           y=move2;
           handler.addObject(new Lightning(0,move2,z, ID.Lightning,0 , handler));
        }
   }
   public void move3()
   {
       if(!attacking)
       {
           int num=(int)(Math.random()*70); 
        
           if(num==1)
           {
               attack1=true;
            }
        }
       if(!attack1)
       {
               if(velocityY==0)
            {
               velocityY=10;
            }
            move();
            handler.addObject(new ElecEnemy(x-30,y,z, ID.ElecEnemy,-14 , handler));
            attackNum++;
            if(attackNum%200==0)
            {
                attack1=true;
            }
        }
        else
        {
            velocityY=0;
            attacking=true;
            attackNum++;
            if(attackNum%80==0)
            {
                attack1=false;
                attacking=false;
            }

            if(attackNum%20==0)
            {
                move2=(int)(Math.random()*Game.HEIGHT);
                y=move2;
                handler.addObject(new Lightning(0,move2,z, ID.Lightning,0 , handler));
            }
            if(create&&attackNum%20==3)
                {
                    handler.addObject(new Lightning(0,move2,z, ID.Lightning,0 , handler));
                    create=false;
                }
        }
   }
   public void reset()
   {
       hud.IonHealth=800;
   }
   public void render(Graphics g, boolean top)
   {
        g.drawImage(img,x-90,y-60,Window.getFrame());
   }
   private void collision()
    {
        for(int i=0;i<handler.objects.size();i++)
        {
            GameObject tempObj= handler.objects.get(i);
            if(tempObj.getID()==ID.Enemy||tempObj.getID()==ID.FireEnemy
            ||tempObj.getID()==ID.IceEnemy||tempObj.getID()==ID.IceBouncer
            ||tempObj.getID()==ID.ElecEnemy)
            {
                if(getBounds().intersects(tempObj.getBounds()))
                {
                    hud.IonHealth-=10;
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
