package runnable.core;

import data.TextSource;
import data.WordLabel;
import data.WordLabel;

import javax.swing.*;
import java.util.ArrayList;

public class WordMaker implements Runnable{
    // 단어 파일을 불러옴
    private TextSource textSource = new TextSource();

    // 게임 레벨, 게임 레벨에 따라 MAKE_SPEED가 달라짐
    private int level;

    // 단어를 담을 리스트
    private ArrayList<WordLabel> wordList;

    // 부모 Panel
    private JPanel panel;

    // 단어 생성 여부
    private boolean running = true;

    // 단어 생성 시, 단어의 너비와 높이 크기 고정
    private final int HEIGHT_SIZE = 30;
    private final int WIDTH_SIZE = 150;

    public WordMaker(JPanel panel, ArrayList<WordLabel> wordList) {
        this.panel = panel;
        this.wordList = wordList;
    }

    @Override
    public void run() {
        while(running) { // 외부에서 단어 생성
            makeWord(); // 단어 생성
            try {
              Thread.sleep(1000); // 1초마다 생성
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    // 단어 생성 스레드 종료 유도
    public void stopMaking() { running = false; }

    // 단어 생성, 동기화 필요
    public void makeWord() {
        String text = textSource.getNextWord(); // 단어 가져오기

        int panelWidth = panel.getWidth(); // 부모 Panel 너비
        if(panelWidth <= WIDTH_SIZE) return; // 패널 크기 미확정 또는 생성 불가능시 리턴

        int xMin = 0; // 단어가 생성될 수 있는 최소 x 좌표
        int xMax = panelWidth - WIDTH_SIZE; // 단어가 생성될 수 있는 최소 y 좌표

        // 단어 낙하 위치
        int x = (int) (Math.random() * (xMax - xMin)) + xMin;
        int y = 0;

        // 단어 이름, 단어 칸 너비, 단어 칸 높이, 단어 위치 (x, y)
        WordLabel word = new WordLabel(text, WIDTH_SIZE, HEIGHT_SIZE, x, y); // 단어 Panel 생성

        // 배열 리스트의 동기화 충돌 방지
        synchronized (wordList) {
            wordList.add(word);
        }

        // 부모 패널에 단어 생성
        panel.add(word);
    }
}
