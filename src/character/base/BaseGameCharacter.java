package character.base;

import character.imageloader.ImageLoader;
import character.type.MotionType;

import javax.swing.*;

// BaseAnimation으로 올릴 코드 더 있는지 확인할것

// 역할: 전투가 가능한 캐릭터 (HP, 피격 판정 추가)
public abstract class BaseGameCharacter extends BaseAnimation {

    // 캐릭터 좌표
    protected int x, y;

    protected int hp;
    // protected boolean isDead = false;
    protected boolean attacked = false;
    protected long nowTime, attackTime;

    // 전투 관련 딜레이 상수들
    protected final int DAMAGE_DELAY;
    protected final int DEAD_DELAY;

    // 스레드 실행중인지
    private boolean isrunning = true;

    // [추가] 인트로 이동 관련 변수
    protected int targetX; // 목표지점
    protected double moveSpeed; // 이동 속도


    public BaseGameCharacter(JPanel panel, ImageLoader loader, int hp, int x, int y,
                             int damageDelay, int deadDelay, int motionEndDelay, int frameDelay) {
        super(panel, loader, frameDelay, motionEndDelay); // 부모(애니메이션) 초기화
        this.x = x;
        this.y = y;
        this.hp = hp;
        this.DAMAGE_DELAY = damageDelay;
        this.DEAD_DELAY = deadDelay;
    }


    public int getX() { return x; }
    public int getY() { return y; }

    // [추가] 등장 세팅 메서드 (목표 위치와 도달 시간 설정)
    public void setIntroMove(int targetX, int durationMs) {
        this.targetX = targetX;
        this.isIntro = true;

        // 거리 = |목표 - 시작|
        // 프레임 횟수 = 총 시간 / 프레임 딜레이
        // 속도 = 거리 / 프레임 횟수
        int distance = targetX - x;
        int totalFrames = durationMs / super.FRAME_DELAY;
        this.moveSpeed = distance / totalFrames;

        // 걷거나 뛰는 모션이 있다면 여기서 설정 (예: setMotion(MotionType.RUN));
        setMotion(MotionType.RUN);
    }

    // [추가] 이동 로직 처리
    protected void handleIntroMovement() {
        if (!isIntro) return;

        // 목표 지점에 거의 도달했는지 확인 (오차 범위 5px)
        if (Math.abs(x - targetX) <= Math.abs(moveSpeed)) {
            x = targetX;
            isIntro = false; // 이동 종료
            setMotion(MotionType.IDLE); // 도착하면 IDLE 모션
        } else {
            x += moveSpeed; // 좌표 이동
        }
    }

    // 전투 라이프사이클 (기존 로직 이동)
    protected boolean characterLifeCycle() {

        // 스레드 종료 유도. (자연스러운 종료를 위해 2초 딜레이)
        if(!isrunning) {
            if(System.currentTimeMillis() - nowTime >= 2000) {
                return false;
            }
        }

        // [추가] 인트로 중이면 이동 로직 수행, 아니면 전투 로직 수행
        if (isIntro) {
            handleIntroMovement();
        } else {
            if (!isDead) {
                handleAttackReaction();
                handleDeadReaction();
            }


        }

        updateFrame(); // 화면 갱신 (부모 기능 사용)

        // 죽음 모션 + 스레드 종료 유도
        if (isDead && motionType == MotionType.DEAD && idx == motionFrames.length - 1) {
            return false;
        }

        return true; // 스레드 실행중
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
            try { Thread.sleep(DEAD_DELAY); } catch (InterruptedException e) { return; } // 자연스러운 모션 유도
            setMotion(MotionType.DEAD); // 죽음 모션 설정
            isDead = true; // 죽음으로 설정
        }
    }

    public void stop() {
        // 멈추고 있는중이라면 무시 -> nowTime이 재갱신 되는것을 막기 위함
        if(!isrunning) return;

        isrunning = false;
        nowTime = System.currentTimeMillis();
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
}