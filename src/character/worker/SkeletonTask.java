package character.worker;

import character.MotionType;

import javax.swing.*;

public class SkeletonTask extends BaseCharacter implements Runnable {
    public SkeletonTask(JPanel panel, int hp) {
        super(panel,  hp, 250, 400, 140, 100);
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
        motionMap.put(MotionType.ENEMY_ATTACK02, setAttackMotion02());
        motionMap.put(MotionType.DAMAGE, setDamageMotion());
        motionMap.put(MotionType.SHIELD, setShieldMotion());
    }

    // 해골 모션
    private ImageIcon[] setIdleMotion() {
        ImageIcon[] tmp = new ImageIcon[4];
        for(int i=0; i<4; i++) {
            tmp[i] = new ImageIcon("resources/sprites/skeleton/idle/idle00" + (i + 1) + ".png");
        }
        return tmp;
    } // 정지 모션
    private ImageIcon[] setDeadMotion() {
        ImageIcon[] tmp = new ImageIcon[4];
        for(int i=0; i<4; i++) {
            tmp[i] = new ImageIcon("resources/sprites/skeleton/dead/dead00" + (i + 1) + ".png");
        }
        return tmp;
    } // 죽음 모션
    private ImageIcon[] setAttackMotion01() {
        ImageIcon[] tmp = new ImageIcon[8];
        for(int i=0; i<8; i++) {
            tmp[i] = new ImageIcon("resources/sprites/skeleton/attack1/attackdown00" + (i + 1) + ".png");
        }
        return tmp;
    } // 공격1 모션
    private ImageIcon[] setAttackMotion02() {
        ImageIcon[] tmp = new ImageIcon[8];
        for(int i=0; i<8; i++) {
            tmp[i] = new ImageIcon("resources/sprites/skeleton/attack2/attackup00" + (i + 1) + ".png");
        }
        return tmp;
    } // 공격2 모션
    private ImageIcon[] setDamageMotion() {
        ImageIcon[] tmp = new ImageIcon[4];
        for(int i=0; i<4; i++) {
            tmp[i] = new ImageIcon("resources/sprites/skeleton/damage/damage00" + (i + 1) + ".png");
        }
        return tmp;
    } // 데미지 모션
    private ImageIcon[] setShieldMotion() {
        ImageIcon[] tmp = new ImageIcon[4];
        for(int i=0; i<4; i++) {
            tmp[i] = new ImageIcon("resources/sprites/skeleton/shield/shield00" + (i + 1) + ".png");
        }
        return tmp;
    } // 쉴드 모션
}
