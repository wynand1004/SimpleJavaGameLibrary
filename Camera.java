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
    private Sprite target = new Sprite(0, 0);
    
    Camera()
    {
    }
    
    Camera(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
    
    Camera(int x, int y, Sprite target)
    {
        this.x = x;
        this.y = y;
        this.target = target;
    }
    
    public void setTarget(Sprite target)
    {
        this.target = target;
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
    
    public void update()
    {
        // If there is a target, set x and y to target x and y
        if(target!=null)
        {
            this.x = (int)target.getX();
            this.y = (int)target.getY();
        }
        
        // Offset is used for shaking and other effects
        this.xOffset += this.dx;
        this.yOffset += this.dy;
        
        this.x += xOffset;
        this.y += yOffset;
        
    }    
}
