package ui.common;

import javax.swing.*;

public class GameImageButton extends JButton {
    public GameImageButton(String defaultIconPath, String rolloverIconPath) {
        // 평소의 이미지
        setIcon(new ImageIcon(getClass().getResource(defaultIconPath)));
        // Rollover 상태의 이미지
        setRolloverIcon(new ImageIcon(getClass().getResource(rolloverIconPath)));

        // 버튼의 테두리 제거
        setBorderPainted(false);
        // 버튼의 배경을 투명하게
        setContentAreaFilled(false);
        // 클릭, 포커스시 생기는 점선 테두리 제거
        setFocusPainted(false);
    }
}
