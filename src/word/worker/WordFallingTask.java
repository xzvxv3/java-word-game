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
        try { Thread.sleep(startDelay); } catch (InterruptedException e) {
            System.out.println("[단어 낙하 스레드 종료]");
            return;
        }

        while(running) {
            checkTimeStop();

            // 스킬 사용중일시
            if(isUseSkill) {
                checkSkillSchedule();
                checkRapidFallTime();
            }

            fallWords();

            // 단어 낙하 속도
            try { Thread.sleep(wordFallSpeed); } catch (InterruptedException e) {
                System.out.println("[단어 낙하 스레드 종료]");
                return;
            }
        }

        System.out.println("[단어 낙하 스레드 종료]");
    }
    // 스킬 발동 예약 시간 (자연스러운 딜레이 유도)
    private long skillScheduledTime = 0;

    // [추가] 예약된 시간이 되면 급낙하 실행
    private void checkSkillSchedule() {
        // 예약된 시간이 있고(>0), 현재 시간이 그 시간을 지났다면
        if (skillScheduledTime > 0 && System.currentTimeMillis() >= skillScheduledTime) {
            rapidFall(); // 급낙하 시작!
            skillScheduledTime = 0; // 예약 초기화 (중복 실행 방지)
        }
    }

    private boolean isUseSkill = false;

    // 0.5초뒤에 스킬 발생 (자연스러운 딜레이 유도)
    public void useReaperSkill() {
        if(isTimeStop) return;
        isUseSkill = true;
        skillScheduledTime = System.currentTimeMillis() + 500;
    }

    // 급낙하가 끝나는 시간
    private long rapidFallEndTime = 0;

    // 원래의 단어 낙하 속도
    private int originalFallSpeed = 0;

    // 단어 급낙하 발동
    public void rapidFall() {
        // 원래 속도 백업
        originalFallSpeed = wordFallSpeed;

        // 단어 낙하 속도 변경
        wordFallSpeed = 20;

        // 스킬 지속시간 (0.2초)
        rapidFallEndTime = System.currentTimeMillis() + 200;
    }

    // 단어 급낙하 종료
    private void checkRapidFallTime() {
        if(rapidFallEndTime > 0 && System.currentTimeMillis() > rapidFallEndTime) {
            wordFallSpeed = originalFallSpeed; // 단어 속도 복수

            rapidFallEndTime = 0;

            isUseSkill = false;
            System.out.println("[리퍼 스킬 종료]");
        }
    }

    private boolean isTimeStop = false;

    private synchronized void checkTimeStop() {
        if (isTimeStop) {
            try {
                System.out.println("⏳ 3초간 멈춤...");
                wait(3000); // 3초 대기 (Lock 반납하고 잠듦)
            } catch (InterruptedException e) {
                System.out.println("[단어 낙하 중 일시 정지]");
                running = false;
                return;
            }
            // 3초 지나면 자동으로 코드가 여기로 내려옴
            isTimeStop = false; // 플래그 끄기 (다시 움직임)
            System.out.println("⏰ 3초 끝! 다시 시작");
        }
    }

    // 아이템 사용 -> 단어 낙하 일시 중지
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
