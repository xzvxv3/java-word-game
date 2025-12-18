package manager;
import character.type.MonsterType;
import user.User;
import ui.game.left.GroundPanel;
import ui.game.right.ScorePanel;
import word.TextStore;
import word.Word;
import word.WordStore;
import word.worker.WordFallingTask;
import word.worker.WordMakerTask;
import java.util.Vector;

// 단어 관리자
public class WordManager {
    // 단어 낙하 Runnable
    private WordFallingTask wordFallingTask = null;
    // 단어 생성 Runable
    private WordMakerTask wordMakerTask = null;

    // 단어 생성 스레드, 단어 낙하 스레드
    private Thread wordMakerThread = null, wordFallingThread = null;

    // 낙하 단어장
    private WordStore wordStore = new WordStore();
    // 텍스트 저장소
    private TextStore textStore = new TextStore();

    // 단어를 낙하시킬 View
    private GroundPanel view;
    // 점수 판정 Panel
    private ScorePanel scorePanel;

    // 단어 낙하 속도
    private int wordFallSpeed;

    // 캐릭터 관리자
    private CharacterManager characterManager;
    // 유저 관리자
    private UserManager userManager;
    // 현재 유저
    private User user;

    // 모드에 따른 단어 낙하 속도
    private int mushroomMode = 170, wolfMode = 140, reaperMode = 110;

    // 단어 관리자
    public WordManager(CharacterManager characterManager, UserManager userManager, User user) {
        this.characterManager = characterManager;
        this.userManager = userManager;
        this.user = user;
    }

    // 낙하 단어장 반환
    public Vector<Word> getWordVector() {
        return wordStore.getWordVector();
    }

    // View 설정
    public void setView(GroundPanel view) {
        this.view = view;
    }

    // 점수 판정 Panel 설정
    public void setScorePanel(ScorePanel scorePanel) {
        this.scorePanel = scorePanel;
    }

    // 단어 제거
    public void removeWord(Word word) {
        view.remove(word);
        view.revalidate();
    }

    // 연습 모드 단어 속도 설정
    public void setPraticeWordFallSpeed(int wordFallSpeed) {
        this.wordFallSpeed = wordFallSpeed;
    }

    // 모드에 따른 단어 낙하 속도 설정
    public void setWordFallSpeed(MonsterType monsterType) {
        switch (monsterType) {
            case MUSHROOM : this.wordFallSpeed = mushroomMode; break;
            case WOLF : this.wordFallSpeed = wolfMode; break;
            case REAPER : this.wordFallSpeed = reaperMode; break;
        }
    }

    // 단어 낙하 Runnable 생성
    private void initWordFallingTask(int startDelay) {
        wordFallingTask = new WordFallingTask(startDelay, wordFallSpeed, scorePanel, wordStore, this, characterManager, view, userManager, user);
    }

    // 단어 생성 Runnable 생성
    private void initWordMakerTask(int startDelay) {
        wordMakerTask = new WordMakerTask(startDelay, textStore, wordStore, view);
    }

    // 단어 생성 & 낙하 시작
    public void gameStart(int startDelay) {
        // 단어 낙하 Runnable 생성
        initWordFallingTask(startDelay);
        // 단어 생성 Runnable 생성
        initWordMakerTask(startDelay);

        // 단어 생성 스레드 생성
        wordMakerThread = new Thread(wordMakerTask);
        // 단어 낙하 스레드 생성
        wordFallingThread = new Thread(wordFallingTask);

        // 단어 생성, 낙하 스레드 시작
        wordMakerThread.start();
        wordFallingThread.start();
    }

    // 타임 스탑 아이템 사용 -> 단어 일시 정지
    public void pauseByItem() {
        wordFallingTask.timeStop();
        wordMakerTask.timeStop();
    }

    // 강제 종료 (뒤로가기 버튼시 활성화)
    public void shutDown() {
        wordMakerThread.interrupt();
        wordFallingThread.interrupt();
        System.out.println("[단어 스레드 종료]");
    }

    // Reaper 스킬 사용
    public void useReaperSkill() {
        wordFallingTask.useReaperSkill();
    }
}
