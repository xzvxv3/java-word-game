package screen.game.right;

import character.CharacterManager;
import character.EnemyType;

import javax.swing.*;
import java.awt.*;

// CharacterManager을 불러와서, Sword인지 Empty인지에 따라 데미지 다르게 할 것

// 점수 판넬
public class ScorePanel extends JPanel {
    private int score = 0; // 초기 점수
    private final int WEAPON_UNLOCK_SCORE = 2;

    private ScoreTextLabel scoreTextLabel = new ScoreTextLabel();
    private ScoreLabel scoreLabel = new ScoreLabel();

    private ImageIcon manHpIconImage = new ImageIcon("resources/icon/hp/ManHpIconImage.png");
    private ImageIcon mushroomHpIconImage = new ImageIcon("resources/icon/hp/MushroomHpIconImage.png");
    private ImageIcon reaperHpIconImage = new ImageIcon("resources/icon/hp/ReaperHpIconImage.png");
    private ImageIcon wolfHpIconImage = new ImageIcon("resources/icon/hp/WolfHpIconImage.png");
    private ImageIcon scarecrowIconImage = new ImageIcon("resources/icon/hp/ScarecrowHpIconImage.png");

    private EnemyType enemyType;

    private ManHpBar manHpBar;
    private EnemyHpBar enemyHpBar;

    public ScorePanel(EnemyType enemyType) {
        this.enemyType = enemyType;
        this.setBackground(Color.GRAY);
        setLayout(null);

        add(scoreTextLabel);
        add(scoreLabel);
    }

    public void setHpBar(CharacterManager characterManager) {
        manHpBar = new ManHpBar(characterManager.getManHP());
        enemyHpBar = new EnemyHpBar(characterManager.getEnemyHP());

        add(manHpBar);
        add(enemyHpBar);
    }

    // Man 체력바
    class ManHpBar extends JProgressBar {
        public ManHpBar(int hp) {
            super(0, hp);
            setValue(hp);

            setStringPainted(false); // HP 수치를 텍스트로 표시하도록 변경 (옵션)

            // 위치 + 크기 필수
            setBounds(87, 100, 180, 20);  // (x, y, w, h)

            //setOpaque(false);
            setBorderPainted(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            int w = getWidth();
            int h = getHeight();

            int hpWidth = (int) (w * (getValue() / (double) getMaximum()));

            Graphics2D g2 = (Graphics2D) g;

            // 빈 부분 (회색)
            g2.setColor(Color.WHITE);
            g2.fillRect(0, 0, w, h);

            // 남은 HP 부분 (빨간색)
            g2.setColor(Color.GREEN);
            g2.fillRect(0, 0, hpWidth, h);
        }

        public void decrease(int amount) {
            int newHp = Math.max(0, getValue() - amount);
            setValue(newHp);
            repaint();   // 화면 갱신
        }

        public void heal() {
            setValue(getValue() + 20);
            repaint();
        }

        public void setHP(int hp) {
            setValue(hp);
        }
    }

    // 적 체력바
    class EnemyHpBar extends JProgressBar {
        public EnemyHpBar(int hp) {
            super(0, hp);

            setValue(hp);

            setStringPainted(false); // HP 수치를 텍스트로 표시하도록 변경 (옵션)

            // 위치 + 크기 필수
            setBounds(87, 190, 180, 20);  // (x, y, w, h)

            setOpaque(false);
            setBorderPainted(false);
        }

        public void setHP(int hp) {
            setValue(hp);
        }

        @Override
        protected void paintComponent(Graphics g) {
            int w = getWidth();
            int h = getHeight();

            int hpWidth = (int) (w * (getValue() / (double) getMaximum()));

            Graphics2D g2 = (Graphics2D) g;

            // 빈 부분 (회색)
            g2.setColor(Color.WHITE);
            g2.fillRect(0, 0, w, h);

            // 남은 HP 부분 (빨간색)
            g2.setColor(Color.RED);
            g2.fillRect(0, 0, hpWidth, h);
        }

        public void decrease(int amount) {
            int newHp = Math.max(0, getValue() - amount);
            setValue(newHp);
            repaint();   // 화면 갱신
        }
    }

    // Score 라벨
    class ScoreTextLabel extends JLabel {
        public ScoreTextLabel() {
            setText("SCORE");
            setSize(150, 100);
            setLocation(60, -10);
            setFont(new Font("맑은 고딕", Font.BOLD, 30));
        }
    }

    // Score 점수 현황 라벨
    class ScoreLabel extends JLabel {
        public ScoreLabel() {
            setText(Integer.toString(score));
            setSize(200, 100);
            setLocation(180, -10);
            setFont(new Font("맑은 고딕", Font.BOLD, 30));
        }
    }

    // Man 체력 증가
    public void healManHP() {
        manHpBar.heal();
    }

    // Man 체력 감소 (단어 바닥에 낙하시)
    public void decreaseManHP(int amount) {
        manHpBar.decrease(amount);
    }

    // Enemy 체력 감소 (단어 맞출시)
    public void decreaseEnemyHp(int amount) {
        enemyHpBar.decrease(amount);
    }

    // 점수 증가 & 점수 현황판 최신화 & Enemy 체력 감소
    public void increaseScore(int amount) {
        score += amount;
        scoreLabel.setText(Integer.toString(score));
        decreaseEnemyHp(amount);
    }

    public boolean isPossibleChangeWeapon() {
        return score >= WEAPON_UNLOCK_SCORE;
    }

    @Override
    protected void paintComponent(Graphics g) { // 배경 요소
        super.paintComponent(g);
        // 전체 배경 화면
        g.drawImage(manHpIconImage.getImage(), -70, -10, 250, 250, this);

        switch (enemyType) {
            case SCARECROW : g.drawImage(scarecrowIconImage.getImage(), -70, 80, 250, 250, this); break;
            case MUSHROOM : g.drawImage(mushroomHpIconImage.getImage(), -70, 80, 250, 250, this); break;
            case WOLF : g.drawImage(wolfHpIconImage.getImage(), -70, 80, 250, 250, this); break;
            case REAPER : g.drawImage(reaperHpIconImage.getImage(), -70, 80, 250, 250, this); break;
        }
    }
}
