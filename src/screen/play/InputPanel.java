package screen.play;

import javax.swing.*;
import java.awt.*;

public class InputPanel extends JPanel {

    public InputPanel() {
        setBackground(Color.cyan);
        JTextField text = new JTextField(30);
        add(text);
    }

}
