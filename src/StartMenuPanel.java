import runnable.RunGunMan;
import runnable.RunMan;
import runnable.RunSwordMan;

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

        // RunMan 모션 스레드
        runMan = new RunMan(this);
        Thread runManThread = new Thread(runMan);
        runManThread.start();

        // SwordMan 모션 스레드
        runSwordMan = new RunSwordMan(this);
        Thread runSwordManThread = new Thread(runSwordMan);
        runSwordManThread.start();

        // GunMan 모션 스레드
        runGunMan = new RunGunMan(this);
        Thread runGunManThread = new Thread(runGunMan);
        runGunManThread.start();
    }

    @Override
    protected void paintComponent(Graphics g) { // 배경 화면 추가
        super.paintComponent(g);
        Image skyImg = sky.getImage();
        g.drawImage(skyImg, 0, 0, this.getWidth(), this.getHeight(),this);

        // RunMan 모션 실행
        ImageIcon currentFrame1 = runMan.getCurrentFrame();
        g.drawImage(currentFrame1.getImage(), 400, 550, 150, 150, this);

        // RunSwordMan 모션 실행
        ImageIcon currentFrame2 = runSwordMan.getCurrentFrame();
        g.drawImage(currentFrame2.getImage(), 300, 550, 150, 150, this);

        // RunGunMan 모션 실행
        ImageIcon currentFrame3 = runGunMan.getCurrentFrame();
        g.drawImage(currentFrame3.getImage(), 200, 550, 150, 150, this);

    }
}
