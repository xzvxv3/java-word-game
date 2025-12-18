package ui.intro;
import manager.LoginManager;
import manager.SoundManager;
import manager.UserManager;
import ui.common.GameImageButton;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignUpPanel extends JPanel {
    // 이미지 기본 경로
    private final String IMG_PATH = "resources/images/";
    // 배경화면 이미지
    private ImageIcon backgroundImage = new ImageIcon(IMG_PATH + "background/ingame/morning.png");
    // 회원 가입 글자 이미지
    private ImageIcon signUpTitleImage = new ImageIcon(IMG_PATH + "element/intro/signup_title_lbl.png");
    // ID 글자 이미지
    private ImageIcon idLabel = new ImageIcon(IMG_PATH + "element/intro/id_lbl.png");
    // PASSWORD 글자 이미지
    private ImageIcon passwordLabel = new ImageIcon(IMG_PATH + "element/intro/password_lbl.png");

    // 아이디 입력 칸
    private JTextField idTextField = new JTextField();
    // 비밀번호 입력 칸
    private JTextField passwordTextField = new JTextField();

    // 아이디, 패스워드
    private String id = null, password = null;

    // 아이디 생성 가능 여부
    private boolean isIdVerified = false;

    // 아이디 중복 방지 버튼
    private JButton checkIdButton = new JButton("Check");

    // 회원 가입 버튼
    private GameImageButton createAccountButton = new GameImageButton(
            IMG_PATH + "button/intro/create_account_btn.png",
            IMG_PATH + "button/intro/create_account_btn_rollover.png"
    );

    // 뒤로가기 버튼
    GameImageButton backButton = new GameImageButton(
            IMG_PATH + "button/common/back_btn.png",
            IMG_PATH + "button/common/back_btn_rollover.png"
    );

    // 유저 관리 클래스
    private LoginManager loginManager;
    // 랭킹 관리 클래스
    private UserManager userManager;

    // JFrame
    private JFrame frame;

    // 회원가입 패널
    public SignUpPanel(JFrame frame, LoginManager loginManager, UserManager userManager) {
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
    }

    // 컴포넌트 요소 초기화
    private void initComponent() {
        idTextField.setBounds(450, 225, 270, 40);
        passwordTextField.setBounds(450, 300, 270, 40);
        checkIdButton.setBounds(720, 228, 50, 33);
        createAccountButton.setBounds(800, 685, 250, 50);
        backButton.setBounds(50, 685, 140, 55);
    }

    // 컴포넌트 이벤트 추가
    private void addActionListeners() {
        // 아이디 중복 체크
        checkIdButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                id = idTextField.getText(); // 아이디
                String msg = loginManager.checkIdValidity(id); // 해당 아이디에 관한 메시지 (아이디 생성 가능 여부)

                // 아이디 생성 가능하다면
                if(msg.equals("아이디 사용 가능")) {
                    // 알림창 출력
                    JOptionPane.showMessageDialog(frame, "사용 가능한 아이디입니다!");
                    // 아이디 생성 가능
                    isIdVerified = true;
                } else {
                    // 알림창에 해당 아이디에 관한 메시지 출력
                    JOptionPane.showMessageDialog(frame, msg, "아이디 오류", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // 계정 추가 버튼
        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 아이디 중복 체크
                if (!isIdVerified) {
                    JOptionPane.showMessageDialog(frame, "아이디 중복 확인을 먼저 해주세요.", "알림", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // 아이디
                String id = idTextField.getText();
                // 패스워드
                String password = passwordTextField.getText();

                // 패스워드 체크
                if (password.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "비밀번호를 입력해주세요.");
                    return;
                }

                // 가입 성공 알림창
                JOptionPane.showMessageDialog(frame, "회원가입이 완료되었습니다!");
                // 로그인 매니저에게 회원가입 시키라고 알림
                loginManager.signUp(id, password);

                // 모두 완료했으면 아이디, 패스워드 입력 칸을 다시 빈칸으로
                idTextField.setText("");
                passwordTextField.setText("");

                SoundManager.getAudio().play("resources/sounds/click_btn.wav");
            }
        });

        // 뒤로가기 버튼
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SoundManager.getAudio().play("resources/sounds/back_btn.wav");
                frame.getContentPane().removeAll();
                frame.getContentPane().add(new LoginPanel(frame, loginManager, userManager), BorderLayout.CENTER);
                frame.revalidate();
                frame.repaint();
                System.out.println("[로그인 화면]");
            }
        });
    }

    // 컴포넌트 패널에 추가
    private void addComponents() {
        // 아이디 입력 칸
        add(idTextField);
        // 패스워드 입력 칸
        add(passwordTextField);

        // 아이디 중복 체크 버튼
        add(checkIdButton);
        // 계정 생성 버튼
        add(createAccountButton);
        // 뒤로가기 버튼
        add(backButton);
    }

    @Override
    protected void paintComponent(Graphics g) { // 배경 요소
        super.paintComponent(g);

        // 전체 배경 화면
        g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
        // 회원 가입 타이틀
        g.drawImage(signUpTitleImage.getImage(), 300, -130, 500, 500, this);
        // 아이디 라벨
        g.drawImage(idLabel.getImage(), 250, 110, 280, 280, this);
        // 패스워드 라벨
        g.drawImage(passwordLabel.getImage(), 160, 200, 280, 260, this);

        // 허수아비 그림
        g.drawImage(new ImageIcon("resources/sprites/scarecrow/idle/ScareCrowIDie001.png").getImage(), 450, 550, 230, 120, this);
    }
}
