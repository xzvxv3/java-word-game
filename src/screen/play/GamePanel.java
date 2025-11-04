package screen.play;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    public GamePanel() {
        setBackground(Color.LIGHT_GRAY);
        setLayout(new BorderLayout());
        add(new GameGroundPanel(), BorderLayout.CENTER);
    }
}
