package ui.game.left;

import dto.User;
import manager.CharacterManager;
import manager.RankingManager;
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

    private CharacterManager characterManager = null;
    private WordManager wordManager = null;
    private RankingManager rankingManager = null;

    private User user = null;

    public PlayPanel(ScorePanel scorePanel, ItemPanel itemPanel, CharacterManager characterManager, WordManager wordManager, RankingManager rankingManager, User user) {
        this.itemPanel = itemPanel;
        this.scorePanel = scorePanel;

        this.characterManager = characterManager;
        this.wordManager = wordManager;
        this.rankingManager = rankingManager;
        this.user = user;

        setLayout(new BorderLayout());
        // 게임 실행 화면 생성
        groundPanel = new GroundPanel(characterManager, wordManager); // wordStore 주입
        // 단어 입력 칸 생성
        inputPanel = new InputPanel(scorePanel, itemPanel, wordManager, characterManager, rankingManager, user); // InputPanel에 CharacterManager 주입

        itemPanel.setCharacterManager(characterManager);

        add(inputPanel, BorderLayout.SOUTH);
        add(groundPanel, BorderLayout.CENTER);
    }
}
