import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {
    public LoginPanel() {
        GridLayout grid = new GridLayout(2, 2);
        grid.setVgap(5);
        setLayout(grid);

        setSize(400, 150);
        setLocation(270, 300);

        // 이름
        JLabel name = new JLabel("Name");
        name.setHorizontalAlignment(JLabel.CENTER);
        name.setFont(new Font("Dialog", Font.BOLD, 20));


        // 이름 칸
        JTextField nameInput = new JTextField(5);

        // 레벨
        JLabel level = new JLabel("Level");
        level.setFont(new Font("Dialog", Font.BOLD, 20));
        level.setHorizontalAlignment(JLabel.CENTER);

        // 레벨 박스
        JComboBox levelBox = new JComboBox();
        levelBox.addItem("Easy");
        levelBox.addItem("Medium");
        levelBox.addItem("Hard");


        add(name);
        add(nameInput);
        add(level);
        add(levelBox);
    }
}
