package screen.play;
import runnable.RunMotion;

import javax.swing.*;
import java.awt.*;

public class GameGroundPanel extends JPanel {
    private ImageIcon ground = new ImageIcon("resources/background/ground.png");

    public GameGroundPanel() {
        setLayout(null);




        // 단어 입력 버튼
        JTextField input = new JTextField(30);
        input.setLocation(250, 700);
        input.setSize(150, 30);
        add(input);
    }

    @Override
    protected void paintComponent(Graphics g) { // 배경 요소
        super.paintComponent(g);
        g.drawImage(ground.getImage(), 0, 0, getWidth(), getHeight(), this);
    }
}