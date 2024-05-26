import java.awt.*;
import java.awt.geom.AffineTransform;

class Label
{
    private String text;
    private int x;
    private int y;
    private int size = 24;
    private Color color = Color.WHITE;
    private boolean isVisible = true;
    private String font = "Courier";
    
    private double rotation = 0.0;
    
    Label(String text, int x, int y)
    {
        this.text = text;
        this.x = x;
        this.y = y;
    }
    
    public void render(Graphics g)
    {
        Font font = new Font(this.font, Font.PLAIN, size);
        g.setFont(font);
        g.setColor(this.color);
        
        if(this.isVisible)
        {
            if(rotation != 0)
            {   
                AffineTransform affineTransform = new AffineTransform();
                affineTransform.rotate(Math.toRadians(this.rotation), 0, 0);
                Font rotatedFont = font.deriveFont(affineTransform);
                g.setFont(rotatedFont);
                g.drawString(text, x, y);
            }
            else
            {
                g.drawString(text, x, y);
            }
        }
    }
    
    public void setText(String text)
    {
        this.text = text;
    }
    
    public void setColor(Color color)
    {
        this.color = color;
    }
    
    public void setX(int x)
    {
        this.x = x;
    }
    
    public void setY(int y)
    {
        this.y = y;
    }
    
    public void setSize(int size)
    {
        this.size = size;
    }
    
    public void show()
    {
        this.isVisible = true;
    }
    
    public void hide()
    {
        this.isVisible = false;
    }
    
    public void setRotation(int rotation)
    {
        this.rotation = -rotation;
    }
    
    public void setRotation(double rotation)
    {
        this.rotation = -(int)rotation;
    }
    
    public void setFont(String font)
    {
        this.font = font;
    }
    
    public String getFont()
    {
        return this.font;
    }
}
