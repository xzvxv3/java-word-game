package screen.play;

import data.TextSource;
import data.Word;
import runnable.core.Man;
import runnable.core.Wolf;
import runnable.core.WordMaker;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameGroundPanel extends JPanel {
    private ImageIcon ground = new ImageIcon("resources/background/ground.png");

    private Man man;
    private Wolf wolf;
    private WordMaker wordRain;

    private Thread manThread;
    private Thread wolfThread;
    private Thread wordMakerThread;

    // 떨어지는 단어들을 저장할 리스트. synchronized로 동기화 걸 것
    private ArrayList<Word> wordList = new ArrayList<>();

    // 게임 종료
    private boolean isFinish = false;



    public GameGroundPanel() {
        setLayout(null);


        // 단어 입력 버튼
        JTextField input = new JTextField(30);
        input.setLocation(250, 720);
        input.setSize(150, 30);
        add(input);


        // idle 모션
        motionStart();
    }

    // 단어 생성자 스레드
    private void wordMakerStart() {
        // 레벨은 임시로 0 설정. 추후에 변경할 것
        WordMaker wordMaker = new WordMaker(this,0, wordList);
        wordMakerThread = new Thread(wordMaker);
        wordMakerThread.start();
    }

    // 단어 관리자 스레드


    private void motionStart() {
        // RunMan 모션
        man = new Man(this);
        // SwordMan 모션
        wolf = new Wolf(this);

        manThread = new Thread(man);
        wolfThread = new Thread(wolf);

        manThread.start();
        wolfThread.start();
    }

    @Override
    protected void paintComponent(Graphics g) { // 배경 요소
        super.paintComponent(g);
        g.drawImage(ground.getImage(), 0, 0, getWidth(), getHeight(), this);

        ImageIcon ManImage = man.getCurrentFrame();
        g.drawImage(ManImage.getImage(), 230, 530, 170, 170, this);

        ImageIcon WolfImage = wolf.getCurrentFrame();
        g.drawImage(WolfImage.getImage(), 300, 515, 250, 250, this);
    }
}