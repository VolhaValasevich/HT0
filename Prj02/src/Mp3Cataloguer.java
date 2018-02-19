import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Mp3Cataloguer {

    String[] inputDirectory;
    String outputFile;

    public Mp3Cataloguer (String[] inputDirectory, String outputFile) {
        this.inputDirectory = inputDirectory;
        this.outputFile = outputFile;
    }

    public void run() throws IOException {
        if (inputDirectory.length == 0) {
            return;
        }
        ArrayList<Mp3File> results;
        for (String v: inputDirectory) {
            File searchDirectory = new File(v);
            if (!searchDirectory.isDirectory()) {
                System.out.println("Directory " + v + " does not exist.");
                continue;
            }
            results = getFiles(searchDirectory);
        }
    }

    public static boolean checkExtension(File f, String ext) {
        String fileName = f.getName();
        String extension = "";
        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            extension = fileName.substring(i+1);
        }
        if (extension.equals(ext)) {
            return true;
        }
        else return false;
    }

    public static ArrayList<Mp3File> getFiles (File searchDirectory) throws IOException {
        File[] filePaths = searchDirectory.listFiles();
        ArrayList<Mp3File> results = new ArrayList();
        for(File p : filePaths) {
            if (p.isDirectory()) {  //if the directory contains subdirectories, do a recursion
                results.addAll(getFiles(p));
            }
            if (checkExtension(p, "mp3")) {
                //Path filepath = Paths.get(p.getPath());
                //reading basic attributes to get creation date and time
                //BasicFileAttributes basicAttr = Files.readAttributes(filepath, BasicFileAttributes.class);
                //Mp3File result = new Mp3File(p.getName(), p.getPath(), p.length(), basicAttr.creationTime().toString());
                //results.add(result);
            }
        }
        return results;
    }
}
