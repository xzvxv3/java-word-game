package ui.menu;
import manager.LoginManager;
import manager.SoundManager;
import manager.UserManager;
import ui.common.GameImageButton;
import ui.intro.LoginPanel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPanel extends JPanel {
    // 배경 이미지
    private ImageIcon backgroundImage = new ImageIcon("resources/images/background/common/default.png");

    // 버튼 이미지 경로
    private final String BTN_PATH = "resources/images/button/menu/";

    // 단어장 기본 경로 (수업 자료)
    private String wordBookPATH = "resources/words/words.txt";

    // 모드 선택 버튼
    JButton selectModeBtn = new GameImageButton(
             BTN_PATH + "selectmode_btn.png",
            BTN_PATH + "selectmode_btn_rollover.png"
    );

    // 랭킹 버튼
    JButton rankingBtn = new GameImageButton(
            BTN_PATH + "ranking_btn.png",
            BTN_PATH + "ranking_btn_rollover.png"
    );

    // 단어 설정 버튼
    JButton settingsBtn = new GameImageButton(
            BTN_PATH + "settings_btn.png",
            BTN_PATH + "settings_btn_rollover.png"
    );

    // 로그아웃 버튼
    JButton logoutBtn = new GameImageButton(
            BTN_PATH + "logout_btn.png",
            BTN_PATH + "logout_btn_rollover.png"
    );

    // 4개의 버튼을 담음
    JButton[] btns = {selectModeBtn, rankingBtn, settingsBtn, logoutBtn};
    // JFrame
    private JFrame frame = null;
    // 로그인 관리자 (로그아웃시 필요)
    private LoginManager loginManager = null;
    // 랭킹 관리자
    private UserManager userManager = null;

    // 메뉴 패널
    public MenuPanel(JFrame frame, LoginManager loginManager, UserManager userManager) {
        this.frame = frame;
        this.userManager = userManager;
        this.loginManager = loginManager;
        setLayout(null);

        // 버튼들의 크기, 위치 설정
        initComponent();
        // 버튼들에게 이벤트 리스너 등록
        addActionListeners();
    }

    // 버튼들의 크기, 위치 설정
    private void initComponent() {
        for(int i=0; i < btns.length; i++) {
            btns[i].setSize(320, 60);
            btns[i].setLocation(400, 200 + 100 * i);
            add(btns[i]);
        }
    }

    // 버튼들에게 이벤트 리스너 등록
    private void addActionListeners() {
        // 모드 선택 버튼
        selectModeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                frame.getContentPane().setLayout(new BorderLayout());
                frame.getContentPane().add(new SelectModePanel(frame, userManager, loginManager), BorderLayout.CENTER);
                frame.revalidate();
                frame.repaint();
                SoundManager.getAudio().play("resources/sounds/click_btn.wav");
                System.out.println("[모드 선택]");
            }
        });

        // 랭킹 버튼
        rankingBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                frame.getContentPane().setLayout(new BorderLayout());
                frame.getContentPane().add(new RankingPanel(frame, userManager, loginManager), BorderLayout.CENTER);
                frame.revalidate();
                frame.repaint();
                SoundManager.getAudio().play("resources/sounds/click_btn.wav");
                System.out.println("[랭킹 화면]");
            }
        });

        // 설정 화면
        settingsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                frame.getContentPane().setLayout(new BorderLayout());
                frame.getContentPane().add(new SettingsPanel(frame, userManager, loginManager), BorderLayout.CENTER);
                frame.revalidate();
                frame.repaint();
                SoundManager.getAudio().play("resources/sounds/click_btn.wav");
                System.out.println("[설정 화면]");
            }
        });

        // 로그아웃 버튼
        logoutBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                frame.getContentPane().setLayout(new BorderLayout());
                frame.getContentPane().add(new LoginPanel(frame, loginManager, userManager), BorderLayout.CENTER);
                frame.revalidate();
                frame.repaint();
                SoundManager.getAudio().play("resources/sounds/click_btn.wav");
                loginManager.logout();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // 전체 배경 화면
        g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
    }
}