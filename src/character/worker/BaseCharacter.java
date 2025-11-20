package character.worker;


import character.MotionType;

import javax.swing.*;
import java.util.HashMap;

public abstract class BaseCharacter {
    protected JPanel panel; // 부모 패널
    protected MotionType motionType; // 현재 모션
    protected HashMap<MotionType, ImageIcon[]> motionMap = new HashMap<>(); // 모션을 담고있는 해시맵
    protected ImageIcon[] motionFrames; // 현재 모션 프레임
    protected int idx = 0; // 모션 프레임 전환용

    protected final int DAMAGE_DELAY; // 데미지 딜레이
    protected final int DEAD_DEALY; // 죽음 딜레이
    protected final int MOTION_END_DELAY; // 모션 죽음 딜레이
    protected final int FRAME_DELAY; // 프레임 전환 딜레이

    protected int hp; // 캐릭터의 체력

    // 공격받은 시각과 현재 시각의 차이를 이용해 자연스러운 반응 딜레이를 구현
    protected long nowTime; // 현재 시간
    protected long attackTime; // 공격 받은 시간
    protected boolean attacked = false; // 공격 받았는지

    // 캐릭터 죽음 체크
    protected boolean isDead = false;

    // 공격 받았는지 체크
    public void onAttacked() {
        attacked = true;
        attackTime = System.currentTimeMillis();
    }

    // 체력 깎일시, 데미지 모션, 죽음 모션 리팩토링 할것
    public void decreaseHP(int amount) {
        hp -= amount;
    }

    public int getCurrentHp() {
        return hp;
    }

    public BaseCharacter(JPanel panel, int hp, int damageDelay, int deadDelay, int motionEndDealy, int frameDelay) {
        this.panel = panel;
        this.hp = hp;
        this.DAMAGE_DELAY = damageDelay;
        this.DEAD_DEALY = deadDelay;
        this.MOTION_END_DELAY = motionEndDealy;
        this.FRAME_DELAY = frameDelay;
    }

    // 모션 설정
    public void setMotion(MotionType motionType) {
        idx = 0; // 모션 프레임 첫장면 idx
        this.motionType = motionType; // 현재 모션
        motionFrames = motionMap.get(motionType); // 모션 프레임 변환
    }

    // 모션 프레임 반환
    public ImageIcon getCurrentFrame() {
        if(idx >= motionFrames.length) idx = 0; // 이미지 null 방지
        return motionFrames[idx]; // 모션 프레임 반환
    }


    protected boolean characterLifeCycle() {
        // 공격 당했을경우
        handleAttackReaction();

        // 죽었을 경우
        handleDeadReaction();

        // 모션 프레임 실행중
        return handleFrameUpdate();
    }

    protected void handleAttackReaction() {
        if(!attacked || isDead) return; // 공격 받은 상태(X) or 이미 죽은 상태(O) -> 실행 안 함

        nowTime = System.currentTimeMillis(); // 현재 시간

        if (nowTime - attackTime >= DAMAGE_DELAY) { // 데미지 딜레이 유도
            setMotion(MotionType.DAMAGE);
            attacked = false;
        }
    }

    protected void handleDeadReaction() {
        if(hp <= 0 && !isDead) { // 피가 0 이하이면서 죽지 않은 상태라면
            try { Thread.sleep(DEAD_DEALY); } catch (InterruptedException e) { return; } // 자연스러운 모션 유도
            setMotion(MotionType.DEAD); // 죽음 모션 설정
            isDead = true; // 죽음으로 설정
        }
    }

    protected boolean handleFrameUpdate() {
        idx = (idx + 1) % motionFrames.length;
        panel.repaint();

        // 마지막 프레임일 때만 onMotionEnd() 호출
        if (idx == motionFrames.length - 1) {
            if (isDead) return false; // 죽으면 더 이상 Idle로의 전환은 없음

            onMotionEnd();
            try { Thread.sleep(MOTION_END_DELAY); } catch (InterruptedException e) { return false; }
        }

        // 프레임 간 딜레이
        try { Thread.sleep(FRAME_DELAY); } catch (InterruptedException e) { return false; }

        return true;
    }

    protected abstract void onMotionEnd();
}