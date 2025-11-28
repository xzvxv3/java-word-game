package character.worker;

import character.MotionType;
import character.imageloader.ImageLoader;

import javax.swing.*;

public class ScarecrowTask extends BaseCharacter implements Runnable{
    public ScarecrowTask(JPanel panel, ImageLoader imageLoader, int hp) {
        super(panel,  imageLoader, hp, 350, 400, 140, 50);
        initCharacter(); // 시작 시, 캐릭터의 모션 설정 (기본 IDLE)
    }


    @Override
    public void run() {
        while (characterLifeCycle()) {

        } // 캐릭터 모션 실행
    }

    // Idle 모션으로 전환
    @Override
    protected void onMotionEnd() {
        if(motionType != MotionType.IDLE) {
            motionType = MotionType.IDLE;
            motionFrames = imageLoader.getMotionMap().get(motionType);
        }
    }

    // HP 설정
    public void setHP(int hp) {
        this.hp = hp;
    }

    // 캐릭터 모션 초기화
    private void initCharacter() {
        motionType = MotionType.IDLE;
        motionFrames = imageLoader.getMotionMap().get(motionType);
    }
}
