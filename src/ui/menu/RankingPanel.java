package ui.menu;

import character.type.EnemyType;
import dto.User;
import manager.LoginManager;
import manager.UserManager;
import ui.common.GameImageButton;
import ui.common.RankingNameLabel;
import ui.common.RankingScoreLabel;
import ui.toolbar.StartToolBar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class RankingPanel extends JPanel {
    private final String BTN_PATH = "resources/images/button/";
    private ImageIcon backgroundImage = new ImageIcon("resources/images/background/common/default.png");
    private ImageIcon rankingTitleImage = new ImageIcon("resources/images/element/ranking/ranking_title_lbl4.png");

    private JFrame frame;

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

        showRankings();
    }

    private void showRankings() {
        showEasyModeRanking();
        showNormalModeRanking();
        showHardModeRanking();
    }

    private void showEasyModeRanking() {
        Vector<User> v = userManager.getRankingsByMode(EnemyType.MUSHROOM);

        for(int i=0; i<v.size(); i++) {
            User u = v.get(i);

            String name = u.getId();
            String score = Integer.toString(u.getEasyModeScore());
            RankingNameLabel j = new RankingNameLabel(name);
            RankingScoreLabel a = new RankingScoreLabel(score);

            j.setBounds(110, 200 + 45 * i, 120, 30);
            a.setBounds(240, 200 + 45 * i, 70, 30);
            this.add(j);
            this.add(a);

            if(i == 7) break; // 상위 8명까지만 보여줌.
        }
    }

    private void showNormalModeRanking() {
        Vector<User> v = userManager.getRankingsByMode(EnemyType.WOLF);

        for(int i=0; i<v.size(); i++) {
            User u = v.get(i);

            String name = u.getId();
            String score = Integer.toString(u.getNormalModeScore());
            RankingNameLabel j = new RankingNameLabel(name);
            RankingScoreLabel a = new RankingScoreLabel(score);

            j.setBounds(470, 200 + 45 * i, 120, 30);
            a.setBounds(600, 200 + 45 * i, 70, 30);
            this.add(j);
            this.add(a);

            if(i == 7) break; // 상위 8명까지만 보여줌.

            System.out.println(u.getId() + " " + u.getNormalModeScore());
        }
    }

    private void showHardModeRanking() {
        Vector<User> v = userManager.getRankingsByMode(EnemyType.REAPER);

        for(int i=0; i<v.size(); i++) {
            User u = v.get(i);

            String name = u.getId();
            String score = Integer.toString(u.getHardModeScore());
            RankingNameLabel j = new RankingNameLabel(name);
            RankingScoreLabel a = new RankingScoreLabel(score);

            j.setBounds(830, 200 + 45 * i, 120, 30);
            a.setBounds(930, 200 + 45 * i, 70, 30);
            this.add(j);
            this.add(a);

            if(i == 7) break; // 상위 8명까지만 보여줌.
        }
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
        g.drawImage(rankingTitleImage.getImage(), 15, 15, 1070, 600, this);


    }
}


