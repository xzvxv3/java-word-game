package word;
import javax.swing.*;
import java.awt.*;

// 단어 낙하에 쓰이는 클래스
public class Word extends JLabel {
    // 게임 화면의 높이
    private int viewHeight;
    // 단어 이름
    private String text;

    public Word(String text, int viewHeight, int width, int height, int x, int y) {
        super(text);
        this.text = text;
        this.viewHeight = viewHeight;

        // 라벨 안 단어 가운데 정렬
        setHorizontalAlignment(SwingConstants.CENTER);

        // 단어 폰트, 크기
        setFont(new Font("맑은 고딕", Font.BOLD, 20));
        // 라벨 크기
        setSize(width, height);

        // 단어 색상
        setForeground(Color.WHITE);

        // 단어 위치
        setLocation(x, y);
    }

    // 단어 낙하시키기
    public void fall() {
        setLocation(getX(), getY() + 10);
    }

    // 단어 바닥 충돌 판정 여부
    public boolean isAtBottom() {
        return getY() > viewHeight - getHeight() - 75;
    }

    // 단어 텍스트 반환
    public String getText() {
        return text;
    }
}
