package character.unit.monster;

import character.base.BaseGameCharacter;
import character.type.MotionType;
import character.imageloader.ImageLoader;

import javax.swing.*;

public class Mushroom extends BaseGameCharacter implements Runnable {
    public Mushroom(JPanel panel, ImageLoader imageLoader, int hp) {
        super(panel, imageLoader, hp, 700, 365,250, 400, 140, 100);
    }

    @Override
    public void run() {
        while (characterLifeCycle()) {
        } // 캐릭터 모션 실행
        System.out.println("[Mushroom 스레드 종료]");
    }
}
