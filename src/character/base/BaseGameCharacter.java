package character.base;
import character.imageloader.ImageLoader;
import character.type.MotionType;
import javax.swing.*;

// 게임 캐릭터의 공통 기능을 정의하는 추상 클래스
public abstract class BaseGameCharacter extends BaseAnimation {
    // 캐릭터 좌표
    protected int x, y;
    // 캐릭터 체력
    protected int hp;
    // 캐릭터가 공격 받았는지 여부
    protected volatile boolean attacked = false;
    // 자연스러운 딜레이 유도용
    protected long nowTime, attackTime;
    // 데미지 딜레이
    protected final int DAMAGE_DELAY;
    // 죽음 딜레이
    protected final int DEAD_DELAY;
    // 스레드 실행중인지
    private volatile boolean isrunning = true;

    // 전투를 시작하는 X 좌표
    protected int targetX;
    // 이동 속도
    protected double moveSpeed;

    // 게임 캐릭터의 공통 기능을 정의하는 추상 클래스
    public BaseGameCharacter(JPanel panel, ImageLoader loader, int hp, int x, int y,
                             int damageDelay, int deadDelay, int motionEndDelay, int frameDelay) {
        super(panel, loader, frameDelay, motionEndDelay);
        this.x = x;
        this.y = y;
        this.hp = hp;
        this.DAMAGE_DELAY = damageDelay;
        this.DEAD_DELAY = deadDelay;
    }

    // 전투 라이프사이클
    protected boolean characterLifeCycle() {
        // 스레드 종료 유도 들어올 시 (자연스러운 종료를 위해 2초 딜레이)
        if(!isrunning) {
            // 2초 뒤에
            if(System.currentTimeMillis() - nowTime >= 2000) {
                // 스레드 종료
                return false;
            }
        }

        // 캐릭터 초기 등장중이라면
        if (motionType == MotionType.RUN) {
            // 캐릭터 등장 전용 모션 처리
            handleIntroMovement();
        }

        // 전투 중이라면
        else {
            if (motionType != MotionType.DEAD) {
                // 데미지를 입었다면, 데미지 모션 유도
                handleDamageReaction();
                // 죽었다면, 죽음 모션 유도
                handleDeadReaction();
            }
        }

        // 화면 갱신
        updateFrame();

        // 죽음 상태이면서, 죽음 모션의 끝에 도달했다면
        if (motionType == MotionType.DEAD && idx == motionFrames.length - 1) {
            // 스레드 종료
            return false;
        }

        // 스레드 실행중
        return true;
    }

    // 캐릭터의 현재 X좌표 반환
    public int getX() { return x; }
    // 캐릭터의 현재 Y좌표 반환
    public int getY() { return y; }

    // 캐릭터 등장 초기 설정
    public void setIntroMove(int targetX, int startDelay) {
        // 전투를 시작하는 X 좌표
        this.targetX = targetX;

        // 이동 모션으로 변환
        setMotion(MotionType.RUN);

        // 거리 = 목표 - 시작
        int distance = targetX - x;

        // 프레임 횟수 = 총 시간 / 프레임 딜레이
        int totalFrames = startDelay / super.FRAME_DELAY;

        // 속도 = 거리 / 프레임 횟수
        moveSpeed = distance / totalFrames;
    }

    // 캐릭터 등장 전용 모션 처리
    protected void handleIntroMovement() {
        // 뛰고있는 상태가 아니라면, 처리하지않음
        if (motionType != MotionType.RUN) return;

        // 목표 지점에 도착했다면
        if (Math.abs(x - targetX) <= Math.abs(moveSpeed)) {
            // 캐릭터 X 좌표 고정
            x = targetX;
            // 도착하면 IDLE 모션
            setMotion(MotionType.IDLE);
        } else {
            // 캐릭터 이동
            x += moveSpeed;
        }
    }

    // 데미지 반응
    protected void handleDamageReaction() {
        // 이미 죽은 상태 or 데미지 모션이 재생 중 -> 실행X
        if (motionType == MotionType.DEAD || motionType == MotionType.DAMAGE) return;

        // 공격 받은 상태라면 실행
        if (attacked) {
            // 데미지 모션 취하기까지의 딜레이 시간 (Man이 공격을 시작하자마자 바로 실행되면 부자연스럽기 때문)
            if (System.currentTimeMillis() - attackTime >= DAMAGE_DELAY) {
                // 모션 변경
                setMotion(MotionType.DAMAGE);
                // 공격 받은 상태 끝남
                attacked = false;
            }
        }
    }

    // 죽음 모션 처리
    protected void handleDeadReaction() {
        // 피가 0 이하이면서 죽지 않은 상태라면
        if(hp <= 0 && motionType != MotionType.DEAD) {
            // 딜레이로 자연스러운 죽음 모션 유도
            try { Thread.sleep(DEAD_DELAY); } catch (InterruptedException e) { return; }
            // 죽음 모션 설정
            setMotion(MotionType.DEAD);
        }
    }

    // 캐릭터 스레드 종료
    public void stop() {
        // 멈추고 있는중이라면 무시 -> nowTime이 재갱신 되는것을 막기 위함
        if(!isrunning) return;
        // 스레드 종료 신호 보냄
        isrunning = false;
        // 자연스러운 종료 위한 현재 시간 저장
        nowTime = System.currentTimeMillis();
    }

    // 공격 받았을 때
    public void onAttacked() {
        // 공격 받은 상태로 체크
        attacked = true;
        // 공격 받은 시간
        attackTime = System.currentTimeMillis();
    }

    // 캐릭터가 데미지를 입었을때
    public void decreaseHP(int amount) {
        // 체력 감소
        hp -= amount;
        // 공격 받았을 때
        onAttacked();
    }

    // 캐릭터 현재 HP 반환
    public int getCurrentHp() {
        return hp;
    }

    // 캐릭터 HP 설정
    public void setHP(int hp) {
        this.hp = hp;
    }
}