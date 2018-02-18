import java.util.ArrayList;

public class Building {
    private String name;
    private ArrayList<Room> rooms;

    private Building(String name) {
        this.name = name;
        rooms = new ArrayList<>();
    }

    public void describe() {
        if (!checkRoomsIllumination()) {
            return;
        }
        StringBuffer message = new StringBuffer();
        message.append(name).append("\n");
        for (Room v : rooms) {
            message.append(v.describe());
        }
        String output = message.toString();
        System.out.println(message);
    }

    private boolean checkRoomsIllumination() {
        boolean flag = true;
        for (Room v : rooms) {
            if (!v.checkIllumination()) {
                flag = false;
                System.out.println("В комнате " + v.getName() + " слишком низкий уровень освещенности (" +
                        v.getIllumination() + "лк). Минимально допустимый - 300 лк.");
            }
        }
        return flag;
    }

    public void addRoom (Room room) {
        rooms.add(room);
    }

    public Room getRoom (int index) {
        return rooms.get(index);
    }

    public void removeRoom (int index) {
        rooms.remove(index);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public void setRooms(ArrayList<Room> rooms) {
        this.rooms = rooms;
    }
}
