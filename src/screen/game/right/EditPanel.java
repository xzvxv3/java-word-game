package screen.game.right;

import javax.swing.*;
import java.awt.*;

public class EditPanel extends JPanel {
    private JTextField inputField = new JTextField(10);
    private JButton saveBtn = new JButton("Save");

    public EditPanel() {
        this.setBackground(Color.cyan);
        add(inputField);
        add(saveBtn);
    }
}
