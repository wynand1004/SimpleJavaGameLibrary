import java.awt.*;
import javax.swing.*;

import javax.swing.Timer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

import java.util.ArrayList;
 
/**
 * Simple Java Game Library based on:
 * https://www3.ntu.edu.sg/home/ehchua/programming/java/J4b_CustomGraphics.html
 */
public class SJGL extends JFrame
{
    // Define window attributes
    private int canvasWidth = 1024;
    private int canvasHeight = 768;
    
    private int updateInterval = 20; // milliseconds
    private int fps = 50;
    
    private String title = "SJGL Game by @TokyoEdtech";
    
    // dt (delta time) to keep framerates consistent
    private double dt = 0.02;
 
    private DrawCanvas canvas;  // the drawing canvas (using an inner class extends JPanel)
    
    // Sprites
    private ArrayList<Sprite> sprites = new ArrayList<Sprite>();
    
    // Labels
    private ArrayList<Label> labels = new ArrayList<Label>();
        
    // Currently pressed key
    private int key = 0;
    
    // Mouse presses
    private boolean isMousePressed = false;
    
    private int mouseX = 0;
    private int mouseY = 0;
 
    // Attributes
    private Color backgroundColor = Color.BLACK;
    private double gravity = 0.0;
    
    // Controls repaint and game updates
    private Timer timer;
    
    public SJGL()
    {
        this.run();
    }
    
    public SJGL(String title)
    {
        this.title = title;
        this.run();
    }   
    
    public SJGL(String title, int canvasWidth, int canvasHeight)
    {
        this.title = title;
        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;
        this.run();
    }
 
    // Setup the GUI components and event handlers
    public void run() 
    {
        canvas = new DrawCanvas();
        canvas.setDoubleBuffered(true);
        canvas.setPreferredSize(new Dimension(canvasWidth, canvasHeight));
        canvas.setFocusable(true);
        canvas.requestFocusInWindow();
        
        this.setContentPane(canvas);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.setTitle(this.title);
        this.setVisible(true);
        
        // Add KeyListener to canvas
        canvas.addKeyListener(new KeyListener()
        { 
          @Override
          public void keyPressed(KeyEvent e){  
                 registerKeyPress(e.getKeyCode());
          }  
          
          @Override
          public void keyTyped(KeyEvent e)
          {
                
          }
          
          @Override
          public void keyReleased(KeyEvent e)
          {
                 registerKeyRelease(e.getKeyCode());
          }
        }); 
        
        // MouseListener to canvas
        canvas.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    mouseX = e.getX();
                    mouseY = e.getY();
                    isMousePressed = true;
                    // System.out.println("Mouse Pressed: " + e.getX() + " " + e.getY());
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    mouseX = e.getX();
                    mouseY = e.getY();
                    isMousePressed = false;
                    // System.out.println("Mouse Released: " + e.getX() + " " + e.getY());
                }
            });
 
        // Create a timer to repaint, and update sprites
        timer = new Timer(updateInterval, new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent ae) {
                repaint();
        
                // Update sprites
                for(Sprite sprite: sprites)
                {
                    // Add gravity
                    sprite.setDY(sprite.getDY() + gravity);
                    sprite.update(canvasWidth, canvasHeight, dt);    
                }
            }
        });
        
        timer.start();
    }
  
    // Define Inner class DrawCanvas, which is a JPanel used for custom drawing
    class DrawCanvas extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            try
            {
                super.paintComponent(g);  // paint parent's background
            }
            catch(Exception e)
            {
                
            }
            // Render game sprites
            render(g);
            
            // This avoids random slowdowns on Linux
            Toolkit.getDefaultToolkit().sync();
            

        }
        
        private void render(Graphics g)
        {
            setBackground(backgroundColor);
            
            // Render Sprites
            // Iterate through clone of sprites to avoid concurrentModificationException
            for(Sprite sprite: new ArrayList<Sprite>(sprites))
            {
                sprite.render(g);
            }
            
            // Render Text
            for(Label label: new ArrayList<Label>(labels))
            {
                label.render(g);
            }
        }
    }
    
    // Public methods
    public void addSprite(Sprite sprite)
    {
         sprites.add(sprite);
    }
    
    public void addLabel(Label label)
    {
         labels.add(label);
    }
    
    public void removeSprite(Sprite sprite)
    {
        sprites.remove(sprite);
    }
    
    public void removeLabel(Label label)
    {
         labels.remove(label);
    }
    
    public void setBackgroundColor(Color color)
    {
         this.backgroundColor = color;
    }
    
    public void setGravity(double gravity)
    {
        this.gravity = gravity;
    }
    
    public int getCanvasWidth()
    {
        return this.canvasWidth;
    }
    
    public int getCanvasHeight()
    {
        return this.canvasHeight;
    }
    
    // Keyboard 
    private void registerKeyPress(int key)
    {
          this.key = key;
    }
    
    private void registerKeyRelease(int key)
    {
          this.key = 0;
    }
    
    public int getKey()
    {
         return key;
    }
    
    // Mouse
    public boolean getIsMousePressed()
    {
        return isMousePressed;
    }
    
    public int getMouseX()
    {
        return mouseX;
    }
    
    public int getMouseY()
    {
        return mouseY;
    }
    
    // Sprites ArrayList
    public ArrayList<Sprite> getSprites()
    {
         // Return copy to avoid concurrentModificationException
         return new ArrayList<Sprite>(sprites);
    }
    
    public ArrayList<Label> getLabels()
    {
         // Return copy to avoid concurrentModificationException
         return new ArrayList<Label>(labels);
    }
    
    // Set FPS (also updates updateInterval and dt
    public void setFPS(int fps)
    {
        this.fps = fps;
        this.updateInterval = (int)(1000.0 / fps);
        this.dt = updateInterval / 1000.0;
        
        this.timer.setDelay(this.updateInterval);
    }
}
