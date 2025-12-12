package ui.menu;

import dto.User;
import manager.LoginManager;
import manager.RankingManager;
import ui.common.GameImageButton;
import ui.intro.LoginPanel;
import ui.toolbar.StartToolBar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPanel extends JPanel {

    private ImageIcon backgroundImage = new ImageIcon("resources/images/background/common/default.png");

    private final String BTN_PATH = "resources/images/button/menu/";

    JButton selectModeBtn = new GameImageButton(
             BTN_PATH + "selectmode_btn.png",
            BTN_PATH + "selectmode_btn_rollover.png"
    );

    JButton rankingBtn = new GameImageButton(
            BTN_PATH + "ranking_btn.png",
            BTN_PATH + "ranking_btn_rollover.png"
    );

    JButton settingBtn = new GameImageButton(
            BTN_PATH + "settings_btn.png",
            BTN_PATH + "settings_btn_rollover.png"
    );

    JButton logoutBtn = new GameImageButton(
            BTN_PATH + "logout_btn.png",
            BTN_PATH + "logout_btn_rollover.png"
    );

    JButton[] btns = {selectModeBtn, rankingBtn, settingBtn, logoutBtn};

    private JFrame frame;

    // 사용자
    private User user = null;


    // 로그인 관리자 (로그아웃 용도)
    private LoginManager loginManager = null;
    // 랭킹 관리자
    private RankingManager rankingManager = null;

    public MenuPanel(JFrame frame, RankingManager rankingManager, LoginManager loginManager, User user) {
        this.frame = frame;
        this.rankingManager = rankingManager;
        this.loginManager = loginManager;
        this.user = user;
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
                frame.getContentPane().add(new SelectModePanel(frame, rankingManager, loginManager, user), BorderLayout.CENTER);
                frame.getContentPane().add(new StartToolBar(), BorderLayout.NORTH);
                frame.revalidate();
                frame.repaint();
                System.out.println("[모드 선택]");
            }
        });

        // 랭킹 버튼
        rankingBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                frame.getContentPane().setLayout(new BorderLayout());
                frame.getContentPane().add(new RankingPanel(frame, rankingManager, loginManager, user), BorderLayout.CENTER);
                frame.getContentPane().add(new StartToolBar(), BorderLayout.NORTH);
                frame.revalidate();
                frame.repaint();
                System.out.println("[랭킹 화면]");
            }
        });

        // 로그아웃 버튼
        logoutBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                frame.getContentPane().setLayout(new BorderLayout());
                frame.getContentPane().add(new LoginPanel(frame, new LoginManager(), rankingManager), BorderLayout.CENTER);
                frame.getContentPane().add(new StartToolBar(), BorderLayout.NORTH);
                frame.revalidate();
                frame.repaint();
                System.out.println("[로그아웃] " + user.getId());
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