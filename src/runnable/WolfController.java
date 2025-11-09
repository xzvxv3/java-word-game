package runnable;

import javax.swing.*;

public class WolfController extends BaseCharacter implements Runnable {

    public WolfController(JPanel panel, int hp) {
        super(panel,  hp, 350, 600, 140, 70);
        setImageMotions(); // 캐릭터의 모션들 초기화
        initCharacter(); // 시작 시, 캐릭터의 모션 설정 (기본 IDLE)
    }


    @Override
    public void run() {
        while (characterLifeCycle()) {} // 캐릭터 모션 실행
    }

    @Override
    protected void onMotionEnd() {
        if(!currentMotion.equals("IDLE")) {
            motionFrames = motionMap.get("IDLE");
        }
    }

    private void initCharacter() {
        currentMotion = "IDLE";
        motionFrames = motionMap.get(currentMotion);
    }

    protected void setImageMotions() {
        motionMap.put("IDLE", setIdleMotion());
        motionMap.put("DEAD", setDeadMotion());
        motionMap.put("ATTACK", setAttackMotion());
        motionMap.put("DAMAGE", setDamageMotion());
    }

    // 늑대 모션
    private ImageIcon[] setIdleMotion() {
        ImageIcon[] tmp = new ImageIcon[5];
        for(int i=0; i<5; i++) {
            tmp[i] = new ImageIcon("resources/sprites/wolf/idle/LoboSombriuIdlle000" + (i + 1) + ".png");
        }
        return tmp;
    } // 정지 모션
    private ImageIcon[] setDeadMotion() {
        ImageIcon[] tmp = new ImageIcon[5];
        for(int i=0; i<5; i++) {
            tmp[i] = new ImageIcon("resources/sprites/wolf/death/LoboSombriuDeath000" + (i + 1) + ".png");
        }
        return tmp;
    } // 죽음 모션
    private ImageIcon[] setAttackMotion() {
        ImageIcon[] tmp = new ImageIcon[14];
        for(int i=0; i<14; i++) {
            if(i < 9) {
                tmp[i] = new ImageIcon("resources/sprites/wolf/attack/LoboSombriuAttack000" + (i + 1) + ".png");
            }
            else if(i >= 9) {
                tmp[i] = new ImageIcon("resources/sprites/wolf/attack/LoboSombriuAttack00" + (i + 1) + ".png");
            }
        }
        return tmp;
    } // 공격 모션
    private ImageIcon[] setDamageMotion() {
        ImageIcon[] tmp = new ImageIcon[7];
        for(int i=0; i<7; i++) {
            tmp[i] = new ImageIcon("resources/sprites/wolf/damage/LoboSombriuDamage000" + (i + 1) + ".png");
        }
        return tmp;
    } // 데미지 모션
}
