import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
/**
 * Write a description of class KeyInput here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class KeyInput extends KeyAdapter
{
    private Handler handler;
    int f=0;
    public KeyInput(Handler h)
    {
        handler=h;
    }
    public void keyPressed(KeyEvent e)
    {
        int key=e.getKeyCode();
            for(int i =0;i<handler.objects.size();i++)
            {
                GameObject tempObj=handler.objects.get(i);
                if(tempObj.getID()==ID.Player)
                {
                    if(key==KeyEvent.VK_A) 
                    {
                            tempObj.setVY(-10);
                    }
                    if(key==KeyEvent.VK_D) 
                    {
                            tempObj.setVY(10);
                    }
                    if(key==KeyEvent.VK_W&&HeadsUpDisplay.score>=100)//&& !tempObj.getRecharge())
                    {
                        tempObj.switchSheild(true);
                    }
                    if(key==KeyEvent.VK_P)
                    {
                        Game.paused=true;
                    }
                    if(key==KeyEvent.VK_O)
                    {
                        Game.paused=false;
                    }
                    if(key==KeyEvent.VK_M)
                    {
                        Game.PlayAgain=!Game.PlayAgain;
                        Game.paused=false;
                    }
                }
            }
    }
    public void keyReleased(KeyEvent e)
    {
        int key=e.getKeyCode();

        for(int i =0;i<handler.objects.size();i++)
        {
            GameObject tempObj=handler.objects.get(i);
            if(tempObj.getID()==ID.Player)
            {
                if(key==KeyEvent.VK_A) 
               {
                   tempObj.setVY(0);
               }
               if(key==KeyEvent.VK_D) 
               {
                   tempObj.setVY(0);
               }
               if(key==KeyEvent.VK_S)
               {
                   handler.addObject(new Enemy(tempObj.getX()+50,tempObj.getY(),tempObj.getZ(), ID.Enemy,10 , handler));         
               }
               if(key==KeyEvent.VK_W&&HeadsUpDisplay.score>=100)//&& !tempObj.getRecharge())
               {
                   tempObj.switchSheild(false);
               }
            }
        }
    }
}
