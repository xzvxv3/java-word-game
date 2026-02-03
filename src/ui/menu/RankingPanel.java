package ui.menu;
import character.type.MonsterType;
import user.User;
import manager.LoginManager;
import manager.SoundManager;
import manager.UserManager;
import ui.common.GameImageButton;
import ui.common.RankingNameLabel;
import ui.common.RankingScoreLabel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class RankingPanel extends JPanel {
    // 이미지 기본 경로
    private final String IMG_PATH = "/images/";
    private ImageIcon backgroundImage = new ImageIcon(getClass().getResource(IMG_PATH + "background/common/default.png"));
    private ImageIcon rankingTitleImage = new ImageIcon(getClass().getResource(IMG_PATH + "element/ranking/ranking_title_lbl4.png"));
    // JFrame
    private JFrame frame = null;

    // 뒤로가기 버튼
    private JButton backBtn = new GameImageButton(
            IMG_PATH + "button/common/back_btn.png",
            IMG_PATH + "button/common/back_btn_rollover.png"
    );

    // 로그인 관리자
    private LoginManager loginManager = null;
    // 랭킹 관리자
    private UserManager userManager = null;

    public RankingPanel(JFrame frame, UserManager userManager, LoginManager loginManager) {
        this.frame = frame;
        this.userManager = userManager;
        this.loginManager = loginManager;
        setLayout(null);

        // 뒤로가기 버튼 추가
        addBackBtn();
        // 랭킹 보여주기
        showRankings();
    }

    private void showRankings() {
        // EasyMode 랭킹 보여주기
        showEasyModeRanking();
        // NormalMode 랭킹 보여주기
        showNormalModeRanking();
        // HardMode 랭킹 보여주기
        showHardModeRanking();
    }

    private void showEasyModeRanking() {
        // 유저 관리자로부터 Easy Mode에 관한 랭킹 정보를 받아옴
        Vector<User> v = userManager.getRankingsByMode(MonsterType.MUSHROOM);

        // 모든 유저 탐색
        for(int i=0; i<v.size(); i++) {
            // 유저
            User u = v.get(i);
            // 유저 이름
            String name = u.getId();
            // 유저 점수
            String score = Integer.toString(u.getEasyModeScore());

            // 유저 이름 라벨 생성
            RankingNameLabel j = new RankingNameLabel(name);
            // 유저 점수 라벨 생성
            RankingScoreLabel a = new RankingScoreLabel(score);

            // 라벨의 크기와 위치 설정
            j.setBounds(110, 200 + 45 * i, 120, 30);
            a.setBounds(240, 200 + 45 * i, 70, 30);

            // 패널에 추가
            add(j);
            add(a);

            // 상위 8명까지만 보여줌.
            if(i == 7) break;
        }
    }

    private void showNormalModeRanking() {
        // 유저 관리자로부터 Normal Mode에 관한 랭킹 정보를 받아옴
        Vector<User> v = userManager.getRankingsByMode(MonsterType.WOLF);

        // 모든 유저 탐색
        for(int i=0; i<v.size(); i++) {
            // 유저
            User u = v.get(i);
            // 유저 이름
            String name = u.getId();
            // 유저 점수
            String score = Integer.toString(u.getNormalModeScore());

            // 유저 이름 라벨 생성
            RankingNameLabel j = new RankingNameLabel(name);
            // 유저 점수 라벨 생성
            RankingScoreLabel a = new RankingScoreLabel(score);

            // 라벨의 크기와 위치 설정
            j.setBounds(450, 200 + 45 * i, 120, 30);
            a.setBounds(580, 200 + 45 * i, 70, 30);

            // 패널에 추가
            add(j);
            add(a);

            // 상위 8명까지만 보여줌.
            if(i == 7) break;
        }
    }

    private void showHardModeRanking() {
        // 유저 관리자로부터 Hard Mode에 관한 랭킹 정보를 받아옴
        Vector<User> v = userManager.getRankingsByMode(MonsterType.REAPER);

        // 모든 유저 탐색
        for(int i=0; i<v.size(); i++) {
            // 유저
            User u = v.get(i);
            // 유저 이름
            String name = u.getId();
            // 유저 점수
            String score = Integer.toString(u.getHardModeScore());

            // 유저 이름 라벨 생성
            RankingNameLabel j = new RankingNameLabel(name);
            // 유저 점수 라벨 생성
            RankingScoreLabel a = new RankingScoreLabel(score);

            // 라벨의 크기와 위치 설정
            j.setBounds(800, 200 + 45 * i, 120, 30);
            a.setBounds(930, 200 + 45 * i, 70, 30);

            // 패널에 추가
            add(j);
            add(a);

            // 상위 8명까지만 보여줌.
            if(i == 7) break;
        }
    }

    // 뒤로가기 버튼 추가
    private void addBackBtn() {
        backBtn.setBounds(50, 630, 140, 55);
        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                frame.getContentPane().add(new MenuPanel(frame, loginManager, userManager), BorderLayout.CENTER);
                frame.revalidate();
                frame.repaint();
                SoundManager.getAudio().play("/sounds/back_btn.wav");
                System.out.println("[메뉴 화면]");
            }
        });
        add(backBtn);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // 배경화면 이미지
        g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
        // 랭킹 제목 이미지
        g.drawImage(rankingTitleImage.getImage(), 15, 15, 1070, 600, this);
    }
}


