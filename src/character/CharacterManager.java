package character;

import character.worker.*;
import screen.game.left.GroundPanel;

public class CharacterManager {
    private ManTask manTask;
    private ScarecrowTask scarecrowTask;
    private MushroomTask mushroomTask;
    private WolfTask wolfTask;
    private SkeletonTask skeletonTask;

    private String enemy = null;

    public CharacterManager(GroundPanel view) {
        manTask = new ManTask(view, "SWORD", 5);
        scarecrowTask = new ScarecrowTask(view , 10);
        mushroomTask = new MushroomTask(view, 30);
        wolfTask = new WolfTask(view, 3);
        skeletonTask = new SkeletonTask(view, 70);
    }


    public void setEnemy(String enemy) {
        this.enemy = enemy;
    }

    public Runnable getManTask() {
        return manTask;
    }

    public Runnable getEnemyTask() {
        switch (enemy) {
            case "SCARECROW" : return scarecrowTask;
            case "MUSHROOM" : return mushroomTask;
            case "WOLF" : return wolfTask;
            case "SKELETON" : return skeletonTask;
        }
        return null;
    }

    public BaseCharacter getMan() {
        return manTask;
    }

    public BaseCharacter getEnemy() {
        switch (enemy) {
            case "SCARECROW" : return scarecrowTask;
            case "MUSHROOM" : return mushroomTask;
            case "WOLF" : return wolfTask;
            case "SKELETON" : return skeletonTask;
        }
        return null;
    }
}
