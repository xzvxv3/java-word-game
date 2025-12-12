package ui.game.left;

import manager.CharacterManager;
import manager.WordManager;
import ui.game.right.ItemPanel;
import ui.game.right.ScorePanel;

import javax.swing.*;
import java.awt.*;

public class PlayPanel extends JPanel {
    private GroundPanel groundPanel;
    private InputPanel inputPanel;

    private ScorePanel scorePanel;
    private ItemPanel itemPanel;

    CharacterManager characterManager;
    WordManager wordManager;

    public PlayPanel(ScorePanel scorePanel, ItemPanel itemPanel, CharacterManager characterManager, WordManager wordManager) {
        this.itemPanel = itemPanel;
        this.scorePanel = scorePanel;
        this.characterManager = characterManager;
        this.wordManager = wordManager;

        setLayout(new BorderLayout());
        // 게임 실행 화면 생성
        groundPanel = new GroundPanel(characterManager, wordManager); // wordStore 주입
        // 단어 입력 칸 생성
        inputPanel = new InputPanel(scorePanel, itemPanel, wordManager, characterManager); // InputPanel에 CharacterManager 주입

        itemPanel.setCharacterManager(characterManager);

        add(inputPanel, BorderLayout.SOUTH);
        add(groundPanel, BorderLayout.CENTER);
    }
}
