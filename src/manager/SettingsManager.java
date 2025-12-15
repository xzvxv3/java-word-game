package manager;

public class SettingsManager {
    // 자기 자신의 인스턴스를 static으로 하나만 가짐
    private static final SettingsManager instance = new SettingsManager();

    // 관리할 데이터
    private String currentWordBookPath = null;

    // 생성자를 private으로 막음 (외부에서 막음)
    private SettingsManager() {
        // 초기값 설정 (기본 단어장)
        this.currentWordBookPath = "resources/words/words.txt";
    }

    // 인스턴스 반환
    public static synchronized SettingsManager getInstance() {
        return instance;
    }

    // 현재 단어장 바환
    public String getCurrentWordBookPath() {
        return currentWordBookPath;
    }

    // 단어장 설정
    public void setCurrentWordBookPath(String path) {
        this.currentWordBookPath = path;
        System.out.println("[단어장 변경 완료]");
    }
}
