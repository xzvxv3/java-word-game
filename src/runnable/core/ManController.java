package runnable.core;

import javax.swing.*;

public class ManController extends BaseCharacter implements Runnable {
    private String weapon = "SWORD";

    public ManController(JPanel panel, String weapon, int hp) {
        super(panel, hp);
        this.weapon = weapon;
        initCharacter();
    }

    @Override
    public void run() {
        while (true) {
            idx = (idx + 1) % motionFrames.length; // 프레임 변경
            panel.repaint(); // 부모 패널 다시 그리기, 부모는 바뀐 프레임을 호출할 예정

            // Idle 상태가 아니면서 Attack 모션이 끝날 경우 Idle 상태로 복귀
            if(idx == motionFrames.length - 1) {
                // 기본 모드의 IDLE 전환
                if(weapon.equals("EMPTY") && !currentMotion.equals("IDLE")) {
                    setMotion("IDLE");
                }
                // 검 모드의 IDLE 전환
                else if(weapon.equals("SWORD") && !currentMotion.equals("SWORD_IDLE")) {
                    setMotion("SWORD_IDLE");
                }
                // 마지막 장면 0.15초 딜레이 (자연스러운 모션 유도)
                try { Thread.sleep(100); } catch (InterruptedException e) { break; }
            }

            // 프레임 전환속도
            try { Thread.sleep(100); } catch (InterruptedException e) { break; }
        }
    }

    private void initCharacter() {
        // 기본 모드
        if(weapon.equals("EMPTY")) {
            currentMotion = "IDLE";
            loadMotionImages(currentMotion); // 기본 상태
        }

        // 검 모드
        else if(weapon.equals("SWORD")) {
            currentMotion = "SWORD_IDLE";
            loadMotionImages(currentMotion);
        }
    }

    // 현재 무기 상태 반환
    public String getCurrentWeapon() {return weapon;}

    // 모션 이미지 설정
    protected void loadMotionImages(String motion) {
        if(weapon.equals("EMPTY")) {
            switch (motion) {
                case "IDLE" : setIdleMotion(); break;
                case "ATTACK01" : setKickMotion01(); break;
                case "ATTACK02" : setKickMotion02(); break;
                case "ATTACK03" : setPunchMotion01(); break;
                case "ATTACK04" : setPunchMotion02(); break;
                case "DIE" : setDieMotion(); break;
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

    // 기본 모션
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

    // 검 모션
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
