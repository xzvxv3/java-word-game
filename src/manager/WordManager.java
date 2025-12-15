package manager;

import character.type.EnemyType;
import dto.User;
import ui.game.left.GroundPanel;
import ui.game.right.ScorePanel;
import word.TextStore;
import word.Word;
import word.WordStore;
import word.worker.WordFallingTask;
import word.worker.WordMakerTask;

import java.util.Vector;

public class WordManager {

    // 단어 낙하 Runnable
    private WordFallingTask wordFallingTask;
    // 단어 생성 Runable
    private WordMakerTask wordMakerTask;

    // 단어 생성 스레드, 단어 낙하 스레드
    private Thread wordMakerThread, wordFallingThread;

    // 낙하 단어장
    private WordStore wordStore = new WordStore();
    // 텍스트 저장소
    private TextStore textStore = new TextStore();

    // 단어를 낙하시킬 View
    private GroundPanel view;
    // 점수 판정 Panel
    private ScorePanel scorePanel;

    private int wordFallSpeed;

    // 캐릭터 관리자
    private CharacterManager characterManager;
    private UserManager userManager;
    private User user;

    public WordManager(CharacterManager characterManager, UserManager userManager, User user) {
        this.characterManager = characterManager;
        this.userManager = userManager;
        this.user = user;
    }

    public void useReaperSkill() {
        wordFallingTask.useReaperSkill();
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

    public void setPraticeWordFallSpeed(int wordFallSpeed) {
        this.wordFallSpeed = wordFallSpeed;
    }

    public void setWordFallSpeed(EnemyType enemyType) {
        switch (enemyType) {
            case MUSHROOM : this.wordFallSpeed = 170; break;
            case WOLF : this.wordFallSpeed = 140; break;
            case REAPER : this.wordFallSpeed = 110; break;
        }
    }

    // 단어 낙하 Runnable 생성
    private void initWordFallingTask(int startDelay) {
        if (view == null || scorePanel == null) {
            System.out.println("view나 scorepanel null임 다시 확인할것");
            System.exit(0);
        }
        // 단어 낙하 Runnable 생성
        wordFallingTask = new WordFallingTask(startDelay, wordFallSpeed, scorePanel, wordStore, characterManager, view, userManager, user);
    }

    // 단어 생성 Runnable 생성
    private void initWordMakerTask(int startDelay) {
        if(view == null) {
            System.out.println("view가 null임");
            System.exit(0);
        }
        // 단어 생성 Runnable 생성
        wordMakerTask = new WordMakerTask(startDelay, textStore, wordStore, view);
    }

    // 단어 생성 & 낙하 시작
    public void gameStart(int startDelay) {
        initWordFallingTask(startDelay);
        initWordMakerTask(startDelay);
        wordMakerThread = new Thread(wordMakerTask);
        wordFallingThread = new Thread(wordFallingTask);

        wordMakerThread.start();
        wordFallingThread.start();
    }

    public void pauseByItem() {
        System.out.println("클릭");
        wordFallingTask.timeStop();
        wordMakerTask.timeStop();
    }

    // 강제 종료 (뒤로가기 버튼시 활성화)
    public void shutDown() {
        wordMakerThread.interrupt();
        wordFallingThread.interrupt();
        System.out.println("종료완료");
    }
}
