import java.awt.Color;
import java.awt.event.KeyEvent;

class RPGDemo
{
    // Main Method
    public static void main(String[] args)
    {
        SJGL game = new SJGL("Simple RPG Demo by @TokyoEdtech");
        game.setBackgroundColor(Color.GREEN);
        
        // Create Background Sprites
        // Sprite spriteName = new Sprite(x, y, "image.png");
        // game.addBackgroundSprite(spriteName);
                
        // Create Sprites
        Sprite player = new Sprite(0, 320);
        player.setColor(Color.BLUE);
        player.setWidth(32);
        player.setHeight(32);
        game.addSprite(player);
        
        game.setCameraTarget(player);
        
        createMap(game, 32, 32, player);
        
        // Create sounds
        // Sound explosion = new Sound("sound.wav");
        
        // Create labels
        Label labelTitle = new Label("RPG DEMO", 500, 25);
        game.addLabel(labelTitle);
                
        // Main Game Loop
        while(true)
        {
            System.out.print("");
            // Your Game Code Goes Here
            if(game.getKey() == KeyEvent.VK_UP)
            {
                player.setDY(-150);
                player.setDX(0);
            }
             
            if(game.getKey() == KeyEvent.VK_DOWN)
            {
                player.setDY(150);
                player.setDX(0);
            }
            
           if(game.getKey() == KeyEvent.VK_LEFT)
            {
                player.setDY(0);
                player.setDX(-150);
            }
             
            if(game.getKey() == KeyEvent.VK_RIGHT)
            {
                player.setDY(0);
                player.setDX(150);
            }
            
            // Check for collisions
            for(Sprite sprite: game.getSprites())
            {
                if(player.isCollision(sprite))
                {
                    // Deal with collisions
                    player.setX(player.getX() - player.getDX() * game.getDT());
                    player.setY(player.getY() - player.getDY() * game.getDT());
                    
                    player.setDX(0);
                    player.setDY(0);
                    
                    game.getCamera().shake(0, 20);
                }
            }
             
        }
    }
    
    public static void createMap(SJGL game, int tileWidth, int tileHeight, Sprite player)
    {
        String map = "";
        
        map =  "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW\n";
        map += "W                              W\n";
        map += "W                              W\n";
        map += "W                              W\n";
        map += "W                              W\n";
        map += "W                              W\n";
        map += "W                              W              WWWWWWWWWWWWWWWWWWWWW\n";
        map += "W                              W              W                   W\n";
        map += "W        WWWWWWWWWW            W              W                   W\n";
        map += "W        W                     WWWWWWWWWWWWWWWW                   W\n";
        map += "W        W                                                        W\n";
        map += "W        W    P  T                                                W\n";
        map += "W        W                                                        W\n";
        map += "W        W                     WWWWWWWWWWWWWWWW                   W\n";
        map += "W        WWWWWWWWWW            W              W                   W\n";
        map += "W                              W              W                   W\n";
        map += "W                              W              WWWWWWWWWWWWWWWWWWWWW\n";
        map += "W                              W\n";
        map += "W                              W\n";
        map += "W                              W\n";
        map += "W                              W\n";
        map += "W                              W\n";
        map += "W                              W\n";
        map += "W                              W\n";
        map += "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW\n";
        
        // Iterate through map
        // World x and y
        int x = 0;
        int y = 0;
        String character = "";
        
        for(int i=0;i<map.length();i++)
        {
            character = map.substring(i, i+1);
            
            // Increment world x
            x += tileWidth;
            
            if(character.equals(" "))
            {
                // Do nothing
            }
            
            // At end of map row, go to 0, y
            else if(character.equals("\n"))
            {
                x = 0;
                y += tileHeight;
            }
        
            else if(character.equals("P"))
            {
                // Player is a special case
                player.setX(x);
                player.setY(y);
            }
            
            else
            {
                Sprite tile = new Sprite(x, y);                
                tile.setWidth(tileWidth);
                tile.setHeight(tileHeight);
                
                // Other game objects
                // Wall
                if(character.equals("W"))
                {
                    // tile.setColor(Color.BLACK);
                    tile.setImage("wall.png");
                    tile.resize(tileWidth, tileHeight);
                }
                
                if(character.equals("T"))
                {
                    tile.setColor(Color.YELLOW);
                }
                
                // Add to game
                game.addSprite(tile);
            }
        }
    }
}
