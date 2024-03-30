import java.awt.*;
import javax.swing.*;

import javax.swing.Timer;

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
    final private int CANVAS_WIDTH = 1024;
    final private int CANVAS_HEIGHT = 768;
    
    private int UPDATE_INTERVAL = 20; // milliseconds
    private int fps = 50;
    
    // dt (delta time) to keep framerates consistent
    private double dt = 0.02;
 
    private DrawCanvas canvas;  // the drawing canvas (using an inner class extends JPanel)
    
    // Need this for keyboard binding (TODO: FIX THIS)
    private JButton btnTemp;
    
    // Sprites
    private ArrayList<Sprite> sprites = new ArrayList<Sprite>();
    
    // Currently pressed key
    private String key = "";
 
    // Attributes
    private Color backgroundColor = Color.BLACK;
    private double gravity = 0.0;
    
    // Controls repaint and game updates
    private Timer timer;
 
    // Constructor to setup the GUI components and event handlers
    public Game(String title) 
    {
        canvas = new DrawCanvas();
        canvas.setDoubleBuffered(true);
        canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
        canvas.setFocusable(true);
        canvas.requestFocusInWindow();
        
        this.setContentPane(canvas);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.setTitle(title);
        this.setVisible(true);
        
        // Add KeyListener to canvas
        canvas.addKeyListener(new KeyListener()
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
 
        // Create a timer to repaint, and update sprites
        timer = new Timer(UPDATE_INTERVAL, new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent ae) {
                repaint();
        
                // Update sprites
                for(Sprite sprite: sprites)
                {
                    // Add gravity
                    sprite.setDY(sprite.getDY() + gravity);
                    sprite.update(CANVAS_WIDTH, CANVAS_HEIGHT, dt);    
                }
            }
        });
        
        timer.start();
    }
  
    // Define Inner class DrawCanvas, which is a JPanel used for custom drawing
    class DrawCanvas extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);  // paint parent's background
            
            // Render game sprites
            render(g);
            
            // This avoids random slowdowns on Linux
            Toolkit.getDefaultToolkit().sync();
        }
        
        private void render(Graphics g)
        {
            setBackground(backgroundColor);
            
            // Render Sprites
            for(Sprite sprite: sprites)
            {
                sprite.render(g);
            }
            
            // Render Text
        }
    }
    
    // Public methods
    public void addSprite(Sprite sprite)
    {
         sprites.add(sprite);
    }
    
    public void removeSprite(Sprite sprite)
    {
        sprites.remove(sprite);
    }
    
    public void setBackgroundColor(Color color)
    {
         this.backgroundColor = color;
    }
    
    // Keyboard 
    public void registerKeyPress(String key)
    {
          this.key = key;
    }
    
    public void registerKeyRelease(String key)
    {
          this.key = "";
    }
    
    public String getKey()
    {
         return key;
    }
    
    // Sprites ArrayList
    public ArrayList<Sprite> getSprites()
    {
         return sprites;
    }
    
    // Set FPS (also updates UPDATE_INTERVAL and dt
    public void setFPS(int fps)
    {
        this.fps = fps;
        this.UPDATE_INTERVAL = (int)(1000.0 / fps);
        this.dt = UPDATE_INTERVAL / 1000.0;
        
        this.timer.setDelay(this.UPDATE_INTERVAL);
    }
}
