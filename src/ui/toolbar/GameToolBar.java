package ui.toolbar;

import ui.menu.SelectModePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameToolBar extends JToolBar {
    private JButton backBtn = new JButton("Back");
    private JButton startBtn = new JButton("Start");
    private JFrame frame;

    // 사용자 아이디
    private String id = null;

    public GameToolBar(JFrame frame, String id) {
        this.frame = frame;
        this.id = id;
        add(backBtn);
        add(startBtn);

        setFloatable(false);
        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                frame.getContentPane().setLayout(new BorderLayout());
                frame.getContentPane().add(new SelectModePanel(frame, id), BorderLayout.CENTER);
                frame.getContentPane().add(new StartToolBar(), BorderLayout.NORTH);
                frame.revalidate();
                frame.repaint();
            }
        });
    }
}
