package user;
import character.type.MonsterType;

// 유저 클래스
public class User {

    // 아이디
    private String id;
    // 비밀번호
    private String password;

    // Easy, Medium, Hard 점수
    private int easyModeScore, normalModeScore, hardModeScore;

    // 유저의 현재 점수
    private int currentScore = 0;

    public User(String id, String password, int easyModeScore, int normalModeScore, int hardModeScore) {
        this.id = id;
        this.password = password;
        this.easyModeScore = easyModeScore;
        this.normalModeScore = normalModeScore;
        this.hardModeScore = hardModeScore;
    }

    // 유저의 아이디와 패스워드만 주어졌을 경우
    public User(String id, String password) {
        this(id, password, 0, 0, 0);
    }

    // 사용자의 아이디 반환
    public String getId() {return id;}
    // 사용자의 비밀번호 반환
    public String getPassword() {return password;}

     // 게임 시작할 때마다 점수를 0으로 초기화
     public void resetCurrentScore() { currentScore = 0; }
     // 현재 점수 증가
     public void incrementCurrentScore(int amount) { currentScore += amount; }
     // 현재 얻은 점수 반환
     public int getCurrentScore() {return currentScore; }

    // Easy Mode 점수 반환
    public int getEasyModeScore() { return easyModeScore; }
    // Medium Mode 점수 반환
    public int getNormalModeScore() { return normalModeScore; }
    // Hard Mode 점수 반환
    public int getHardModeScore() {return hardModeScore; }

    // 게임 종료시, 모드에 따른 점수 반영
    public void updateCurrentScore(MonsterType monsterType, int currentScore) {
        switch (monsterType) {
            case MUSHROOM : if(easyModeScore < currentScore) easyModeScore = currentScore; break;
            case WOLF : if(normalModeScore < currentScore) normalModeScore = currentScore; break;
            case REAPER : if(hardModeScore < currentScore) hardModeScore = currentScore; break;
        }
    }
}
