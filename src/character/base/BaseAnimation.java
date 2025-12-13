package character.base;

import character.imageloader.ImageLoader;
import character.type.MotionType;
import javax.swing.*;

public abstract class BaseAnimation {
    protected JPanel panel;
    protected ImageLoader imageLoader;
    protected ImageIcon[] motionFrames;
    protected MotionType motionType;
    protected int idx = 0;

    protected boolean isDead = false;

    protected final int FRAME_DELAY; // 프레임 속도
    protected final int MOTION_END_DELAY;

    protected boolean isIntro = true; // 현재 인트로(등장) 중인가?

    public BaseAnimation(JPanel panel, ImageLoader loader, int frameDelay, int motionEndDelay) {
        this.panel = panel;
        this.imageLoader = loader;
        this.FRAME_DELAY = frameDelay;
        this.MOTION_END_DELAY = motionEndDelay;
        initAnimation();
    }

    public BaseAnimation(JPanel panel, ImageLoader loader, int frameDelay) {
        this(panel, loader, frameDelay, 0);
    }

    private void initAnimation() {
        this.motionType = MotionType.IDLE;
        this.motionFrames = imageLoader.getMotionMap().get(MotionType.IDLE);
    }

    protected void updateFrame() {
        if (motionFrames == null) return;
        idx = (idx + 1) % motionFrames.length;
        panel.repaint();

        try {
            // 마지막 프레임일 때 처리
            if (idx == motionFrames.length - 1) {

                // 모션 종료에 도달했을떄 -> Idle로 변경
                onMotionEnd();

                // 모션 종료 딜레이 (뛰고 있는 상태, 죽음 상태는 제외)
                if(motionType != MotionType.RUN && motionType != MotionType.DEAD) {
                    Thread.sleep(MOTION_END_DELAY);
                }
            }

            // 프레임 딜레이
            Thread.sleep(FRAME_DELAY);

        } catch (InterruptedException e) { return; }
    }


    protected void onMotionEnd() {

        // 이동중인 상태 or 죽음 상태 -> 더이상의 모션을 주지 않음.
        if (isIntro || isDead) { return; }

        // 특정 모션 종료 후, IDLE로 다시 전환
        if(motionType != MotionType.IDLE) {
            motionType = MotionType.IDLE;
            motionFrames = imageLoader.getMotionMap().get(motionType);
        }
    }

    // 모션 변경
    public void setMotion(MotionType type) {
        this.motionType = type;
        this.idx = 0;
        this.motionFrames = imageLoader.getMotionMap().get(type);
    }

    // 현재 프레임 반환 (패널에서 그릴 때 사용)
    public ImageIcon getCurrentFrame() {
        if (motionFrames == null || idx >= motionFrames.length) idx = 0;
        return motionFrames[idx];
    }
}