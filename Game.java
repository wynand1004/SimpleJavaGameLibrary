import java.awt.*;     // Using AWT's Graphics and Color
import javax.swing.*; // Using Swing's components and containers

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.util.ArrayList;
 
/**
 * Simple Java Game Library based on:
 * https://www3.ntu.edu.sg/home/ehchua/programming/java/J4b_CustomGraphics.html
 */
public class Game extends JFrame
{
    // Define named-constants
    private int CANVAS_WIDTH = 1024;
    private int CANVAS_HEIGHT = 768;
    private int UPDATE_INTERVAL = 20; // milliseconds
    
    private int fps = 60;
    
    private long start;
    private long end;
    private double dt = 1.0;
 
    private DrawCanvas canvas;  // the drawing canvas (an inner class extends JPanel)
    
    private JButton btnTemp;
    
    // Sprites
    private ArrayList<Sprite> sprites = new ArrayList<Sprite>();
    
    private String key = "";
 
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
        
        // Add KeyListener
        btnTemp = new JButton(" ");
        this.add(btnTemp);
        btnTemp.addKeyListener(new KeyListener()
        {  
          public void keyPressed(KeyEvent e){  
                 registerKeyPress(e.getKeyText(e.getKeyCode()));
          }  
          
          public void keyTyped(KeyEvent e)
          {
                
          }
          
          public void keyReleased(KeyEvent e)
          {
                 registerKeyRelease(e.getKeyText(e.getKeyCode()));
          }
        }); 
 
        // Create a new thread to run update at regular interval
        Thread updateThread = new Thread() 
        {
            @Override
            public void run() 
            {
                while (true) 
                {  
                    start = System.currentTimeMillis();
                     
                    // Update sprites
                    for(Sprite sprite: sprites)
                    {
                     sprite.update(CANVAS_WIDTH, CANVAS_HEIGHT, dt);    
                    }
                    
                    repaint();  // Refresh the JFrame. Called back paintComponent()
                    
                    try 
                    {
                        // Delay and give other thread a chance to run
                        Thread.sleep(UPDATE_INTERVAL);  // milliseconds
                        end = System.currentTimeMillis();
                        dt = (double)((end-start)/1000F);
                        // System.out.println("dt: " + dt);
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
    
    public void registerKeyPress(String key)
    {
          // System.out.println("Key: " + key + " pressed.");
          this.key = key;
    }
    
    public void registerKeyRelease(String key)
    {
          // System.out.println("Key: " + key + " released.");
          this.key = "";
    }
    
    public String getKey()
    {
         return key;
    }
    
    public ArrayList<Sprite> getSprites()
    {
         return sprites;
    }
    
    public void setFPS(int fps)
    {
        this.fps = fps;
        this.UPDATE_INTERVAL = (int)(1000.0 / fps);
    }
}
