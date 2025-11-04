package runnable.idle;

import javax.swing.*;

public class Motion {
    protected JPanel panel;
    protected int frame = 0;

    // 1. 부모의 패널 받아오기 -> 2. 이미지 설정하기
    public Motion(JPanel panel) {
        this.panel = panel;
    }
}
