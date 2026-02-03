package ui.menu;
import character.type.MonsterType;
import manager.*;
import ui.common.GameImageButton;
import ui.game.GamePanel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

public class SelectModePanel extends JPanel {
    // 버튼 기본 경로
    private final String BTN_PATH = "/images/button/";
    // 배경화면 이미지 경로
    private ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/images/background/common/default.png"));
    // 게임 제목 이미지 경로
    private ImageIcon gameTitleImage = new ImageIcon(getClass().getResource("/images/element/menu/selectmode_lbl.png"));

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
    // JFrame
    private JFrame frame = null;
    // 로그인 관리자
    private LoginManager loginManager = null;
    // 랭킹 관리자
    private UserManager userManager = null;
    // 캐릭터 관리자
    private CharacterManager characterManager = new CharacterManager();
    // 단어 관리자
    private WordManager wordManager = null;

    // 모드 선택 패널
    public SelectModePanel(JFrame frame, UserManager userManager, LoginManager loginManager) {
        this.frame = frame;
        this.userManager = userManager;
        this.loginManager = loginManager;
        setLayout(null);

        // 버튼 크기, 위치 설정
        initComponent();
        // 버튼에게 이벤트 리스너 등록
        addActionListeners();
        // 패널에 버튼 추가
        addComponents();
    }

    // 버튼 크기, 위치 초기화
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
                // 캐릭터 관리자에게 Scarecrow 모드로 설정하라고 알림
                characterManager.setMonsterType(MonsterType.SCARECROW);
                // 현재의 정보를 바탕으로 단어 관리자 생성
                wordManager = new WordManager(characterManager, userManager, loginManager.getCurrentUser());
                SoundManager.getAudio().play("/sounds/click_btn.wav");

                // 팝업창을 꾸밀 패널
                JPanel panel = new JPanel();
                panel.setLayout(null);

                // 팝업창의 크기
                panel.setPreferredSize(new Dimension(300, 200));

                // 단어 낙하 관련 컴포넌트
                JLabel speedLabel = new JLabel("단어 낙하 속도");
                speedLabel.setFont(new Font("맑은 고딕", Font.BOLD, 20));
                speedLabel.setBounds(10, 10, 280, 30); // 맨 위에 위치
                speedLabel.setHorizontalAlignment(JLabel.CENTER); // 글자 가운데 정렬

                // 단어 낙하 속도 조절 슬라이더
                JSlider speedSlider = new JSlider(JSlider.HORIZONTAL, 80, 170, 140);
                speedSlider.setInverted(true); // 느림 -> 빠름 (역순으로)
                speedSlider.setMajorTickSpacing(30); // 큰 눈금 간격
                speedSlider.setPaintTicks(true); // 슬라이더 눈금 보이게함
                speedSlider.setPaintLabels(true); // 슬라이더 라벨 보이게함

                // 단어 낙하 속도의 라벨들
                Hashtable<Integer, JLabel> speedLabelTable = new Hashtable<>();
                speedLabelTable.put(80, new JLabel("Very Hard")); // 가장 작은 숫자
                speedLabelTable.put(110, new JLabel("Hard"));
                speedLabelTable.put(140, new JLabel("Medium"));
                speedLabelTable.put(170, new JLabel("Easy")); // 가장 큰 숫자
                speedSlider.setLabelTable(speedLabelTable);

                // 단어 낙하 속도 조절 라벨 바로 아래에 배치
                speedSlider.setBounds(10, 35, 280, 50);

                // ====================================

                // 체력 관련 컴포넌트
                JLabel hpLabel = new JLabel("몬스터 체력");
                hpLabel.setFont(new Font("맑은 고딕", Font.BOLD, 20));
                hpLabel.setHorizontalAlignment(JLabel.CENTER);
                hpLabel.setBounds(10, 120, 280, 20);

                // 체력 입력 칸
                JTextField hpTextField = new JTextField(5);
                hpTextField.setHorizontalAlignment(JTextField.CENTER);
                hpTextField.setBounds(100, 155, 100, 30);

                // 패널에 부착
                panel.add(speedLabel);
                panel.add(speedSlider);
                panel.add(hpLabel);
                panel.add(hpTextField);

                // 팝업창 띄우기
                int option = JOptionPane.showConfirmDialog(frame, panel, "연습 모드 설정", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                // 확인 버튼 누를 시, 게임 시작
                if (option == JOptionPane.OK_OPTION) {
                    // 공백 제거
                    String inputHp = hpTextField.getText();
                    // 임시로 -1 초기화
                    int hp = -1;
                    // 예외 처리
                    try {
                        hp = Integer.parseInt(inputHp);
                        if(hp <= 0) {
                            System.out.println("체력은 1 이상이어야 합니다.");
                            return;
                        }
                    } catch (NumberFormatException exception) {
                        System.out.println("잘못된 입력");
                        return;
                    }

                    // 단어 낙하 속도
                    int wordFallSpeed = speedSlider.getValue();

                    // 단어 관리자에게 입력 받은 단어 낙하 속도를 넘겨줌
                    wordManager.setPraticeWordFallSpeed(wordFallSpeed);
                    // 캐릭터 관리자에게 입력 받은 체력을 넘겨줌
                    characterManager.setMonsterHP(hp);

                    // 화면 생성
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
                // 캐릭터 관리자에게 Scarecrow 모드로 설정하라고 알림
                characterManager.setMonsterType(MonsterType.MUSHROOM);
                // 현재의 정보를 바탕으로 단어 관리자 생성
                wordManager = new WordManager(characterManager, userManager, loginManager.getCurrentUser());
                // Mushroom에 맞는 단어 낙하 속도 설정
                wordManager.setWordFallSpeed(MonsterType.MUSHROOM);

                // 화면 생성
                frame.getContentPane().removeAll();
                frame.getContentPane().setLayout(new BorderLayout());
                frame.getContentPane().add(new GamePanel(frame, characterManager, wordManager, loginManager, userManager), BorderLayout.CENTER);
                frame.revalidate();
                frame.repaint();
                SoundManager.getAudio().play("/sounds/click_btn.wav");
                System.out.println("[게임 시작] Easy Mode");
            }
        });

        // Wolf 모드
        wolfBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 캐릭터 관리자에게 Wolf 모드로 설정하라고 알림
                characterManager.setMonsterType(MonsterType.WOLF);
                // 현재의 정보를 바탕으로 단어 관리자 생성
                wordManager = new WordManager(characterManager, userManager, loginManager.getCurrentUser());
                // Wolf에 맞는 단어 낙하 속도 설정
                wordManager.setWordFallSpeed(MonsterType.WOLF);

                // 화면 생성
                frame.getContentPane().removeAll();
                frame.getContentPane().setLayout(new BorderLayout());
                frame.getContentPane().add(new GamePanel(frame, characterManager, wordManager, loginManager, userManager), BorderLayout.CENTER);
                frame.revalidate();
                frame.repaint();
                SoundManager.getAudio().play("/sounds/click_btn.wav");
                System.out.println("[게임 시작] Normal Mode");
            }
        });

        // Reaper 모드
        reaperBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 캐릭터 관리자에게 Wolf 모드로 설정하라고 알림
                characterManager.setMonsterType(MonsterType.REAPER);
                // 현재의 정보를 바탕으로 단어 관리자 생성
                wordManager = new WordManager(characterManager, userManager, loginManager.getCurrentUser());
                // Wolf에 맞는 단어 낙하 속도 설정
                wordManager.setWordFallSpeed(MonsterType.REAPER);

                // 화면 생성
                frame.getContentPane().removeAll();
                frame.getContentPane().setLayout(new BorderLayout());
                frame.getContentPane().add(new GamePanel(frame, characterManager, wordManager, loginManager, userManager), BorderLayout.CENTER);
                frame.revalidate();
                frame.repaint();
                SoundManager.getAudio().play("/sounds/click_btn.wav");
                System.out.println("[게임 시작] Hard Mode");
            }
        });

        // 뒤로가기 버튼
        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 화면 생성
                frame.getContentPane().removeAll();
                frame.getContentPane().add(new MenuPanel(frame,  loginManager, userManager), BorderLayout.CENTER);
                frame.revalidate();
                frame.repaint();
                SoundManager.getAudio().play("/sounds/back_btn.wav");
                System.out.println("[메뉴 화면]");
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // 배경 화면
        g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
        // 게임 제목
        g.drawImage(gameTitleImage.getImage(), 250, -250, 600, 700, this);
    }
}