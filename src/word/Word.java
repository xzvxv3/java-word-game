package word;

import javax.swing.*;
import java.awt.*;

public class Word extends JLabel {
    private int viewHeight;
    private String text;

    public Word(String text, int viewHeight, int width, int height, int x, int y) {
        super(text); // 단어 이름
        this.text = text;
        this.viewHeight = viewHeight;

        setHorizontalAlignment(SwingConstants.CENTER); // 라벨 안 단어 가운데 정렬

        setFont(new Font("맑은 고딕", Font.BOLD, 20)); // 단어 폰트, 크기
        setSize(width, height); // 라벨 크기

        setForeground(Color.WHITE); // 단어 색상

        setLocation(x, y); // 단어 위치
    }

    // 단어 떨어트리기
    public void fall() {
        setLocation(getX(), getY() + 10); // 임시 설정. 수정 필요
    }

    // 단어 바닥 충돌 판정 여부
    public boolean isAtBottom() {
        return getY() > viewHeight - getHeight() - 50;
    }

    public String getText() {
        return text;
    }
}
