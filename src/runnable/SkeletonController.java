package runnable;

import javax.swing.*;

public class SkeletonController extends BaseCharacter implements Runnable {
    public SkeletonController(JPanel panel, int hp) {
        super(panel,  hp);
        setImageMotions();
        initCharacter();
    }


    @Override
    public void run() {
        while (true) {
            if (attacked) { // 공격 당했을 경우
                if(!isDead) { // 살아 있는 경우에만 공격
                    nowTime = System.currentTimeMillis();
                    if (nowTime - attackTime >= 250) { // 1초 지연 후
                        setMotion("DAMAGE");
                        attacked = false;
                    }
                }
            }

            // 죽었을 경우
            if(hp <= 0 && !isDead) {
                isDead = true;
                try { Thread.sleep(400); } catch (InterruptedException e) { break; } // 자연스러운 모션 유도
                setMotion("DEAD");
            }

            idx = (idx + 1) % motionFrames.length; // 프레임 변경
            panel.repaint(); // 부모 패널 다시 그리기, 부모는 바뀐 프레임을 호출할 예정


            if(idx == motionFrames.length - 1) {

                if(isDead) break;

                if(!currentMotion.equals("IDLE")) {
                    motionFrames = motionMap.get("IDLE");
                }

                // 마지막 장면 0.15초 딜레이 (자연스러운 모션 유도)
                try { Thread.sleep(140); } catch (InterruptedException e) { break; }
            }
            // 프레임 속도
            try { Thread.sleep(100); } catch (InterruptedException e) { break; }
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
