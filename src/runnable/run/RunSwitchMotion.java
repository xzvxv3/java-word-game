package runnable.run;

import javax.swing.*;

public class RunSwitchMotion implements Runnable {

    private RunMotion[] motions = new RunMotion[3];
    private RunMotion currentRunMotion;
    private int idx = 0;
    private JPanel panel;

    public RunSwitchMotion(RunMotion runMan, RunMotion runSwordMan, RunMotion runGunMan, JPanel panel) {
        motions[0] = runMan;
        motions[1] = runSwordMan;
        motions[2] = runGunMan;
        this.panel = panel;
    }

    public RunMotion getCurrentRunMotion() {
        return currentRunMotion;
    }

    @Override
    public void run() {
        while(true) {
            currentRunMotion = motions[idx];
            panel.repaint();

            // 다음 장면으로 전환
            idx = (idx + 1) % motions.length;

            try {
                Thread.sleep(3000); // 3초마다 교체
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
