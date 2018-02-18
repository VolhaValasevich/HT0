import java.util.ArrayList;

public class Room {
    protected String name;
    protected double space;
    protected ArrayList<Lamp> lamps;
    protected int lampBrightness;
    protected int numberOfWindows;
    protected ArrayList<Furniture> furniture;
    protected double furnitureMinSpace;
    protected double furnitureMaxSpace;
    protected double freeSpacePercent;
    protected int illumination;
    private static final int WINDOW_BRIGHTNESS = 700;
    private static final int MIN_ILLUMINATION = 300;
    private static final int MAX_ILLUMINATION = 4000;
    private static final int MIN_FREE_SPACE_PERCENT = 30;

    public Room(String roomName) throws IlluminanceTooMuchException {
        name = roomName;
        space = 0;
        lamps = new ArrayList<>();
        lampBrightness = 0;
        numberOfWindows = 0;
        furniture = new ArrayList<>();
        furnitureMinSpace = 0;
        furnitureMaxSpace = 0;
        freeSpacePercent = 100;
        if (numberOfWindows*WINDOW_BRIGHTNESS > MAX_ILLUMINATION) {
            throw new IlluminanceTooMuchException(name, numberOfWindows*WINDOW_BRIGHTNESS);
        } else {
            illumination = numberOfWindows * WINDOW_BRIGHTNESS;
        }
    }

    public Room(String roomName, double roomSpace) throws IlluminanceTooMuchException {
        name = roomName;
        space = roomSpace;
        lamps = new ArrayList<>();
        lampBrightness = 0;
        numberOfWindows = 0;
        furniture = new ArrayList<>();
        furnitureMinSpace = 0;
        furnitureMaxSpace = 0;
        freeSpacePercent = 100;
        if (numberOfWindows*WINDOW_BRIGHTNESS > MAX_ILLUMINATION) {
            throw new IlluminanceTooMuchException(name, numberOfWindows*WINDOW_BRIGHTNESS);
        } else {
            illumination = numberOfWindows * WINDOW_BRIGHTNESS;
        }
    }

    public Room(String roomName, double roomSpace, int windowsNumber) throws IlluminanceTooMuchException {
        name = roomName;
        space = roomSpace;
        lamps = new ArrayList<>();
        lampBrightness = 0;
        numberOfWindows = windowsNumber;
        furniture = new ArrayList<>();
        furnitureMinSpace = 0;
        furnitureMaxSpace = 0;
        freeSpacePercent = 100;
        if (numberOfWindows*WINDOW_BRIGHTNESS > MAX_ILLUMINATION) {
            throw new IlluminanceTooMuchException(name, numberOfWindows*WINDOW_BRIGHTNESS);
        } else {
            illumination = numberOfWindows * WINDOW_BRIGHTNESS;
        }
    }

    public boolean add(Furniture object) throws SpaceUsageTooMuchException {
        double percent = (space - furnitureMaxSpace - object.getMaxSpace())/space;
        if (percent < (MIN_FREE_SPACE_PERCENT / 100)) {
            throw new SpaceUsageTooMuchException(name, percent);
        } else {
            furniture.add(object);
            furnitureMaxSpace = furnitureMaxSpace + object.getMaxSpace();
            furnitureMinSpace = furnitureMinSpace + object.getMinSpace();
            freeSpacePercent = ((space - furnitureMaxSpace)/space)*100;
            return true;
        }
    }

    public boolean add (Lamp object) throws IlluminanceTooMuchException {
        if (illumination + object.getBrightness() > MAX_ILLUMINATION) {
            throw new IlluminanceTooMuchException(name, illumination+object.getBrightness());
        } else {
            lamps.add(object);
            lampBrightness = lampBrightness + object.getBrightness();
            illumination = illumination + object.getBrightness();
            return true;
        }
    }

    public boolean checkIllumination() {
        if (illumination < MIN_ILLUMINATION || illumination > MAX_ILLUMINATION) {
            return false;
        } else {
            return true;
        }
    }


    public StringBuffer describe(){
        StringBuffer message = new StringBuffer();
        message.append(name).append("\n");

        message.append("Освещенность = ").append(illumination).append(" (Кол-во окон: ").append(numberOfWindows).append(" по 700 лк, ");
        if (lamps.isEmpty()) {
            message.append("лампочек нет.) \n");
        } else {
            message.append("лампочки: ");
            for (Lamp v : lamps) {
                message.append(v.getBrightness()).append(" лк ");
            }
            message.append(") \n");
        }

        message.append("Площадь: ").append(space).append(" м2 (занято ");
        if (furnitureMaxSpace == furnitureMinSpace) {
            message.append(furnitureMaxSpace);
        } else {
            message.append(furnitureMinSpace).append("-").append(furnitureMaxSpace);
        }
        message.append(" м2, гарантированно свободно ").append(space-furnitureMaxSpace).append(" м2 или ").append(freeSpacePercent).append("% площади)\n");

        if (furniture.isEmpty()) {
            message.append("Мебели нет. \n");
        } else {
            message.append("Мебель: \n");
            for (Furniture v : furniture) {
                message.append(v.describe()).append("\n");
            }
        }
        return message;
    }


    public String getName() {
        return name;
    }

    public double getFurnitureMinSpace() {
        return furnitureMinSpace;
    }

    public void setFurnitureMinSpace(double furnitureMinSpace) {
        this.furnitureMinSpace = furnitureMinSpace;
    }

    public double getFurnitureMaxSpace() {
        return furnitureMaxSpace;
    }

    public void setFurnitureMaxSpace(double furnitureMaxSpace) {
        this.furnitureMaxSpace = furnitureMaxSpace;
    }

    public double getSpace() {
        return space;
    }

    public ArrayList<Lamp> getLamps() {
        return lamps;
    }

    public int getLampBrightness() {
        return lampBrightness;
    }

    public int getNumberOfWindows() {
        return numberOfWindows;
    }

    public ArrayList<Furniture> getFurniture() {
        return furniture;
    }

    public double getFreeSpacePercent() {
        return freeSpacePercent;
    }

    public int getIllumination() {
        return illumination;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSpace(double space) {
        this.space = space;
    }

    public void setLamps(ArrayList<Lamp> lamps) {
        this.lamps = lamps;
    }

    public void setLampBrightness(int lampBrightness) {
        this.lampBrightness = lampBrightness;
    }

    public void setNumberOfWindows(int numberOfWindows) {
        this.numberOfWindows = numberOfWindows;
    }

    public void setFurniture(ArrayList<Furniture> furniture) {
        this.furniture = furniture;
    }

    public void setFreeSpacePercent(double freeSpacePercent) {
        this.freeSpacePercent = freeSpacePercent;
    }

    public void setIllumination(int illumination) {
        this.illumination = illumination;
    }
}
