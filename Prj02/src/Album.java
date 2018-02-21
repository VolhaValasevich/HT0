import java.util.HashSet;
import java.util.TreeMap;

public class Album {
    private String album;                   //name of the album
    private HashSet<byte[]> hash;           //all hash codes
    private TreeMap<String, Mp3File> list;  //songs in the album <Song Title, Song>

    public Album(String name) {
        album = name;
        hash = new HashSet<>();
        list = new TreeMap<>();
    }

    public void add(String name, Mp3File obj) {
        if (hash.contains(obj.getHash())) {
            //полный дубликат
            System.out.println(obj.getPath() + " - дубликат");
            return;
        } else if (list.containsKey(obj.getTitle())) {
            //одноименный файл
            System.out.println(obj.getPath() + " - одно имя");
            return;
        } else {
            list.put(obj.getTitle(), obj);
        }

    }

    public String out() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(" ").append(album).append("<br>");
        for (Mp3File v : list.values()) {
            buffer.append(v.out());
        }
        return buffer.toString();
    }
}
