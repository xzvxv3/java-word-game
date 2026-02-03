package word;
import manager.SettingsManager;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.Vector;

// 단어장에 있는 단어들을 관리하는 클래스
public class TextStore {

    // 단어장에 있는 단어들을 담을 벡터
    private Vector<String> textVector = new Vector<>();

    // 초기 설정
    public TextStore() {
        String path = SettingsManager.getInstance().getCurrentWordBookPath();
        try {
            InputStream is = getClass().getResourceAsStream(path);

            if (is == null) {
                System.out.println("[파일 존재X] 경로를 확인하세요: " + path);
                System.exit(0);
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    textVector.add(line);
                }
            }
            reader.close();

        } catch (Exception e) {
            System.out.println("[단어장 로드 실패]");
            e.printStackTrace();
            System.exit(0);
        }
    }

    // 랜덤 텍스트 추출
    public String getText() {
        int index = (int) (Math.random() * textVector.size());
        return textVector.get(index);
    }
}
