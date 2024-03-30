import java.awt.Color;

class DemoGame
{
   // Main Method
   public static void main(String[] args)
   {
      Game game = new Game("Side Scrolling Shooter Demo");
      game.setBackgroundColor(Color.BLACK);
      game.setFPS(60);
            
      // Create Sprites
      Sprite player = new Sprite(0, 400, "player.png");
      game.addSprite(player);
      
      Sprite missile = new Sprite(12000.0, 0.0, "missile.png");
      missile.setDX(200);
      game.addSprite(missile);
      
      Sprite enemy = new Sprite(1000.0, 400.0, "enemy.png");
      enemy.setDX(-100);
      game.addSprite(enemy);
      
      // Create sounds
      Sound beep = new Sound("beep.wav");
      
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
                  beep.play();
              }
          }
      }
   }
}
