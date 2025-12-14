package ui.menu;

import character.type.EnemyType;
import dto.User;
import manager.CharacterManager;
import manager.LoginManager;
import manager.UserManager;
import manager.WordManager;
import ui.common.GameImageButton;
import ui.game.GamePanel;
import ui.toolbar.GameToolBar;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

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

    private CharacterManager characterManager = new CharacterManager();
    private WordManager wordManager = null;

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
                characterManager.setEnemyType(EnemyType.SCARECROW);
                wordManager = new WordManager(characterManager, userManager, loginManager.getCurrentUser());

                JPanel panel = new JPanel();
                panel.setLayout(null);

                // 팝업창의 크기
                panel.setPreferredSize(new Dimension(300, 200));

                // 단어 낙하 관련 컴포넌트
                JLabel speedLabel = new JLabel("단어 낙하 속도");
                speedLabel.setFont(new Font("맑은 고딕", Font.BOLD, 20));
                speedLabel.setBounds(10, 10, 280, 30); // 맨 위에 위치
                speedLabel.setHorizontalAlignment(JLabel.CENTER); // 글자 가운데 정렬

                JSlider speedSlider = new JSlider(JSlider.HORIZONTAL, 80, 170, 140);
                speedSlider.setInverted(true); // 느림 -> 빠름 (역순으로)
                speedSlider.setMajorTickSpacing(30); // 큰 눈금 간격

                speedSlider.setPaintTicks(true); // 슬라이더 눈금 보이게함
                speedSlider.setPaintLabels(true); // 슬라이더 라벨 보이게함

                // 라벨 테이블
                Hashtable<Integer, JLabel> speedLabelTable = new Hashtable<>();
                speedLabelTable.put(80, new JLabel("Very Hard")); // 가장 작은 숫자 (딜레이 짧음)
                speedLabelTable.put(110, new JLabel("Hard"));
                speedLabelTable.put(140, new JLabel("Medium"));
                speedLabelTable.put(170, new JLabel("Easy"));     // 가장 큰 숫자 (딜레이 김)
                speedSlider.setLabelTable(speedLabelTable);

                // 라벨 바로 아래에 배치
                speedSlider.setBounds(10, 35, 280, 50);

                // ====================================

                // 체력 관련 컴포넌트
                JLabel hpLabel = new JLabel("몬스터 체력");
                hpLabel.setFont(new Font("맑은 고딕", Font.BOLD, 20));
                hpLabel.setHorizontalAlignment(JLabel.CENTER);
                hpLabel.setBounds(10, 120, 280, 20);

                JTextField hpTextField = new JTextField(5);
                hpTextField.setHorizontalAlignment(JTextField.CENTER);
                hpTextField.setBounds(100, 155, 100, 30);

                // 패널에 부착
                panel.add(speedLabel);
                panel.add(speedSlider);
                panel.add(hpLabel);
                panel.add(hpTextField);

                // 2. 팝업창 띄우기
                int option = JOptionPane.showConfirmDialog(frame, panel, "연습 모드 설정", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (option == JOptionPane.OK_OPTION) {
                    int wordFallingSpeed = speedSlider.getValue(); // 단어 낙하 속도
                    int scarecrowHP = Integer.parseInt(hpTextField.getText()); // 몬스터 체력

                    wordManager.setPraticeWordFallSpeed(wordFallingSpeed);

                    characterManager.setEnemyHP(scarecrowHP);

                    frame.getContentPane().removeAll();
                    frame.getContentPane().setLayout(new BorderLayout());
                    frame.getContentPane().add(new GamePanel(frame, characterManager, wordManager, loginManager, userManager), BorderLayout.CENTER);
                    frame.revalidate();
                    frame.repaint();
                    System.out.println("[게임 시작] Pratice Mode");
                }
                else {
                    System.out.println("[취소] 게임 시작 취소했습니다.");
                }
            }
        });

        // Mushroom 모드
        mushroomBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                characterManager.setEnemyType(EnemyType.MUSHROOM);
                wordManager = new WordManager(characterManager, userManager, loginManager.getCurrentUser());
                wordManager.setWordFallSpeed(EnemyType.MUSHROOM);

                frame.getContentPane().removeAll();
                frame.getContentPane().setLayout(new BorderLayout());
                frame.getContentPane().add(new GamePanel(frame, characterManager, wordManager, loginManager, userManager), BorderLayout.CENTER);
                frame.revalidate();
                frame.repaint();
                System.out.println("[게임 시작] Easy Mode");
            }
        });

        // Wolf 모드
        wolfBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                characterManager.setEnemyType(EnemyType.WOLF);
                wordManager = new WordManager(characterManager, userManager, loginManager.getCurrentUser());
                wordManager.setWordFallSpeed(EnemyType.WOLF);

                frame.getContentPane().removeAll();
                frame.getContentPane().setLayout(new BorderLayout());
                frame.getContentPane().add(new GamePanel(frame, characterManager, wordManager, loginManager, userManager), BorderLayout.CENTER);
                frame.revalidate();
                frame.repaint();
                System.out.println("[게임 시작] Medium Mode");
            }
        });

        // Reaper 모드
        reaperBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                characterManager.setEnemyType(EnemyType.REAPER);
                wordManager = new WordManager(characterManager, userManager, loginManager.getCurrentUser());
                wordManager.setWordFallSpeed(EnemyType.REAPER);

                frame.getContentPane().removeAll();
                frame.getContentPane().setLayout(new BorderLayout());
                frame.getContentPane().add(new GamePanel(frame, characterManager, wordManager, loginManager, userManager), BorderLayout.CENTER);
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