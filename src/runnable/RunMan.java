package runnable;

import javax.swing.*;

public class RunMan implements Runnable{
    private ImageIcon[] runImages = new ImageIcon[8];
    private JPanel panel;
    private int frame = 0;

    // 1. 부모의 패널 받아오기, 2. 이미지 설정하기
    public RunMan(JPanel panel) {
        this.panel = panel;
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

    public ImageIcon getCurrentFrame() {return runImages[frame];}

    private void setRunImages() {
        for (int i = 0; i < runImages.length; i++) {
            runImages[i] = new ImageIcon("resources/sprites/original/run/Run0" + (i + 1) + ".png");
        }
    }
}
