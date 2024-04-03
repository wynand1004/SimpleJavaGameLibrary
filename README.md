# SJGL
Simple Java Game Library

This is a simple Java Game Library suitable for beginning Java students. It has no dependencies and all necessary files are in the same folder. As it relies on the standard Java library, it is lightweight and portable. You can use a simple text editor such as Geany without having to worry about folder structure.

## Getting Started

Download the Directory to your computer. Create a your game file in the same folder.

Compile the Java files and execute DemoGame.java.

## Game Class

### Create a game object

```SJGL game = new SJGL("Side Scrolling Shooter Demo");```

```game.setBackgroundColor(Color.BLACK);```

```game.setFPS(30);```

Default canvas size is 1024 x 768.

### Keyboard

```game.getKey() returns KeyEvent.VK_??```

#### Keys
 - Up VK_UP
 - Down VK_DOWN
 - Left VK_LEFT
 - Right VK_RIGHT
 - Space VK_SPACE
 - A - Z VK_A ... VK_Z
 - 0 - 9 VK_0 ... VK_9
 - Others

### Mouse

```game.getIsMousePressed()```

```game.getMouseX()```
```game.getMouseY()```

### Sprites

All sprites are stored in an ArrayList.

```game.getSprites()```

Returns an ArrayList of Sprite objects.

```game.addSprite(Sprite s)```

## Sprite Class

### Create a sprite object

Sprite spriteName = new Sprite(double x, double y);

Ex: ```Sprite player = new Sprite(100.0, 100.0);```

Sprite spriteName = new Sprite(double x, double y, String filename);

Ex: ```Sprite player = new Sprite(100.0, 100.0, "player.png");```

### Sprite Methods

.getX()
.getY()
.setX()
.setY()
.setDX()
.setDY()

.isCollision(Sprite other)
.isCircleCollision(Sprite other)

## Sound Class

### Create a sound object

Sound soundName = new Sound(String filename);

Ex: ```Sound beep = new Sound("beep.wav");```

Note: The file should be a .wav file.

### Play sound object

soundName.play();

Ex: ```beep.play();```

Note: The sound will play one time all the way through. Subsequent calls to .play will be ignored until the previous play has completed. To play background music, just play the file in the main game loop. 
