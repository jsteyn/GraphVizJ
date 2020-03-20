package com.jannetta.graphvizj.components;

import com.jannetta.graphvizj.components.DotRSyntaxTextArea;
import com.jannetta.graphvizj.components.DrawPanel;
import com.jannetta.graphvizj.components.RightHandPanes;
import com.jannetta.graphvizj.components.TextPanes;
import com.jannetta.graphvizj.controller.Globals;
import com.jannetta.graphvizj.graphVizAPI.GraphVizAPI;
import com.jannetta.graphvizj.model.FileRecord;
import com.jannetta.graphvizj.model.ListOfFiles;
import org.apache.log4j.Logger;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.DefaultEditorKit;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.Properties;
import java.util.Scanner;

public class MenuBar extends JMenuBar implements ActionListener {
    private Logger logger = Logger.getLogger(this.getClass());
    private Console console = Console.getInstance();
    private TextPanes textPanes;
    private RightHandPanes rightHandPanes;
    private Properties properties = new Properties();
    private String executable;
    private ListOfFiles listOfFiles = ListOfFiles.getInstance();
    private int newFileCounter = 0;
    private String newFilePrefix = "graph";

    public MenuBar(TextPanes textPanes, RightHandPanes rightHandPanes) {
        super();
        this.textPanes = textPanes;
        this.rightHandPanes = rightHandPanes;
        try {
            File f = new File("system.properties");
            if (!(f.exists())) {
                OutputStream out = new FileOutputStream(f);

            }
            InputStream is = new FileInputStream(f);
            properties.load(is);
            executable = properties.getProperty("executable");
            logger.debug("Dot executable: " + executable);
            console.log("Dot executable: " + executable);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JMenu file;
        JMenu edit;
        JMenu window;
        JMenu graph;
        JMenu help;

        JMenuItem newfile;
        JMenuItem openFile;
        JMenuItem saveFile;
        JMenuItem saveAsFile;
        JMenuItem exit;

        JMenuItem cut;
        JMenuItem copy;
        JMenuItem paste;

        JMenuItem close;
        JMenuItem closeAll;

        JMenuItem settings;
        JMenuItem layout;

        JMenuItem about;

        file = new JMenu("File");
        file.setMnemonic('F');
        edit = new JMenu("Edit");
        edit.setMnemonic('E');
        window = new JMenu("Window");
        window.setMnemonic('W');
        graph = new JMenu("Graph");
        graph.setMnemonic('G');
        help = new JMenu("Help");
        help.setMnemonic('H');

        // File
        newfile = new JMenuItem("New", 'N');
        newfile.setAccelerator((KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK)));
        newfile.addActionListener(this);
        openFile = new JMenuItem("Open", 'O');
        openFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        openFile.addActionListener(this);
        saveFile = new JMenuItem("Save", 'S');
        saveFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        saveFile.addActionListener(this);
        saveAsFile = new JMenuItem("Save As", 'A');
        saveAsFile.addActionListener(this);
        exit = new JMenuItem("Exit", 'X');
        exit.addActionListener(this);

        //Edit
        cut = new JMenuItem(new DefaultEditorKit.CutAction());
        cut.setText("Cut");
        cut.setMnemonic(KeyEvent.VK_T);
        cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.CTRL_MASK));

        copy = new JMenuItem(new DefaultEditorKit.CopyAction());
        copy.setText("Copy");
        copy.setMnemonic(KeyEvent.VK_C);
        copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));

        paste = new JMenuItem(new DefaultEditorKit.PasteAction());
        paste.setText("Paste");
        paste.setMnemonic(KeyEvent.VK_V);
        paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));

        //Window
        close = new JMenuItem("Close");
        close.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.CTRL_MASK));
        close.addActionListener(this);
        closeAll = new JMenuItem("Close All", 'A');
        closeAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
        closeAll.addActionListener(this);

        //Graph
        settings = new JMenuItem("Settings");
        settings.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, ActionEvent.SHIFT_MASK));
        settings.addActionListener(this);
        layout = new JMenuItem("Layout");
        layout.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));
        layout.addActionListener(this);

        //Help
        about = new JMenuItem("About", 'A');
        about.addActionListener(this);

        //File
        file.add(newfile);
        file.add(openFile);
        file.add(saveFile);
        file.add(saveAsFile);
        file.add(exit);

        //Edit
        edit.add(cut);
        edit.add(copy);
        edit.add(paste);

        //Window
        window.add(close);
        window.add(closeAll);

        //Graph
        graph.add(settings);
        graph.add(layout);

        //Help
        help.add(about);

        this.add(file);
        this.add(edit);
        this.add(window);
        this.add(graph);
        this.add(help);
    }

    /**
     * Create a new tab, set it as selected and return its index
     *
     * @return the index of the newly created tab
     */
    private int newTab(String dotfile, String graphfile) {
        // Add a new text tab
        DotRSyntaxTextArea dotRSyntaxTextArea = new DotRSyntaxTextArea();
        DrawPanel drawPanel = new DrawPanel();
        RTextScrollPane rTextScrollPane = new RTextScrollPane(dotRSyntaxTextArea);
        JScrollPane jScrollPane = new JScrollPane();
        jScrollPane.setViewportView(drawPanel);
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        textPanes.add(rTextScrollPane, dotfile);
        rightHandPanes.add(jScrollPane, graphfile);
        int index = textPanes.getTabCount() - 1;
        textPanes.setSelectedIndex(index);
        rightHandPanes.setSelectedIndex(index);
        return textPanes.getTabCount() - 1;
    }

    /**
     * Render the dot file, i.e. create the diagram, write it to the given filename
     *
     * @param filename  The name of the dot file
     * @param graphfile The name of the file to write the graph to
     * @param index     The index of the tab in which the diagram should be displayed (-1 if it is a
     *                  newly opened file)
     */
    private void renderNew(File filename, File graphfile, int index) {
        // Run the dot exectable
        byte[] byteStream = GraphVizAPI.runDot(executable, filename.getAbsolutePath());
        // Write the diagram to file
        GraphVizAPI.writeToFile(byteStream, graphfile);
        // Load the diagram into the panel
        DrawPanel drawPanel = (DrawPanel) ((JScrollPane) rightHandPanes.getComponentAt(index)).getViewport().getComponent(0);
        drawPanel.setByteStream(byteStream);
        drawPanel.readFile(graphfile.getAbsolutePath());
        ((JScrollPane) rightHandPanes.getComponentAt(index)).setViewportView(drawPanel);
        rightHandPanes.getComponentAt(index).revalidate();
        rightHandPanes.getComponentAt(index).repaint();
//        logger.debug( rightHandPanes.getComponentAt(textPanes.getSelectedIndex()).getClass());
    }

    protected ImageIcon createImageIcon(String path,
                                        String description) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        try {
            Image icon = toolkit.getImage(ClassLoader.getSystemResource("Logo.png"));
            return new ImageIcon(icon);
        } catch (NullPointerException e) {
            logger.error("Logo.png not found.");
            return null;
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("About")) {
            ImageIcon icon = createImageIcon("Logo.png",
                    "a pretty but meaningless splat");
            JOptionPane.showMessageDialog(this,
                    "GraphVizJ is an editor, written in Java for Graphviz.\n" +
                            "It provides functionality similar to GVEdit but with a few extra\n" +
                            "features and differences in the user interface.\n\n" +
                            "Copyright: Jannetta S Steyn, 2020",
                    "About GraphVizJ", JOptionPane.PLAIN_MESSAGE,
                    icon);
        }
        if (e.getActionCommand().equals("Exit")) {
            System.exit(0);
        }
        if (e.getActionCommand().equals("New")) {
            newFileCounter++;
            File dotFile = new File(newFilePrefix + newFileCounter + ".gv");
            File graphFile = new File(newFilePrefix + newFileCounter + "png");
            int index = newTab(dotFile.getName(), graphFile.getName());
            listOfFiles.add(index, dotFile, graphFile);

        }
        if (e.getActionCommand().equals("Save")) {
            if (textPanes.getTitleAt(textPanes.getSelectedIndex()).startsWith(newFilePrefix)) {
                saveAs();
            } else {
                save();
            }
        }
        if (e.getActionCommand().equals("Save As")) {
            saveAs();
        }
        if (e.getActionCommand().equals("Open")) {
            final JFileChooser fc = new JFileChooser(Globals.getLastDir());
            FileNameExtensionFilter filter = new FileNameExtensionFilter("gv", "gv");
            fc.setFileFilter(filter);
            int returnVal = fc.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File graphfile;
                File file = fc.getSelectedFile();
                Globals.setLastDir(file.getParent());

                // check if file is already open, if not, open it
                int tabindex = listOfFiles.indexOfTab(file.getAbsolutePath());
                if (tabindex == -1) {
                    try {
                        // Add a new tab
                        Scanner sc = new Scanner(file);
                        StringBuilder sb = new StringBuilder();
                        while (sc.hasNext()) {
                            sb.append(sc.nextLine() + "\n");
                        }
                        String filename = file.getName();
                        logger.debug("Opened " + filename);
                        graphfile = new File(file.getAbsolutePath().replace(".gv", ".png"));
                        int selected = newTab(filename, graphfile.getName());
                        ((RTextScrollPane) textPanes.getComponentAt(selected)).getTextArea().setText(sb.toString());
                        renderNew(file, graphfile, selected);

                        // Add filename to list of files in tabs
                        listOfFiles.getFiles().add(new FileRecord(selected, file, graphfile));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    logger.trace("Set index to: " + tabindex);
                    rightHandPanes.getSelectedIndex();
                    rightHandPanes.updateUI();
                    textPanes.setSelectedIndex(tabindex);
                }

            }
            rightHandPanes.setSelectedIndex(textPanes.getTabCount() - 1);

        }
        if (e.getActionCommand().equals("Cut")) {
            logger.debug("Cut");
        }
        if (e.getActionCommand().equals("Copy")) {
            logger.debug("Copy");
        }
        if (e.getActionCommand().equals("Paste")) {
            logger.debug("Paste");
        }
        if (e.getActionCommand().equals("Close")) {
            int index = textPanes.getSelectedIndex();
            closeTab(index);
        }
        if (e.getActionCommand().equals("Close All")) {
            while (textPanes.getTabCount() > 0) {
                closeTab(0);
            }
        }
        if (e.getActionCommand().equals("Layout")) {
            save();
        }
        if (e.getActionCommand().equals("Settings")) {
            int index = textPanes.getSelectedIndex();

        }
    }

    private void save() {
        logger.debug("Saving ..." + listOfFiles.getFiles().get(textPanes.getSelectedIndex()).getDotFile());
        int index = textPanes.getSelectedIndex();
        File dotFile = listOfFiles.getFiles().get(index).getDotFile();
        File gaphFile = listOfFiles.getFiles().get(index).getImageFile();
        GraphVizAPI.writeText(((RTextScrollPane) textPanes.getSelectedComponent()).getTextArea().getText(), dotFile);
        renderNew(dotFile, gaphFile, index);
    }

    private void saveAs() {
        int currentSelectedIndex = textPanes.getSelectedIndex();
        final JFileChooser fc = new JFileChooser(Globals.getLastDir());
        FileNameExtensionFilter filter = new FileNameExtensionFilter("gv", "gv");
        int returnVal = fc.showSaveDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File dotFile = fc.getSelectedFile();
            // add .gv extension if it is not added already
            if (!fc.getSelectedFile().getAbsolutePath().endsWith(".gv")) {
                dotFile = new File(fc.getSelectedFile() + ".gv");
            }
            fc.setFileFilter(filter);
            File graphfile = new File(dotFile.getAbsolutePath().replace(".gv", ".png"));
            GraphVizAPI.writeText(((RTextScrollPane) textPanes.getSelectedComponent()).getTextArea().getText(), dotFile);
            renderNew(dotFile, graphfile, currentSelectedIndex);
            textPanes.setTitleAt(currentSelectedIndex, dotFile.getName());
            rightHandPanes.setTitleAt(currentSelectedIndex, graphfile.getName());
            listOfFiles.getFiles().get(currentSelectedIndex).setDotFile(dotFile);
            listOfFiles.getFiles().get(currentSelectedIndex).setImageFile(graphfile);
            Globals.setLastDir(dotFile.getParent());
        }

    }

    private void closeTab(int index) {
        logger.debug("Close tab " + index);
        // TODO Check if file was changed and ask to save
        textPanes.remove(index);
        rightHandPanes.remove(index);
        listOfFiles.getFiles().remove(index);

    }
}
