import java.awt.Color;

class MyGame
{
   // Main Method
   public static void main(String[] args)
   {
      Game game = new Game("My Game");
      game.setBackgroundColor(Color.YELLOW);
            
      // Create Sprites
      Sprite player = new Sprite(0.0, 0.0);
      player.setColor(Color.RED);
      
      game.addSprite(player);
      
      // Add a few more Sprites for fun
      for(int i=0;i<20;i++)
      {
          Sprite sprite = new Sprite(200, 200);
          double dx = Math.random() * 5;
          double dy = Math.random() * 5;
          sprite.setDX(dx);
          sprite.setDY(dy);
          game.addSprite(sprite);
      }
      
      while(true)
      {
          // Deal with keypresses
          System.out.println(game.getKey());
          
          if(game.getKey().equals("A"))
          {
                player.setDX(-3);
                player.setDY(0);
          }
          
          if(game.getKey().equals("D"))
          {
                player.setDX(3);
                player.setDY(0);
          }
          
          if(game.getKey().equals("W"))
          {
                player.setDY(-3);
                player.setDX(0);
          }
          
          if(game.getKey().equals("S"))
          {
                player.setDY(3);
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
                  // sprite.setActive(false);
              }
          }
          
      }
   }
}
