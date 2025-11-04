import runnable.*;

import javax.swing.*;
import java.awt.*;

public class StartMenuPanel extends JPanel {
    JButton startbtn = new JButton("Game Start");
    JButton showRanking = new JButton("Scores");
    JButton setWord = new JButton("Word Settings");
    JButton[] btns = {startbtn, showRanking, setWord};

    private RunMan runMan;
    private RunSwordMan runSwordMan;
    private RunGunMan  runGunMan;
    private RunSwitchMotion runSwitchMotion;

    private ImageIcon sky = new ImageIcon("resources/background/menu.png");

    public StartMenuPanel() {
        setLayout(null);
        setBackground(Color.BLACK);

        // 로그인 판넬 위에 추가
        add(new LoginPanel());

        for(int i=0; i < btns.length; i++) {
            btns[i].setSize(330, 50);
            btns[i].setLocation(300, 350 + 70 * i);
            add(btns[i]);
        }

        // RunMan 모션
        runMan = new RunMan(this);
        // SwordMan 모션
        runSwordMan = new RunSwordMan(this);
        // GunMan 모션
        runGunMan = new RunGunMan(this);

        // 각각의 모션 스레드 실행
        new Thread(runMan).start();
        new Thread(runSwordMan).start();
        new Thread(runGunMan).start();

        runSwitchMotion = new RunSwitchMotion(runMan, runSwordMan, runGunMan, this);
        Thread switchThread = new Thread(runSwitchMotion);
        switchThread.start();
    }

    @Override
    protected void paintComponent(Graphics g) { // 배경 요소
        super.paintComponent(g);
        // 전체 배경 화면
        g.drawImage(sky.getImage(), 0, 0, getWidth(), getHeight(), this);

        // RunMotion, 3가지의 모
        RunMotion active = runSwitchMotion.getCurrentRunMotion();
        ImageIcon frame = active.getCurrentFrame();
        g.drawImage(frame.getImage(), 400, 550, 150, 150, this);
    }
}
