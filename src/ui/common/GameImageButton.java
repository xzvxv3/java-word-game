package ui.common;

import javax.swing.*;

public class GameImageButton extends JButton {

    public GameImageButton(String defaultIconPath, String rolloverIconPath) {
        setIcon(new ImageIcon(defaultIconPath));
        setRolloverIcon(new ImageIcon(rolloverIconPath));

        // 공통 디자인 설정
        setBorderPainted(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
    }
}
