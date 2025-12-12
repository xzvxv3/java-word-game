package ui.game.left;

import manager.CharacterManager;
import character.type.MotionType;
import character.type.WeaponType;
import manager.WordManager;
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
    private JTextField inputField = new JTextField(10);


    // 캐릭터 관리 클래스
    private CharacterManager characterManager;
    // 단어 관리 클래스
    private WordManager wordManager;

    // 단어 저장소
    private Vector<Word> words;

    private ScorePanel scorePanel;
    private ItemPanel itemPanel;

    // WordManager을 여기에다가 주입시킬것.
    public InputPanel(ScorePanel scorePanel, ItemPanel itemPanel, WordManager wordManager, CharacterManager characterManager) {
        this.scorePanel = scorePanel;
        this.itemPanel = itemPanel;
        this.wordManager = wordManager;
        this.characterManager = characterManager;

        this.setBackground(Color.GRAY);

        this.characterManager = characterManager;
        words = wordManager.getWordVector();

        add(inputField);

        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // 게임 종료시, 더이상의 입력 불가.
                if (characterManager.getMan().getCurrentHp() <= 0) {
                    inputField.setText(""); // 텍스트만 비우고
                    return; // 즉시 종료 (공격 로직 실행 X)
                }

                JTextField tf = (JTextField) (e.getSource());
                String userText = tf.getText();
                tf.setText("");

                synchronized (words) {
                    Iterator<Word> it = words.iterator();

                    while(it.hasNext()) {
                        Word word = it.next();

                        // 단어를 맞췄을 경우
                        if(word.getText().equals(userText)) {
                            scorePanel.increaseScore(1);
                            it.remove();
                            wordManager.removeWord(word);

                            int r = (int) (Math.random() * 4);

                            if(characterManager.getManWeapon() == WeaponType.EMPTY) {
                                switch (r) {
                                    case 0 : characterManager.getMan().setMotion(MotionType.MAN_ATTACK01); break;
                                    case 1 : characterManager.getMan().setMotion(MotionType.MAN_ATTACK02); break;
                                    case 2 : characterManager.getMan().setMotion(MotionType.MAN_ATTACK03); break;
                                    case 3 : characterManager.getMan().setMotion(MotionType.MAN_ATTACK04); break;
                                }
                            }

                            else if(characterManager.getManWeapon() == WeaponType.SWORD) {
                                switch (r) {
                                    case 0 : characterManager.getMan().setMotion(MotionType.MAN_SWORD_ATTACK01); break;
                                    case 1 : characterManager.getMan().setMotion(MotionType.MAN_SWORD_ATTACK02); break;
                                    case 2 : characterManager.getMan().setMotion(MotionType.MAN_SWORD_ATTACK03); break;
                                    case 3 : characterManager.getMan().setMotion(MotionType.MAN_SWORD_ATTACK04); break;
                                }
                            }

                            characterManager.decreaseEnemyHP(1);
                            characterManager.getEnemy().onAttacked();
                            System.out.println("Man 공격");

                            // 게임 종료 되는지
                            if(characterManager.isGameOver()) {
                                if(characterManager.getEnemyHP() <= 0) characterManager.getMan().stop();
                                else if(characterManager.getManHP() <= 0) characterManager.getEnemy().stop();
                            }

                            break;
                        }
                    }
                }

                // 점수가 15점 이상이면 무기 버튼 활성화
                if(scorePanel.isPossibleChangeWeapon()) {
                    itemPanel.setSwordON();
                }
            }
        });
    }
}