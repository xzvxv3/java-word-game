package manager;

import ui.game.left.GroundPanel;
import word.TextStore;
import word.WordStore;
import word.worker.WordFallingTask;
import word.worker.WordMakerTask;

public class WordManager {

    private WordFallingTask wordFallingTask;
    private WordMakerTask wordMakerTask;

    private WordStore wordStore = new WordStore();
    private TextStore textStore = new TextStore();

    private GroundPanel view;
    public WordManager(GroundPanel view) {
        this.view = view;


    }


}
