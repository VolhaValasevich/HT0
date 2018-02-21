public class Mp3File {

    private String title;
    private String artist;
    private String album;
    private String duration;
    private String path;
    private byte[] hash;

    public Mp3File (String name, String author, String album, String time, String path, byte[] hash) {
        this.title = name;
        this.artist = author;
        this.album = album;
        this.duration = time;
        this.path = path;
        this.hash = hash;
    }

    public String out() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp").append(title).append("; ").append(duration).append(";" +
                " <a href=\"file:///").append(path).append("\">").append(path).append("; </a>").append("<br>");

        return buffer.toString();
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

    public String getPath() {
        return path;
    }

    public byte[] getHash() {
        return hash;
    }

    public String getTitle() {
        return title;
    }

}
