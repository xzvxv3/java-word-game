package ui.game.left;
import character.type.MonsterType;
import user.User;
import manager.*;
import character.type.MotionType;
import character.type.WeaponType;
import ui.game.right.ItemPanel;
import ui.game.right.ScorePanel;
import word.Word;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.Vector;

public class InputPanel extends JPanel {
    // 단어를 입력하는 곳
    private JTextField inputField = new JTextField(10);
    // 캐릭터 관리자
    private CharacterManager characterManager = null;
    // 단어 관리자
    private WordManager wordManager = null;
    // 로그인 관리자
    private LoginManager loginManager = null;
    // 랭킹 관리자
    private UserManager userManager = null;
    // 단어 저장소
    private Vector<Word> words = null;
    // 점수 패널
    private ScorePanel scorePanel = null;
    // 아이템 패널
    private ItemPanel itemPanel = null;
    // 현재 유저
    private User user = null;

    public InputPanel(ScorePanel scorePanel, ItemPanel itemPanel, WordManager wordManager, CharacterManager characterManager, LoginManager loginManager, UserManager userManager) {
        setBackground(Color.GRAY);
        this.scorePanel = scorePanel;
        this.itemPanel = itemPanel;
        this.wordManager = wordManager;
        this.characterManager = characterManager;
        this.userManager = userManager;
        this.loginManager = loginManager;

        // 단어 관리자를 통해 단어 벡터를 가져옴
        words = wordManager.getWordVector();

        // 현재 유저를 로그인 관리자에서 가져옴
        this.user = loginManager.getCurrentUser();
        // 현재 유저 점수 초기화
        user.resetCurrentScore();

        // 컴포넌트 추가
        add(inputField);

        // 입력 칸의 이벤트 리스너 추가
        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 주인공의 체력이 0 이하일 때, 입력 금지
                if (characterManager.getMan().getCurrentHp() <= 0) {
                    // 텍스트만 비우고
                    inputField.setText("");
                    // 즉시 종료 (공격 로직 실행 X)
                    return;
                }

                // 텍스트를 가져옴
                JTextField tf = (JTextField) (e.getSource());
                String userText = tf.getText();
                tf.setText("");

                // 사용자가 입력한 단어가 일치하는지 사용
                Word matchedWord = null;

                // 단어 목록을 안전하게 관리 (동기화)
                synchronized (words) {
                    Iterator<Word> it = words.iterator();

                    // 모든 단어 탐색
                    while (it.hasNext()) {
                        Word word = it.next();
                        // 단어를 맞췄을 경우
                        if (word.getText().equals(userText)) {
                            // 찾은 단어를 백업
                            matchedWord = word;
                            // 리스트에서 삭제
                            it.remove();
                            // 더이상 탐색 X
                            break;
                        }
                    }
                }

                // 단어를 맞춘 경우
                if(matchedWord != null) {
                    // 단어 삭제
                    wordManager.removeWord(matchedWord);

                    // 캐릭터의 무기 상태에 따른 데미지 설정
                    switch (characterManager.getManWeapon()) {
                        case WeaponType.EMPTY :
                            user.incrementCurrentScore(1);
                            scorePanel.increaseScore(1);
                            System.out.println("[공격] → 점수 +1");
                            break;
                        case WeaponType.SWORD :
                            user.incrementCurrentScore(3);
                            scorePanel.increaseScore(3);
                            System.out.println("[공격] → 점수 +3");
                            break;
                    }

                    // 랜덤 모션 용도
                    int r = (int) (Math.random() * 4);

                    // 캐릭터가 빈손일 경우
                    if(characterManager.getManWeapon() == WeaponType.EMPTY) {
                        SoundManager.getAudio().play("resources/sounds/punch01.wav");
                        switch (r) {
                            case 0 : characterManager.getMan().setMotion(MotionType.MAN_ATTACK01); break;
                            case 1 : characterManager.getMan().setMotion(MotionType.MAN_ATTACK02); break;
                            case 2 : characterManager.getMan().setMotion(MotionType.MAN_ATTACK03); break;
                            case 3 : characterManager.getMan().setMotion(MotionType.MAN_ATTACK04); break;
                        }
                        // 몬스터 체력 감소
                        characterManager.decreaseEnemyHP(1);
                    }
                    // 캐릭터가 무기를 가지고 있는 경우
                    else if(characterManager.getManWeapon() == WeaponType.SWORD) {
                        SoundManager.getAudio().play("resources/sounds/sword.wav");
                        switch (r) {
                            case 0 : characterManager.getMan().setMotion(MotionType.MAN_SWORD_ATTACK01); break;
                            case 1 : characterManager.getMan().setMotion(MotionType.MAN_SWORD_ATTACK02); break;
                            case 2 : characterManager.getMan().setMotion(MotionType.MAN_SWORD_ATTACK03); break;
                            case 3 : characterManager.getMan().setMotion(MotionType.MAN_SWORD_ATTACK04); break;
                        }
                        // 몬스터 체력 감소
                        characterManager.decreaseEnemyHP(3);
                    }

                    // 적 데미지 입는 모션
                    characterManager.getEnemy().onAttacked();

                    // 게임 종료 되는지
                    if(characterManager.isGameOver()) {
                        // 모든 단어 스레드 종료
                        wordManager.shutDown();

                        // 캐릭터 스레드 자연스러운 종료 유도
                        if(characterManager.getCurrentMonsterHP() <= 0) characterManager.getMan().stop();
                        else if(characterManager.getCurrentManHP() <= 0) characterManager.getEnemy().stop();

                        // 모드에 따른 현재 유저의 랭킹 갱신
                        if(SettingsManager.getInstance().getCurrentWordBookPath().equals("resources/words/words.txt")) {
                            // 유저 점수 갱신
                            user.updateCurrentScore(characterManager.getMonsterType(), user.getCurrentScore());
                            // 랭킹에 업데이트
                            userManager.updateUser(user);
                        }
                    }
                }

                // Reaper 몬스터 한정 스킬 발동
                else if(characterManager.getMonsterType() == MonsterType.REAPER) {
                    System.out.println("[Reaper 스킬 발동!]");
                    SoundManager.getAudio().play("resources/sounds/reaper_skill.wav");
                    characterManager.useReaperSkill();
                    wordManager.useReaperSkill();
                }

                // 점수가 15점 이상이면 무기 버튼 활성화
                if(scorePanel.isPossibleChangeWeapon()) {
                    itemPanel.setSwordON();
                }
            }
        });
    }

    // 단어 입력창에 포커스가 가도록 함
    public void requestFocusOnTextField() {
        inputField.requestFocusInWindow();
    }
}