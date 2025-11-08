package screen.play;

import data.WordLabel;

// import runnable.InputTextManager;
import runnable.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

public class GameGroundPanel extends JPanel {
    private ImageIcon ground = new ImageIcon("resources/background/ground.png");

    private ManController manController;
    private WolfController wolfController;
    private WordMaker wordRain;
    private WordFallManager wordFallManager;
    private MushroomController mushroomController;
    private ScareCrowController scareCrowController;

    // 캐릭터 스레드
    private Thread manControllerThread, wolfControllerThread,
                   mushroomControllerThread, scareCrowControllerThread;

    // 단어 관리 스레드
    private Thread wordMakerThread, wordFallManagerThread;

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

                            String weapon = manController.getCurrentWeapon();

                            int r = (int) (Math.random() * 4);

                            if(manController.getCurrentWeapon().equals("EMPTY")) {
                                if(r == 0) manController.setMotion("ATTACK01");
                                else if(r == 1) manController.setMotion("ATTACK02");
                                else if(r == 2) manController.setMotion("ATTACK03"); // MISS
                                else if(r == 3) manController.setMotion("ATTACK04");

                                mushroomController.onAttacked();
                                mushroomController.decreaseHP();
                            }
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
        // wolfMotionStart();

        // Mushroom 모션 스레드 시작
        mushroomMotionStart();

        // ScareCrow 모션 스레드 시작
        // scareCrowMotionStart();

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
        WordFallManager wordFallManager = new WordFallManager(this, wordList,  wolfController, manController  ,mushroomController, scareCrowController, 1);
        wordFallManagerThread = new Thread(wordFallManager);
        wordFallManagerThread.start();
    }

    // Man 모션
    private void manMotionStart() {
        manController = new ManController(this, "EMPTY", 3);
        manControllerThread = new Thread(manController);
        manControllerThread.start();
    }

    // Wolf 모션
    private void wolfMotionStart() {
        wolfController = new WolfController(this, 3);
        wolfControllerThread = new Thread(wolfController);
        wolfControllerThread.start();
    }

    // Mushroom 모션
    private void mushroomMotionStart() {
        mushroomController = new MushroomController(this, 3);
        mushroomControllerThread = new Thread(mushroomController);
        mushroomControllerThread.start();
    }

    // ScareCrow 모션
    private void scareCrowMotionStart() {
        scareCrowController = new ScareCrowController(this, 3);
        scareCrowControllerThread = new Thread(scareCrowController);
        scareCrowControllerThread.start();
    }



    @Override
    protected void paintComponent(Graphics g) { // 배경 요소
        super.paintComponent(g);
        g.drawImage(ground.getImage(), 0, 0, getWidth(), getHeight(), this);


        // 움직이는 모션을 사람에 넣으면 된다.
        ImageIcon manImage;
        manImage = manController.getCurrentFrame();
        if(manImage != null) {
            g.drawImage(manImage.getImage(), 185, 530, 170, 170, this);
        }

//        ImageIcon wolfImage = wolfController.getCurrentFrame();
//        if(wolfImage != null) {
//            g.drawImage(wolfImage.getImage(), 230, 515, 250, 250, this);
//        }

        ImageIcon mushroomImage = mushroomController.getCurrentFrame();
        if(mushroomImage != null) {
            g.drawImage(mushroomImage.getImage(), 190, 430, 300, 400, this);
        }

//        ImageIcon scareCrowImage = scareCrowController.getCurrentFrame();
//        if(scareCrowImage != null) {
//            g.drawImage(scareCrowImage.getImage(), 240, 590, 180, 110, this);
//        }
    }
}