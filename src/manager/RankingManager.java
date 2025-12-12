package manager;

import character.type.EnemyType;
import dto.User;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Vector;


public class RankingManager {
    private HashMap<String, User> userScoresMap = new HashMap<>();

    private Scanner sc;
    private final String DATA_PATH = "data/users_score.txt";

    public RankingManager() {
        // 기존에 있던 점수들을 불러와서 해시맵에 저장
        loadRankings();
        System.out.println("[랭킹 불러오기 성공]");
    }

    // 기존에 있던 점수들을 불러와서 해시맵에 저장
    public void loadRankings() {
        try {
            sc = new Scanner(new FileReader(DATA_PATH));

            // 1. 기존 유저들의 점수 불러오기
            while(sc.hasNext()) {
                String id = sc.next();
                int easyModeScore = Integer.parseInt(sc.next());
                int mediumModeScore = Integer.parseInt(sc.next());
                int hardModeScore = Integer.parseInt(sc.next());

                userScoresMap.put(id, new User(id, easyModeScore, mediumModeScore, hardModeScore));
            }
            // sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("Cannot found user database.");
            System.exit(0);
        }
    }

    // 게임이 끝난 뒤, 유저의 점수 업데이트
    public void updateScore(EnemyType enemyType, String id, int score) {

        // 기존에 없던 유저라면, 새로 작성
        if (!userScoresMap.containsKey(id)) {
            userScoresMap.put(id, new User(id, 0, 0, 0));
        }

        // 유저 가져오기
        User user = userScoresMap.get(id);

        // 유저 점수 업데이트
        user.updateScore(enemyType, score);

        // 파일에 새로 작성
        saveRankings();
    }

    // 랭킹 새로 갱신
    private void saveRankings() {
        // 기존에 있던 점수들을 모두 지우고 새로 작성
        try  {
            FileWriter fw = new FileWriter(DATA_PATH, false);

            for (User user : userScoresMap.values()) {
                // 저장할 유저 데이터 구성
                String line = user.getId() + " " +
                        user.getEasyModeScore() + " " +
                        user.getMediumModeScore() + " " +
                        user.getHardModeScore() + "\n";

                // 파일에 쓰기
                fw.write(line);
            }
            fw.close();
        } catch (IOException e) {
            System.out.println("랭킹 파일 저장 실패");
            System.exit(0);
        }
    }

    // 모드에 따른 랭킹 반환
    public Vector<User> getRankingsByMode(EnemyType enemyType) {
        // 1. 전체 유저 리스트 가져오기
        Vector<User> userVector = new Vector<>(userScoresMap.values());

        // 2. 모드에 따라 다르게 정렬
        switch (enemyType) {
            case MUSHROOM : userVector.sort((o1, o2) -> o2.getEasyModeScore() - o1.getEasyModeScore()); break;
            case WOLF : userVector.sort((o1, o2) -> o2.getMediumModeScore() - o1.getMediumModeScore()); break;
            case REAPER : userVector.sort((o1, o2) -> o2.getHardModeScore() - o2.getHardModeScore()); break;
        }

        return userVector;
    }
}
