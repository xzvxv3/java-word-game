package ui.menu;

import character.type.EnemyType;
import dto.User;
import manager.LoginManager;
import manager.UserManager;
import ui.common.GameImageButton;
import ui.game.GamePanel;
import ui.toolbar.GameToolBar;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectModePanel extends JPanel {

    private final String BTN_PATH = "resources/images/button/";

    private ImageIcon backgroundImage = new ImageIcon("resources/images/background/common/default.png");

    private ImageIcon gameTitleImage = new ImageIcon("resources/images/element/menu/selectmode_lbl.png");

    // Scarecrow 모드 버튼
    private JButton scarecrowBtn = new GameImageButton(
            BTN_PATH + "menu/scarecrow_btn.png",
            BTN_PATH + "menu/scarecrow_btn_rollover.png"
    );

    // Mushroom 모드 버튼
    private JButton mushroomBtn = new GameImageButton(
            BTN_PATH + "menu/mushroom_btn.png",
            BTN_PATH + "menu/mushroom_btn_rollover.png"
    );

    // Wolf 모드 버튼
    private JButton wolfBtn = new GameImageButton(
            BTN_PATH + "menu/wolf_btn.png",
            BTN_PATH + "menu/wolf_btn_rollover.png"
    );

    // Repear 모드 버튼
    private JButton reaperBtn = new GameImageButton(
            BTN_PATH + "menu/reaper_btn.png",
            BTN_PATH + "menu/reaper_btn_rollover.png"
    );

    // 뒤로가기 버튼
    private JButton backBtn = new GameImageButton(
            BTN_PATH + "common/back_btn.png",
            BTN_PATH + "common/back_btn_rollover.png"
    );

    // 게임 모드 버튼 배열
    private JButton[] modeBtns = {scarecrowBtn, mushroomBtn, wolfBtn, reaperBtn};

    private JFrame frame;

    // 사용자 아이디
    private User user = null;

    // 로그인 관리자
    private LoginManager loginManager = null;
    // 랭킹 관리자
    private UserManager userManager = null;

    public SelectModePanel(JFrame frame, UserManager userManager, LoginManager loginManager) {
        this.frame = frame;
        this.userManager = userManager;
        this.loginManager = loginManager;
        setLayout(null);
        initComponent();
        addActionListeners();
        addComponents();
    }

    // 컴포넌트 요소 초기화
    private void initComponent() {
        // mode 버튼
        for (int i = 0; i < modeBtns.length; i++) {
            modeBtns[i].setBounds(100 + 250 * i, 200, 150, 150);
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
                frame.getContentPane().add(new GamePanel(frame, EnemyType.SCARECROW, loginManager, userManager), BorderLayout.CENTER);
                //frame.getContentPane().add(new GameToolBar(frame, userManager, loginManager, user), BorderLayout.NORTH);
                frame.revalidate();
                frame.repaint();
                System.out.println("[게임 시작] Pratice Mode");
            }
        });

        // Mushroom 모드
        mushroomBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                frame.getContentPane().setLayout(new BorderLayout());
                frame.getContentPane().add(new GamePanel(frame, EnemyType.MUSHROOM, loginManager, userManager), BorderLayout.CENTER);
                //frame.getContentPane().add(new GameToolBar(frame, userManager, loginManager, user), BorderLayout.NORTH);
                frame.revalidate();
                frame.repaint();
                System.out.println("[게임 시작] Easy Mode");
            }
        });

        // Wolf 모드
        wolfBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                frame.getContentPane().setLayout(new BorderLayout());
                frame.getContentPane().add(new GamePanel(frame, EnemyType.WOLF, loginManager, userManager), BorderLayout.CENTER);
                //frame.getContentPane().add(new GameToolBar(frame, userManager, loginManager, user), BorderLayout.NORTH);
                frame.revalidate();
                frame.repaint();
                System.out.println("[게임 시작] Medium Mode");
            }
        });

        // Reaper 모드
        reaperBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                frame.getContentPane().setLayout(new BorderLayout());
                frame.getContentPane().add(new GamePanel(frame, EnemyType.REAPER, loginManager, userManager), BorderLayout.CENTER);
                //frame.getContentPane().add(new GameToolBar(frame, userManager, loginManager, user), BorderLayout.NORTH);
                frame.revalidate();
                frame.repaint();
                System.out.println("[게임 시작] Hard Mode");
            }
        });

        // Back 버튼
        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                frame.getContentPane().add(new MenuPanel(frame,  loginManager, userManager), BorderLayout.CENTER);
                frame.revalidate();
                frame.repaint();
                System.out.println("[메뉴 화면]");
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