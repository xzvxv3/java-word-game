package character.worker;

import character.MotionType;
import character.WeaponType;
import character.imageloader.ImageLoader;

import javax.swing.*;

public class ManTask extends BaseCharacter implements Runnable {
    private WeaponType weapon; // 캐릭터의 무기

    public ManTask(JPanel panel, ImageLoader imageLoader, WeaponType weapon, int hp) {
        super(panel, imageLoader, hp, 450, 800, 200, 100); // 부모 패널, 캐릭터 체력, 데미지 딜레이, 죽음 딜레이
        this.weapon = weapon; // 캐릭터 무기
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
            motionFrames = imageLoader.getMotionMap().get(motionType);
        }
        // 검 모드의 IDLE 전환
        else if(weapon.equals(WeaponType.SWORD) && motionType != MotionType.MAN_SWORD_IDLE) {
            motionType = MotionType.MAN_SWORD_IDLE;
            motionFrames = imageLoader.getMotionMap().get(motionType);
        }
    }

    public void changeToSword() {
        this.weapon = WeaponType.SWORD;
    }

    private void initCharacter() {
        switch (weapon) {
            case EMPTY : // 기본 모드
                motionType = MotionType.IDLE;
                motionFrames = imageLoader.getMotionMap().get(MotionType.IDLE);
                break;
            case SWORD : // 검 모드
                motionType = MotionType.MAN_SWORD_IDLE;
                motionFrames = imageLoader.getMotionMap().get(MotionType.MAN_SWORD_IDLE);
                break;
        }
    }

    // 현재 무기 상태 반환
    public WeaponType getCurrentWeapon() {return weapon;}
}
