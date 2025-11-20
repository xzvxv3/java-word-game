package character;

import character.worker.*;
import screen.game.left.GroundPanel;

public class CharacterManager {
    private ManTask manTask;
    private ScarecrowTask scarecrowTask;
    private MushroomTask mushroomTask;
    private WolfTask wolfTask;
    private ReaperTask reaperTask;

    private EnemyType enemyType;

    public CharacterManager(GroundPanel view) {
        manTask = new ManTask(view, WeaponType.EMPTY, 100);
        scarecrowTask = new ScarecrowTask(view , 10); // 기본 10
        mushroomTask = new MushroomTask(view, 5);
        wolfTask = new WolfTask(view, 50);
        reaperTask = new ReaperTask(view, 100);
    }


    public void setEnemy(EnemyType enemyType) {
        this.enemyType = enemyType;
    }

    public Runnable getManTask() {
        return manTask;
    }

    public Runnable getEnemyTask() {
        switch (enemyType) {
            case SCARECROW : return scarecrowTask;
            case MUSHROOM : return mushroomTask;
            case WOLF : return wolfTask;
            case REAPER : return reaperTask;
        }
        return null;
    }

    public BaseCharacter getMan() {
        return manTask;
    }

    public void setEnemyType(EnemyType enemyType) {
        this.enemyType = enemyType;
    }

    public EnemyType getEnemyType() {
        return enemyType;
    }

    public WeaponType getManWeapon() {
        return manTask.getCurrentWeapon();
    }

    // 수정 필요
    public BaseCharacter getEnemy() {
        switch (enemyType) {
            case SCARECROW : return scarecrowTask;
            case MUSHROOM : return mushroomTask;
            case WOLF : return wolfTask;
            case REAPER : return reaperTask;
        }
        return null;
    }

    public int getEnemyHP() {
        switch (enemyType) {
            case MUSHROOM : return mushroomTask.getCurrentHp();
            case WOLF : return wolfTask.getCurrentHp();
            case REAPER : return reaperTask.getCurrentHp();
        }
        return 0;
    }

    public int getManHP() {
        return manTask.getCurrentHp();
    }

    public void decreaseManHP(int amount) {
        manTask.decreaseHP(amount);
    }

    public void decreaseEnemyHP(int amount) {
        switch (enemyType) {
            case MUSHROOM:
                mushroomTask.decreaseHP(amount);
                break;
            case WOLF:
                wolfTask.decreaseHP(amount);
                break;
            case REAPER:
                reaperTask.decreaseHP(amount);
                break;
        }
    }

    public void setScarecrowHP(int hp) {
        scarecrowTask.setHP(hp);
    }
}
