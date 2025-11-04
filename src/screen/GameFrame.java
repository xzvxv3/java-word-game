package screen;

import screen.menu.StartMenuPanel;

import javax.swing.*;

import java.awt.*;

public class GameFrame extends JFrame {

    public GameFrame() {
        setTitle("타이핑 게임");
        setSize(900, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container c = getContentPane();
        c.add(new StartMenuPanel());

        // c.add(new screen.GameToolBar(), BorderLayout.NORTH);

        setVisible(true);
        setResizable(false);
    }
}