package runnable.run;

import javax.swing.*;

public class RunMotion {
    protected ImageIcon[] runImages = new ImageIcon[8];
    protected JPanel panel;
    protected int frame = 0;

    // 1. 부모의 패널 받아오기, 2. 이미지 설정하기
    public RunMotion(JPanel panel) {
        this.panel = panel;
    }

    public ImageIcon getCurrentFrame() {
        return runImages[frame];
    }
}
