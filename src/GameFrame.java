import javax.swing.*;

import java.awt.*;

public class GameFrame extends JFrame {

    public GameFrame() {
        setTitle("타이핑 게임");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container c = getContentPane();
        c.setLayout(new BorderLayout());

        setVisible(true);
    }
}