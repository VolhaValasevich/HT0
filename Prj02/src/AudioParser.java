import java.io.*;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.digest.DigestUtils;
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


    public void getParams(File file) throws NoSuchAlgorithmException {

        try {
            InputStream input = new FileInputStream(file);
            //FileInputStream finput = new FileInputStream(file);
            MessageDigest md = MessageDigest.getInstance("MD5");
            ContentHandler handler = new DefaultHandler();
            Metadata metadata = new Metadata();
            Parser parser = new Mp3Parser();
            ParseContext parseCtx = new ParseContext();
            parser.parse(input, handler, metadata, parseCtx);
            DigestInputStream dis = new DigestInputStream(input, md);
            hash = md.digest();;
            //hash = DigestUtils.md5Hex(finput);
            input.close();

            String[] metadataNames = metadata.names();

            for(String name : metadataNames){
                System.out.println(name + ": " + metadata.get(name));
            }

            title = metadata.get("title");
            artist = metadata.get("xmpDM:artist");
            album = metadata.get("xmpDM:album");
            String dur = metadata.get("xmpDM:duration");
            int millDuration = Integer.valueOf(dur.substring(0, dur.lastIndexOf(".")));
            int hours = millDuration / 3600000;
            int minutes = (millDuration - (hours * 3600000)) / 60000;
            int seconds = (millDuration - (hours * 3600000) - (minutes * 60000)) / 1000;
            duration = convertTime(hours)+":"+convertTime(minutes)+":"+convertTime(seconds);


        } catch (SAXException | IOException | TikaException e) {
            e.printStackTrace();
        }
    }

    private String convertTime (int n) {
        if (n == 0) {
            return "00";
        } else if (n < 10) {
            return "0"+n;
        } else {
            return String.valueOf(n);
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