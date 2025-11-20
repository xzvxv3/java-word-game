package screen.game.right;

import javax.swing.*;
import java.awt.*;

public class EditPanel extends JPanel {
//    private ImageIcon backgroundImage = new ImageIcon("resources/background/StartMenuImage.png");
    private JTextField inputField = new JTextField(10);
    private JButton saveBtn = new JButton("Save");

    public EditPanel() {
        this.setBackground(Color.GRAY);
        add(inputField);
        add(saveBtn);
    }

//    @Override
//    protected void paintComponent(Graphics g) { // 배경 요소
//        super.paintComponent(g);
//        // 전체 배경 화면
//        g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
//    }
}
