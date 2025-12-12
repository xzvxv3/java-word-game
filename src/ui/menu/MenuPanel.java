package ui.menu;

import manager.RankingManager;
import ui.common.GameImageButton;
import ui.toolbar.StartToolBar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPanel extends JPanel {

    private ImageIcon backgroundImage = new ImageIcon("resources/images/background/common/default.png");

    JButton selectModeBtn = new GameImageButton(
            "resources/images/button/menu/selectmode_btn.png",
            "resources/images/button/menu/selectmode_btn_rollover.png"
    );

    JButton rankingBtn = new GameImageButton(
            "resources/images/button/menu/ranking_btn.png",
            "resources/images/button/menu/ranking_btn_rollover.png"
    );

    JButton settingBtn = new GameImageButton(
            "resources/images/button/menu/settings_btn.png",
            "resources/images/button/menu/settings_btn_rollover.png"
    );

    JButton logoutBtn = new GameImageButton(
            "resources/images/button/menu/logout_btn.png",
            "resources/images/button/menu/logout_btn_rollover.png"
    );

    JButton[] btns = {selectModeBtn, rankingBtn, settingBtn, logoutBtn};

    private JFrame frame;
    // 사용자 아이디
    private String id = null;

    public MenuPanel(JFrame frame, String id) {
        this.frame = frame;
        this.id = id;
        setLayout(null);

        initComponent();

        addActionListeners();
    }

    private void initComponent() {
        for(int i=0; i < btns.length; i++) {
            btns[i].setSize(320, 60);
            btns[i].setLocation(400, 200 + 100 * i);
            add(btns[i]);
        }
    }

    private void addActionListeners() {

        // 모드 선택 버튼
        selectModeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                frame.getContentPane().setLayout(new BorderLayout());
                frame.getContentPane().add(new SelectModePanel(frame, id), BorderLayout.CENTER);
                frame.getContentPane().add(new StartToolBar(), BorderLayout.NORTH);
                frame.revalidate();
                frame.repaint();
            }
        });

        // 랭킹 버튼
        rankingBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                frame.getContentPane().setLayout(new BorderLayout());
                frame.getContentPane().add(new RankingPanel(frame, id), BorderLayout.CENTER);
                frame.getContentPane().add(new StartToolBar(), BorderLayout.NORTH);
                frame.revalidate();
                frame.repaint();
            }
        });


    }

    @Override
    protected void paintComponent(Graphics g) { // 배경 요소
        super.paintComponent(g);
        // 전체 배경 화면
        g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
    }
}