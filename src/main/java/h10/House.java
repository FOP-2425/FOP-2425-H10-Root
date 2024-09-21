package h10;

public class House {
    public enum HouseColor {
        RED, GREEN, YELLOW
    }

    private int houseNumber;
    private HouseColor color;

    public House(int houseNumber, HouseColor color) {
        this.houseNumber = houseNumber;
        this.color = color;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public HouseColor getColor() {
        return color;
    }
}
