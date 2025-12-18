package word;
import java.util.Vector;

// 실제로 떨어지는 단어 라벨을 관리하는 클래스
public class WordStore {
    // 단어 라벨을 담을 수 있는 벡터
    private Vector<Word> wordVector = new Vector<>();

    // 단어 추가
    public void addWord(Word word) {
        wordVector.add(word);
    }

    // 단어 벡터 반환
    public Vector<Word> getWordVector() {
        return wordVector;
    }
}
