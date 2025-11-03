import javax.swing.*;
import java.awt.*;

public class StartMenuPanel extends JPanel {
    JButton startbtn = new JButton("Game Start");
    JButton showRanking = new JButton("Scores");
    JButton setWord = new JButton("Word Settings");

    JButton[] btns = {startbtn, showRanking, setWord};

    public StartMenuPanel() {
        setLayout(null);
        setBackground(Color.BLACK);
        // 1. 메뉴 화면 배경 추가할 것

        add(new LoginPanel());

        // 2. 버튼 배경 추가할 것
        for(int i=0; i < btns.length; i++) {
            btns[i].setSize(330, 50);
            btns[i].setLocation(300, 530 + 70 * i);
            add(btns[i]);
        }

    }
}
