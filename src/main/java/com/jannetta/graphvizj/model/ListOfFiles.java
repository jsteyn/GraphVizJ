package com.jannetta.graphvizj.model;

import java.io.File;
import java.util.ArrayList;

public class ListOfFiles {
    private static ListOfFiles listOfFiles;
    private ArrayList<FileRecord> files = new ArrayList<>();

    private ListOfFiles() {}

    public static ListOfFiles getInstance() {
        if (listOfFiles == null) {
            listOfFiles = new ListOfFiles();
        }
        return listOfFiles;
    }

    public ArrayList<FileRecord> getFiles() {
        return files;
    }

    public void setFiles(ArrayList<FileRecord> files) {
        this.files = files;
    }

    public void remove(int index) {
        files.remove(index);
        for (int i = 0; i < files.size(); i++) {
            files.get(i).setTabIndex(i);
        }
    }

    public void add(FileRecord newFileRecord) {
        files.add(newFileRecord);
    }

    public void add(int i, File dotfile, File graphfile) {
        FileRecord fileRecord = new FileRecord(i, dotfile, graphfile);
        files.add(fileRecord);
    }

    /**
     * Returns the index of the tab containing info for a file or -1 if the file is
     * not in the ArrayList
     * @param file the name of the file as a string
     * @return index of the tab of the file
     */
    public int indexOfTab(String file) {
        for (int i = 0; i < files.size(); i++) {
            if (files.get(i).getDotFile().getAbsolutePath().equals(file)) {
                return i;
            }
        }
        return -1;
    }

}
