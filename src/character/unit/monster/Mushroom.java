package character.unit.monster;
import character.base.BaseGameCharacter;
import character.imageloader.ImageLoader;
import javax.swing.*;

// Mushroom 캐릭터 클래스
public class Mushroom extends BaseGameCharacter implements Runnable {

    // Mushroom 생성자
    public Mushroom(JPanel panel, ImageLoader imageLoader, int hp) {
        super(panel, imageLoader, hp, 700, 365,250, 400, 140, 100);
    }

    @Override
    public void run() {
        // 캐릭터가 살아있는 동안 모션 실행
        while (characterLifeCycle()) {}
        System.out.println("[Mushroom 스레드 종료]");
    }
}
