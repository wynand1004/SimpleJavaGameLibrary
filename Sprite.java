import java.awt.*;    // Using AWT's Graphics and Color
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.File;

class Sprite
{
    // Simple Sprite Class
    // Default location and acceleration
    private double x = 0.0;
    private double y = 0.0;
    private double dx = 0.0;
    private double dy = 0.0;
    
    // Default sizes
    private double width = 24.0;
    private double height = 24.0;
    private double size = 24.0;
    
    // Initial state
    private boolean bounce = false;
    private boolean warp = false;
    private boolean stop = false;
    
    private Color color = Color.BLUE;
    private boolean active = true;
    
    // Image
    private BufferedImage image = null;
    
    Sprite(double x, double y)
    {
        this.x = x;
        this.y = y;
    }
    
    Sprite(double x, double y, String filename)
    {
        this.x = x;
        this.y = y;
        
        try
        {
            BufferedImage image = ImageIO.read(new File(filename));
            this.image = image;
            
            this.width = image.getWidth();
            this.height = image.getHeight();
        }
        catch(Exception e)
        {
        }
    }
    
    public void setX(double x)
    {
        this.x = x;
    }
    
    public double getX()
    {
        return this.x;
    }
    
    public void setY(double y)
    {
        this.y = y;
    }
    
    public double getY()
    {
        return this.y;
    }
    
    public void setDX(double dx)
    {
        this.dx = dx;
    }
    
    public void setDY(double dy)
    {
        this.dy = dy;
    }
    
    public double getDY()
    {
        return this.dy;
    }
    
    public void setBounce(boolean bounce)
    {
        this.bounce = bounce;
    }
    
    public void setWarp(boolean warp)
    {
        this.warp = warp;
    }
    
    public void setStop(boolean stop)
    {
        this.stop = stop;
    }
    
    public void setColor(Color color)
    {
        this.color = color;
    }
    
    
    public void render(Graphics g)
    {
        if(!active)
        {
            return;
        }
        
        if(!(image==null))
        {
            // Render image
            g.drawImage(image, (int)x, (int)y, null);
        }
        else
        {
            // Render circle (default)
            g.setColor(color);
            g.fillOval((int)x, (int)y, (int)width, (int)height);
        }
    }
    
    public void update(double CANVAS_WIDTH, double CANVAS_HEIGHT, double dt)
    {
      if(!active)
      {
         return;
      }
      
      x += dx * dt;
      y += dy * dt;
      
      // bounce
      if(bounce)
      {
          if (x > CANVAS_WIDTH - size || x < 0) 
          {
             dx = -dx;
          }
          
          if (y > CANVAS_HEIGHT - size || y < 0) 
          {
             dy = -dy;
          }
      }
      
      else if(warp)
      {
          if (this.x > CANVAS_WIDTH - size) 
          {
             this.x = 0.0;
          }
          
          else if (this.y > CANVAS_HEIGHT - size) 
          {
             this.y = 0.0;
          }
          
          else if (this.x < 0) 
          {
             this.x = CANVAS_WIDTH - size;
          }
          
          else if (this.y < 0) 
          {
             this.y = CANVAS_HEIGHT - size;
          }          
      }
      
      else if(stop)
      {
          if (x > CANVAS_WIDTH - size || x < 0) 
          {
             dx = 0;
          }
          
          if (y > CANVAS_HEIGHT - size || y < 0) 
          {
             dy = 0;
          }
          
      }
    }
    
    public boolean isCollision(Sprite other)
    {
        boolean xOverlap = this.x <= other.x + other.width && this.x + this.width >= other.x;
        boolean yOverlap = this.y <= other.y + other.height && this.y + this.height >= other.y;
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
}
