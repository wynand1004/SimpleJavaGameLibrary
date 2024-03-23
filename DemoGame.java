import java.awt.Color;

class DemoGame
{
   // Main Method
   public static void main(String[] args)
   {
      Game game = new Game("My Game");
      game.setBackgroundColor(Color.YELLOW);
      game.setFPS(60);
            
      // Create Sprites
      Sprite player = new Sprite(0.0, 0.0, "player.png");
      player.setColor(Color.RED);
      
      game.addSprite(player);
      
      // Add a few more Sprites for fun
      for(int i=0;i<100;i++)
      {
          Sprite sprite = new Sprite(512, 384);
          double dx = Math.random() * 200 - 100;
          double dy = Math.random() * 100 - 50;
          sprite.setDX(dx);
          sprite.setDY(dy);
          int r = (int)(Math.random() * 256);
          int g = (int)(Math.random() * 256);
          int b = (int)(Math.random() * 256);
          sprite.setColor(new Color(r, g, b));
          game.addSprite(sprite);
      }
      
      // Create sounds
      Sound beep = new Sound("beep.wav");
      
      // Main Game Loop
      while(true)
      {
          // Deal with keypresses
          // System.out.println(game.getKey());
          
          if(game.getKey().equals("A"))
          {
                player.setDX(-100);
                player.setDY(0);
          }
          
          if(game.getKey().equals("D"))
          {
                player.setDX(100);
                player.setDY(0);
          }
          
          if(game.getKey().equals("W"))
          {
                player.setDY(-100);
                player.setDX(0);
          }
          
          if(game.getKey().equals("S"))
          {
                player.setDY(100);
                player.setDX(0);
          }
          
          // Check for player collision
          for(Sprite sprite: game.getSprites())
          {
              if(sprite==player)
              {
                  continue;
              }
              
              if(player.isCollision(sprite))
              {
                  sprite.setColor(Color.BLACK);
                  beep.playSound();
                  // sprite.setActive(false);
              }
          }
      }
   }
}
