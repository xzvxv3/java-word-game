package manager;

import character.base.BaseGameCharacter;
import character.imageloader.*;
import character.type.EnemyType;
import character.type.MotionType;
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

    private Thread manThread, enemyThread;

    // 현재 Enemy
    private EnemyType enemyType;

    // 현재 Man 무기 상태
    private WeaponType weaponType;

    private GroundPanel view;

    // Man 체력
    private int manHP = 100;
    // Monster 체력
    private int scarecrowHP, mushroomHP = 5, wolfHP = 30, reaperHP = 60;

    private int mushroomAttackPower = 5, wolfAttackPower = 10, reaperAttackPower = 20;

    public int getMonsterAttackPower(EnemyType enemyType) {
        switch (enemyType) {
            case MUSHROOM : return 5;
            case WOLF : return 10;
            case REAPER: return 20;
        }
        return -1;
    }

    public void shutDown() {
        manThread.interrupt();
        enemyThread.interrupt();
        System.out.println("[캐릭터 스레드 종료]");
    }


    public CharacterManager() {
        // 시작은 무기 보유X
        weaponType = WeaponType.EMPTY;
    }

    public void setView(GroundPanel view) {
        this.view = view;
        setCharacters(view);
    }

    private void setCharacters(GroundPanel view) {
        man = new Man(view, manImageLoader, WeaponType.EMPTY, manHP);
        scarecrow = new Scarecrow(view, scarecrowImageLoader, scarecrowHP);
        mushroom = new Mushroom(view, mushroomImageLoader, mushroomHP);
        wolf = new Wolf(view, wolfImageLoader, wolfHP);
        reaper = new Reaper(view, reaperImageLoader, reaperHP);
    }

    public void changeManWeapon() {
        man.changeToSword();
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

    public BaseGameCharacter getMan() { return man; }

    // Enemy 타입 설정
    public void setEnemyType(EnemyType enemyType) {
        this.enemyType = enemyType;
    }
    // Enemy 타입 반환
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

    public boolean isGameOver() {
        if(getCurrentEnemyHP() <= 0 || getCurrentManHP() <= 0) return true;
        return false;
    }

    // Man 체력 반환
    public int getManHP() {
        return manHP;
    }

    public void healManHP() {
        getMan().setHP(getCurrentManHP() + 20);
    }

    // Enemy 체력 반환
    public int getEnemyHP() {
        switch (enemyType) {
            case SCARECROW : return scarecrowHP; // 여기 추가!
            case MUSHROOM : return mushroomHP;
            case WOLF : return wolfHP;
            case REAPER : return reaperHP;
        }
        return 0;
    }

    public int getCurrentManHP() {
        return man.getCurrentHp();
    }

    public int getCurrentEnemyHP() {
        switch (enemyType) {
            case SCARECROW : return scarecrow.getCurrentHp();
            case MUSHROOM : return mushroom.getCurrentHp();
            case WOLF : return wolf.getCurrentHp();
            case REAPER : return reaper.getCurrentHp();
        }
        return 0;
    }

    // Man 데미지 입음
    public void decreaseManHP(int amount) {
        man.decreaseHP(amount);
    }

    // Monster 데미지 입음
    public void decreaseEnemyHP(int amount) {
        switch (enemyType) {
            case SCARECROW: // 여기 추가!
                scarecrow.decreaseHP(amount);
                break;
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

    // 몬스터 체력 설정
    public void setEnemyHP(int hp) {
        scarecrowHP = hp;
    }

    public void useReaperSkill() {
        getEnemy().setMotion(MotionType.ENEMY_SKILL01);
    }


    public void gameStart() {
        // Man 스레드
        manThread = new Thread(getManTask());
        // Enemy 스레드
        enemyThread = new Thread(getEnemyTask());

        manThread.start();
        enemyThread.start();
    }
}
