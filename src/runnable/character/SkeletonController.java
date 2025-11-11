package runnable.character;

import javax.swing.*;

public class SkeletonController extends BaseCharacter implements Runnable {
    public SkeletonController(JPanel panel, int hp) {
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
        motionMap.put("ATTACK01", setAttackMotion01());
        motionMap.put("ATTACK02", setAttackMotion02());
        motionMap.put("DAMAGE", setDamageMotion());
        motionMap.put("SHIELD", setShieldMotion());
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
