package ui.game.left;

import manager.CharacterManager;
import character.type.EnemyType;
import manager.WordManager;
import ui.game.right.EditPanel;
import ui.game.right.ScorePanel;
import word.WordStore;

import javax.swing.*;
import java.awt.*;

public class PlayPanel extends JPanel {
    WordStore wordStore = new WordStore();

    WordManager wordManager;

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

        // 이렇게 하는 이유가 있었는데
        // 단어 입력 칸 생성
        CharacterManager characterManager = groundPanel.getCharacterManager(); // GroundPanel의 정보를 받은 CharacterManager 반환
        inputPanel = new InputPanel(scorePanel, editPanel, wordStore, characterManager, groundPanel); // InputPanel에 CharacterManager 주입

        editPanel.setCharacterManager(characterManager);

        add(inputPanel, BorderLayout.SOUTH);
        add(groundPanel, BorderLayout.CENTER);
    }
}
