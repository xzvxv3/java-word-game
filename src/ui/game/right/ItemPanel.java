package ui.game.right;

import manager.*;
import ui.common.GameImageButton;
import ui.game.left.InputPanel;
import ui.menu.MenuPanel;
import ui.menu.SelectModePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ItemPanel extends JPanel {

    private SwordBtn swordBtn = new SwordBtn();
    private PotionBtn potionBtn = new PotionBtn();
    private TimeBtn timeBtn = new TimeBtn();

    private final String BTN_PATH = "resources/images/button/";

    // 뒤로가기 버튼
    private JButton backBtn = new GameImageButton(
            BTN_PATH + "common/back_btn.png",
            BTN_PATH + "common/back_btn_rollover.png"
    );

    private SwordLabel swordLabel = new SwordLabel();
    private PotionLabel potionLabel = new PotionLabel();
    private TimeLabel timeLabel = new TimeLabel();

    private ScorePanel scorePanel = null;

    private CharacterManager characterManager = null;
    private WordManager wordManager = null;
    private UserManager userManager = null;
    private LoginManager loginManager = null;
    private InputPanel inputPanel = null;

    private JFrame frame;

    public ItemPanel(JFrame frame, ScorePanel scorePanel, WordManager wordManager, UserManager userManager, LoginManager loginManager) {
        this.frame = frame;
        this.scorePanel = scorePanel;
        this.wordManager = wordManager;
        this.userManager = userManager;
        this.loginManager = loginManager;

        this.setBackground(Color.GRAY);
        this.setLayout(null);
        add(swordBtn);
        add(potionBtn);
        add(timeBtn);

        add(swordLabel);
        add(potionLabel);
        add(timeLabel);

        initBackBtn();
    }

    public void setCharacterManager(CharacterManager characterManager) {
        this.characterManager = characterManager;
    }

    public void setInputPanel(InputPanel inputPanel) {this.inputPanel = inputPanel; }

    // 무기 활성화
    public void setSwordON() {
        if(swordLabel.isAlreadyUsedSword()) return;

        swordLabel.setAvailableSword(); // "사용 불가능" -> "사용 가능"
        swordBtn.setSwordON(); // 캐릭터 무기 바꿈
        swordLabel.checkSword(); // 라벨에 무기로 바꿨다고 알림
    }

    // "사용 가능" -> "사용 중"
    public void setAlreadyUsedSword() {
        swordLabel.setAlreadyUsedSword();
    }

    private void initBackBtn() {
        backBtn.setBounds(70, 420, 140, 55);

        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SoundManager.getAudio().play("resources/sounds/back_btn.wav");
                frame.getContentPane().removeAll();
                frame.getContentPane().add(new SelectModePanel(frame, userManager, loginManager), BorderLayout.CENTER);
                frame.revalidate();
                frame.repaint();

                // 게임이 종료되지 않은 경우에만, 스레드 강제 종료
                // 게임이 종료됐는데도 스레드를 다시 interrupt() 시키는걸 방지하기 위하
                if(!characterManager.isGameOver()) {
                    characterManager.shutDown();
                    wordManager.shutDown();
                }
            }
        });

        add(backBtn);
    }

    class SwordBtn extends JButton {
        public SwordBtn() {
            setSize(100, 100);
            setLocation(30, 30);
            setBorderPainted(false);
            setContentAreaFilled(false);
            setFocusPainted(false);
            setIcon(new ImageIcon("resources/images/button/ingame/SwordOFF.png"));

            addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    SoundManager.getAudio().play("resources/sounds/sword_btn.wav");
                    if(scorePanel.isPossibleChangeWeapon()) {
                        characterManager.changeManWeapon();
                        setAlreadyUsedSword();
                    }
                    inputPanel.requestFocusOnTextField();
                }
            });
        }

        public void setSwordON() {
            ImageIcon swordONIcon = new ImageIcon("resources/images/button/ingame/SwordON.png");
            setIcon(swordONIcon);
        }
    }

    class SwordLabel extends JLabel {
        private boolean sword = false;

        public SwordLabel() {
            setText("사용 불가");
            setSize(200, 50);
            setLocation(155, 50);
            setFont(new Font("맑은 고딕", Font.BOLD, 25));
        }

        public void setAvailableSword() {
            setText("사용 가능");
        }

        public void setAlreadyUsedSword() {
            setText("사용 중");
        }

        public void checkSword() {
            sword = true;
        }

        public boolean isAlreadyUsedSword() {
            return sword;
        }
    }

    // ========================= 포션 버튼 =========================

    class PotionBtn extends JButton {
        private int healCount = 0;

        public PotionBtn() {
            setSize(100, 100);
            setLocation(30, 160);
            setBorderPainted(false);
            setContentAreaFilled(false);
            setFocusPainted(false);

            ImageIcon potionIcon = new ImageIcon("resources/images/button/ingame/Potion.png");
            setIcon(potionIcon);
            // 클릭시 Man의 HP 20 증가
            addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    inputPanel.requestFocusOnTextField();
                    if(healCount >= 3) return;

                    SoundManager.getAudio().play("resources/sounds/potion.wav");

                    scorePanel.healManHP();
                    characterManager.healManHP();
                    potionLabel.usePotion();
                    healCount++;

                    if(healCount == 3) {
                        setIcon(new ImageIcon("resources/images/button/ingame/PotionEmpty.png"));
                    }

                }
            });
        }
    }

    class PotionLabel extends JLabel {
        private final int MAX_POTION_CAPACITY = 3;
        private int potionsLeft = 3;
        public PotionLabel() {
            setText(potionsLeft + " / " + MAX_POTION_CAPACITY);
            setSize(200, 50);
            setLocation(165, 180);
            setFont(new Font("맑은 고딕", Font.BOLD, 30));
        }

        public void usePotion() {
            potionsLeft--;
            setText(potionsLeft + " / " + MAX_POTION_CAPACITY);
        }
    }

    // ========================= 타임 버튼 =========================
    class TimeBtn extends JButton {
        private int timeCount = 0;

        public TimeBtn() {
            setSize(100, 100);
            setLocation(30, 290);

            // 1. 버튼의 입체적인 테두리 제거 (가장자리 경계선 제거)
            setBorderPainted(false);

            // 2. 버튼의 내용 영역 배경 채우기 제거 (회색 등의 기본 배경 제거)
            setContentAreaFilled(false);

            // (선택 사항) 버튼을 눌렀을 때 나타나는 포커스 테두리도 제거
            setFocusPainted(false);

            ImageIcon timeIcon = new ImageIcon("resources/images/button/ingame/TimeOFF.png");
            setIcon(timeIcon);

            addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    SoundManager.getAudio().play("resources/sounds/time_btn.wav");
                    inputPanel.requestFocusOnTextField();

                    if(timeCount >= 3) return;

                    timeCount++;

                    if(timeCount == 3) {
                        setIcon(new ImageIcon("resources/images/button/ingame/PotionEmpty.png"));
                    }

                    wordManager.pauseByItem();
                    inputPanel.requestFocusOnTextField();
                    timeLabel.useTimeStop();
                }
            });
        }
    }

    class TimeLabel extends JLabel {
        private final int MAX_TIMESTOP_CAPACITY = 3;
        private int timeStopLeft = 3;

        public TimeLabel() {
            setText(timeStopLeft + " / " + MAX_TIMESTOP_CAPACITY);
            setSize(200, 50);
            setLocation(165, 315);
            setFont(new Font("맑은 고딕", Font.BOLD, 30));
        }

        public void useTimeStop() {
            timeStopLeft--;
            setText(timeStopLeft + " / " + MAX_TIMESTOP_CAPACITY);
        }
    }
}
