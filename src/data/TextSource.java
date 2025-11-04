package data;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.Vector;

public class TextSource {

    private Scanner sc;
    private StringTokenizer st;
    private Vector<String> wordVector = new Vector<>(1000); // 영어 단어만
    private HashMap<String, String>  EngKorDict = new HashMap<>(); // 영,한 단어

    public TextSource() {
        try {
            sc = new Scanner(new FileReader("resources/words/words_eng_1000.txt"));
            while(sc.hasNext()) {
                wordVector.add(sc.nextLine());
            }

            sc = new Scanner(new FileReader("resources/words/words_EngKorDic_800.txt"));
            while(sc.hasNext()) {
                st = new StringTokenizer(sc.nextLine(), "\t");
                String eng = st.nextToken();
                String kor = st.nextToken();
                EngKorDict.put(eng, kor);
            }

        } catch(FileNotFoundException e) {
            System.out.println("No File.");
            System.exit(0);
        }
    }

    public String getNextWord() {
        int wordSize = wordVector.size();
        int idx = (int) (Math.random() * wordSize);
        return wordVector.get(idx);
    }
}
