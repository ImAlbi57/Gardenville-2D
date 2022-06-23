package it.unibs.pajc.salm2d;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class Sound {

    private Clip clip;
    private URL soundURL[] = new URL[30];
    public static final int MAINTHEME = 0;
    public static final int PGSOUND = 1;
    public static final int MOBSOUND = 2;
    public static final int EXTERNALSOUND = 3;

    public Sound(){
        soundURL[0] = this.getClass().getResource("/res/sound/HomeSong.wav");
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
        clip.start();
    }
    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop(){
        clip.stop();
    }

}
