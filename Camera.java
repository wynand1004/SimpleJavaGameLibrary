class Camera
{
    // World Coordinates of the Camera
    private int x;
    private int y;
    
    // Camera Movement
    private int xOffset = 0;
    private int yOffset = 0;
    private double dx;
    private double dy;
    
    // Target sprite to follow (ex. Player)
    private Sprite target;
    
    Camera()
    {
    }
    
    Camera(int x, int y)
    {
        target = new Sprite(x, y);
    }
    
    Camera(Sprite target)
    {
        this.target = target;
    }
    
    public void setTarget(Sprite target)
    {
        this.target = target;
    }
    
    public void setTarget(int x, int y)
    {
        this.target.setX(x);
        this.target.setY(y);
    }
    
    public int getX()
    {
        return this.x;
    }
    
    public int getY()
    {
        return this.y;
    }
    
    public Sprite getTarget()
    {
        return this.target;
    }
    
    public void shake()
    {
        shake(30, 30);
    }
    
    public void shake(int strength)
    {
        shake(strength, strength);
    }
    
    public void shake(int strengthX, int strengthY)
    {
        if(Math.abs(this.dx) < 1 && Math.abs(this.dy) < 1)
        {
            this.dx = strengthX;
            this.dy = strengthY;
        }
    }
    
    public void update()
    {
        this.x = (int)(this.target.getX() + dx);
        this.y = (int)(this.target.getY() + dy);
        
        // Reduce dx and dy gradually
        this.dx *= -0.9;
        this.dy *= -0.9;
        
        // Reset offset
        if(Math.abs(this.dx) < 1)
        {
            this.dx = 0.0;            
        }
        
        if(Math.abs(this.dy) < 1)
        {
            this.dy = 0.0;
        }
        
        if(Math.abs(this.dx) < 1 && Math.abs(this.dy) < 1)
        {
            this.x = (int)target.getX();
            this.y = (int)target.getY();
        }
        
        // System.out.println(this.dx + " " + this.dy);
        
    }    
}
