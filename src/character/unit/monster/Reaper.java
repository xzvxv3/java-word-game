package character.unit.monster;

import character.base.BaseGameCharacter;
import character.type.MotionType;
import character.imageloader.ImageLoader;
import character.imageloader.ReaperImageLoader;

import javax.swing.*;

public class Reaper extends BaseGameCharacter implements Runnable {

    private ReaperImageLoader reaperImageLoader;

    public Reaper(JPanel panel, ImageLoader imageLoader, int hp) {
        super(panel, imageLoader, hp, 250, 400, 140, 100);
    }

    @Override
    public void run() {
        while (characterLifeCycle()) {} // 캐릭터 모션 실행
        System.out.println("Repear 스레드 종료");
    }
}
