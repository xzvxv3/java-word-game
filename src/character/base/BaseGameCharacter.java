package character.base;

import character.imageloader.ImageLoader;
import character.type.MotionType;

import javax.swing.*;

// BaseAnimation으로 올릴 코드 더 있는지 확인할것

// 역할: 전투가 가능한 캐릭터 (HP, 피격 판정 추가)
public abstract class BaseGameCharacter extends BaseAnimation {

    protected int hp;
    // protected boolean isDead = false;
    protected boolean attacked = false;
    protected long attackTime;

    // 전투 관련 딜레이 상수들
    protected final int DAMAGE_DELAY;
    protected final int DEAD_DELAY;

    private long nowTime;

    public BaseGameCharacter(JPanel panel, ImageLoader loader, int hp,
                             int damageDelay, int deadDelay, int motionEndDelay, int frameDelay) {
        super(panel, loader, frameDelay, motionEndDelay); // 부모(애니메이션) 초기화
        this.hp = hp;
        this.DAMAGE_DELAY = damageDelay;
        this.DEAD_DELAY = deadDelay;
    }

    // 전투 라이프사이클 (기존 로직 이동)
    protected boolean characterLifeCycle() {
        if (!isDead) {
            handleAttackReaction();
            handleDeadReaction();   // 여기서 isDead = true가 될 수 있음
        }

        updateFrame(); // 화면 갱신 (부모 기능 사용)

        if (isDead && motionType == MotionType.DEAD && idx == motionFrames.length - 1) {
            return false; // 루프 종료 신호
        }

        return true;
    }

    // 공격 받았는지 체크
    public void onAttacked() {
        attacked = true;
        attackTime = System.currentTimeMillis();
    }

    // 체력 깎일시, 데미지 모션, 죽음 모션 리팩토링 할것
    public void decreaseHP(int amount) {
        hp -= amount;
    }

    public int getCurrentHp() { return hp; }

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
            try { Thread.sleep(DEAD_DELAY); } catch (InterruptedException e) { return; } // 자연스러운 모션 유도
            setMotion(MotionType.DEAD); // 죽음 모션 설정
            isDead = true; // 죽음으로 설정
        }
    }
}