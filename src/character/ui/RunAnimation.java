package character.ui;

import character.base.BaseAnimation;
import character.imageloader.ImageLoader;
import character.type.MotionType;

import javax.swing.*;

// 마저 끝낼것
public class RunAnimation extends BaseAnimation implements Runnable{
    public RunAnimation(JPanel jPanel, ImageLoader imageLoader) {
        super(jPanel, imageLoader, 100);
    }

    @Override
    public void run() {
        setMotion(MotionType.RUN);

        while (true) {
            updateFrame();
        }
    }

    @Override
    protected void onMotionEnd() {
        // 아무것도 하지 않음 -> 계속 현재 모션(RUN) 유지
    }
}
