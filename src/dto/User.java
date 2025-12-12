package dto;

import character.type.EnemyType;

public class User {

    // Easy, Medium, Hard 점수
    private int easyModeScore, mediumModeScore, hardModeScore;

    // 아이디
    private String id = null;

    // 현재 게임에서 얻은 점수
    private int currentScore = 0;

    public User(String id, int easyModeScore, int mediumModeScore, int hardModeScore) {
        this.id = id;
        this.easyModeScore = easyModeScore;
        this.mediumModeScore = mediumModeScore;
        this.hardModeScore = hardModeScore;
    }

    // 사용자의 아이디 반환
    public String getId() {return id;}

    // 게임 시작할 때마다 점수를 0으로 초기화
    public void resetCurrentScore() { currentScore = 0; }

    // 현재 점수 증가
    public void incrementCurrentScore(int amount) { currentScore += amount; }

    // 현재 얻은 점수 반환
    public int getCurrentScore() {return currentScore; }


    // Easy Mode 점수 반환
    public int getEasyModeScore() { return easyModeScore; }
    // Medium Mode 점수 반환
    public int getMediumModeScore() { return mediumModeScore; }
    // Hard Mode 점수 반환
    public int getHardModeScore() {return hardModeScore; }

    // 게임 종료시, 모드에 따른 점수 반영
    public void updateCurrentScore(EnemyType enemyType) {
        switch (enemyType) {
            case MUSHROOM : if(easyModeScore < currentScore) easyModeScore = currentScore; break;
            case WOLF : if(mediumModeScore < currentScore) mediumModeScore = currentScore; break;
            case REAPER : if(hardModeScore < currentScore) hardModeScore = currentScore; break;
            default: return;
        }
    }
}
