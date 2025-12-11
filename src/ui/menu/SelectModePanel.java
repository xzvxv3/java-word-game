package ui.menu;

import character.type.EnemyType;
import ui.common.GameImageButton;
import ui.game.GamePanel;
import ui.toolbar.GameToolBar;
import ui.toolbar.StartToolBar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectModePanel extends JPanel {
    private ImageIcon backgroundImage = new ImageIcon("resources/images/background/menu/select_mode_background_image.png");

    private ImageIcon gameTitleImage = new ImageIcon("resources/images/element/menu/selectmode_lbl.png");

    private JButton scarecrowBtn = new GameImageButton(
            "resources/images/button/menu/scarecrow_btn.png",
            "resources/images/button/menu/scarecrow_btn_rollover.png"
    );

    private JButton mushroomBtn = new GameImageButton(
        "resources/images/button/menu/mushroom_btn.png",
            "resources/images/button/menu/mushroom_btn_rollover.png"
    );

    private JButton wolfBtn = new GameImageButton(
    "resources/images/button/menu/wolf_btn.png",
            "resources/images/button/menu/wolf_btn_rollover.png"
    );

    private JButton reaperBtn = new GameImageButton(
"resources/images/button/menu/reaper_btn.png",
            "resources/images/button/menu/reaper_btn_rollover.png"
    );

    private JButton[] modeBtns = {scarecrowBtn, mushroomBtn, wolfBtn, reaperBtn};

    // 뒤로가기 버튼
    private JButton backBtn = new GameImageButton(
            "resources/images/button/common/back_btn.png",
            "resources/images/button/common/back_btn_rollover.png"
    );

    private JFrame frame;

    public SelectModePanel(JFrame frame) {
        this.frame = frame;
        setLayout(null);
        initComponent();
        addActionListeners();
        addComponents();
    }

    // 컴포넌트 요소 초기화
    private void initComponent() {
        // mode 버튼
        for (int i = 0; i < modeBtns.length; i++) {
            modeBtns[i].setBounds(150 + 210 * i, 200, 150, 150);
        }
        // back 버튼
        backBtn.setBounds(50, 630, 140, 55);
    }

    // 컴포넌트 패널에 추가
    private void addComponents() {
        add(scarecrowBtn);
        add(mushroomBtn);
        add(wolfBtn);
        add(reaperBtn);
        add(backBtn);
    }

    // 컴포넌트 이벤트 추가
    private void addActionListeners() {

        // Scarecrow 모드
        scarecrowBtn.addActionListener(new ActionListener() {
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
        mushroomBtn.addActionListener(new ActionListener() {
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
        wolfBtn.addActionListener(new ActionListener() {
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
        reaperBtn.addActionListener(new ActionListener() {
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

        // Back 버튼
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
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // 이미지 크기 비율 맞게 버튼 크기에 맞춰 그림
        g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
        g.drawImage(gameTitleImage.getImage(), 250, -250, 600, 700, this);
    }
}