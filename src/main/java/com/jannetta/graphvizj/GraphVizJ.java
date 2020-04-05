package com.jannetta.graphvizj;

import com.jannetta.graphvizj.components.*;
import com.jannetta.graphvizj.components.MenuBar;
import com.jannetta.graphvizj.controller.Globals;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;

public class GraphVizJ extends JFrame {
    private Logger logger = Logger.getLogger(this.getClass());
    private Console console = Console.getInstance();
    private MainPanel mainPanel;
    private com.jannetta.graphvizj.components.MenuBar menuBar;
    private TextPanes textPanes = new TextPanes();
    private RightHandPanes rightHandPanes = new RightHandPanes();

    private GraphVizJ(String title) {
        super(title);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Globals.loadProperties();
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        try {
            Image icon = toolkit.getImage(ClassLoader.getSystemResource("Logo.png"));

            setIconImage(icon);
        } catch (NullPointerException e) {
            logger.error("Logo.png not found.");
        }
        menuBar = new MenuBar(textPanes, rightHandPanes);
        mainPanel = new MainPanel(this, textPanes, rightHandPanes, console);
        frameLayout();
    }

    private void frameLayout() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
            this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            setLayout(new BorderLayout());
            setJMenuBar(menuBar);
            getContentPane().add(mainPanel);

            this.pack();
            this.setVisible(true);
            setSize(1024, 768);
            mainPanel.repaint();
            mainPanel.revalidate();
            console.add(console);
        } catch (Exception e) {
            // If Nimbus is not available, you can set the GUI to another look and feel.
        }

    }

    public static void main(String[] args) {
        GraphVizJ graphVizJ = new GraphVizJ("GraphVizJ");
    }

}
