import java.io.File;

public class FileRecord {

    private int tab;
    private File dotFile;
    private File imageFile;

    public FileRecord(int tab, File dotFile, File imageFile) {
        this.tab = tab;
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

    public int getTab() {
        return tab;
    }

    public void setTab(int tab) {
        this.tab = tab;
    }
}
