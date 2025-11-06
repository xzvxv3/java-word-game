package runnable.core;

import javax.swing.*;
import java.util.HashMap;

public class Character {
    protected JPanel panel;
    protected int idx = 0;

    // 각 캐릭터의 모션 집합
    // protected final HashMap<String,  ImageIcon[]> motions = new HashMap<>();

    // 1. 부모의 패널 받아오기 -> 2. 이미지 설정하기
    public Character(JPanel panel) {
        this.panel = panel;
    }
}
