import java.util.ArrayList;

public class Building {
    private String name;
    private ArrayList<Room> rooms;

    public Building(String name) {
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
        System.out.println(output);
    }

    private boolean checkRoomsIllumination() {
        boolean flag = true;
        for (Room v : rooms) {
            try {
                v.checkIllumination();
            } catch (IlluminanceTooLittleException ex) {
                System.out.println(ex.getMessage());
                flag = false;
            }
        }
        return flag;
    }

    public void addRoom (Room room) {
        rooms.add(room);
    }

    public void addRoom(String name) {
        try {
            Room room = new Room(name);
            rooms.add(room);
        } catch (IlluminanceTooMuchException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void addRoom(String name, double space) {
        try {
            Room room = new Room(name, space);
            rooms.add(room);
        } catch (IlluminanceTooMuchException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void addRoom(String name, double space, int windowsNumber) {
        try {
            Room room = new Room(name, space, windowsNumber);
            rooms.add(room);
        } catch (IlluminanceTooMuchException ex) {
            System.out.println(ex.getMessage());
        }
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
