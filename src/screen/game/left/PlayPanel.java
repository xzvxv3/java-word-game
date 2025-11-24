package screen.game.left;

import character.CharacterManager;
import character.EnemyType;
import screen.game.right.EditPanel;
import screen.game.right.ScorePanel;
import word.WordStore;

import javax.swing.*;
import java.awt.*;

public class PlayPanel extends JPanel {
    WordStore wordStore = new WordStore();
    private GroundPanel groundPanel;
    private InputPanel inputPanel;

    private ScorePanel scorePanel;
    private EditPanel editPanel;

    public PlayPanel(ScorePanel scorePanel, EditPanel editPanel, EnemyType enemyType) {
        this.editPanel = editPanel;
        this.scorePanel = scorePanel;
        setLayout(new BorderLayout());

        // 게임 실행 화면 생성
        groundPanel = new GroundPanel(scorePanel, enemyType, wordStore); // wordStore 주입

        // 단어 입력 칸 생성
        CharacterManager characterManager = groundPanel.getCharacterManager(); // GroundPanel의 정보를 받은 CharacterManager 반환
        inputPanel = new InputPanel(scorePanel, editPanel, wordStore, characterManager, groundPanel); // InputPanel에 CharacterManager 주입

        editPanel.setCharacterManager(characterManager);

        add(inputPanel, BorderLayout.SOUTH);
        add(groundPanel, BorderLayout.CENTER);
    }
}
