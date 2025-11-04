package runnable.core;

import javax.swing.*;

public class Wolf extends Motion implements Runnable {
    protected ImageIcon[] idleImages = new ImageIcon[14];

    public Wolf(JPanel panel) {
        super(panel);
        setImages();
    }

    @Override
    public void run() {
        while (true) {
            frame = (frame + 1) % idleImages.length; // 프레임 변경
            panel.repaint(); // 부모 패널 다시 그리기, 부모는 바뀐 프레임을 호출할 예정
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    // 추후 수정 필요. 각 상황에 맞게
    public ImageIcon getCurrentFrame() {
        return idleImages[frame];
    }

    // 추후 수정 필요. 각 상황에 맞게
    private void setImages() {
        for (int i = 0; i < idleImages.length; i++) {

            if(i >= 9) {
                idleImages[i] = new ImageIcon("resources/sprites/wolf/attack/LoboSombriuAttack001" + (i - 9) + ".png");
            } else {
                idleImages[i] = new ImageIcon("resources/sprites/wolf/attack/LoboSombriuAttack000" + (i + 1) + ".png");
            }
        }
    }
}
