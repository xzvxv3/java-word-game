package screen.play;

import data.WordLabel;

import runnable.core.ManMotion;
import runnable.core.WolfMotion;
import runnable.core.WordFallManager;
import runnable.core.WordMaker;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameGroundPanel extends JPanel {
    private ImageIcon ground = new ImageIcon("resources/background/ground.png");

    private ManMotion manMotion;
    private WolfMotion wolfMotion;
    private WordMaker wordRain;
    private WordFallManager wordFallManager;

    // 스레드
    private Thread manThread, wolfThread, wordMakerThread, wordFallManagerThread;

    // 떨어지는 단어들을 저장할 리스트. synchronized로 동기화 걸 것
    private ArrayList<WordLabel> wordList = new ArrayList<>();

    // 게임 종료
    private boolean isFinish = false;

    public GameGroundPanel() {
        setLayout(null);

        // 단어 입력 버튼
        JTextField input = new JTextField(30);
        input.setLocation(250, 720);
        input.setSize(150, 30);
        add(input);

        // Man 모션 스레드 시작
        manMotionStart();

        // Wolf 모션 스레드 시작
        wolfMotionStart();

        // 단어 생성 스레드 시작
        wordMakerStart();

        // 단어 관리자 스레드 시작
        wordFallManagerStart();
    }

    // 단어 생성자 스레드
    private void wordMakerStart() {
        // 레벨은 임시로 0 설정. 추후에 변경할 것
        WordMaker wordMaker = new WordMaker(this, wordList);
        wordMakerThread = new Thread(wordMaker);
        wordMakerThread.start();
    }

    // 단어 관리자 스레드
    private void wordFallManagerStart() {
        WordFallManager wordFallManager = new WordFallManager(this, wordList, 1);
        wordFallManagerThread = new Thread(wordFallManager);
        wordFallManagerThread.start();
    }

    // Man 모션
    private void manMotionStart() {
        manMotion = new ManMotion(this);
        manThread = new Thread(manMotion);
        manThread.start();
    }

    // Wolf 모션
    private void wolfMotionStart() {
        wolfMotion = new WolfMotion(this);
        wolfThread = new Thread(wolfMotion);
        wolfThread.start();
    }


    @Override
    protected void paintComponent(Graphics g) { // 배경 요소
        super.paintComponent(g);
        g.drawImage(ground.getImage(), 0, 0, getWidth(), getHeight(), this);

        ImageIcon ManImage = manMotion.getCurrentFrame();
        g.drawImage(ManImage.getImage(), 230, 530, 170, 170, this);

        ImageIcon WolfImage = wolfMotion.getCurrentFrame();
        g.drawImage(WolfImage.getImage(), 300, 515, 250, 250, this);
    }
}