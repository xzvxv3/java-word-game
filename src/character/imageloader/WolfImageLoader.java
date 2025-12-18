package character.imageloader;
import character.type.MotionType;
import javax.swing.*;

// Wolf 이미지 로더
public class WolfImageLoader extends ImageLoader {
    private ImageIcon[] idleMotion = new ImageIcon[5];
    private ImageIcon[] deadMotion = new ImageIcon[5];
    private ImageIcon[] damageMotion = new ImageIcon[7];
    private ImageIcon[] attackMotion = new ImageIcon[14];
    private ImageIcon[] runMotion = new ImageIcon[8];

    public WolfImageLoader() {
        // 이미지 생성
        setMotions();
        // 이미지 저장
        recordAllMotions();
    }

    // 이미지 저장
    private void recordAllMotions() {
        motionMap.put(MotionType.IDLE, idleMotion);
        motionMap.put(MotionType.DEAD, deadMotion);
        motionMap.put(MotionType.ENEMY_ATTACK01, attackMotion);
        motionMap.put(MotionType.DAMAGE, damageMotion);
        motionMap.put(MotionType.RUN, runMotion);
    }

    // 이미지 생성
    private void setMotions() {
        // Idle Motion
        for (int i = 0; i < 5; i++)
            idleMotion[i] = new ImageIcon(BASE_PATH + "wolf/idle/LoboSombriuIdlle000" + (i + 1) + ".png");

        // Dead Motion
        for (int i = 0; i < 5; i++)
            deadMotion[i] = new ImageIcon(BASE_PATH + "wolf/death/LoboSombriuDeath000" + (i + 1) + ".png");

        // Damage Motion
        for (int i = 0; i < 7; i++)
            damageMotion[i] = new ImageIcon(BASE_PATH + "wolf/damage/LoboSombriuDamage000" + (i + 1) + ".png");

        // Attack Motion
        for (int i = 0; i < 14; i++) {
            if(i < 9) attackMotion[i] = new ImageIcon(BASE_PATH + "wolf/attack/LoboSombriuAttack000" + (i + 1) + ".png");
            else if(i >= 9) attackMotion[i] = new ImageIcon(BASE_PATH + "wolf/attack/LoboSombriuAttack00" + (i + 1) + ".png");
        }

        // Run Motion
        for (int i = 0; i < 8; i++)
            runMotion[i] = new ImageIcon(BASE_PATH + "wolf/walk/LoboSombriuWalk000" + (i + 1) + ".png");
    }
}
