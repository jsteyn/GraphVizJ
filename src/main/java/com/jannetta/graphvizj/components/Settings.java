package com.jannetta.graphvizj.components;

import com.jannetta.graphvizj.controller.Globals;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Settings extends JFrame implements ActionListener {
    private Logger logger = Logger.getLogger(this.getClass());
    static private Settings settings = null;
    private JPanel holdAll = new JPanel();

    SpringLayout layout = new SpringLayout();
    Container contentPane = getContentPane();
    String[] layouts = {"circo", "dot", "fdp"};
    String[] types = {"png", "svg", "dot", "bmp"};
    private JLabel lbl_layoutEngine = new JLabel("Layout Engine");
    private JLabel lbl_outputType = new JLabel("Output File Type");
    private JLabel lbl_outputFile = new JLabel("Output File Name");
    private JComboBox<String> cb_layoutEngine = new JComboBox<>(layouts);
    private JComboBox<String> cb_outputType = new JComboBox<>(types);
    private JTextField tf_outputFile = new JTextField(25);
    private JButton selectFile = new JButton("...");
    private JButton save = new JButton("save");

    public static Settings getInstance() {
        if (settings == null) {
            settings = new Settings();
        }   return settings;
    }

    private Settings() {
        super();
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);


        save.addActionListener(this);

        lbl_layoutEngine.setPreferredSize(new Dimension(150, (int) lbl_layoutEngine.getPreferredSize().getHeight()));
        lbl_outputType.setPreferredSize(new Dimension(150, (int) lbl_outputType.getPreferredSize().getHeight()));
        lbl_outputFile.setPreferredSize(new Dimension(150, (int) lbl_outputFile.getPreferredSize().getHeight()));


        holdAll.setLayout(layout);

        holdAll.add(lbl_layoutEngine);
        holdAll.add(cb_layoutEngine);
        holdAll.add(lbl_outputType);
        holdAll.add(cb_outputType);
        holdAll.add(lbl_outputFile);
        holdAll.add(tf_outputFile);
        holdAll.add(selectFile);
        holdAll.add(save);

        layout.putConstraint(SpringLayout.WEST, lbl_layoutEngine,
                5,
                SpringLayout.WEST, holdAll);
        layout.putConstraint(SpringLayout.NORTH, lbl_layoutEngine,
                5,
                SpringLayout.NORTH, holdAll);

        layout.putConstraint(SpringLayout.WEST, cb_layoutEngine,
                5,
                SpringLayout.EAST, lbl_layoutEngine);
        layout.putConstraint(SpringLayout.NORTH, cb_layoutEngine,
                5,
                SpringLayout.NORTH, holdAll);

        layout.putConstraint(SpringLayout.WEST, lbl_outputType,
                5,
                SpringLayout.WEST, holdAll);
        layout.putConstraint(SpringLayout.NORTH, lbl_outputType,
                45,
                SpringLayout.NORTH, holdAll);

        layout.putConstraint(SpringLayout.WEST, cb_outputType,
                5,
                SpringLayout.EAST, lbl_outputType);
        layout.putConstraint(SpringLayout.NORTH, cb_outputType,
                45,
                SpringLayout.NORTH, holdAll);

        layout.putConstraint(SpringLayout.WEST, lbl_outputFile,
                5,
                SpringLayout.WEST, holdAll);
        layout.putConstraint(SpringLayout.NORTH, lbl_outputFile,
                90,
                SpringLayout.NORTH, holdAll);

        layout.putConstraint(SpringLayout.WEST, tf_outputFile,
                5,
                SpringLayout.EAST, lbl_outputFile);
        layout.putConstraint(SpringLayout.NORTH, tf_outputFile,
                90,
                SpringLayout.NORTH, holdAll);

        layout.putConstraint(SpringLayout.WEST, selectFile,
                5,
                SpringLayout.EAST, tf_outputFile);
        layout.putConstraint(SpringLayout.NORTH, selectFile,
                90,
                SpringLayout.NORTH, holdAll);

        layout.putConstraint(SpringLayout.WEST, save,
                5,
                SpringLayout.WEST, holdAll);
        layout.putConstraint(SpringLayout.NORTH, save,
                130,
                SpringLayout.NORTH, holdAll);

        contentPane.add(holdAll);
        pack();
        setSize(new Dimension(600, 400));

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        if (action.equals("save")) {
            logger.debug("saving ...");
            Globals.setType((String)cb_outputType.getSelectedItem());
        }
    }
}
