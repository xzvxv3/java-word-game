package screen.start;

import character.EnemyType;
import screen.game.GamePanel;
import screen.toolbar.StartToolBar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectModePanel extends JPanel {
    private ImageIcon backgroundImage = new ImageIcon("resources/background/StartMenuImage.png");
    private ImageIcon gameTitleImage = new ImageIcon("resources/background/icon/GameModeTitle.png");

    private String[] names = {"Scarecrow", "Mushroom", "Wolf", "Skeleton"};
    private JButton[] btns = new JButton[names.length];

    private JFrame frame;

    ImageIcon[] iconsImage = {
            new ImageIcon("resources/background/icon/ScarecrowIcon.png"),
            new ImageIcon("resources/background/icon/MushroomIcon.png"),
            new ImageIcon("resources/background/icon/WolfIcon.png"),
            new ImageIcon("resources/background/icon/SkeletonIcon.png"),
    };

    public SelectModePanel(JFrame frame) {
        this.frame = frame;
        setLayout(null);
        setBtnsLayout();
        setBtnsInit();
    }


    private void setBtnsLayout() {
        for (int i = 0; i < iconsImage.length; i++) {
            Image img = iconsImage[i].getImage();

            JButton btn = new JButton(names[i]) {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    // 이미지 크기 비율 맞게 버튼 크기에 맞춰 그림
                    g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
                }
            };

            btn.setSize(150, 150);
            btn.setLocation(50 + 210 * i, 200 );

            add(btn);
            btns[i] = btn;
        }
    }

    private void setBtnsInit() {
        // Scarecrow 모드
        btns[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                frame.getContentPane().setLayout(new BorderLayout());
                frame.getContentPane().add(new GamePanel(EnemyType.SCARECROW), BorderLayout.CENTER);
                frame.getContentPane().add(new StartToolBar(), BorderLayout.NORTH);
                frame.revalidate();
                frame.repaint();
            }
        });

        // Mushroom 모드
        btns[1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                frame.getContentPane().setLayout(new BorderLayout());
                frame.getContentPane().add(new GamePanel(EnemyType.MUSHROOM), BorderLayout.CENTER);
                frame.getContentPane().add(new StartToolBar(), BorderLayout.NORTH);
                frame.revalidate();
                frame.repaint();
            }
        });

        // Wolf 모드
        btns[2].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                frame.getContentPane().setLayout(new BorderLayout());
                frame.getContentPane().add(new GamePanel(EnemyType.WOLF), BorderLayout.CENTER);
                frame.getContentPane().add(new StartToolBar(), BorderLayout.NORTH);
                frame.revalidate();
                frame.repaint();
            }
        });

        // Skeleton 모드
        btns[3].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                frame.getContentPane().setLayout(new BorderLayout());
                frame.getContentPane().add(new GamePanel(EnemyType.SKELETON), BorderLayout.CENTER);
                frame.getContentPane().add(new StartToolBar(), BorderLayout.NORTH);
                frame.revalidate();
                frame.repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // 이미지 크기 비율 맞게 버튼 크기에 맞춰 그림
        g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
        g.drawImage(gameTitleImage.getImage(), 140, -250, 600, 700, this);
    }
}