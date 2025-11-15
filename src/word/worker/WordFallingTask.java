package word.worker;

import character.CharacterManager;
import word.Word;
import word.WordStore;
import screen.game.left.GroundPanel;

import java.util.Iterator;
import java.util.Vector;

public class WordFallingTask implements Runnable {

    // 게임 화면
    private GroundPanel view;

    // 단어 관리 클래스
    private WordStore wordStore;

    // 캐릭터 관리 클래스
    private CharacterManager characterManager;

    // 단어 저장소
    private Vector<Word> words;

    // 스레드 작동 여부
    private boolean running = true;

    public WordFallingTask(WordStore wordStore, CharacterManager characterManager, GroundPanel view) {
        this.wordStore = wordStore;
        this.view = view;
        this.characterManager = characterManager;
        words = wordStore.getWordVector();
    }

    @Override
    public void run() {
        while(running) {
            fallWords();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // 단어 낙하 시작
    private void fallWords() {
        // 배열 리스트의 동기화 충돌 방지
        synchronized (words) {
            // 배열 리스트에 있는 단어 라벨들의 반복자 생성
            Iterator<Word> it = words.iterator();

            // 단어 라벨 순회
            while(it.hasNext()) {
                Word word = it.next(); // 단어 라벨 추출
                word.fall(); // 단어 라벨 떨어트림

                // 단어 라벨 충돌 판정
                if(word.isAtBottom()) {
                    characterManager.getEnemy().setMotion("ATTACK");
                    characterManager.getMan().onAttacked();
                    characterManager.getMan().decreaseHP();

                    view.remove(word); // 화면에서 단어 삭제
                    it.remove(); // 단어 완전 제거
                }
            }
        }
    }
}
