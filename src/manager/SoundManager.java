package manager;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import java.io.File;

public class SoundManager {

    private static final SoundManager audio = new SoundManager();

    // 생성자 생성 금지
    private SoundManager() { }


    public static SoundManager getAudio() {
        return audio;
    }

    // 3. 실제 기능
    public void play(String fileName) {
        try {
            File soundFile = new File(fileName);
            final Clip clip = AudioSystem.getClip();

            clip.addLineListener(new LineListener() {
                @Override
                public void update(LineEvent event) {
                    if(event.getType() == LineEvent.Type.STOP)
                        clip.close();
                }
            });
            clip.open(AudioSystem.getAudioInputStream(soundFile));
            clip.start();
            System.out.println("[사운드 재생]");
        } catch (Exception e) {
            System.out.println("[사운드 재생 실패]");
        }
    }
}