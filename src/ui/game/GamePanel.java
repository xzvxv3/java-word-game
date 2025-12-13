package ui.game;

import character.type.EnemyType;
import dto.User;
import manager.CharacterManager;
import manager.LoginManager;
import manager.UserManager;
import manager.WordManager;
import ui.game.left.InputPanel;
import ui.game.left.PlayPanel;
import ui.game.right.ItemPanel;
import ui.game.right.ScorePanel;

import javax.swing.*;

public class GamePanel extends JSplitPane {

    private PlayPanel playPanel;
    private ScorePanel scorePanel; // 점수 판넬
    private ItemPanel itemPanel; // 편집 판넬
    private InputPanel inputPanel;

    private CharacterManager characterManager = new CharacterManager();
    private WordManager wordManager = null;
    private UserManager userManager = null;
    private LoginManager loginManager = null;

    private JFrame frame;

    // 게임 전체 화면
    public GamePanel(JFrame frame, EnemyType enemyType, LoginManager loginManager, UserManager userManager) {
        this.frame = frame;
        // Monster 설정
        characterManager.setEnemyType(enemyType);

        this.userManager = userManager;
        this.loginManager = loginManager;

        wordManager = new WordManager(characterManager, userManager, loginManager.getCurrentUser());


        // ====================== 오른쪽 화면(점수 + 체력바, 아이템 선택 화면) ======================
        scorePanel = new ScorePanel(characterManager); // 점수 Panel 생성
        scorePanel.setHpBar(characterManager.getManHP(), characterManager.getEnemyHP()); // 체력바 생성

        wordManager.setScorePanel(scorePanel); // WordManager에게 Score Panel 주입 (점수 판정용)

        itemPanel = new ItemPanel(frame, scorePanel, wordManager, userManager, loginManager); // 아이템 선택 Panel

        // ====================== 왼쪽 화면(게임 화면) ======================
        playPanel = new PlayPanel(scorePanel, itemPanel, characterManager, wordManager, loginManager, userManager);


        // 컴포넌트 배치
        setOrientation(JSplitPane.HORIZONTAL_SPLIT); // 수직 구분선
        setDividerLocation(800); // 수직 구분선 위치
        setEnabled(false); // 구분선 고정

        JSplitPane vPane = new JSplitPane(); // 화면 분할 판넬2
        vPane.setOrientation(JSplitPane.VERTICAL_SPLIT); // 수평 구분선
        vPane.setDividerLocation(250); // 수평 구분선 위치
        vPane.setEnabled(false); // 구분선 고정
        vPane.setTopComponent(scorePanel); // 위는 점수 판넬
        vPane.setBottomComponent(itemPanel); // 아래는 편집 판넬

        setLeftComponent(playPanel); // 화면 분할 판넬2의 왼쪽 배치
        setRightComponent(vPane); // 화면 분할 판넬1의 오른쪽 배치
    }
}
