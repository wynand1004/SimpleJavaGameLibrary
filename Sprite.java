import java.awt.*;    // Using AWT's Graphics and Color

class Sprite
{
    // Simple Sprite Class
    private double x = 0.0;
    private double y = 0.0;
    private double dx = 0.0;
    private double dy = 0.0;
    private double size = 24.0;
    private boolean bounce = true;
    private boolean warp = false;
    private Color color = Color.BLUE;
    private boolean active = true;
    
    Sprite(double x, double y)
    {
        this.x = x;
        this.y = y;
    }
    
    public void setX(double x)
    {
        this.x = x;
    }
    
    public void setY(double y)
    {
        this.y = y;
    }
    
    public void setDX(double dx)
    {
        this.dx = dx;
    }
    
    public void setDY(double dy)
    {
        this.dy = dy;
    }
    
    public void setBounce(boolean bounce)
    {
        this.bounce = bounce;
    }
    
    public void setWarp(boolean warp)
    {
        this.warp = warp;
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
        
        g.setColor(color);
        g.fillOval((int)x, (int)y, (int)size, (int)size);
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
    }
    
    public boolean isCollision(Sprite other)
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
