import java.util.ArrayList;

public class ListOfFiles {
    private static ListOfFiles listOfFiles;
    private ArrayList<FileRecord> files = new ArrayList<>();

    ListOfFiles() {}

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
            files.get(i).setTab(i);
        }
    }

}
