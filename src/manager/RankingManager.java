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
    private HashMap<String, User> userMap = new HashMap<>();

    private Scanner sc;
    private final String DATA_PATH = "data/users_score.txt";

    public RankingManager() {
        // 기존에 있던 점수들을 불러와서 해시맵에 저장
        loadRankings();
        System.out.println("[랭킹 불러오기 성공]");
    }

    // 랭킹에 저장된 유저면 그대로 반환, 아니면 새로 만들어서 반환
    public User getUser(String id) {
        if (userMap.containsKey(id)) {
            return userMap.get(id); // 이미 있는 유저 반환 (점수 유지)
        } else {
            return new User(id, 0, 0, 0); // 없는 유저면 0점짜리 새 객체 반환
        }
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

                userMap.put(id, new User(id, easyModeScore, mediumModeScore, hardModeScore));
            }
            // sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("Cannot found user database.");
            System.exit(0);
        }
    }

    // 여기 재가공 필요!!
    // 게임이 끝난 뒤, 유저의 점수 업데이트
    public void updateScore(User user) {
        userMap.put(user.getId(), user);
        // 파일에 새로 작성
        saveRankings();
    }

    // 랭킹 새로 갱신
    private void saveRankings() {
        // 기존에 있던 점수들을 모두 지우고 새로 작성
        try  {
            FileWriter fw = new FileWriter(DATA_PATH, false);

            for (User user : userMap.values()) {
                // 저장할 유저 데이터 구성
                String line = user.getId() + " " + user.getEasyModeScore() + " " + user.getMediumModeScore() + " " +  user.getHardModeScore() + "\n";

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
        Vector<User> userVector = new Vector<>(userMap.values());

        // 2. 모드에 따라 다르게 정렬
        switch (enemyType) {
            case MUSHROOM : userVector.sort((o1, o2) -> o2.getEasyModeScore() - o1.getEasyModeScore()); break;
            case WOLF : userVector.sort((o1, o2) -> o2.getMediumModeScore() - o1.getMediumModeScore()); break;
            case REAPER : userVector.sort((o1, o2) -> o2.getHardModeScore() - o1.getHardModeScore()); break;
        }

        return userVector;
    }
}
