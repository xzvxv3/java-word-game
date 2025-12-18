package character.unit.monster;
import character.base.BaseGameCharacter;
import character.imageloader.ImageLoader;
import javax.swing.*;

// Wolf 캐릭터 클래스
public class Wolf extends BaseGameCharacter implements Runnable {

    // Wolf 생성자
    public Wolf(JPanel panel, ImageLoader imageLoader, int hp) {
        super(panel, imageLoader,  hp, 700, 435, 350, 600, 140, 70);
    }

    @Override
    public void run() {
        // 캐릭터가 살아있는 동안 모션 실행
        while (characterLifeCycle()) {}
        System.out.println("[Wolf 스레드 종료]");
    }
}
