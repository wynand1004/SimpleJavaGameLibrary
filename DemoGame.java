import java.awt.Color;
import java.awt.event.KeyEvent;

class DemoGame
{
    // Main Method
    public static void main(String[] args)
    {
        SJGL game = new SJGL("Side Scrolling Shooter Demo by @TokyoEdtech", 1024, 768);
        game.setBackgroundColor(Color.BLACK);
        game.setFPS(30);
        
        // Create Background Sprites
        Sprite backgroundSprite = new Sprite(0, 0, "background_1024_768.jpg");
        game.addBackgroundSprite(backgroundSprite);
        
        // Create Sprites
        Sprite player = new Sprite(0, 400, "player.png");
        player.setStop(true);
        // player.setBoundingBox(true);
        game.addSprite(player);
        
        Sprite missile = new Sprite(12000, 400, "missile.png");
        missile.setDX(200);
        // missile.setBoundingBox(true);
        game.addSprite(missile);
        
        for(int i=0;i<25;i++)
        {
            double x = (Math.random() * 1000) + 1024;
            double y = (Math.random() * 768);
            Sprite enemy = new Sprite(x, y, "enemy.png");
            enemy.setDX((Math.random() * -200));
            // enemy.setBoundingBox(true);
            game.addSprite(enemy);
        }
        
        // Create sounds
        Sound explosion = new Sound("explosion.wav");
        Sound bgm = new Sound("bgm.wav");
        
        // Create labels
        Label scoreLabel = new Label("Score: 0", 500, 25);
        scoreLabel.setColor(Color.GREEN);
        game.addLabel(scoreLabel);
        
        int score = 0;
        
        // Main Game Loop
        while(true)
        {
            // Play background music
            bgm.play();
             
            // Deal with keypresses
            if(game.getKey() == KeyEvent.VK_UP)
            {
                player.setDY(-100);
                player.setDX(0);
            }
             
            if(game.getKey() == KeyEvent.VK_DOWN )
            {
                player.setDY(100);
                player.setDX(0);
            }
             
            if(game.getKey() == KeyEvent.VK_SPACE)
            {
                if(missile.getX() > game.getCanvasWidth())
                {
                    missile.setY(player.getY()+20);
                    missile.setX(player.getX()+100);
                }
            }
             
            // Mouse clicked
            if(game.getIsMousePressed())
            {
                // System.out.println("MOUSE DOWN at " + game.getMouseX() + " " + game.getMouseY());
            }
            else
            {
                // System.out.println("MOUSE RELEASED at " + game.getMouseX() + " " + game.getMouseY());
            }
             
            // Check for collisions
            for(Sprite sprite: game.getSprites())
            {
                if(sprite==player)
                {
                    continue;
                }
                  
                if(sprite==missile)
                {
                    continue;
                }
                  
                if(missile.isCollision(sprite))
                {
                    explosion.play();
                    game.removeSprite(sprite);
                        
                    missile.setX(12000);
                        
                    score += 1;
                    scoreLabel.setText("Score: " + score);
                }
                  
                // Move missiles back if off screen
                if(sprite.getX() < -sprite.getWidth())
                {
                    double x = (Math.random() * 1000) + 1024;
                    double y = (Math.random() * 768);
                    sprite.setX(x);
                    sprite.setY(y);
                }
            }
        }
    }
}
