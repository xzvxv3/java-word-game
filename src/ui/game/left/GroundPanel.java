package ui.game.left;
import character.type.MotionType;
import manager.CharacterManager;
import manager.WordManager;
import javax.swing.*;
import java.awt.*;

public class GroundPanel extends JPanel {
    // 배경 화면 이미지 경로
    private static final String BG_PATH = "/images/background/ingame/";
    // 배경 화면 이미지를 담을 곳
    ImageIcon stageImage = null;

    // 캐릭터 관리자
    private CharacterManager characterManager;
    // 단어 관리자
    private WordManager wordManager;

    // 게임 실행 화면
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
        switch (characterManager.getMonsterType()) {
            case SCARECROW : stageImage = new ImageIcon(getClass().getResource(BG_PATH + "morning.png")); break;
            case MUSHROOM : stageImage = new ImageIcon(getClass().getResource(BG_PATH + "normalday.png")); break;
            case WOLF : stageImage = new ImageIcon(getClass().getResource(BG_PATH + "night.png")); break;
            case REAPER :  stageImage = new ImageIcon(getClass().getResource(BG_PATH + "night.png")); break;
        }
    }

    // 게임 시작
    public void startGame() {
        // 캐릭터 스레드 시작 (움직일 준비)
        characterManager.gameStart();
        // 주인공 초기 등장 설정 (targetX -> 캐릭터가 멈출 위치, startDelay -> 몇 초 동안 걸어올건지)
        characterManager.getMan().setIntroMove(270, 3000);

        // 몬스터 초기 등장 설정 (targetX -> 캐릭터가 멈출 위치, startDelay -> 몇 초 동안 걸어올건지)
        switch (characterManager.getMonsterType()) {
            // scarecrow는 움직일 필요 없음
            case SCARECROW : characterManager.getEnemy().setMotion(MotionType.IDLE); break;
            case MUSHROOM : characterManager.getEnemy().setIntroMove(260, 3000); break;
            case WOLF : characterManager.getEnemy().setIntroMove(330, 3000); break;
            case REAPER : characterManager.getEnemy().setIntroMove(200, 3000); break;
        }

        // 몇 초 뒤에 단어가 떨어지게 할 건지
        wordManager.gameStart(4000);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 게임 배경 화면
        g.drawImage(stageImage.getImage(), 0, 0, getWidth(), getHeight(), this);

        // 몬스터 이미지
        ImageIcon MonsterImage = characterManager.getEnemy().getCurrentFrame();

        // 몬스터 좌표
        int monsterX = characterManager.getEnemy().getX();
        int monsterY = characterManager.getEnemy().getY();

        // 몬스터 이미지 그리기
        if(MonsterImage != null) {
            switch (characterManager.getMonsterType()) {
                case SCARECROW : g.drawImage(MonsterImage.getImage(), monsterX, monsterY, 230, 120, this); break;
                case MUSHROOM : g.drawImage(MonsterImage.getImage(), monsterX, monsterY, 350, 400, this); break;
                case WOLF : g.drawImage(MonsterImage.getImage(), monsterX, monsterY, 250, 270, this); break;
                case REAPER : g.drawImage(MonsterImage.getImage(), monsterX, monsterY, 340, 250, this); break;
            }
        }

        // 주인공 좌표
        int manX = characterManager.getMan().getX();
        int manY = characterManager.getMan().getY();

        // 주인공 이미지
        ImageIcon manImage = characterManager.getMan().getCurrentFrame();

        // 주인공 그리기
        if(manImage != null) {
            g.drawImage(manImage.getImage(), manX, manY, 200, 200, this);
        }
    }
}