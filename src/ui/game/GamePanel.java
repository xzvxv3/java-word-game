package ui.game;

import character.type.EnemyType;
import dto.User;
import manager.CharacterManager;
import manager.RankingManager;
import manager.WordManager;
import ui.game.left.PlayPanel;
import ui.game.right.ItemPanel;
import ui.game.right.ScorePanel;

import javax.swing.*;

public class GamePanel extends JSplitPane {

    private PlayPanel playPanel;
    private ScorePanel scorePanel; // 점수 판넬
    private ItemPanel itemPanel; // 편집 판넬

    private CharacterManager characterManager = new CharacterManager();
    private WordManager wordManager = null;
    private RankingManager rankingManager = null;
    private User user = null;

    // 게임 전체 화면
    public GamePanel(EnemyType enemyType, RankingManager rankingManager, User user) {
        // Monster 설정
        characterManager.setEnemyType(enemyType);

        this.rankingManager = rankingManager;
        this.user = user;

        wordManager = new WordManager(characterManager, rankingManager, user);

        // ====================== 오른쪽 화면(점수 + 체력바, 아이템 선택 화면) ======================
        scorePanel = new ScorePanel(characterManager); // 점수 Panel 생성
        scorePanel.setHpBar(characterManager.getManHP(), characterManager.getEnemyHP()); // 체력바 생성

        wordManager.setScorePanel(scorePanel); // WordManager에게 Score Panel 주입 (점수 판정용)
        itemPanel = new ItemPanel(scorePanel); // 아이템 선택 Panel

        // ====================== 왼쪽 화면(게임 화면) ======================
        playPanel = new PlayPanel(scorePanel, itemPanel, characterManager, wordManager, rankingManager, user);


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
