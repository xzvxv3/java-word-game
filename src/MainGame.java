import com.sun.tools.javac.Main;

import javax.swing.*;
import java.awt.*;

public class MainGame extends JFrame {

    public MainGame() {
        setTitle("Test1");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 150);

        MyPanel panel = new MyPanel();
        add(panel);

        setVisible(true);
    }

    class MyPanel extends JPanel {
        private ImageIcon icon = new ImageIcon("src/sprites/original/idle/Idle01.png");
        private Image img = icon.getImage();

        public MyPanel() {
            setBackground(Color.BLACK);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(img, 20, 20, this);
        }
    }

    public static void main(String[] args) {
        new MainGame();
    }
}
