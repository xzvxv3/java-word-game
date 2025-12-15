package ui.menu;

import manager.LoginManager;
import manager.SettingsManager;
import manager.SoundManager;
import manager.UserManager;
import ui.common.GameImageButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.Set;
import java.util.Vector;

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


    // 단어장 이름 목록
    private JList<String> wordBookNameList;
    // 단어장의 단어 목록
    private JList<String> wordList;

    // 현재 단어장 경로
    private String currentWordBookPath;

    // 단어 목록 보여주는 곳
    private JScrollPane wordListScrollPane = new JScrollPane();
    private JScrollPane wordBookScrollPane = new JScrollPane();

    private JTextField inputWordTextField = new JTextField();

    private String wordBookPath = null;

    public SettingsPanel(JFrame frame, UserManager userManager, LoginManager loginManager) {
        this.frame = frame;
        this.userManager = userManager;
        this.loginManager = loginManager;
        this.wordBookPath = wordBookPath;
        setLayout(null);

        // 컴포넌트 요소들의 초기 설정
        initComponent();

        // 뒤로가기 버튼
        addBackBtn();

        // ==========================

        // 단어장의 이름 목록
        addWordBookListScrollPane();

        // 단어장의 단어 목록
        addWordListScrollPane();


        // 이벤트는 여기에
        addEventAction();
    }

    // 단어장 목록
    private void addWordBookListScrollPane() {
        wordBookNameList = new JList<>(getWordFileName());
        wordBookNameList.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        // 줄 높이
        wordBookNameList.setFixedCellHeight(30);

        // 스크롤 기능 추가
        wordBookScrollPane = new JScrollPane(wordBookNameList);
        wordBookScrollPane.setBounds(140, 230, 200, 150);
        add(wordBookScrollPane);
    }

    // 단어장 이름 목록 반환
    private Vector<String> getWordFileName() {
        File path = new File("resources/words");

        // 파일들을 모두 불러오기
        File[] wordBooks = path.listFiles();

        // 파일에 아무것도 없을시
        if (wordBooks == null) {
            return null;
        }

        // 배열리스트 생성 (단어장 이름 목록)
        Vector<String> wordBooksNameList = new Vector<>();

        // .txt 글자는 제외시키기
        for (File f : wordBooks) {
            String fileName = f.getName();

            if (fileName.endsWith(".txt")) {
                String name = fileName.substring(0, fileName.length() - 4);
                wordBooksNameList.add(name);
            }
        }

        return wordBooksNameList;
    }

    private void addWordListScrollPane() {
        // 단어장의 단어 목록 보여주는곳
        wordListScrollPane.setBounds(740,230,200,400);
        add(wordListScrollPane);
    }

    // ===============================================

    private void addEventAction() {
        // 단어장 선택 버튼 클릭시
        choiceBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 단어장 경로 불러오기
                String selectedWordBook = wordBookNameList.getSelectedValue();
                currentWordBookPath = "resources/words/" + selectedWordBook + ".txt";

                // 앞으로 이 단어장으로 게임이 시작됨
                SettingsManager.getInstance().setCurrentWordBookPath(currentWordBookPath);

                // 단어를 담을 벡터
                Vector<String> textVector = new Vector<>();

                try {
                    Scanner sc = new Scanner(new FileReader(currentWordBookPath));
                    while(sc.hasNext()) {
                        textVector.add(sc.nextLine());
                    }

                } catch(FileNotFoundException exception) {
                    System.out.println("No File.");
                    System.exit(0);
                }

                // JList에 단어 담기
                wordList = new JList<>(textVector);
                wordList.setFont(new Font("맑은 고딕", Font.BOLD, 15));
                wordList.setFixedCellHeight(30);

                // 단어 내용 갱신
                wordListScrollPane.setViewportView(wordList);

                // 화면 갱신
                wordListScrollPane.revalidate();
                wordListScrollPane.repaint();
            }
        });

        // 단어장 새로 생성
        createBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JLabel label = new JLabel("단어장의 이름을 입력하세요.");
                label.setFont(new Font("맑은 고딕", Font.BOLD, 18));

                // 입력창
                String name = JOptionPane.showInputDialog(null, label,  "단어장 생성",  JOptionPane.PLAIN_MESSAGE);

                // 유효성 검사
                if (name == null || name.trim().isEmpty()) {
                    return;
                }

                // 4. 파일 생성 로직
                String path = "resources/words/" + name + ".txt";
                File newFile = new File(path);

                // 이미 같은 이름의 단어장이 있는지 확인
                if (newFile.exists()) {
                    JOptionPane.showMessageDialog(null, "이미 존재하는 단어장입니다.", "오류", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    // 실제 빈 파일 생성 (.txt)
                    if (newFile.createNewFile()) {
                        JOptionPane.showMessageDialog(null, "[단어장 생성 완료]");

                        wordBookNameList = new JList<>(getWordFileName());
                        wordBookNameList.setFont(new Font("맑은 고딕", Font.BOLD, 15));
                        wordBookNameList.setFixedCellHeight(30);

                        wordBookScrollPane.setViewportView(wordBookNameList);
                        wordBookScrollPane.revalidate();
                        wordBookScrollPane.repaint();
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "[파일 생성 실패]");
                    System.exit(0);
                }

            }
        });

        inputWordTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField tf = (JTextField) (e.getSource());
                String text = tf.getText();

                if(text.isEmpty()) return;

                tf.setText("");

                try {
                    // ★ 핵심: 뒤에 true를 붙여야 '이어쓰기(Append)'가 됩니다.
                    // true가 없으면 파일 내용이 싹 지워지고 새 단어만 남습니다.
                    FileWriter fw = new FileWriter(currentWordBookPath, true);

                    fw.write(text + "\n");  // 단어 쓰기

                    fw.close();      // 저장 후 닫기

                    // 4. 화면 갱신
                    choiceBtn.doClick();

                    System.out.println("단어 추가 완료: " + text);

                } catch (Exception ex) {
                    ex.printStackTrace(); // 에러 내용을 콘솔에 출력
                }
            }
        });
    }



    // =================

    // 컴포넌트 요소들의 초기 설정
    private void initComponent() {
        inputWordTextField.setBounds(770, 650, 150, 30);

        // 단어장 추가 버튼
        createBtn.setBounds(220, 385, 60, 55);

        // 단어장 선택 버튼
        choiceBtn.setBounds(285, 385, 60, 55);


        add(createBtn);
        add(choiceBtn);
        add(inputWordTextField);
    }

    // 뒤로가기 버튼
    private void addBackBtn() {
        backBtn.setBounds(50, 630, 140, 55);
        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SoundManager.getAudio().play("resources/sounds/back_btn.wav");
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
