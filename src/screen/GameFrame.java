package screen;

import screen.menu.GameMenuPanel;

import javax.swing.*;

import java.awt.*;

public class GameFrame extends JFrame {

    public GameFrame() {
        setTitle("타이핑 게임");
        setSize(900, 800);
        setLocation(500, 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container c = getContentPane();
        c.add(new GameMenuPanel());
        // c.add(new GameSplitPane());
        c.add(new GameToolBar(),BorderLayout.NORTH);

        // c.add(new screen.GameToolBar(), BorderLayout.NORTH);

        setVisible(true);
        setResizable(false);
    }
}