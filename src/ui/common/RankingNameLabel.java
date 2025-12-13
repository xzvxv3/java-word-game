package ui.common;

import javax.swing.*;
import java.awt.*;

public class RankingNameLabel extends JLabel {
    public RankingNameLabel(String text) {
        setText(text);
        setForeground(Color.BLACK);
        setFont(new Font("맑은 고딕", Font.BOLD, 23));
    }
}
