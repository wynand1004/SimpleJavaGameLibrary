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
import java.util.Set;
import java.util.HashSet;
 
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
    
    // Background Sprites
    private ArrayList<Sprite> backgroundSprites = new ArrayList<Sprite>();
    
    // Labels
    private ArrayList<Label> labels = new ArrayList<Label>();
    
    // Currently pressed keys
    private Set<Integer> keys = new HashSet<Integer>();
    
    // Mouse presses
    private boolean isMousePressed = false;
    
    private int mouseX = 0;
    private int mouseY = 0;
    
    // Camera
    private Camera camera;;
 
    // Attributes
    private Color backgroundColor = Color.BLACK;
    private int gravity = 0;
    
    // Controls repaint and game updates
    private Timer timer;
    
    // Used to prevent updates while rendering
    public boolean isRendering = false;
    
    
    /** 
    * Class constructor.
    */
    public SJGL()
    {
        this.run();
    }

    /** 
    * Class constructor specifying the tile displayed in the window.
    * Default canvas size is 1024 x 768
    * @param title the title to be displayed
    */
    public SJGL(String title)
    {
        this.title = title;
        this.run();
    }   

    /** 
    * Class constructor specifying the tile displayed in the window and canvas width and height.
    * @param title the title to be displayed
    * @param canvasWidth width of the camvas in pixels
    * @param canvasHeight height of the canvas in pixels
    */
    
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
                
                if(!isRendering)
                {
                    // Update sprites
                    for(int i=sprites.size()-1;i>-1;i--)
                    {
                        // Add gravity
                        Sprite sprite = sprites.get(i);
                        sprite.setDY(sprite.getDY() + gravity);
                        sprite.update(canvasWidth, canvasHeight, dt);    
                    }
                }
                
                if(camera != null)
                {
                    camera.update();
                }
            }
        });
        
        timer.start();
    }
  
    // Define Inner class DrawCanvas, which is a JPanel used for custom drawing
    class DrawCanvas extends JPanel {
        final SJGL sjgl = SJGL.this;
        
        @Override
        public void paintComponent(Graphics g) {
            try
            {
                if(!sjgl.isRendering)
                {
                    super.paintComponent(g);  // paint parent's background
                }
            }
            catch(Exception e)
            {
                
            }
            
            // Render game sprites
            if(!sjgl.isRendering)
            {
                // System.out.print(" RENDER ");
                render(g);
            }
            
            // This avoids random slowdowns on Linux
            Toolkit.getDefaultToolkit().sync();
        }
        
        private void render(Graphics g)
        {
            // System.out.print("T");
            sjgl.isRendering = true;
            
            setBackground(backgroundColor);
            
            // Render Background Sprites
            // Render in order (background in back)
            // Background sprites do not follow the camera
            for(int i=0;i<backgroundSprites.size();i++)
            {
                Sprite backgroundSprite = backgroundSprites.get(i);
                backgroundSprite.render(g, null, canvasWidth, canvasHeight);
            }
            
            // Render Sprites           
            // Render in reverse (player in front) 
            for(int i=sprites.size()-1;i>-1;i--)
            {
                Sprite sprite = sprites.get(i);
                sprite.render(g, camera, canvasWidth, canvasHeight);
            }
            
            // Render Text
            // Render in order
            for(int i=0;i<labels.size();i++)
            {
                Label label = labels.get(i);
                label.render(g);
            }
            
            sjgl.isRendering = false;
            // System.out.print("F");
        }
    }
    
    // Public methods

    /** 
    * Add a sprite to ArrayList sprites.
    * @param sprite the sprite to be added
    * @see Sprite
    */
    public void addSprite(Sprite sprite)
    {
        while(isRendering)
        {   
            sleep(1);
        }
        sprites.add(sprite);
    }

    /** 
    * Add a background sprite to ArrayList backgroundSprites.
    * @param sprite the sprite to be added
    * @see Sprite
    */
    public void addBackgroundSprite(Sprite sprite)
    {
        while(isRendering)
        {   
            sleep(1);
        }
        backgroundSprites.add(sprite);
    }

    /** 
    * Add a label to ArrayList labels.
    * @param label the label to be added
    * @see Label
    */
    public void addLabel(Label label)
    {
        while(isRendering)
        {   
            sleep(1);
        }
        labels.add(label);
    }
    

    /** 
    * Remove a sprite from ArrayList sprites.
    * @param sprite the sprite to be removed
    * @see Sprite
    */
    public void removeSprite(Sprite sprite)
    {
        while(isRendering)
        {   
            sleep(1);
        }
        sprites.remove(sprite);
    }

    /** 
    * Remove a background sprite from ArrayList backgroundSprites.
    * @param sprite the sprite to be removed
    * @see Sprite
    */    
    public void removeBackgroundSprite(Sprite sprite)
    {
        while(isRendering)
        {   
            sleep(1);
        }
        backgroundSprites.remove(sprite);
    }

    /** 
    * Remove a label from ArrayList labels.
    * @param label the label to be removed
    * @see Label
    */    
    public void removeLabel(Label label)
    {
        while(isRendering)
        {   
            sleep(1);
        }
        labels.remove(label);
    }

    /** 
    * Set the background color.
    * @param color the color value
    * @see java.awt.Color;
    */    
    public void setBackgroundColor(Color color)
    {
         this.backgroundColor = color;
    }

    /** 
    * Set global gravity value.
    * @param gravity the gravity value
    */        
    public void setGravity(int gravity)
    {
        this.gravity = gravity;
    }

    /** 
    * Set the game camera.
    * @param camera the camera
    * @see Camera
    */        
    public void setCamera(Camera camera)
    {
        this.camera = camera;
    }

    /** 
    * Set the target of the camera.
    * @param target the sprite for the camera to follow
    * @see Sprite
    */        
    public void setCameraTarget(Sprite target)
    {
        if(this.camera == null)
        {
            this.camera = new Camera(target);
        }
        else
        {
            this.camera.setTarget(target);
        }
    }

    /** 
    * Set the camera to an x/y location on the screen.
    * @param x the x coordinate
    * @param y the y coordinate
    */        
    public void setCameraTarget(int x, int y)
    {
        if(this.camera == null)
        {
            this.camera = new Camera(new Sprite(x, y));
        }
        else
        {
            this.camera.setTarget(x, y);
        }
    }

    /** 
    * Returns the width of the canvas.
    * @return the canvas width in pixels
    */        
    public int getCanvasWidth()
    {
        return this.canvasWidth;
    }

    /** 
    * Returns the height of the canvas.
    * @return the canvas height in pixels 
    */ 
    public int getCanvasHeight()
    {
        return this.canvasHeight;
    }
    
    /** 
    * Returns the current delta time.
    * @return delta time
    */ 
    public double getDT()
    {
        return this.dt;
    }
    
    // Keyboard 
    /** 
    * Adds key pressed to Set keys.
    * @param key that is pressed
    * @see java.awt.event.KeyEvent
    */ 
    private void registerKeyPress(int key)
    {
          keys.add(key);
    }

    /** 
    * Removes key pressed from Set keys.
    * @param key that is released
    * @see java.awt.event.KeyEvent
    */ 
    private void registerKeyRelease(int key)
    {
          keys.remove(key);
    }

    /** 
    * Indicates if a particular key is currently pressed.
    * @return true if pressed / false if not currently pressed
    */     
    public boolean isKeyPressed(int key)
    {
        if(keys.contains(key))
        {
            return true;
        }
        
        return false;
    }
    
    // Mouse
    /** 
    * Indicates if the mouse is currently pressed.
    * @return true if pressed / false if not currently pressed
    */  
    public boolean getIsMousePressed()
    {
        return isMousePressed;
    }

    /** 
    * Returns the x coordinate of the mouse.
    * @return x coordinate of the mouse
    */  
    public int getMouseX()
    {
        return mouseX;
    }

    /** 
    * Returns the y coordinate of the mouse.
    * @return y coordinate of the mouse
    */  
    public int getMouseY()
    {
        return mouseY;
    }

    /** 
    * Returns the game camera.
    * @return the game camera
    * @see Camera
    */  
    public Camera getCamera()
    {
        return camera;
    }
    
    /** 
    * Returns the ArrayList sprites.
    * @return an ArrayList containing all game sprites
    * @see Sprite
    */  
    public ArrayList<Sprite> getSprites()
    {
         // Return copy to avoid concurrentModificationException
         return new ArrayList<Sprite>(sprites);
    }

    /** 
    * Returns the ArrayList backgroundSprites.
    * @return an ArrayList containing all game background sprites
    * @see Sprite
    */ 
    public ArrayList<Sprite> getBackgroundSprites()
    {
         // Return copy to avoid concurrentModificationException
         return new ArrayList<Sprite>(backgroundSprites);
    }
    
    /** 
    * Returns the ArrayList labels.
    * @return an ArrayList containing all game labels
    * @see Label
    */ 
    public ArrayList<Label> getLabels()
    {
         // Return copy to avoid concurrentModificationException
         return new ArrayList<Label>(labels);
    }
    
    /** 
    * Sets the frames per second of the game.
    * @param fps the target frames per second
    */ 
    // Set FPS (also updates updateInterval and dt
    public void setFPS(int fps)
    {
        this.fps = fps;
        this.updateInterval = (int)(1000.0 / fps);
        this.dt = updateInterval / 1000.0;
        
        this.timer.setDelay(this.updateInterval);
    }
    
    /** 
    * Helper method to pause changes to ArrayLists while rendering.
    */ 
    private void sleep(int milliseconds)
    {
        // System.out.print("* ");
        try
        {
            Thread.sleep(milliseconds);
        }
        catch(Exception e)
        {
        }
    }
        
        
}
