package ui.menu;

import character.type.EnemyType;
import ui.game.GamePanel;
import ui.toolbar.GameToolBar;
import ui.toolbar.StartToolBar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectModePanel extends JPanel {
    private ImageIcon backgroundImage = new ImageIcon("resources/images/background/menu/select_mode_background_image.png");

    private ImageIcon gameTitleImage = new ImageIcon("resources/background/icon/SelectModeImage2.png");

    private String[] modeNames = {"Scarecrow", "Mushroom", "Wolf", "Skeleton"};
    private JButton[] btns = new JButton[modeNames.length];

    private JFrame frame;

    ImageIcon[] iconsImage = {
            new ImageIcon("resources/icon/ScarecrowIcon.png"),
            new ImageIcon("resources/icon/MushroomIcon.png"),
            new ImageIcon("resources/icon/WolfIcon.png"),
            new ImageIcon("resources/icon/ReaperIcon.png"),
    };

    ImageIcon[] iconsImageRollover = {
            new ImageIcon("resources/icon/ScarecrowIconRollover.png"),
            new ImageIcon("resources/icon/MushroomIconRollover.png"),
            new ImageIcon("resources/icon/WolfIconRollover.png"),
            new ImageIcon("resources/icon/ReaperIconRollover.png")
    };

    JButton backBtn = new JButton(new ImageIcon("resources/icon/backbtn/BackBtn.png"));

    public SelectModePanel(JFrame frame) {
        this.frame = frame;
        setLayout(null);
        setModeBtns();
        setBackBtn();
    }

    private void setBackBtn() {
        backBtn.setLocation(50, 630);
        backBtn.setSize(140, 55);
        backBtn.setContentAreaFilled(false); // 버튼 기본 배경 제거
        backBtn.setBorderPainted(false); // 테두리 제거
        backBtn.setFocusPainted(false); // 포커스 테두리 제거
        backBtn.setOpaque(false); // 불투명 속성 제거

        backBtn.setRolloverIcon(new ImageIcon("resources/icon/backbtn/BackBtnRollover.png"));

        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                frame.getContentPane().add(new MenuPanel(frame), BorderLayout.CENTER);
                frame.getContentPane().add(new StartToolBar(), BorderLayout.NORTH);
                frame.revalidate();
                frame.repaint();
            }
        });
        add(backBtn);
    }


    private void setModeBtns() {
        // 모드 선택 버튼 레이아웃 배치
        for (int i = 0; i < iconsImage.length; i++) {
            JButton btn = new JButton(iconsImage[i]);
            btn.setRolloverIcon(iconsImageRollover[i]);

            btn.setSize(150, 150);
            btn.setLocation(50 + 210 * i, 200 );

            add(btn);
            btns[i] = btn;
        }

        // 이벤트 리스너 추가
        // Scarecrow 모드
        btns[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                frame.getContentPane().setLayout(new BorderLayout());
                frame.getContentPane().add(new GamePanel(EnemyType.SCARECROW), BorderLayout.CENTER);
                frame.getContentPane().add(new GameToolBar(frame), BorderLayout.NORTH);
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
                frame.getContentPane().add(new GameToolBar(frame), BorderLayout.NORTH);
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
                frame.getContentPane().add(new GameToolBar(frame), BorderLayout.NORTH);
                frame.revalidate();
                frame.repaint();
            }
        });

        // Reaper 모드
        btns[3].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                frame.getContentPane().setLayout(new BorderLayout());
                frame.getContentPane().add(new GamePanel(EnemyType.REAPER), BorderLayout.CENTER);
                frame.getContentPane().add(new GameToolBar(frame), BorderLayout.NORTH);
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