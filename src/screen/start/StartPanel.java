package screen.start;

import screen.game.GamePanel;
import screen.toolbar.StartToolBar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartPanel extends JPanel {
    JButton startbtn = new JButton("Game Start");
    JButton showRankingbtn = new JButton("Scores");
    JButton setWordbtn = new JButton("Word Settings");
    JButton[] btns = {startbtn, showRankingbtn, setWordbtn};

    private JFrame frame;

    private GamePanel gamePanel;

    // private StartMenuRunMotion startMenuRunMotion; // 달리는 모션 Runnable
    private Thread runThread; // 달리는 모션 스레드

    private ImageIcon sky = new ImageIcon("resources/background/StartMenuImage.png");

    public StartPanel(JFrame frame) {
        this.frame = frame;

        setLayout(null);
        setBackground(Color.BLACK);

        // 로그인 판넬 추가
        add(new LoginPanel());

        // 버튼 추가(시작 버튼, 점수, 단어 설정)
        setBtns();

        // 달리는 모션 추가
        // runMotion();
    }

    // 버튼 설정
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
                frame.getContentPane().removeAll();
                frame.getContentPane().setLayout(new BorderLayout());
                frame.getContentPane().add(new SelectModePanel(frame), BorderLayout.CENTER);
                frame.getContentPane().add(new StartToolBar(), BorderLayout.NORTH);
                frame.revalidate();
                frame.repaint();
            }
        });

        // 1. scores 버튼 클릭시 작동
        showRankingbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


            }
        });

        // 2. Word Settings 버튼 클릭시 작동
    }
    // 달리는 모션 실행
//    private void runMotion() {
//        startMenuRunMotion = new StartMenuRunMotion(this);
//        runThread = new Thread(startMenuRunMotion);
//        runThread.start();
//    }

    @Override
    protected void paintComponent(Graphics g) { // 배경 요소
        super.paintComponent(g);
        // 전체 배경 화면
        g.drawImage(sky.getImage(), 0, 0, getWidth(), getHeight(), this);

        // 달리는 모션
//        ImageIcon frame = startMenuRunMotion.getCurrentFrame();
//        g.drawImage(frame.getImage(), 400, 550, 150, 150, this);
    }
}