import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.Vector;

public class FallingWordMain extends JFrame {
    private GamePanel gamePanel = null;
    private ControlPanel controlPanel = null;

    public FallingWordMain() {
        super("떨어지는 단어 맞추기");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container c = getContentPane();
        gamePanel = new GamePanel();
        c.add(gamePanel,  BorderLayout.CENTER); // GamePanel을 컨텐트팬의 CENTER에 배치한다.


        controlPanel = new ControlPanel(gamePanel);
        c.add(controlPanel,  BorderLayout.SOUTH); // GamePanel을 컨텐트팬의 SOUTH에 배치한다.

        setSize(300,400);
        setVisible(true);

        gamePanel.startGame();
    }

    class ControlPanel extends JPanel {
        private GamePanel gamePanel;
        private JTextField input = new JTextField(15); // 사용자가 단어를 입력하는 창

        public ControlPanel(GamePanel gamePanel) {
            this.gamePanel = gamePanel;
            this.setLayout(new FlowLayout());
            this.setBackground(Color.LIGHT_GRAY);
            add(input);

            input.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    JTextField tf = (JTextField)e.getSource();
                    String text = tf.getText();
                    if(text.equals("그만"))
                        System.exit(0); // 프로그램 종료

                    if(!gamePanel.isGameOn())
                        return;

                    boolean match = gamePanel.matchWord(text);
                    if(match == true) {
                        gamePanel.printResult("성공");
                        gamePanel.stopGame(); // 게임 중지
                        gamePanel.startGame(); // 새 단어 시작
                        tf.setText(""); // 창에 입력된 텍스트 지우기
                    }
                    else {
                        gamePanel.printResult(tf.getText()+ " 틀림");
                    }
                }
            });
        }
    }

    // 컨텐트팬을 사용되는 패널 클래스
    class GamePanel extends JPanel {
        private JLabel label = new JLabel(); // 떨어지는 단어
        private JLabel resLabel = new JLabel(); // 성공 실패를 나타내는 레이블
        private Words words = null;
        private String fallingWord = null;
        private FallingThread thread = null;
        private boolean gameOn = false;

        public GamePanel() {
            setLayout(null); // 배치관리자를 사용하지 않고 절대 위치에 컴포넌트를 배치한다.
            add(label); // 레이블을 부착한다.

            resLabel.setLocation(0, 0);
            resLabel.setSize(100, 30);
            add(resLabel);
            words = new Words("resources/words/words_eng_1000.txt");
        }

        public boolean isGameOn() {
            return gameOn;
        }

        public void stopGame() {
            if(thread == null)
                return; // 스레드가 없음
            thread.interrupt(); // 스레드 강제 종료
            thread = null;
            gameOn = false;
        }

        public void stopSelfAndNewGame() { // 스레드가 바닥에 닿아서 실패할 때 호출
            startGame();
        }

        public void printResult(String text) {
            resLabel.setText(text);
        }

        public void startGame() {
            fallingWord = words.getRandomWord();
            label.setText(fallingWord);
            label.setSize(200, 30); // 레이블 크기
            label.setLocation((getWidth()-200)/2, 0); // 레이블 위치
            label.setForeground(Color.MAGENTA); //레이블의 글자 색을 설정한다.
            label.setFont(new Font("Tahoma", Font.ITALIC, 20)); // 레이블 글자의 폰트를 설정한다.

            thread = new FallingThread(this, label); // 게임 스레드
            thread.start();
            gameOn = true;
        }

        public boolean matchWord(String text) {
            if(fallingWord != null && fallingWord.equals(text))
                return true;
            else
                return false;
        }

        class FallingThread extends Thread {
            private GamePanel panel;
            private JLabel label; //게임 숫자를 출력하는 레이블
            private long delay = 200; // 지연 시간의 초깃값 = 200
            private boolean falling = false; // 떨이지고 있는지. 초깃값 = false

            public FallingThread(GamePanel panel, JLabel label) {
                this.panel = panel;
                this.label = label;
            }

            public boolean isFalling() {
                return falling;
            }

            @Override
            public void run() {
                falling = true;
                while(true) {
                    try {
                        sleep(delay);
                        int y = label.getY() + 5; //5픽셀 씩 아래로 이동
                        if(y >= panel.getHeight()-label.getHeight()) {
                            falling = false;
                            label.setText("");
                            panel.printResult("시간초과실패");
                            panel.stopSelfAndNewGame();
                            break; // 스레드 종료
                        }

                        label.setLocation(label.getX(), y);
                        GamePanel.this.repaint();
                    } catch (InterruptedException e) {
                        falling = false;
                        return; // 스레드 종료
                    }
                }
            }
        }
    }

    // words.txt 파일을 읽고 벡터에 저장하고 벡터로부터 랜덤하게 단어를 추출하는 클래스
    class Words {
        private Vector<String> wordVector = new Vector<String>();

        public Words(String fileName) {
            try {
                Scanner scanner = new Scanner(new FileReader(fileName));
                while(scanner.hasNext()) { // 파일 끝까지 읽음
                    String word = scanner.nextLine(); // 한 라인을 읽고 '\n'을 버린 나머지 문자열만 리턴
                    wordVector.add(word); // 문자열을 벡터에 저장
                }
                scanner.close();
            }
            catch(FileNotFoundException e) {
                System.out.println("file not found error");
                System.exit(0);
            }
        }

        public String getRandomWord() {
            final int WORDMAX = wordVector.size(); // 총 단어의 개수
            int index = (int)(Math.random()*WORDMAX);
            return wordVector.get(index);
        }
    }


    public static void main(String[] args) {
        new FallingWordMain();
    }
}

