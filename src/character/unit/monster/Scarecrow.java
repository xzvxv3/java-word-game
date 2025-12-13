package character.unit.monster;

import character.base.BaseGameCharacter;
import character.type.MotionType;
import character.imageloader.ImageLoader;

import javax.swing.*;

public class Scarecrow extends BaseGameCharacter implements Runnable{
    public Scarecrow(JPanel panel, ImageLoader imageLoader, int hp) {
        super(panel,  imageLoader, hp, 315, 490, 350, 400, 140, 50);
    }
    @Override
    public void run() {
        while (characterLifeCycle()) {}
        System.out.println("Scarecrow 스레드 종료");// 캐릭터 모션 실행
    }
    // HP 설정
    public void setHP(int hp) {
        this.hp = hp;
    }
}
