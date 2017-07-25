package lv.parser.objects;

public class SearchParams {
    private String city;
    private int rooms;
    private int area;
    private int floor;
    private int costBottom;
    private int costTop;

    public SearchParams(String city, int rooms, int area, int floor, int costBottom, int costTop) {
        this.city = city;
        this.rooms = rooms;
        this.area = area;
        this.floor = floor;
        this.costBottom = costBottom;
        this.costTop = costTop;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getRooms() {
        return rooms;
    }

    public void setRooms(int rooms) {
        this.rooms = rooms;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getCostBottom() {
        return costBottom;
    }

    public void setCostBottom(int costBottom) {
        this.costBottom = costBottom;
    }

    public int getCostTop() {
        return costTop;
    }

    public void setCostTop(int costTop) {
        this.costTop = costTop;
    }
}
