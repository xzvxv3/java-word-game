package runnable;

import javax.swing.*;
import java.util.HashMap;

public abstract class BaseCharacter {
    protected JPanel panel; // 부모 패널
    protected String currentMotion; // 현재 모션
    protected HashMap<String, ImageIcon[]> motionMap = new HashMap<>(); // 모션을 담고있는 해시맵
    protected ImageIcon[] motionFrames; // 현재 모션 프레임
    protected int idx = 0; // 모션 프레임 전환용

    protected int hp; // 캐릭터의 체력

    // 공격받은 시각과 현재 시각의 차이를 이용해 자연스러운 반응 딜레이를 구현
    protected long nowTime; // 현재 시간
    protected long attackTime; // 공격 받은 시간
    protected boolean attacked = false; // 공격 받았는지

    // 캐릭터 죽음 체크
    protected boolean isDead = false;

    // 데미지 딜레이
    protected int damageDelay;

    // 공격 받았는지 체크
    public void onAttacked() {
        attacked = true;
        attackTime = System.currentTimeMillis();
    }

    public BaseCharacter(JPanel panel, int hp) {
        this.panel = panel;
        this.hp = hp;
    }

    // 모션 설정
    public void setMotion(String currentMotion) {
        idx = 0; // 모션 프레임 첫장면 idx
        this.currentMotion = currentMotion; // 현재 모션
        motionFrames = motionMap.get(currentMotion); // 모션 프레임 변환
    }

    // 모션 프레임 반환
    public ImageIcon getCurrentFrame() {
        if(idx >= motionFrames.length) idx = 0; // 이미지 null 방지
        return motionFrames[idx]; // 모션 프레임 반환
    }

    public void decreaseHP() {
        hp--;
    }


}