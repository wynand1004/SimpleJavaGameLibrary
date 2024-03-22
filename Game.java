import java.awt.*;    // Using AWT's Graphics and Color
import javax.swing.*; // Using Swing's components and containers
import java.util.ArrayList;
 
/**
 * Simple Java Game Library based on:
 * https://www3.ntu.edu.sg/home/ehchua/programming/java/J4b_CustomGraphics.html
 */
public class Game extends JFrame 
{
   // Define named-constants
   private static final int CANVAS_WIDTH = 640;
   private static final int CANVAS_HEIGHT = 480;
   private static final int UPDATE_INTERVAL = 30; // milliseconds
 
   private DrawCanvas canvas;  // the drawing canvas (an inner class extends JPanel)
 
   // Sprites
   private ArrayList<Sprite> sprites = new ArrayList<Sprite>();
 
 
   // Attributes
   private Color backgroundColor = Color.BLACK;
 
   // Constructor to setup the GUI components and event handlers
   public Game(String title) 
   {
      canvas = new DrawCanvas();
      canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
      this.setContentPane(canvas);
      this.setDefaultCloseOperation(EXIT_ON_CLOSE);
      this.pack();
      this.setTitle(title);
      this.setVisible(true);
 
      // Create a new thread to run update at regular interval
      Thread updateThread = new Thread() 
      {
         @Override
         public void run() 
         {
            while (true) 
            {
               // Update sprites
               for(Sprite sprite: sprites)
               {
                sprite.update(CANVAS_WIDTH, CANVAS_HEIGHT);   
               }
               
               repaint();  // Refresh the JFrame. Called back paintComponent()
               
               try 
               {
                  // Delay and give other thread a chance to run
                  Thread.sleep(UPDATE_INTERVAL);  // milliseconds
               } 
               catch (InterruptedException ignore) 
               {
               }
            }
         }
      };
      updateThread.start(); // called back run()
   }
  
   // Define Inner class DrawCanvas, which is a JPanel used for custom drawing
   class DrawCanvas extends JPanel {
      @Override
      public void paintComponent(Graphics g) {
         super.paintComponent(g);  // paint parent's background
         setBackground(backgroundColor);
         
         // Render Sprites
         for(Sprite sprite: sprites)
         {
            sprite.render(g);
         }
      }
   }
   
   public void addSprite(Sprite sprite)
   {
       sprites.add(sprite);
   }
   
   public void setBackgroundColor(Color color)
   {
       this.backgroundColor = color;
   }
}
