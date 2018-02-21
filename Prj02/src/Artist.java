import java.util.TreeMap;

public class Artist {
    private String artist;                      //name of the artist
    private TreeMap<String, Album> list;        //artist's albums <album name, album>

    public Artist(String name) {
        this.artist = name;
        list = new TreeMap<>();
    }

    public String getName() {
            return artist;
        }

    public TreeMap<String, Album> getList() {
            return list;
        }

    public void add(String album, Mp3File obj) {
        if (!list.containsKey(album)) {
            list.put(album, new Album(obj.getAlbum()));
        }
        list.get(album).add(obj.getTitle(), obj);
    }

    public String out() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("<p>Artist: ").append(artist).append("<br>");
        for (Album v : list.values()) {
            buffer.append(v.out());
        }
        buffer.append("</p>");
        return buffer.toString();
    }

    public StringBuffer getHashDups() {
        StringBuffer dups = new StringBuffer();
        for (Album v : list.values()) {
            dups.append(v.getHashDups());
        }
        return dups;
    }

    public StringBuffer getNameDups() {
        StringBuffer dups = new StringBuffer();
        for (Album v : list.values()) {
            dups.append(v.getNameDups());
        }
        return dups;
    }
}


