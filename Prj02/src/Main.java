import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main (String[] args) throws IOException {
        ArrayList<String> path = new ArrayList<>();
        String output = "output.html";       //default output file name
       // path.add("E:/Music1");
        String inputExample = "\nInput example: C:/SomePath1/ \"D:/Some Path2\" E:/SomePath3/ C:/SomePath4 c:/results.html";
        if (args.length < 1) {
            System.out.println("Input one or more directories and the name of the output file (optional), separated with whitespaces. "
                    + "If any of the paths contains whitespaces, surround it with quotation marks. " + inputExample);
            return;
        }
        for (String v : args) {
            if (Check.ifDirectory(v)) {
                path.add(v);
            } else if (Check.ifHtml(v)) {
                output = v;
                break;
            } else {
                System.out.println("Error in line \"" + v + "\". Please check your input." + inputExample);
                return;
            }
        }
        if (path.isEmpty()) {
            System.out.println("No directories have been found in your input." + inputExample);
            return;
        }
        AudioParser parser = new AudioParser(path, output);
        parser.run();

    }
}
