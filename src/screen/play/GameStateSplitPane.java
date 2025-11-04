package screen.play;

import javax.swing.*;
import java.awt.*;

public class GameStateSplitPane extends JSplitPane {
    public GameStateSplitPane() {
        super(JSplitPane.VERTICAL_SPLIT);
        setBackground(Color.YELLOW);

        setTopComponent(new ScorePanel());
        setBottomComponent(new EditPanel());

        setDividerLocation(300);
    }
}
