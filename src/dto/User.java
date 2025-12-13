package dto;

import character.type.EnemyType;

public class User {

    private String id; // 아이디
    private String password; // 비밀번호

    // Easy, Medium, Hard 점수
    private int easyModeScore, normalModeScore, hardModeScore;

    private int currentScore = 0;

    public User(String id, String password, int easyModeScore, int normalModeScore, int hardModeScore) {
        this.id = id;
        this.password = password;
        this.easyModeScore = easyModeScore;
        this.normalModeScore = normalModeScore;
        this.hardModeScore = hardModeScore;
    }

    public User(String id, String password) {
        this(id, password, 0, 0, 0);
    }

    // 사용자의 아이디 반환
    public String getId() {return id;}
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
    public void updateCurrentScore(EnemyType enemyType, int currentScore) {
        switch (enemyType) {
            case MUSHROOM : if(easyModeScore < currentScore) easyModeScore = currentScore; break;
            case WOLF : if(normalModeScore < currentScore) normalModeScore = currentScore; break;
            case REAPER : if(hardModeScore < currentScore) hardModeScore = currentScore; break;
            default: return;
        }
    }
}
