import java.awt.*;    // Using AWT's Graphics and Color
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.geom.AffineTransform;

class Sprite
{
    // Simple Sprite Class
    // Default location and acceleration
    private double x = 0.0;
    private double y = 0.0;
    private double dx = 0.0;
    private double dy = 0.0;
    private double heading = 0.0;
    private double dh = 0.0;
    
    // Default sizes
    private int width = 24;
    private int height = 24;
    private int size = 24;
    
    // Initial state
    private boolean bounce = false;
    private boolean warp = false;
    private boolean stop = false;
    private boolean boundingBox = false;
    private boolean isVisible = true;
    
    private boolean hasPhysics = true;
    
    private Color color = Color.BLUE;
    private boolean active = true;
    
    // Image
    private String filename;
    // Currently Displayed Image
    private BufferedImage image = null;
    // Original Image (used for resizing / rotating)
    private BufferedImage originalImage = null;
    
    Sprite(double x, double y)
    {
        this.x = x;
        this.y = y;
        // this.initialize();
    }
    
    Sprite(double x, double y, String filename)
    {
        this.x = x;
        this.y = y;
        this.filename = filename;
        this.initialize();
    }
    
    private void initialize()
    {
        try
        {
            BufferedImage image = ImageIO.read(new File(filename));
            this.image = image;
            this.originalImage = image;
            
            this.width = image.getWidth();
            this.height = image.getHeight();
            
            this.size = (this.width + this.height) / 2;
            
            // System.out.println(this.width + " x " + this.height);
        }
        catch(Exception e)
        {
        }
    }
    
    public void setImage(String filename)
    {
        try
        {
            BufferedImage image = ImageIO.read(new File(filename));
            this.image = image;
            this.originalImage = image;
            
            this.width = image.getWidth();
            this.height = image.getHeight();
            
            this.size = (this.width + this.height) / 2;
            
            // System.out.println(this.width + " x " + this.height);
        }
        catch(Exception e)
        {
        }
    }
    
    public void goTo(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
    
    public void goTo(double x, double y)
    {
        this.x = x;
        this.y = y;
    }
    
    public void setX(int x)
    {
        this.x = x;
    }
    
    public void setX(double x)
    {
        this.x = x;
    }
    
    public double getX()
    {
        return this.x;
    }
    
    public void setY(int y)
    {
        this.y = y;
    }
    
    public void setY(double y)
    {
        this.y = y;
    }
    
    public double getY()
    {
        return this.y;
    }
    
    public void setDX(int dx)
    {
        this.dx = dx;
    }
    
    public void setDX(double dx)
    {
        this.dx = dx;
    }
    
    public void setDY(int dy)
    {
        this.dy = dy;
    }
    
    public void setDY(double dy)
    {
        this.dy = dy;
    }
    
    public void setHeading(int heading)
    {
        this.heading = heading;
        rotate(heading);
    }
    
    public void setHeading(double heading)
    {
        this.heading = heading;
        rotate(heading);
    }
    
    public void setDH(int dh)
    {
        this.dh = dh;
    }
    
    public void setDH(double dh)
    {
        this.dh = dh;
    }
    
    public void setHasPhysics(boolean hasPhysics)
    {
        this.hasPhysics = hasPhysics;
    }
    
    public boolean getHasPhysics()
    {
        return this.hasPhysics;
    }
    
    public double getDH()
    {
        return this.dh;
    }
    
    public double getDY()
    {
        return this.dy;
    }
    
    public int getWidth()
    {
        return this.width;
    }
    
    public int getHeight()
    {
        return this.height;
    }
    
    public void setHeight(int height)
    {
        this.height = height;
    }
    
    public void setWidth(int width)
    {
        this.width = width;
    }
    
    public void setBounce(boolean bounce)
    {
        this.bounce = bounce;
        this.warp = false;
        this.stop = false;
    }
    
    public void setWarp(boolean warp)
    {
        this.warp = warp;
        this.bounce = false;
        this.stop = false;
    }
    
    public void setStop(boolean stop)
    {
        this.stop = stop;
        this.bounce = false;
        this.warp = false;
    }
    
    public void setColor(Color color)
    {
        this.color = color;
    }
    
    public void setBoundingBox(boolean boundingBox)
    {
        this.boundingBox = boundingBox;
    }
    
    public void render(Graphics g, Camera camera)
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
            
            // If no camera, create one
            // This allows background sprites to not move with the camera
            if(camera == null)
            {
                camera = new Camera();
            }
            else
            {
                renderX = (int)x - camera.getX() + 1024 / 2;
                renderY = (int)y - camera.getY() + 768 / 2;
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
    
    public void update(int canvasWidth, int canvasHeight, double dt)
    {
      if(!active)
      {
         return;
      }
      
      if(hasPhysics)
      {
          x += dx * dt;
          y += dy * dt;
          heading += dh * dt;
          
          // System.out.println(heading);
          
          if(dh!=0)
          {
              rotate(heading);
          }
          
          // bounce
          if(bounce)
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
          
          else if(warp)
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
          
          else if(stop)
          {
              if (x > canvasWidth - this.width || x < 0) 
              {
                 dx = 0;
              }
              
              if (y > canvasHeight - this.height || y < 0) 
              {
                 dy = 0;
              }
              
          }
        }
    }
    
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
    
    public void setActive(boolean active)
    {
        this.active = active;
    }
    
    public void resize(int width, int height)
    {
        Image tempImage = this.originalImage.getScaledInstance(width, height, BufferedImage.TYPE_INT_ARGB);
        
        BufferedImage buffered = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        buffered.getGraphics().drawImage(tempImage, 0, 0 , null);
        
        this.image = buffered;
        this.width = width;
        this.height = height;
    }
    
    public void scale(double ratio)
    {
        this.width = (int)(this.width * ratio);
        this.height = (int)(this.height * ratio);
        
        if(this.image != null)
        {
            resize(this.width, this.height);
        }
    }
    
    public void rotate(double angle) {
        // REF: https://stackoverflow.com/questions/37758061/rotate-a-buffered-image-in-java
        // This ensures positive is counterclockwise rotation
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
        this.heading = (int)angle;

        this.image = rotated;
    }

    public void show()
    {
        this.isVisible = true;
    }
    
    public void hide()
    {
        this.isVisible = false;
    }
}
