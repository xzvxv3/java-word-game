package screen.start;

import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {

    public LoginPanel() {
        GridLayout grid = new GridLayout(2, 2);
        grid.setVgap(5);
        setLayout(null);

        setSize(350, 150);
        setLocation(290, 150);

        // 이름
        JLabel name = new JLabel("Name");
        name.setHorizontalAlignment(JLabel.CENTER);
        name.setFont(new Font("Dialog", Font.BOLD, 20));
        name.setSize(150, 150);
        name.setHorizontalAlignment(JLabel.CENTER);
        name.setLocation(0, -30);

        // 이름 칸
        JTextField nameInput = new JTextField(5);
        nameInput.setSize(130, 30);
        nameInput.setLocation(150, 30);

        // 레벨
        JLabel level = new JLabel("Level");
        level.setFont(new Font("Dialog", Font.BOLD, 20));
        level.setSize(150, 150);
        level.setHorizontalAlignment(JLabel.CENTER);
        level.setLocation(0, 30);

        // 레벨 박스
        JComboBox<String> levelBox = new JComboBox<>();
        levelBox.addItem("Easy");
        levelBox.addItem("Medium");
        levelBox.addItem("Hard");
        levelBox.setSize(130, 30);
        levelBox.setLocation(150, 90);

        add(name);
        add(nameInput);
        add(level);
        add(levelBox);
    }
}
