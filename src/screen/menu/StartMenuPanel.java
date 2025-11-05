package screen.menu;

import runnable.RunAnimation;
import screen.play.GameSplitPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartMenuPanel extends JPanel {
    JButton startbtn = new JButton("Game Start");
    JButton showRanking = new JButton("Scores");
    JButton setWord = new JButton("Word Settings");
    JButton[] btns = {startbtn, showRanking, setWord};


    private RunAnimation runAnimation; // 달리는 모션 Runnable
    private Thread runThread; // 달리는 모션 스레드

    private ImageIcon sky = new ImageIcon("resources/background/menu.png");

    public StartMenuPanel() {
        setLayout(null);
        setBackground(Color.BLACK);

        // 로그인 판넬 추가
        add(new LoginPanel());

        // 버튼 추가(시작 버튼, 점수, 단어 설정)
        setBtns();

        // 달리는 모션 추가
        runMotion();
    }

    private void setBtns() {
        for(int i=0; i < btns.length; i++) {
            btns[i].setSize(330, 50);
            btns[i].setLocation(300, 350 + 70 * i);
            add(btns[i]);
        }

        // 게임시작 버튼 클릭 시 작동
        startbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 현재 패널이 올라가 있는 JFrame 찾기
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(StartMenuPanel.this);
                // 기존 컴포넌트 제거
                frame.getContentPane().removeAll();
                // 새 화면 추가
                frame.getContentPane().add(new GameSplitPane(), BorderLayout.CENTER);
                // 변경 사항 반영
                frame.getContentPane().revalidate();
                frame.getContentPane().repaint();

                runThread.interrupt();
            }
        });

        // 1. scores 버튼 클릭시 작동

        // 2. Word Settings 버튼 클릭시 작동

    }
    // 달리는 모션 실행
    private void runMotion() {
        runAnimation = new RunAnimation(this);
        runThread = new Thread(runAnimation);
        runThread.start();
    }

    @Override
    protected void paintComponent(Graphics g) { // 배경 요소
        super.paintComponent(g);
        // 전체 배경 화면
        g.drawImage(sky.getImage(), 0, 0, getWidth(), getHeight(), this);

        // 달리는 모션
        ImageIcon frame = runAnimation.getCurrentFrame();
        g.drawImage(frame.getImage(), 400, 550, 150, 150, this);
    }
}