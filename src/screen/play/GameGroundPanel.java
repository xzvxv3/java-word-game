package screen.play;

import runnable.idle.Man;
import runnable.idle.Wolf;
import runnable.run.RunGunMan;
import runnable.run.RunMan;
import runnable.run.RunMotion;
import runnable.run.RunSwordMan;

import javax.swing.*;
import java.awt.*;

public class GameGroundPanel extends JPanel {
    private ImageIcon ground = new ImageIcon("resources/background/ground.png");

    private Man Man;
    private Wolf Wolf;

    private Thread ManThread;
    private Thread WolfThread;

    public GameGroundPanel() {
        setLayout(null);


        // 단어 입력 버튼
        JTextField input = new JTextField(30);
        input.setLocation(250, 720);
        input.setSize(150, 30);
        add(input);

        // idle 모션
        idleMotion();
    }

    private void idleMotion() {
        // RunMan 모션
        Man = new Man(this);
        // SwordMan 모션
        Wolf = new Wolf(this);

        ManThread = new Thread(Man);
        WolfThread = new Thread(Wolf);

        ManThread.start();
        WolfThread.start();
    }

    @Override
    protected void paintComponent(Graphics g) { // 배경 요소
        super.paintComponent(g);
        g.drawImage(ground.getImage(), 0, 0, getWidth(), getHeight(), this);

        ImageIcon ManImage = Man.getCurrentFrame();
        g.drawImage(ManImage.getImage(), 200, 500, 200, 200, this);

        ImageIcon WolfImage = Wolf.getCurrentFrame();
        g.drawImage(WolfImage.getImage(), 300, 515, 250, 250, this);
    }
}