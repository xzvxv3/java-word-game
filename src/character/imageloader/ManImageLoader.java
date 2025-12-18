package character.imageloader;
import character.type.MotionType;
import javax.swing.*;

// 주인공 이미지 로더
public class ManImageLoader extends ImageLoader{
    private ImageIcon[] idleMotion = new ImageIcon[7];
    private ImageIcon[] damageMotion = new ImageIcon[4];
    private ImageIcon[] deadMotion = new ImageIcon[9];
    private ImageIcon[] runMotion = new ImageIcon[8];
    private ImageIcon[] swordRunMotion = new ImageIcon[8];

    private ImageIcon[] attackMotion01 = new ImageIcon[9];
    private ImageIcon[] attackMotion02 = new ImageIcon[9];
    private ImageIcon[] attackMotion03 = new ImageIcon[8];
    private ImageIcon[] attackMotion04 = new ImageIcon[6];

    private ImageIcon[] swordIdleMotion = new ImageIcon[7];
    private ImageIcon[] swordAttackMotion01 = new ImageIcon[8];
    private ImageIcon[] swordAttackMotion02 = new ImageIcon[8];
    private ImageIcon[] swordAttackMotion03 = new ImageIcon[6];
    private ImageIcon[] swordAttackMotion04 = new ImageIcon[5];

    public ManImageLoader() {
        // 이미지 생성
        setMotions();
        // 이미지 저장
        recordAllMotions();
    }

    // 이미지 저장
    private void recordAllMotions() {
        motionMap.put(MotionType.IDLE, idleMotion);
        motionMap.put(MotionType.DEAD, deadMotion);
        motionMap.put(MotionType.DAMAGE, damageMotion);
        motionMap.put(MotionType.RUN, runMotion);

        motionMap.put(MotionType.MAN_ATTACK01, attackMotion01);
        motionMap.put(MotionType.MAN_ATTACK02, attackMotion02);
        motionMap.put(MotionType.MAN_ATTACK03, attackMotion03);
        motionMap.put(MotionType.MAN_ATTACK04, attackMotion04);

        motionMap.put(MotionType.MAN_SWORD_IDLE, swordIdleMotion);
        motionMap.put(MotionType.MAN_SWORD_RUN, swordRunMotion);
        motionMap.put(MotionType.MAN_SWORD_ATTACK01, swordAttackMotion01);
        motionMap.put(MotionType.MAN_SWORD_ATTACK02, swordAttackMotion02);
        motionMap.put(MotionType.MAN_SWORD_ATTACK03, swordAttackMotion03);
        motionMap.put(MotionType.MAN_SWORD_ATTACK04, swordAttackMotion04);
    }

    // 이미지 생성
    private void setMotions() {
        // Idle Motion
        for(int i=0; i<7; i++)
            idleMotion[i] = new ImageIcon(BASE_PATH + "original/idle/Idle0" + (i + 1) + ".png");

        // Dead Motion
        for (int i = 0; i < 9; i++)
            deadMotion[i] = new ImageIcon(BASE_PATH + "original/die/Die0" + (i + 1) + ".png");

        // Damage Motion
        for(int i=0; i < 4; i++)
            damageMotion[i] = new ImageIcon(BASE_PATH + "original/damage/ShockHeavy0" + (i + 1) + ".png");

        // Run Motion
        for (int i = 0; i < 8; i++)
            runMotion[i] = new ImageIcon(BASE_PATH + "original/run/Run0" + (i + 1) + ".png");

        // AttackMotion01
        for (int i = 0; i < 9; i++)
            attackMotion01[i] = new ImageIcon(BASE_PATH + "original/kick01/KickC0" + (i + 1) + ".png");

        // AttackMotion02
        for (int i = 0; i < 9; i++)
            attackMotion02[i] = new ImageIcon(BASE_PATH + "original/kick02/KickA0" + (i + 1) + ".png");

        // AttackMotion03
        for (int i = 0; i < 8; i++)
            attackMotion03[i] = new ImageIcon(BASE_PATH + "original/kick03/KickB0" + (i + 1) + ".png");

        // AttackMotion04
        for (int i = 0; i < 6; i++)
            attackMotion04[i] = new ImageIcon(BASE_PATH + "original/punch02/Punch010" + (i + 1) + ".png");

        // Sword Idle Motion
        for (int i = 0; i < 7; i++)
            swordIdleMotion[i] = new ImageIcon(BASE_PATH + "sword/idle/SwordIdle0" + (i + 1) + ".png");

        // SwordRunMotion
        for (int i = 0; i < 8; i++)
            swordRunMotion[i] = new ImageIcon(BASE_PATH + "sword/run/SwordRun0" + (i + 1) +".png");

        // SwordAttackMotion04
        for (int i = 0; i < 8; i++)
            swordAttackMotion01[i] = new ImageIcon(BASE_PATH + "sword/attack1/SwordCombo040" + (i + 1) + ".png");

        // SwordAttackMotion02
        for (int i = 0; i < 8; i++)
            swordAttackMotion02[i] = new ImageIcon(BASE_PATH + "sword/attack2/SwordSlash010" + (i + 1) + ".png");

        // SwordAttackMotion03
        for (int i = 0; i < 6; i++)
            swordAttackMotion03[i] = new ImageIcon(BASE_PATH + "sword/attack3/SwordCombo010" + (i + 1) + ".png");

        // SwordAttackMotion04
        for (int i = 0; i < 5; i++)
            swordAttackMotion04[i] = new ImageIcon(BASE_PATH + "sword/attack4/StandingSlash0" + (i + 1) + ".png");
    }
}
