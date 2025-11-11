package runnable;

import data.WordLabel;
import runnable.character.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Iterator;

public class WordFallManager implements Runnable{

    private final int FALL_SPEED; // 단어 떨어지는 속도
    private ArrayList<WordLabel> wordList; // 단어 라벨을 관리하는 리스트
    private JPanel panel; // 부모 패널
    private boolean running = true; // 단어 생성 작동 여부
    private WolfController wolfController;
    private ManController manController;
    private MushroomController mushroomController;
    private ScareCrowController scareCrowController;
    private SkeletonController skeletonController;

    public WordFallManager(JPanel panel, ArrayList<WordLabel> wordlist,
                           WolfController wolfController, ManController manController,
                           MushroomController mushroomController, ScareCrowController scareCrowController,
                           SkeletonController skeletonController, int level) {

        this.panel = panel; // 부모 패널
        this.wordList = wordlist; // 단어 라벨 리스트
        this.wolfController = wolfController;
        this.manController = manController;
        this.mushroomController = mushroomController;
        this.scareCrowController = scareCrowController;
        this.skeletonController = skeletonController;

        switch (level) {
            case 0: FALL_SPEED = 1; break; // 레벨0 -> 떨어지는 속도 1
            case 1: FALL_SPEED = 3; break; // 레벨1 -> 떨어지는 속도 3
            case 2: FALL_SPEED = 6; break; // 레벨2 -> 떨어지는 속도 6
            default: FALL_SPEED = 3; break; // 기본 레벨 -> 떨어지는 속도 3
        }
    }

    @Override
    public void run() {
        while(running) {
            fallWord(); // 단어를 떨어트림
            panel.repaint(); // 좌표값이 바뀐걸 다시 그림(버벅임 방지)
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    // 단어 낙하 스레드 종료 유도
    public void stopFalling() { running = false; }

    private void fallWord() {
        // 배열 리스트의 동기화 충돌 방지
        synchronized (wordList) {
            // 배열 리스트에 있는 단어 라벨들의 반복자 생성
            Iterator<WordLabel> it = wordList.iterator();

            // 단어 라벨 순회
            while(it.hasNext()) {
                WordLabel word = it.next(); // 단어 라벨 추출
                word.fall(FALL_SPEED); // 단어 라벨 떨어트림

                // 단어 라벨 충돌 판정
                if(word.isAtBottom(panel.getHeight())) {
                    // 늑대 상태 변화
                    // wolfController.setMotion("ATTACK");
                    // mushroomController.setMotion("ATTACK");
                    // skeletonController.setMotion("ATTACK01");
                    skeletonController.setMotion("ATTACK02");

                    manController.onAttacked();
                    manController.decreaseHP();

                    panel.remove(word); // 부모 패널에서 삭제
                    it.remove(); // 배열 리스트에서 삭제
                }
            }
        }
    }
}
