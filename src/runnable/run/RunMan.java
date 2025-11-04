package runnable.run;

import javax.swing.*;

public class RunMan extends RunMotion implements Runnable{

    public RunMan(JPanel panel) {
        super(panel);
        setRunImages();
    }

    @Override
    public void run() {
        while (true) {
            frame = (frame + 1) % runImages.length; // 프레임 변경
            panel.repaint(); // 부모 패널 다시 그리기, 부모는 바뀐 프레임을 호출할 예정
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    private void setRunImages() {
        for (int i = 0; i < runImages.length; i++) {
            runImages[i] = new ImageIcon("resources/sprites/original/run/Run0" + (i + 1) + ".png");
        }
    }
}
