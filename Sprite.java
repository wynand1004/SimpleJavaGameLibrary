import java.awt.*;    // Using AWT's Graphics and Color
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.geom.AffineTransform;
import java.util.HashMap;

class Sprite
{
    enum BorderAction {NONE, BOUNCE, STOP, WARP};
    
    // Simple Sprite Class
    // Default location and acceleration
    private double x = 0.0;
    private double y = 0.0;
    private double dx = 0.0;
    private double dy = 0.0;
    private double ddx = 0.0;
    private double ddy = 0.0;
    private double heading = 0.0;
    private double dh = 0.0;
    
    private String type = "";
    
    // Default sizes
    private int width = 24;
    private int height = 24;
    private int size = 24;
    
    // Initial state
    private boolean boundingBox = false;
    private boolean isVisible = true;

    private BorderAction borderAction = BorderAction.NONE;
    
    private boolean hasPhysics = true;
    
    private Color color = Color.BLUE;
    private boolean active = true;
    
    // Image
    private String filename;
    // Currently Displayed Image
    private BufferedImage image = null;
    // Original Image (used for resizing / rotating)
    private BufferedImage originalImage = null;
    
    // Images For Sprites that are reused
    private static HashMap<String, BufferedImage> imageMap = new HashMap<>();    
    
    /** 
    * Class constructor specifying x and y location.
    * @param x the x location
    * @param y the y location
    */
    Sprite(double x, double y)
    {
        this.x = x;
        this.y = y;
        // this.initialize();
    }

    /** 
    * Class constructor specifying x and y location and filename of sprite image.
    * @param filename the name of the file
    * @param x the x location
    * @param y the y location
    */    
    Sprite(double x, double y, String filename)
    {
        this.x = x;
        this.y = y;
        this.filename = filename;
        this.setImage(filename);
    }
    
    /** 
    * Set the image of a sprite.
    * @param filename the name of the file
    */      
    public void setImage(String filename)
    {
        try
        {
            // Check if image in imageMap
            
            if(imageMap.containsKey(filename))
            {
                if(this.originalImage != imageMap.get(filename))
                {
                    this.image = imageMap.get(filename);
                    this.originalImage = imageMap.get(filename);
                    
                    this.width = this.image.getWidth();
                    this.height = this.image.getHeight();
                    
                    this.size = (this.width + this.height) / 2;
                    
                    // System.out.print(imageMap.size() + " ");
                }
            }
            
            else
            {
                BufferedImage image = ImageIO.read(new File(filename));
                this.image = image;
                this.originalImage = image;
                
                this.width = image.getWidth();
                this.height = image.getHeight();
                
                this.size = (this.width + this.height) / 2;
                
                // System.out.println(this.width + " x " + this.height);
                imageMap.put(filename, image);
            }
        }
        catch(Exception e)
        {
        }
    }

    /** 
    * Move a sprite to x and y coordinates.
    * @param x the x coordinate
    * @param y the y coordinate
    */       
    public void goTo(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
    
    /** 
    * Move a sprite to x and y coordinates.
    * @param x the x coordinate
    * @param y the y coordinate
    */        
    public void goTo(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    /** 
    * Set the x coordinate.
    * @param x the x coordinate
    */        
    public void setX(int x)
    {
        this.x = x;
    }

    /** 
    * Set the x coordinate.
    * @param x the x coordinate
    */      
    public void setX(double x)
    {
        this.x = x;
    }
    
    /** 
    * Get the x coordinate.
    * @return the x coordinate
    */     
    public double getX()
    {
        return this.x;
    }

    /** 
    * Set the y coordinate.
    * @param y the y coordinate
    */   
    public void setY(int y)
    {
        this.y = y;
    }
    
    /** 
    * Set the y coordinate.
    * @param y the y coordinate
    */   
    public void setY(double y)
    {
        this.y = y;
    }
    
    /** 
    * Get the y coordinate.
    * @return the y coordinate
    */ 
    public double getY()
    {
        return this.y;
    }
    
    /** 
    * Get the current heading.
    * @return the heading in degrees
    */ 
    public double getHeading()
    {
        return this.heading;
    }
    
    /** 
    * Get the type.
    * @return the type
    */ 
    public String getType()
    {
        return this.type;
    }
    
    /** 
    * Set the type.
    * @param type the type
    */  
    public void setType(String type)
    {
        this.type = type;
    }
    
    /** 
    * Set delta x.
    * @param dx delta x
    */ 
    public void setDX(int dx)
    {
        this.dx = dx;
    }
    
    /** 
    * Set delta x.
    * @param dx delta x
    */ 
    public void setDX(double dx)
    {
        this.dx = dx;
    }
    
    /** 
    * Set delta y.
    * @param dy delta y
    */ 
    public void setDY(int dy)
    {
        this.dy = dy;
    }

    /** 
    * Set delta y.
    * @param dy delta y
    */     
    public void setDY(double dy)
    {
        this.dy = dy;
    }
    
    
    /** 
    * Set delta delta x.
    * @param ddx delta delta x
    */ 
    public void setDDX(double ddx)
    {
        this.ddx = ddx;
    }
    
    /** 
    * Set delta delta y.
    * @param ddy delta delta y
    */     
    public void setDDY(double ddy)
    {
        this.ddy = ddy;
    }
    
    /** 
    * Set heading in degrees.
    * @param heading heading
    */ 
    public void setHeading(int heading)
    {
        this.heading = heading;
        rotate(heading);
    }
    
    /** 
    * Set heading in degrees.
    * @param heading heading
    */ 
    public void setHeading(double heading)
    {
        this.heading = heading;
        rotate(heading);
    }
    
    /** 
    * Set delta heading in degrees.
    * @param dh delta heading
    */ 
    public void setDH(int dh)
    {
        this.dh = dh;
    }
    
    /** 
    * Set delta heading in degrees.
    * @param dh delta heading
    */ 
    public void setDH(double dh)
    {
        this.dh = dh;
    }
    
    /** 
    * Set whether or not the sprite has physics (dx, dy, gravity, collisions, etc).
    * @param hasPhysics has physics
    */ 
    public void setHasPhysics(boolean hasPhysics)
    {
        this.hasPhysics = hasPhysics;
    }
    
    /** 
    * Get whether or not the sprite has physics.
    * @return hasPhysics
    */ 
    public boolean getHasPhysics()
    {
        return this.hasPhysics;
    }
    
    /** 
    * Get delta x
    * @return dx
    */ 
    public double getDX()
    {
        return this.dx;
    }
    
    /** 
    * Get delta y
    * @return dy
    */ 
    public double getDY()
    {
        return this.dy;
    }

    /** 
    * Get delta delta x (acceleration x)
    * @return ddx
    */ 
    public double getDDX()
    {
        return this.ddx;
    }
    
    /** 
    * Get delta delta y (acceleration y)
    * @return ddy
    */ 
    public double getDDY()
    {
        return this.ddy;
    }

    /** 
    * Get sprite width
    * @return width
    */ 
    public int getWidth()
    {
        return this.width;
    }
    
    /** 
    * Get sprite height
    * @return height
    */ 
    public int getHeight()
    {
        return this.height;
    }
    
    /** 
    * Set height in pixels.
    * @param height height in pixels
    */ 
    public void setHeight(int height)
    {
        this.height = height;
    }
    
    /** 
    * Set width in pixels.
    * @param width width in pixels
    */ 
    public void setWidth(int width)
    {
        this.width = width;
    }
    
    /** 
    * Set border action: NONE, BOUNCE, STOP, WARP.
    * @param borderAction Sprite.BorderAction.____
    */ 
    public void setBorderAction(BorderAction borderAction)
    {
        this.borderAction = borderAction;
    }
    
    /** 
    * Set color.
    * @param color Color object
    * @see java.awt.Color
    */     
    public void setColor(Color color)
    {
        this.color = color;
    }
    
    /** 
    * Set whether or not to show a red bounding box around the sprite. Useful for debugging.
    * @param boundingBox true / false
    */  
    public void setBoundingBox(boolean boundingBox)
    {
        this.boundingBox = boundingBox;
    }
    
    /** 
    * Renders the sprite on a Graphics object, offset with a Camera object, canvas width, and canvas height.
    * @param g Graphics object
    * @param camera Camera object
    * @param canvasWidth width of the canvas (used for not rendering off canvas objects and border action)
    * @param canvasHeight height of the canvas (used for not rendering off canvas objects and border action)
    * @see Graphics
    * @see Camera
    */      
    public void render(Graphics g, Camera camera, int canvasWidth, int canvasHeight)
    {
        if(!active)
        {
            return;
        }
        
        // Check if visible
        if(this.isVisible)
        {
            int renderX = (int)this.x;
            int renderY = (int)this.y;
            
            // If there is a camera, add camera offset
            if(camera != null)
            {  
                renderX = renderX - camera.getX() + (canvasWidth / 2);
                renderY = renderY - camera.getY() + (canvasHeight / 2);
            }
            
            // Check if coordinates are on the visible screen
            // If not, do not render
            if(renderX < -this.width || renderX > canvasWidth)
            {
                return;
            }
            
            if(renderY < -this.height || renderY > canvasHeight)
            {
                return;
            }
            
            if(!(image==null))
            {
                // Render image
                g.drawImage(image, renderX , renderY, null);
            }
            else
            {
                // Render rectangle (default)
                g.setColor(color);
                g.fillRect(renderX, renderY, width, height);
            }
            
            // Render bounding box
            if(this.boundingBox)
            {
                g.setColor(Color.RED);
                g.drawLine(renderX, renderY, (renderX + this.width), renderY);
                g.drawLine(renderX, renderY, renderX, (renderY + this.height));
                g.drawLine((renderX + this.width), renderY, (renderX + this.width), (renderY + this.height));
                g.drawLine(renderX, (renderY + this.height), (renderX + this.width), (renderY + this.height));
            }
        }
    }
    
    /** 
    * Updates sprite attributes based on physics (dx, dy)
    * @param canvasWidth canvas width used for border action
    * @param canvasHeight canvas height used for border action
    * @param dt delta time 
    */     
    public void update(int canvasWidth, int canvasHeight, double dt)
    {
      if(!active)
      {
         return;
      }
      
      if(hasPhysics)
      {
          dx += ddx;
          dy -= ddy;
          
          x += dx * dt;
          y += dy * dt;
          
          heading += dh * dt;
          
          // System.out.println(dh + " " + heading);
          
          if(dh!=0)
          {
              rotate(heading);
          }
          
          // bounce
          if(this.borderAction == BorderAction.BOUNCE)
          {
              if (x > canvasWidth - this.width) 
              {
                  x = canvasWidth - this.width;  
                  dx = -dx;
              }
              
              else if (x < 0)
              {
                  x = 0;  
                  dx = -dx;
              }
              
              if (y > canvasHeight - this.height) 
              {
                  y = canvasHeight - this.height;  
                  dy = -dy;
              }
              
              else if (y < 0)
              {
                  y = 0;  
                  dy = -dy;
              }
              
          }
          
          else if(this.borderAction == BorderAction.WARP)
          {
              if (this.x > canvasWidth) 
              {
                 this.x = -this.width;
              }
              
              else if (this.y > canvasHeight) 
              {
                 this.y = -this.height;
              }
              
              else if (this.x < -this.width) 
              {
                 this.x = canvasWidth;
              }
              
              else if (this.y < -this.height) 
              {
                 this.y = canvasHeight;
              }          
          }
          
          else if(this.borderAction == BorderAction.STOP)
          {
              if (x > canvasWidth - this.width) 
              {
                  x = canvasWidth - this.width;  
                  dx = 0;
              }
              
              else if (x < 0)
              {
                  x = 0;  
                  dx = 0;
              }
              
              if (y > canvasHeight - this.height) 
              {
                  y = canvasHeight - this.height;  
                  dy = 0;
              }
              
              else if (y < 0)
              {
                  y = 0;  
                  dy = 0;
              }
              
          }
        }
    }
    
    /** 
    * Checks for a collision between two sprites using AABB collision detection
    * @return true if a collision occurs
    * @return false if no collision occurs
    */ 
    public boolean isCollision(Sprite other)
    {
        // Do not register collision with self
        if(this==other)
        {
            return false;
        }
        
        // Return false if it does not havePhysics
        if(!hasPhysics)
        {
            return false;
        }
        
        boolean xOverlap = (this.x <= (other.x + other.width)) && ((this.x + this.width) >= other.x);
        boolean yOverlap = (this.y <= (other.y + other.height)) && ((this.y + this.height) >= other.y);
        return xOverlap && yOverlap;
    }
    
    /** 
    * Checks for a collision between two sprites using distance checking (based on sprite size)
    * @return true if a collision occurs
    * @return false if no collision occurs
    */ 
    public boolean isCircleCollision(Sprite other)
    {
        double a = this.x - other.x;
        double b = this.y - other.y;
        
        double distance = Math.sqrt(a*a + b*b);
        
        if(distance < ((this.size+other.size)/2.0))
        {
            return true;
        }
        
        return false;
    }
    
    /** 
    * Set whether or not a sprite is active. Non-active sprites are not rendered.
    * @param active true / false
    */  
    public void setActive(boolean active)
    {
        this.active = active;
    }
    
    /** 
    * Resize a sprite.
    * @param width width in pixels
    * @param height in pixels
    */ 
    public void resize(int width, int height)
    {
        Image tempImage = this.originalImage.getScaledInstance(width, height, BufferedImage.TYPE_INT_ARGB);
        
        BufferedImage buffered = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        buffered.getGraphics().drawImage(tempImage, 0, 0 , null);
        
        this.image = buffered;
        this.width = width;
        this.height = height;
    }
    
    /** 
    * Scale a sprite.
    * @param ratio percent to scale (0.5 is half current size, 2.0 is twice current size)
    */ 
    public void scale(double ratio)
    {
        this.width = (int)(this.width * ratio);
        this.height = (int)(this.height * ratio);
        
        if(this.image != null)
        {
            resize(this.width, this.height);
        }
    }
    
    /** 
    * Rotate a sprite.
    * @param angle angle in degrees to rotate the sprite from current heading.
    */     
    public void rotate(double angle) {
        // REF: https://stackoverflow.com/questions/37758061/rotate-a-buffered-image-in-java
        // This ensures positive is counterclockwise rotation
        double centerX = this.x + (this.width/2);
        double centerY = this.y + (this.height/2);
        
        angle = -angle;
        
        double rads = Math.toRadians(angle);
        double sin = Math.abs(Math.sin(rads));
        double cos = Math.abs(Math.cos(rads));
        
        int w = this.originalImage.getWidth();
        int h = this.originalImage.getHeight();
        
        int newWidth = (int)(Math.floor(w * cos + h * sin));
        int newHeight = (int)(Math.floor(h * cos + w * sin));

        BufferedImage rotated = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = rotated.createGraphics();
        AffineTransform at = new AffineTransform();
        at.translate((newWidth - w) / 2, (newHeight - h) / 2);

        int x = w / 2;
        int y = h / 2;

        at.rotate(rads, x, y);
        
        g2d.setTransform(at);
        g2d.drawImage(this.originalImage, 0, 0, null);
        Color transparent = new Color(255, 255, 255, 0);
        g2d.setColor(transparent);
        g2d.drawRect(0, 0, newWidth - 1, newHeight - 1);
        g2d.dispose();
        
        // Update width and height and heading
        this.width = rotated.getWidth();
        this.height = rotated.getHeight();

        this.image = rotated;
        
        double xOffset = (this.x + this.width/2) - centerX;
        double yOffset = (this.y + this.height/2) - centerY;
        
        this.x -= xOffset;
        this.y -= yOffset;
        
    }

    /** 
    * Show a sprite. Sets isVisible to true.
    */     
    public void show()
    {
        this.isVisible = true;
    }
    
    /** 
    * Hide a sprite. Sets isVisible to false.
    */  
    public void hide()
    {
        this.isVisible = false;
    }
}
