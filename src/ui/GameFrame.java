package ui;
import manager.LoginManager;
import manager.SoundManager;
import manager.UserManager;
import ui.intro.LoginPanel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameFrame extends JFrame {
    // 랭킹 관리자
    private UserManager userManager = new UserManager();
    // 유저 관리 클래스
    private LoginManager loginManager = new LoginManager(userManager);
    // 로그인 패널
    private LoginPanel loginPanel = new LoginPanel(this, loginManager, userManager);

    public GameFrame() {
        // 게임 제목
        super("Man's Journey");
        // 창이 꺼지면 모든 요소가 종료되게끔
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 사용자가 임의로 크기 변경 불가능
        setResizable(false);
        setSize(1100, 800);
        setLocation(350, 50);

        makeMenu(); // 메뉴
        showLogin(); // 로그인 화면으로 이동
        setVisible(true); // 설정한 프레임 창을 화면에 실제로 보이게 함
    }

    // 게임 초기 화면 -> 로그인 창
    private void showLogin() {
        getContentPane().add(loginPanel, BorderLayout.CENTER);
    }

    // 메뉴바 생성
    private void makeMenu() {
        JMenuBar mBar = new JMenuBar();
        this.setJMenuBar(mBar);

        JMenu soundMenu = new JMenu("Sound");
        mBar.add(soundMenu);

        JMenuItem on = new JMenuItem("ON");
        JMenuItem off = new JMenuItem("OFF");

        // ON
        on.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SoundManager.getAudio().setSoundOn(true);
            }
        });

        // OFF
        off.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SoundManager.getAudio().setSoundOn(false);
            }
        });

        // 메뉴에 아이템 추가
        soundMenu.add(on);
        soundMenu.add(off);
    }
}
