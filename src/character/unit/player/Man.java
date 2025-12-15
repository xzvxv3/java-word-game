package character.unit.player;

import character.base.BaseGameCharacter;
import character.type.MotionType;
import character.type.WeaponType;
import character.imageloader.ImageLoader;

import javax.swing.*;

public class Man extends BaseGameCharacter implements Runnable {
    private WeaponType weapon; // 캐릭터의 무기

    public Man(JPanel panel, ImageLoader imageLoader, WeaponType weapon, int hp) {
        super(panel, imageLoader, hp, -150, 435, 450, 800, 200, 100); // 부모 패널, 캐릭터 체력, 데미지 딜레이, 죽음 딜레이
        this.weapon = weapon; // 캐릭터 무기
    }

    @Override
    public void run() {
        while (characterLifeCycle()) {} // 캐릭터 모션 실행
        System.out.println("[Man 스레드 종료]");
    }

    @Override
    protected void onMotionEnd() {
        // 이동중인 상태 or 죽음 상태 -> 더이상의 모션을 주지 않음.
        if (motionType == MotionType.DEAD || motionType == MotionType.RUN) {
            return;
        }

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

    // 현재 무기 상태 반환
    public WeaponType getCurrentWeapon() { return weapon; }
}
