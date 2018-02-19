import java.io.File;
import java.security.NoSuchAlgorithmException;

public class Main {

    public static void main (String[] args) throws NoSuchAlgorithmException {
        AudioParser parser = new AudioParser();
        File file = new File ("D:/file.mp3");
        parser.getParams(file);
        System.out.println("Title: " + parser.getTitle());
        System.out.println("Artist: " + parser.getArtist());
        System.out.println("Album: " + parser.getAlbum());
        System.out.println("Duration: " + parser.getDuration());
        System.out.println("Hash: " + parser.getHash());
    }

}
