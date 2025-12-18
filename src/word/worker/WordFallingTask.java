package word.worker;
import user.User;
import manager.*;
import character.type.MonsterType;
import character.type.MotionType;
import ui.game.right.ScorePanel;
import word.Word;
import word.WordStore;
import ui.game.left.GroundPanel;
import java.util.Iterator;
import java.util.Vector;

// 단어 낙하 클래스
public class WordFallingTask implements Runnable {

    // 게임 화면
    private GroundPanel view = null;
    // 점수 패널
    private ScorePanel scorePanel = null;
    // 단어 저장소
    private WordStore wordStore = null;

    // 캐릭터 관리자
    private CharacterManager characterManager = null;
    // 단어 관리자
    private WordManager wordManager = null;
    // 유저 관리자
    private UserManager userManager = null;
    // 현재 유저
    private User user = null;

    // 단어 저장소
    private Vector<Word> words = null;

    // 스레드 작동 여부
    private volatile boolean isrunning = true;
    // 스킬을 발동했는지
    private volatile boolean isUseSkill = false;
    // Time Stop 아이템 사용 여부
    private volatile boolean isTimeStop = false;

    // 시작 딜레이
    private int startDelay;
    // 단어 낙하 속도
    private int wordFallSpeed;

    // 스킬 발동 예약 시간 (자연스러운 딜레이 유도)
    private long skillScheduledTime = 0;
    // 급낙하가 끝나는 시간 (스킬 전용)
    private long rapidFallEndTime = 0;
    // 원래의 단어 낙하 속도 (스킬 전용)
    private int originalFallSpeed = 0;

    public WordFallingTask(int startDelay, int wordFallSpeed, ScorePanel scorePanel, WordStore wordStore, WordManager wordManager, CharacterManager characterManager, GroundPanel view, UserManager userManager, User user) {
        this.startDelay = startDelay;
        this.wordFallSpeed = wordFallSpeed;
        this.scorePanel = scorePanel;
        this.wordStore = wordStore;
        this.wordManager = wordManager;
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
            // System.out.println("[단어 낙하 스레드 종료]");
            return;
        }

        while(isrunning) {
            checkTimeStop();

            // 스킬 사용중일시
            if(isUseSkill) {
                // 0.5초뒤에 스킬 발생 체크
                checkSkillSchedule();
                // 스킬 종료 되는지 체크
                checkRapidFallTime();
            }

            // 단어 낙하
            fallWords();

            // 단어 낙하 속도
            try { Thread.sleep(wordFallSpeed); } catch (InterruptedException e) {
                // System.out.println("[단어 낙하 스레드 종료]");
                return;
            }
        }
        // System.out.println("[단어 낙하 스레드 종료]");
    }

    // 0.5초뒤에 스킬 발생 체크
    private void checkSkillSchedule() {
        // 예약된 시간이 있고, 현재 시간이 그 시간을 지났다면
        if (skillScheduledTime > 0 && System.currentTimeMillis() >= skillScheduledTime) {
            rapidFall(); // 급낙하 시작
            skillScheduledTime = 0; // 예약 초기화 (중복 실행 방지)
        }
    }

    // 0.5초뒤에 스킬 발생 (자연스러운 딜레이 유도)
    public void useReaperSkill() {
        if(isTimeStop) return;
        isUseSkill = true;
        skillScheduledTime = System.currentTimeMillis() + 500;
    }

    // 단어 급낙하 발동
    public void rapidFall() {
        // 원래 속도 백업
        originalFallSpeed = wordFallSpeed;

        // 단어 낙하 속도 변경
        wordFallSpeed = 20;

        // 스킬 지속시간 (0.2초)
        rapidFallEndTime = System.currentTimeMillis() + 200;
    }

    // 스킬 종료 되는지 체크
    private void checkRapidFallTime() {
        // 급낙하 종료 조건
        if(rapidFallEndTime > 0 && System.currentTimeMillis() > rapidFallEndTime) {
            // 단어 낙하 속도를 원래대로 복구
            wordFallSpeed = originalFallSpeed;

            // 종료 시간 초기화
            rapidFallEndTime = 0;

            // 타임 스탑 아이템 종료
            isUseSkill = false;
            System.out.println("[리퍼 스킬 종료]");
        }
    }

    // 타임 스탑 아이템 사용했는지
    private void checkTimeStop() {
        if (isTimeStop) {
            try {
                System.out.println("[Time Stop 아이템 사용]");
                // 3초간 단어 낙하 중단
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                // System.out.println("[단어 낙하 중 일시 정지]");
                isrunning = false;
                return;
            }

            // 타임 스탑 아이템 종료
            isTimeStop = false;
            System.out.println("[Time Stop 아이템 종료]");
        }
    }

    // 아이템 사용 -> 단어 낙하 일시 중지
    public void timeStop() {
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
                // 단어 라벨 추출
                Word word = it.next();
                // 단어 라벨 떨어트림
                word.fall();

                // 화면 재갱신
                view.repaint();

                // 단어 라벨 충돌 판정
                if(word.isAtBottom()) {
                    // 몬스터의 공격
                    monsterAttack();
                    // 화면에서 단어 삭제
                    view.remove(word);
                    // 단어 완전 제거
                    it.remove();

                    System.out.println("[" + characterManager.getMonsterType() + "] 공격");
                }
            }
        }

        // 게임 종료 되는지 체크
        if(characterManager.isGameOver()) {
            // 게임 종료
            gameOver();
        }
    }

    // 몬스터의 공격
    private void monsterAttack() {
        switch (characterManager.getMonsterType()) {
            case MonsterType.MUSHROOM:
                SoundManager.getAudio().play("resources/sounds/mushroom_attack.wav");
                break;
            case MonsterType.WOLF:
                SoundManager.getAudio().play("resources/sounds/wolf_attack.wav");
                break;
            case MonsterType.REAPER:
                SoundManager.getAudio().play("resources/sounds/reaper_attack.wav");
                break;
        }

        // Mushroom, Wolf 공격
        if (characterManager.getMonsterType() == MonsterType.MUSHROOM || characterManager.getMonsterType() == MonsterType.WOLF) {
            characterManager.getEnemy().setMotion(MotionType.ENEMY_ATTACK01);
            if (characterManager.getMonsterType() == MonsterType.MUSHROOM) {
                scorePanel.decreaseManHP(characterManager.getMonsterAttackPower(characterManager.getMonsterType()));
                characterManager.decreaseManHP(characterManager.getMonsterAttackPower(characterManager.getMonsterType()));
            } else if (characterManager.getMonsterType() == MonsterType.WOLF) {
                scorePanel.decreaseManHP(characterManager.getMonsterAttackPower(characterManager.getMonsterType()));
                characterManager.decreaseManHP(characterManager.getMonsterAttackPower(characterManager.getMonsterType()));
            }
        }

        // Reaper 공격
        if (characterManager.getMonsterType() == MonsterType.REAPER) {
            characterManager.getEnemy().setMotion(MotionType.ENEMY_ATTACK01);
            scorePanel.decreaseManHP(characterManager.getMonsterAttackPower(characterManager.getMonsterType()));
            characterManager.decreaseManHP(characterManager.getMonsterAttackPower(characterManager.getMonsterType()));
        }

        // 허수아비를 제외한 나머지 몬스터의 공격은 Man의 데미지 모션 유도
        if (characterManager.getMonsterType() != MonsterType.SCARECROW) {
            characterManager.getMan().onAttacked();
        }
    }

    // 게임 종료
    private void gameOver() {
        // 단어 관련 스레드 모두 중단 유도
        wordManager.shutDown();

        // 캐릭터 스레드 종료 유도 (자연스러운 종료 위해, interrupt() 사용X)
        if(characterManager.getCurrentMonsterHP() <= 0) characterManager.getMan().stop();
        else if(characterManager.getCurrentManHP() <= 0) characterManager.getEnemy().stop();

        // 모드에 따른 현재 유저의 랭킹 갱신 (words 파일인 경우에만 점수를 갱신)
        if(SettingsManager.getInstance().getCurrentWordBookPath().equals("resources/words/words.txt")) {
            // 유저 점수 갱신
            user.updateCurrentScore(characterManager.getMonsterType(), user.getCurrentScore());
            // 랭킹에 업데이트
            userManager.updateUser(user);
        }
    }
}
