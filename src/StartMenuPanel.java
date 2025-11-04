import javax.swing.*;
import java.awt.*;

public class StartMenuPanel extends JPanel {
    JButton startbtn = new JButton("Game Start");
    JButton showRanking = new JButton("Scores");
    JButton setWord = new JButton("Word Settings");
    JButton[] btns = {startbtn, showRanking, setWord};

    private ImageIcon sky = new ImageIcon("resources/background/menu.png");

    public StartMenuPanel() {
        setLayout(null);
        setBackground(Color.BLACK);

        // 로그인 판넬 위에 추가
        add(new LoginPanel());

        for(int i=0; i < btns.length; i++) {
            btns[i].setSize(330, 50);
            btns[i].setLocation(300, 350 + 70 * i);
            add(btns[i]);
        }
    }

    @Override
    protected void paintComponent(Graphics g) { // 배경 화면 추가
        super.paintComponent(g);
        Image skyImg = sky.getImage();
        g.drawImage(skyImg, 0, 0, this.getWidth(), this.getHeight(),this);
    }
}
