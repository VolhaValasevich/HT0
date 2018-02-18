public class Furniture {
    //отсюда будет наследоваться любая мебель

    protected String name;
    protected double minSpace;
    protected double maxSpace;

    public Furniture (String furnitureName, double space){
        name = furnitureName;
        minSpace = space;
        maxSpace = space;
    }

    public Furniture (String furnitureName, double minimumSpace, double maximumSpace) {
        name = furnitureName;
        minSpace = minimumSpace;
        maxSpace = maximumSpace;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMinSpace(double minSpace) {
        this.minSpace = minSpace;
    }

    public void setMaxSpace(double maxSpace) {
        this.maxSpace = maxSpace;
    }

    public String getName() {
        return name;
    }

    public double getMinSpace() {
        return minSpace;
    }

    public double getMaxSpace() {
        return maxSpace;
    }

    public String describe() {
        String result;
        if (minSpace == maxSpace) {
            result = "\"" + name + "\" (площадь " + minSpace + " м2)";
            return result;
        } else {
            result = "\"" + name + "\" (площадь от " + minSpace + " м2 до " + maxSpace + "m2)";
            return result;
        }
    }
}
