package screen.play;

import data.WordLabel;

// import runnable.InputTextManager;
import runnable.core.ManMotion;
import runnable.core.WolfMotion;
import runnable.core.WordFallManager;
import runnable.core.WordMaker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

public class GameGroundPanel extends JPanel {
    private ImageIcon ground = new ImageIcon("resources/background/ground.png");

    private ManMotion manMotion;
    private WolfMotion wolfMotion;
    private WordMaker wordRain;
    private WordFallManager wordFallManager;
    // private InputTextManager inputTextManager;

    // 스레드
    private Thread manThread, wolfThread, wordMakerThread, wordFallManagerThread, inputTextManagerThread;

    // 떨어지는 단어들을 저장할 리스트. synchronized로 동기화 걸 것
    private ArrayList<WordLabel> wordList = new ArrayList<>();

    // 게임 종료
    private boolean isFinish = false;

    private String inputText = null;

    public GameGroundPanel() {
        setLayout(null);

        // ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 단어 입력 처리 버튼 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
        JTextField input = new JTextField(30);
        input.setLocation(230, 720);
        input.setSize(150, 30);

        input.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField t = (JTextField) e.getSource();
                inputText = t.getText();
                t.setText("");

                synchronized (wordList) {
                    Iterator<WordLabel> it = wordList.iterator();
                    while(it.hasNext()) {
                        WordLabel word = it.next();
                        if(word.getWord().equals(inputText)) {
                            it.remove();
                            remove(word); // 현재 패널에서 단어 삭제
                            revalidate(); // 내부 배치 정보 갱신
                            repaint(); // 화면 다시 그리기
                            break;
                        }
                    }
                }
            }
        });

        add(input);
        // ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 단어 입력 처리 버튼 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ

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
        g.drawImage(ManImage.getImage(), 100, 530, 170, 170, this);

        ImageIcon WolfImage = wolfMotion.getCurrentFrame();
        g.drawImage(WolfImage.getImage(), 300, 515, 250, 250, this);
    }
}