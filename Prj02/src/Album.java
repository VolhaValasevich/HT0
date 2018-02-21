import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeMap;

public class Album {
    private String album;                       //name of the album
    private HashSet<byte[]> hash;               //all hash codes
    private TreeMap<String, Mp3File> list;      //songs in the album <Song Title, Song>
    private ArrayList<Mp3File> hashDups;
    private ArrayList<Mp3File> nameDups;
    //private StringBuffer hashDups;              //checksum duplicates
    //private StringBuffer nameDups;              //duplicates by title, artist and album

    public Album(String name) {
        album = name;
        hash = new HashSet<>();
        list = new TreeMap<>();
        hashDups = new ArrayList<>();
        nameDups = new ArrayList<>();
    }

    public void add(String name, Mp3File obj) {
        if (hash.contains(obj.getHash())) {
            //hashDups.append(obj.getPath()).append(" Hash: ").append(obj.getHash()+"\n");
            hashDups.add(obj);
            return;
        } else if (list.containsKey(obj.getTitle())) {
            //nameDups.append(obj.getPath()+"\n");
            nameDups.add(obj);
            return;
        } else {
            list.put(obj.getTitle(), obj);
            hash.add(obj.getHash());
        }

    }

    public String out() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("&nbsp&nbsp&nbsp&nbspAlbum: ").append(album).append("<br>");
        for (Mp3File v : list.values()) {
            buffer.append(v.out());
        }
        return buffer.toString();
    }

    public ArrayList<Mp3File> getHashDups() {
        return hashDups;
    }

    public ArrayList<Mp3File> getNameDups() {
        return nameDups;
    }
}
