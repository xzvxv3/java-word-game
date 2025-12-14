package word.worker;

import dto.User;
import manager.CharacterManager;
import character.type.EnemyType;
import character.type.MotionType;
import manager.UserManager;
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
    private CharacterManager characterManager = null;

    private UserManager userManager = null;
    private User user = null;

    // 단어 저장소
    private Vector<Word> words;

    // 스레드 작동 여부
    private boolean running = true;

    private ScorePanel scorePanel;

    private int startDelay;

    private int wordFallSpeed;

    public WordFallingTask(int startDelay, int wordFallSpeed, ScorePanel scorePanel, WordStore wordStore, CharacterManager characterManager, GroundPanel view, UserManager userManager, User user) {
        this.startDelay = startDelay;
        this.wordFallSpeed = wordFallSpeed;
        this.scorePanel = scorePanel;
        this.wordStore = wordStore;
        this.view = view;
        this.characterManager = characterManager;
        this.user = user;
        this.userManager = userManager;

        words = wordStore.getWordVector();
    }

    @Override
    public void run() {

        // startDelay초 후, 단어가 생성되기 시작
        try { Thread.sleep(startDelay); } catch (InterruptedException e) { throw new RuntimeException(e); }

        while(running) {
            checkTimeStop();

            fallWords();

            // 80 -> 매우 빠름, 110 -> 빠름, 140 -> 보통, 170 -> 쉬움

            // 단어 낙하 속도
            try { Thread.sleep(wordFallSpeed); } catch (InterruptedException e) { throw new RuntimeException(e); }
        }

        System.out.println("[단어 낙하 스레드 종료]");
    }

//    public void setPraticeWordFallSpeed(int fallingSpeed) {
//        this.wordFallSpeed = fallingSpeed;
//    }
//
//    public void setWordFallSpeed(EnemyType enemyType) {
//        switch (enemyType) {
//            case MUSHROOM : this.fallingSpeed = 170; break;
//            case WOLF : this.fallingSpeed = 140; break;
//            case REAPER : this.fallingSpeed = 110; break;
//        }
//    }

    // 강제 종료 (뒤로가기 버튼시 활성화)
    public void shutDown() {
        running = false;
    }

    private boolean isTimeStop = false;

    private synchronized void checkTimeStop() {
        if (isTimeStop) {
            try {
                System.out.println("⏳ 3초간 멈춤...");
                wait(3000); // 3초 대기 (Lock 반납하고 잠듦)
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 3초 지나면 자동으로 코드가 여기로 내려옴
            isTimeStop = false; // 플래그 끄기 (다시 움직임)
            System.out.println("⏰ 3초 끝! 다시 시작");
        }
    }

    // [추가] 외부(Manager)에서 호출하는 버튼
    public synchronized void timeStop() {
        isTimeStop = true;
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
                        if(characterManager.getCurrentEnemyHP() <= 0) characterManager.getMan().stop();
                        else if(characterManager.getCurrentManHP() <= 0) characterManager.getEnemy().stop();

                        // 모드에 따른 현재 유저의 점수 갱신
                        user.updateCurrentScore(characterManager.getEnemyType(), user.getCurrentScore());

                        // 랭킹에 업데이트
                        userManager.updateUser(user);
                    }
                }
            }
        }
    }
}
