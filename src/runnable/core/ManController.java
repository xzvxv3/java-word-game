package runnable.core;

import javax.swing.*;

public class ManController extends BaseCharacter implements Runnable {
    private String weapon; // 캐릭터의 무기

    public ManController(JPanel panel, String weapon, int hp) {
        super(panel, hp); // 부모 패널, 캐릭터 체력
        this.weapon = weapon; // 캐릭터 무기
        setImageMotions(); // 캐릭터의 모션들 초기화
        initCharacter(); // 시작 시, 캐릭터의 모션 설정 (기본 IDLE)
        damageDelay = 450;
    }

    @Override
    public void run() {
        while (true) {
            if (attacked) { // 공격 당했을 경우
                if(!isDead) { // 살아 있는 경우에만 공격
                    nowTime = System.currentTimeMillis();
                    if (nowTime - attackTime >= 450) { // 1초 지연 후
                        setMotion("DAMAGE");
                        attacked = false;
                    }
                }
            }

            // 죽었을 경우
            if(hp <= 0 && !isDead) {
                isDead = true;
                try { Thread.sleep(800); } catch (InterruptedException e) { break; } // 자연스러운 모션 유도
                setMotion("DEAD");
            }

            idx = (idx + 1) % motionFrames.length; // 프레임 변경
            panel.repaint(); // 부모 패널 다시 그리기, 부모는 바뀐 프레임을 호출할 예정

            // Idle 상태가 아니면서 Attack 모션이 끝날 경우 Idle 상태로 복귀
            if(idx == motionFrames.length - 1) {

                // 죽으면 더이상 모션 X
                if(isDead) break;

                // 기본 모드의 IDLE 전환
                if(weapon.equals("EMPTY") && !currentMotion.equals("IDLE")) {
                    motionFrames = motionMap.get("IDLE");
                }
                // 검 모드의 IDLE 전환
                else if(weapon.equals("SWORD") && !currentMotion.equals("SWORD_IDLE")) {
                    motionFrames = motionMap.get("SWORD_IDLE");
                }
                // 마지막 장면 0.15초 딜레이 (자연스러운 모션 유도)
                try { Thread.sleep(200); } catch (InterruptedException e) { break; }
            }
            // 프레임 전환속도
            try { Thread.sleep(100); } catch (InterruptedException e) { break; }
        }
    }


    private void initCharacter() {
        switch (weapon) {
            case "EMPTY" : // 기본 모드
                currentMotion = "IDLE";
                motionFrames = motionMap.get("IDLE");
                break;
            case "SWORD" : // 검 모드
                currentMotion = "SWORD_IDLE";
                motionFrames = motionMap.get("SWORD_IDLE");
                break;
        }
    }

    // 현재 무기 상태 반환
    public String getCurrentWeapon() {return weapon;}

    private void setImageMotions() {
        // 기본 모션
        motionMap.put("IDLE", setIdleMotion());
        motionMap.put("ATTACK01", setKickMotion01());
        motionMap.put("ATTACK02", setKickMotion02());
        motionMap.put("ATTACK03", setPunchMotion01());
        motionMap.put("ATTACK04", setPunchMotion02());

        // 검 모션
        motionMap.put("SWORD_IDLE", setSwordIdleMotion());
        motionMap.put("SWORD_ATTACK01", setSwordAttackMotion01());
        motionMap.put("SWORD_ATTACK02", setSwordAttackMotion02());
        motionMap.put("SWORD_ATTACK03", setSwordAttackMotion03());
        motionMap.put("SWORD_ATTACK04", setSwordAttackMotion04());

        // 공통 모션
        motionMap.put("DEAD", setDeadMotion());
        motionMap.put("DAMAGE", setDamageMotion());
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
    private ImageIcon[] setPunchMotion01() {
        ImageIcon[] tmp = new ImageIcon[7];
        for (int i = 0; i < 7; i++) {
            tmp[i] = new ImageIcon("resources/sprites/original/punch01/Punch030" + (i + 1) + ".png");
        }
        return tmp;
    } // 펀치 모션 1
    private ImageIcon[] setPunchMotion02() { // 펀치 모션 2
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
