package character.imageloader;
import character.type.MotionType;
import javax.swing.ImageIcon;

// Reaper 이미지 로더
public class ReaperImageLoader extends ImageLoader {
    private ImageIcon[] idleMotion = new ImageIcon[8];
    private ImageIcon[] deadMotion = new ImageIcon[10];
    private ImageIcon[] attackMotion = new ImageIcon[10];
    private ImageIcon[] skillMotion = new ImageIcon[9];
    private ImageIcon[] damageMotion = new ImageIcon[4];
    private ImageIcon[] runMotion = new ImageIcon[8];

    public ReaperImageLoader() {
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
        motionMap.put(MotionType.ENEMY_SKILL01, skillMotion);
        motionMap.put(MotionType.DAMAGE, damageMotion);
        motionMap.put(MotionType.RUN, runMotion);
    }

    // 이미지 생성
    private void setMotions() {
        // Idle Motion
        for (int i = 0; i < 8; i++) idleMotion[i] = new ImageIcon(BASE_PATH + "reaper/idle/idle00" + (i + 1) + ".png");

        // Dead Motion
        for (int i = 0; i < 10; i++) {
            if(i < 9) deadMotion[i] = new ImageIcon(BASE_PATH + "reaper/dead/dead00" + (i + 1) + ".png");
            else if(i >= 9) deadMotion[i] = new ImageIcon(BASE_PATH + "reaper/dead/dead0" + (i + 1) + ".png");
        }

        // Attack Motion
        for (int i = 0; i < 10; i++) {
            if(i < 9) attackMotion[i] = new ImageIcon(BASE_PATH + "reaper/attack/attack00" + (i + 1) + ".png");
            else attackMotion[i] = new ImageIcon(BASE_PATH + "reaper/attack/attack0" + (i + 1) + ".png");
        }

        // Skill Motion
        for (int i = 0; i < 9; i++) skillMotion[i] = new ImageIcon(BASE_PATH + "reaper/skill/skill00" + (i + 1) + ".png");

        // Damage Motion
        for (int i = 0; i < 4; i++) damageMotion[i] = new ImageIcon(BASE_PATH + "reaper/damage/damage00" + (i + 1) + ".png");

        // Run Motion
        for (int i = 0; i < 8; i++) runMotion[i] = new ImageIcon(BASE_PATH + "reaper/run/run00" + (i + 1) + ".png");
    }
}