package ui.toolbar;

import dto.User;
import manager.CharacterManager;
import manager.LoginManager;
import manager.UserManager;
import manager.WordManager;
import ui.common.GameImageButton;
import ui.menu.SelectModePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameToolBar extends JToolBar {


    private JButton backBtn = new JButton("Back");

    private JFrame frame;

    // 로그인 관리자
    private LoginManager loginManager = null;
    // 유저 관리자
    private UserManager userManager = null;

    private CharacterManager characterManager = null;

    private WordManager wordManager = null;

    public GameToolBar(JFrame frame, UserManager userManager, LoginManager loginManager, User user) {
        this.frame = frame;
        this.loginManager = loginManager;
        this.userManager = userManager;

        backBtn.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        add(backBtn);

        setFloatable(false);
        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                frame.getContentPane().setLayout(new BorderLayout());
                frame.getContentPane().add(new SelectModePanel(frame, userManager, loginManager), BorderLayout.CENTER);
                frame.revalidate();
                frame.repaint();
            }
        });
    }

}
