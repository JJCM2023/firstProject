import java.awt.Graphics;
import java.awt.Rectangle;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
/**
 * Write a description of class FireBossEnemy here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class FireBossEnemy extends GameObject
{
   Handler handler;
   private BufferedImage img;
   HeadsUpDisplay hud;
   int move;
   static int moveCount=0;
   static boolean justMoved=false;
   static int attackCount=0;
   static boolean justAttacked=false;
   static int attackNum=0;
   static boolean attacking=false;
   static int part=0;
   static boolean hasMetPlayer=false;
   static boolean inBounds=true;
   static boolean attack1=false;
   static boolean attack2=false;
   static boolean attack3=false;
   static int attackStyle=0;
   static int attackStyleCount=0;
   boolean isPuppet;
   public FireBossEnemy(int x,int y, int z, ID id, int VY, HeadsUpDisplay h1, Handler h, boolean b)
   {
       super(x,y,z, id);
       handler=h;
       velocityY=VY;
       hud=h1;
       hud.setIsFireBoss(true);
       isPuppet=b;
       if(isPuppet)
       {
           try {
               img = ImageIO.read(getClass().getResource("PuppetFireBoss.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else
        {
            try {
               img = ImageIO.read(getClass().getResource("FireBoss.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
   }
   public void reset()
    {
        hud.FireBossHealth=400;
    }
   public void tick()
   {
       y+=velocityY;
       if(hud.FireBossHealth>=200)
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
                handler.addObject(new FireEnemy(x-30,y,z, ID.FireEnemy,-10 , handler));
                attackNum++;
                if(attackNum>=3)
                {
                    attackNum=0;
                    attacking=false;
                    justAttacked=true;
                }
            }
        }
        else
        {
            attackStyle=(int)(Math.random()*3);
           if(!attack1&&!attack2&&!attack3)
           {
            if(attackStyle==0)
            {
                attack1=true;
            }
            else if(attackStyle==2)
            {
                attack2=true;
            }
            else
            {
                attack3=true;
            }
           } 
            if(attack1&&!isPuppet)
            {
                attackStyleCount++;
                move();
                handler.addObject(new FireEnemy(x-30,y-90,z, ID.FireEnemy,-10 , handler));
                handler.addObject(new FireEnemy(x-30,y+90,z, ID.FireEnemy,-10 , handler));
                attack1=true;
                if(attackStyleCount==300)
                {
                    attack1=false;
                    attackStyleCount=0;
                }
            }
            else if(attack1)
            {
                attackStyleCount++;
                move();
                puppetAttack1();
                attack1=true;
                if(attackStyleCount==200)
                {
                    attack1=false;
                    attackStyleCount=0;
                }
            }
            else if(attack2)
            {
                attack2=true;
                velocityY=0;
                if(attackStyleCount==0)
                {
                    for(int i=0; i<Game.WIDTH;i++)
                    {
                        if(i%80==0)
                        {
                            handler.addObject(new FireEnemy(x-30,i,z, ID.FireEnemy,0 , handler));
                        }
                    }
                }
                attackStyleCount++;
                if(attackStyleCount==100)
                {
                    for(int i=0;i<handler.objects.size();i++)
                    {
                        GameObject tempObj= handler.objects.get(i);
                        if(tempObj.getID()==ID.FireEnemy)
                        {
                            tempObj.setVX(-15);
                        }
                    }
                    velocityY=5;
                    attackStyleCount=0;
                    attack2=false;
                }
           }
           else if(attack3)
           {
               attack3=true;
               velocityY=0;
               if(attackStyleCount==0)
               {
                    for(int i=0; i<Game.WIDTH;i++)
                    {
                        if(i%20==0)
                        {
                            handler.addObject(new FireEnemy(x-30,i,z, ID.FireEnemy,0 , handler));
                        }
                    }
               }
               attackStyleCount++;
               if(attackStyleCount==50)
               {
                    for(int i=0;i<handler.objects.size();i++)
                    {
                        GameObject tempObj= handler.objects.get(i);
                        if(tempObj.getID()==ID.FireEnemy)
                        {
                            tempObj.setVX(-15);
                        }
                    }
                    velocityY=5;
                    attackStyleCount=0;
                    attack3=false;
               }
           }
        }
       collision();
       if(hud.FireBossHealth<=0)
       {
           death();
       }
   }
   private void death()
   {
           for(int i=0;i<handler.objects.size();i++)
           {
               GameObject tempObj= handler.objects.get(i);
               if(tempObj.getID()==ID.FireEnemy||tempObj.getID()==ID.IceEnemy)
               {
                   handler.objects.remove(tempObj);
                   if(handler.objects.size()>0)
                   {
                       i--;
                   }
               }
           }
           handler.objects.remove((GameObject)this);
   }
   public void render(Graphics g, boolean top)
   {
        g.drawImage(img,x-90,y-60,Window.getFrame());
   }
   private void puppetAttack1()
   {
       if(attackCount==0)
       {
            handler.addObject(new FireEnemy(x-30,y-90,z, ID.FireEnemy,0 , handler));
            handler.addObject(new IceEnemy(x-30,y+90,z, ID.IceEnemy,0 , handler));
       }
       if(attackCount==20)
       {
           for(int i=0;i<handler.objects.size();i++)
           {
               GameObject tempObj= handler.objects.get(i);
               if(tempObj.getID()==ID.IceEnemy&&tempObj.getX()==x-30)
               {
                   tempObj.setVX(-10);
                   tempObj.setVY(-5);
               }
               if(tempObj.getID()==ID.FireEnemy&&tempObj.getX()==x-30)
               {
                   tempObj.setVX(-10);
                   tempObj.setVY(5);
               }
           }
           handler.addObject(new IceEnemy(x-30,y-90,z, ID.IceEnemy,0 , handler));
            handler.addObject(new FireEnemy(x-30,y+90,z, ID.FireEnemy,0 , handler));
       }
       if(attackCount==40)
       {
           for(int i=0;i<handler.objects.size();i++)
           {
               GameObject tempObj= handler.objects.get(i);
               if(tempObj.getID()==ID.IceEnemy&&tempObj.getX()==x-30)
               {
                   tempObj.setVX(-10);
                   tempObj.setVY(5);
               }
               if(tempObj.getID()==ID.FireEnemy&&tempObj.getX()==x-30)
               {
                   tempObj.setVX(-10);
                   tempObj.setVY(-5);
               }
           }
           attackCount=0;
       }
       attackCount++;
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
   private void collision()
    {
        for(int i=0;i<handler.objects.size();i++)
        {
            GameObject tempObj= handler.objects.get(i);
            if(tempObj.getID()==ID.Enemy||tempObj.getID()==ID.FireEnemy||tempObj.getID()==ID.IceEnemy&&tempObj.getVX()>0)
            {
                if(getBounds().intersects(tempObj.getBounds()))
                {
                    HeadsUpDisplay.FireBossHealth-=5;
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