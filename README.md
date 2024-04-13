# Simple Java Game Library
Simple Java Game Library

This is a simple 2D Java Game Library suitable for beginning Java students. It has no dependencies and all necessary files are in one folder. As it relies on the standard Java library, it is lightweight and portable. You can use a simple text editor such as Geany without having to worry about folder structure.

## Getting Started

### Testing the Game Library
Compile the Java files and execute DemoGame.java.

### Making Your Own Game
Download the Directory to your computer. There is a file called Template.java. Open this and save it as your game name (don't forget to update the class name as well.)

## Classes

### Game Class

#### Create a game object

Default title and canvas size.
``SJGL game = new SJGL();```

Custom title and default canvas size.
```SJGL game = new SJGL("Side Scrolling Shooter Demo");```

Custom title and canvas size.
``SJGL game = new SJGL("Side Scrolling Shooter Demo", 1024, 768);```

```game.setBackgroundColor(Color.BLACK);```

```game.setFPS(30);```

Default canvas size is 1024 x 768.

#### Keyboard

```game.getKey() returns KeyEvent.VK_??```

##### Keys
 - Up VK_UP
 - Down VK_DOWN
 - Left VK_LEFT
 - Right VK_RIGHT
 - Space VK_SPACE
 - A - Z VK_A ... VK_Z
 - 0 - 9 VK_0 ... VK_9
 - Others

#### Mouse

```game.getIsMousePressed()```

```game.getMouseX()```
```game.getMouseY()```

#### Sprites

All sprites are stored in an ArrayList.

```game.getSprites()```
```game.getBackgroundSprites()```

Returns an ArrayList of Sprite objects.

```game.addSprite(Sprite s)```
Adds a sprite to the ArrayList of game sprites

```game.addBackgroundSprite(Sprite s)```
Adds a sprite to the ArrayList of game sprites


### Sprite Class

There are two types of Sprite objects. Background sprites, and regular sprites. Background sprites do not interact - they are simply decoration. Regular sprites do interact (register collisions).

#### Create a sprite object

Sprite spriteName = new Sprite(double x, double y);

Ex: ```Sprite player = new Sprite(100.0, 100.0);```

Sprite spriteName = new Sprite(double x, double y, String filename);

Ex: ```Sprite player = new Sprite(100.0, 100.0, "player.png");```

#### Sprite Methods

.getX()
.getY()
.setX()
.setY()
.setDX()
.setDY()

.isCollision(Sprite other)
.isCircleCollision(Sprite other)

### Sound Class

#### Create a sound object

Sound soundName = new Sound(String filename);

Ex: ```Sound beep = new Sound("beep.wav");```

Note: The file should be a .wav file.

#### Play sound object

soundName.play();

Ex: ```beep.play();```

Note: The sound will play one time all the way through. Subsequent calls to .play will be ignored until the previous play has completed. To play background music, just play the file in the main game loop. 

### Label Class

#### Create a label object

Label labelName = new Label(String text, int x, int y);

Ex: ```Label scoreLabel = new Label("Score: 0", 480, 100);```


