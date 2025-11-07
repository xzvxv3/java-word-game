package runnable.core;

import javax.swing.*;

public class WolfController extends BaseCharacter implements Runnable {

    public WolfController(JPanel panel, int hp) {
        super(panel,  hp);
        initCharacter();
    }

    @Override
    public void run() {
        while (true) {
            // 이렇게 하면 왜 프레임 문제가 해결되는지?
            idx = (idx + 1) % motionFrames.length; // 프레임 변경
            panel.repaint(); // 부모 패널 다시 그리기, 부모는 바뀐 프레임을 호출할 예정

            if(!currentMotion.equals("IDLE") && idx == motionFrames.length - 1) {
                setMotion("IDLE");
                // 마지막 장면 0.15초 딜레이 (자연스러운 모션 유도)
                try { Thread.sleep(150); } catch (InterruptedException e) { break; }
            }
            // 프레임 속도
            try { Thread.sleep(100); } catch (InterruptedException e) { break; }
        }
    }

    private void initCharacter() {
        currentMotion = "IDLE";
        loadMotionImages(currentMotion);
    }

    protected void loadMotionImages(String motion) {
        switch (motion) {
            case "IDLE" : setIdleMotion(); break;
            case "ATTACK" : setAttackMotion(); break;
            case "Damage" : setDamageMotion(); break;
            case "DIE" : setDieMotion(); break;
        }
    }

    // 늑대 모션
    private void setIdleMotion() {
        motionFrames = new ImageIcon[5];
        for(int i=0; i<5; i++) {
            motionFrames[i] = new ImageIcon("resources/sprites/wolf/idle/LoboSombriuIdlle000" + (i + 1) + ".png");
        }
    } // 정지 모션
    private void setDieMotion() {
        motionFrames = new ImageIcon[5];
        for(int i=0; i<5; i++) {
            motionFrames[i] = new ImageIcon("resources/sprites/wolf/death/LoboSombriuDeath000" + (i + 1) + ".png");
        }
    } // 죽음 모션
    private void setAttackMotion() {
        motionFrames = new ImageIcon[14];
        for(int i=0; i<14; i++) {
            if(i < 9) {
                motionFrames[i] = new ImageIcon("resources/sprites/wolf/attack/LoboSombriuAttack000" + (i + 1) + ".png");
            }
            else if(i >= 9) {
                motionFrames[i] = new ImageIcon("resources/sprites/wolf/attack/LoboSombriuAttack00" + (i + 1) + ".png");
            }
        }
    } // 공격 모션
    private void setDamageMotion() {
        motionFrames = new ImageIcon[4];
        for(int i=0; i<4; i++) {
            motionFrames[i] = new ImageIcon("resources/sprites/wolf/damage/LoboSombriuDamage000" + (i + 1) + ".png");
        }
    } // 데미지 모션
}
