package ui.game.right;

import manager.CharacterManager;
import ui.game.left.InputPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ItemPanel extends JPanel {

    private SwordBtn swordBtn = new SwordBtn();
    private PotionBtn potionBtn = new PotionBtn();
    private TimeBtn timeBtn = new TimeBtn();

    private SwordLabel swordLabel = new SwordLabel();
    private PotionLabel potionLabel = new PotionLabel();
    private TimeLabel timeLabel = new TimeLabel();

    private ScorePanel scorePanel = null;

    private CharacterManager characterManager = null;
    private InputPanel inputPanel = null;

    public ItemPanel(ScorePanel scorePanel) {
        this.scorePanel = scorePanel;
        this.setBackground(Color.GRAY);
        this.setLayout(null);
        add(swordBtn);
        add(potionBtn);
        add(timeBtn);

        add(swordLabel);
        add(potionLabel);
        add(timeLabel);
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

    public void usePotion() {
        potionLabel.usePotion();
    }


    class SwordBtn extends JButton {
        public SwordBtn() {
            setSize(100, 100);
            setLocation(30, 40);
            setBorderPainted(false);
            setContentAreaFilled(false);
            setFocusPainted(false);
            setIcon(new ImageIcon("resources/images/button/ingame/SwordOFF.png"));

            // 버튼 한번 클릭하면 다시 못바꾸게?
            addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
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
            setLocation(155, 60);
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

    class PotionBtn extends JButton {
        private int healCount = 0;

        public PotionBtn() {
            setSize(100, 100);
            setLocation(30, 190);
            setBorderPainted(false);
            setContentAreaFilled(false);
            setFocusPainted(false);

            ImageIcon potionIcon = new ImageIcon("resources/images/button/ingame/Potion.png");
            setIcon(potionIcon);
            // 클릭시 Man의 HP 20 증가
            addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    if(healCount >= 3) return;

                    scorePanel.healManHP();
                    usePotion();
                    healCount++;

                    if(healCount == 3) {
                        setIcon(new ImageIcon("resources/images/button/ingame/PotionEmpty.png"));
                    }
                    inputPanel.requestFocusOnTextField();
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
            setLocation(165, 210);
            setFont(new Font("맑은 고딕", Font.BOLD, 30));
        }

        public void usePotion() {
            potionsLeft--;
            setText(potionsLeft + " / " + MAX_POTION_CAPACITY);
        }
    }

    class TimeBtn extends JButton {
        public TimeBtn() {
            setSize(100, 100);
            setLocation(30, 340);

            // 1. 버튼의 입체적인 테두리 제거 (가장자리 경계선 제거)
            setBorderPainted(false);

            // 2. 버튼의 내용 영역 배경 채우기 제거 (회색 등의 기본 배경 제거)
            setContentAreaFilled(false);

            // (선택 사항) 버튼을 눌렀을 때 나타나는 포커스 테두리도 제거
            setFocusPainted(false);

            ImageIcon timeIcon = new ImageIcon("resources/images/button/ingame/TimeOFF.png");
            setIcon(timeIcon);
        }
    }

    class TimeLabel extends JLabel {
        private final int MAX_TIMESTOP_CAPACITY = 3;
        private int timeStopLeft = 3;

        public TimeLabel() {
            setText(timeStopLeft + " / " + MAX_TIMESTOP_CAPACITY);
            setSize(200, 50);
            setLocation(165, 365);
            setFont(new Font("맑은 고딕", Font.BOLD, 30));
        }

        public void usePotion() {
            timeStopLeft--;
            setText(timeStopLeft + " / " + MAX_TIMESTOP_CAPACITY);
        }
    }
}
