package character.imageloader;
import character.type.MotionType;
import javax.swing.*;
import java.util.HashMap;

// 이미지 로더 클래스
public class ImageLoader {
    // 이미지 기본 경로
    protected final String BASE_PATH = "resources/sprites/";

    // 모션을 담을 해시맵
    protected HashMap<MotionType, ImageIcon[]> motionMap = new HashMap<>();

    // 해시맵 반환
    public HashMap<MotionType, ImageIcon[]> getMotionMap() {
        return motionMap;
    }
}
