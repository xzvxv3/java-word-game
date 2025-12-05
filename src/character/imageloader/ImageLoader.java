package character.imageloader;

import character.type.MotionType;

import javax.swing.*;
import java.util.HashMap;

public class ImageLoader {
    protected final String BASE_PATH = "resources/sprites/";

    protected HashMap<MotionType, ImageIcon[]> motionMap = new HashMap<>();

    public HashMap<MotionType, ImageIcon[]> getMotionMap() {
        return motionMap;
    }
}
