package character.worker;

import character.MotionType;
import character.WeaponType;

import javax.swing.*;

public class ManTask extends BaseCharacter implements Runnable {
    private WeaponType weapon; // 캐릭터의 무기

    public ManTask(JPanel panel, WeaponType weapon, int hp) {
        super(panel, hp, 450, 800, 200, 100); // 부모 패널, 캐릭터 체력, 데미지 딜레이, 죽음 딜레이
        this.weapon = weapon; // 캐릭터 무기
        setImageMotions(); // 캐릭터의 모션들 초기화
        initCharacter(); // 시작 시, 캐릭터의 모션 설정 (기본 IDLE)
    }

    @Override
    public void run() {
        while (characterLifeCycle()) {} // 캐릭터 모션 실행
    }

    @Override
    protected void onMotionEnd() {
        // 기본 모드의 IDLE 전환
        if(weapon.equals(WeaponType.EMPTY) && motionType != MotionType.IDLE) {
            motionType = MotionType.IDLE;
            motionFrames = motionMap.get(motionType);
        }
        // 검 모드의 IDLE 전환
        else if(weapon.equals(WeaponType.SWORD) && motionType != MotionType.MAN_SWORD_IDLE) {
            motionType = MotionType.MAN_SWORD_IDLE;
            motionFrames = motionMap.get(motionType);
        }
    }

    private void initCharacter() {
        switch (weapon) {
            case EMPTY : // 기본 모드
                motionType = MotionType.IDLE;
                motionFrames = motionMap.get(MotionType.IDLE);
                break;
            case SWORD : // 검 모드
                motionType = MotionType.MAN_SWORD_IDLE;
                motionFrames = motionMap.get(MotionType.MAN_SWORD_IDLE);
                break;
        }
    }

    // 현재 무기 상태 반환
    public WeaponType getCurrentWeapon() {return weapon;}

    private void setImageMotions() {
        // 기본 모션
        motionMap.put(MotionType.IDLE, setIdleMotion());
        motionMap.put(MotionType.MAN_ATTACK01, setKickMotion01());
        motionMap.put(MotionType.MAN_ATTACK02, setKickMotion02());
        motionMap.put(MotionType.MAN_ATTACK03, setKickMotion03());
        motionMap.put(MotionType.MAN_ATTACK04, setPunchMotion01());

        // 검 모션
        motionMap.put(MotionType.MAN_SWORD_IDLE, setSwordIdleMotion());
        motionMap.put(MotionType.MAN_SWORD_ATTACK01, setSwordAttackMotion01());
        motionMap.put(MotionType.MAN_SWORD_ATTACK02, setSwordAttackMotion02());
        motionMap.put(MotionType.MAN_SWORD_ATTACK03, setSwordAttackMotion03());
        motionMap.put(MotionType.MAN_SWORD_ATTACK04, setSwordAttackMotion04());

        // 공통 모션
        motionMap.put(MotionType.DEAD, setDeadMotion());
        motionMap.put(MotionType.DAMAGE, setDamageMotion());
    }

    // 공통 모션
    private ImageIcon[] setDamageMotion() {
        ImageIcon[] tmp = new ImageIcon[4];
        for(int i=0; i<4; i++) {
            tmp[i] = new ImageIcon("resources/sprites/original/damage/ShockHeavy0" + (i + 1) + ".png");
        }
        return tmp;
    } // 데미지 모션
    private ImageIcon[] setDeadMotion() {
        ImageIcon[] tmp = new ImageIcon[9];
        for (int i = 0; i < 9; i++) {
            tmp[i] = new ImageIcon("resources/sprites/original/die/Die0" + (i + 1) + ".png");
        }
        return tmp;
    } // 죽음 모션

    // 기본 모션
    private ImageIcon[] setIdleMotion() {
        ImageIcon[] tmp = new ImageIcon[7];
        for(int i=0; i<7; i++) {
            tmp[i] = new ImageIcon("resources/sprites/original/idle/Idle0" + (i + 1) + ".png");
        }
        return tmp;
    } // Man 정지 상태
    private ImageIcon[] setKickMotion01() {
        ImageIcon[] tmp = new ImageIcon[9];
        for (int i = 0; i < 9; i++) {
            tmp[i] = new ImageIcon("resources/sprites/original/kick01/KickC0" + (i + 1) + ".png");
        }
        return tmp;
    } // 킥 모션 1
    private ImageIcon[] setKickMotion02() {
        ImageIcon[] tmp = new ImageIcon[9];
        for (int i = 0; i < 9; i++) {
            tmp[i] = new ImageIcon("resources/sprites/original/kick02/KickA0" + (i + 1) + ".png");
        }
        return tmp;
    } // 킥 모션 2
    private ImageIcon[] setKickMotion03() {
        ImageIcon[] tmp = new ImageIcon[8];
        for (int i = 0; i < 8; i++) {
            tmp[i] = new ImageIcon("resources/sprites/original/kick03/KickB0" + (i + 1) + ".png");
        }
        return tmp;
    } // 펀치 모션 1
    private ImageIcon[] setPunchMotion01() { // 펀치 모션 2
        ImageIcon[] tmp = new ImageIcon[6];
        for (int i = 0; i < 6; i++) {
            tmp[i] = new ImageIcon("resources/sprites/original/punch02/Punch010" + (i + 1) + ".png");
        }
        return tmp;
    } // 펀치 모션 2

    // 검 모션
    private ImageIcon[] setSwordIdleMotion() {
        ImageIcon[] tmp = new ImageIcon[7];
        for(int i=0; i<7; i++) {
            tmp[i] = new ImageIcon("resources/sprites/sword/idle/SwordIdle0" + (i + 1) + ".png");
        }
        return tmp;
    } // Sword Man 정지 상태
    private ImageIcon[] setSwordAttackMotion01() {
        ImageIcon[] tmp = new ImageIcon[8];
        for(int i=0; i<8; i++) {
            tmp[i] = new ImageIcon("resources/sprites/sword/attack1/SwordCombo040" + (i + 1) + ".png");
        }
        return tmp;
    } // 검 공격 1
    private ImageIcon[] setSwordAttackMotion02() {
        ImageIcon[] tmp = new ImageIcon[8];
        for(int i=0; i<8; i++) {
            tmp[i] = new ImageIcon("resources/sprites/sword/attack2/SwordSlash010" + (i + 1) + ".png");
        }
        return tmp;
    } // 검 공격 2
    private ImageIcon[] setSwordAttackMotion03() {
        ImageIcon[] tmp = new ImageIcon[6];
        for(int i=0; i<6; i++) {
            tmp[i] = new ImageIcon("resources/sprites/sword/attack3/SwordCombo010" + (i + 1) + ".png");
        }
        return tmp;
    } // 검 공격 3
    private ImageIcon[] setSwordAttackMotion04() {
        ImageIcon[] tmp = new ImageIcon[5];
        for(int i=0; i<5; i++) {
            tmp[i] = new ImageIcon("resources/sprites/sword/attack4/StandingSlash0" + (i + 1) + ".png");
        }
        return tmp;
    } // 검 공격 4
}
