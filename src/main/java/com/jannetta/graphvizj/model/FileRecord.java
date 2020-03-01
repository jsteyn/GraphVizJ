package com.jannetta.graphvizj.model;

import java.io.File;

public class FileRecord {

    private int tabIndex;
    private File dotFile;
    private File imageFile;

    public FileRecord(int tabIndex, File dotFile, File imageFile) {
        this.tabIndex = tabIndex;
        this.dotFile = dotFile;
        this.imageFile = imageFile;
    }

    public File getDotFile() {
        return dotFile;
    }

    public void setDotFile(File dotFile) {
        this.dotFile = dotFile;
    }

    public File getImageFile() {
        return imageFile;
    }

    public void setImageFile(File imageFile) {
        this.imageFile = imageFile;
    }

    public int getTabIndex() {
        return tabIndex;
    }

    protected void setTabIndex(int tabIndex) {
        this.tabIndex = tabIndex;
    }
}
