package screen.game.left;

import character.CharacterManager;
import character.EnemyType;
import word.WordStore;

import javax.swing.*;
import java.awt.*;

public class PlayPanel extends JPanel {
    WordStore wordStore = new WordStore();
    private GroundPanel groundPanel;
    private InputPanel inputPanel;

    public PlayPanel(EnemyType enemyType) {
        setLayout(new BorderLayout());

        // 게임 실행 화면 생성
        groundPanel = new GroundPanel(enemyType, wordStore); // wordStore 주입

        // 단어 입력 칸 생성
        CharacterManager characterManager = groundPanel.getCharacterManager(); // GroundPanel의 정보를 받은 CharacterManager 반환
        inputPanel = new InputPanel(wordStore, characterManager, groundPanel); // InputPanel에 CharacterManager 주입

        add(inputPanel, BorderLayout.SOUTH);
        add(groundPanel, BorderLayout.CENTER);
    }
}
