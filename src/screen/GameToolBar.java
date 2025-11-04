package screen;

import javax.swing.*;
import java.awt.*;

public class GameToolBar extends JToolBar {

    public GameToolBar() {
        setBackground(Color.LIGHT_GRAY);

        add(new JButton("열기"));
        add(new JButton("닫기"));
    }
}
