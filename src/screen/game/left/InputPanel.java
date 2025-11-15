package screen.game.left;

import character.CharacterManager;
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

    public InputPanel(WordStore wordStore, CharacterManager characterManager, GroundPanel view) {
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
                            it.remove();
                            view.remove(word);
                            view.revalidate();
                            characterManager.getMan().setMotion("SWORD_ATTACK01");
                            characterManager.getEnemy().decreaseHP();
                            characterManager.getEnemy().onAttacked();
                        }
                    }
                }
            }
        });
    }
}