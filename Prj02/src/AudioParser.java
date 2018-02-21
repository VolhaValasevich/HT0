import java.io.*;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.TreeMap;

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
    private String[] inputDirectory;
    private String outputFile;

    public AudioParser (String[] inputDirectory, String outputFile) {
        this.inputDirectory = inputDirectory;
        this.outputFile = outputFile;
    }

    //главная функция
    public void run() throws IOException {
        //есть ли исходные данные
        if (inputDirectory.length == 0) {
            return;
        }
        ArrayList<Mp3File> results = new ArrayList<>();
        //для каждой директории добавляем файлы
        for (String v: inputDirectory) {
            File searchDirectory = new File(v);
            if (!searchDirectory.isDirectory()) {
                System.out.println("Directory " + v + " does not exist.");
                continue;
            }
            results.addAll(getFiles(searchDirectory));
        }
        sortFiles(results);
        //вывод результатов
        /*for (Mp3File p : results) {
            System.out.println(p.toString());
        }*/
    }

    private void sortFiles(ArrayList<Mp3File> list) {
        TreeMap<String, Artist> results = new TreeMap<>();
        StringBuffer buffer = new StringBuffer();
        for (Mp3File v : list) {
            if (!results.containsKey(v.getArtist())) {
                results.put(v.getArtist(), new Artist(v.getArtist()));
            }
            results.get(v.getArtist()).add(v.getAlbum(), v);
        }
        buffer.append("<html><head></head><body>");
        for (Artist p : results.values()) {
            buffer.append(p.out());
        }
        buffer.append("</body></html>");
        try(FileWriter writer = new FileWriter(outputFile, false))
        {
            writer.write(buffer.toString());
        }
        catch(IOException ex){
            System.out.println("Cannot save results in a file! Please, check the filepath and the directory's properties.");
        }
    }

    //проверка расширения mp3 файла
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

    //получение параметров файлов из директорий и поддиректорий
    public ArrayList<Mp3File> getFiles (File searchDirectory) throws IOException {
        File[] filePaths = searchDirectory.listFiles();
        ArrayList<Mp3File> results = new ArrayList();
        for(File p : filePaths) {
            if (p.isDirectory()) {          //if the directory contains subdirectories, do a recursion
                results.addAll(getFiles(p));
            }
            if (checkExtension(p, "mp3")) {
                getParams(p);
                results.add(new Mp3File(title, artist, album, duration, p.getPath(), hash));
            }
        }
        return results;
    }

    //получение атрибутов mp3 файла
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
            //sometimes it gets into an endless cycle while getting duration
            //need to use another library for reading mp3 tags
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