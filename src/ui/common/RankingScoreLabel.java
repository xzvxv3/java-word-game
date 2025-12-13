package ui.common;

import javax.swing.*;
import java.awt.*;

public class RankingScoreLabel extends JLabel {
    public RankingScoreLabel(String text) {
        setText(text);
        setForeground(Color.BLACK);
        setFont(new Font("맑은 고딕", Font.BOLD, 23));
    }
}
