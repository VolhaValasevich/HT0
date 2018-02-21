import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main (String[] args) throws IOException {
        String[] path = {"E:/Music1"};
        AudioParser parser = new AudioParser(path, "E:/output.html");
        parser.run();
    }
}
