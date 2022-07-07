package it.unibs.pajc.salm2d;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class SoundManager {

    private Clip clip;
    private URL soundURL[] = new URL[30];
    public static final int MAINTHEME = 0;
    public static final int WALKINGSOUND = 1;
    public static final int GAMEOVER = 2;
    public static final int EXTERNALSOUND = 3;
    public static final int UNLOCKDOOR = 4;
    public static final int KEYSOUND = 5;
    public static final int WINSOUND = 6;

    public SoundManager(){
        soundURL[0] = this.getClass().getResource("/res/sound/HomeSong.wav");
        soundURL[1] = this.getClass().getResource("/res/sound/step_grass.wav");
        soundURL[2] = this.getClass().getResource("/res/sound/gameover.wav");
        soundURL[3] = this.getClass().getResource("/res/sound/birdSoundNewDownDecibel.wav");
        soundURL[4] = this.getClass().getResource("/res/sound/doorUnlockWav.wav");
        soundURL[5] = this.getClass().getResource("/res/sound/keySoundWav.wav");
        soundURL[6] = this.getClass().getResource("/res/sound/fanfare.wav");
    }
    public void setFile(int i){
        try{
            AudioInputStream is = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(is);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void play(){
        if(!clip.isRunning())
            clip.start();
    }
    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop(){
        if(clip.isRunning())
            clip.stop();
    }

}
