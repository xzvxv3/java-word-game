package ui.intro;
import character.imageloader.ManImageLoader;
import character.imageloader.MushroomImageLoader;
import character.ui.RunAnimation;
import manager.LoginManager;
import manager.SoundManager;
import manager.UserManager;
import ui.menu.MenuPanel;
import ui.common.GameImageButton;
import ui.common.GameTextField;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPanel extends JPanel {
    // 이미지 기본 경로
    private final String IMG_PATH = "resources/images/";

    // 배경화면 이미지
    private ImageIcon backgroundImage = new ImageIcon(IMG_PATH + "background/ingame/normalday.png");
    // 게임 제목 이미지
    private ImageIcon gameTitleImage = new ImageIcon(IMG_PATH + "element/intro/game_title_lbl.png");
    // id, password 이미지
    private ImageIcon idImage = new ImageIcon(IMG_PATH + "element/intro/id_lbl.png");
    private ImageIcon passwordImage = new ImageIcon(IMG_PATH + "element/intro/password_lbl.png");
    // id, password 입력칸
    private JTextField idTextField = new GameTextField();
    private JPasswordField passwordTextField = new JPasswordField(20); // Password 입력칸
    // id, password
    private String id, password;

    // 로그인 버튼
    private JButton loginButton = new GameImageButton(
            IMG_PATH + "button/intro/login_btn.png",
            IMG_PATH + "button/intro/login_btn_rollover.png"
    );
    // 회원 가입 버튼
    private JButton signupButton = new GameImageButton(
            IMG_PATH + "button/intro/signup_btn.png",
            IMG_PATH + "button/intro/signup_btn_rollover.png"
    );

    // 게임 종료 버튼
    private JButton exitBtn = new GameImageButton(
            IMG_PATH + "button/intro/exit_btn01.png",
            IMG_PATH + "button/intro/exit_btn_rollover01.png"
    );

    // 캐릭터 뛰는 모션
    private RunAnimation runManAnimation = new RunAnimation(this, new ManImageLoader());
    private RunAnimation runMushroomAnimation = new RunAnimation(this, new MushroomImageLoader());

    // 모션 스레드
    private Thread runManMotionThread;
    private Thread runMushroomMotionThread;

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
        runMotionStart();
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
                stopMotion();
                SoundManager.getAudio().play("resources/sounds/login_btn.wav");
                id = idTextField.getText(); // id
                password = String.valueOf(passwordTextField.getPassword()); // password
                if(loginManager.login(id, password)) {
                    frame.getContentPane().removeAll();
                    frame.getContentPane().add(new MenuPanel(frame, loginManager, userManager), BorderLayout.CENTER);
                    frame.revalidate();
                    frame.repaint();
                }
            }
        });

        // 회원가입 버튼
        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopMotion();
                frame.getContentPane().removeAll();
                frame.getContentPane().add(new SignUpPanel(frame, loginManager, userManager), BorderLayout.CENTER);
                frame.revalidate();
                frame.repaint();
                SoundManager.getAudio().play("resources/sounds/click_btn.wav");

                System.out.println("[회원가입 화면]");
            }
        });

        // 게임 종료 버튼
        exitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SoundManager.getAudio().play("resources/sounds/click_btn.wav");

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
        add(exitBtn); // 게임 종료 버튼
    }

    // 뛰는 모션 시작
    private void runMotionStart() {
        runManMotionThread = new Thread(runManAnimation);
        runManMotionThread.start();

        runMushroomMotionThread = new Thread(runMushroomAnimation);
        runMushroomMotionThread.start();
    }

    // 뛰는 모션 종료
    private void stopMotion() {
        runManMotionThread.interrupt();
        runMushroomMotionThread.interrupt();
        System.out.println("[로그인 캐릭터 모션 종료]");
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

        // MAN 이미지
        ImageIcon manMotionImage = runManAnimation.getCurrentFrame();
        if (manMotionImage != null) {
            g.drawImage(manMotionImage.getImage(), 300, 465, 200, 200, this);
        }

        // Mushroom 이미지
        ImageIcon mushroomMotionImage = runMushroomAnimation.getCurrentFrame();
        if (mushroomMotionImage != null) {
            g.drawImage(mushroomMotionImage.getImage(), 580, 400, 350, 400, this);
        }
    }
}
