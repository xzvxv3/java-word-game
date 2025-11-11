package screen.menu;

import javax.swing.*;
import java.awt.*;

public class StartMenuPanel extends JPanel {
    private ImageIcon backgroundImage = new ImageIcon("resources/background/StartMenuImage.png");
    private ImageIcon gameTitleImage = new ImageIcon("resources/background/icon/GameModeTitle.png");

    private String[] names = {"Scarecrow", "Mushroom", "Wolf", "Skeleton"};
    private JButton[] btns = new JButton[names.length];

    ImageIcon[] iconsImage = {
            new ImageIcon("resources/background/icon/ScarecrowIcon.png"),
            new ImageIcon("resources/background/icon/MushroomIcon.png"),
            new ImageIcon("resources/background/icon/WolfIcon.png"),
            new ImageIcon("resources/background/icon/SkeletonIcon.png"),
    };

    public StartMenuPanel() {
        setLayout(null);
        setBtns();
    }

    private void setBtns() {
        for (int i = 0; i < iconsImage.length; i++) {
            Image img = iconsImage[i].getImage();

            // 🎨 커스텀 JButton 생성
            JButton btn = new JButton(names[i]) {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    // 이미지 크기 비율 맞게 버튼 크기에 맞춰 그림
                    g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
                }
            };

            btn.setSize(150, 150);
            btn.setLocation(50 + 210 * i, 200 );

            add(btn);
            btns[i] = btn;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // 이미지 크기 비율 맞게 버튼 크기에 맞춰 그림
        g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
        g.drawImage(gameTitleImage.getImage(), 140, -250, 600, 700, this);
    }

}