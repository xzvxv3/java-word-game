package runnable;

import javax.swing.*;

public class ScareCrowController extends BaseCharacter implements Runnable{
    public ScareCrowController(JPanel panel, int hp) {
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
                    if (nowTime - attackTime >= 350) { // 1초 지연 후
                        setMotion("DAMAGE");
                        attacked = false;
                    }
                }
            }

            // 죽었을 경우
            if(hp <= 0 && !isDead) {
                isDead = true;
                try { Thread.sleep(600); } catch (InterruptedException e) { break; } // 자연스러운 모션 유도
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
        motionMap.put("DAMAGE", setDamageMotion());
    }

    // 버섯 모션
    private ImageIcon[] setIdleMotion() {
        ImageIcon[] tmp = new ImageIcon[3];
        for(int i=0; i<3; i++) {
            tmp[i] = new ImageIcon("resources/sprites/scarecrow/idle/ScareCrowIDie00" + (i + 1) + ".png");
        }
        return tmp;
    } // 정지 모션
    private ImageIcon[] setDeadMotion() {
        ImageIcon[] tmp = new ImageIcon[16];
        for(int i=0; i<16; i++) {
            if(i < 9) {
                tmp[i] = new ImageIcon("resources/sprites/scarecrow/dead/ScareCrowDead00" + (i + 1) + ".png");
            }
            else if(i >= 9) {
                tmp[i] = new ImageIcon("resources/sprites/scarecrow/dead/ScareCrowDead0" + (i + 1) + ".png");
            }
        }
        return tmp;
    } // 죽음 모션
    private ImageIcon[] setDamageMotion() {
        ImageIcon[] tmp = new ImageIcon[9];
        for(int i=0; i<9; i++) {
            tmp[i] = new ImageIcon("resources/sprites/scarecrow/damage/ScareCrowHit00" + (i + 1) + ".png");
        }
        return tmp;
    } // 데미지 모션
}
