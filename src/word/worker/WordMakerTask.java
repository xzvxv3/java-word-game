package word.worker;
import word.TextStore;
import word.Word;
import word.WordStore;
import ui.game.left.GroundPanel;

// 단어 생성 클래스
public class WordMakerTask implements Runnable{
    // 텍스트 저장소
    private TextStore textStore = null;

    // 단어 저장소
    private WordStore wordStore = null;

    // 게임 실행 화면
    private GroundPanel view = null;

    // 단어 생성 시, 단어의 너비와 높이 크기 고정
    private final int HEIGHT_SIZE = 30;
    private final int WIDTH_SIZE = 150;

    // 시작 딜레이
    private int startDelay;

    // 스레드 작동 여부
    private volatile boolean running = true;

    // 타입 스탑 여부
    private volatile boolean isTimeStop = false;

    public WordMakerTask(int startDelay, TextStore textStore, WordStore wordStore, GroundPanel view) {
        this.startDelay = startDelay;
        this.textStore = textStore;
        this.wordStore = wordStore;
        this.view = view;
    }

    @Override
    public void run() {
        // startDelay초 후, 단어가 생성되기 시작
        try { Thread.sleep(startDelay); } catch (InterruptedException e) {
            // System.out.println("[단어 생성 스레드 종료]");
            return;
        }

        while(running) {
            // 타입 스탑 활성됐는지 체크
            checkTimeStop();
            // 단어 생성
            makeWord();

            // 단어 생성 딜레이
            try { Thread.sleep(1650); } catch (InterruptedException e) {
                // System.out.println("[단어 생성 스레드 종료]");
                return;
            }
        }
        // System.out.println("[단어 생성 스레드 종료]");
    }

    // 타임 스탑 활성됐는지
    private void checkTimeStop() {
        if (isTimeStop) {
            try {
                Thread.sleep(3000); // 3초 대기
            } catch (InterruptedException e) {
                System.out.println("[단어 생성 중 일시 정지]");
                running = false;
            }
            // 타임 스탑 아이템 종료
            isTimeStop = false;
        }
    }

    // 타임 스탑 유도
    public void timeStop() {
        isTimeStop = true;
    }

    // 단어 생성, 동기화 필요
    public void makeWord() {
        // 단어 가져오기
        String text = textStore.getText();

        // 게임 실행 화면 너비
        int groundPanelWidth = view.getWidth();
        // 패널 크기 미확정 또는 생성 불가능시 리턴
        if(groundPanelWidth <= WIDTH_SIZE) return;

        // 단어가 생성될 수 있는 최소 x 좌표
        int xMin = 0;
        // 단어가 생성될 수 있는 최소 y 좌표
        int xMax = groundPanelWidth - WIDTH_SIZE;

        // 단어 시작 위치
        int x = (int) (Math.random() * (xMax - xMin)) + xMin;
        int y = 0;

        // 단어 이름, 단어 칸 너비, 단어 칸 높이, 단어 위치 (x, y)
        Word word = new Word(text, view.getHeight(), WIDTH_SIZE, HEIGHT_SIZE, x, y); // 단어 Panel 생성

        // wordStore에 단어 저장
        synchronized (wordStore) {
            wordStore.addWord(word);
        }

        // 단어를 화면에 추가
        view.add(word);
    }
}
