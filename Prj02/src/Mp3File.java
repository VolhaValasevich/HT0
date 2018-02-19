import java.io.File;

public class Mp3File { //extends File{
    /*   Исполнитель1
    Альбом-1
     Название-1 Длительность-1 (Ссылка на локальный файл)
*/
    String name;
    String author;
    String album;
    String time;
    String path;

    public Mp3File (String name, String author, String album, String time, String path) {
        this.name = name;
        this.author = author;
        this.album = album;
        this.time = time;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getAlbum() {
        return album;
    }

    public String getTime() {
        return time;
    }

    public String getPath() {
        return path;
    }
}
