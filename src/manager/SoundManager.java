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

    // 기본값: 소리 켜짐
    private boolean isSoundOn = true;

    public static SoundManager getAudio() {
        return audio;
    }

    public void setSoundOn(boolean flag) {
        isSoundOn = flag;
        System.out.println("[사운드 상태] " + (flag ? "ON" : "OFF"));
    }

    // 소리 발생
    public void play(String fileName) {
        if(!isSoundOn) return; // 소리가 꺼져있는 상태면, 발생X

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
        } catch (Exception e) {
            System.out.println("[사운드 재생 실패]");
        }
    }
}