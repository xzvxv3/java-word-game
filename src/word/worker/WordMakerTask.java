package word.worker;

import word.TextStore;
import word.Word;
import word.WordStore;
import ui.game.left.GroundPanel;

// 단어 생성 Runnable
public class WordMakerTask implements Runnable{

    // 텍스트 저장소
    private TextStore textStore;

    // 단어 저장소
    private WordStore wordStore;

    // 스레드 작동 여부
    private boolean running = true;

    // 게임 실행 화면
    private GroundPanel view;

    // 단어 생성 시, 단어의 너비와 높이 크기 고정
    private final int HEIGHT_SIZE = 30;
    private final int WIDTH_SIZE = 150;

    private int startDelay;

    public WordMakerTask(int startDelay, TextStore textStore, WordStore wordStore, GroundPanel view) {
        this.startDelay = startDelay;
        this.textStore = textStore;
        this.wordStore = wordStore;
        this.view = view;
    }

    @Override
    public void run() {

        // startDelay초 후, 단어가 생성되기 시작
        try { Thread.sleep(startDelay); } catch (InterruptedException e) { throw new RuntimeException(e); }

        while(running) {

            checkTimeStop();
            // 단어 생성
            makeWord();

            // 단어 생성 딜레이
            try { Thread.sleep(2000); } catch (InterruptedException e) { throw new RuntimeException(e); }
        }

        System.out.println("[단어 생성 스레드 종료]");
    }

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

    // 단어 생성, 동기화 필요
    public void makeWord() {
        String text = textStore.getText(); // 단어 가져오기

        int groundPanelWidth = view.getWidth(); // 게임 실행 화면 너비
        if(groundPanelWidth <= WIDTH_SIZE) return; // 패널 크기 미확정 또는 생성 불가능시 리턴

        int xMin = 0; // 단어가 생성될 수 있는 최소 x 좌표
        int xMax = groundPanelWidth - WIDTH_SIZE; // 단어가 생성될 수 있는 최소 y 좌표

        // 단어 시작 위치
        int x = (int) (Math.random() * (xMax - xMin)) + xMin;
        int y = 0;

        // 단어 이름, 단어 칸 너비, 단어 칸 높이, 단어 위치 (x, y)
        Word word = new Word(text, view.getHeight(), WIDTH_SIZE, HEIGHT_SIZE, x, y); // 단어 Panel 생성

        synchronized (wordStore) {
            wordStore.addWord(word);
        }

        // 단어를 화면에 추가
        view.add(word);
    }
}
