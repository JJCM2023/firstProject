import java.awt.Canvas;
import java.awt.image.BufferStrategy;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.KeyAdapter;

import java.applet.AudioClip;
import java.applet.Applet;
/**
 * Write a description of class Game here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Game extends Canvas implements Runnable
{
   public static boolean finishLevel=false;
   public boolean stopPlay=false;
   static boolean paused=true;
   private Thread thread;
   boolean running=false;
   public static final int WIDTH=1000, HEIGHT=540;
   private Handler handler;
   private HeadsUpDisplay hud;
   private Spawner spawn;
   private static boolean top=true;
   public static boolean PlayAgain=false;
   public int part2=0;
   public int score2=158;
   private AudioClip sound1=Applet.newAudioClip(getClass().getResource("The Alchemist.wav"));;
   public Game()
   {
       handler=new Handler();
       hud=new HeadsUpDisplay();
       spawn=new Spawner(handler,hud);
       this.addKeyListener(new KeyInput(handler));
       new Window(WIDTH, HEIGHT,"Icarus",this);
       handler.addObject(new Player(40,270,270, ID.Player, handler));
   }
   public static void setTop(boolean boo)
   {
       top=boo;
   }
   public synchronized void start()
   {
       thread=new Thread(this);
       thread.start();
       running = true;
   }
   public synchronized void stop()
   {
    try
    {
        thread.join();
        running=false;
    }
    catch(Exception e)
    {
        e.printStackTrace();
    }
   }
   public void run()
   {
       this.requestFocus();
       long lastTime=System.nanoTime();
       double amountOfTicks=60.0;
       double ns=1000000000/amountOfTicks;
       double delta=0;
       long timer=System.currentTimeMillis();
       int frames=0;
       while(running)
       {
           long now = System.nanoTime();
           delta+=(now-lastTime)/ns;
           lastTime=now;
           while(delta>=1)
           {
               part2++;
               if(part2%100==0)
               {
                   score2++;
               }
               if(score2%158==0&&!stopPlay)
               {
                   sound1.play();
                   stopPlay=true;
               }
               if(score2%159==0)
               {
                   stopPlay=false;
               }
               tick();
               delta--;
           }
           if(running)
           {
               render();
           }
           frames++;
           if(System.currentTimeMillis()-timer>1000)
           {
               timer+=1000;
               //System.out.println("FPS: "+frames);
               frames=0;
           }
       }
       stop();
   }
   private void tick()
   {
       if(!paused)
       {
           handler.tick();
           hud.tick();
           spawn.tick();
           if(finishLevel)
           {
               for(int i=0;i<handler.objects.size();i++)
               {
                   GameObject tempObj= handler.objects.get(i);
                   handler.removeObject(tempObj);
               }
           }
           if(hud.Health<=0||hud.CinHealth<=0)
           {
               paused=true;
               if(PlayAgain==true)
               {
                   hud.reset();
                   handler.reset();
                   PlayAgain=false;
               }
           }
       }
   }
   private void render()
   {
        BufferStrategy bs= this.getBufferStrategy();
        if(bs==null)
        {
            this.createBufferStrategy(3);
            return;
        }
        if(!paused)
        {
            Graphics g=bs.getDrawGraphics();
            g.setColor(Color.black);
            g.fillRect(0,0,WIDTH, HEIGHT);
       
            handler.render(g, top);
            hud.render(g);
            if(finishLevel)
            {
                g.setColor(Color.black);
                g.fillRect(0,0,WIDTH, HEIGHT);
                g.setColor(Color.white);
                g.drawString("You Win",Game.WIDTH/2,Game.HEIGHT/2);
            }
            g.dispose();
            bs.show();
        }
        else if(hud.Health<=0)
        {
            Graphics g=bs.getDrawGraphics();
            g.setColor(Color.black);
            g.fillRect(0,0,WIDTH, HEIGHT);
            
            g.setColor(Color.white);
            g.drawString("You've lost",475,180);
            g.drawString("Your score was "+hud.score+" points but again that doesn't mean anything",365,195);
            g.drawString("Would you like to play again? (Press M)",415,210);
            g.dispose();
            bs.show();
        }
        else if(hud.CinHealth<=0&&hud.score<=325)
        {
            Graphics g=bs.getDrawGraphics();
            g.setColor(Color.black);
            g.fillRect(0,0,WIDTH, HEIGHT);
            
            g.setColor(Color.white);
            g.drawString("Congratulations",435,180);
            g.drawString("You've Won",475,195);
            g.drawString("Your score was "+hud.score+" Geez, slow down there speedy, how'd you do that so fast?",345,210);
            g.drawString("Would you like to play again? (Press M)",415,225);
            g.dispose();
            bs.show();
        }
        else if(hud.CinHealth<=0&&hud.score>350)
        {
            Graphics g=bs.getDrawGraphics();
            g.setColor(Color.black);
            g.fillRect(0,0,WIDTH, HEIGHT);
            
            g.setColor(Color.white);
            g.drawString("Congratulations",435,180);
            g.drawString("You've Won",475,195);
            g.drawString("Your score was "+hud.score+" points. Wow, that took you a long time slow poke",350,210);
            g.drawString("Would you like to play again? (Press M)",415,225);
            g.dispose();
            bs.show();
        }
        else if(hud.CinHealth<=0)
        {
            Graphics g=bs.getDrawGraphics();
            g.setColor(Color.black);
            g.fillRect(0,0,WIDTH, HEIGHT);
            
            g.setColor(Color.white);
            g.drawString("Congratulations",450,180);
            g.drawString("You've Won",475,195);
            g.drawString("Your score was "+hud.score+" points, but again that doesn't mean anything",360,210);
            g.drawString("Would you like to play again? (Press M)",415,225);
            g.dispose();
            bs.show();
        }
        else
        {
            Graphics g=bs.getDrawGraphics();
            g.setColor(Color.black);
            g.fillRect(0,0,WIDTH, HEIGHT);
            
            g.setColor(Color.white);
            g.drawString("Press 'O' to start",455,200+45);
            g.drawString("Instructions",470,200+75);
            g.drawString("Press 'P' to pause",455,200+90); 
            g.drawString("Press 'A' to fly right",450,200+105);
            g.drawString("Press 'D' to fly left",451,200+120);
            g.drawString("Press 'S' to shoot",455,200+135);
            g.drawString("Press 'W' to shield",454,200+150);
            g.drawString("Icarus",475,200);
            g.drawString("The object of the game is to not get hit by any of the objects flying towards you. Score is kept by time, but does not matter as the only way to win is by defeating the Boss.",45,200+165);
            g.drawString("Good Luck!",470,200+180);
            g.dispose();
            bs.show();
        }
   }
   public static int clamp(int var, int min, int max)
   {
       if(var>=max)
       {
           return var = max;
       }
       else if(var<=min)
       {
           return var=min;
       }
       else
       {
           return var;
       }
   }
   public static void main (String args[])
   {
    new Game();
   }
}
