package ui.game.left;

import dto.User;
import manager.CharacterManager;
import manager.LoginManager;
import manager.UserManager;
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
    private LoginManager loginManager = null;
    private UserManager userManager = null;

    private User user = null;

    public PlayPanel(ScorePanel scorePanel, ItemPanel itemPanel, CharacterManager characterManager, WordManager wordManager, LoginManager loginManager, UserManager userManager) {
        this.itemPanel = itemPanel;
        this.scorePanel = scorePanel;

        this.loginManager = loginManager;
        this.characterManager = characterManager;
        this.wordManager = wordManager;
        this.userManager = userManager;
        this.user = user;

        setLayout(new BorderLayout());
        // 게임 실행 화면 생성
        groundPanel = new GroundPanel(characterManager, wordManager); // wordStore 주입

        // 입력칸
        inputPanel = new InputPanel(scorePanel, itemPanel, wordManager, characterManager, loginManager, userManager);

        // itemPanel과 InputPanel의 상호작용을 위한 연결
        itemPanel.setCharacterManager(characterManager);
        itemPanel.setInputPanel(inputPanel);

        add(inputPanel, BorderLayout.SOUTH);
        add(groundPanel, BorderLayout.CENTER);
    }
}
