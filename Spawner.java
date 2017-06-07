/**
 * Write a description of class Spawner here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Spawner
{
    private Handler handler;
    private HeadsUpDisplay hud;
    private int part;
    private int numBoss=0;
    public Spawner(Handler h, HeadsUpDisplay h1)
    {
        handler=h;
        hud=h1;
    }
    public void tick()
    {
       part++;
       if(hud.getScore()<=45)
       {
           if(part%(100-hud.getScore())==0)
           {
               for(int i=0;i<(int)((Math.random()*(hud.getScore())));i++)
               {
                   handler.addObject(new Enemy(1000,(int)(Math.random()*540),(int)(Math.random()*540), ID.Enemy, (int)((Math.random()*-4)-2), handler));
              }
           }
       }
       else if(hud.getScore()<=50)
       {
        
       }
       else if(hud.getScore()<=95)
       {
           if(part%40==0)
           {
               for(int i=0;i<(int)((Math.random()*60));i++)
               {
                   handler.addObject(new Enemy(1000,(int)(Math.random()*540),(int)(Math.random()*540), ID.Enemy, (int)((Math.random()*-4)-4), handler));
               }
           }
        }
       else if(hud.getScore()<=100)
       {
           if(hud.Health<100)
           { 
               hud.Health++;
           }
       }
        else if(numBoss==0)
       {
           handler.addObject(new Cin(750,270,270, ID.Cin, 0,hud , handler));
           numBoss=1;
       }
       /*else if(numBoss==0)
       {
           handler.addObject(new Ion(500,0,270, ID.Ion, 7,hud , handler,false));
           numBoss=1;
       }
       
       else if(numBoss==0)
       {
           handler.addObject(new IceBossEnemy(500,0,270, ID.IceBossEnemy, 5,hud , handler,false));
           numBoss=1;
       }
       
       else if(hud.IceBossHealth<=0)
       {
           Game.finishLevel=true;
       }
       else if(numBoss==0)
       {
           handler.addObject(new FireBossEnemy(500,0,270, ID.FireBossEnemy, 5,hud , handler,false));
           numBoss=1;
       }
       if(hud.FireBossHealth<=0)
       {
           Game.finishLevel=true;
       }*/
       for(int i=0;i<handler.objects.size();i++)
       {
           GameObject tempObj=handler.objects.get(i);
           if(tempObj.getX()<0)
           {
               handler.removeObject(tempObj);
               if(handler.objects.size()>0)
               {
                   i--;
               }
           }
           if(tempObj.getX()>=Game.WIDTH+100)
           {
               handler.removeObject(tempObj);
               if(handler.objects.size()>0)
               {
                   i--;
               }
           }
       }
    }
}
