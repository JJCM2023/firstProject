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
public class IceBossEnemy extends GameObject
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
   static boolean attack2=false;
   static boolean attack3=false;
   public static int spawnCount=0;
   static int attackStyle=0;
   static int attackStyleCount=0;
   private BufferedImage img;
   boolean isPuppet;
   public IceBossEnemy(int x,int y, int z, ID id, int VY, HeadsUpDisplay h1, Handler h,boolean b)
   {
       super(x,y,z, id);
       handler=h;
       velocityY=VY;
       hud=h1;
       hud.setIsIceBoss(true);
       isPuppet=b;
       if(isPuppet)
       {
           try {
               img = ImageIO.read(getClass().getResource("PuppetIceBoss.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }   
        else
        {
            try {
                img = ImageIO.read(getClass().getResource("IceBoss.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
   }
   public void reset()
    {
        hud.IceBossHealth=600;
    }
   public void tick()
   {
       y+=velocityY;
       if(hud.IceBossHealth>=400)
       {
           attack();
        }
        else if(hud.IceBossHealth>=200)
        {
           if(!attack1&&!attack2&&!attack3)
           {
                chooseAttack();
           }
            if(attack1)
            {
                attack1=true;
                attack1();
            }
            else if(attack2)
            {
                attack2=true;
                attack2();
           }
           else if(attack3)
           {
               attack3=true;
               attack3();
           }
        }
        else
        {
           if(velocityY==0)
           {
              velocityY=-5;
           }
           move(false);
           lastAttack();
       }  
       collision();
       if(hud.IceBossHealth<=0)
       {
           death();
       }
   }
   private void death()
   {
           for(int i=0;i<handler.objects.size();i++)
           {
               GameObject tempObj= handler.objects.get(i);
               if(tempObj.getID()==ID.IceEnemy||tempObj.getID()==ID.IceBouncer)
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
   private void lastAttack()
   {
       if(attackCount==0)
       {
            handler.addObject(new IceEnemy(x-30,y-90,z, ID.IceEnemy,0 , handler));
            handler.addObject(new FireEnemy(x-30,y+90,z, ID.FireEnemy,0 , handler));
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
   private void chooseAttack()
   {
       attackStyle=(int)(Math.random()*3);
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
   private void attack()
   {
            move(true);
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
                handler.addObject(new IceEnemy(x-30,y,z, ID.IceEnemy,-10 , handler));
                attackNum++;
                if(attackNum>=3)
                {
                    attackNum=0;
                    attacking=false;
                    justAttacked=true;
                }
            }
   }
   private void move(boolean randomMotion)
   {
                if(randomMotion)
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
   private void attack1()
   {
                attackStyleCount++;
                move(true);
                handler.addObject(new IceEnemy(x-30,y-90,z, ID.IceEnemy,-10 , handler));
                handler.addObject(new FireEnemy(x-30,y+90,z, ID.FireEnemy,-10 , handler));
                if(attackStyleCount==300)
                {
                    attack1=false;
                    attackStyleCount=0;
                }
   }
   private void attack2()
   {
                velocityY=0;
                if(attackStyleCount==0)
                {
                    for(int i=0; i<Game.WIDTH;i++)
                    {
                        if(i%80==0)
                        {
                            handler.addObject(new IceEnemy(x-30,i,z, ID.IceEnemy,0 , handler));
                        }
                    }
                }
                attackStyleCount++;
                if(attackStyleCount==75)
                {
                    for(int i=0;i<handler.objects.size();i++)
                    {
                        GameObject tempObj= handler.objects.get(i);
                        if(tempObj.getID()==ID.IceEnemy)
                        {
                            tempObj.setVX(-15);
                        }
                    }
                    velocityY=5;
                    attackStyleCount=0;
                    attack2=false;
                }
   }
   private void attack3()
   {
                velocityY=0;
               if(attackStyleCount==0)
               {
                    for(int i=0; i<Game.WIDTH;i++)
                    {
                        if(i%20==0)
                        {
                            handler.addObject(new IceEnemy(x-30,i,z, ID.IceEnemy,0 , handler));
                        }
                    }
               }
               attackStyleCount++;
               if(attackStyleCount==50)
               {
                    for(int i=0;i<handler.objects.size();i++)
                    {
                        GameObject tempObj= handler.objects.get(i);
                        if(tempObj.getID()==ID.IceEnemy)
                        {
                            tempObj.setVX(-15);
                        }
                    }
                    velocityY=5;
                    attackStyleCount=0;
                    attack3=false;
               }
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
            if(tempObj.getID()==ID.Enemy||tempObj.getID()==ID.FireEnemy||tempObj.getID()==ID.IceEnemy||tempObj.getID()==ID.IceBouncer)
            {
                if(getBounds().intersects(tempObj.getBounds()))
                {
                    HeadsUpDisplay.IceBossHealth-=10;
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