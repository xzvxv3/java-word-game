package character.worker;

import character.MotionType;
import character.imageloader.ImageLoader;

import javax.swing.*;

public class WolfTask extends BaseCharacter implements Runnable {

    public WolfTask(JPanel panel, ImageLoader imageLoader, int hp) {
        super(panel, imageLoader,  hp, 350, 600, 140, 70);
        initCharacter(); // 시작 시, 캐릭터의 모션 설정 (기본 IDLE)
    }


    @Override
    public void run() {
        while (characterLifeCycle()) {} // 캐릭터 모션 실행
    }

    @Override
    protected void onMotionEnd() {
        if(motionType != MotionType.IDLE) {
            motionType = MotionType.IDLE;
            motionFrames = imageLoader.getMotionMap().get(motionType);
        }
    }

    private void initCharacter() {
        motionType = MotionType.IDLE;
        motionFrames = imageLoader.getMotionMap().get(motionType);
    }
}
