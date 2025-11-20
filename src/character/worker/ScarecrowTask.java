package character.worker;

import character.MotionType;

import javax.swing.*;

public class ScarecrowTask extends BaseCharacter implements Runnable{
    public ScarecrowTask(JPanel panel, int hp) {
        super(panel,  hp, 350, 400, 140, 50);
        setImageMotions(); // 캐릭터의 모션들 초기화
        initCharacter(); // 시작 시, 캐릭터의 모션 설정 (기본 IDLE)
    }


    @Override
    public void run() {
        while (characterLifeCycle()) {} // 캐릭터 모션 실행
    }

    // Idle 모션으로 전환
    @Override
    protected void onMotionEnd() {
        if(motionType != MotionType.IDLE) {
            motionType = MotionType.IDLE;
            motionFrames = motionMap.get(motionType);
        }
    }

    // HP 설정
    public void setHP(int hp) {
        this.hp = hp;
    }

    // 캐릭터 모션 초기화
    private void initCharacter() {
        motionType = MotionType.IDLE;
        motionFrames = motionMap.get(motionType);
    }

    // 캐릭터 모션 저장
    protected void setImageMotions() {
        motionMap.put(MotionType.IDLE, setIdleMotion());
        motionMap.put(MotionType.DEAD, setDeadMotion());
        motionMap.put(MotionType.DAMAGE, setDamageMotion());
    }

    // 허수아비 모션
    private ImageIcon[] setIdleMotion() {
        ImageIcon[] tmp = new ImageIcon[3];
        for(int i=0; i<3; i++) {
            tmp[i] = new ImageIcon("resources/sprites/scarecrow/idle/ScareCrowIDie00" + (i + 1) + ".png");
        }
        return tmp;
    } // 정지 모션
    private ImageIcon[] setDeadMotion() {
        ImageIcon[] tmp = new ImageIcon[16];
        for(int i=0; i<16; i++) {
            if(i < 9) {
                tmp[i] = new ImageIcon("resources/sprites/scarecrow/dead/ScareCrowDead00" + (i + 1) + ".png");
            }
            else if(i >= 9) {
                tmp[i] = new ImageIcon("resources/sprites/scarecrow/dead/ScareCrowDead0" + (i + 1) + ".png");
            }
        }
        return tmp;
    } // 죽음 모션
    private ImageIcon[] setDamageMotion() {
        ImageIcon[] tmp = new ImageIcon[9];
        for(int i=0; i<9; i++) {
            tmp[i] = new ImageIcon("resources/sprites/scarecrow/damage/ScareCrowHit00" + (i + 1) + ".png");
        }
        return tmp;
    } // 데미지 모션
}
