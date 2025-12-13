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


// 유저 관리자
// 1. 유저 데이터 불러오기
// 2. 유저 데이터 새로 갱신
// 3. 유저 존재 여부 확인
public class UserManager {
    private HashMap<String, User> userMap = new HashMap<>();
    private final String DATA_PATH = "data/users.txt";

    public UserManager() {
        // 기존에 있던 점수들을 불러와서 해시맵에 저장
        loadData();
        System.out.println("[유저 데이터 불러오기 성공]");
    }

    // 유저 데이터 불러오기
    public void loadData() {
        userMap.clear(); // 기존 데이터 삭제

        try {
            // 파일 불러오기
            Scanner sc = new Scanner(new FileReader(DATA_PATH));

            // 유저 데이터 탐색 후, map에 새로 저장
            while(sc.hasNext()) {
                String id = sc.next();
                String pw = sc.next();
                int easy = Integer.parseInt(sc.next());
                int medium = Integer.parseInt(sc.next());
                int hard = Integer.parseInt(sc.next());

                userMap.put(id, new User(id, pw, easy, medium, hard));
            }
        } catch (FileNotFoundException e) {
            System.out.println("[유저 데이터 불러오기 실패");
            System.exit(0);
        }
    }

    // 유저 데이터 새로 갱신 (점수 갱신 용도)
    public void updateUser(User user) {
        userMap.put(user.getId(), user); // 유저 새로 저장

        try {
            FileWriter fw = new FileWriter(DATA_PATH, false); // 파일 새로 쓰기

            // 모든 유저 탐색
            for (User u : userMap.values()) {
                String data = u.getId() + " " + u.getPassword() + " "
                            + u.getEasyModeScore() + " " + u.getMediumModeScore()
                            + " " + u.getHardModeScore() + "\n";
                fw.write(data);
            }

            fw.close(); // 저장 완료
            System.out.println("[유저 데이터 갱신 완료]");

        } catch (IOException e) {
            System.out.println("[유저 데이터 갱신 실패]");
            System.exit(0);
        }
    }

    // 유저 존재 여부 확인
    public User getUser(String id) {
        return userMap.get(id);
    }

    // 유저 추가
    public void addUser(String id, String password) {
        User user = new User(id, password);
        userMap.put(id, user);

        try  {
            // 파일 불러오기 (덮어쓰기 모드)
            FileWriter fw = new FileWriter(DATA_PATH, true);

            // 새로운 유저의 데이터
            String data = user.getId() + " " + user.getPassword() + " "
                    + user.getEasyModeScore() + " " + user.getMediumModeScore()
                    + " " + user.getHardModeScore() + "\n";

            // 파일에 저장
            fw.write(data);
            fw.close();
        } catch (IOException e) {
            System.out.println("[파일 불러오기 실패]");
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
