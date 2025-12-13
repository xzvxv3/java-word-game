package ui.common;

import javax.swing.*;
import java.awt.*;

public class RankingScoreLabel extends JLabel {
    public RankingScoreLabel(String text) {
        setText(text);
        //setBounds(240, 190, 70, 30);
        //setOpaque(true); // 배경색 보이게 설정
        setForeground(Color.BLACK);
        setFont(new Font("맑은 고딕", Font.BOLD, 23));
    }
}
