import java.util.Objects;

public class Room {
    private int id;
    private int roomTypeId;
    private int hotelId;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return id == room.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Room(int id, int roomTypeId, int hotelId) {
        this.id = id;
        this.roomTypeId = roomTypeId;
        this.hotelId = hotelId;
    }

    // Getter and setter methods for id, roomTypeId, and hotelId

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(int roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }
}