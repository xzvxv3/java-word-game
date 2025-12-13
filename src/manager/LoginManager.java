package manager;

import dto.User;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

// 한글패치 안 됨
public class LoginManager {

    // 유저 관리자
    private UserManager userManager = null;

    // 현재 로그인중인 유저
    private User currentUser = null;

    public LoginManager(UserManager userManager) {
        this.userManager = userManager;
    }

    // 회원가입
    public boolean signUp(String id, String password) {
        // 유효성 검사
        String checkResult = checkIdValidity(id);
        if(!checkResult.equals("아이디 사용 가능")) {
            System.out.println(checkResult);
            return false;
        }

        // UserManager에게 저장 위임
        userManager.addUser(id, password);
        System.out.println("[회원가입 완료] " + "\n" +  "ID: " + id + "\n" + "PASSWORD: " + password);
        return true;
    }

    // 로그인
    public boolean login(String id, String password) {
        // 유저의 정보를 받아옴
        User user = userManager.getUser(id);

        // 존재하지 않는 유저
        if(user == null) {
            System.out.println("[로그인 실패] -> 존재하지 않는 유저");
            return false;
        }

        // 비밀번호 검증
        if(!user.getPassword().equals(password)) {
            System.out.println("[비밀번호 틀림]");
            return false;
        }

        // 로그인 성공
        this.currentUser = user;
        System.out.println("[로그인 성공] " + id + " " + password);
        return true;
    }

    // 로그아웃
    public void logout() {
        System.out.println("[로그아웃] " + currentUser.getId());
        this.currentUser = null;
    }

    // [추가] 외부에서 현재 유저 정보가 필요할 때 호출
    public User getCurrentUser() {
        return currentUser;
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
        if (userManager.getUser(id) != null) {
            return "이미 사용 중인 아이디입니다.";
        }

        return "아이디 사용 가능"; // 모든 통과
    }
}
