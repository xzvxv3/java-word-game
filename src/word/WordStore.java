package word;

import java.util.Vector;

public class WordStore {
    private Vector<Word> wordVector = new Vector<>();

    public void addWord(Word word) {
        wordVector.add(word);
    }

    public void removeWord(Word word) {
        wordVector.remove(word);
    }

    public Vector<Word> getWordVector() {return wordVector;}
}
