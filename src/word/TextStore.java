package word;
import manager.SettingsManager;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.Vector;

// 단어장에 있는 단어들을 관리하는 클래스
public class TextStore {

    // 단어장에 있는 단어들을 담을 벡터
    private Vector<String> textVector = new Vector<>();

    // 초기 설정
    public TextStore() {
        try {
            // 단어장을 불러옴
            Scanner sc = new Scanner(new FileReader(SettingsManager.getInstance().getCurrentWordBookPath()));
            // 단어장의 단어들을 벡터에 삽입
            while(sc.hasNext()) {
                textVector.add(sc.nextLine());
            }

        } catch(FileNotFoundException e) {
            System.out.println("[파일 존재X]");
            System.exit(0);
        }
    }

    // 랜덤 텍스트 추출
    public String getText() {
        int index = (int) (Math.random() * textVector.size());
        return textVector.get(index);
    }
}
