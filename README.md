# SJGL
Simple Java Game Library

This is a simple Java Game Library suitable for beginning Java students. It has no dependencies and all necessary files are in the same folder. As it relies on the standard Java library, it is lightweight and portable. You can use a simple text editor such as Geany without having to worry about folder structure.

## Getting Started

Download the Directory to your computer. Create a your game file in the same folder.

Compile the Java files and execute MyGame.java.

## Game Class

## Sprite Class

### Create a sprite object

Sprite spriteName = new Sprite(double x, double y);

Ex: ```Sprite player = new Sprite(100.0, 100.0);```

Sprite spriteName = new Sprite(double x, double y, String filename);

Ex: ```Sprite player = new Sprite(100.0, 100.0, "player.png");```

## Sound Class

### Create a sound object

Sound soundName = new Sound(String filename);

Ex: ```Sound beep = new Sound("beep.wav");```

Note: The file should be a .wav file.

### Play sound object

soundName.play();

Ex: ```beep.play();```

Note: The sound will play one time all the way through. Subsequent calls to .play will be ignored until the previous play has completed. To play background music, just play the file in the main game loop. 
