package character.imageloader;
import character.type.MotionType;
import javax.swing.*;

// Scarecrow 이미지 로더
public class ScarecrowImageLoader extends ImageLoader{

    private ImageIcon[] idleMotion = new ImageIcon[3];
    private ImageIcon[] deadMotion = new ImageIcon[16];
    private ImageIcon[] damageMotion = new ImageIcon[9];

    public ScarecrowImageLoader() {
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
    }

    // 이미지 생성
    private void setMotions() {
        // Idle 모션
        for (int i = 0; i < 3; i++)
            idleMotion[i] = new ImageIcon(BASE_PATH + "scarecrow/idle/ScareCrowIDie00" + (i + 1) + ".png");

        // Dead 모션
        for (int i = 0; i < 16; i++) {
            if(i < 9) deadMotion[i] = new ImageIcon(BASE_PATH + "scarecrow/dead/ScareCrowDead00" + (i + 1) + ".png");
            else if(i >= 9) deadMotion[i] = new ImageIcon(BASE_PATH + "scarecrow/dead/ScareCrowDead0" + (i + 1) + ".png");
        }

        // Damage 모션
        for (int i = 0; i < 9; i++) {
            damageMotion[i] = new ImageIcon(BASE_PATH + "scarecrow/damage/ScareCrowHit00" + (i + 1) + ".png");
        }
    }
}
