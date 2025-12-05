package ui.intro;

import character.imageloader.ManImageLoader;
import character.ui.RunAnimation;
import manager.UserManager;
import ui.common.GameImageButton;
import ui.toolbar.StartToolBar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignUpPanel extends JPanel {

    // 배경화면 이미지
    private ImageIcon backgroundImage = new ImageIcon("resources/images/background/intro/signup.png");
    // 회원 가입 글자 이미지
    private ImageIcon signUpTitleImage = new ImageIcon("resources/images/element/intro/signup_title_lbl.png");
    // ID 글자 이미지
    private ImageIcon idLabel = new ImageIcon("resources/images/element/intro/id_lbl.png");
    // PASSWORD 글자 이미지
    private ImageIcon passwordLabel = new ImageIcon("resources/images/element/intro/password_lbl.png");

    // 아이디, 비밀번호 입력 칸
    private JTextField idTextField = new JTextField(); // ID 입력칸
    private JTextField passwordTextField = new JTextField(); // Password 입력칸

    private String id, password;

    // 아이디 생성 가능 여부
    private boolean isIdVerified = false;

    // ID 중복 방지 버튼
    private JButton checkIdButton = new JButton("Check");
    // 회원 가입 버튼
    private JButton createAccountButton = new GameImageButton(
            "resources/images/button/intro/create_account_btn.png",
            "resources/images/button/intro/create_account_btn_rollover.png"
    );
    // 뒤로가기 버튼
    JButton backButton = new GameImageButton(
            "resources/images/button/common/back_btn.png",
            "resources/images/button/common/back_btn_rollover.png"
    );

    // 캐릭터 뛰는 모션
    private RunAnimation runAnimation = new RunAnimation(this, new ManImageLoader());

    // 유저 관리 클래스
    private UserManager userManager;
    // 부모 프레임
    private JFrame frame;

    public SignUpPanel(JFrame frame, UserManager userManager) {
        setLayout(null);
        this.frame = frame;
        this.userManager = userManager;

        initComponent();

        addActionListeners();

        // 리팩토링 필요. 로그인 화면 -> 회원가입 화면으로 넘어갈때 모션 부드럽게
        // runMotionStart();

        addComponents();
    }

    // 컴포넌트 요소 초기화
    private void initComponent() {
        idTextField.setBounds(350, 225, 270, 40);
        passwordTextField.setBounds(350, 300, 270, 40);

        checkIdButton.setBounds(620, 228, 50, 33);
        createAccountButton.setBounds(620, 660, 250, 50);

        backButton.setBounds(50, 660, 140, 55);
    }

    // 컴포넌트 이벤트 추가
    private void addActionListeners() {
        // 아이디 중복 체크
        checkIdButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                id = idTextField.getText(); // id

                String msg = userManager.checkIdValidity(id); // 해당 id에 관한 메시지

                if(msg.equals("아이디 사용 가능")) {
                    JOptionPane.showMessageDialog(frame, "사용 가능한 아이디입니다!");
                    isIdVerified = true; // 아이디 생성 가능
                } else {
                    JOptionPane.showMessageDialog(frame, msg, "아이디 오류", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // 계정 추가 버튼
        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // ID 중복 체크
                if (!isIdVerified) {
                    JOptionPane.showMessageDialog(frame, "아이디 중복 확인을 먼저 해주세요.", "알림", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String id = idTextField.getText(); // id
                String password = passwordTextField.getText(); // password

                // Password 체크
                if (password.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "비밀번호를 입력해주세요.");
                    return;
                }

                // 가입 성공
                JOptionPane.showMessageDialog(frame, "회원가입이 완료되었습니다!");
                userManager.addUser(id, password);

                idTextField.setText("");
                passwordTextField.setText("");
            }
        });

        // 뒤로가기 버튼
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                frame.getContentPane().add(new LoginPanel(frame), BorderLayout.CENTER);
                frame.getContentPane().add(new StartToolBar(), BorderLayout.NORTH);
                frame.revalidate();
                frame.repaint();
            }
        });
    }

    // 컴포넌트 패널에 추가
    private void addComponents() {
        add(idTextField); // ID 입력 칸
        add(passwordTextField); // Password 입력 칸

        add(checkIdButton); // 아이디 중복 체크 버튼
        add(createAccountButton);  // 계정 생성 버튼
        add(backButton); // 뒤로가기 버튼
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

        g.drawImage(signUpTitleImage.getImage(), 200, -130, 500, 500, this);

        g.drawImage(idLabel.getImage(), 170, 110, 280, 280, this);

        g.drawImage(passwordLabel.getImage(), 75, 200, 280, 260, this);

        // Run 모션 이미지
        ImageIcon currentFrame = runAnimation.getCurrentFrame();
        if (currentFrame != null) {
            g.drawImage(currentFrame.getImage(), 370, 450, 200, 200, this);
        }
    }
}
