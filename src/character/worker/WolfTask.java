package character.worker;

import character.MotionType;

import javax.swing.*;

public class WolfTask extends BaseCharacter implements Runnable {

    public WolfTask(JPanel panel, int hp) {
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
        if(motionType != MotionType.IDLE) {
            motionType = MotionType.IDLE;
            motionFrames = motionMap.get(motionType);
        }
    }

    private void initCharacter() {
        motionType = MotionType.IDLE;
        motionFrames = motionMap.get(motionType);
    }

    protected void setImageMotions() {
        motionMap.put(MotionType.IDLE, setIdleMotion());
        motionMap.put(MotionType.DEAD, setDeadMotion());
        motionMap.put(MotionType.ENEMY_ATTACK01, setAttackMotion01());
        motionMap.put(MotionType.DAMAGE, setDamageMotion());
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
    private ImageIcon[] setAttackMotion01() {
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
