package com.jannetta.graphvizj.components;

import com.jannetta.graphvizj.components.RightHandPanes;
import com.jannetta.graphvizj.components.TextPanes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPanel extends JSplitPane implements ActionListener {
    private TextPanes textPanes;
    private RightHandPanes rightHandPanes;

    public MainPanel(JFrame frame, TextPanes textPanes, RightHandPanes rightHandPanes, Console consolePanel) {
        JSplitPane horizontalSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, textPanes, rightHandPanes);
        setOrientation(JSplitPane.VERTICAL_SPLIT);
        setResizeWeight(0.7);
        horizontalSplit.setResizeWeight(0.5);
        this.setLeftComponent(horizontalSplit);
        this.setRightComponent(consolePanel);

    }

    public void actionPerformed(ActionEvent e) {

    }
}
