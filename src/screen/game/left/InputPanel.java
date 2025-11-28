package screen.game.left;

import character.CharacterManager;
import character.MotionType;
import character.WeaponType;
import screen.game.right.EditPanel;
import screen.game.right.ScorePanel;
import word.Word;
import word.WordStore;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.Vector;

public class InputPanel extends JPanel {
    private JTextField inputField = new JTextField(10);
    private WordStore wordStore;
    private CharacterManager characterManager;
    private GroundPanel view;

    // 단어 저장소
    private Vector<Word> words;

    private ScorePanel scorePanel;
    private EditPanel editPanel;

    public InputPanel(ScorePanel scorePanel, EditPanel editPanel, WordStore wordStore, CharacterManager characterManager, GroundPanel view) {
        this.editPanel = editPanel;
        this.scorePanel = scorePanel;
        this.setBackground(Color.GRAY);
        this.wordStore = wordStore;
        this.characterManager = characterManager;
        this.view = view;
        words = wordStore.getWordVector();

        add(inputField);

        inputField.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
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
                            view.remove(word);
                            view.revalidate();

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

                            characterManager.getEnemy().decreaseHP(1);
                            characterManager.getEnemy().onAttacked();

                            break;
                        }
                    }
                }

                // 점수가 15점 이상이면 무기 버튼 활성화
                if(scorePanel.isPossibleChangeWeapon()) {
                    editPanel.setSwordON();
                }
            }
        });
    }
}