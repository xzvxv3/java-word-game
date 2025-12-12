package ui.toolbar;

import dto.User;
import manager.LoginManager;
import manager.RankingManager;
import ui.menu.SelectModePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameToolBar extends JToolBar {
    private JButton backBtn = new JButton("Back");
    private JButton startBtn = new JButton("Start");
    private JFrame frame;

    // 사용자 아이디
    private User user = null;

    // 로그인 관리자
    private LoginManager loginManager = null;
    // 랭킹 관리자
    private RankingManager rankingManager = null;

    public GameToolBar(JFrame frame, RankingManager rankingManager, LoginManager loginManager, User user) {
        this.frame = frame;
        this.loginManager = loginManager;
        this.rankingManager = rankingManager;
        this.user = user;
        add(backBtn);
        add(startBtn);

        setFloatable(false);
        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                frame.getContentPane().setLayout(new BorderLayout());
                frame.getContentPane().add(new SelectModePanel(frame, rankingManager, loginManager, user), BorderLayout.CENTER);
                frame.getContentPane().add(new StartToolBar(), BorderLayout.NORTH);
                frame.revalidate();
                frame.repaint();
            }
        });
    }
}
