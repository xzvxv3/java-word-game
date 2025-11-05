package runnable;

import javax.swing.*;

public class RunAnimation implements Runnable{
    private ImageIcon[] runManImages = new ImageIcon[8];
    private ImageIcon[] runSwordManImages = new ImageIcon[8];
    private ImageIcon[] runGunManImages = new ImageIcon[8];

    private JPanel panel;
    private int frame = 0;
    private int motionNumber = 0;


    public ImageIcon getCurrentFrame() {
        switch (motionNumber) {
            case 0 : return runManImages[frame];
            case 1 : return runSwordManImages[frame];
            case 2 : return runGunManImages[frame];
            default : return null;
        }
    }

    public RunAnimation(JPanel panel) {
        this.panel = panel;
        setRunImages();
    }

    @Override
    public void run() {
        long lastMotionTime = System.currentTimeMillis();

        while (true) {
            frame = (frame + 1) % 8; // 프레임 변경
            panel.repaint(); // 패널 다시 그리기

            // 3초마다 모션 변경
            if (System.currentTimeMillis() - lastMotionTime >= 3000) {
                motionNumber = (motionNumber + 1) % 3; // 다음 모션으로
                lastMotionTime = System.currentTimeMillis(); // 기준 시점 갱신
            }

            try {
                Thread.sleep(100); // 프레임 속도 (0.1초)
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    private void setRunImages() {
        for (int i = 0; i < 8; i++) {
            runManImages[i] = new ImageIcon("resources/sprites/original/run/Run0" + (i + 1) + ".png");
            runSwordManImages[i] = new ImageIcon("resources/sprites/sword/run/SwordRun0" + (i + 1) +".png");
            runGunManImages[i] = new ImageIcon("resources/sprites/gun/run/GunRun0" + (i + 1) + ".png");
        }
    }
}
