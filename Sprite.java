import java.awt.*;    // Using AWT's Graphics and Color

class Sprite
{
    // Simple Sprite Class
    private double x = 0.0;
    private double y = 0.0;
    private double dx = 1.0;
    private double dy = 1.0;
    private double size = 24.0;
    private boolean bounce = true;
    private boolean warp = false;
    
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
    
    
    
    public void render(Graphics g)
    {
        g.setColor(Color.BLUE);
        g.fillOval((int)x, (int)y, (int)size, (int)size);
    }
    
    public void update(double CANVAS_WIDTH, double CANVAS_HEIGHT)
    {
      x += dx;
      y += dy;
      
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
}
