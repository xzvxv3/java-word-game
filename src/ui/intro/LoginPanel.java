package ui.intro;

import character.imageloader.ManImageLoader;
import character.ui.RunAnimation;

import dto.User;
import manager.LoginManager;
import manager.UserManager;
import ui.menu.MenuPanel;
import ui.common.GameImageButton;
import ui.common.GameTextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPanel extends JPanel {
    // 배경화면 이미지
    private ImageIcon backgroundImage = new ImageIcon("resources/images/background/ingame/normalday.png");
    // 게임 제목 이미지
    private ImageIcon gameTitleImage = new ImageIcon("resources/images/element/intro/game_title_lbl.png");
    // id, password 이미지
    private ImageIcon idImage = new ImageIcon("resources/images/element/intro/id_lbl.png");
    private ImageIcon passwordImage = new ImageIcon("resources/images/element/intro/password_lbl.png");
    // id, password 입력칸
    private JTextField idTextField = new GameTextField();
    private JPasswordField passwordTextField = new JPasswordField(20); // Password 입력칸
    // id, password
    private String id, password;

    // 로그인 버튼
    private JButton loginButton = new GameImageButton(
            "resources/images/button/intro/login_btn.png",
            "resources/images/button/intro/login_btn_rollover.png"
    );
    // 회원 가입 버튼
    private JButton signupButton = new GameImageButton(
            "resources/images/button/intro/signup_btn.png",
            "resources/images/button/intro/signup_btn_rollover.png"
    );

    private JButton exitBtn = new GameImageButton(
            "resources/images/button/intro/exit_btn01.png",
            "resources/images/button/intro/exit_btn_rollover01.png"
    );

    // 캐릭터 뛰는 모션
    private RunAnimation runAnimation = new RunAnimation(this, new ManImageLoader());

    // 유저 관리 클래스
    private LoginManager loginManager;
    // 랭킹 관리 클래스
    private UserManager userManager;

    // 부모 프레임
    private JFrame frame;

    public LoginPanel(JFrame frame, LoginManager loginManager, UserManager userManager) {
        setLayout(null);
        this.frame = frame;
        this.loginManager = loginManager;
        this.userManager = userManager;

        // 컴포넌트 요소 초기화
        initComponent();
        // 컴포넌트 이벤트 추가
        addActionListeners();
        // 컴포넌트 패널에 추가
        addComponents();
        // 움직이는 모션 추가
        //runMotionStart();
    }

    // 컴포넌트 요소 초기화
    private void initComponent() {
        loginButton.setBounds(645, 345, 80, 40);
        signupButton.setBounds(540, 345, 100, 40);
        idTextField.setBounds(450, 225, 270, 40);
        exitBtn.setBounds(50, 687, 130, 50);

        passwordTextField.setBounds(450, 300, 270, 40);
        passwordTextField.setEchoChar('●');
    }

    // 컴포넌트 이벤트 추가
    private void addActionListeners() {
        // 로그인 버튼
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                id = idTextField.getText(); // id
                password = String.valueOf(passwordTextField.getPassword()); // password
                if(loginManager.login(id, password)) {
                    frame.getContentPane().removeAll();
                    frame.getContentPane().add(new MenuPanel(frame, loginManager, userManager), BorderLayout.CENTER);
                    //frame.getContentPane().add(new StartToolBar(), BorderLayout.NORTH);
                    frame.revalidate();
                    frame.repaint();
                }
            }
        });

        // 회원가입 버튼
        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                frame.getContentPane().add(new SignUpPanel(frame, loginManager, userManager), BorderLayout.CENTER);
                frame.revalidate();
                frame.repaint();
                System.out.println("[회원가입 화면]");
            }
        });

        // 게임 종료 버튼
        exitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JLabel label = new JLabel("게임을 종료합니다");
                label.setFont(new Font("맑은 고딕", Font.BOLD, 18)); // 폰트와 크기 설정 (더 크게)
                label.setHorizontalAlignment(SwingConstants.CENTER);   // 글자 가운데 정렬

                int option = JOptionPane.showConfirmDialog(frame, label, "알림창", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (option == JOptionPane.YES_OPTION) {
                    System.out.println("[게임 종료]");
                    System.exit(0); // 프로그램 전체 종료
                }
            }
        });

    }

    // 컴포넌트 패널에 추가
    private void addComponents() {
        add(idTextField); // ID 입력 칸
        add(passwordTextField); // Password 입력 칸
        add(loginButton); // 로그인 버튼 (누를 시, 게임 초기 화면으로 넘어감)
        add(signupButton); // 회원 가입 버튼, (누를 시, 회원 가입 창으로 넘어감)
        add(exitBtn);
    }

    // 뛰는 모션 시작
    private void runMotionStart() {
        Thread runMotionThread = new Thread(runAnimation);
        runMotionThread.start();
    }

    @Override
    protected void paintComponent(Graphics g) { // 배경 요소
        super.paintComponent(g);
        // 전체 배경 화면
        g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
        // 로그인 글자
        g.drawImage(gameTitleImage.getImage(), 300, -130, 500, 500, this);
        // ID 글자
        g.drawImage(idImage.getImage(), 250, 110, 280, 280, this);
        // Password 글자
        g.drawImage(passwordImage.getImage(), 160, 200, 280, 260, this);
        // Run 모션 이미지
//        ImageIcon currentFrame = runAnimation.getCurrentFrame();
//        if (currentFrame != null) {
//            g.drawImage(currentFrame.getImage(), 170, 450, 200, 200, this);
//        }
    }
}
