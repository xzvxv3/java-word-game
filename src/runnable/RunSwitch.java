package runnable;

import javax.swing.*;

public class RunSwitch implements Runnable {

    private RunMan runMan;
    private RunSwordMan runSwordMan;
    private RunGunMan runGunMan;
    private JPanel panel;

    public RunSwitch(RunMan runMan, RunSwordMan runSwordMan, RunGunMan runGunMan, JPanel panel) {
        this.runMan = runMan;
        this.runSwordMan = runSwordMan;
        this.runGunMan = runGunMan;
        this.panel = panel;
    }

    @Override
    public void run() {

    }
}
