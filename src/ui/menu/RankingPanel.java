package ui.menu;

import dto.User;
import manager.LoginManager;
import manager.UserManager;
import ui.common.GameImageButton;
import ui.toolbar.StartToolBar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RankingPanel extends JPanel {
    private final String BTN_PATH = "resources/images/button/";
    private ImageIcon backgroundImage = new ImageIcon("resources/images/background/common/default.png");
    private ImageIcon rankingTitleImage = new ImageIcon("resources/images/element/menu/ranking_lbl.png");
    private ImageIcon rankingModeImage = new ImageIcon("resources/images/element/menu/ranking_mode_img.png");

    private JFrame frame;
    // 사용자 아이디
    private User user = null;

    // 뒤로가기 버튼
    private JButton backBtn = new GameImageButton(
            BTN_PATH + "common/back_btn.png",
            BTN_PATH + "common/back_btn_rollover.png"
    );

    // 로그인 관리자
    private LoginManager loginManager = null;
    // 랭킹 관리자
    private UserManager userManager = null;

    public RankingPanel(JFrame frame, UserManager userManager, LoginManager loginManager) {
        this.frame = frame;
        this.userManager = userManager;
        this.loginManager = loginManager;

        setLayout(null);
        addBackBtn();
    }

    private void addBackBtn() {
        backBtn.setBounds(50, 630, 140, 55);
        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                frame.getContentPane().add(new MenuPanel(frame, loginManager, userManager), BorderLayout.CENTER);
                frame.getContentPane().add(new StartToolBar(), BorderLayout.NORTH);
                frame.revalidate();
                frame.repaint();
                System.out.println("[메뉴 화면]");
            }
        });
        add(backBtn);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // 이미지 크기 비율 맞게 버튼 크기에 맞춰 그림
        g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
        g.drawImage(rankingTitleImage.getImage(), 250, -250, 600, 700, this);
        g.drawImage(rankingModeImage.getImage(), 140, 40, 800, 400, this);
    }
}


