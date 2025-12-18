package character.unit.monster;
import character.base.BaseGameCharacter;
import character.imageloader.ImageLoader;
import javax.swing.*;

// Scarecrow 객체
public class Scarecrow extends BaseGameCharacter implements Runnable{

    // Scarecrow 생성자
    public Scarecrow(JPanel panel, ImageLoader imageLoader, int hp) {
        super(panel,  imageLoader, hp, 315, 515, 350, 400, 140, 50);
    }

    @Override
    public void run() {
        // 캐릭터가 살아있는 동안 모션 실행
        while (characterLifeCycle()) {}
        System.out.println("[Scarecrow 스레드 종료]");
    }

    // HP 설정
    public void setHP(int hp) {
        this.hp = hp;
    }
}
