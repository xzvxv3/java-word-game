package word.worker;

import manager.CharacterManager;
import character.type.EnemyType;
import character.type.MotionType;
import ui.game.right.ScorePanel;
import word.Word;
import word.WordStore;
import ui.game.left.GroundPanel;

import java.util.Iterator;
import java.util.Vector;

public class WordFallingTask implements Runnable {

    // 게임 화면
    private GroundPanel view;

    // 단어 관리 클래스
    private WordStore wordStore;

    // 캐릭터 관리 클래스
    private CharacterManager characterManager;

    // 단어 저장소
    private Vector<Word> words;

    // 스레드 작동 여부
    private boolean running = true;

    private ScorePanel scorePanel;

    public WordFallingTask(ScorePanel scorePanel, WordStore wordStore, CharacterManager characterManager, GroundPanel view) {
        this.scorePanel = scorePanel;
        this.wordStore = wordStore;
        this.view = view;
        this.characterManager = characterManager;
        words = wordStore.getWordVector();
    }

    @Override
    public void run() {
        while(running) {
            fallWords();
            try {
                Thread.sleep(150);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // 단어 낙하 시작
    private void fallWords() {
        // 배열 리스트의 동기화 충돌 방지
        synchronized (words) {
            // 배열 리스트에 있는 단어 라벨들의 반복자 생성
            Iterator<Word> it = words.iterator();

            // 단어 라벨 순회
            while(it.hasNext()) {
                Word word = it.next(); // 단어 라벨 추출
                word.fall(); // 단어 라벨 떨어트림
                view.repaint(); // 화면 울렁거림 방지

                // 단어 라벨 충돌 판정
                if(word.isAtBottom()) {

                    // Mushroom, Wolf 공격
                    if(characterManager.getEnemyType() == EnemyType.MUSHROOM || characterManager.getEnemyType() == EnemyType.WOLF) {
                        characterManager.getEnemy().setMotion(MotionType.ENEMY_ATTACK01);

                        if(characterManager.getEnemyType() == EnemyType.MUSHROOM) {
                            scorePanel.decreaseManHP(5);
                            characterManager.decreaseManHP(5);
                        }

                        else if(characterManager.getEnemyType() == EnemyType.WOLF) {
                            scorePanel.decreaseManHP(10);
                            characterManager.decreaseManHP(10);
                        }
                    }

                    // Reaper 공격
                    if(characterManager.getEnemyType() == EnemyType.REAPER) {
                        int r = (int) (Math.random() * 2);
                        switch (r) {
                            case 0 : characterManager.getEnemy().setMotion(MotionType.ENEMY_ATTACK01); break;
                            case 1 : characterManager.getEnemy().setMotion(MotionType.ENEMY_SKILL01); break;
                        }
                        scorePanel.decreaseManHP(20);
                        characterManager.decreaseManHP(20);
                    }

                    if(characterManager.getEnemyType() != EnemyType.SCARECROW) {
                        characterManager.getMan().decreaseHP(1);
                        characterManager.getMan().onAttacked();
                    }

                    view.remove(word); // 화면에서 단어 삭제
                    it.remove(); // 단어 완전 제거

                    System.out.println(characterManager.getEnemyType() + " 공격");

                    // 게임 종료 되는지
                    if(characterManager.isGameOver()) {
                        running = false;
                        if(characterManager.getEnemyHP() <= 0) characterManager.getMan().stop();
                        else if(characterManager.getManHP() <= 0) characterManager.getEnemy().stop();
                    }
                }
            }
        }
    }
}
