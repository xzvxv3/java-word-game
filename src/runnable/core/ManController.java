package runnable.core;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class ManController extends Character implements Runnable {
    protected ImageIcon[] motionFrames;
    private int locX = 100;
    private int locY = 530;
    private JPanel panel;
    private String currentState = "IDLE";

    public ManController(JPanel panel) {
        super(panel);
        this.panel = panel;
        loadImages(currentState); // 기본 상태
    }

    @Override
    public void run() {
        while (true) {
            idx = (idx + 1) % motionFrames.length; // 프레임 변경
            panel.repaint(); // 부모 패널 다시 그리기, 부모는 바뀐 프레임을 호출할 예정

            // Idle 상태가 아니면서 Attack 모션이 끝날 경우 Idle 상태로 복귀
            if (!currentState.equals("IDLE") && idx == motionFrames.length - 1) {
                try { Thread.sleep(100); } catch (InterruptedException e) { break; } // 마지막 장면 0.15초 딜레이 (자연스러운 모션 유도)
                setCurrentState("IDLE");
            }

            // 프레임 전환속도
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                break;
            }
        }
    }


    // 모션 프레임 반환
    public ImageIcon getCurrentFrame() {
        return motionFrames[idx];
    }


    public void setCurrentState(String currentState) {
        this.currentState = currentState;
        idx = 0;
        loadImages(currentState);
    }


    private void loadImages(String behavior) {
        switch (behavior) {
            case "IDLE" : setIdleMotion(); break;
            case "KICK01" : setKickMotion01(); break;
            case "KICK02" : setKickMotion02(); break;
            case "PUNCH01" : setPunchMotion01(); break;
            case "PUNCH02" : setPunchMotion02(); break;
            case "DIE" : setDieMotion(); break;
            default: return;
        }
    }

    private void setIdleMotion() {
        motionFrames = new ImageIcon[7];
        for(int i=0; i<7; i++) {
            motionFrames[i] = new ImageIcon("resources/sprites/original/idle/Idle0" + (i + 1) + ".png");
        }
    }

    private void setKickMotion01() {
        motionFrames = new ImageIcon[9];
        for (int i = 0; i < 9; i++) {
            motionFrames[i] = new ImageIcon("resources/sprites/original/kick01/KickC0" + (i + 1) + ".png");
        }
    }

    private void setKickMotion02() {
        motionFrames = new ImageIcon[9];
        for (int i = 0; i < 9; i++) {
            motionFrames[i] = new ImageIcon("resources/sprites/original/kick02/KickA0" + (i + 1) + ".png");
        }
    }

    private void setPunchMotion01() {
        motionFrames = new ImageIcon[7];
        for (int i = 0; i < 7; i++) {
            motionFrames[i] = new ImageIcon("resources/sprites/original/punch01/Punch030" + (i + 1) + ".png");
        }
    }

    private void setPunchMotion02() {
        motionFrames = new ImageIcon[6];
        for (int i = 0; i < 6; i++) {
            motionFrames[i] = new ImageIcon("resources/sprites/original/punch02/Punch010" + (i + 1) + ".png");
        }
    }


    private void setDieMotion() {
        motionFrames = new ImageIcon[9];
        for (int i = 0; i < 9; i++) {
            motionFrames[i] = new ImageIcon("resources/sprites/original/die/Die0" + (i + 1) + ".png");
        }
    }
}
