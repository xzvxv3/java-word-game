package ui.game.right;
import manager.CharacterManager;
import javax.swing.*;
import java.awt.*;

// 점수 판넬
public class ScorePanel extends JPanel {
    // 이미지 기본 경로
    private static final String BG_PATH = "resources/images/element/ingame/";

    // 초기 점수
    private int score = 0;

    // 점수 글자 라벨
    private ScoreTextLabel scoreTextLabel = new ScoreTextLabel();
    // 점수 숫자 라벳
    private ScoreLabel scoreLabel = new ScoreLabel();

    // 캐릭터 프로필 사진
    private ImageIcon manHpIconImage = new ImageIcon(BG_PATH + "ManHpIconImage.png");
    private ImageIcon scarecrowIconImage = new ImageIcon(BG_PATH + "ScarecrowHpIconImage.png");
    private ImageIcon mushroomHpIconImage = new ImageIcon(BG_PATH + "MushroomHpIconImage.png");
    private ImageIcon wolfHpIconImage = new ImageIcon(BG_PATH + "WolfHpIconImage.png");
    private ImageIcon reaperHpIconImage = new ImageIcon(BG_PATH + "ReaperHpIconImage.png");

    // 주인공 체력바
    private ManHpBar manHpBar;
    // 몬스터 체력바
    private EnemyHpBar enemyHpBar;

    // 캐릭터 관리자
    private CharacterManager characterManager;

    // 점수 패널
    public ScorePanel(CharacterManager characterManager) {
        this.setBackground(Color.GRAY);
        setLayout(null);
        this.characterManager = characterManager;

        // 점수 글자 라벨 추가
        add(scoreTextLabel);
        // 점수 숫자 라벨 추가
        add(scoreLabel);
    }

    // 체력바 설정
    public void setHpBar(int manHP, int monsterHP) {
        manHpBar = new ManHpBar(manHP);
        enemyHpBar = new EnemyHpBar(monsterHP);
        add(manHpBar);
        add(enemyHpBar);
    }

    // Man 체력바
    class ManHpBar extends JProgressBar {
        public ManHpBar(int hp) {
            // 체력바의 최솟값, 최댓값
            super(0, hp);
            // 초기 상태
            setValue(hp);
            setBounds(87, 100, 180, 20);
        }

        @Override
        protected void paintComponent(Graphics g) {
            // 컴포넌트의 현재 크기 가져오기
            int w = getWidth();
            int h = getHeight();

            // 현재 체력과 전체 체력의 비율 계산 -> 체력바의 너비 설정
            int hpWidth = (int) (w * (getValue() / (double) getMaximum()));

            // 체력이 깎인 빈 공간
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, w, h);

            // 남은 체력
            g.setColor(Color.GREEN);
            g.fillRect(0, 0, hpWidth, h);
        }

        // 체력 감소
        public void decrease(int amount) {
            int newHp = Math.max(0, getValue() - amount);
            setValue(newHp);
            repaint();
        }

        // 체력 증가
        public void heal() {
            setValue(getValue() + 20);
            repaint();
        }
    }

    // 적 체력바
    class EnemyHpBar extends JProgressBar {
        public EnemyHpBar(int hp) {
            super(0, hp);
            setValue(hp);
            setBounds(87, 190, 180, 20);  // (x, y, w, h)
        }

        @Override
        protected void paintComponent(Graphics g) {
            // 컴포넌트의 현재 크기 가져오기
            int w = getWidth();
            int h = getHeight();

            // 현재 체력과 전체 체력의 비율 계산 -> 체력바의 너비 설정
            int hpWidth = (int) (w * (getValue() / (double) getMaximum()));

            // 체력이 깎인 빈 공간
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, w, h);

            // 남은 체력
            g.setColor(Color.RED);
            g.fillRect(0, 0, hpWidth, h);
        }

        // 체력 감소
        public void decrease(int amount) {
            int newHp = Math.max(0, getValue() - amount);
            setValue(newHp);
            repaint();
        }
    }

    // 점수 글자 라벨
    class ScoreTextLabel extends JLabel {
        public ScoreTextLabel() {
            setText("SCORE");
            setSize(150, 100);
            setLocation(75, -10);
            setFont(new Font("맑은 고딕", Font.BOLD, 30));
        }
    }

    // 점수 숫자 라벨
    class ScoreLabel extends JLabel {
        public ScoreLabel() {
            setText(Integer.toString(score));
            setSize(200, 100);
            setLocation(195, -10);
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

    // 무기 변경 가능한지
    public boolean isPossibleChangeWeapon() {
        return score >= characterManager.getWeaponUnlockScore();
    }

    @Override
    protected void paintComponent(Graphics g) { // 배경 요소
        super.paintComponent(g);
        // 전체 배경 화면
        g.drawImage(manHpIconImage.getImage(), -70, -10, 250, 250, this);

        // 몬스터 프로필
        switch (characterManager.getMonsterType()) {
            case SCARECROW : g.drawImage(scarecrowIconImage.getImage(), -70, 80, 250, 250, this); break;
            case MUSHROOM : g.drawImage(mushroomHpIconImage.getImage(), -70, 80, 250, 250, this); break;
            case WOLF : g.drawImage(wolfHpIconImage.getImage(), -70, 80, 250, 250, this); break;
            case REAPER : g.drawImage(reaperHpIconImage.getImage(), -70, 80, 250, 250, this); break;
        }
    }
}
