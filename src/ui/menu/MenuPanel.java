package ui.menu;

import ui.common.GameImageButton;
import ui.toolbar.StartToolBar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPanel extends JPanel {

    private ImageIcon backgroundImage = new ImageIcon("resources/images/background/menu/menu_background_image.png");

    JButton selectModeButton = new GameImageButton(
            "resources/images/button/menu/selectmode_btn.png",
            "resources/images/button/menu/selectmode_btn_rollover.png"
    );

    JButton scoresButton = new GameImageButton(
            "resources/images/button/menu/scores_btn.png",
            "resources/images/button/menu/scores_btn_rollover.png"
    );

    JButton settingsButton = new GameImageButton(
            "resources/images/button/menu/settings_btn.png",
            "resources/images/button/menu/settings_btn_rollover.png"
    );

    JButton logoutButton = new GameImageButton(
            "resources/images/button/menu/logout_btn.png",
            "resources/images/button/menu/logout_btn_rollover.png"
    );

    JButton[] btns = {selectModeButton, scoresButton, settingsButton, logoutButton};


    private JFrame frame;

    public MenuPanel(JFrame frame) {
        this.frame = frame;
        setLayout(null);

        initComponent();

        addActionListeners();

    }

    private void initComponent() {
        for(int i=0; i < btns.length; i++) {
            btns[i].setSize(320, 60);
            btns[i].setLocation(300, 200 + 100 * i);
            add(btns[i]);
        }
    }

    private void addActionListeners() {

        // 모드 선택 버튼
        selectModeButton.addActionListener(new ActionListener() {
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


    }

    @Override
    protected void paintComponent(Graphics g) { // 배경 요소
        super.paintComponent(g);
        // 전체 배경 화면
        g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
    }
}