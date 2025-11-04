package screen.play;

import javax.swing.*;

public class GameSplitPane extends JSplitPane {
    public GameSplitPane() {
        super(JSplitPane.HORIZONTAL_SPLIT); // 수평 분할(왼/오)

        JPanel leftPanel = new GamePanel();

        JSplitPane rightSplitPane = new GameStateSplitPane();

        setLeftComponent(leftPanel);
        setRightComponent(rightSplitPane);

        setDividerLocation(650);
    }
}
