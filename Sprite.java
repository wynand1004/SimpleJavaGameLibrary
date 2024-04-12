import java.awt.*;    // Using AWT's Graphics and Color
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.File;

class Sprite
{
    // Simple Sprite Class
    // Default location and acceleration
    private int x = 0;
    private int y = 0;
    private int dx = 0;
    private int dy = 0;
    
    // Default sizes
    private int width = 24;
    private int height = 24;
    private int size = 24;
    
    // Initial state
    private boolean bounce = false;
    private boolean warp = false;
    private boolean stop = false;
    private boolean boundingBox = false;
    
    private Color color = Color.BLUE;
    private boolean active = true;
    
    // Image
    private String filename;
    private BufferedImage image = null;
    
    Sprite(double x, double y)
    {
        this.x = (int)x;
        this.y = (int)y;
        this.initialize();
    }
    
    Sprite(double x, double y, String filename)
    {
        this.x = (int)x;
        this.y = (int)y;
        this.filename = filename;
        this.initialize();
    }
    
    private void initialize()
    {
        try
        {
            BufferedImage image = ImageIO.read(new File(filename));
            this.image = image;
            
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
    
    public void setX(int x)
    {
        this.x = x;
    }
    
    public void setX(double x)
    {
        this.x = (int)x;
    }
    
    public int getX()
    {
        return this.x;
    }
    
    public void setY(int y)
    {
        this.y = y;
    }
    
    public void setY(double y)
    {
        this.y = (int)y;
    }
    
    public int getY()
    {
        return this.y;
    }
    
    public void setDX(int dx)
    {
        this.dx = dx;
    }
    
    public void setDX(double dx)
    {
        this.dx = (int)dx;
    }
    
    public void setDY(int dy)
    {
        this.dy = dy;
    }
    
    public void setDY(double dy)
    {
        this.dy = (int)dy;
    }
    
    public int getDY()
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
    
    public void setBoundingBox(boolean boundingBox)
    {
        this.boundingBox = boundingBox;
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
            g.drawImage(image, x, y, null);
            
            // Render bounding box
            if(this.boundingBox)
            {
                g.setColor(Color.RED);
                g.drawLine(this.x, this.y, (this.x + this.width), this.y);
                g.drawLine(this.x, this.y, (this.x), (this.y + this.height));
                g.drawLine((this.x + this.width), this.y, (this.x + this.width), (this.y + this.height));
                g.drawLine(this.x, (this.y + this.height), (this.x + this.width), (this.y + this.height));
            }
        }
        else
        {
            // Render circle (default)
            g.setColor(color);
            g.fillOval((int)x, (int)y, (int)width, (int)height);
        }
    }
    
    public void update(int canvasWidth, int canvasHeight, double dt)
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
          if (x > canvasWidth - this.width || x < 0) 
          {
             dx = -dx;
          }
          
          if (y > canvasHeight - this.height || y < 0) 
          {
             dy = -dy;
          }
      }
      
      else if(warp)
      {
          if (this.x > canvasWidth - this.width) 
          {
             this.x = 0;
          }
          
          else if (this.y > canvasHeight - this.height) 
          {
             this.y = 0;
          }
          
          else if (this.x < 0) 
          {
             this.x = canvasWidth - this.width;
          }
          
          else if (this.y < 0) 
          {
             this.y = canvasHeight - this.height;
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
    
    public boolean isCollision(Sprite other)
    {
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
}
