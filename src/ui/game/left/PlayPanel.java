package ui.game.left;
import manager.CharacterManager;
import manager.LoginManager;
import manager.UserManager;
import manager.WordManager;
import ui.game.right.ItemPanel;
import ui.game.right.ScorePanel;
import javax.swing.*;
import java.awt.*;

public class PlayPanel extends JPanel {
    // 게임 화면 패널
    private GroundPanel groundPanel = null;
    // 입력칸 패널
    private InputPanel inputPanel = null;

    // 점수 패널
    private ScorePanel scorePanel = null;
    // 아이템 패널
    private ItemPanel itemPanel = null;

    // 캐릭터 관리자
    private CharacterManager characterManager = null;
    // 단어 관리자
    private WordManager wordManager = null;
    // 로그인 관리자
    private LoginManager loginManager = null;
    // 유저 관리자
    private UserManager userManager = null;

    public PlayPanel(ScorePanel scorePanel, ItemPanel itemPanel, CharacterManager characterManager, WordManager wordManager, LoginManager loginManager, UserManager userManager) {
        this.itemPanel = itemPanel;
        this.scorePanel = scorePanel;
        this.loginManager = loginManager;
        this.characterManager = characterManager;
        this.wordManager = wordManager;
        this.userManager = userManager;
        setLayout(new BorderLayout());

        // 게임 실행 화면 생성
        // 캐릭터 관리자, 단어 관리자 주입
        groundPanel = new GroundPanel(characterManager, wordManager);

        // 입력칸 패널 생성
        // 점수 패널, 아이템 패널, 단어 관리자, 캐릭터 관리자, 로그인 관리자, 유저 관리자 주입
        inputPanel = new InputPanel(scorePanel, itemPanel, wordManager, characterManager, loginManager, userManager);

        // 아이템 패널이 캐릭터와 입력창을 제어할 수 있도록 연결
        itemPanel.setCharacterManager(characterManager);
        itemPanel.setInputPanel(inputPanel);

        // 레이아웃 배치
        add(inputPanel, BorderLayout.SOUTH);
        add(groundPanel, BorderLayout.CENTER);
    }
}
