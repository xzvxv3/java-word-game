package runnable;

import javax.swing.*;

public class RunSwordMan extends RunMotion implements Runnable{

    // 1. 부모의 패널 받아오기, 2. 이미지 설정하기
    public RunSwordMan(JPanel panel) {
        super(panel);
        setRunImages();
    }

    @Override
    public void run() {
        while (true) {
            frame = (frame + 1) % runImages.length;
            panel.repaint();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    private void setRunImages() {
        for (int i = 0; i < runImages.length; i++) {
            runImages[i] = new ImageIcon("resources/sprites/sword/run/SwordRun0" + (i + 1 ) +".png");
        }
    }
}
