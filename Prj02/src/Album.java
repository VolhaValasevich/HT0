import java.util.HashSet;
import java.util.TreeMap;

public class Album {
    private String album;                       //name of the album
    private TreeMap<String, Mp3File> list;      //songs in the album <Song Title, Song>
    private HashSet<Mp3File> nameDups;          //duplicates by title, artist and album

    public Album(String name) {
        album = name;
        list = new TreeMap<>();
        nameDups = new HashSet<>();
    }

    public void add(String name, Mp3File obj) {
        if (list.containsKey(obj.getTitle())) {
            nameDups.add(obj);
            nameDups.add(list.get(obj.getTitle()));
            return;
        } else {
            list.put(obj.getTitle(), obj);
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


    public HashSet<Mp3File> getNameDups() {
        return nameDups;
    }
}
