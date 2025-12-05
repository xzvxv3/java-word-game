package ui.game;

import character.type.EnemyType;
import ui.game.left.PlayPanel;
import ui.game.right.EditPanel;
import ui.game.right.ScorePanel;

import javax.swing.*;

public class GamePanel extends JSplitPane {


    private PlayPanel playPanel;
    private ScorePanel scorePanel; // 점수 판넬
    private EditPanel editPanel; // 편집 판넬

    // 게임 전체 화면
    public GamePanel(EnemyType enemyType) {
        scorePanel = new ScorePanel(enemyType);
        editPanel = new EditPanel(scorePanel);

        playPanel = new PlayPanel(scorePanel, editPanel, enemyType);

        setOrientation(JSplitPane.HORIZONTAL_SPLIT); // 수직 구분선
        setDividerLocation(600); // 수직 구분선 위치
        setEnabled(false); // 구분선 고정

        JSplitPane vPane = new JSplitPane(); // 화면 분할 판넬2
        vPane.setOrientation(JSplitPane.VERTICAL_SPLIT); // 수평 구분선
        vPane.setDividerLocation(250); // 수평 구분선 위치
        vPane.setEnabled(false); // 구분선 고정
        vPane.setTopComponent(scorePanel); // 위는 점수 판넬
        vPane.setBottomComponent(editPanel); // 아래는 편집 판넬

        setLeftComponent(playPanel); // 화면 분할 판넬2의 왼쪽 배치
        setRightComponent(vPane); // 화면 분할 판넬1의 오른쪽 배치
    }
}
