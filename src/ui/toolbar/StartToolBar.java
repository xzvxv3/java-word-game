package ui.toolbar;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartToolBar extends JToolBar {
    private JButton startBtn = new JButton("Start");

    public StartToolBar() {
        add(startBtn);
        setFloatable(false);
        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //start();
            }
        });
    }
}
