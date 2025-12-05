package ui.game.left;

import manager.CharacterManager;
import character.type.EnemyType;
import ui.game.right.ScorePanel;
import word.TextStore;
import word.WordStore;
import word.worker.WordFallingTask;
import word.worker.WordMakerTask;

import javax.swing.*;
import java.awt.*;

// 게임 실행 화면
public class GroundPanel extends JPanel {

    private ImageIcon stageImage01 = new ImageIcon("resources/images/background/stage/morning.png");
    private ImageIcon stageImage02 = new ImageIcon("resources/images/background/stage/normalday.png");
    private ImageIcon stageImage03 = new ImageIcon("resources/images/background/stage/night.png");
    private ImageIcon stageImage04 = new ImageIcon("resources/images/background/stage/winter.png");
    ImageIcon stageImage = null;

    // 캐릭터 관리 클래스
    public CharacterManager characterManager = new CharacterManager(this);

    // 텍스트 관리 클래스
    private TextStore textStore = new TextStore();

    // 단어 관리 클래스
    private WordStore wordStore;

    // 단어 생성 Runnable & Thread
    private WordMakerTask wordMakerTask;
    private Thread wordMakerThread;

    // 단어 낙하 Runnable & Thread
    private WordFallingTask wordFallingTask;
    private Thread fallingThread;

    private ScorePanel scorePanel;

    ImageIcon manImage, enemyImage;

    public GroundPanel(ScorePanel scorePanel, EnemyType enemyType, WordStore wordStore) {
        this.scorePanel = scorePanel;

        this.setBackground(Color.WHITE);
        this.setLayout(null);

        // 1. 적 타입 초기화
        characterManager.setEnemyType(enemyType);
        scorePanel.setHpBar(characterManager); // 캐릭터들의 체력바 설정

        // 2. wordStore 초기화
        this.wordStore = wordStore;

        // 3. wordStore 객체가 할당된 후에 Task 객체들을 생성하고 초기화
        this.wordMakerTask = new WordMakerTask(textStore, wordStore, this);
        this.wordMakerThread = new Thread(wordMakerTask);

        this.wordFallingTask = new WordFallingTask(scorePanel, wordStore, characterManager, this);
        this.fallingThread = new Thread(wordFallingTask);

        start();
    }

    public CharacterManager getCharacterManager() {
        return characterManager;
    }

    // start() 호출 ->  단어 생성 & 낙하 시작
    public void start() {
        // Man 스레드
        Thread manThread = new Thread(characterManager.getManTask());
        // Enemy 스레드
        Thread enemyThread = new Thread(characterManager.getEnemyTask());

        switch (characterManager.getEnemyType()) {
            case SCARECROW : stageImage = stageImage01; break;
            case MUSHROOM : stageImage = stageImage02; break;
            case WOLF : stageImage = stageImage03; break;
            case REAPER :  stageImage = stageImage04; break;
        }

        wordMakerThread.start();
        fallingThread.start();
        manThread.start();
        enemyThread.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(stageImage.getImage(), 0, 0, getWidth(), getHeight(), this);

        enemyImage = characterManager.getEnemy().getCurrentFrame();
        if(enemyImage != null) {
            switch (characterManager.getEnemyType()) {
                case SCARECROW : g.drawImage(enemyImage.getImage(), 215, 470, 230, 140, this); break;
                case MUSHROOM : g.drawImage(enemyImage.getImage(), 158, 340, 350, 400, this); break;
                case WOLF : g.drawImage(enemyImage.getImage(), 230, 415, 250, 270, this); break;
                case REAPER : g.drawImage(enemyImage.getImage(), 110, 365, 310, 250, this); break;
            }
        }

        manImage = characterManager.getMan().getCurrentFrame();
        if(manImage != null) {
            g.drawImage(manImage.getImage(), 165, 410, 200, 200, this);
        }
    }
}