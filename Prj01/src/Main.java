public class Main {
    public static void main (String[] args) throws IlluminanceTooMuchException, SpaceUsageTooMuchException {
        Building building = new Building("Здание 1");
        building.addRoom("Комната 1", 50, 0);
        building.addRoom("Комната 2", 10, 2);
        building.getRoom(0).add(new Lamp(100));
        building.getRoom(0).add(new Lamp(1000));
        building.getRoom(0).add(new Furniture("Стол", 4));
        building.describe();
    }
}
