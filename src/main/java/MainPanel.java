import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPanel extends JPanel implements ActionListener {
    private TextPanes textPanes;
    private RightHandPanes rightHandPanes;

    public MainPanel(JFrame frame, TextPanes textPanes, RightHandPanes rightHandPanes) {
        this.textPanes = textPanes;
        this.rightHandPanes = rightHandPanes;
        this.setLayout(new BorderLayout());
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, textPanes, rightHandPanes);
        splitPane.setResizeWeight(0.5);
        this.add(splitPane, BorderLayout.CENTER);
    }

    public void actionPerformed(ActionEvent e) {

    }
}
