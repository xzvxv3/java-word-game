package character.imageloader;

import character.type.MotionType;

import javax.swing.*;

public class MushroomImageLoader extends ImageLoader {
    private ImageIcon[] idleMotion = new ImageIcon[4];
    private ImageIcon[] deadMotion = new ImageIcon[4];
    private ImageIcon[] damageMotion = new ImageIcon[4];
    private ImageIcon[] attackMotion = new ImageIcon[8];

    public MushroomImageLoader() {
        setMotions(); // 이미지 생성
        recordAllMotions(); // 이미지 저장
    }

    // 이미지 저장
    private void recordAllMotions() {
        motionMap.put(MotionType.IDLE, idleMotion);
        motionMap.put(MotionType.DEAD, deadMotion);
        motionMap.put(MotionType.ENEMY_ATTACK01, attackMotion);
        motionMap.put(MotionType.DAMAGE, damageMotion);
    }

    // 이미지 생성
    private void setMotions() {
        // Idle Motion
        for (int i = 0; i < 4; i++)
            idleMotion[i] = new ImageIcon(BASE_PATH + "mushroom/idle/MushroomIdle00" + (i + 1) + ".png");

        // Dead Motion
        for (int i = 0; i < 4; i++)
           deadMotion[i] = new ImageIcon(BASE_PATH + "mushroom/dead/MushroomDead00" + (i + 1) + ".png");

        // Damage Motion
        for (int i = 0; i < 4; i++)
            damageMotion[i] = new ImageIcon(BASE_PATH + "mushroom/damage/MushroomHit00" + (i + 1) + ".png");

        // Attack Motion
        for (int i = 0; i < 8; i++)
            attackMotion[i] = new ImageIcon(BASE_PATH + "mushroom/attack/MushroomAttack00" + (i + 1) + ".png");
    }
}
