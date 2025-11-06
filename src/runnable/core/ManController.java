package runnable.core;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class ManController extends Character implements Runnable {
    protected ImageIcon[] motionFrames;
    private JPanel panel;
    private String currentState;
    private String weapon = "SWORD"; // 무기

    public ManController(JPanel panel, String weapon) {
        super(panel);
        this.panel = panel;
        this.weapon = weapon;

        if(this.weapon.equals("EMPTY")) {
            currentState = "IDLE";
            loadImages(currentState, weapon); // 기본 상태
        }
        else if(this.weapon.equals("SWORD")) {
            currentState = "SWORD_IDLE";
            loadImages(currentState, weapon);
        }

    }

    @Override
    public void run() {
        while (true) {
            idx = (idx + 1) % motionFrames.length; // 프레임 변경
            panel.repaint(); // 부모 패널 다시 그리기, 부모는 바뀐 프레임을 호출할 예정

            // Idle 상태가 아니면서 Attack 모션이 끝날 경우 Idle 상태로 복귀
            if(idx == motionFrames.length - 1) {
                if(weapon.equals("EMPTY") && !currentState.equals("IDLE")) {
                    setCurrentState("IDLE", weapon);
                }

                else if(weapon.equals("SWORD") && !currentState.equals("SWORD_IDLE")) {
                    setCurrentState("SWORD_IDLE", weapon);
                }

                try { Thread.sleep(100); } catch (InterruptedException e) { break; } // 마지막 장면 0.15초 딜레이 (자연스러운 모션 유도)
            }

            // 프레임 전환속도
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                break;
            }
        }
    }


    public String getCurrentWeapon() {return weapon;}
    // 모션 프레임 반환
    public ImageIcon getCurrentFrame() {
        return motionFrames[idx];
    }


    public void setCurrentState(String currentState, String weapon) {
        this.currentState = currentState;
        this.weapon = weapon;
        idx = 0;
        loadImages(currentState, weapon);
    }


    private void loadImages(String motion, String weapon) {

        if(weapon.equals("EMPTY")) {
            switch (motion) {
                case "IDLE" : setIdleMotion(); break;
                case "ATTACK01" : setKickMotion01(); break;
                case "ATTACK02" : setKickMotion02(); break;
                case "ATTACK03" : setPunchMotion01(); break;
                case "ATTACK04" : setPunchMotion02(); break;
                case "DIE" : setDieMotion(); break;
                default: return;
            }
        }

        else if(weapon.equals("SWORD")) {
            switch (motion) {
                case "SWORD_IDLE" : setSwordIdleMotion(); break;
                case "ATTACK01" : setSwordAttackMotion01(); break;
                case "ATTACK02" : setSwordAttackMotion02(); break;
                case "ATTACK03" : setSwordAttackMotion03(); break;
                case "ATTACK04" : setSwordAttackMotion04(); break;
            }
        }

    }

    private void setIdleMotion() {
        motionFrames = new ImageIcon[7];
        for(int i=0; i<7; i++) {
            motionFrames[i] = new ImageIcon("resources/sprites/original/idle/Idle0" + (i + 1) + ".png");
        }
    } // Man 정지 상태
    private void setKickMotion01() {
        motionFrames = new ImageIcon[9];
        for (int i = 0; i < 9; i++) {
            motionFrames[i] = new ImageIcon("resources/sprites/original/kick01/KickC0" + (i + 1) + ".png");
        }
    } // 킥 모션 1
    private void setKickMotion02() {
        motionFrames = new ImageIcon[9];
        for (int i = 0; i < 9; i++) {
            motionFrames[i] = new ImageIcon("resources/sprites/original/kick02/KickA0" + (i + 1) + ".png");
        }
    } // 킥 모션 2
    private void setPunchMotion01() {
        motionFrames = new ImageIcon[7];
        for (int i = 0; i < 7; i++) {
            motionFrames[i] = new ImageIcon("resources/sprites/original/punch01/Punch030" + (i + 1) + ".png");
        }
    } // 펀치 모션 1
    private void setPunchMotion02() { // 펀치 모션 2
        motionFrames = new ImageIcon[6];
        for (int i = 0; i < 6; i++) {
            motionFrames[i] = new ImageIcon("resources/sprites/original/punch02/Punch010" + (i + 1) + ".png");
        }
    } // 펀치 모션 2
    private void setDieMotion() {
        motionFrames = new ImageIcon[9];
        for (int i = 0; i < 9; i++) {
            motionFrames[i] = new ImageIcon("resources/sprites/original/die/Die0" + (i + 1) + ".png");
        }
    } // 죽음 모션

    private void setSwordIdleMotion() {
        motionFrames = new ImageIcon[7];
        for(int i=0; i<7; i++) {
            motionFrames[i] = new ImageIcon("resources/sprites/sword/idle/SwordIdle0" + (i + 1) + ".png");
        }
    } // Sword Man 정지 상태
    private void setSwordAttackMotion01() {
        motionFrames = new ImageIcon[8];
        for(int i=0; i<8; i++) {
            motionFrames[i] = new ImageIcon("resources/sprites/sword/attack1/SwordCombo040" + (i + 1) + ".png");
        }
    } // 검 공격 1
    private void setSwordAttackMotion02() {
        motionFrames = new ImageIcon[8];
        for(int i=0; i<8; i++) {
            motionFrames[i] = new ImageIcon("resources/sprites/sword/attack2/SwordSlash010" + (i + 1) + ".png");
        }
    } // 검 공격 2
    private void setSwordAttackMotion03() {
        motionFrames = new ImageIcon[6];
        for(int i=0; i<6; i++) {
            motionFrames[i] = new ImageIcon("resources/sprites/sword/attack3/SwordCombo010" + (i + 1) + ".png");
        }
    } // 검 공격 3
    private void setSwordAttackMotion04() {
        motionFrames = new ImageIcon[5];
        for(int i=0; i<5; i++) {
            motionFrames[i] = new ImageIcon("resources/sprites/sword/attack4/StandingSlash0" + (i + 1) + ".png");
        }
    } // 검 공격 4

}
