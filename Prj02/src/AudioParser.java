import java.io.*;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class AudioParser {

    private String title;
    private String artist;
    private String album;
    private String duration;
    private byte[] hash;
    private HashSet<byte[]> allHash;               //all hash codes
    private ArrayList<Mp3File> hashDups;
    private ArrayList<String> inputDirectory;
    private String outputFile;

    public AudioParser (ArrayList<String> inputDirectory, String outputFile) {
        this.inputDirectory = inputDirectory;
        this.outputFile = outputFile;
        allHash = new HashSet<>();
        hashDups = new ArrayList<>();
    }

    public void run() throws IOException {
        ArrayList<Mp3File> results = new ArrayList<>();
        //adding files for each directory
        for (String v: inputDirectory) {
            File searchDirectory = new File(v);
            if (!searchDirectory.isDirectory()) {
                System.out.println("Directory " + v + " does not exist.");
                continue;
            }
            results.addAll(getFiles(searchDirectory));
        }
        writeFiles(results);
    }

    //getting all file parameters
    public ArrayList<Mp3File> getFiles (File searchDirectory) throws IOException {
        File[] filePaths = searchDirectory.listFiles();
        ArrayList<Mp3File> results = new ArrayList();
        for(File p : filePaths) {
            if (p.isDirectory()) {          //if the directory contains subdirectories, do a recursion
                results.addAll(getFiles(p));
            }
            if (Check.ifMp3(p.getName())) {
                getParams(p);
                Mp3File newMp3 = new Mp3File(title, artist, album, duration, p.getPath(), hash);
                results.add(newMp3);
                System.out.println("add " + newMp3.getTitle());
                if (allHash.contains(newMp3.getHash())) {
                    hashDups.add(newMp3);
                } else {
                    allHash.add(newMp3.getHash());
                }
            }
        }
        return results;
    }

    //getting mp3 tags
    public void getParams(File file) {

        try {
            InputStream input = new FileInputStream(file);
            MessageDigest md = null;
            try {
                md = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            ContentHandler handler = new DefaultHandler();
            Metadata metadata = new Metadata();
            Parser parser = new Mp3Parser();
            ParseContext parseCtx = new ParseContext();
            //sometimes external library method gets into an endless cycle while calculating duration
            //seemingly regardless of file's properties. might need to use another library for reading mp3 tags
            parser.parse(input, handler, metadata, parseCtx);
            DigestInputStream dis = new DigestInputStream(input, md);
            hash = md.digest();
            input.close();

            title = checkNullData(metadata.get("title"));
            artist = checkNullData(metadata.get("xmpDM:artist"));
            album = checkNullData(metadata.get("xmpDM:album"));
            String dur =  metadata.get("xmpDM:duration");
            //converting milliseconds into the time format (00:00:00)
            try {
                int millDuration = Integer.valueOf(dur.substring(0, dur.lastIndexOf(".")));
                int hours = (millDuration / 3600000);
                int minutes = (millDuration - (hours * 3600000)) / 60000;
                int seconds = (millDuration - (hours * 3600000) - (minutes * 60000)) / 1000;
                duration = convertTime(hours)+":"+convertTime(minutes)+":"+convertTime(seconds);
            } catch (NullPointerException ex) {
                duration = "unknown";
            }
        } catch (SAXException | IOException | TikaException e) {
            e.printStackTrace();
        }
    }

    //converting a number into a time format
    private String convertTime (int n) {
        if (n == 0) {
            return "00";
        } else if (n < 10) {
            return "0"+n;
        } else {
            return String.valueOf(n);
        }
    }

    //replacing "null" with "unknown"
    private String checkNullData (String s) {
        if (s == null || s.equals("")) {
            return "unknown";
        } else return s;
    }

    //writing data into logs and output file
    private void writeFiles(ArrayList<Mp3File> list) {
        TreeMap<String, Artist> results = new TreeMap<>();
        StringBuffer buffer = new StringBuffer();
        StringBuffer dups = new StringBuffer();
        System.setProperty("log4j.configurationFile", "log4j2.xml");
        Logger logger = LogManager.getRootLogger();
        //sorting data by artist and album
        for (Mp3File v : list) {
            if (!results.containsKey(v.getArtist())) {
                results.put(v.getArtist(), new Artist(v.getArtist()));
            }
            results.get(v.getArtist()).add(v.getAlbum(), v);
        }
        buffer.append("<html><head></head><body>");
        //generating an html file and log data
        for (Artist p : results.values()) {
            buffer.append(p.out());
            dups.append(p.getNameDups());
        }
        buffer.append("</body></html>");
        //displaying logs (didn't have the time to make them look like in the task)
        if (dups.length() != 0) {
            logger.info("Duplicates by name found:\n" + dups.toString());
        }
        if (!hashDups.isEmpty()) {
            StringBuffer s = new StringBuffer();
            s.append("Duplicates by checksum found:\n");
            for (Mp3File p : hashDups) {
                s.append(p.getTitle()+" ").append(p.getArtist()+" ").append(p.getAlbum()+" ").append(p.getPath()+"\n");
            }
            logger.info(s.toString());
        }

        try(FileWriter writer = new FileWriter(outputFile, false))
        {
            writer.write(buffer.toString());
        }
        catch(IOException ex){
            System.out.println("Cannot save results in a file! Please, check the filepath and the directory's properties.");
        }
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }

    public String getDuration() {
        return duration;
    }

    public byte[] getHash() {
        return hash;
    }
}