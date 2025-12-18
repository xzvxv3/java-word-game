package character.unit.monster;
import character.base.BaseGameCharacter;
import character.imageloader.ImageLoader;
import javax.swing.*;

// Reaper 캐릭터 클래스
public class Reaper extends BaseGameCharacter implements Runnable {

    // Reaper 생성자
    public Reaper(JPanel panel, ImageLoader imageLoader, int hp) {
        super(panel, imageLoader, hp, 700, 390, 250, 400, 140, 100);
    }

    @Override
    public void run() {
        // 캐릭터가 살아있는 동안 모션 실행
        while (characterLifeCycle()) {} // 캐릭터 모션 실행
        System.out.println("[Reaper 스레드 종료]");
    }
}
