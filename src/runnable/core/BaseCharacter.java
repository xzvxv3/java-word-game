package runnable.core;

import javax.swing.*;

public abstract class BaseCharacter {
    protected JPanel panel;
    protected ImageIcon[] motionFrames; // 캐릭터의 모션 프레임
    protected String currentMotion; // 현재 모션

    protected int idx = 0;
    protected int hp;

    public BaseCharacter(JPanel panel, int hp) {
        this.panel = panel;
        this.hp = hp;
    }

    // 모션 설정
    public void setMotion(String currentMotion) {
        this.currentMotion = currentMotion;
        idx = 0; // 모션 프레임 첫장면 idx
        loadMotionImages(currentMotion); // 입력 받은 모션 설정
    }

    protected abstract void loadMotionImages(String motion);

    // 모션 프레임 반환
    public ImageIcon getCurrentFrame() {
        return motionFrames[idx];
    }
}