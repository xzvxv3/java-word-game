package ui.menu;

import manager.LoginManager;
import manager.UserManager;
import ui.common.GameImageButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsPanel extends JPanel {
    private final String BTN_PATH = "resources/images/button/";
    private ImageIcon backgroundImage = new ImageIcon("resources/images/background/common/default.png");
    private ImageIcon settingsTitleImage = new ImageIcon("resources/images/element/settings/settings_title_lbl.png");

    private JFrame frame;
    private UserManager userManager;
    private LoginManager loginManager;

    // 뒤로가기 버튼
    private JButton backBtn = new GameImageButton(
            BTN_PATH + "common/back_btn.png",
            BTN_PATH + "common/back_btn_rollover.png"
    );

    // 단어장 생성 버튼
    private JButton createBtn = new GameImageButton(
            "resources/images/button/settings/create_btn.png",
            "resources/images/button/settings/create_btn_rollover.png"
    );

    // 단어장 선택 버튼
    private JButton choiceBtn = new GameImageButton(
            "resources/images/button/settings/choice_btn.png",
            "resources/images/button/settings/choice_btn_rollover.png"
    );



    private final String word = "resources/words/words.txt";

    private String[] fruits = {"words"};

    public SettingsPanel(JFrame frame, UserManager userManager, LoginManager loginManager) {
        this.frame = frame;
        this.userManager = userManager;
        this.loginManager = loginManager;
        setLayout(null);



        setWordBook();

        setBtn();
        addBackBtn();
    }

    private void setWordBook() {
        JList<String> list = new JList<>(fruits);
        list.setFont(new Font("맑은 고딕", Font.BOLD, 15));

        // (선택사항) 글자가 커지면 줄 간격이 좁아 보일 수 있으므로 줄 높이도 지정 가능
        list.setFixedCellHeight(30);
        // 2. 스크롤 기능을 위해 JScrollPane에 넣기
        JScrollPane scrollPane = new JScrollPane(list);

        scrollPane.setBounds(140, 230, 200, 150);
        add(scrollPane);
    }

    // =================

    private void setBtn() {
        createBtn.setBounds(220, 385, 60, 55);
        choiceBtn.setBounds(285, 385, 60, 55);

        add(createBtn);
        add(choiceBtn);
    }


    private void addBackBtn() {
        backBtn.setBounds(50, 630, 140, 55);
        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                frame.getContentPane().add(new MenuPanel(frame, loginManager, userManager), BorderLayout.CENTER);
                frame.revalidate();
                frame.repaint();
                System.out.println("[메뉴 화면]");
            }
        });
        add(backBtn);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // 이미지 크기 비율 맞게 버튼 크기에 맞춰 그림
        g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
        g.drawImage(settingsTitleImage.getImage(), 15, 15, 1070, 600, this);
    }
}
