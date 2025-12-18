package manager;
import character.base.BaseGameCharacter;
import character.imageloader.*;
import character.type.MonsterType;
import character.type.MotionType;
import character.type.WeaponType;
import character.unit.monster.Mushroom;
import character.unit.monster.Reaper;
import character.unit.monster.Scarecrow;
import character.unit.monster.Wolf;
import character.unit.player.Man;
import ui.game.left.GroundPanel;

// 캐릭터 관리자
public class CharacterManager {
    // Runnable
    private Man man = null;
    private Scarecrow scarecrow = null;
    private Mushroom mushroom = null;
    private Wolf wolf = null;
    private Reaper reaper = null;

    // 이미지 로더 클래스
    private ManImageLoader manImageLoader = new ManImageLoader();
    private ScarecrowImageLoader scarecrowImageLoader = new ScarecrowImageLoader();
    private MushroomImageLoader mushroomImageLoader = new MushroomImageLoader();
    private WolfImageLoader wolfImageLoader = new WolfImageLoader();
    private ReaperImageLoader reaperImageLoader = new ReaperImageLoader();

    // 주인공, 몬스터 스레드
    private Thread manThread = null, monsterThread = null;

    // 현재 몬스터
    private MonsterType monsterType = null;

    // 현재 주인공 무기 상태
    private WeaponType weaponType = WeaponType.EMPTY;

    // 무기 잠금 해제 조건의 점수
    private int weaponUnlockScore = 3;

    // 캐릭터 이미지를 그릴 곳
    private GroundPanel view = null;

    // 주인공 체력
    private int manHP = 100;
    // 몬스터 체력
    private int scarecrowHP, mushroomHP = 5, wolfHP = 30, reaperHP = 60;
    // 몬스터 공격력
    private int mushroomAttackPower = 5, wolfAttackPower = 10, reaperAttackPower = 20;

    // 캐릭터 관리자
    public CharacterManager() {}

    // 무기 잠금 해제 조건의 점수 반환
    public int getWeaponUnlockScore() {
        return weaponUnlockScore;
    }

    // 몬스터 공격력 반환
    public int getMonsterAttackPower(MonsterType monsterType) {
        switch (monsterType) {
            case MUSHROOM : return mushroomAttackPower;
            case WOLF : return wolfAttackPower;
            case REAPER: return reaperAttackPower;
        }
        return -1;
    }

    // 캐릭터 이미지를 그릴 곳을 설정
    public void setView(GroundPanel view) {
        this.view = view;
        setCharacters(view);
    }

    // 캐릭터 생성
    private void setCharacters(GroundPanel view) {
        man = new Man(view, manImageLoader, WeaponType.EMPTY, manHP);
        scarecrow = new Scarecrow(view, scarecrowImageLoader, scarecrowHP);
        mushroom = new Mushroom(view, mushroomImageLoader, mushroomHP);
        wolf = new Wolf(view, wolfImageLoader, wolfHP);
        reaper = new Reaper(view, reaperImageLoader, reaperHP);
    }

    // 주인공의 무기 변경
    public void changeManWeapon() {
        man.changeToSword();
    }

    // 주인공이 무기를 가지고 있는지
    public boolean hasSword() {
        return man.getCurrentWeapon() == WeaponType.SWORD;
    }

    // 주인공의 Runnable 반환 [스레드 제어용]
    public Runnable getManTask() {
        return man;
    }

    // 주인공 객체 반환 [로직 제어용]
    public BaseGameCharacter getMan() {
        return man;
    }

    // 몬스터의 Runnable 반환 [스레드 제어용]
    public Runnable getMonsterTask() {
        switch (monsterType) {
            case SCARECROW : return scarecrow;
            case MUSHROOM : return mushroom;
            case WOLF : return wolf;
            case REAPER : return reaper;
        }
        return null;
    }

    // 몬스터 객체 반환 [로직 제어용]
    public BaseGameCharacter getEnemy() {
        switch (monsterType) {
            case SCARECROW : return scarecrow;
            case MUSHROOM : return mushroom;
            case WOLF : return wolf;
            case REAPER : return reaper;
        }
        return null;
    }

    // 몬스터 타입 설정
    public void setMonsterType(MonsterType monsterType) {
        this.monsterType = monsterType;
    }

    // 몬스터 타입 반환
    public MonsterType getMonsterType() {
        return monsterType;
    }

    // 주인공 현재 무기 상태 반환
    public WeaponType getManWeapon() {
        return man.getCurrentWeapon();
    }

    // 주인공 기존 체력 반환
    public int getManHP() {
        return manHP;
    }

    // 몬스터 기존 체력 반환
    public int getMonsterHP() {
        switch (monsterType) {
            case SCARECROW : return scarecrowHP;
            case MUSHROOM : return mushroomHP;
            case WOLF : return wolfHP;
            case REAPER : return reaperHP;
        }
        return -1;
    }

    // 주인공 현재 체력 반환
    public int getCurrentManHP() {
        return man.getCurrentHp();
    }

    // 몬스터 현재 체력 반환
    public int getCurrentMonsterHP() {
        switch (monsterType) {
            case SCARECROW : return scarecrow.getCurrentHp();
            case MUSHROOM : return mushroom.getCurrentHp();
            case WOLF : return wolf.getCurrentHp();
            case REAPER : return reaper.getCurrentHp();
        }
        return -1;
    }

    // 주인공 체력 회복
    public void healManHP() {
        getMan().setHP(getCurrentManHP() + 20);
    }

    // 주인공 데미지 입음
    public void decreaseManHP(int amount) {
        man.decreaseHP(amount);
    }

    // 몬스터 데미지 입음
    public void decreaseEnemyHP(int amount) {
        switch (monsterType) {
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
    public void setMonsterHP(int hp) {
        scarecrowHP = hp;
    }

    // Reaper 스킬 사용
    public void useReaperSkill() {
        getEnemy().setMotion(MotionType.ENEMY_SKILL01);
    }

    // 게임이 끝나는지 체크
    public boolean isGameOver() {
        if(getCurrentMonsterHP() <= 0 || getCurrentManHP() <= 0) return true;
        return false;
    }

    // 게임 시작
    public void gameStart() {
        // 주인공 스레드
        manThread = new Thread(getManTask());
        // 몬스터 스레드
        monsterThread = new Thread(getMonsterTask());

        // 주인공, 몬스터 스레드 시작
        manThread.start();
        monsterThread.start();
    }

    // 모든 캐릭터 스레드 강제 종료
    public void shutDown() {
        manThread.interrupt();
        monsterThread.interrupt();
        System.out.println("[캐릭터 스레드 종료]");
    }
}
