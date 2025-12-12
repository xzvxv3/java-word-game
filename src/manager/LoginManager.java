package manager;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

// 한글패치 안 됨
public class LoginManager {
    private HashMap<String, String> userMap = new HashMap<>();
    private final String DATA_PATH = "data/users.txt";

    public LoginManager() {
        try {
            Scanner sc = new Scanner(new FileReader(DATA_PATH));

            while(sc.hasNext()) {
                String id = sc.next();
                String password = sc.next();
                userMap.put(id, password);
            }
            // sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("Cannot found user database.");
            System.exit(0);
        }
    }

    public void addUser(String id, String password) {
        userMap.put(id, password);

        // 데이터에 쓰기
        try  {
            FileWriter fw = new FileWriter(DATA_PATH, true);
            fw.write(id + " " + password + "\n");
            fw.close();
            System.out.println("[회원가입 완료] " + "\n" +  "ID: " + id + "\n" + "PASSWORD: " + password);
        } catch (IOException e) {
            System.out.println("[회원가입 실패]");
        }
    }

    public boolean findUser(String id, String password) {
        // ID 존재하는지 먼저 확인
        if(userMap.containsKey(id)) {
            // 해당 ID의 Password
            String userPassword = userMap.get(id);

            // 해당 ID의 Password와 사용자가 입력한 Password가 일치하는지 확인
            if(userPassword.equals(password)) {
                System.out.println("[로그인] " + id);
                return true;
            }
        }

        System.out.println("[로그인 실패]");
        return false;
    }

    public String checkIdValidity(String id) {
        // 1. 빈 값 체크
        if (id == null || id.isEmpty()) {
            return "아이디를 입력해주세요.";
        }

        // 2. 공백 체크
        if (id.contains(" ")) {
            return "아이디에 공백을 포함할 수 없습니다.";
        }

        // 3. 중복 체크
        if (userMap.containsKey(id)) {
            return "이미 사용 중인 아이디입니다.";
        }

        return "아이디 사용 가능"; // 모든 통과
    }
}
