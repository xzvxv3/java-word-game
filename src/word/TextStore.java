package word;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.Vector;

public class TextStore {

    // private Scanner sc;
    private StringTokenizer st;
    private Vector<String> textVector = new Vector<>(); // 영어 단어만

    // 초기 설정
    public TextStore() {
        try {
            Scanner sc = new Scanner(new FileReader("resources/words/words.txt"));
            while(sc.hasNext()) {
                textVector.add(sc.nextLine());
            }

        } catch(FileNotFoundException e) {
            System.out.println("No File.");
            System.exit(0);
        }
    }

    // 텍스트 추출
    public String getText() {
        int index = (int) (Math.random() * textVector.size());
        return textVector.get(index);
    }
}
