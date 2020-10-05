package com.jannetta.graphvizj.components;

import com.jannetta.graphvizj.controller.Globals;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Properties;

public class Settings extends JFrame implements ActionListener {
	Logger logger = LoggerFactory.getLogger(getClass());
    static private Settings settings = null;
    private JPanel holdAll = new JPanel();

    private SpringLayout layout = new SpringLayout();
    private Container contentPane = getContentPane();
    private String[] layouts = {"dot", "neato", "fdp", "circo", "osage", "patchwork", "sfdp", "twopi", "lefty", "dotty"};
    private String[] types = {"png", "svg", "bmp", "canon", "dot", "fig", "gd", "gif", "imap", "cmapx", "pdf", "plain", "png", "ps", "ps2", "vrml", "wbmp"};
    private JLabel lbl_layoutEngine = new JLabel("Layout Engine");
    private JLabel lbl_outputType = new JLabel("Output File Type");
    private JLabel lbl_outputFile = new JLabel("Output Directory Name");
    private JLabel lbl_executable = new JLabel("Dot Executable");
    private JComboBox<String> cb_layoutEngine = new JComboBox<>(layouts);
    private JComboBox<String> cb_outputType = new JComboBox<>(types);
    private JTextField tf_outputFile = new JTextField(25);
    private JTextField tf_executable = new JTextField(25);
    private JButton selectFile = new JButton("...");
    private JButton selectExecutable = new JButton("...");
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
        lbl_executable.setPreferredSize(new Dimension(150, (int) lbl_executable.getPreferredSize().getHeight()));

        selectFile.setActionCommand("selectFile");
        selectExecutable.setActionCommand("selectExecutable");

        holdAll.setLayout(layout);

        holdAll.add(lbl_layoutEngine);
        holdAll.add(cb_layoutEngine);
        holdAll.add(lbl_outputType);
        holdAll.add(cb_outputType);
        holdAll.add(lbl_outputFile);
        holdAll.add(lbl_executable);
        holdAll.add(tf_outputFile);
        holdAll.add(tf_executable);
        holdAll.add(selectFile);
        holdAll.add(selectExecutable);
        holdAll.add(save);

        selectFile.addActionListener(this);

        String layoutIndex = Globals.getLayout();
        cb_layoutEngine.setSelectedIndex(indexOf(layouts, layoutIndex));
        String typeIndex = Globals.getType();
        cb_outputType.setSelectedIndex(indexOf(types,typeIndex));
        tf_outputFile.setText(Globals.getOutputDir());
        tf_executable.setText(Globals.getExecutable());

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

        
        layout.putConstraint(SpringLayout.WEST, lbl_executable,
                5,
                SpringLayout.WEST, holdAll);
        layout.putConstraint(SpringLayout.NORTH, lbl_executable,
                135,
                SpringLayout.NORTH, holdAll);

        layout.putConstraint(SpringLayout.WEST, tf_executable,
                5,
                SpringLayout.EAST, lbl_outputFile);
        layout.putConstraint(SpringLayout.NORTH, tf_executable,
        		135,
                SpringLayout.NORTH, holdAll);

        layout.putConstraint(SpringLayout.WEST, selectExecutable,
                5,
                SpringLayout.EAST, tf_outputFile);
        layout.putConstraint(SpringLayout.NORTH, selectExecutable,
        		135,
                SpringLayout.NORTH, holdAll);

        
        
        
        
        layout.putConstraint(SpringLayout.WEST, save,
                5,
                SpringLayout.WEST, holdAll);
        layout.putConstraint(SpringLayout.NORTH, save,
                170,
                SpringLayout.NORTH, holdAll);

        contentPane.add(holdAll);
        pack();
        setSize(new Dimension(600, 400));

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        if (action.equals("save")) {
            Globals.setType((String)cb_outputType.getSelectedItem());
            Globals.setLayout((String)cb_layoutEngine.getSelectedItem());
            Globals.setOutputDir(tf_outputFile.getText());
            Globals.setExecutable(tf_executable.getText());
            logger.debug("Store properties");
       }
        if (action.equals("selectFile")) {
            final JFileChooser fc = new JFileChooser(Globals.getLastDir());
            fc.setCurrentDirectory(new File(Globals.getOutputDir()));
            fc.setAcceptAllFileFilterUsed(false);
            fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int returnVal = fc.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                 tf_outputFile.setText(String.valueOf(fc.getSelectedFile()));
            }
        }
        if (action.equals("selectExecutable")) {
            final JFileChooser fc = new JFileChooser(Globals.getLastDir());
            fc.setCurrentDirectory(new File(Globals.getOutputDir()));
            fc.setAcceptAllFileFilterUsed(false);
            fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int returnVal = fc.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                 tf_executable.setText(String.valueOf(fc.getSelectedFile()));
            }
        }
    }

    private int indexOf(String[] array, String str) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(str)) {
                return i;
            }
        }
        return 0;
    }
}
