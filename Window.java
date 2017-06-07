import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;
/**
 * Write a description of class Window here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Window extends Canvas
{
  private static JFrame frame;
    public Window(int width, int height, String title, Game game)
  {
      frame= new JFrame(title);
      
      Dimension dim= new Dimension(width,height);
      frame.setPreferredSize(dim);
      frame.setMaximumSize(dim);
      frame.setMinimumSize(dim);
      
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setResizable(false);
      frame.setLocationRelativeTo(null);
      
      frame.add(game);
      frame.setVisible(true);
      game.start();
  }
  public static JFrame getFrame()
  {
      return frame;
  }
}
