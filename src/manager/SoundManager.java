package manager;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import java.io.File;

// 사운드 관리자, 싱글톤 패턴 사용
public class SoundManager {
    // 자기 자신의 인스턴스를 static으로 하나만 가짐
    private static final SoundManager audio = new SoundManager();

    // 생성자 생성 금지
    private SoundManager() {}

    // 기본값: 소리 켜짐
    private boolean isSoundOn = true;

    // 인스턴스 반환
    public static SoundManager getAudio() {
        return audio;
    }

    // 소리 켜기 / 끄기
    public void setSoundOn(boolean flag) {
        // 소리 상태
        isSoundOn = flag;
        System.out.println("[사운드 상태] " + (flag ? "ON" : "OFF"));
    }

    // 효과음 재생
    public void play(String fileName) {
        // 소리가 꺼져있는 상태면, 발생X
        if(!isSoundOn) return;

        try {
            // 파일 불러오기
            File soundFile = new File(fileName);
            // 소리를 재생할 도구
            final Clip clip = AudioSystem.getClip();

            // 리스너 등록 -> 소리가 다 끝나면 자동으로 닫아줌
            clip.addLineListener(new LineListener() {
                @Override
                public void update(LineEvent event) {
                    // 소리가 멈췄다면
                    if(event.getType() == LineEvent.Type.STOP)
                        // 플레이어 닫기
                        clip.close();
                }
            });
            // 오디로 파일 열어서 플레이어(Clip)에 장착
            clip.open(AudioSystem.getAudioInputStream(soundFile));
            // 재생 시작
            clip.start();
        } catch (Exception e) {
            System.out.println("[사운드 재생 실패]");
        }
    }
}