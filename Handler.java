import java.awt.Graphics;
import java.util.LinkedList;
/**
 * Write a description of class Handler here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Handler
{
   LinkedList<GameObject> objects=new LinkedList<GameObject>();
   
   public void tick()
   {
       for(int i=0;i<objects.size();i++)
       {
           GameObject tempOBJ= objects.get(i);
           
           tempOBJ.tick();
       }
   }
   public void render(Graphics g, boolean top)
   {
       for(int i=0;i<objects.size();i++)
       {
           GameObject tempOBJ= objects.get(i);
           tempOBJ.render(g, top);
       }
   }
   public void addObject(GameObject object)
   {
       objects.add(object);
   }
   public void removeObject(GameObject object)
   {
       objects.remove(object);
   }
   public void reset()
   {
       for(int i=0;i<objects.size();i++)
       {
           GameObject tempOBJ= objects.get(i);
           tempOBJ.reset();
       }
       for(int i=0;i<objects.size();i++)
       {
           GameObject tempOBJ= objects.get(i);
        if(tempOBJ.getID()!=ID.Player)
        {
           removeObject(tempOBJ);
        }
       }
   }
}
