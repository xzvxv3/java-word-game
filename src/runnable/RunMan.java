package runnable;

import javax.swing.*;

public class RunMan extends RunMotion implements Runnable{

    public RunMan(JPanel panel) {
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
            runImages[i] = new ImageIcon("resources/sprites/original/run/Run0" + (i + 1) + ".png");
        }
    }
}
