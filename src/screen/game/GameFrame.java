package screen.game;

import screen.game.left.GamePanel;
import screen.game.right.EditPanel;
import screen.game.right.ScorePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameFrame extends JFrame {
    private JMenuItem startItem = new JMenuItem("Start");
    private JButton startBtn = new JButton("Start");

    private ScorePanel scorePanel = new ScorePanel(); // 점수 판넬
    private EditPanel editPanel = new EditPanel(); // 편집 판넬

    private GamePanel gamePanel = new GamePanel(); // 게임 화면 판넬

    // 이미지 로딩
    ImageIcon normalIcon = new ImageIcon("test1/images/normal.png");
    ImageIcon pressedIcon = new ImageIcon("test1/images/rollover.png");
    ImageIcon rolloverIcon = new ImageIcon("test1/images/pressed.png");

    public GameFrame() {
        super("게임");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 800);
        setLocation(550, 100);

        makeMenu(); // 메뉴
        makeToolBar(); // 툴바
        makeSplit(); // 화면 분할
        setVisible(true);
    }

    // 메뉴바 생성
    private void makeMenu() {
        JMenuBar mBar = new JMenuBar();
        this.setJMenuBar(mBar);

        JMenu fileMenu = new JMenu("File");
        mBar.add(fileMenu);

        JMenu editMenu = new JMenu("Edit");
        mBar.add(editMenu);

        JMenuItem openItem = new JMenuItem("Open");
        fileMenu.add(openItem);
        fileMenu.add("Save");
        fileMenu.addSeparator();
        fileMenu.add(startItem);

        startItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //start();
            }
        });
    }


    // 툴바 생성
    private void makeToolBar() {
        JToolBar tBar = new JToolBar();
        tBar.add(startBtn);
        tBar.setFloatable(false);
        getContentPane().add(tBar, BorderLayout.NORTH);
        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //start();
            }
        });

        JButton b = new JButton(normalIcon);
        b.setPressedIcon(pressedIcon);
        b.setRolloverIcon(rolloverIcon);
        tBar.add(b);
    }

    // 화면 분할
    private void makeSplit() {
        JSplitPane hPane = new JSplitPane(); // 화면 분할 판넬1
        hPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT); // 수직 구분선
        hPane.setDividerLocation(600); // 수직 구분선 위치
        hPane.setEnabled(false); // 구분선 고정
        getContentPane().add(hPane, BorderLayout.CENTER); // 컨탠트팬에 추가

        JSplitPane vPane = new JSplitPane(); // 화면 분할 판넬2
        vPane.setOrientation(JSplitPane.VERTICAL_SPLIT); // 수평 구분선
        vPane.setDividerLocation(250); // 수평 구분선 위치
        vPane.setEnabled(false); // 구분선 고정
        vPane.setTopComponent(scorePanel); // 위는 점수 판넬
        vPane.setBottomComponent(editPanel); // 아래는 편집 판넬

        hPane.setRightComponent(vPane); // 화면 분할 판넬1의 오른쪽 배치
        hPane.setLeftComponent(gamePanel); // 화면 분할 판넬2의 왼쪽 배치
    }
}
