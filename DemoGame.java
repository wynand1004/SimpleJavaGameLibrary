import java.awt.Color;

class DemoGame
{
   // Main Method
   public static void main(String[] args)
   {
      Game game = new Game("Side Scrolling Shooter Demo");
      game.setBackgroundColor(Color.BLACK);
      game.setFPS(30);
            
      // Create Sprites
      Sprite player = new Sprite(0, 400, "player.png");
      game.addSprite(player);
      
      Sprite missile = new Sprite(12000.0, 0.0, "missile.png");
      missile.setDX(200);
      game.addSprite(missile);
      
      for(int i=0;i<25;i++)
      {
        double x = (Math.random() * 1000) + 1024;
        double y = (Math.random() * 768);
        Sprite enemy = new Sprite(x, y, "enemy.png");
        enemy.setDX(Math.random() * -200);
        game.addSprite(enemy);
      }
      
      // Create sounds
      Sound explosion = new Sound("explosion.wav");
      
      // Main Game Loop
      while(true)
      {
          // Deal with keypresses
          if(game.getKey().equals("Up"))
          {
                player.setDY(-100);
                player.setDX(0);
          }
          
          if(game.getKey().equals("Down"))
          {
                player.setDY(100);
                player.setDX(0);
          }
          
          if(game.getKey().equals("Space"))
          {
                missile.setY(player.getY()+20);
                missile.setX(player.getX()+100);
          }
          
          // Mouse clicked
          if(game.getIsMousePressed())
          {
              System.out.println("MOUSE DOWN at " + game.getMouseX() + " " + game.getMouseY());
          }
          else
          {
              System.out.println("MOUSE RELEASED at " + game.getMouseX() + " " + game.getMouseY());
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
              
              if(missile.isCircleCollision(sprite))
              {
                  sprite.setX(1200);
                  missile.setX(12000);
                  explosion.play();
              }
              
              // Move missiles back if off screen
              if(sprite.getX() < -100)
              {
                 double x = (Math.random() * 1000) + 1024;
                 double y = (Math.random() * 400);
                 sprite.setX(x);
                 sprite.setY(y);
              }
          }
      }
   }
}
