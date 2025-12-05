package manager;

import character.base.BaseGameCharacter;
import character.imageloader.*;
import character.type.EnemyType;
import character.type.WeaponType;
import character.unit.monster.Mushroom;
import character.unit.monster.Reaper;
import character.unit.monster.Scarecrow;
import character.unit.monster.Wolf;
import character.unit.player.Man;
import ui.game.left.GroundPanel;

public class CharacterManager {
    // Runnable
    private Man man;
    private Scarecrow scarecrow;
    private Mushroom mushroom;
    private Wolf wolf;
    private Reaper reaper;

    // 이미지 로더 클래스
    private ManImageLoader manImageLoader = new ManImageLoader();
    private ScarecrowImageLoader scarecrowImageLoader = new ScarecrowImageLoader();
    private MushroomImageLoader mushroomImageLoader = new MushroomImageLoader();
    private WolfImageLoader wolfImageLoader = new WolfImageLoader();
    private ReaperImageLoader reaperImageLoader = new ReaperImageLoader();

    // 현재 Enemy
    private EnemyType enemyType;

    public CharacterManager(GroundPanel view) {
        man = new Man(view, manImageLoader, WeaponType.EMPTY, 100);
        scarecrow = new Scarecrow(view, scarecrowImageLoader, 10); // 기본 10
        mushroom = new Mushroom(view, mushroomImageLoader, 2);
        wolf = new Wolf(view, wolfImageLoader, 30);
        reaper = new Reaper(view, reaperImageLoader, 60);
    }

    public void changeManWeapon() {
        man.changeToSword();
    }

    public void setEnemy(EnemyType enemyType) {
        this.enemyType = enemyType;
    }

    public Runnable getManTask() {
        return man;
    }

    public Runnable getEnemyTask() {
        switch (enemyType) {
            case SCARECROW : return scarecrow;
            case MUSHROOM : return mushroom;
            case WOLF : return wolf;
            case REAPER : return reaper;
        }
        return null;
    }

    public BaseGameCharacter getMan() {
        return man;
    }

    public void setEnemyType(EnemyType enemyType) {
        this.enemyType = enemyType;
    }

    public EnemyType getEnemyType() {
        return enemyType;
    }

    public WeaponType getManWeapon() {
        return man.getCurrentWeapon();
    }

    // 수정 필요
    public BaseGameCharacter getEnemy() {
        switch (enemyType) {
            case SCARECROW : return scarecrow;
            case MUSHROOM : return mushroom;
            case WOLF : return wolf;
            case REAPER : return reaper;
        }
        return null;
    }

    public int getEnemyHP() {
        switch (enemyType) {
            case MUSHROOM : return mushroom.getCurrentHp();
            case WOLF : return wolf.getCurrentHp();
            case REAPER : return reaper.getCurrentHp();
        }
        return 0;
    }

    public int getManHP() {
        return man.getCurrentHp();
    }

    public void decreaseManHP(int amount) {
        man.decreaseHP(amount);
    }

    public void decreaseEnemyHP(int amount) {
        switch (enemyType) {
            case MUSHROOM:
                mushroom.decreaseHP(amount);
                break;
            case WOLF:
                wolf.decreaseHP(amount);
                break;
            case REAPER:
                reaper.decreaseHP(amount);
                break;
        }
    }

    public void setScarecrowHP(int hp) {
        scarecrow.setHP(hp);
    }
}
