package ui.game.right;
import manager.*;
import ui.common.GameImageButton;
import ui.game.left.InputPanel;
import ui.menu.SelectModePanel;
import user.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ItemPanel extends JPanel {
    // 이미지
    private final String IMG_PATH = "/images/";
    // 무기 버튼
    private SwordBtn swordBtn = new SwordBtn();
    // 포션 버튼
    private PotionBtn potionBtn = new PotionBtn();
    // 타임 스탑 버튼
    private TimeBtn timeBtn = new TimeBtn();

    // 뒤로가기 버튼
    private JButton backBtn = new GameImageButton(
            IMG_PATH + "button/common/back_btn.png",
            IMG_PATH + "button/common/back_btn_rollover.png"
    );

    // 무기 글자 라벨
    private SwordLabel swordLabel = new SwordLabel();
    // 포션 글자 라벨
    private PotionLabel potionLabel = new PotionLabel();
    // 타임 스탑 글자 라벨
    private TimeLabel timeLabel = new TimeLabel();

    // JFrame
    private JFrame frame = null;
    // 점수 패널
    private ScorePanel scorePanel = null;
    // 입력 패널
    private InputPanel inputPanel = null;
    // 캐릭터 관리자
    private CharacterManager characterManager = null;
    // 단어 관리자
    private WordManager wordManager = null;
    // 유저 관리자
    private UserManager userManager = null;
    // 로그인 관리자
    private LoginManager loginManager = null;

    // 현재 유저
    private User user;

    // 아이템 패널
    public ItemPanel(JFrame frame, ScorePanel scorePanel, WordManager wordManager, UserManager userManager, LoginManager loginManager) {
        this.frame = frame;
        this.scorePanel = scorePanel;
        this.wordManager = wordManager;
        this.userManager = userManager;
        this.loginManager = loginManager;

        // 배경은 회색
        this.setBackground(Color.GRAY);
        this.setLayout(null);

        // 현재 유저
        user = loginManager.getCurrentUser();

        // 컴포넌트 배치
        add(swordBtn);
        add(potionBtn);
        add(timeBtn);
        add(swordLabel);
        add(potionLabel);
        add(timeLabel);

        // 뒤로가기 버튼
        initBackBtn();
    }

    // 캐릭터 관리자 주입
    public void setCharacterManager(CharacterManager characterManager) {
        this.characterManager = characterManager;
    }

    // 입력 패널 주입
    public void setInputPanel(InputPanel inputPanel) {
        this.inputPanel = inputPanel;
    }

    // 무기 버튼
    class SwordBtn extends JButton {
        public SwordBtn() {
            setSize(100, 100);
            setLocation(30, 30);

            // 무기 잠금 상태
            setIcon(new ImageIcon(getClass().getResource(IMG_PATH + "button/ingame/SwordOFF.png")));

            // 버튼 이벤트 리스너 추가
            addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    SoundManager.getAudio().play("/sounds/sword_btn.wav");
                    // 무기를 바꿀 수 있는 상태라면
                    if(scorePanel.isPossibleChangeWeapon()) {
                        System.out.println("[무기 사용]");
                        // 무기 변경됐다고 캐릭터 관리자에게 알림
                        characterManager.changeManWeapon();
                        // 무기 글자 라벨 변경 [사용 가능 -> 사용 중]
                        swordLabel.setAlreadyUsedSword();
                    }
                    // 사용자의 편의를 위해 단어 입력 칸으로 초점 맞춤
                    inputPanel.requestFocusOnTextField();
                }
            });
        }

        // 무기 사용 중 이미지로 변경
        public void setSwordON() {
            ImageIcon swordONIcon = new ImageIcon(getClass().getResource(IMG_PATH + "button/ingame/SwordON.png"));
            setIcon(swordONIcon);
        }
    }

    // 무기 활성화
    public void setSwordON() {
        // 이미 사용중이라고 표시가 돼있으면 PASS
        if(characterManager.hasSword()) return;
        // [사용 불가능] -> [사용 가능]
        swordLabel.setAvailableSword();
        // 캐릭터 무기 바꿈
        swordBtn.setSwordON();
    }

    // 무기 라벨
    class SwordLabel extends JLabel {
        public SwordLabel() {
            setText("사용 불가");
            setSize(200, 50);
            setLocation(155, 50);
            setFont(new Font("맑은 고딕", Font.BOLD, 25));
        }
        // [사용 불가능] -> [사용 가능]
        public void setAvailableSword() {
            setText("사용 가능");
        }
        // [사용 가능] -> [사용 중]
        public void setAlreadyUsedSword() {
            setText("사용 중");
        }
    }

    // ========================= 포션 버튼 =========================

    // 포션 버튼
    class PotionBtn extends JButton {
        // 몇 번 포션을 사용했는지 체크
        private int healCount = 0;

        public PotionBtn() {
            setSize(100, 100);
            setLocation(30, 160);
            // 포션 이미지 설정
            setIcon(new ImageIcon(getClass().getResource(IMG_PATH + "button/ingame/Potion.png")));

            // 클릭시 Man의 HP 20 증가
            addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // 사용자의 편의를 위해 단어 입력 칸으로 초점 맞춤
                    inputPanel.requestFocusOnTextField();

                    // 포션을 이미 3개 먹은 상태라면 더이상 먹지 못함
                    if(healCount == 3) return;
                    System.out.println("[포션 사용]");

                    SoundManager.getAudio().play("/sounds/potion.wav");

                    // 주인공 체력바 충전
                    scorePanel.healManHP();
                    // 주인공 체력 충전
                    characterManager.healManHP();
                    // 포션 현재 남은 개수 재갱신
                    potionLabel.usePotion();
                    // 포션 먹은 횟수 + 1
                    healCount++;

                    // 포션을 3개 먹었다면, 'X' 표시를 남김
                    if(healCount == 3) {
                        setIcon(new ImageIcon(getClass().getResource(IMG_PATH + "button/ingame/PotionEmpty.png")));
                    }
                }
            });
        }
    }

    // 포션 라벨
    class PotionLabel extends JLabel {
        // 포션 최대 개수
        private final int MAX_POTION_CAPACITY = 3;
        // 현재 남은 포션 개수
        private int potionsLeft = 3;

        public PotionLabel() {
            setText(potionsLeft + " / " + MAX_POTION_CAPACITY);
            setSize(200, 50);
            setLocation(165, 180);
            setFont(new Font("맑은 고딕", Font.BOLD, 30));
        }

        // 포션 사용
        public void usePotion() {
            potionsLeft--;
            setText(potionsLeft + " / " + MAX_POTION_CAPACITY);
        }
    }

    // ========================= 타임 스탑 버튼 =========================

    class TimeBtn extends JButton {
        // 타임 스탑 아이템 사용 횟수
        private int timeCount = 0;

        public TimeBtn() {
            setSize(100, 100);
            setLocation(30, 290);
            setIcon(new ImageIcon(getClass().getResource(IMG_PATH + "button/ingame/TimeOFF.png")));

            // 클릭 시 시간을 3초동안 멈추게 함
            addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    SoundManager.getAudio().play("/sounds/time_btn.wav");
                    // 사용자의 편의를 위해 단어 입력 칸으로 초점 맞춤
                    inputPanel.requestFocusOnTextField();

                    // 타임 스탑 아이템을 3번 쓴 상태라면 더이상 못 씀
                    if(timeCount == 3) return;

                    // 타임 스탑 아이템 사용 횟수 + 1
                    timeCount++;

                    // 타입 스탑 아이템을 3번 사용했다면 'X' 이미지 표시
                    if(timeCount == 3) {
                        setIcon(new ImageIcon(getClass().getResource(IMG_PATH + "button/ingame/PotionEmpty.png")));
                    }

                    // 단어를 멈추게함
                    wordManager.pauseByItem();
                    // 타임 스탑 아이템 현재 개수 재갱신
                    timeLabel.useTimeStop();
                }
            });
        }
    }

    // 타임 스탑 라벨
    class TimeLabel extends JLabel {
        // 타임 스탑 아이템 최대 개수
        private final int MAX_TIMESTOP_CAPACITY = 3;
        // 현재 타임 스탑 아이템 개수
        private int timeStopLeft = 3;

        // 타임 스탑 라벨
        public TimeLabel() {
            setText(timeStopLeft + " / " + MAX_TIMESTOP_CAPACITY);
            setSize(200, 50);
            setLocation(165, 315);
            setFont(new Font("맑은 고딕", Font.BOLD, 30));
        }

        // 타임 스탑 아이템 현재 개수 재갱신
        public void useTimeStop() {
            timeStopLeft--;
            setText(timeStopLeft + " / " + MAX_TIMESTOP_CAPACITY);
        }
    }

    // 뒤로가기 버튼
    private void initBackBtn() {
        backBtn.setBounds(70, 420, 140, 55);

        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SoundManager.getAudio().play("/sounds/back_btn.wav");
                frame.getContentPane().removeAll();
                frame.getContentPane().add(new SelectModePanel(frame, userManager, loginManager), BorderLayout.CENTER);
                frame.revalidate();
                frame.repaint();

                // 게임이 종료되지 않은 경우에만, 스레드 강제 종료
                // 게임이 종료됐는데도 스레드를 다시 interrupt() 시키는걸 방지하기 위한 것
                if(!characterManager.isGameOver()) {
                    characterManager.shutDown();
                    wordManager.shutDown();
                }

                // 모드에 따른 현재 유저의 랭킹 갱신 (words 파일인 경우에만 점수를 갱신)
                if(SettingsManager.getInstance().getCurrentWordBookPath().equals("/words/words.txt")) {
                    // 유저 점수 갱신
                    user.updateCurrentScore(characterManager.getMonsterType(), user.getCurrentScore());
                    // 랭킹에 업데이트
                    userManager.updateUser(user);
                }
            }
        });

        add(backBtn);
    }
}
