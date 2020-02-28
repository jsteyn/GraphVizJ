import graphVizAPI.GraphVizAPI;
import org.apache.log4j.Logger;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

public class MenuBar extends JMenuBar implements ActionListener {
    private Logger logger = Logger.getLogger(this.getClass());
    private TextPanes textPanes;
    private RightHandPanes rightHandPanes;
    Properties properties = new Properties();
    String executable;
    ListOfFiles listOfFiles = ListOfFiles.getInstance();

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

        JMenuItem close;
        JMenuItem closeAll;

        JMenuItem settings;
        JMenuItem layout;

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
        saveAsFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
        exit = new JMenuItem("Exit", 'X');

        close = new JMenuItem("Close");
        close.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.CTRL_MASK));
        close.addActionListener(this);

        settings = new JMenuItem("Settings");
        settings.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, ActionEvent.SHIFT_MASK));
        settings.addActionListener(this);
        layout = new JMenuItem("Layout");
        layout.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));
        layout.addActionListener(this);

        file.add(newfile);
        file.add(openFile);
        file.add(saveFile);
        file.add(saveAsFile);
        file.add(exit);

        window.add(close);

        graph.add(settings);
        graph.add(layout);

        this.add(file);
        this.add(edit);
        this.add(window);
        this.add(graph);
        this.add(help);
    }

    private void newTab() {
        // Add a new text tab
        DotRSyntaxTextArea dotRSyntaxTextArea = new DotRSyntaxTextArea();
        RTextScrollPane rTextScrollPane = new RTextScrollPane(dotRSyntaxTextArea);
        textPanes.addTab("New", rTextScrollPane);
        textPanes.setSelectedIndex(textPanes.getTabCount() - 1);
    }


    private void renderNew(File filename, File graphfile, int index) {
        // Run the dot exectable
        byte[] byteStream = GraphVizAPI.runDot(executable, filename.getAbsolutePath());
        // Write the diagram to file
        GraphVizAPI.writeToFile(byteStream, graphfile);
        DrawPanel graphPanel = null;
        if (index == -1) {
            graphPanel = new DrawPanel();
            JScrollPane jScrollPane = new JScrollPane(graphPanel);
            jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            rightHandPanes.addTab(graphfile.getName(), graphPanel);
        } else {
            logger.debug( rightHandPanes.getComponentAt(index).getClass());
            graphPanel = (DrawPanel)rightHandPanes.getComponentAt(index);
        }

        graphPanel.setByteStream(byteStream);
        graphPanel.readFile(graphfile.getName());
//        logger.debug( rightHandPanes.getComponentAt(textPanes.getSelectedIndex()).getClass());
    }


    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Exit")) {
            System.exit(0);
        }
        if (e.getActionCommand().equals("New")) {
            newTab();
        }
        if (e.getActionCommand().equals("Save")) {
            logger.debug("Saving ..." + listOfFiles.getFiles().get(textPanes.getSelectedIndex()).getDotFile());
            int index = textPanes.getSelectedIndex();
            File dotFile = listOfFiles.getFiles().get(index).getDotFile();
            File gaphFile = listOfFiles.getFiles().get(index).getImageFile();
            GraphVizAPI.writeText(((RTextScrollPane) textPanes.getSelectedComponent()).getTextArea().getText(), dotFile);
            renderNew(dotFile, gaphFile, index);
        }
        if (e.getActionCommand().equals("Open")) {
            // Add a new tab
            newTab();
            int selected = textPanes.getSelectedIndex();
            final JFileChooser fc = new JFileChooser(".");
            FileNameExtensionFilter filter = new FileNameExtensionFilter("gv", "gv");
            fc.setFileFilter(filter);
            int returnVal = fc.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File graphfile;
                File file = fc.getSelectedFile();
                try {
                    Scanner sc = new Scanner(file);
                    StringBuilder sb = new StringBuilder();
                    while (sc.hasNext()) {
                        sb.append(sc.nextLine() + "\n");
                    }
                    ((RTextScrollPane) textPanes.getSelectedComponent()).getTextArea().setText(sb.toString());
                    String filename = file.getName();
                    logger.debug("Opened " + filename);
                    textPanes.setTitleAt(textPanes.getSelectedIndex(), filename);
                    graphfile = new File(file.getAbsolutePath().replace(".gv", ".png"));
                    renderNew(file, graphfile, -1);

                    // Add filename to list of files in tabs
                    listOfFiles.getFiles().add(new FileRecord(selected, file, graphfile));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }
            rightHandPanes.setSelectedIndex(textPanes.getTabCount() - 1);

        }
        if (e.getActionCommand().equals("Close")) {
            int index = textPanes.getSelectedIndex();
            logger.debug("Close tab " + index);
            // TODO Check if file was changed and ask to save
            textPanes.remove(index);
            rightHandPanes.remove(index);
            listOfFiles.getFiles().remove(index);
        }
        if (e.getActionCommand().equals("Layout")) {
            logger.debug("Run dot");
        }
        if (e.getActionCommand().equals("Settings")) {
            int index = textPanes.getSelectedIndex();

        }
    }
}
