package ui;

import ui.intro.LoginPanel;
import ui.menu.MenuPanel;
import ui.toolbar.StartToolBar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameFrame extends JFrame {
    private JMenuItem startItem = new JMenuItem("Start");

    private MenuPanel menuPanel = new MenuPanel(this);

    private LoginPanel loginPanel = new LoginPanel(this);


    public GameFrame() {
        super("게임");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 800);
        setLocation(350, 100);

        makeMenu(); // 메뉴
        makeToolBar(); // 툴바

        start(); // 게임 초기 화면

        setVisible(true);
    }

    // 게임 초기 화면
    private void start() {
        getContentPane().add(menuPanel, BorderLayout.CENTER);
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
        getContentPane().add(new StartToolBar(), BorderLayout.NORTH);
    }
}
