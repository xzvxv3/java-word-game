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
import java.io.*;
import java.util.Scanner;
import java.util.Vector;

public class SettingsPanel extends JPanel {
    // 이미지 기본 경로
    private final String IMG_PATH = "/images/";
    // 배경화면 이미지 경로
    private ImageIcon backgroundImage = new ImageIcon(getClass().getResource(IMG_PATH + "background/common/default.png"));
    // 설정 제목 이미지 경로
    private ImageIcon settingsTitleImage = new ImageIcon(getClass().getResource(IMG_PATH + "element/settings/settings_title_lbl.png"));

    // JFrame
    private JFrame frame = null;
    // 유저 관리자
    private UserManager userManager = null;
    // 로그인 관리자
    private LoginManager loginManager = null;

    // 뒤로가기 버튼
    private JButton backBtn = new GameImageButton(
            IMG_PATH + "button/common/back_btn.png",
            IMG_PATH + "button/common/back_btn_rollover.png"
    );
    // 단어장 생성 버튼
    private JButton createBtn = new GameImageButton(
            IMG_PATH + "button/settings/create_btn.png",
            IMG_PATH + "button/settings/create_btn_rollover.png"
    );
    // 단어장 선택 버튼
    private JButton choiceBtn = new GameImageButton(
            IMG_PATH + "button/settings/choice_btn.png",
            IMG_PATH + "button/settings/choice_btn_rollover.png"
    );

    // 단어장 이름 목록
    private JList<String> wordBookNameList = null;
    // 단어장의 단어 목록
    private JList<String> wordList = null;
    // 현재 단어장 경로
    private String currentWordBookPath = null;

    // 단어장 목록 보여주는 곳
    private JScrollPane wordBookScrollPane = new JScrollPane();
    // 단어장의 단어 목록 보여주는곳
    private JScrollPane wordListScrollPane = new JScrollPane();
    // 단어 입력하는 곳
    private JTextField inputWordTextField = new JTextField();

    public SettingsPanel(JFrame frame, UserManager userManager, LoginManager loginManager) {
        this.frame = frame;
        this.userManager = userManager;
        this.loginManager = loginManager;
        setLayout(null);
        // 컴포넌트 요소들의 초기 설정
        initComponent();

        // 단어장의 이름 목록
        addWordBookListScrollPane();
        // 단어장의 단어 목록
        addWordListScrollPane();

        // 뒤로가기 버튼
        addBackBtn();

        // 이벤트 리스너 등록
        addActionListeners();
    }

    // 컴포넌트 요소들의 초기 설정
    private void initComponent() {
        // 단어 입력 칸
        inputWordTextField.setBounds(770, 650, 150, 30);
        // 단어장 추가 버튼
        createBtn.setBounds(220, 385, 60, 55);
        // 단어장 선택 버튼
        choiceBtn.setBounds(285, 385, 60, 55);

        // 생성 버튼 패널에 추가
        add(createBtn);
        // 선택 버튼 패널에 추가
        add(choiceBtn);
        // 단어 입력 칸 패널에 추가
        add(inputWordTextField);
    }

    // 단어장 목록
    private void addWordBookListScrollPane() {
        // getWordFileName() 함수를 통해 단어장 이름 목록 반환
        wordBookNameList = new JList<>(getWordFileName());
        // 폰트 설정
        wordBookNameList.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        // 칸 하나당 높이
        wordBookNameList.setFixedCellHeight(30);

        // 스크롤 팬에 단어장 목록 추가
        wordBookScrollPane = new JScrollPane(wordBookNameList);
        // 스크롤 팬 위치, 크기 설정
        wordBookScrollPane.setBounds(140, 230, 200, 150);
        // 스크롤 팬을 패널에 추가
        add(wordBookScrollPane);
    }

    // 단어장 이름 목록 반환
    private Vector<String> getWordFileName() {
        // 파일 경로 불러오기
        File path = new File("resources/words");
        // 파일들을 모두 불러오기
        File[] wordBooks = path.listFiles();

        // 파일에 아무것도 없을시
        if (wordBooks == null) {
            System.out.println("[단어장 파일이 존재하지 않습니다.]");
            return null;
        }

        // 벡터 생성 (단어장 이름 목록)
        Vector<String> wordBooksNameList = new Vector<>();

        // 파일 목록을 탐색하면서 .txt 글자는 제외시키기
        for (File f : wordBooks) {
            // 파일 이름
            String fileName = f.getName();
            // 파일 이름이 .txt로 끝난다면
            if (fileName.endsWith(".txt")) {
                // .txt 부분만 잘라냄
                String name = fileName.substring(0, fileName.length() - 4);
                // 벡터에 추가
                wordBooksNameList.add(name);
            }
        }
        // 단어장 목록 반환
        return wordBooksNameList;
    }

    // 단어장의 단어 목록
    private void addWordListScrollPane() {
        // 단어장의 단어 목록 보여주는곳
        wordListScrollPane.setBounds(740,230,200,400);
        // 패널에 단어장 스크롤 팬 추가
        add(wordListScrollPane);
    }

    // 뒤로가기 버튼
    private void addBackBtn() {
        backBtn.setBounds(50, 630, 140, 55);
        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                frame.getContentPane().add(new MenuPanel(frame, loginManager, userManager), BorderLayout.CENTER);
                frame.revalidate();
                frame.repaint();
                SoundManager.getAudio().play("/sounds/back_btn.wav");
                System.out.println("[메뉴 화면]");
            }
        });
        add(backBtn);
    }

    // 이벤트 리스너 등록
    private void addActionListeners() {
        // 단어장 선택 버튼 클릭시
        choiceBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 사용자가 마우스로 클릭하여 선택한 값을 가져옴
                String selectedWordBook = wordBookNameList.getSelectedValue();
                // 단어장 경로
                currentWordBookPath = "/words/" + selectedWordBook + ".txt";
                // 앞으로 이 단어장으로 게임이 시작됨. SettingsManager에게 알림. (싱글톤 패턴)
                SettingsManager.getInstance().setCurrentWordBookPath(currentWordBookPath);
                // 단어 목록 최신화
                updateWordList();
            }
        });

        // 단어장 새로 생성
        createBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 입력창에 뜨게 할 라벨
                JLabel label = new JLabel("단어장의 이름을 입력하세요.");
                label.setFont(new Font("맑은 고딕", Font.BOLD, 18));

                // 입력창
                String name = JOptionPane.showInputDialog(null, label,  "단어장 생성",  JOptionPane.PLAIN_MESSAGE);

                // 유효성 검사
                if (name == null || name.trim().isEmpty()) {
                    System.out.println("[올바르지 않은 단어장 이름입니다.]");
                    return;
                }

                // 파일의 경로 생성
                String path = "/words/" + name + ".txt";
                // 파일 생성
                File newFile = new File(path);

                // 이미 같은 이름의 단어장이 있는지 확인
                if (newFile.exists()) {
                    // 에러 메시지 출력
                    JOptionPane.showMessageDialog(null, "이미 존재하는 단어장입니다.", "오류", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    // 로컬 컴퓨터의 지정된 경로에 텍스트 파일 생성
                    if (newFile.createNewFile()) {
                        // 파일 생성이 완료됐다고 알림
                        JOptionPane.showMessageDialog(null, "[단어장 생성 완료]");

                        // 단어장 이름 목록 반환
                        wordBookNameList = new JList<>(getWordFileName());
                        wordBookNameList.setFont(new Font("맑은 고딕", Font.BOLD, 15));
                        wordBookNameList.setFixedCellHeight(30);

                        // 틀은 그대로 두고, 내용물만 바꿔서 보여줌
                        wordBookScrollPane.setViewportView(wordBookNameList);
                        wordBookScrollPane.revalidate();
                        wordBookScrollPane.repaint();
                    }
                } catch (IOException exception) {
                    JOptionPane.showMessageDialog(null, "[파일 생성 실패]");
                    System.exit(0);
                }
            }
        });

        // 단어 입력 시, 단어장에 단어가 추가됨
        inputWordTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 텍스트를 가져옴
                JTextField tf = (JTextField) (e.getSource());
                String text = tf.getText();

                // 텍스트가 비어있으면 아무것도 넣지 않음.
                if(text.isEmpty()) return;

                // 엔터를 치면 빈칸으로 바꿈
                tf.setText("");

                try {
                    // 파일 쓰기 객체를 생성
                    FileWriter fw = new FileWriter(currentWordBookPath, true);
                    // 단어 쓰기
                    fw.write(text + "\n");
                    // 저장 후 닫기
                    fw.close();
                    // 단어장의 단어 목록 재갱신
                    updateWordList();

                    System.out.println("단어 추가 완료: " + text);
                } catch (IOException exception) {
                    System.out.println("[단어 추가 실패]");
                }
            }
        });
    }

    // 단어장의 단어 목록 재갱신
    private void updateWordList() {
        // 텍스트를 담는 벡터
        Vector<String> textVector = new Vector<>();
        String path = SettingsManager.getInstance().getCurrentWordBookPath();

        try {
            InputStream is = getClass().getResourceAsStream(path); // 리소스에서 스트림 추출
            if (is == null) {
                System.out.println("파일을 찾을 수 없습니다: " + path);
                return;
            }

            // 한글 깨짐 방지를 위해 UTF-8 설정 필수
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                textVector.add(line);
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 새로운 데이터를 담은 JList 생성
        wordList = new JList<>(textVector);
        wordList.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        wordList.setFixedCellHeight(30);

        // 스크롤패널의 화면을 새 단어장으로 교체
        wordListScrollPane.setViewportView(wordList);

        // 화면 다시 그리기
        wordListScrollPane.revalidate();
        wordListScrollPane.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // 이미지 크기 비율 맞게 버튼 크기에 맞춰 그림
        g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
        g.drawImage(settingsTitleImage.getImage(), 15, 15, 1070, 600, this);
    }
}