package screen.game.left;

import character.CharacterManager;
import word.WordStore;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    WordStore wordStore = new WordStore();
    private GroundPanel groundPanel;
    private InputPanel inputPanel;

    public GamePanel() {
        setLayout(new BorderLayout());

        // 게임 실행 화면 생성
        groundPanel = new GroundPanel(wordStore); // wordStore 주입

        // 단어 입력 칸 생성
        CharacterManager characterManager = groundPanel.getCharacterManager();
        inputPanel = new InputPanel(wordStore, characterManager, groundPanel); // CharacterManager 주입

        add(inputPanel, BorderLayout.SOUTH);
        add(groundPanel, BorderLayout.CENTER);
    }
}
