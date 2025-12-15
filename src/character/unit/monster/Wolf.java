package character.unit.monster;

import character.base.BaseGameCharacter;
import character.type.MotionType;
import character.imageloader.ImageLoader;

import javax.swing.*;

public class Wolf extends BaseGameCharacter implements Runnable {
    public Wolf(JPanel panel, ImageLoader imageLoader, int hp) {
        super(panel, imageLoader,  hp, 700, 435, 350, 600, 140, 70);
    }

    @Override
    public void run() {
        while (characterLifeCycle()) {} // 캐릭터 모션 실행
        System.out.println("[Wolf 스레드 종료]");
    }
}
