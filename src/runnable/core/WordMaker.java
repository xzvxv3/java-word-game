package runnable.core;

import data.TextSource;
import data.Word;

import javax.swing.*;
import java.util.ArrayList;

public class WordMaker implements Runnable{
    // 단어 파일을 불러옴
    private TextSource textSource = new TextSource();

    // 난이도에 따라 달라지는 단어 생성 속도
    private int makeSpeed;

    // 게임 레벨, 게임 레벨에 따라 MAKE_SPEED가 달라짐
    private int level;

    // 단어를 담을 리스트
    private ArrayList<Word> wordList;

    private JPanel jPanel;

    // 단어 생성 여부
    private boolean isFinish = false;

    public WordMaker(JPanel jPanel, int level, ArrayList<Word> wordList) {
        this.jPanel = jPanel;
        this.level = level;
        this.wordList = wordList;
        setLevel(level); // 난이도 설정
    }

    @Override
    public void run() {
        while(!isFinish) {
            makeWord();
            try {
              Thread.sleep(makeSpeed);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    public void stopMakin() {
        isFinish = true;
    }

    public synchronized void makeWord() {
        String text = textSource.getNextWord();
        int x = (int) (Math.random() * jPanel.getWidth());
        Word word = new Word(text, x, 0);
        wordList.add(word);
    }

    // 임시 속도, 나중에 다시 설정할 것
    public void setLevel(int level) {
        switch (level) {
            case 0:
                makeSpeed = 1000;
                break;
            case 1:
                makeSpeed = 500;
                break;
            case 2:
                makeSpeed = 100;
                break;
        }
    }
}
