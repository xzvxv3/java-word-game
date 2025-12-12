package dto;

import character.type.EnemyType;

public class User {

    // Easy, Medium, Hard 점수
    private int easyModeScore, mediumModeScore, hardModeScore;

    // 아이디
    private String id = null;

    public User(String id, int easyModeScore, int mediumModeScore, int hardModeScore) {
        this.id = id;
        this.easyModeScore = easyModeScore;
        this.mediumModeScore = mediumModeScore;
        this.hardModeScore = hardModeScore;
    }

    public String getId() {return id;}
    // Easy Mode 점수 반환
    public int getEasyModeScore() { return easyModeScore; }
    // Medium Mode 점수 반환
    public int getMediumModeScore() { return mediumModeScore; }
    // Hard Mode 점수 반환
    public int getHardModeScore() {return hardModeScore; }

    public void updateScore(EnemyType enemyType, int score) {
        switch (enemyType) {
            case MUSHROOM : if(easyModeScore < score) easyModeScore = score; break;
            case WOLF : if(mediumModeScore < score) mediumModeScore = score; break;
            case REAPER : if(hardModeScore < score) hardModeScore = score; break;
            default: return;
        }

    }

}
