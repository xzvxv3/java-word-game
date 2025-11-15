package screen.game.right;

import javax.swing.*;
import java.awt.*;

// 점수 판넬
public class ScorePanel extends JPanel {
    private int score = 0; // 초기 점수
    private JLabel scoreLabel = new JLabel(Integer.toString(score)); // 점수 현황판

    public ScorePanel() {
        this.setBackground(Color.YELLOW);
        add(new JLabel("점수"));
        add(scoreLabel);
    }

    // 점수 증가 & 점수 현황판 최신화
    public void increase(int amount) {
        score += amount;
        scoreLabel.setText(Integer.toString(score));
    }

    // 점수 증가
    public void increase() {
        increase(10);
    }
}
