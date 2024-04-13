import java.io.File;
import javax.sound.sampled.*;

class Sound
{
    private String filename;
    private boolean isPlaying = false;
    private long lengthTime;
    private long startTime;
    
    Sound(String filename)
    {
        this.filename = filename;
        initialize(filename);
    }
    
    private void initialize(String filename)
    {
        try
        {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File(this.filename)));
            // Get length and convert to milliseconds
            this.lengthTime = clip.getMicrosecondLength() / 1000;
        }
        catch (Exception exc)
        {
            exc.printStackTrace(System.out);
        }
    }
    
    public void play()
    {
        // System.out.println("playSound");
        // System.out.println("lengthTime: " + this.lengthTime);
        
        if(this.isPlaying)
        {
            long currentTime = System.currentTimeMillis();
            // Check how long since clip started playing
            if((currentTime-this.startTime) > this.lengthTime)
            {
                this.isPlaying = false;
            }
            else
            {
                // If still playing exit method
                return;
            }
        }
        
        // Set start time
        this.startTime = System.currentTimeMillis();
        this.isPlaying = true;
        
        new Thread(() -> 
        {
            try
            {
                Clip clip = AudioSystem.getClip();
                clip.open(AudioSystem.getAudioInputStream(new File(this.filename)));
                clip.setFramePosition(0);
                clip.start();
            }       
            catch (Exception exc)
            {
                exc.printStackTrace(System.out);
            }
            
        }).start(); 
    }
    
    public void setFilename(String filename)
    {
        this.filename = filename;
        initialize(filename);
    }
}
