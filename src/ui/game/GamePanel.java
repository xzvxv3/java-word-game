package ui.game;
import manager.CharacterManager;
import manager.LoginManager;
import manager.UserManager;
import manager.WordManager;
import ui.game.left.PlayPanel;
import ui.game.right.ItemPanel;
import ui.game.right.ScorePanel;
import javax.swing.*;

// 게임 전체 패널
public class GamePanel extends JSplitPane {

    // 게임 화면 패널
    private PlayPanel playPanel = null;
    // 점수 패널
    private ScorePanel scorePanel = null;
    // 아이템 패널
    private ItemPanel itemPanel = null;

    // 단어 관리자
    private WordManager wordManager = null;
    // 캐릭터 관리자
    private CharacterManager characterManager = null;
    // 유저 관리자
    private UserManager userManager = null;
    // 로그인 관리자
    private LoginManager loginManager = null;
    // JFrame
    private JFrame frame = null;


    // 게임 전체 패널
    public GamePanel(JFrame frame, CharacterManager characterManager, WordManager wordManager, LoginManager loginManager, UserManager userManager) {
        this.frame = frame;
        this.characterManager = characterManager;
        this.wordManager = wordManager;
        this.userManager = userManager;
        this.loginManager = loginManager;

        // ====================== 전체 화면 수직 분할 ======================

        // 수직 구분선
        setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        // 수직 구분선 위치
        setDividerLocation(800);
        // 구분선 고정
        setEnabled(false);

        // ====================== 오른쪽 화면(점수 + 체력바, 아이템 선택 화면) 생성 ======================
        // 점수 패널 생성
        scorePanel = new ScorePanel(characterManager);
        // 체력바 생성
        scorePanel.setHpBar(characterManager.getManHP(), characterManager.getMonsterHP());

        // 단어 관리자에게 점수 패널 주입 (점수 판정용)
        wordManager.setScorePanel(scorePanel);

        // 아이템 선택 패널
        itemPanel = new ItemPanel(frame, scorePanel, wordManager, userManager, loginManager);
        // ====================== 왼쪽 화면(게임 화면) 생성 ======================

        // 게임 화면 패널 생성
        playPanel = new PlayPanel(scorePanel, itemPanel, characterManager, wordManager, loginManager, userManager);

        // ====================== 오른쪽 화면 수평 분할 ======================

        // 오른쪽 화면 분할용
        JSplitPane vPane = new JSplitPane();
        // 수평 구분선
        vPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        // 수평 구분선 위치
        vPane.setDividerLocation(250);
        // 구분선 고정
        vPane.setEnabled(false);
        // 위는 점수 패널
        vPane.setTopComponent(scorePanel);
        // 아래는 편집 패널
        vPane.setBottomComponent(itemPanel);

        // ====================== 오른쪽 화면, 왼쪽 화면 배치 ======================
        // 화면 분할 패널2의 왼쪽 배치
        setLeftComponent(playPanel);
        // 화면 분할 패널1의 오른쪽 배치
        setRightComponent(vPane);
    }
}
