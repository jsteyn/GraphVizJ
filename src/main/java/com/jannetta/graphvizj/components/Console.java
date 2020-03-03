package com.jannetta.graphvizj.components;

import com.jannetta.graphvizj.graphVizAPI.GraphVizAPI;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Console extends JPanel implements ActionListener {
    private static JTextArea console = new JTextArea();
    private static JScrollPane consoleScroller = new JScrollPane(console);
    private static Console thisConsole = null;
    private static JPanel buttonPanel = new JPanel();
    private static JButton clear = new JButton("Clear");
    private static JButton save = new JButton("Save");

    private Console() {
    }

    public static Console getInstance() {
        if (thisConsole == null) {
            thisConsole = new Console();
            thisConsole.setLayout(new BorderLayout());

            clear.addActionListener(thisConsole);
            save.addActionListener(thisConsole);

            buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            buttonPanel.add(clear);
            buttonPanel.add(save);

            thisConsole.add(consoleScroller, BorderLayout.CENTER);
            thisConsole.add(buttonPanel, BorderLayout.SOUTH);
        }
        return thisConsole;
    }

    public static void log(String text) {
        console.append(text + "\n");
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String action = actionEvent.getActionCommand();
        if (action.equals("Clear")) {
            console.setText("");
        }
        if (action.equals("Save")) {
            final JFileChooser fc = new JFileChooser(".");
            FileNameExtensionFilter filter = new FileNameExtensionFilter("txt", "txt");
            int returnVal = fc.showSaveDialog(this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File logfile = fc.getSelectedFile();
                // add .gv extension if it is not added already
                if (!fc.getSelectedFile().getAbsolutePath().endsWith(".txt")) {
                    logfile = new File(fc.getSelectedFile() + ".txt");
                }
                try {
                    PrintWriter pw = new PrintWriter(logfile);
                    pw.write(console.getText());
                    pw.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
