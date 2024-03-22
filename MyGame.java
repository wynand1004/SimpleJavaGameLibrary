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
      game.addSprite(player);
      
      // Add a few more Sprites for fun
      for(int i=0;i<20;i++)
      {
          Sprite sprite = new Sprite(0, 0);
          double dx = Math.random() * 10;
          double dy = Math.random() * 10;
          sprite.setDX(dx);
          sprite.setDY(dy);
          game.addSprite(sprite);
      }
          
      
   }
}
