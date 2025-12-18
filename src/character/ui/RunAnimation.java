package character.ui;
import character.base.BaseAnimation;
import character.imageloader.ImageLoader;
import character.type.MotionType;
import javax.swing.*;

// 로그인 화면 전용 뛰는 객체
public class RunAnimation extends BaseAnimation implements Runnable{

    public RunAnimation(JPanel jPanel, ImageLoader imageLoader) {
        super(jPanel, imageLoader, 100, 0);
    }

    @Override
    public void run() {
        // 뛰는 모션으로 설정
        setMotion(MotionType.RUN);

        // 뛰는 모션 진행
        while (true) { updateFrame(); }
    }

    @Override
    protected void onMotionEnd() {
        // 아무것도 하지 않음 -> 계속 현재 모션(RUN) 유지
    }
}
