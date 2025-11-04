package data;

// 단어
public class Word {
    private String text;
    private int x, y;
    private final int FALL_SPEED = 10;

    public Word(String text, int x, int y) {
        this.text = text;
        this.x = x;
        this.y = y;
    }

    public String getText() { return text; }
    public int getX() {return x;}
    public int getY() {return y;}

    public void fall() {
        y += FALL_SPEED;
    }
}