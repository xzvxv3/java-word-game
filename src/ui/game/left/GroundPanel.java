package ui.game.left;

import manager.CharacterManager;
import manager.WordManager;
import javax.swing.*;
import java.awt.*;

// 게임 실행 화면
public class GroundPanel extends JPanel {

    private static final String BG_PATH = "resources/images/background/ingame/";
    private ImageIcon stageImage01 = new ImageIcon(BG_PATH + "morning.png");
    private ImageIcon stageImage02 = new ImageIcon(BG_PATH + "normalday.png");
    private ImageIcon stageImage03 = new ImageIcon(BG_PATH + "night.png");
    private ImageIcon stageImage04 = new ImageIcon(BG_PATH + "winter.png");
    ImageIcon stageImage = null;

    ImageIcon manImage, enemyImage;

    private CharacterManager characterManager;
    private WordManager wordManager;

    public GroundPanel(CharacterManager characterManager, WordManager wordManager) {
        this.setLayout(null);
        this.characterManager = characterManager;
        this.wordManager = wordManager;

        // 단어 관리자, 캐릭터 관리자에게 View을 넘김
        wordManager.setView(this);
        characterManager.setView(this);

        // 모드에 따른 배경화면 설정
        setModeScene();

        // 게임 시작
        startGame();
    }

    // 모드에 따른 배경화면 설정
    private void setModeScene() {
        switch (characterManager.getEnemyType()) {
            case SCARECROW : stageImage = stageImage01; break;
            case MUSHROOM : stageImage = stageImage02; break;
            case WOLF : stageImage = stageImage03; break;
            case REAPER :  stageImage = stageImage04; break;
        }
    }

    public void startGame() {
        // 1. 캐릭터 스레드 시작 (움직일 준비)
        characterManager.gameStart();

        // Man 초기 등장 설정
        characterManager.getMan().setIntroMove(270, 3000);

        // Enemy 초기 등장 설정
        switch (characterManager.getEnemyType()) {
            case SCARECROW : characterManager.getEnemy().setIntroMove(260, 3000); break;
            case MUSHROOM : characterManager.getEnemy().setIntroMove(260, 3000); break;
            case WOLF : characterManager.getEnemy().setIntroMove(330, 3000); break;
            case REAPER : characterManager.getEnemy().setIntroMove(200, 3000); break;
        }

        wordManager.gameStart(4000);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(stageImage.getImage(), 0, 0, getWidth(), getHeight(), this);

        enemyImage = characterManager.getEnemy().getCurrentFrame();

        // 몬스터 좌표
        int monsterX = characterManager.getEnemy().getX();
        int monsterY = characterManager.getEnemy().getY();

        if(enemyImage != null) {
            switch (characterManager.getEnemyType()) {
                case SCARECROW : g.drawImage(enemyImage.getImage(), monsterX, monsterY, 230, 120, this); break;
                case MUSHROOM : g.drawImage(enemyImage.getImage(), monsterX, monsterY, 350, 400, this); break;
                case WOLF : g.drawImage(enemyImage.getImage(), monsterX, monsterY, 250, 270, this); break;
                case REAPER : g.drawImage(enemyImage.getImage(), monsterX, monsterY, 340, 250, this); break;
            }
        }

        int manX = characterManager.getMan().getX();
        int manY = characterManager.getMan().getY();

        manImage = characterManager.getMan().getCurrentFrame();
        if(manImage != null) {
            g.drawImage(manImage.getImage(), manX, manY, 200, 200, this);
        }
    }
}