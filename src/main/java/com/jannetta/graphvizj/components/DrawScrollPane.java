package com.jannetta.graphvizj.components;

import javax.swing.*;
import java.awt.*;

public class DrawScrollPane extends JScrollPane {
    private DrawPanel drawPanel = new DrawPanel();

    public static DrawScrollPane create() {
        return new DrawScrollPane(new DrawPanel());
    }

    private DrawScrollPane(Component drawPanel) {
        super(drawPanel);
//        setLayout(new BorderLayout());
        setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    }

    public DrawPanel getDrawPanel() {
        return drawPanel;
    }
}
